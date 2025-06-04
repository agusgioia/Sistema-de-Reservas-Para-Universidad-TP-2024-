package DomainClases;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Representa un evento externo organizado por una organización.
 */
public class ExternEvent extends Event {
    private double RentCost;
    private String OrganizationName;

    /**
     * Constructor para crear un evento externo con detalles específicos.
     *
     * @param code              El código del evento.
     * @param eventDescription  La descripción del evento.
     * @param maxParticipants   El número máximo de participantes.
     * @param rentCost          El costo de alquiler del evento.
     * @param organizationName  El nombre de la organización.
     */
    public ExternEvent(String code, String eventDescription, int maxParticipants, double rentCost, String organizationName) {
        super(code, eventDescription, maxParticipants);
        RentCost = rentCost;
        OrganizationName = organizationName;
    }

    /**
     * Obtiene el costo de alquiler del evento.
     *
     * @return El costo de alquiler.
     */
    public double getRentCost() {
        return RentCost;
    }

    /**
     * Establece el costo de alquiler del evento.
     *
     * @param rentCost El nuevo costo de alquiler.
     */
    public void setRentCost(double rentCost) {
        RentCost = rentCost;
    }

    /**
     * Obtiene el nombre de la organización que organiza el evento.
     *
     * @return El nombre de la organización.
     */
    public String getOrganizationName() {
        return OrganizationName;
    }

    /**
     * Establece el nombre de la organización que organiza el evento.
     *
     * @param organizationName El nuevo nombre de la organización.
     */
    public void setOrganizationName(String organizationName) {
        OrganizationName = organizationName;
    }

    /**
     * Devuelve una representación en cadena del evento externo.
     *
     * @return Una cadena que representa el evento externo.
     */
    @Override
    public String toString() {
        return super.toString()+"ExternEvent{" +
                "RentCost=" + RentCost +
                ", OrganizationName='" + OrganizationName + '\'' +
                '}';
    }

    /**
     * Calcula la cantidad total recaudada por el evento.
     *
     * @return La cantidad total recaudada.
     */
    @Override
    public double AmountCollected() {
        return RentCost;
    }

    /**
     * Obtiene el tipo del evento.
     *
     * @return El tipo del evento.
     */
    @Override
    public String getTipo() {
        return "EventoExterno";
    }

    /**
     * Realiza una reserva para el evento externo.
     *
     * @param Param         Los parámetros necesarios para la reserva.
     * @param ClassroomList La lista de aulas disponibles.
     * @throws NumberFormatException    Si el formato del número es incorrecto.
     * @throws DateTimeParseException   Si el formato de la fecha es incorrecto.
     * @throws IllegalArgumentException Si no se proporcionan suficientes parámetros.
     * @throws RuntimeException         Si ocurre un error durante la reserva.
     */
    @Override
    public void Reservation(ArrayList<String> Param, TreeSet<Classroom> ClassroomList) throws NumberFormatException, DateTimeParseException {
        if (Param.size() < 6)
            throw new IllegalArgumentException("Se requieren más parámetros");

        String Code = getCode();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");
            LocalDateTime FechaInicio = LocalDateTime.parse(Param.get(0), formatter);
            LocalDateTime FechaFin = LocalDateTime.parse(Param.get(1), formatter);

            if (FechaInicio.isBefore(LocalDateTime.now()) || FechaInicio.isEqual(LocalDateTime.now()))
                throw new RuntimeException("Fecha de inicio menor o igual a la actual");
            else if (FechaFin.isBefore(FechaInicio) || FechaFin.isEqual(FechaInicio))
                throw new RuntimeException("Fecha final menor o igual a la actual");

            int CantMax = Integer.parseInt(Param.get(2));
            String Descripcion = Param.get(3);
            String NomOrg = Param.get(4);
            double Costo = Double.parseDouble(Param.get(5));
            boolean Disponible = false;

            for (Classroom classroom : ClassroomList) {
                if (classroom.getMaximumCapacity() >= CantMax) {
                    Disponible = true;
                    for (Booking booking : classroom.getBookingList()) {
                        boolean Rango = (FechaInicio.isBefore(booking.getEndOfClass()) && FechaFin.isAfter(booking.getStartOfClass()));
                        if (Rango) {
                            Disponible = false;
                            break;
                        }
                    }
                }
                if (Disponible) {
                    LocalDateTime FecAct = LocalDateTime.now();
                    Booking booking = new Booking(classroom.BookingCount(), FecAct, FechaInicio, FechaFin, new ExternEvent(Code, Descripcion, CantMax, Costo, NomOrg));
                    classroom.getBookingList().add(booking);
                    break;
                }
            }

            if (!Disponible)
                JOptionPane.showMessageDialog(null, "No hay aulas con la capacidad requerida o la fecha está ocupada", "La reserva no pudo realizarse", JOptionPane.WARNING_MESSAGE);
            else
                JOptionPane.showMessageDialog(null, "La reserva se realizó con éxito", "Reserva realizada", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException | DateTimeParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Obtiene el panel de reserva asociado al evento.
     *
     * @return El número del panel de reserva.
     */
    @Override
    public int PanelReserva() {
        return 4;
    }
}
