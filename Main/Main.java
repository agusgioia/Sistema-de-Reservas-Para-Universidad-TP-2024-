package Main;

import DomainClases.Classroom;
import GIU.Inicio;
import XML.Deserializador;
import XML.Serializador;
import XML.XML;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.TreeSet;

/**
 * Clase principal que contiene el método main para iniciar la aplicación de gestión de aulas.
 */
public class Main {

    /**
     * Método principal que inicia la aplicación de gestión de aulas.
     * @param args Los argumentos de línea de comandos (no se utilizan en esta aplicación).
     * @throws SAXException Sí ocurre un error durante el procesamiento XML (lectura).
     * @throws IOException Sí ocurre un error de entrada/salida durante la lectura del archivo serializado o XML.
     * @throws ParserConfigurationException Si ocurre un error de configuración del parser XML.
     */
    public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
        // Creación de instancias y configuración inicial
        XML xml = new XML();
        String RutaArchivoSerializado = "Archivo.ser";
        TreeSet<Classroom> ClassroomList = new TreeSet<>();
        File ArchivoSerializado = new File(RutaArchivoSerializado);

        // Verificar si existe un archivo serializado previamente
        if (ArchivoSerializado.exists()) {
            ClassroomList = Deserializador.deserializarTreeSet(RutaArchivoSerializado);
        } else {
            // Cargar datos desde un archivo XML si no hay archivo serializado
            xml.XmlLoad(ClassroomList);
            // Serializar el conjunto de aulas para futuras ejecuciones
            Serializador.serializarTreeSet(ClassroomList, RutaArchivoSerializado);
        }

        // Configuración y visualización de la interfaz gráfica de inicio
        JFrame frame = new JFrame("Inicio");
        frame.setContentPane(new Inicio(ClassroomList).getPrincipalPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
