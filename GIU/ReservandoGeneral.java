package GIU;

import DomainClases.Classroom;
import DomainClases.Organization;
import XML.Serializador;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Clase que representa la ventana para realizar reservas generales de diferentes tipos de eventos o cursos en aulas.
 */
public class ReservandoGeneral extends JDialog {
    private JPanel PrincipalPanel;
    private JPanel PanelCurso;
    private JPanel PanelEventoInterno;
    private JPanel PanelEventoExterno;
    private JTextField FechaDeInicioDeReserva;
    private JTextField FechaDeFinDeReserva;
    private JButton aceptarButton;
    private JTextField CantidadMaximadeParticipantesIN;
    private JTextField NombreDeLaOrganizacion;
    private JTextField CostoDeAlquiler;
    private JTextField DescripcionDelCurso;
    private JTextField EstudiantesInscriptos;
    private JTextField CantidadDeClases;
    private JTextField CostoPorEstudiante;
    private JTextField DescripciondelEventoIntero;
    private JTextField CantidadMaximaDeParticipantesEX;
    private JTextField DescripcionDelEventoExterno;
    private JPanel PanelReserva;
    private JTextField ComienzodeCursada;
    private JTextField FindeCursada;
    private JTextField CantidaddeEstudiantes;
    private JPanel PanelAsignatura;
    private JPanel PanelBotones;
    private JButton salirButton;
    private JTextField NombreAsignatura;

    /**
     * Constructor para crear la ventana de reserva general.
     *
     * @param ClassroomList La lista de aulas disponibles para la reserva.
     * @param organization  La organización para la cual se realiza la reserva.
     * @param IDPanel       El identificador del panel de reserva a mostrar.
     */
    public ReservandoGeneral(TreeSet<Classroom> ClassroomList, Organization organization, int IDPanel) {

        // Configuración del panel según el IDPanel proporcionado
        JPanel jPanel = new JPanel();
        switch (IDPanel) {
            case 1 -> {
                jPanel.add(PanelAsignatura);
                setTitle("Reserva de Asignatura");
            }
            case 2 -> {
                jPanel.add(PanelCurso);
                setTitle("Reserva de Curso");
            }
            case 3 -> {
                jPanel.add(PanelEventoInterno);
                setTitle("Reserva de Evento Interno");
            }
            case 4 -> {
                jPanel.add(PanelEventoExterno);
                setTitle("Reserva de Evento Externo");
            }
            default -> jPanel=new JPanel();
        }

        // Contenedor principal con disposición vertical
        JPanel Contenedor = new JPanel(new GridLayout(0, 1));
        Contenedor.add(jPanel, BorderLayout.NORTH); // Agrega el panel específico al contenedor
        Contenedor.add(PanelReserva, BorderLayout.CENTER); // Agrega el panel de reserva al contenedor
        Contenedor.add(PanelBotones, BorderLayout.SOUTH); // Agrega el panel de botones al contenedor
        Contenedor.add(new JLabel("Formato de fecha YYYY-MM-DD-HH:mm:ss"), BorderLayout.NORTH); // Agrega etiqueta para el formato de fecha
        getContentPane().add(Contenedor, BorderLayout.CENTER); // Agrega el contenedor al contenido del diálogo
        pack();
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra la ventana al presionar el botón de salir
        setModal(true); // Ventana modal

        // Listener para el botón de salir
        salirButton.addActionListener(e -> onCancel());

        // Listener para el botón de aceptar (realizar la reserva)
        aceptarButton.addActionListener(e -> {
            try {
                ArrayList<String> Param = new ArrayList<>();
                String ComienzaReserva = FechaDeInicioDeReserva.getText();
                String FinReserva = FechaDeFinDeReserva.getText();
                Param.add(ComienzaReserva);
                Param.add(FinReserva);

                // Según el IDPanel, se configuran los parámetros específicos para la reserva
                switch (IDPanel) {
                    case 1 -> {
                        String NombreAsi = NombreAsignatura.getText();
                        String ComienzoCursada = ComienzodeCursada.getText();
                        String FinCursada = FindeCursada.getText();
                        String CantEst = CantidaddeEstudiantes.getText();
                        Param.add(NombreAsi);
                        Param.add(ComienzoCursada);
                        Param.add(FinCursada);
                        Param.add(CantEst);
                        organization.Reservation(Param, ClassroomList); // Realiza la reserva de la asignatura
                    }
                    case 2 -> {
                        String Descripcion = DescripcionDelCurso.getText();
                        String EstudInscr = EstudiantesInscriptos.getText();
                        String CantClases = CantidadDeClases.getText();
                        String CostoPEst = CostoPorEstudiante.getText();
                        Param.add(Descripcion);
                        Param.add(EstudInscr);
                        Param.add(CantClases);
                        Param.add(CostoPEst);
                        organization.Reservation(Param, ClassroomList); // Realiza la reserva del curso
                    }
                    case 3 -> {
                        String DescEvent = DescripciondelEventoIntero.getText();
                        String CantMaxPart = CantidadMaximadeParticipantesIN.getText();
                        Param.add(CantMaxPart);
                        Param.add(DescEvent);
                        organization.Reservation(Param, ClassroomList); // Realiza la reserva del evento interno
                    }
                    case 4 -> {
                        String CantMaxPart = CantidadMaximaDeParticipantesEX.getText();
                        String DescEvent = DescripcionDelEventoExterno.getText();
                        String NomOrg = NombreDeLaOrganizacion.getText();
                        String CostoAlq = CostoDeAlquiler.getText();
                        Param.add(CantMaxPart);
                        Param.add(DescEvent);
                        Param.add(NomOrg);
                        Param.add(CostoAlq);
                        organization.Reservation(Param, ClassroomList); // Realiza la reserva del evento externo
                    }
                }
                Serializador.serializarTreeSet(ClassroomList, "Archivo.ser"); // Serializa la lista de aulas actualizada
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Datos numéricos erróneos o fechas incompletas", exception.getMessage(), JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    /**
     * Método para cancelar y cerrar la ventana.
     */
    private void onCancel() {
        dispose();
    }
}
