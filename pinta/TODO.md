### TO DO
- #1. Por cada entidad:
  - (M) Modelo (a partir de la DB)
  - (T) Transferible
  - (T) TransferibleCreación
  - (T) Transformador
  - (A) Acceso
  - (S) Servicio
  - (R) Recurso
- #2. Build de la API
  - #2.1. Configurar application-prod.properties
  - #2.2. Configurar compose.yaml
- #3. Refactor de la API (Validar campos)
- #4. Agregar método PUT a todas las entidades
- #5. Agregar restricción Unique (column1, column2) a tablas intermedias de DB
- #6. Corregir Scripts DB (unificarlos)
- #7. Configurar compose.yaml DB
- #8. refactor: cambiar path de Titulo a plural ('titulos')
- #9. Permitir que una materia pueda ser cargada con una carga horario mayor o igual a cero. (En la API)
- #10. Las recomendaciones curriculares deberán poder soportar exigencias nulas. (En la API y en la DB)
- #11. Permitir que una asignatura acepte nulos en las horas de teoria, en las horas practica, en las exigencias y en la intensidad.
- #12. Agregar relación de Plan de Estudio (un nuevo atributo) a Contenidos Minimos Plan Estudio y a Asignatura. En la DB y en la API.
- #13. Generar Recurso, Transferible y mapeo de Entidades con Dependencias (el recurso devuelve la entidad en lugar del identificador).
- #14. Probar y bosquejar los primeros reportes y endpoints de reportes html
- #15. Generar endpoint de clonación de planes de estudio
- #16. Agregar queryParams a algunos endpoints para filtros y orden.
- #17. Modificar el/los scripts para que el de creacion invoque al de insercion masiva
- #18. Modificar el compose.yaml para que ejecute el primer script y se cree y popule la DB
- #19. Modificar el script de creación de la DB, agregando el procedimiento almacenado de clonación
- #20. Modificar TransferiblesConDependencias de Asignatura y ContenidosMinimos para que incluya todas las dependencias
- #21. Exportar archivo CSV desde la DB
- #22. Descargar CSV desde la API

### NEXT
- #16. Agregar queryParams a algunos endpoints para filtros y orden.

### DOING
- #14. Probar y bosquejar los primeros reportes y endpoints de reportes html
- 
### DONE
- #1, #3, #4. Asignatura
- #1, #3, #4. PlanEstudio
- #2. Crear Enum de Exigencia
- #1, #3, #4. Intensidad
- #1, #3, #4. ContenidoMinimoPlanEstudio
- #1, #3, #4. Titulo
- #1, #3, #4. ActividadProfesionalReservada
- #1, #3. TituloXActividadProfesionalReservada
- #1, #3, #4. TrayectoFormativo
- #1, #3, #4. ContenidoCurricularBasico
- #1, #3, #4. AreaRedUnci
- #1, #3, #4. PesoRelativo
- #1, #3, #4. RecomendacionCurricular
- #1, #3. RecomendacionCurricularXContenidoMinimoPlanEstudio (TABLA INTERMEDIA SIMPLE)
- #1, #3. ActividadProfesionalReservadaXRecomendacionCurricular
- #5. Agregar restricción Unique (column1, column2) a tablas intermedias de DB
- #6. Corregir Scripts DB (unificarlos)
- #7. Configurar compose.yaml DB 
- #2. Build de la API
  - #2.1. Configurar application-prod.properties
  - #2.2. Configurar compose.yaml
- #9. Permitir que una materia pueda ser cargada con una carga horario mayor o igual a cero.
- #10. Las recomendaciones curriculares deberán poder soportar exigencias nulas. (En la API y en la DB)
- #11. Permitir que una asignatura acepte nulos en las horas de teoria, en las horas practica, en las exigencias y en la intensidad.
- #12. Agregar relación de Plan de Estudio (un nuevo atributo) a Contenidos Minimos Plan Estudio y a Asignatura. En la DB y en la API.
- #13. Generar Recurso, Transferible y mapeo de Entidades con Dependencias (el recurso devuelve la entidad en lugar del identificador).
- #14. Probar y bosquejar los primeros reportes y endpoints de reportes html
- #15. Generar endpoint de clonación de planes de estudio
  - #15.1. Eliminar restriccion UNIQUE de 'nombre' del contenidoMinimoPlanEstudio. API y DB
  - #15.2. Eliminar restriccion UNIQUE de 'nombre' de asignatura. API y DB
  - #15.3. Eliminar restriccion UNIQUE de 'codigo' de asignatura. API y DB
  - #15.4. Refactor del servicio de ContenidoMinimo para verificar que la Asignatura tenga el mismo Plan Estudio 
- #19. Modificar el script de creación de la DB, agregando el procedimiento almacenado de clonación
- #20. Modificar TransferiblesConDependencias de Asignatura y ContenidosMinimos para que incluya todas las dependencias
- #21. Exportar archivo CSV desde la DB
- #22. Descargar CSV desde la API

