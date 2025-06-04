package GIU;

import DomainClases.Classroom;
import Main.BookingSystem;

import javax.swing.*;
import java.util.TreeSet;

/**
 * Clase que representa la ventana de listados de información sobre aulas, asignaturas, cursos y eventos.
 */
public class Listados extends JDialog {
    private JPanel PanelPrincipal;
    private JButton listadoDeEventosExternosButton;
    private JButton listadoDeAsignaturasButton;
    private JButton listadoDeCursosButton;
    private JButton listadoDeEventosInternosButton;
    private JTextArea textArea1;
    private JButton salirButton;
    private JButton listadoDeAulasButton;

    /**
     * Constructor para crear la ventana de listados.
     *
     * @param ClassroomList La lista de aulas disponibles.
     */
    public Listados(TreeSet<Classroom> ClassroomList) {
        setContentPane(PanelPrincipal);
        setModal(true);
        getRootPane().setDefaultButton(salirButton);
        setSize(1000, 700);
        textArea1.setSize(400, 500);
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);
        textArea1.setEditable(false);
        setLocationRelativeTo(null);

        BookingSystem bookingSystem = new BookingSystem(ClassroomList);
        listadoDeAulasButton.addActionListener(e -> textArea1.setText(bookingSystem.ListadoDeAulas()));
        listadoDeAsignaturasButton.addActionListener(e -> textArea1.setText(bookingSystem.ListadoAsignaturas()));
        listadoDeCursosButton.addActionListener(e -> textArea1.setText(bookingSystem.ListadoCursos()));
        listadoDeEventosInternosButton.addActionListener(e -> textArea1.setText(bookingSystem.ListadoEventosInternos()));
        listadoDeEventosExternosButton.addActionListener(e -> textArea1.setText(bookingSystem.ListadoEventosExternos()));
        salirButton.addActionListener(e -> dispose());
    }
}
