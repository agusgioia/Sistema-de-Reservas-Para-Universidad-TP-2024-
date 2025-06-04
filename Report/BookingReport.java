package Report;

import DomainClases.Classroom;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * Clase que genera un reporte de aulas ordenadas por cantidad de reservas en forma descendente.
 */
public class BookingReport {

    /**
     * Constructor por defecto de la clase BookingReport.
     */
    public BookingReport() {}

    /**
     * Clase interna para comparar aulas por cantidad de reservas.
     */
    private static class ComparadorDeReservas implements Comparator<Classroom> {
        @Override
        public int compare(Classroom c1, Classroom c2) {
            // Compara las aulas por cantidad de reservas en orden descendente
            return Integer.compare(c2.BookingCount(), c1.BookingCount());
        }
    }

    /**
     * Genera un reporte de aulas ordenadas por cantidad de reservas en forma descendente y lo guarda en un archivo.
     * Calcula también el promedio de reservas por aula.
     *
     * @param filename Nombre del archivo donde se guardará el reporte.
     * @param ClassroomList Lista de aulas para las cuales se generará el reporte.
     * @return Una cadena que contiene el reporte generado.
     * @throws IOException Si ocurre un error de entrada/salida al escribir en el archivo.
     */
    public String GenerateReportByBooking(String filename, TreeSet<Classroom> ClassroomList) throws IOException {
        try {
            FileWriter writer = new FileWriter(filename);
            StringBuilder result = new StringBuilder();
            writer.write("Reporte de aulas por cantidad de reservas en forma descendente:\n");
            writer.write("-----------------------------------\n");
            result.append("Reporte aulas ordenadas en forma descendente por número de reservas:\n");
            result.append("-----------------------------------\n");

            double TotalReservations = 0;
            ArrayList<Classroom> Classroomreport = new ArrayList<>();

            // Calcula el total de reservas y prepara la lista de aulas para el reporte
            for (Classroom classroom : ClassroomList) {
                TotalReservations += classroom.BookingCount();
                Classroomreport.add(classroom);
            }

            // Ordena las aulas por cantidad de reservas usando el comparador personalizado
            Classroomreport.sort(new ComparadorDeReservas());

            // Escribe el listado ordenado en el archivo y en la cadena de resultado
            writer.write("Listado de aulas en orden descendiente: \n");
            for (Classroom classroom : Classroomreport) {
                result.append(classroom).append("\n");
                writer.write(classroom + "\n");
            }

            // Calcula y escribe el promedio de reservas por aula
            double averageReservations = TotalReservations / Classroomreport.size();
            writer.write("La cantidad de reservas promedio por aula fue = " + averageReservations);
            result.append("La cantidad de reservas promedio por aula fue = ").append(averageReservations);

            writer.close();
            return result.toString();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
