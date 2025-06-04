package DomainClases;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Representa una reserva de aula.
 */
public class Booking implements Comparable<Booking>, Serializable {

    private int NumberCode;
    private LocalDateTime BookingDate;
    private LocalDateTime StartOfClass;
    private LocalDateTime EndOfClass;
    private Organization BookingOwner;

    /**
     * Constructor para crear una reserva.
     *
     * @param numberCode   El código único de la reserva.
     * @param bookingDate  La fecha de la reserva.
     * @param startOfClass La hora de inicio de la clase.
     * @param endOfClass   La hora de finalización de la clase.
     * @param bookingOwner La organización propietaria de la reserva.
     */
    public Booking(int numberCode, LocalDateTime bookingDate, LocalDateTime startOfClass, LocalDateTime endOfClass, Organization bookingOwner) {
        this.NumberCode = numberCode;
        this.BookingDate = bookingDate;
        this.StartOfClass = startOfClass;
        this.EndOfClass = endOfClass;
        this.BookingOwner = bookingOwner;
    }

    /**
     * Obtiene el propietario de la reserva.
     *
     * @return La organización propietaria de la reserva.
     */
    public Organization getBookingOwner() {
        return BookingOwner;
    }

    /**
     * Obtiene el código de la reserva.
     *
     * @return El código de la reserva.
     */
    public int getNumberCode() {
        return NumberCode;
    }

    /**
     * Establece el código de la reserva.
     *
     * @param numberCode El código de la reserva.
     */
    public void setNumberCode(int numberCode) {
        NumberCode = numberCode;
    }

    /**
     * Obtiene la fecha de la reserva.
     *
     * @return La fecha de la reserva.
     */
    public LocalDateTime getBookingDate() {
        return BookingDate;
    }

    /**
     * Establece la fecha de la reserva.
     *
     * @param bookingDate La fecha de la reserva.
     */
    public void setBookingDate(LocalDateTime bookingDate) {
        BookingDate = bookingDate;
    }

    /**
     * Obtiene la hora de inicio de la clase.
     *
     * @return La hora de inicio de la clase.
     */
    public LocalDateTime getStartOfClass() {
        return StartOfClass;
    }

    /**
     * Establece la hora de inicio de la clase.
     *
     * @param startOfClass La hora de inicio de la clase.
     */
    public void setStartOfClass(LocalDateTime startOfClass) {
        StartOfClass = startOfClass;
    }

    /**
     * Obtiene la hora de finalización de la clase.
     *
     * @return La hora de finalización de la clase.
     */
    public LocalDateTime getEndOfClass() {
        return EndOfClass;
    }

    /**
     * Establece la hora de finalización de la clase.
     *
     * @param endOfClass La hora de finalización de la clase.
     */
    public void setEndOfClass(LocalDateTime endOfClass) {
        EndOfClass = endOfClass;
    }

    /**
     * Establece el propietario de la reserva.
     *
     * @param bookingOwner La organización propietaria de la reserva.
     */
    public void setBookingOwner(Organization bookingOwner) {
        BookingOwner = bookingOwner;
    }

    /**
     * Devuelve una representación en cadena de la reserva.
     *
     * @return Una cadena que representa la reserva.
     */
    @Override
    public String toString() {
        return "\n \n Booking{" +
                "NumberCode=" + NumberCode +
                ", BookingDate=" + BookingDate +
                ", StartOfClass=" + StartOfClass +
                ", EndOfClass=" + EndOfClass +
                ", BookingOwner=" + BookingOwner +
                '}';
    }

    /**
     * Compara esta reserva con otra reserva basada en el código de la organización.
     *
     * @param B La reserva a comparar.
     * @return Un valor negativo, cero o positivo si el código de la organización de esta reserva es menor, igual o mayor que el de la reserva especificada.
     */
    @Override
    public int compareTo(Booking B) {
        return BookingOwner.getCode().compareTo(B.BookingOwner.getCode());
    }
}
