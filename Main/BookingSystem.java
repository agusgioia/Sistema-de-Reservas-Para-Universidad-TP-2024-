package Main;

import DomainClases.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Sistema de reservas de aulas.
 */
public class BookingSystem {

    private final TreeSet<Classroom> ClassroomList;

    /**
     * Constructor para inicializar el sistema de reservas con una lista de aulas.
     * @param classroomList La lista de aulas a ser gestionada por el sistema.
     */
    public BookingSystem(TreeSet<Classroom> classroomList) {
        ClassroomList = classroomList;
    }

    /**
     * Genera un listado de todas las aulas disponibles en el sistema.
     * @return Una cadena que contiene la lista de aulas.
     * @throws NullPointerException Si ocurre un error al intentar acceder a los datos.
     */
    public String ListadoDeAulas() throws NullPointerException {
        try {
            StringBuilder result = new StringBuilder();
            for (Classroom classroom : ClassroomList) {
                result.append(classroom).append("\n");
            }
            return result.toString();
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Genera un listado de todas las asignaturas reservadas en el sistema.
     * @return Una cadena que contiene la lista de asignaturas.
     * @throws NullPointerException Si ocurre un error al intentar acceder a los datos.
     */
    public String ListadoAsignaturas() throws NullPointerException {
        try {
            StringBuilder result = new StringBuilder();
            ArrayList<Booking> BookingList;
            Set<Organization> organizacionesUnicas = new HashSet<>();
            for (Classroom classroom : ClassroomList) {
                BookingList = classroom.getBookingList();
                for (Booking booking : BookingList) {
                    Organization organization = booking.getBookingOwner();
                    if ("Asignatura".equals(organization.getTipo()))
                        organizacionesUnicas.add(organization);
                }
            }
            for (Organization organizacion : organizacionesUnicas) {
                result.append(organizacion).append("\n");
            }
            return result.toString();
        } catch (NullPointerException Ex) {
            throw new RuntimeException(Ex);
        }
    }

    /**
     * Genera un listado de todos los cursos reservados en el sistema.
     * @return Una cadena que contiene la lista de cursos.
     * @throws NullPointerException Si ocurre un error al intentar acceder a los datos.
     */
    public String ListadoCursos() throws NullPointerException {
        try {
            StringBuilder result = new StringBuilder();
            ArrayList<Booking> BookingList;
            Set<Organization> organizacionesUnicas = new HashSet<>();
            for (Classroom classroom : ClassroomList) {
                BookingList = classroom.getBookingList();
                for (Booking booking : BookingList) {
                    Organization organization = booking.getBookingOwner();
                    if ("Curso".equals(organization.getTipo()))
                        organizacionesUnicas.add(organization);
                }
            }
            for (Organization organizacion : organizacionesUnicas) {
                result.append(organizacion).append("\n");
            }
            return result.toString();
        } catch (NullPointerException Ex) {
            throw new RuntimeException(Ex);
        }
    }

    /**
     * Genera un listado de todos los eventos internos reservados en el sistema.
     * @return Una cadena que contiene la lista de eventos internos.
     * @throws NullPointerException Si ocurre un error al intentar acceder a los datos.
     */
    public String ListadoEventosInternos() throws NullPointerException {
        try {
            StringBuilder result = new StringBuilder();
            ArrayList<Booking> BookingList;
            Set<Organization> organizacionesUnicas = new HashSet<>();
            for (Classroom classroom : ClassroomList) {
                BookingList = classroom.getBookingList();
                for (Booking booking : BookingList) {
                    Organization organization = booking.getBookingOwner();
                    if ("EventoInterno".equals(organization.getTipo()))
                        organizacionesUnicas.add(organization);
                }
            }
            for (Organization organizacion : organizacionesUnicas) {
                result.append(organizacion).append("\n");
            }
            return result.toString();
        } catch (NullPointerException Ex) {
            throw new RuntimeException(Ex);
        }
    }

    /**
     * Genera un listado de todos los eventos externos reservados en el sistema.
     * @return Una cadena que contiene la lista de eventos externos.
     * @throws NullPointerException Si ocurre un error al intentar acceder a los datos.
     */
    public String ListadoEventosExternos() throws NullPointerException {
        try {
            StringBuilder result = new StringBuilder();
            ArrayList<Booking> BookingList;
            Set<Organization> organizacionesUnicas = new HashSet<>();
            for (Classroom classroom : ClassroomList) {
                BookingList = classroom.getBookingList();
                for (Booking booking : BookingList) {
                    Organization organization = booking.getBookingOwner();
                    if ("EventoExterno".equals(organization.getTipo()))
                        organizacionesUnicas.add(organization);
                }
            }
            for (Organization organizacion : organizacionesUnicas) {
                result.append(organizacion).append("\n");
            }
            return result.toString();
        } catch (NullPointerException Ex) {
            throw new RuntimeException(Ex);
        }
    }

    /**
     * Filtra y devuelve las aulas ubicadas en un determinado piso.
     * @param floor Número del piso a filtrar.
     * @param classroomList La lista de aulas en la que se realizará la búsqueda.
     * @return Una cadena que contiene las aulas encontradas en el piso especificado.
     */
    public String floorFilter(int floor, TreeSet<Classroom> classroomList) {
        StringBuilder result = new StringBuilder();
        boolean found = false;
        for (Classroom classroom : classroomList) {
            if (classroom.FloorNumber() == floor) {
                found = true;
                result.append(classroom).append("\n");
            }
        }
        if (!found)
            result.append("No se encontraron aulas en el piso ").append(floor);
        if (found)
            return floor + " piso \n" + result;
        else
            return result.toString();
    }

    /**
     * Filtra y devuelve las aulas reservadas por una organización con un código específico.
     * @param code Código de la organización a buscar.
     * @param ClassroomList La lista de aulas en la que se realizará la búsqueda.
     * @return Una cadena que contiene las aulas reservadas por la organización con el código especificado.
     * @throws NullPointerException Si ocurre un error al intentar acceder a los datos.
     */
    public String CodeFilter(String code, TreeSet<Classroom> ClassroomList) throws NullPointerException {
        try {
            StringBuilder res = new StringBuilder();
            boolean found = false;
            for (Classroom classroom : ClassroomList) {
                for (Booking booking : classroom.getBookingList()) {
                    String organizationCode = booking.getBookingOwner().getCode();
                    if (organizationCode.equals(code)) {
                        res.append(classroom).append("\n");
                        found = true;
                        break;
                    }
                }
            }
            if (!found)
                res.append("No se encontró el código").append("\n");
            return res.toString();
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Busca y devuelve la organización que ha reservado un aula específica con un código dado.
     * @param code Código de la organización a buscar.
     * @return La organización que ha reservado el aula con el código especificado.
     * @throws NullPointerException Si ocurre un error al intentar acceder a los datos.
     */
    public Organization bookClassroomForOrganization(String code) throws NullPointerException {
        try {
            Organization organization = null;
            for (Classroom classroom : ClassroomList) {
                for (Booking booking : classroom.getBookingList()) {
                    if (booking.getBookingOwner().getCode().equals(code)) {
                        organization = booking.getBookingOwner();
                        break;
                    }
                }
                if (organization != null)
                    break;
            }
            return organization;
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Obtiene una lista de códigos de todas las organizaciones que han realizado reservas.
     * @return Una cadena que contiene todos los códigos de las organizaciones que han reservado aulas.
     * @throws NullPointerException Si ocurre un error al intentar acceder a los datos.
     */
    public String ObtenerCodigosOrganizacion() throws NullPointerException {
        try {
            StringBuilder result = new StringBuilder();
            ArrayList<Booking> BookingList;
            ArrayList<Booking> TotalBooking = new ArrayList<>();
            for (Classroom classroom : ClassroomList) {
                BookingList = classroom.getBookingList();
                TotalBooking.addAll(BookingList);
            }
            TreeSet<Booking> BookingTree = new TreeSet<>(TotalBooking);
            for (Booking booking : BookingTree)
                result.append(booking.getBookingOwner().getCode()).append("\n");
            return result.toString();
        } catch (NullPointerException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Cancela una reserva específica de un aula en base a su identificador y el código de la reserva.
     * @param idClassrom Identificador del aula.
     * @param bookingCode Código de la reserva a cancelar.
     * @param ClassroomList La lista de aulas en la que se realizará la cancelación.
     * @return Una cadena que representa las reservas restantes en el aula después de la cancelación.
     * @throws NullPointerException Si ocurre un error al intentar acceder a los datos o si el aula no se encuentra.
     */
    public String BookingDelete(int idClassrom, int bookingCode, TreeSet<Classroom> ClassroomList) throws NullPointerException {
        try {
            StringBuilder result = new StringBuilder();
            Classroom classroomfound = null;
            for (Classroom classroom : ClassroomList) {
                if (classroom.getIdNumber() == idClassrom) {
                    classroom.bookingCancel(bookingCode);
                    classroomfound = classroom;
                    break;
                }
            }
            if (classroomfound == null)
                throw new NullPointerException("Aula no encontrada");

            for (Booking booking : classroomfound.getBookingList())
                result.append(booking.toString());

            return result.toString();
        } catch (NullPointerException e) {
            throw new NullPointerException("Aula no encontrada");
        }
    }
}

