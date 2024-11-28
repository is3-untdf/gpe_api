package untdf.is3.acceso;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import untdf.is3.modelo.ActividadProfesionalReservada;

import java.util.List;
import java.util.Optional;

@RequestScoped 
public class AccesoActividadProfesionalReservada implements PanacheRepositoryBase<ActividadProfesionalReservada, Integer> {

    @Inject 
    Logger auditoria; 

    public List<ActividadProfesionalReservada> obtenerTodas(){

        auditoria.debug("Intentando obtener todas las Actividad Profesionales Reservadas desde la DB.");
        return findAll(Sort.ascending("actividadProfesionalReservadaId")).list();

    }

    public Optional<ActividadProfesionalReservada> obtener(int id) {

        auditoria.debugv("Intentando obtener Actividad Profesional Reservada {0} desde la DB.", id);
        return findByIdOptional(id);

    }

    public Optional<ActividadProfesionalReservada> persistir(ActividadProfesionalReservada actividadProfesionalReservada) {

        auditoria.debug("Intentando crear nueva Actividad Profesional Reservada en la DB.");
        persist(actividadProfesionalReservada);
        return findByIdOptional(actividadProfesionalReservada.getActividadProfesionalReservadaId());

    }

    public boolean eliminar(ActividadProfesionalReservada actividadProfesionalReservada) {

        auditoria.warnf("Intentando eliminar Actividad Profesional Reservada {0} en la DB.", actividadProfesionalReservada.getActividadProfesionalReservadaId());
        return deleteById(actividadProfesionalReservada.getActividadProfesionalReservadaId());

    }

}
