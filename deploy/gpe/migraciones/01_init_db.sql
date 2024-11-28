-- Versión: 0.1
-- Script inicial IS3 - PINTA: Planes de Informática para Nuevas Trayectorias Académicas

---------------- INICIO SCRIPT -----------------

\connect is3;

-- Establecer horario local en la DB
SET TIMEZONE TO 'America/Argentina/Ushuaia';

-- Crear esquema y asignar permisos
DO $$
BEGIN
    -- Crear esquema 'pinta' si no existe
    IF NOT EXISTS (SELECT 1 FROM information_schema.schemata WHERE schema_name = 'pinta') THEN
        CREATE SCHEMA pinta;
    END IF;
    
    -- Asignar permisos al usuario
    GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA pinta TO is3_admin;
    GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA pinta TO is3_admin;
END $$;

-- Crear tablas
DO $$
BEGIN
    -- Verificar existencia de la base de datos
    IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'is3') THEN
        RAISE EXCEPTION 'NO SE ENCUENTRA REGISTRADA LA BASE DE DATOS is3';
    END IF;

    -- Crear las tablas necesarias
    CREATE TABLE pinta.titulo (
        id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        nombre VARCHAR(512) UNIQUE NOT NULL
    );
    
    CREATE TABLE pinta.actividad_profesional_reservada (
        id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        codigo INTEGER CHECK(codigo > 0) UNIQUE NOT NULL,
        sigla VARCHAR(16) UNIQUE NOT NULL, 
        descripcion VARCHAR(1024) NOT NULL
    );

    CREATE TABLE pinta.trayecto_formativo (
        id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        sigla VARCHAR(16) UNIQUE NOT NULL,
        nombre VARCHAR(128) UNIQUE NOT NULL,
        horas_minimas INTEGER CHECK(horas_minimas > 0) NOT NULL
    );
    
    CREATE TABLE pinta.area_red_unci (
        id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        sigla VARCHAR(16) UNIQUE NOT NULL,
        nombre VARCHAR(128) UNIQUE NOT NULL,
        trayecto_formativo_id INTEGER REFERENCES pinta.trayecto_formativo(id) ON DELETE SET NULL
    );

    CREATE TABLE pinta.peso_relativo (
        id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        descripcion VARCHAR(256) UNIQUE NOT NULL
    );
    
    CREATE DOMAIN pinta.exigencia AS CHAR
    DEFAULT NULL
    CHECK(value = 'O' OR value = 'R');

    CREATE TABLE pinta.intensidad (
        id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        nivel INTEGER UNIQUE NOT NULL,
        descripcion VARCHAR(1024)
    );

    CREATE TABLE pinta.recomendacion_curricular (
        id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        codigo INTEGER UNIQUE NOT NULL, 
        nombre VARCHAR(256) UNIQUE NOT NULL,
        exigencia pinta.exigencia,
        intensidad_id INTEGER REFERENCES pinta.intensidad ON DELETE SET NULL,
        peso_relativo_id INTEGER REFERENCES pinta.peso_relativo(id) ON DELETE SET NULL,
        area_red_unci_id INTEGER REFERENCES pinta.area_red_unci(id) ON DELETE SET NULL
    );

    CREATE TABLE pinta.plan_estudio (
        id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        nombre VARCHAR(64) UNIQUE NOT NULL
    );

    CREATE TABLE pinta.asignatura (
        id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        codigo VARCHAR(8) NOT NULL, 
        nombre VARCHAR(256) NOT NULL,
        carga_horaria INTEGER CHECK(carga_horaria >= 0),
        plan_estudios_id INTEGER NOT NULL REFERENCES pinta.plan_estudio(id) ON DELETE CASCADE,
        UNIQUE(id, plan_estudios_id)
    );

    CREATE TABLE pinta.contenido_minimo_plan_estudio (
        id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        nombre VARCHAR(256) NOT NULL,
        asignatura_id INTEGER REFERENCES pinta.asignatura(id) ON DELETE SET NULL,
        horas_practica INTEGER CHECK(horas_practica > 0),
        horas_teoria INTEGER CHECK(horas_teoria > 0),
        exigencia pinta.exigencia, 
        intensidad_id INTEGER REFERENCES pinta.intensidad(id) ON DELETE SET NULL,
        plan_estudios_id INTEGER NOT NULL REFERENCES pinta.plan_estudio(id) ON DELETE CASCADE,
        UNIQUE(id, plan_estudios_id)
    );

    CREATE TABLE pinta.contenido_curricular_basico (
        id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        nombre VARCHAR(256) UNIQUE NOT NULL,
        trayecto_formativo_id INTEGER REFERENCES pinta.trayecto_formativo(id) ON DELETE SET NULL
    );

    CREATE TABLE pinta.titulo_x_actividad_profesional_reservada (
        id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        titulo_id INTEGER REFERENCES pinta.titulo(id) ON DELETE SET NULL,
        actividad_profesional_reservada_id INTEGER REFERENCES pinta.actividad_profesional_reservada(id) ON DELETE SET NULL,
        UNIQUE(titulo_id, actividad_profesional_reservada_id)
    );

    CREATE TABLE pinta.actividad_profesional_reservada_x_recomendacion_curricular (
        id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        actividad_profesional_reservada_id INTEGER REFERENCES pinta.actividad_profesional_reservada(id) ON DELETE SET NULL,
        recomendacion_curricular_id INTEGER REFERENCES pinta.recomendacion_curricular(id) ON DELETE SET NULL,
        UNIQUE(actividad_profesional_reservada_id, recomendacion_curricular_id)
    );

    CREATE TABLE pinta.recomendacion_curricular_x_contenido_minimo_plan_estudio (
        id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        recomendacion_curricular_id INTEGER NOT NULL REFERENCES pinta.recomendacion_curricular(id) ON DELETE CASCADE,
        contenido_minimo_plan_estudio_id INTEGER NOT NULL REFERENCES pinta.contenido_minimo_plan_estudio(id) ON DELETE CASCADE,
	horas_practica INTEGER CHECK(horas_practica > 0),
        horas_teoria INTEGER CHECK(horas_teoria > 0),
        exigencia pinta.exigencia,
        intensidad_id INTEGER REFERENCES pinta.intensidad(id) ON DELETE SET NULL,
	observaciones varchar(255),
        UNIQUE(recomendacion_curricular_id, contenido_minimo_plan_estudio_id)
    );

    RAISE NOTICE 'OPERACIÓN FINALIZADA OK';
