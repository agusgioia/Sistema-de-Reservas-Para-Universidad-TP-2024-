package DomainClases;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Clase que representa una asignatura, que es un tipo de organización.
 * Implementa la interfaz Comparable para permitir la comparación de asignaturas por nombre.
 */
public class Subject extends Organization implements Comparable<Subject> {

    private String SubjectName;
    private LocalDateTime StartOfSubject;
    private LocalDateTime EndOfSubject;
    private LocalDateTime StartOfClass;
    private LocalDateTime EndOfClass;
    private byte DayOfTheWeek;
    private int StudentsAmount;

    /**
     * Constructor para crear una asignatura con todos los detalles necesarios.
     *
     * @param code            El código de la asignatura.
     * @param subjectName     El nombre de la asignatura.
     * @param startOfSubject  La fecha y hora de inicio del curso.
     * @param endOfSubject    La fecha y hora de finalización del curso.
     * @param startOfClass    La fecha y hora de inicio de la clase.
     * @param endOfClass      La fecha y hora de finalización de la clase.
     * @param dayOfTheWeek    El día de la semana en que se imparte la clase.
     * @param studentsAmount  La cantidad de estudiantes inscritos.
     */
    public Subject(String code, String subjectName, LocalDateTime startOfSubject, LocalDateTime endOfSubject,
                   LocalDateTime startOfClass, LocalDateTime endOfClass, byte dayOfTheWeek, int studentsAmount) {
        super(code);
        SubjectName = subjectName;
        StartOfSubject = startOfSubject;
        EndOfSubject = endOfSubject;
        StartOfClass = startOfClass;
        EndOfClass = endOfClass;
        DayOfTheWeek = dayOfTheWeek;
        StudentsAmount = studentsAmount;
    }

    /**
     * Obtiene el nombre de la asignatura.
     *
     * @return El nombre de la asignatura.
     */
    public String getSubjectName() {
        return SubjectName;
    }

    /**
     * Realiza una reserva de aula para la asignatura.
     *
     * @param Param         Los parámetros necesarios para la reserva.
     * @param ClassroomList La lista de aulas disponibles.
     * @throws NumberFormatException   Si se produce un error al analizar un número.
     * @throws DateTimeParseException  Si se produce un error al analizar una fecha.
     */
    @Override
    public void Reservation(ArrayList<String> Param, TreeSet<Classroom> ClassroomList) throws NumberFormatException, DateTimeParseException {
        if (Param.size() < 6)
            throw new IllegalArgumentException("Se requieren más parámetros");
        StringBuilder Fechas = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");
        String Code = getCode();
        try {
            LocalDateTime ComienzoClase = LocalDateTime.parse(Param.get(0), formatter);
            LocalDateTime FinClase = LocalDateTime.parse(Param.get(1), formatter);
            if (ComienzoClase.isBefore(LocalDateTime.now()) || ComienzoClase.isEqual(LocalDateTime.now()))
                throw new RuntimeException("Fecha de inicio menor o igual a la actual");
            else if (FinClase.isBefore(ComienzoClase) || FinClase.isEqual(ComienzoClase))
                throw new RuntimeException("Fecha de fin menor o igual a la inicial");
            String NombreAsignatura = Param.get(2);
            LocalDateTime ComienzoCursada = LocalDateTime.parse(Param.get(3), formatter);
            LocalDateTime FinCursada = LocalDateTime.parse(Param.get(4), formatter);
            int CantidadEstudiantes = Integer.parseInt(Param.get(5));
            boolean Bandera = true;
            while (!FinClase.isAfter(FinCursada) && Bandera){
                boolean Disponible = false;
                for (Classroom classroom : ClassroomList) {
                    if (classroom.getMaximumCapacity() >= CantidadEstudiantes) {
                        boolean AulaDisponible = true;
                        for (Booking booking : classroom.getBookingList()) {
                            boolean DentroRango = (ComienzoClase.isBefore(booking.getEndOfClass()) && FinClase.isAfter(booking.getStartOfClass()));
                            if (DentroRango) {
                                AulaDisponible = false;
                                break;
                            }
                        }
                        if (AulaDisponible) {
                            LocalDateTime HoraAct = LocalDateTime.now();
                            byte Dia = (byte) ComienzoClase.getDayOfMonth();
                            Booking booking = new Booking(classroom.BookingCount(), HoraAct, ComienzoClase, FinClase, new Subject(Code, NombreAsignatura, ComienzoCursada, FinCursada, ComienzoClase, FinClase, Dia, CantidadEstudiantes));
                            classroom.getBookingList().add(booking);
                            Disponible=true;
                            break;
                        }
                    }
                }
                if (!Disponible){
                    Fechas.append("No se pudo realizar la reserva en la fecha = ").append(ComienzoClase).append("\n");
                    Bandera=false;
                }
                ComienzoClase = ComienzoClase.plusWeeks(1);
                FinClase = FinClase.plusWeeks(1);
            }
            if (Bandera)
                JOptionPane.showMessageDialog(null, "Se realizaron todas las reservas con éxito");
            else
                if (ComienzoClase.isEqual(ComienzoCursada.plusWeeks(1)))
                    JOptionPane.showMessageDialog(null,"No se realizó ninguna reserva","Error",JOptionPane.ERROR_MESSAGE);
                else
                    JOptionPane.showMessageDialog(null, Fechas.toString(), "No se realizaron todas las reservas", JOptionPane.INFORMATION_MESSAGE);
        } catch (DateTimeParseException | NumberFormatException dateTimeParseException) {
            throw new RuntimeException(dateTimeParseException);
        }
    }

    /**
     * Obtiene el tipo de organización.
     *
     * @return El tipo de organización (Asignatura).
     */
    @Override
    public String getTipo() {
        return "Asignatura";
    }

    /**
     * Obtiene el identificador único de la asignatura.
     *
     * @return El nombre de la asignatura.
     */
    @Override
    public String getIdentificadorUnico() {
        return SubjectName;
    }

    /**
     * Compara esta asignatura con otra asignatura por nombre.
     *
     * @param S La otra asignatura a comparar.
     * @return Un valor negativo, cero o positivo si el nombre de esta asignatura es menor, igual o mayor que el de la otra asignatura.
     */
    @Override
    public int compareTo(Subject S) {
        return SubjectName.compareTo(S.getSubjectName());
    }

    /**
     * Devuelve una representación en cadena de la asignatura.
     *
     * @return Una cadena que representa la asignatura.
     */
    @Override
    public String toString() {
        return super.toString()+"Subject{" +
                "SubjectName='" + SubjectName + '\'' +
                ", StartOfSubject=" + StartOfSubject +
                ", EndOfSubject=" + EndOfSubject +
                ", StartOfClass=" + StartOfClass +
                ", EndOfClass=" + EndOfClass +
                ", DayOfTheWeek=" + DayOfTheWeek +
                ", StudentsAmount=" + StudentsAmount +
                '}';
    }

    /**
     * Obtiene el número del panel de reserva asociado a la asignatura.
     *
     * @return El número del panel de reserva.
     */
    @Override
    public int PanelReserva() {
        return 1;
    }
}
