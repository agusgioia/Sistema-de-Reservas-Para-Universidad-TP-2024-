package GIU;

import DomainClases.Classroom;
import Report.AmountReport;
import Report.BookingReport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.TreeSet;

/**
 * Clase que representa la ventana de reportes de la aplicación.
 */
public class Reporte extends JDialog {
    private JPanel mainPanel;
    private JButton reporte1Button;
    private JButton reporte4Button;
    private JButton reporte3Button;
    private JButton reporte2Button;
    private JButton salirButton;
    private JTextArea textArea1;
    private JPanel PanelReportes;

    /**
     * Constructor para crear la ventana de reportes.
     *
     * @param ClassroomList La lista de aulas disponibles para generar reportes.
     */
    public Reporte(TreeSet<Classroom> ClassroomList) {
        AmountReport amountReport = new AmountReport();
        BookingReport bookingReport = new BookingReport();

        setContentPane(mainPanel);
        setTitle("Reportes");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);
        textArea1.setEditable(false);
        setModal(true);

        // Listener para el botón de salir
        salirButton.addActionListener(e -> onCancel());

        // Listeners para los botones de generación de reportes
        reporte1Button.addActionListener(e -> {
            try {
                textArea1.setText(amountReport.GenerateReportByAmountCollectedByClassroom("Monto Recaudado por Aula.txt", ClassroomList));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error por IOExcepción", ex.getMessage(), JOptionPane.WARNING_MESSAGE);
            }
        });

        reporte2Button.addActionListener(e -> {
            try {
                textArea1.setText(amountReport.GenerateReportByAmountCollectedByFloor("Monto Recaudado por Piso.txt", ClassroomList));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error por IOExcepción", ex.getMessage(), JOptionPane.WARNING_MESSAGE);
            }
        });

        reporte3Button.addActionListener(e -> {
            try {
                textArea1.setText(amountReport.GenerateReportByAmountCollected("Monto Recaudado por Universidad.txt", ClassroomList));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error por IOExcepción", ex.getMessage(), JOptionPane.WARNING_MESSAGE);
            }
        });

        reporte4Button.addActionListener(e -> {
            try {
                textArea1.setText(bookingReport.GenerateReportByBooking("Reporte de Aulas ordenadas descendentemente por número de Reservas.txt", ClassroomList));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error por IOExcepción", ex.getMessage(), JOptionPane.WARNING_MESSAGE);
            }
        });

        // Alineación del área de texto en el centro
        textArea1.setAlignmentX(Component.CENTER_ALIGNMENT);
        textArea1.setAlignmentY(Component.CENTER_ALIGNMENT);

        // Configuración del cierre de la ventana
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // Acción para cerrar la ventana con ESCAPE
        mainPanel.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    /**
     * Método para cerrar la ventana de reportes.
     */
    public void onCancel() {
        dispose();
    }
}
