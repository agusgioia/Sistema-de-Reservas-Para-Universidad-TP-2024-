package GIU;

import DomainClases.Classroom;
import DomainClases.Organization;
import Main.BookingSystem;

import javax.swing.*;
import java.awt.event.*;
import java.util.TreeSet;

/**
 * Clase que representa la ventana de reserva de aulas para una organización.
 */
public class reserva extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JTextField textField1;
    private JButton okButton;
    private JPanel PanelBotones;
    private JPanel PanelCodigo;
    private JTextArea textArea1;

    /**
     * Constructor para crear la ventana de reserva de aulas.
     *
     * @param ClassroomList La lista de aulas disponibles para la reserva.
     * @throws NullPointerException Si ocurre un error de referencia nula durante la creación de la ventana.
     */
    public reserva(TreeSet<Classroom> ClassroomList) throws NullPointerException {
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonCancel);
        setTitle("Reserva");
        BookingSystem bookingSystem = new BookingSystem(ClassroomList);
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);
        textArea1.setEditable(false);
        textArea1.setText(bookingSystem.ObtenerCodigosOrganizacion());
        pack();
        setLocationRelativeTo(null);
        setModal(true);

        // Listener para el botón de cancelar
        buttonCancel.addActionListener(e -> onCancel());

        // Cerrar la ventana cuando se presione la 'X'
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // Cancelar con la tecla ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        // Listener para el botón de reserva
        okButton.addActionListener(e -> {
            try {
                Organization organization = bookingSystem.bookClassroomForOrganization(textField1.getText());
                setModal(false);
                ReservandoGeneral reservandoGeneral = new ReservandoGeneral(ClassroomList, organization, organization.PanelReserva());
                reservandoGeneral.setVisible(true);
            } catch (NullPointerException nullPointerException) {
                JOptionPane.showMessageDialog(null, "El código no existe en el sistema", nullPointerException.getMessage(), JOptionPane.WARNING_MESSAGE);
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
