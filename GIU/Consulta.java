package GIU;

import DomainClases.Classroom;
import Main.BookingSystem;
import javax.swing.*;
import java.awt.event.*;
import java.util.TreeSet;

/**
 * Clase que representa el diálogo para realizar consultas sobre las reservas.
 */
public class Consulta extends JDialog {
    private JPanel contentPane;
    private JButton buttonSalir;
    private JComboBox<String> comboBox1;
    private JButton buscarButton;
    private JTextArea AreaConsulta;
    private JTextField consulta;
    private JPanel Panelconsulta;

    /**
     * Constructor para crear el diálogo de consulta.
     *
     * @param ClassroomList La lista de aulas disponibles.
     */
    public Consulta(TreeSet<Classroom> ClassroomList) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buscarButton);
        setSize(600, 700);
        setLocationRelativeTo(null);
        setTitle("Consulta");

        comboBox1.addItem("Numero de piso");
        comboBox1.addItem("Codigo");

        Panelconsulta.setSize(400, 500);

        AreaConsulta.setLineWrap(true);
        AreaConsulta.setWrapStyleWord(true);
        AreaConsulta.setEditable(false);

        // Escucha del combobox
        buscarButton.addActionListener(e -> {
            String resultado = oncombobox(ClassroomList);
            buscarButtonAction(resultado);
        });

        buttonSalir.addActionListener(e -> onSalir());

        // Llama a dispose() cuando se hace clic en la cruz
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // Llama a dispose() al presionar ESCAPE
        contentPane.registerKeyboardAction(e -> dispose(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    /**
     * Método que se llama al seleccionar una opción del combobox y buscar.
     *
     * @param ClassroomList La lista de aulas disponibles.
     * @return El resultado de la consulta.
     * @throws NumberFormatException    Si hay un error al analizar un número.
     * @throws NullPointerException     Si no se selecciona una opción de filtro.
     */
    private String oncombobox(TreeSet<Classroom> ClassroomList) throws NumberFormatException, NullPointerException {
        try {
            BookingSystem bookingSystem = new BookingSystem(ClassroomList);
            String selectedOption = (String) comboBox1.getSelectedItem();
            String finalRes;
            int numpiso;
            if (("Numero de piso").equals(selectedOption)) {
                // Método de búsqueda por número de piso
                numpiso = Integer.parseInt(consulta.getText());
                finalRes = bookingSystem.floorFilter(numpiso, ClassroomList);
            } else {
                finalRes = bookingSystem.CodeFilter(consulta.getText(), ClassroomList);
            }
            return finalRes;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese un número de piso válido", e.getMessage(), JOptionPane.WARNING_MESSAGE);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Seleccione una opción de filtro", e.getMessage(), JOptionPane.WARNING_MESSAGE);
        }
        return "";
    }

    /**
     * Método que muestra el resultado de la búsqueda en el área de consulta.
     *
     * @param resultado El resultado de la búsqueda.
     */
    private void buscarButtonAction(String resultado) {
        AreaConsulta.setEditable(false);
        AreaConsulta.setText(resultado);
    }

    /**
     * Método que se llama al hacer clic en el botón Salir para cerrar el diálogo.
     */
    private void onSalir() {
        dispose();
    }
}
