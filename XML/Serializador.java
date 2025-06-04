package XML;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.TreeSet;

/**
 * Clase que se encarga de serializar un TreeSet a un archivo.
 */
public class Serializador {

    /**
     * Constructor por defecto de la clase Serializador.
     */
    public Serializador() {}

    /**
     * Serializa un TreeSet a un archivo especificado.
     *
     * @param <T> Tipo de elementos contenidos en el TreeSet.
     * @param treeSet TreeSet que se desea serializar.
     * @param rutaArchivo Ruta del archivo donde se realizará la serialización.
     */
    public static <T> void serializarTreeSet(TreeSet<T> treeSet, String rutaArchivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(treeSet);
            System.out.println("TreeSet serializado correctamente en " + rutaArchivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
