package DomainClases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.TreeSet;

/**
 * Clase abstracta que representa una organización.
 * Proporciona métodos y campos comunes para las subclases que representan diferentes tipos de organizaciones.
 */
public abstract class Organization implements Serializable {
    private final String Code;

    /**
     * Constructor para crear una organización con un código específico.
     *
     * @param code El código de la organización.
     */
    public Organization(String code) {
        Code = code;
    }

    /**
     * Obtiene el código de la organización.
     *
     * @return El código de la organización.
     */
    public String getCode() {
        return Code;
    }

    /**
     * Método abstracto para realizar una reserva.
     * Debe ser implementado por las subclases.
     *
     * @param Param      Los parámetros necesarios para la reserva.
     * @param classrooms La lista de aulas disponibles.
     */
    public abstract void Reservation(ArrayList<String> Param, TreeSet<Classroom> classrooms);

    /**
     * Calcula la cantidad total recaudada por la organización.
     * Puede ser sobrescrito por las subclases para proporcionar una implementación específica.
     *
     * @return La cantidad total recaudada.
     */
    public double AmountCollected() {
        return 0;
    }

    /**
     * Devuelve una representación en cadena de la organización.
     *
     * @return Una cadena que representa la organización.
     */
    @Override
    public String toString() {
        return "Organization{" +
                "Code='" + Code + '\'' +
                '}';
    }

    /**
     * Método abstracto para obtener el identificador único de la organización.
     * Debe ser implementado por las subclases.
     *
     * @return El identificador único de la organización.
     */
    public abstract String getIdentificadorUnico();

    /**
     * Método abstracto para obtener el tipo de la organización.
     * Debe ser implementado por las subclases.
     *
     * @return El tipo de la organización.
     */
    public abstract String getTipo();

    /**
     * Compara esta organización con otro objeto para verificar la igualdad.
     *
     * @param obj El objeto a comparar.
     * @return true si los objetos son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Organization that = (Organization) obj;
        return Objects.equals(this.getIdentificadorUnico(), that.getIdentificadorUnico());
    }

    /**
     * Calcula el código hash para la organización.
     *
     * @return El código hash de la organización.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getIdentificadorUnico());
    }

    /**
     * Método abstracto para obtener el panel de reserva asociado a la organización.
     * Debe ser implementado por las subclases.
     *
     * @return El número del panel de reserva.
     */
    public abstract int PanelReserva();
}
