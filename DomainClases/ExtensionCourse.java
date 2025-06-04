package DomainClases;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Representa un curso de extensión organizado por una organización.
 */
public class ExtensionCourse extends Organization {
    private String CourseDescription;
    private int InscriptedStudents;
    private int AmountOfClasses;
    private double CostPerStudent;

    /**
     * Constructor para crear un curso de extensión con detalles específicos.
     *
     * @param code              El código del curso.
     * @param courseDescription La descripción del curso.
     * @param inscriptedStudents El número de estudiantes inscritos.
     * @param amountOfClasses   La cantidad de clases del curso.
     * @param costPerStudent    El costo por estudiante.
     */
    public ExtensionCourse(String code, String courseDescription, int inscriptedStudents, int amountOfClasses, double costPerStudent) {
        super(code);
        CourseDescription = courseDescription;
        InscriptedStudents = inscriptedStudents;
        AmountOfClasses = amountOfClasses;
        CostPerStudent = costPerStudent;
    }

    /**
     * Obtiene la descripción del curso.
     *
     * @return La descripción del curso.
     */
    public String getCourseDescription() {
        return CourseDescription;
    }

    /**
     * Establece la descripción del curso.
     *
     * @param courseDescription La nueva descripción del curso.
     */
    public void setCourseDescription(String courseDescription) {
        CourseDescription = courseDescription;
    }

    /**
     * Obtiene el número de estudiantes inscritos en el curso.
     *
     * @return El número de estudiantes inscritos.
     */
    public int getInscriptedStudents() {
        return InscriptedStudents;
    }

    /**
     * Establece el número de estudiantes inscritos en el curso.
     *
     * @param inscriptedStudents El nuevo número de estudiantes inscritos.
     */
    public void setInscriptedStudents(int inscriptedStudents) {
        InscriptedStudents = inscriptedStudents;
    }

    /**
     * Obtiene la cantidad de clases del curso.
     *
     * @return La cantidad de clases.
     */
    public int getAmountOfClasses() {
        return AmountOfClasses;
    }

    /**
     * Establece la cantidad de clases del curso.
     *
     * @param amountOfClasses La nueva cantidad de clases.
     */
    public void setAmountOfClasses(int amountOfClasses) {
        AmountOfClasses = amountOfClasses;
    }

    /**
     * Obtiene el costo por estudiante.
     *
     * @return El costo por estudiante.
     */
    public double getCostPerStudent() {
        return CostPerStudent;
    }

    /**
     * Establece el costo por estudiante.
     *
     * @param costPerStudent El nuevo costo por estudiante.
     */
    public void setCostPerStudent(double costPerStudent) {
        CostPerStudent = costPerStudent;
    }

    /**
     * Obtiene el identificador único del curso.
     *
     * @return La descripción del curso.
     */
    @Override
    public String getIdentificadorUnico() {
        return CourseDescription;
    }

    /**
     * Realiza una reserva para el curso de extensión.
     *
     * @param Param          Los parámetros necesarios para la reserva.
     * @param ClassroomList  La lista de aulas disponibles.
     * @throws NumberFormatException     Si el formato del número es incorrecto.
     * @throws DateTimeParseException    Si el formato de la fecha es incorrecto.
     * @throws IllegalArgumentException  Si no se proporcionan suficientes parámetros.
     * @throws RuntimeException          Si ocurre un error durante la reserva.
     */
    @Override
    public void Reservation(ArrayList<String> Param, TreeSet<Classroom> ClassroomList) throws NumberFormatException, DateTimeParseException {
        if (Param.size() < 6)
            throw new IllegalArgumentException("Se requieren más parámetros");

        StringBuilder Fechas = new StringBuilder();
        String Code = getCode();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");
            LocalDateTime FechaInicio = LocalDateTime.parse(Param.get(0), formatter);
            LocalDateTime FechaFin = LocalDateTime.parse(Param.get(1), formatter);

            if (FechaInicio.isBefore(LocalDateTime.now()) || FechaInicio.isEqual(LocalDateTime.now()))
                throw new RuntimeException("Fecha de inicio menor o igual a la actual");
            else if (FechaFin.isBefore(FechaInicio) || FechaFin.isEqual(FechaInicio))
                throw new RuntimeException("Fecha de fin menor o igual a la de inicio");
            LocalDateTime FechaInicialCurso = FechaInicio;
            String Descripcion = Param.get(2);
            int EstudiantesIns = Integer.parseInt(Param.get(3));
            int CantClases = Integer.parseInt(Param.get(4));
            double CostoPorEst = Double.parseDouble(Param.get(5));
            LocalDateTime FechaFinalCurso = FechaFin.plusWeeks(CantClases - 1);
            boolean Bandera = true;
            while (!FechaFin.isAfter(FechaFinalCurso)&& Bandera) {
                boolean Disponible=false;
                for (Classroom classroom : ClassroomList) {
                    if (classroom.getMaximumCapacity() >= EstudiantesIns) {
                        boolean AulaDisponible = true;
                        for (Booking booking : classroom.getBookingList()) {
                            boolean DentroRango = (FechaInicio.isBefore(booking.getEndOfClass()) && FechaFin.isAfter(booking.getStartOfClass()));
                            if (DentroRango) {
                                AulaDisponible = false;
                                break;
                            }
                        }
                        if (AulaDisponible) {
                            LocalDateTime FecAct = LocalDateTime.now();
                            Booking booking = new Booking(classroom.BookingCount(), FecAct, FechaInicio, FechaFin, new ExtensionCourse(Code, Descripcion, EstudiantesIns, CantClases, CostoPorEst));
                            classroom.getBookingList().add(booking);
                            Disponible=true;
                            break;
                        }
                    }
                }
                if (!Disponible){
                    Fechas.append("No se pudo realizar la reserva en la fecha = ").append(FechaInicio).append("Reservas posteriores sin hacer").append("\n");
                    Bandera=false;
                }
                FechaInicio = FechaInicio.plusWeeks(1);
                FechaFin = FechaFin.plusWeeks(1);
            }

            if (Bandera)
                JOptionPane.showMessageDialog(null, "Se realizaron todas las reservas con éxito");
            else
                if (FechaInicio.isEqual(FechaInicialCurso.plusWeeks(1)))
                    JOptionPane.showMessageDialog(null,"No se realizó ninguna reserva","Error",JOptionPane.ERROR_MESSAGE);
                else
                    JOptionPane.showMessageDialog(null, Fechas.toString(), "No se realizaron todas las reservas", JOptionPane.INFORMATION_MESSAGE);


        } catch (NumberFormatException | DateTimeParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Calcula la cantidad total recaudada por el curso.
     *
     * @return La cantidad total recaudada.
     */
    @Override
    public double AmountCollected() {
        return CostPerStudent * InscriptedStudents;
    }

    /**
     * Obtiene el tipo del curso.
     *
     * @return El tipo del curso.
     */
    @Override
    public String getTipo() {
        return "Curso";
    }

    /**
     * Devuelve una representación en cadena del curso de extensión.
     *
     * @return Una cadena que representa el curso de extensión.
     */
    @Override
    public String toString() {
        return super.toString()+"ExtensionCourse{" +
                "CourseDescription='" + CourseDescription + '\'' +
                ", InscriptedStudents=" + InscriptedStudents +
                ", AmountOfClasses=" + AmountOfClasses +
                ", CostPerStudent=" + CostPerStudent +
                '}';
    }

    /**
     * Obtiene el panel de reserva asociado al curso.
     *
     * @return El número del panel de reserva.
     */
    @Override
    public int PanelReserva() {
        return 2;
    }
}
