package DomainClases;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Representa un evento organizado por una organización.
 */
public class Event extends Organization {

    private String EventDescription;
    private int MaxParticipants;

    /**
     * Constructor para crear un evento con detalles específicos.
     *
     * @param code            El código del evento.
     * @param eventDescription La descripción del evento.
     * @param maxParticipants  El número máximo de participantes.
     */
    public Event(String code, String eventDescription, int maxParticipants) {
        super(code);
        EventDescription = eventDescription;
        MaxParticipants = maxParticipants;
    }

    /**
     * Obtiene la descripción del evento.
     *
     * @return La descripción del evento.
     */
    public String getEventDescription() {
        return EventDescription;
    }

    /**
     * Establece la descripción del evento.
     *
     * @param eventDescription La nueva descripción del evento.
     */
    public void setEventDescription(String eventDescription) {
        EventDescription = eventDescription;
    }

    /**
     * Obtiene el número máximo de participantes del evento.
     *
     * @return El número máximo de participantes.
     */
    public int getMaxParticipants() {
        return MaxParticipants;
    }

    /**
     * Establece el número máximo de participantes del evento.
     *
     * @param maxParticipants El nuevo número máximo de participantes.
     */
    public void setMaxParticipants(int maxParticipants) {
        MaxParticipants = maxParticipants;
    }

    /**
     * Obtiene el identificador único del evento.
     *
     * @return La descripción del evento.
     */
    @Override
    public String getIdentificadorUnico() {
        return EventDescription;
    }

    /**
     * Obtiene el tipo del evento.
     *
     * @return El tipo del evento.
     */
    @Override
    public String getTipo() {
        return "EventoInterno";
    }

    /**
     * Realiza una reserva para el evento.
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
        if (Param.size() < 4)
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

            String Descripcion = Param.get(2);
            int Participantes = Integer.parseInt(Param.get(3));
            boolean Disponible = false;

            for (Classroom classroom : ClassroomList) {
                if (classroom.getMaximumCapacity() >= Participantes) {
                    Disponible = true;
                    for (Booking booking : classroom.getBookingList()) {
                        boolean Rango = (FechaInicio.isBefore(booking.getEndOfClass()) && FechaFin.isAfter(booking.getStartOfClass()));
                        if (Rango) {
                            Disponible = false;
                            break;
                        }
                    }
                    if (Disponible) {
                        LocalDateTime FecAct = LocalDateTime.now();
                        Booking booking = new Booking(classroom.BookingCount(), FecAct, FechaInicio, FechaFin, new Event(Code, Descripcion, Participantes));
                        classroom.getBookingList().add(booking);
                        break;
                    }
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
     * Devuelve una representación en cadena del evento.
     *
     * @return Una cadena que representa el evento.
     */
    @Override
    public String toString() {
        return super.toString()+"Event{" +
                "EventDescription='" + EventDescription + '\'' +
                ", MaxParticipants=" + MaxParticipants +
                '}';
    }

    /**
     * Obtiene el panel de reserva asociado al evento.
     *
     * @return El número del panel de reserva.
     */
    @Override
    public int PanelReserva() {
        return 3;
    }
}
