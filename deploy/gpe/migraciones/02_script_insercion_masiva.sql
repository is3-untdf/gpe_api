
\connect is3

DO $$
BEGIN
    SET search_path TO pinta;

    INSERT INTO titulo (nombre)
	VALUES ('Licenciatura en Sistemas (con Orientación en Calidad de Software)');

	INSERT INTO intensidad(nivel, descripcion)
	VALUES (1, 'Entender lo que el concepto representa y su significado. Este nivel de dominio involucra el conocimiento de su existencia y la interpretación de soluciones en las que se aplica, pero no su producción'),
		   (2, 'Además, tener capacidad para utilizar o aplicar el concepto de manera concreta. Este nivel de dominio implica capacidad para resolver problemas que requieran la aplicación del concepto.'),
		   (3, 'Capacidad para considerar el concepto desde múltiples puntos de vista y/ o justificar la selección de un abordaje particular para resolver un problema. Este nivel de dominio implica más que usar el concepto; involucrala habilidad para seleccionar un abordaje a partir de la comprensión de las alternativas.');

	INSERT INTO peso_relativo(descripcion)
	VALUES ('T'),
		   ('P'),
		   ('T=P'),
		   ('T>P'),
		   ('P>T');

	INSERT INTO trayecto_formativo(sigla, nombre, horas_minimas)
	VALUES ('CBGyE', 'Ciencias Básicas Generales y Específicas', 650),
		   ('AyL', 'Algoritmos y Lenguajes', 500),
		   ('ISBDSI', 'Ingeniería de Software, Base de Datos y Sistemas de Información', 650),
		   ('ARSORE', ' Arquitectura, Sistemas Operativos y Redes', 350),
		   ('ASyP', 'Aspectos Sociales y Profesionales', 50);

	INSERT INTO plan_estudio (nombre)
	VALUES ('Res CS 049-2017');

	INSERT INTO asignatura (codigo, nombre, carga_horaria, plan_estudios_id)
	VALUES ('IF001', 'Elementos de Informática', 105, 1),
		   ('IF002', 'Expresión de Problemas y Algoritmos', 90, 1),
		   ('MA045', 'Algebra', 135, 1),
		   ('IF003', 'Algorítmica y Programación I', 120, 1),
		   ('MA008', 'Elementos de Lógica y Matemática Discreta', 120, 1),
		   ('MA046', 'Análisis Matemático', 165, 1),
		   ('IF004', 'Sistemas y Organizaciones', 90, 1),
		   ('IF005', 'Arquitectura de Computadoras', 120, 1),
		   ('IF006', 'Algorítmica y Programación II', 120, 1),
		   ('MA006', 'Estadística', 90, 1),
		   ('IF007', 'Bases de Datos I', 135, 1),
		   ('IF030', 'Programación y Diseño Orientado a Objetos', 120, 1),
		   ('IF031', 'Ingeniería de Software I', 150, 1),
		   ('IF009', 'Laboratorio de Programación y Lenguajes', 90, 1),
		   ('IF013', 'Fundamentos Teóricos de Informática', 120, 1),
		   ('IF033', 'Ingeniería de Software II', 120, 1),
		   ('IF038', 'Introducción a la Concurrencia', 60, 1),
		   ('IF044', 'Bases de Datos II', 120, 1),
		   ('IF037', 'Sistemas Operativos', 120, 1),
		   ('IF055', 'Laboratorio de Software', 105, 1),
		   ('IF056', 'Seminario de Aspectos Legales y Profesionales I', 60, 1),
		   ('IF019', 'Redes y Transmisión de Datos', 135, 1),
		   ('IF020', 'Paradigmas y Lenguajes de Programación', 120, 1),
		   ('IF017', 'Taller de nuevas Tecnologías', 90, 1),
		   ('IF022', 'Sistemas Distribuidos', 120, 1),
		   ('IF035', 'Ingeniería de Software III', 120, 1),
		   ('IF057', 'Seminario de Aspectos Legales y Profesionales II', 60, 1),
		   ('IF059', 'Sistemas Inteligentes', 90, 1),
		   ('IF060', 'Sistemas de Tiempo Real', 105, 1),
		   ('IF061', 'Sistemas Paralelos', 90, 1),
		   ('IF062', 'Bases de Datos Distribuidas', 90, 1),
		   ('IF063', 'Seminario de Seguridad', 60, 1),
		   ('IF027', 'Modelos y Simulación', 90, 1),
		   ('IF042', 'Proyecto de Software', 120, 1),
		   ('FA007', 'Acreditación de Idioma Inglés', 0, 1),
		   ('IF026', 'Tesina', 200, 1);


    ----- COMIENZO INSERCIÓN AREAS RED UNCI ------
	DROP TABLE IF EXISTS areas_red_unci_temp;

	CREATE TEMPORARY TABLE areas_red_unci_temp(
		sigla_trayecto_formativo VARCHAR(16) NOT NULL,
		sigla_area VARCHAR(16) NOT NULL,
		nombre VARCHAR(128) NOT NULL
	);

	INSERT INTO areas_red_unci_temp (sigla_trayecto_formativo, sigla_area, nombre)
	VALUES ('CBGyE', 'CBG', 'Ciencias Básicas Generales'),
			('CBGyE', 'CBE', 'Ciencias Básicas Específicas '),
			('AyL', 'AyED', 'Algoritmos y Estructuras de Datos'),
			('AyL', 'PyL', 'Paradigmas y Lenguajes'),
			('ISBDSI', 'IS', 'Ingeniería de Software'),
			('ISBDSI', 'BD', 'Bases de Datos'),
			('ISBDSI', 'SI', 'Sistemas de Información'),
			('ARSORE', 'AR', 'Arquitectura'),
			('ARSORE', 'SO', 'Sistemas Operativos'),
			('ARSORE', 'RE', 'Redes');

	INSERT INTO area_red_unci (sigla, nombre, trayecto_formativo_id)
	SELECT temp.sigla_area,
	       temp.nombre,
	       tf.id
	FROM trayecto_formativo tf
		 JOIN areas_red_unci_temp temp ON (tf.sigla = temp.sigla_trayecto_formativo);
	----- FIN INSERCIÓN AREAS RED UNCI ------


	---- COMIENZO INSERCION CONTENIDOS CURRICULARES BÁSICOS ---
	DROP TABLE IF EXISTS contenidos_curriculares_basicos_temp;

	CREATE TEMPORARY TABLE contenidos_curriculares_basicos_temp (
		sigla_trayecto_formativo varchar(16) NOT NULL,
		nombre varchar(256) NOT NULL
	);

	INSERT INTO contenidos_curriculares_basicos_temp (sigla_trayecto_formativo, nombre)
	VALUES ('CBGyE', 'Álgebra lineal, Análisis Numérico, Cálculo diferencial e integral, Matemática discreta y Probabilidad y estadística'),
			('CBGyE', 'Lógica proposicional y de primer orden'),
			('CBGyE', 'Fundamentos de Autómatas y Gramáticas'),
			('CBGyE', 'Evaluación de Computabilidad. Complejidad computacional'),
			('CBGyE', 'Fundamentos de Inteligencia Artificial'),
			('AyL', 'Lenguajes, Algoritmos y Estructuras de Datos'),
			('AyL', 'Fundamentos de Concurrencia y paralelismo. '),
			('AyL', 'Programación distribuida y paralela'),
			('AyL', 'Paradigmas de programación'),
			('ISBDSI', 'Análisis, Diseño, Implementación y Mantenimiento en Ingeniería de Software'),
			('ISBDSI', 'Evaluación de Calidad de software'),
			('ISBDSI', 'Gestión de Auditoría de Sistemas Informáticos'),
			('ISBDSI', 'Fundamentos y aplicaciones de Bases de Datos'),
			('ISBDSI', 'Proyecto de Sistemas de Información'),
			('ISBDSI', 'Fundamentos de Teoría de Sistemas y Modelos'),
			('ISBDSI', 'Análisis de Organizaciones y Modelos de Negocios.'),
			('ISBDSI', 'Proyecto de Sistemas Informáticos'),
			('ISBDSI', 'Análisis y Gestión de Seguridad Informática en Software y Datos'),
			('ARSORE', 'Fundamentos de Organización y Arquitectura de Computadoras'),
			('ARSORE', 'Gestión de Sistemas Operativos'),
			('ARSORE', 'Análisis y Evaluación de Redes de Computadoras'),
			('ARSORE', 'Fundamentos de Teoría de la Información y la comunicación'),
			('ARSORE', 'Análisis y gestión de Seguridad Informática en hardware y sistemas operativos');

    INSERT INTO contenido_curricular_basico (nombre, trayecto_formativo_id)
	SELECT temp.nombre,
	       tf.id
	FROM trayecto_formativo tf
		 JOIN contenidos_curriculares_basicos_temp temp ON (tf.sigla = temp.sigla_trayecto_formativo);
	---- FIN INSERCION CONTENIDOS CURRICULARES BÁSICOS --------

	---- COMIENZO INSERCION RECOMENDACIONES CURRICULARES ----
	DROP TABLE IF EXISTS recomendaciones_curriculares_temp;

	CREATE TEMPORARY TABLE recomendaciones_curriculares_temp (
		sigla_area_red_unci VARCHAR(16) NOT NULL,
		nombre VARCHAR(256) NOT NULL,
		exigencia CHAR(1),
        intensidad_nivel INTEGER,
		peso_relativo VARCHAR(256)
	);

	INSERT INTO recomendaciones_curriculares_temp (sigla_area_red_unci, nombre, exigencia, intensidad_nivel, peso_relativo)
	VALUES ('AR', 'ARSORE-AR Arq. multiprocesador.', 'O', 2, 'T>P'),
			('AR', 'ARSORE-AR Arquitectura y Organización de Computadoras.', 'O', 2, 'T=P'),
			('AR', 'ARSORE-AR Arquitecturas Orientadas a Servicios', 'O', 2, 'T=P'),
			('AR', 'ARSORE-AR Cluster, Grid y Cloud Computing. Arquitectura y Soft de base.', 'O', 2, 'T=P'),
			('AR', 'ARSORE-AR Comunicación por Msgy Memoria compartida en Arq. multiprocesador.', 'R', NULL, NULL),
			('AR', 'ARSORE-AR Interfaces no tradicionales', 'R', NULL, NULL),
			('AR', 'ARSORE-AR Lenguajes de máquinay Lenguaje Ensamblador.', 'O', 1, 'T=P'),
			('AR', 'ARSORE-AR Manejo de Excepciones', 'O', 2, 'T=P'),
			('AR', 'ARSORE-AR Máquinas Algorítmicas.Procesadores de alta prestación.', 'R', NULL, NULL),
			('AR', 'ARSORE-AR Organización y Administración de memoria.', 'O', 2, 'T>P'),
			('AR', 'ARSORE-AR Procesamiento digital de señales', NULL, NULL, NULL),
			('AR', 'ARSORE-AR Representación de losdatos a nivel máquina.', 'O', 1, 'T>P'),
			('AR', 'ARSORE-AR Sistemas embebidos', NULL, NULL, NULL),
			('RE', 'ARSORE-RE Internet de las cosas.', NULL, NULL, NULL),
			('RE', 'ARSORE-RE Introducción de Teoría de la Información y la Comunicación para Redes de Datos', 'O', 1, 'T'),
			('RE', 'ARSORE-RE Protocolos/servicios de integración. Comunicaciones unificadas.', 'R', NULL, NULL),
			('RE', 'ARSORE-RE Redes de Sensores.', NULL, NULL, NULL),
			('RE', 'ARSORE-RE Redes: Modelos, Topologías, Protocolos, Algoritmos deruteo. Administración de Redes.', 'O', 2, 'T=P'),
			('RE', 'ARSORE-RE Seguridad en redes y dispositivos', 'O', 2, 'T=P'),
			('RE', 'ARSORE-RE Sistemas cliente/servidor, v variantes. El modelo computacional de la Web', 'O', 2, 'T=P'),
			('SO', 'ARSORE-SO Arquitecturas de almacenamiento.', NULL, NULL, NULL),
			('SO', 'ARSORE-SO Fallos y Tolerancia a fallos.', 'O', 2, 'T=P'),
			('SO', 'ARSORE-SO Sistemas Distribuidosy paralelos', 'O', 2, 'T>P'),
			('SO', 'ARSORE-SO Sistemas Operativos orientados: a TR, Sist. embebidos, Móviles.', 'O', 2, 'T>P'),
			('SO', 'ARSORE-SO Sistemas Operativos. Sistemas Operativos Distribuidos.', 'O', 2, 'T>P'),
			('SO', 'ARSORE-SO Virtualización / Máquinas virtuales y reconfiguracióndinámica.', 'O', 1, 'T=P'),
			('ASyP', 'ASyP Asp. legales. Peritaje y Auditoría', 'O', 2, 'T>P'),
			('ASyP', 'ASyP Conceptos de Micro y Macroecon. Análisis de Costos, Financiamiento. Rentabilidad, Amortización.', NULL, NULL, NULL),
			('ASyP', 'ASyP Conceptos de Software libre,Hardware libre y Cont. de acceso abierto', 'O', 1, 'T>P'),
			('ASyP', 'ASyP Dirección Ejecutiva de organizac. de software', NULL, NULL, NULL),
			('ASyP', 'ASyP Fundamentos para la comprensión de una lengua extranjera (preferentemente inglés).', NULL, NULL, NULL),
			('ASyP', 'ASyP Gobierno de TI', 'R', NULL, NULL),
			('ASyP', 'ASyP Higiene y seguridad en el trabajo.', 'O', 1, 'T'),
			('ASyP', 'ASyP Historia y Evoluc. de la Informática', 'O', 1, 'T'),
			('ASyP', 'ASyP Innovación y Emprendedorismo', 'R', NULL, NULL),
			('ASyP', 'ASyP Legislación laboral, comercial yespecífica. Contratos.', 'O', 1, 'T>P'),
			('ASyP', 'ASyP Organización empresarial: Estruct. de empresas. Planificación yprogramac. Relaciones laborales. Teletrabajo.', NULL, NULL, NULL),
			('ASyP', 'ASyP Propiedad Intelectual, licenciam. de soft. y contratos informáticos.', 'O', 2, 'T'),
			('ASyP', 'ASyP Protección ambiental. Accesibilidad. Legislaciones y Normas sobre Protección ambiental yAccesib.', NULL, NULL, NULL),
			('ASyP', 'ASyP Responsabilidad Social, Prof. yAmbiental. Ejercicio y Ética Profesional.', 'O', 2, 'T'),
			('ASyP', 'ASyP-Impacto ambiental de las actividades Informáticas', 'O', 1, 'T'),
			('AyED', 'AyL-AyED Algoritmos fundamentales:Recorrido, búsqueda, ordenamiento, actualización', 'O', 3, 'P>T'),
			('AyED', 'AyL-AyED Algoritmos secuenciales, concurrentes, distribuidos y paralelos.', 'O', 3, 'P>T'),
			('AyED', 'AyL-AyED Algoritmos y Programas', 'O', 3, 'P>T'),
			('AyED', 'AyL-AyED Datos elementales. Estructuras de Datos. Tipos abstractos de datos.', 'O', 3, 'P>T'),
			('AyED', 'AyL-AyED Eficiencia, legibilidad y reusabilidad de Algoritmos.', 'O', 3, 'T=P'),
			('AyED', 'AyL-AyED Estrategias de diseño de algoritmos', 'O', 3, 'P>T'),
			('AyED', 'AyL-AyED Estructuras de Control. Eventos. Excepciones.', 'O', 3, 'P>T'),
			('AyED', 'AyL-AyED Recursividad.', 'O', 3, 'P>T'),
			('AyED', 'AyL-AyED Representación de datos en memoria. Estrategias de implementación. Manejo de memoriaen ejecución.', 'O', 2, 'P>T'),
			('AyED', 'AyL-AyED Uso de heurísticas en Algoritmos', NULL, NULL, NULL),
			('AyED', 'AyL-AyED Verificación y Depuraciónde Algoritmos', 'O', 3, 'P>T'),
			('PyL', 'AyL-PyL Concurrencia y Paralelismo', 'O', 2, 'P>T'),
			('PyL', 'AyL-PyL Paradigmas y Lenguajesde Programación.', 'O', 3, 'T>P'),
			('PyL', 'AyL-PyL Programación basada en eventos', NULL, NULL, NULL),
			('PyL', 'AyL-PyL Programación basada en scripting', NULL, NULL, NULL),
			('PyL', 'AyL-PyL Programación Funcional.', 'O', 2, 'P>T'),
			('PyL', 'AyL-PyL Programación imperativa', 'O', 3, 'P>T'),
			('PyL', 'AyL-PyL Programación Lógica.', 'O', 2, 'P>T'),
			('PyL', 'AyL-PyL Programación orientada a Objetos.', 'O', 3, 'P>T'),
			('CBE', 'CB-CBE Análisis de Algoritmos', 'O', 2, 'T=P'),
			('CBE', 'CB-CBE Complejidad Computacional', 'O', 1, 'T>P'),
			('CBE', 'CB-CBE Eficiencia Energética en Algoritmos, Programas y Sistemas', 'O', 1, 'T=P'),
			('CBE', 'CB-CBE Especificaciones Formales', 'O', 2, 'T>P'),
			('CBE', 'CB-CBE Fundamentos de Compiladores e Intérpretes', 'O', 1, 'T'),
			('CBE', 'CB-CBE Fundamentos de InteligenciaArtificial', 'O', 1, 'T'),
			('CBE', 'CB-CBE Fundamentos de Lenguajesde Programación', 'O', 1, 'T=P'),
			('CBE', 'CB-CBE Grafos y Árboles', 'O', 2, 'T=P'),
			('CBE', 'CB-CBE Lenguajes formales: Autómatas y Gramáticas', 'O', 1, 'T>P'),
			('CBE', 'CB-CBE Lógica proposicional y deprimer orden', 'O', 1, 'P>T'),
			('CBE', 'CB-CBE Seguridad en Sistemas Computacionales (Infraestructura, Hardware y Software)', 'O', 1, 'T>P'),
			('CBE', 'CB-CBE Técnicas de Pruebas', 'O', 2, 'T=P'),
			('CBE', 'CB-CBE Teoría de Base de Datos', 'O', 1, 'T'),
			('CBE', 'CB-CBE Tratabilidad y Computabilidad', 'O', 1, 'T>P'),
			('CBG', 'CB-CBG Algebra Lineal', 'O', 2, 'T=P'),
			('CBG', 'CB-CBG Álgebras de Boole', 'O', 1, 'P>T'),
			('CBG', 'CB-CBG Análisis Numérico y Cálculo Avanzado', NULL, NULL, NULL),
			('CBG', 'CB-CBG Cálculo diferencial eintegral en una y varias variables', 'O', 2, 'T=P'),
			('CBG', 'CB-CBG Ecuaciones Diferenciales ordinarias', 'O', 2, 'T=P'),
			('CBG', 'CB-CBG Estructuras algebraicas', 'O', 2, 'T=P'),
			('CBG', 'CB-CBG Estructuras Discretas', 'O', 2, 'T>P'),
			('CBG', 'CB-CBG Física', NULL, NULL, NULL),
			('CBG', 'CB-CBG Geometría Analítica', 'O', 2, 'T=P'),
			('CBG', 'CB-CBG Probabilidad y Estadística', 'O', 2, 'P>T'),
			('BD', 'ISBDSI-BD Bases de Datosdistribuidas', 'O', 2, 'T>P'),
			('BD', 'ISBDSI-BD Gestión de datos masivos', 'O', 2, 'T>P'),
			('BD', 'ISBDSI-BD Lenguajes de DBMS', 'O', 3, 'P>T'),
			('BD', 'ISBDSI-BD Minería de datos. (Datamining)', 'O', 2, 'T=P'),
			('BD', 'ISBDSI-BD Modelado y calidad dedatos', 'O', 3, 'P>T'),
			('BD', 'ISBDSI-BD Privacidad, Seguridad eIntegridad en BD. Elementos de Criptografía', 'O', 2, 'T>P'),
			('BD', 'ISBDSI-BD Sistemas de Gestión deBases de Datos. Escalabilidad, eficiencia y efectividad', 'O', 3, 'T>P'),
			('IS', 'ISBDSI-IS Administración y Gestión de proyectos Informáticos', 'O', 3, 'T=P'),
			('IS', 'ISBDSI-IS Auditoría y Peritaje Informático', 'O', 3, 'P>T'),
			('IS', 'ISBDSI-IS Calidad de Software: del producto y del proceso', 'O', 3, 'T=P'),
			('IS', 'ISBDSI-IS El Proceso de software. Ciclos de vida del software', 'O', 3, 'T>P'),
			('IS', 'ISBDSI-IS Evolución del software', 'O', 3, 'T>P'),
			('IS', 'ISBDSI-IS Gestión de Configuración del Software', 'O', 2, 'T=P'),
			('IS', 'ISBDSI-IS Ingeniería de Requerimientos', 'O', 3, 'T=P'),
			('IS', 'ISBDSI-IS Interacción Humano Computadora (HCI)', 'O', 2, 'T>P'),
			('IS', 'ISBDSI-IS Métodos formales', 'R', NULL, NULL),
			('IS', 'ISBDSI-IS Modelado y Arquitectura de la Aplicación. Lenguajes de Modelado', 'O', 3, 'P>T'),
			('IS', 'ISBDSI-IS Reingeniería de software.', 'O', 2, 'T>P'),
			('IS', 'ISBDSI-IS Sistemas colaborativos', 'R', NULL, NULL),
			('IS', 'ISBDSI-IS Sistemas de Tiempo Real.', 'O', 2, 'T=P'),
			('IS', 'ISBDSI-IS Sistemas para plataformas Móviles', 'R', NULL, NULL),
			('IS', 'ISBDSI-IS Sistemas WEB', 'O', 2, 'P>T'),
			('IS', 'ISBDSI-IS Verificación y validación del software', 'O', 3, 'T=P'),
			('SI', 'ISBDSI-SI Conceptos y metodologías para su construcción', 'O', 3, 'T=P'),
			('SI', 'ISBDSI-SI Modelos de Sistemas', 'R', NULL, NULL),
			('SI', 'ISBDSI-SI Privacidad, integridad y seguridad en sistemas de información', 'O', 3, 'T=P'),
			('SI', 'ISBDSI-SI Teoría general deSistemas', 'O', 2, 'T'),
			('SI', 'ISBDSI-SI Visión estratégica de la organización y modelo de negocio', 'O', 1, 'T>P');


    INSERT INTO recomendacion_curricular(codigo, nombre, exigencia, intensidad_id, peso_relativo_id, area_red_unci_id)
    SELECT ROW_NUMBER() OVER (), -- modificar luego por el código preestablecido en el documento
           temp.nombre,
           temp.exigencia,
           i.id,
           p.id,
           a.id
    FROM recomendaciones_curriculares_temp temp
             LEFT JOIN trayecto_formativo tf on (temp.sigla_area_red_unci = tf.sigla)
             LEFT JOIN intensidad i ON (temp.intensidad_nivel = i.nivel)
             LEFT JOIN peso_relativo p ON (temp.peso_relativo = p.descripcion)
             LEFT JOIN area_red_unci a ON (temp.sigla_area_red_unci = a.sigla);
    ---- FIN INSERCION RECOMENDACIONES CURRICULARES ---------


	DROP TABLE IF EXISTS contenidos_minimos_tmp;

	CREATE TEMPORARY TABLE contenidos_minimos_tmp (
		codigo varchar(8) not null,
		nombre varchar(256) not null,
		plan_estudios_id integer not null
	);

	INSERT INTO contenidos_minimos_tmp (codigo, nombre, plan_estudios_id)
	VALUES ('IF001', 'Historia de la Computación. Computación y sociedad', 1),
			('IF001', 'Sistemas de numeración', 1),
			('IF001', 'Arquitectura y organización de computadoras', 1),
			('IF001', 'Representación de los datos a nivel máquina. Error.', 1),
			('IF001', 'Conceptos de software, sistemas operativos, lenguajes de programación.', 1),
			('IF001', 'Nociones de interpretación y compilación', 1),
			('IF002', 'Análisis y resolución de problemas', 1),
			('IF002', 'Especificación simbólica.', 1),
			('IF002', 'Expresión de soluciones en un lenguaje algorítmico.', 1),
			('MA045', 'Estructuras Algebraicas', 1),
			('MA045', 'Sistemas de ecuaciones lineales y matrices.', 1),
			('MA045', 'Determinantes.', 1),
			('MA045', 'Vectores en los espacios bidimensional y tridimensional.', 1),
			('MA045', 'Espacios vectoriales.', 1),
			('MA045', 'Transformaciones lineales.', 1),
			('MA045', 'Valores y vectores propios.', 1),
			('MA045', 'Geometría de las transformaciones lineales del plano.', 1),
			('IF003', 'Análisis y diseño de algoritmos.', 1),
			('IF003', 'Tipos de datos y estructuras de datos elementales. Representación en memoria.', 1),
			('IF003', 'Estructuras de Control. Programación estructurada.', 1),
			('IF003', 'Procedimientos y funciones.', 1),
			('IF003', 'Recursividad.', 1),
			('IF003', 'Algoritmos fundamentales: recorrido, búsqueda, ordenamiento, actualización.', 1),
			('MA008', 'Elementos de la lógica formal.', 1),
			('MA008', 'Lógica proposicional y de primer orden: enfoque sintáctico y semántico.', 1),
			('MA008', 'Lógica de términos y predicados.', 1),
			('MA008', 'Teoría de las estructuras discretas. Definiciones y pruebas estructurales', 1),
			('MA008', 'Técnicas de prueba.', 1),
			('MA008', 'Estructura de pruebas formales.', 1),
			('MA008', 'Conjuntos parcialmente ordenados.', 1),
			('MA008', 'Reticulados.', 1),
			('MA008', 'Álgebras booleanas.', 1),
			('MA046', 'Funciones reales de una variable.', 1),
			('MA046', 'Límite funcional y continuidad.', 1),
			('MA046', 'Cálculo Diferencial.', 1),
			('MA046', 'Cálculo Integral.', 1),
			('MA046', 'Sucesiones y series numéricas.', 1),
			('MA046', 'Funciones de varias variables reales.', 1),
			('MA046', 'Diferenciabilidad.', 1),
			('IF004', 'Teoría General de Sistemas.', 1),
			('IF004', 'La organización como sistema.', 1),
			('IF004', 'Gestión de organizaciones. Gestión de recursos humanos.', 1),
			('IF004', 'Sistemas de Información: conceptos y metodologías para su construcción.', 1),
			('IF004', 'Integración del área de sistemas en la organización.', 1),
			('IF004', 'Privacidad, integridad y seguridad en sistemas de información.', 1),
			('IF004', 'Administración de sistemas de información.', 1),
			('IF005', 'Introducción a las técnicas digitales. Circuitos combinatorios y secuenciales.', 1),
			('IF005', 'Análisis detallado de los subsistemas de un procesador.', 1),
			('IF005', 'Ejecución de instrucciones. Interrupciones.', 1),
			('IF005', 'Análisis temporal de las instrucciones de máquina.', 1),
			('IF005', 'Unidades de control cableadas y microprogramadas.', 1),
			('IF005', 'Memorias. Jerarquía de memoria. Organización funcional.', 1),
			('IF005', 'Técnicas de comunicación con los dispositivos de entrada/salida.', 1),
			('IF005', 'Lenguaje Ensamblador.', 1),
			('IF005', 'Introducción a las arquitecturas no convencionales. Arquitecturas no von Neumann', 1),
			('IF005', 'Máquinas algorítmicas. Procesadores de alta prestación.', 1),
			('IF005', 'Arquitecturas multiprocesadores.', 1),
			('IF005', 'Conceptos de arquitecturas reconfigurables.', 1),
			('IF006', 'Estrategias de diseño de algoritmos.', 1),
			('IF006', 'Tipos abstractos de datos. Estructuras de datos: listas, pilas, colas, árboles, grafos. Tipos de datos recursivos.', 1),
			('IF006', 'Programación con recursividad.', 1),
			('IF006', 'Representación de datos en memoria. Estrategias de implementación. Manejo de memoria en ejecución.', 1),
			('IF006', 'Métodos avanzados de ordenamiento y búsqueda.', 1),
			('IF006', 'Análisis de complejidad de algoritmos.', 1),
			('IF006', 'Tratamiento de errores y manejo de excepciones.', 1),
			('IF006', 'Corrección, verificación y eficiencia de algoritmos.', 1),
			('IF006', 'Algoritmos numéricos y propagación del error.', 1),
			('MA006', 'Estadística descriptiva.', 1),
			('MA006', 'Teoría de la probabilidad.', 1),
			('MA006', 'Distribuciones de frecuencia y de probabilidad.', 1),
			('MA006', 'Inferencia estadística. Muestreo. Estimación. Prueba de hipótesis. Prueba de bondad de ajuste.', 1),
			('MA006', 'Análisis de regresión.', 1),
			('MA006', 'Análisis de correlación.', 1),
			('MA006', 'Análisis de varianza.', 1),
			('IF007', 'Organización de la información.', 1),
			('IF007', 'Conceptos generales de Bases de Datos: Sistemas de Bases de Datos.', 1),
			('IF007', 'Teoría de Bases de Datos.', 1),
			('IF007', 'Diseño de bases de datos. Modelado y calidad de datos.', 1),
			('IF007', 'Modelo relacional. Formas normales.', 1),
			('IF007', 'Lenguajes de DBMS.', 1),
			('IF007', 'Transacciones y concurrencia.', 1),
			('IF007', 'Diseño y administración de Sistemas de Bases de Datos. Escalabilidad, eficiencia y efectividad.', 1),
			('IF030', 'Objetos. Clases e instancias.', 1),
			('IF030', 'Herencia. Jerarquías de clases.', 1),
			('IF030', 'Encapsulamiento y abstracción en OO.', 1),
			('IF030', 'Polimorfismo.', 1),
			('IF030', 'Lenguajes OO.', 1),
			('IF030', 'Interfases gráficas. Eventos', 1),
			('IF030', 'Metodologías de diseño orientado a objetos.', 1),
			('IF030', 'Patrones.', 1),
			('IF030', 'Diseño de aplicaciones.', 1),
			('IF031', 'El proceso de software.', 1),
			('IF031', 'Modelos de Ciclos de vida del Software.', 1),
			('IF031', 'Herramientas para el proceso de software.', 1),
			('IF031', 'Ingeniería de Requerimientos.', 1),
			('IF031', 'Conceptos de Análisis y Diseño.', 1),
			('IF031', 'Arquitectura y Diseño.', 1),
			('IF031', 'Desarrollo de Software: implementación, verificación, validación y mantenimiento.', 1),
			('IF031', 'Introducción a los métodos formales.', 1),
			('IF009', 'Desarrollo de aplicaciones concretas en las que se integren conocimientos adquiridos en los dos primeros años.', 1),
			('IF013', 'Lenguajes formales y autómatas. Minimización de autómatas. Expresiones regulares.', 1),
			('IF013', 'Jerarquía de Chomsky. Gramáticas e isomorfismos.', 1),
			('IF013', 'Autómatas finitos. Autómatas a pila. Máquinas de Turing.', 1),
			('IF013', 'Conceptos básicos de teoría de la computabilidad y complejidad: problemas computables y no computables. Problema de la detención. Problemas tratables e intratables. Funciones recursivas.', 1),
			('IF013', 'Análisis de algoritmos: análisis asintótico, comportamiento en el mejor caso, caso promedio y peor caso. Notación 0(). Balance entre tiempo y espacio en los algoritmos. Análisis de complejidad de algoritmos.', 1),
			('IF033', 'Gestión de Proyectos: Planificación y Administración.', 1),
			('IF033', 'Métricas y Estimaciones.', 1),
			('IF033', 'Análisis y Gestión del Riesgo.', 1),
			('IF033', 'Gestión de cambios y configuraciones.', 1),
			('IF033', 'Reingeniería e Ingeniería Inversa.', 1),
			('IF033', 'Conceptos de calidad de software. Calidad del Proceso y Calidad del Producto.', 1),
			('IF033', 'Normas ISO y CMMI. La certificación de normas en PyMEs.', 1),
			('IF033', 'Estándares.', 1),
			('IF033', 'Auditoría y peritaje de sistemas.', 1),
			('IF038', 'Conceptos de concurrencia.', 1),
			('IF038', 'Especificación de la ejecución concurrente.', 1),
			('IF038', 'Comunicación y sincronización.', 1),
			('IF038', 'Concurrencia con variables compartidas.', 1),
			('IF038', 'Concurrencia con pasaje de mensajes.', 1),
			('IF038', 'Sistemas multiprocesador para concurrencia real.', 1),
			('IF038', 'Lenguajes de programación concurrente.', 1),
			('IF038', 'Diseño y programación de algoritmos concurrentes.', 1),
			('IF044', 'Tópicos avanzados de Diseño y Administración de Sistemas de Bases de Datos. Escalabilidad, eficiencia y efectividad.', 1),
			('IF044', 'Gestión de Datos Masivos (Data Warehousing).', 1),
			('IF044', 'Minería de Datos (Data Mining).', 1),
			('IF044', 'Conceptos de Bases de Datos orientadas a objetos.', 1),
			('IF044', 'Conceptos de Bases de Datos Distribuidas.', 1),
			('IF044', 'Control y seguridad de datos.', 1),
			('IF037', 'Objetivos y funciones.', 1),
			('IF037', 'Concepto de proceso. Descripción y control de procesos.', 1),
			('IF037', 'Administración del procesador. Planificación de procesos.', 1),
			('IF037', 'Procesos concurrentes. Concurrencia de ejecución. Interbloqueos.', 1),
			('IF037', 'Administración de memoria. Memoria virtual.', 1),
			('IF037', 'Administración de sistemas de archivos. Protección.', 1),
			('IF037', 'Administración de entrada salida.', 1),
			('IF037', 'Conceptos de sistemas operativos para redes de computadoras, sistemas distribuidos, sistemas de tiempo real, sistemas embebidos, y sistemas multiprocesador.', 1),
			('IF037', 'Introducción al diseño de sistemas operativos.', 1),
			('IF055', 'Desarrollo específico integrando conocimientos teóricos y herramientas conocidas por el alumno.', 1),
			('IF056', 'Conceptos de Estrategias Comunicacionales', 1),
			('IF056', 'Introducción a la Metodología de la Investigación', 1),
			('IF019', 'Introducción a las redes de computadoras.', 1),
			('IF019', 'Técnicas de transmisión de datos.', 1),
			('IF019', 'Estructuras de redes. Topologías. Modelos de referencia (OSI/ISO y TCP/IP).', 1),
			('IF019', 'Nivel físico.', 1),
			('IF019', 'Niveles de enlace de datos y de red. Algoritmos de ruteo. Servicios y protocolos.', 1),
			('IF019', 'Redes locales. Subcapa de acceso al medio. Protocolos.', 1),
			('IF019', 'Nivel de transporte. Protocolos.', 1),
			('IF019', 'Niveles de sesión, presentación y aplicación.', 1),
			('IF019', 'Administración de redes.', 1),
			('IF020', 'Sintaxis y semántica. Nociones básicas de semántica formal. Semántica operacional.', 1),
			('IF020', 'Lenguajes de programación: entidades y ligaduras.', 1),
			('IF020', 'Sistemas de tipos.', 1),
			('IF020', 'Niveles de polimorfismo.', 1),
			('IF020', 'Encapsulamiento y abstracción.', 1),
			('IF020', 'Conceptos de intérpretes y compiladores.', 1),
			('IF020', 'Criterios de diseño y de implementación de lenguajes de programación.', 1),
			('IF020', 'Paradigmas de programación: imperativo, orientado a objetos, funcional, lógico.', 1),
			('IF020', 'Concurrencia y paralelismo.', 1),
			('IF017', 'Estudio, experimentación y aplicación de tecnologías de última generación para el desarrollo de software.', 1),
			('IF022', 'Fundamentos del procesamiento distribuido.', 1),
			('IF022', 'Arquitecturas de procesamiento distribuido.', 1),
			('IF022', 'Procesamiento distribuido y programación concurrente. Control de concurrencia.', 1),
			('IF022', 'Comunicación y sincronización en sistemas distribuidos.', 1),
			('IF022', 'Sistemas cliente servidor y sus variantes.', 1),
			('IF022', 'Sistemas Operativos distribuidos.', 1),
			('IF022', 'Manejo de recursos y planificación en sistemas distribuidos.', 1),
			('IF022', 'Sistemas de archivos distribuidos.', 1),
			('IF022', 'Transacciones distribuidas.', 1),
			('IF022', 'Memoria compartida distribuida.', 1),
			('IF022', 'Conceptos de arquitecturas GRID.', 1),
			('IF022', 'Seguridad en sistemas distribuidos.', 1),
			('IF022', 'Algoritmos sobre arquitecturas distribuidas.', 1),
			('IF035', 'El modelo computacional de Internet y la web.', 1),
			('IF035', 'Diseño de aplicaciones orientadas al E-Citizen', 1),
			('IF035', 'E-Government', 1),
			('IF035', 'E-Learning', 1),
			('IF035', 'E-Health', 1),
			('IF035', 'Diseño centrado en el usuario.', 1),
			('IF035', 'Protocolos de integración.', 1),
			('IF035', 'Conceptos de sistemas colaborativos.', 1),
			('IF035', 'Conceptos de arquitecturas basadas en servicios.', 1),
			('IF035', 'Diseño de Aplicaciones Distribuidas en Tiempo Real.', 1),
			('IF035', 'Aspectos de seguridad y privacidad.', 1),
			('IF035', 'Evaluación y certificación de calidad en sistemas web.', 1),
			('IF057', 'Responsabilidad y ética profesional.', 1),
			('IF057', 'Propiedad intelectual, licenciamiento de software y contratos informáticos.', 1),
			('IF057', 'Aspectos legales.', 1),
			('IF057', 'Firma digital.', 1),
			('IF057', 'Auditoria y Peritaje.', 1),
			('IF057', 'Software libre.', 1),
			('IF057', 'Conceptos de Relaciones Humanas.', 1),
			('IF059', 'Fundamentos de los sistemas inteligentes.', 1),
			('IF059', 'Inteligencia artificial simbólica y no simbólica.', 1),
			('IF059', 'Redes neuronales.', 1),
			('IF059', 'Algoritmos evolutivos.', 1),
			('IF059', 'Algoritmos genéticos.', 1),
			('IF059', 'Ingeniería de conocimiento.', 1),
			('IF059', 'Aplicaciones.', 1),
			('IF060', 'Características de los Sistemas de tiempo real y su software.', 1),
			('IF060', 'Analizar los problemas asociados con la distribución de procesamiento y datos en STR.', 1),
			('IF060', 'Ingeniería de Software de SDTR.', 1),
			('IF060', 'Herramientas de especificación y lenguajes de programación para SDTR.', 1),
			('IF060', 'Aplicaciones a control industrial y robótica.', 1),
			('IF060', 'Sistemas operativos orientados a SDTR.', 1),
			('IF061', 'Algoritmos paralelos', 1),
			('IF061', 'Paradigmas de resolución de sistemas paralelos', 1),
			('IF061', 'Métricas de performance', 1),
			('IF061', 'Arquitecturas de procesamiento paralelo.', 1),
			('IF061', 'Modelos de memoria compartida y/o mensajes', 1),
			('IF061', 'Lenguajes y sistemas operativos para procesamiento paralelo', 1),
			('IF061', 'Optimización de algoritmos paralelos', 1),
			('IF061', 'Aplicaciones', 1),
			('IF062', 'Heterogeneidad y distribución de datos.', 1),
			('IF062', 'Replicación y fragmentación.', 1),
			('IF062', 'Optimización de consultas distribuidas.', 1),
			('IF062', 'Consistencia en datos distribuidos.', 1),
			('IF062', 'Fallas. Protocolos de recuperación de fallas.', 1),
			('IF062', 'Servidores centralizados y distribuidos.', 1),
			('IF063', 'Conceptos básicos de seguridad informática.', 1),
			('IF063', 'Elementos de criptografía.', 1),
			('IF063', 'Seguridad en los datos. Encriptación.', 1),
			('IF063', 'Seguridad en Sistemas Operativos. Autenticación.', 1),
			('IF063', 'Seguridad en Redes.', 1),
			('IF027', 'Modelos. Metodología para la construcción de modelos.', 1),
			('IF027', 'Modelos matemáticos clásicos.', 1),
			('IF027', 'Simulación. Etapas en la construcción de un proyecto para simulación.', 1),
			('IF027', 'Simulación de sistemas estocásticos discretos y continuos.', 1),
			('IF027', 'Software específico y lenguajes para simulación.', 1),
			('IF027', 'Simulación de modelos conocidos. Análisis de sensibilidad.', 1),
			('IF042', 'Trabajo integrador que signifique para el alumno una aplicación concreta de los conocimientos adquiridos hasta el momento (integrando temas de lenguajes, ingeniería de software y bases de datos)', 1);

	INSERT INTO contenido_minimo_plan_estudio (nombre, asignatura_id, plan_estudios_id)
	SELECT c.nombre, a.id, c.plan_estudios_id
	FROM contenidos_minimos_tmp c
		 JOIN asignatura a on (c.plan_estudios_id = a.plan_estudios_id
		 					   and c.codigo = a.codigo);

	INSERT INTO actividad_profesional_reservada (codigo, sigla, descripcion)
    VALUES (1,  'AR1', 'Especificar, proyectar y desarrollar sistemas de información, sistemas de comunicación de datos y software cuya utilización pueda afectar la seguridad, salud, bienes o derechos.'),
            (2, 'AR2', 'Proyectar y dirigir lo referido a seguridad informática.'),
            (3, 'AR3', 'Establecer métricas y normas de calidad de software.'),
            (4, 'AR4', 'Certificar el funcionamiento, condición de uso o estado de lo mencionado anteriormente.'),
            (5, 'AR5', 'Dirigir y controlar la implementación, operación y mantenimiento de lo anteriormente mencionado.');

    INSERT INTO  titulo_x_actividad_profesional_reservada (titulo_id, actividad_profesional_reservada_id)
    VALUES (1, 1),
            (1, 2),
            (1, 3),
            (1, 4),
            (1, 5);

	RAISE NOTICE 'OPERACION FINALIZADA OK';

EXCEPTION
    WHEN OTHERS THEN
        -- Rollback the transaction in case of error
        ROLLBACK;
        -- Optionally log the error
        RAISE NOTICE 'OPERACION FINALIZADA CON ERROR: %', SQLERRM;
        RAISE NOTICE 'SE REALIZA UN ROLLBACK DE LA TRANSACCION';
END $$;