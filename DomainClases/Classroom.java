package DomainClases;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Representa un aula con su capacidad máxima y lista de reservas.
 */
public class Classroom implements Comparable<Classroom>, Serializable {
    private int IdNumber;
    private long MaximumCapacity;
    private ArrayList<Booking> BookingList;

    /**
     * Constructor por defecto para la clase Classroom.
     */
    public Classroom() {}

    /**
     * Constructor para crear una aula con sus detalles.
     *
     * @param idNumber        El número de identificación del aula.
     * @param maximumCapacity La capacidad máxima del aula.
     * @param bookingList     La lista de reservas del aula.
     */
    public Classroom(int idNumber, long maximumCapacity, ArrayList<Booking> bookingList) {
        this.IdNumber = idNumber;
        this.MaximumCapacity = maximumCapacity;
        this.BookingList = bookingList;
    }

    /**
     * Calcula la cantidad total recaudada por el aula.
     *
     * @return La cantidad total recaudada.
     */
    public double AmountCollected() {
        TreeSet<Booking> Reservas = new TreeSet<>(BookingList);
        double Amount = 0;
        for (Booking booking : Reservas)
            Amount += booking.getBookingOwner().AmountCollected();
        return Amount;
    }

    /**
     * Cancela una reserva basada en su ID.
     *
     * @param bookingId El ID de la reserva a cancelar.
     */
    public void bookingCancel(int bookingId) {
        if (BookingList.removeIf(booking -> booking.getNumberCode() == bookingId))
            JOptionPane.showMessageDialog(null, "La cancelación se realizó con éxito");
        else
            JOptionPane.showMessageDialog(null, "No se encontró el código ingresado");
    }

    /**
     * Obtiene el número de identificación del aula.
     *
     * @return El número de identificación del aula.
     */
    public int getIdNumber() {
        return IdNumber;
    }

    /**
     * Establece el número de identificación del aula.
     *
     * @param idNumber El número de identificación del aula.
     */
    public void setIdNumber(int idNumber) {
        IdNumber = idNumber;
    }

    /**
     * Obtiene la capacidad máxima del aula.
     *
     * @return La capacidad máxima del aula.
     */
    public long getMaximumCapacity() {
        return MaximumCapacity;
    }

    /**
     * Establece la capacidad máxima del aula.
     *
     * @param maximumCapacity La capacidad máxima del aula.
     */
    public void setMaximumCapacity(long maximumCapacity) {
        MaximumCapacity = maximumCapacity;
    }

    /**
     * Obtiene la lista de reservas del aula.
     *
     * @return La lista de reservas del aula.
     */
    public ArrayList<Booking> getBookingList() {
        return BookingList;
    }

    /**
     * Establece la lista de reservas del aula.
     *
     * @param bookingList La lista de reservas del aula.
     */
    public void setBookingList(ArrayList<Booking> bookingList) {
        BookingList = bookingList;
    }

    /**
     * Obtiene el número de reservas del aula.
     *
     * @return El número de reservas del aula.
     */
    public int BookingCount() {
        return BookingList.size();
    }

    /**
     * Obtiene el número de piso basado en el número de identificación del aula.
     *
     * @return El número de piso del aula.
     */
    public int FloorNumber() {
        return IdNumber / 100;
    }

    /**
     * Devuelve una representación en cadena del aula.
     *
     * @return Una cadena que representa el aula.
     */
    @Override
    public String toString() {
        return "Classroom{" +
                "IdNumber=" + IdNumber +
                ", MaximumCapacity=" + MaximumCapacity +
                ", BookingList=" + BookingList +
                '}' + "\n";
    }

    /**
     * Compara esta aula con otra basada en el número de identificación.
     *
     * @param classroom El aula a comparar.
     * @return Un valor negativo, cero o positivo si el número de identificación de esta aula es menor, igual o mayor que el del aula especificada.
     */
    @Override
    public int compareTo(Classroom classroom) {
        return Integer.compare(this.IdNumber, classroom.getIdNumber());
    }
}
