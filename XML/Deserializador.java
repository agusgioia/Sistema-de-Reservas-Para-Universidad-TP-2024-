package XML;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.TreeSet;

/**
 * Clase que se encarga de deserializar un TreeSet desde un archivo.
 */
public class Deserializador {

    /**
     * Deserializa un TreeSet desde un archivo especificado.
     *
     * @param <T> Tipo de elementos contenidos en el TreeSet.
     * @param rutaArchivo Ruta del archivo desde donde se realizará la deserialización.
     * @return TreeSet deserializado, o null si ocurre un error durante la deserialización.
     */
    public static <T> TreeSet<T> deserializarTreeSet(String rutaArchivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            @SuppressWarnings("unchecked")
            TreeSet<T> treeSet = (TreeSet<T>) ois.readObject();
            System.out.println("TreeSet deserializado correctamente desde " + rutaArchivo);
            return treeSet;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