### Commits
- Commit b7c7d28:
  - feat: #1. Asignatura
  - feat: #1. PlanEstudio
  - feat: #2. Crear Enum de Exigencia
  - feat: #1. ContenidosMinimosPlanEstudio
  - feat: #1. Titulo
  - feat: #1. ActividadProfesionalReservada
  - feat: #1. TituloXActividadProfesionalReservada
- Commit 885c057:
  - feat: #1. TrayectosFormativos
  - feat: #1. ContenidosCurricularesBasicos
  - feat: #1. AreaRedUnci
- Commit 9a51e9d:
  - feat: #1, #3. PesoRelativo (Campos validados)
  - feat: #1, #3. RecomendacionCurricular
- Commit 48fc212:
  - feat: #1, #3. (TABLA INTERMEDIA SIMPLE) RecomendacionCurricularXContenidoMinimoPlanEstudio
  - feat: #1, #3. ActividadProfesionalReservadaXRecomendacionCurricular
- Commit 643a9e2:
  - refactor: #3. ActividadProfesionalReservada
  - refactor: #3. Asignatura
  - refactor: #3. ContenidoCurricularBasico
  - refactor: #3. ContenidoMinimoPlanEstudio
  - feat: Agregado de Anotación 'ValidarExigencia'
  - refactor: #3. Intensidad
  - refactor: #3. PlanEstudio
  - refactor: #3. TrayectoFormativo
  - refactor: #3. Titulo
  - refactor: #3. AreaRedUnci
  - refactor: #3. TransferibleCrear Tablas Intermedias
- Commit 817228b:
  - feat: #4. Asignatura
  - feat: #4. PlanEstudio
  - feat: #4. Intensidad
  - feat: #4. ContenidoMinimoPlanEstudio
  - feat: #4. Titulo
  - feat: #4. ActividadProfesionalReservada
  - feat: #4. TrayectoFormativo
  - feat: #4. PesoRelativo
  - feat: #4. AreaRedUnci
  - feat: #4. ContenidoCurricularBasico
  - feat: #4. RecomendacionCurricular
- Commit e80dedc:
  - refactor: #5. Agregar restricción Unique (column1, column2) a tablas intermedias de DB
  - refactor: #6. Corregir Scripts DB (unificarlos)
  - feat: #7. Configurar compose.yaml DB
- Commit 749494a:
  - build: Build de la API --> csparadiso/pinta:0.1.0
- Commit 0ca0cec:
  - refactor: #9. Permitir que una materia pueda ser cargada con una carga horario mayor o igual a cero.
  - refactor: #10. Las recomendaciones curriculares deberán poder soportar exigencias nulas. (En la API y en la DB)
- Commit 75b21c3:
  - Añadido el script de insercion de DB y el readme
- Commit 7e00142:
  - refactor: #11. Permitir que una asignatura acepte nulos en las horas de teoria, en las horas practica, en las exigencias y en la intensidad.
  - feat: #12. Agregar relación de Plan de Estudio (un nuevo atributo) a Contenidos Minimos Plan Estudio y a Asignatura. En la DB y en la API.
  - feat: #13. Generar Recurso, Transferible y mapeo de Entidades con Dependencias (el recurso devuelve la entidad en lugar del identificador).
  - feat: #14. Probar y bosquejar los primeros reportes y endpoints de reportes html
- Commit bddcdef: -> POM, DOCKER PUSH (0.1.3)
  - feat: #15. Generar endpoint de clonación de planes de estudio. #15.1. Eliminar restriccion UNIQUE de 'nombre' del contenidoMinimoPlanEstudio. API y DB. #15.2. Eliminar restriccion UNIQUE de 'nombre' de asignatura. API y DB. #15.3. Eliminar restriccion UNIQUE de 'codigo' de asignatura. API y DB. #15.4. Refactor del servicio de ContenidoMinimo para verificar que la Asignatura tenga el mismo Plan Estudio. refactor: #19. Modificar el script de creación de la DB, agregando el procedimiento almacenado de clonación. refactor: #20. Modificar TransferiblesConDependencias de Asignatura y ContenidosMinimos para que incluya todas las dependencias
- Commit ebd0ed6: -> POM, DOCKER PUSH (0.1.5)
  - feat: #21. Exportar archivo CSV desde la DB
  - feat: #22. Descargar CSV desde la API
- Commit 6e39dd3: --> POM, DOCKER PUSH (0.1.7)
  - feat: Descargar csv general de planes de Estudios (agregado como funcion a la db)
  - feat: agregar a tabla intermedia RecomendacionCurricularXConetenidMinimoPlanEstudio los atributos de la relacion
  - feat: añadido el archivo de configuracion de nginx
  
##### ADITIONAL DATA
## ENTITIES
Asignatura
PlanEstudio
Intensidad
ContenidoMinimoPlanEstudio
Titulo
ActividadProfesionalReservada
TrayectoFormativo
ContenidoCurricularBasico
AreaRedUnci
PesoRelativo (Campos validados)
RecomendacionCurricular

