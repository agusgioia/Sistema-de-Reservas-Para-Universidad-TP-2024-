package GIU;

import DomainClases.Classroom;
import javax.swing.*;
import java.util.TreeSet;

/**
 * Clase que representa la ventana de inicio con el menú principal de opciones.
 */
public class Inicio extends JDialog {
    private JPanel PrincipalPanel;
    private JPanel MenuPanel;
    private JButton consultarButton;
    private JButton hacerReservasButton;
    private JButton cancelarReservasButton;
    private JButton generarReportesButton;
    private JButton salirButton;
    private JButton listadosButton;

    /**
     * Obtiene el panel principal.
     *
     * @return El panel principal.
     */
    public JPanel getPrincipalPanel() {
        return PrincipalPanel;
    }

    /**
     * Constructor para crear la ventana de inicio.
     *
     * @param ClassroomList La lista de aulas disponibles.
     */
    public Inicio(TreeSet<Classroom> ClassroomList) {
        setModal(true);

        listadosButton.addActionListener(e -> onListados(ClassroomList));
        consultarButton.addActionListener(e -> onconsulta(ClassroomList));
        hacerReservasButton.addActionListener(e -> onreserva(ClassroomList));
        cancelarReservasButton.addActionListener(e -> oncancela(ClassroomList));
        generarReportesButton.addActionListener(e -> onreporte(ClassroomList));
        salirButton.addActionListener(e -> System.exit(0));
    }

    /**
     * Método que se llama al presionar el botón de listados.
     *
     * @param classroomList La lista de aulas disponibles.
     */
    public void onListados(TreeSet<Classroom> classroomList) {
        setModal(false);
        Listados listados = new Listados(classroomList);
        listados.setVisible(true);
    }

    /**
     * Método que se llama al presionar el botón de generar reportes.
     *
     * @param classroomList La lista de aulas disponibles.
     */
    private void onreporte(TreeSet<Classroom> classroomList) {
        setModal(false);
        Reporte ReporteInterfaz = new Reporte(classroomList);
        ReporteInterfaz.setVisible(true);
    }

    /**
     * Método que se llama al presionar el botón de cancelar reservas.
     *
     * @param classroomList La lista de aulas disponibles.
     */
    private void oncancela(TreeSet<Classroom> classroomList) {
        setModal(false);
        CancelarReserva cancelarInterfaz = new CancelarReserva(classroomList);
        cancelarInterfaz.setVisible(true);
    }

    /**
     * Método que se llama al presionar el botón de consulta.
     *
     * @param classroomList La lista de aulas disponibles.
     */
    private void onconsulta(TreeSet<Classroom> classroomList) {
        setModal(false);
        Consulta ConsultaInterfaz = new Consulta(classroomList);
        ConsultaInterfaz.setVisible(true);
    }

    /**
     * Método que se llama al presionar el botón de hacer reservas.
     *
     * @param classroomList La lista de aulas disponibles.
     */
    private void onreserva(TreeSet<Classroom> classroomList) {
        setModal(false);
        reserva reservaInterfaz = new reserva(classroomList);
        reservaInterfaz.setVisible(true);
    }
}
