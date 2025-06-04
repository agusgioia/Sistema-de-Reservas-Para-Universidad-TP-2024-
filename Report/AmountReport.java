package Report;

import DomainClases.Classroom;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.TreeSet;

/**
 * Clase que genera diferentes reportes basados en el monto recaudado por aulas y pisos.
 */
public class AmountReport {

    private final DecimalFormat decimalFormat = new DecimalFormat("#.###");

    /**
     * Constructor por defecto de la clase AmountReport.
     */
    public AmountReport() {}

    /**
     * Genera un reporte del monto recaudado por cada aula y lo guarda en un archivo.
     * @param filename Nombre del archivo donde se guardará el reporte.
     * @param ClassroomList Lista de aulas para las cuales se generará el reporte.
     * @return Una cadena que contiene el reporte generado.
     * @throws IOException Si ocurre un error de entrada/salida al escribir en el archivo.
     */
    public String GenerateReportByAmountCollectedByClassroom(String filename, TreeSet<Classroom> ClassroomList) throws IOException {
        try {
            StringBuilder result = new StringBuilder();
            FileWriter writer = new FileWriter(filename);
            writer.write("Reporte de monto recaudado por aula:\n");
            writer.write("-----------------------------------\n");
            result.append("Reporte de monto recaudado por aula:\n");
            result.append("-----------------------------------\n");
            double Amount;
            for (Classroom classroom : ClassroomList) {
                Amount = classroom.AmountCollected();
                result.append(classroom.getIdNumber()).append("Aula - Monto recaudado = ").append(decimalFormat.format(Amount)).append("\n");
                writer.write(classroom.getIdNumber() + "Aula - Monto recaudado = " + decimalFormat.format(Amount) + "\n");
            }
            writer.close();
            return result.toString();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    /**
     * Genera un reporte del monto recaudado por cada piso y lo guarda en un archivo.
     * @param filename Nombre del archivo donde se guardará el reporte.
     * @param ClassroomList Lista de aulas para las cuales se generará el reporte.
     * @return Una cadena que contiene el reporte generado.
     * @throws IOException Si ocurre un error de entrada/salida al escribir en el archivo.
     */
    public String GenerateReportByAmountCollectedByFloor(String filename, TreeSet<Classroom> ClassroomList) throws IOException {
        try {
            FileWriter writer = new FileWriter(filename);
            StringBuilder result = new StringBuilder();
            writer.write("Reporte de monto recaudado por piso:\n");
            writer.write("-----------------------------------\n");
            result.append("Reporte de monto recaudado por piso:\n");
            result.append("-----------------------------------\n");
            double Amount = 0;
            int ActualFloor, ChangeFloor = 0;
            for (Classroom classroom : ClassroomList) {
                ActualFloor = classroom.FloorNumber();
                if (ActualFloor == ChangeFloor)
                    Amount += classroom.AmountCollected();
                else {
                    writer.write(ChangeFloor + " piso - Monto Recaudado = " + decimalFormat.format(Amount) + "\n");
                    result.append(ChangeFloor).append(" piso - Monto Recaudado = ").append(decimalFormat.format(Amount)).append("\n");
                    ChangeFloor = ActualFloor;
                    Amount = classroom.AmountCollected();
                }
            }
            writer.write(ChangeFloor + " piso - Monto Recaudado = " + decimalFormat.format(Amount) + "\n");
            result.append(ChangeFloor).append(" piso - Monto Recaudado = ").append(decimalFormat.format(Amount)).append("\n");
            writer.close();
            return result.toString();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    /**
     * Genera un reporte del monto total recaudado por todas las aulas y lo guarda en un archivo.
     * @param filename Nombre del archivo donde se guardará el reporte.
     * @param ClassroomList Lista de aulas para las cuales se generará el reporte.
     * @return Una cadena que contiene el reporte generado.
     * @throws IOException Si ocurre un error de entrada/salida al escribir en el archivo.
     */
    public String GenerateReportByAmountCollected(String filename, TreeSet<Classroom> ClassroomList) throws IOException {
        try {
            StringBuilder result = new StringBuilder();
            FileWriter writer = new FileWriter(filename);
            writer.write("Reporte de monto recaudado por la Universidad:\n");
            writer.write("-----------------------------------\n");
            double Amount = 0;
            result.append("Reporte de monto recaudado por la Universidad:\n");
            result.append("-----------------------------------\n");
            for (Classroom classroom : ClassroomList) {
                Amount += classroom.AmountCollected();
            }
            result.append("Monto Recaudado por la institución = ").append(decimalFormat.format(Amount)).append("\n");
            writer.write("Monto Recaudado por la institución = " + decimalFormat.format(Amount) + "\n");
            writer.close();
            return result.toString();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }
}