END $$;

----------------  FIN SCRIPT -------------------

-- Procedimiento para clonar planes de estudio
CREATE OR REPLACE PROCEDURE pinta.clonar_plan_estudio(
    viejo_plan_estudio_id INT,   -- Viejo plan_estudio ID
    nuevo_plan_estudio_id INT    -- Nuevo plan_estudio ID
)
LANGUAGE plpgsql
AS $$
DECLARE
    viejaAsignaturaId INT;
    nuevaAsignaturaId INT;
    rec RECORD;  -- Variable que almacena cada fila en el bucle
BEGIN
    -- Crear una tabla temporal para almacenar las asignaturas procesadas
    CREATE TEMP TABLE asignatura_cache (
        vieja_asignatura_id INT PRIMARY KEY,
        nueva_asignatura_id INT
    ) ON COMMIT DROP;  -- La tabla se elimina al final de la transacción

    -- Iterar sobre cada contenido_minimo_plan_estudio asociado al viejo plan_estudio
    FOR rec IN
        SELECT id, asignatura_id, nombre, horas_practica, horas_teoria, exigencia, intensidad_id
        FROM pinta.contenido_minimo_plan_estudio
        WHERE plan_estudios_id = viejo_plan_estudio_id
    LOOP
        -- Obtener el viejo id de la asignatura del contenido
        viejaAsignaturaId := rec.asignatura_id;

        -- Verificar si ya se ha creado la asignatura (si está en la tabla temporal)
        SELECT nueva_asignatura_id INTO nuevaAsignaturaId
        FROM asignatura_cache
        WHERE vieja_asignatura_id = viejaAsignaturaId
        LIMIT 1;

        -- Si no se encuentra el nuevo ID de la asignatura, se inserta en la tabla y se cachea
        IF NOT FOUND THEN
            -- Comprobar si existe en el nuevo plan
            SELECT id INTO nuevaAsignaturaId
            FROM pinta.asignatura
            WHERE id = viejaAsignaturaId
              AND plan_estudios_id = nuevo_plan_estudio_id
            LIMIT 1;

            -- Si no existe, se agrega al nuevo plan
            IF NOT FOUND THEN
                INSERT INTO pinta.asignatura (codigo, nombre, carga_horaria, plan_estudios_id)
                SELECT codigo, nombre, carga_horaria, nuevo_plan_estudio_id
                FROM pinta.asignatura
                WHERE id = viejaAsignaturaId
                RETURNING id INTO nuevaAsignaturaId;
            END IF;

            -- Cachear el nuevo ID de la Asignatura con el viejo ID de la Asignatura en la tabla temporal
            INSERT INTO asignatura_cache (vieja_asignatura_id, nueva_asignatura_id)
            VALUES (viejaAsignaturaId, nuevaAsignaturaId);
        END IF;

        -- Insertar el contenido_minimo_plan_estudio clonado, asociándolo con la nueva asignatura
        INSERT INTO pinta.contenido_minimo_plan_estudio
        (nombre, asignatura_id, horas_practica, horas_teoria, exigencia, intensidad_id, plan_estudios_id)
        VALUES (rec.nombre, nuevaAsignaturaId, rec.horas_practica, rec.horas_teoria, rec.exigencia, rec.intensidad_id, nuevo_plan_estudio_id);

        -- Salida de depuración (opcional)
        RAISE NOTICE 'Contenido clonado: % con nuevo asignatura_id: %', rec.nombre, nuevaAsignaturaId;
    END LOOP;

