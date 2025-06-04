package GIU;

import DomainClases.Classroom;
import Main.BookingSystem;
import XML.Serializador;

import javax.swing.*;
import java.awt.event.*;
import java.util.TreeSet;
import javax.swing.JOptionPane;

/**
 * Clase que representa el diálogo para cancelar una reserva.
 */
public class CancelarReserva extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField ID;
    private JTextField Codigo;
    private JTextArea textArea1;

    /**
     * Constructor para crear el diálogo de cancelar reserva.
     *
     * @param ClassroomList La lista de aulas disponibles.
     */
    public CancelarReserva(TreeSet<Classroom> ClassroomList) {
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);
        setContentPane(contentPane);
        setSize(600, 700);
        setLocationRelativeTo(null);
        setTitle("Cancelar Reserva");

        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);
        textArea1.setEditable(false);

        setModal(true);
        buttonOK.addActionListener(e -> onOK(ClassroomList));
        buttonCancel.addActionListener(e -> onCancel());

        // Llama a onCancel() cuando se hace clic en la cruz
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // Llama a onCancel() al presionar ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    /**
     * Método que se llama al hacer clic en el botón OK para cancelar la reserva.
     *
     * @param ClassroomList La lista de aulas disponibles.
     * @throws NumberFormatException Si hay un error al analizar un número.
     */
    private void onOK(TreeSet<Classroom> ClassroomList) throws NumberFormatException {
        int IDact, codigoact;
        try {
            BookingSystem bookingSystem = new BookingSystem(ClassroomList);
            codigoact = Integer.parseInt(Codigo.getText());
            IDact = Integer.parseInt(ID.getText());
            if (codigoact<0)
                throw new RuntimeException("Ingrese un valor de código válido");
            textArea1.setEditable(false);
            textArea1.setText(bookingSystem.BookingDelete(IDact, codigoact, ClassroomList));
            Serializador.serializarTreeSet(ClassroomList, "Archivo.ser");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Método que se llama al hacer clic en el botón Cancel para cerrar el diálogo.
     */
    private void onCancel() {
        dispose();
    }
}
