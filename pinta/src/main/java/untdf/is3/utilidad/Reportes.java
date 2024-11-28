package untdf.is3.utilidad;

import io.quarkus.qute.TemplateExtension;
import org.w3c.dom.stylesheets.LinkStyle;
import untdf.is3.transferible.TransferibleContenidoMinimoPlanEstudioConDependencias;

import java.util.List;
import java.util.Objects;

@TemplateExtension
public class Reportes {

    static Double calcularPromedio(List<TransferibleContenidoMinimoPlanEstudioConDependencias> tcmcd) {
        if (tcmcd == null || tcmcd.isEmpty()) {
            return 0.0; // or return 0 if you prefer
        }

        return tcmcd.stream()
                .map(TransferibleContenidoMinimoPlanEstudioConDependencias::getHorasTeoria) // Get horasTeoria
                .filter(Objects::nonNull) // Filter out null values
                .mapToInt(Integer::intValue) // Convert to IntStream
                .average() // Calculate average
                .orElse(0); // Return null if no values present
    }

}