EXCEPTION
    WHEN OTHERS THEN
        -- Manejo de errores e impresión de mensaje
        RAISE NOTICE 'Error: %', SQLERRM;
END $$;

-- Funcion para exportar CSV (retorna el booleano del resultado de la operación)
CREATE OR REPLACE FUNCTION pinta.exportar_csv(nombre_tabla VARCHAR, ruta_archivo VARCHAR)
RETURNS BOOLEAN AS
$$
DECLARE
    query TEXT;
BEGIN
    -- Construir la consulta para exportar la tabla indicada como argumento
    query := format(
        'COPY (SELECT * FROM pinta.%I) TO %L WITH CSV HEADER',
        nombre_tabla, ruta_archivo
    );

    -- Intentar ejecutar la consulta y retornar el resultado booleano
    BEGIN
        EXECUTE query;
        RETURN TRUE;  -- Éxito
    EXCEPTION
        WHEN OTHERS THEN
            -- Si ocurre un error, FALSE
            RETURN FALSE;
    END;
END;
$$ LANGUAGE plpgsql;

 -- Funcion para exportar CSV de consulta general (retorna el booleano del resultado de la operación)
CREATE OR REPLACE FUNCTION pinta.exportar_consulta_csv(ruta_archivo VARCHAR)
RETURNS BOOLEAN AS
$$
DECLARE
    query TEXT;
BEGIN
    query := format(
        'COPY (select a.plan_estudios_id, a.id, a.codigo, a.nombre, a.carga_horaria, cm.id as cm_id, cm.nombre, cm.horas_practica, cm.horas_teoria, cm.exigencia, cm.intensidad_id from pinta.asignatura as a inner join pinta.contenido_minimo_plan_estudio as cm on (a.id = cm.asignatura_id) order by a.plan_estudios_id, a.id, cm.id) TO %L WITH CSV HEADER',
        ruta_archivo
    );

    -- Intentar ejecutar la consulta y retornar el resultado booleano
    BEGIN
        EXECUTE query;
        RETURN TRUE;  -- Éxito
    EXCEPTION
        WHEN OTHERS THEN
            -- Si ocurre un error, FALSE
            RETURN FALSE;
    END;
END;
$$ LANGUAGE plpgsql;
