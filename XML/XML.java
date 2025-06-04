package XML;

import DomainClases.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Clase que maneja la carga de datos desde un archivo XML a un TreeSet de aulas.
 */
public class XML {

    /**
     * Carga datos desde un archivo XML a un TreeSet de aulas.
     *
     * @param ClassroomList TreeSet donde se almacenarán las aulas cargadas.
     * @throws NumberFormatException Si ocurre un error al intentar convertir un número.
     * @throws NullPointerException Si se produce una referencia nula durante el procesamiento.
     * @throws SAXException Si ocurre un error durante el parseo SAX del archivo XML.
     * @throws IOException Si ocurre un error de entrada/salida al leer el archivo XML.
     * @throws ParserConfigurationException Si hay errores de configuración en el parser XML.
     * @throws DateTimeParseException Si hay errores al parsear fechas desde el archivo XML.
     */
    public void XmlLoad(TreeSet<Classroom> ClassroomList)
            throws NumberFormatException, NullPointerException, SAXException, IOException,
            ParserConfigurationException, DateTimeParseException {

        File file = new File("XML/DatosTP.xml");

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();

            Element elementUni = (Element) doc.getFirstChild();

            // Almacenar nodos y la cantidad
            NodeList nList = elementUni.getElementsByTagName("Aula");
            System.out.println("Número de aulas: " + nList.getLength());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");

            for (int i = 0; i < nList.getLength(); i++) {
                // Tomo un aula
                Classroom classroom;
                Node nNodo = nList.item(i);
                if (nNodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementAula = (Element) nNodo;

                    // ID del aula
                    String strAula = elementAula.getElementsByTagName("Id").item(0).getTextContent();
                    int idAula = Integer.parseInt(strAula);

                    // Capacidad máxima
                    String strCapacidadMax = elementAula.getElementsByTagName("CapacidadMáxima").item(0).getTextContent();
                    int CapacidadMax = Integer.parseInt(strCapacidadMax);

                    // Lista de reservas
                    ArrayList<Booking> BookingList = new ArrayList<>();

                    NodeList rList = elementAula.getElementsByTagName("Reserva");
                    for (int j = 0; j < rList.getLength(); j++) {
                        Booking booking;
                        Node nodeRes = rList.item(j);
                        if (nodeRes.getNodeType() == Node.ELEMENT_NODE) {
                            Element elementRes = (Element) nodeRes;

                            // Código de reserva
                            String CodReservaStr = elementRes.getElementsByTagName("Codigo").item(0).getTextContent();
                            int CodReserva = Integer.parseInt(CodReservaStr);

                            // Fecha de reserva
                            String FechaReservaStr = elementRes.getElementsByTagName("FechaDeReserva").item(0).getTextContent();
                            LocalDateTime FechaReserva = LocalDateTime.parse(FechaReservaStr, formatter);
                            if (FechaReserva.isAfter(LocalDateTime.now()))
                                throw new RuntimeException("La fecha es invalida ya que es un valor posterior a la fecha de hoy");

                            // Fecha de inicio
                            String FechaInicioStr = elementRes.getElementsByTagName("ComienzoDeReserva").item(0).getTextContent();
                            LocalDateTime FechaInicio = LocalDateTime.parse(FechaInicioStr, formatter);

                            // Fecha de fin
                            String FechaFinStr = elementRes.getElementsByTagName("FinDeReserva").item(0).getTextContent();
                            LocalDateTime FechaFin = LocalDateTime.parse(FechaFinStr, formatter);
                            if (FechaInicio.isBefore(LocalDateTime.now())||FechaInicio.isEqual(LocalDateTime.now()))
                                throw new RuntimeException("Fecha de inicio menor o igual a la actual");
                            else if (FechaFin.isBefore(FechaInicio)||FechaFin.isEqual(FechaInicio))
                                throw new RuntimeException("Fecha de fin menor o igual a la inicial");

                            // Organización
                            Element elementOrg = (Element) elementRes.getElementsByTagName("Organización").item(0);

                            NamedNodeMap tipoNodeMap = elementOrg.getAttributes();
                            String tipoStr = tipoNodeMap.getNamedItem("id").getNodeValue();
                            int tipo = Integer.parseInt(tipoStr);
                            String CodOrg = elementOrg.getElementsByTagName("CodigoOrg").item(0).getTextContent();
                            Organization organization = null;
                            switch (tipo) {
                                case 1 -> {
                                    String nomAsign = elementOrg.getElementsByTagName("Nombre").item(0).getTextContent();
                                    String comCusrsada = elementOrg.getElementsByTagName("ComienzoDeCusada").item(0).getTextContent();
                                    LocalDateTime comienzoCursada = LocalDateTime.parse(comCusrsada, formatter);
                                    String finCursada = elementOrg.getElementsByTagName("FinDeCursada").item(0).getTextContent();
                                    LocalDateTime finalCursada = LocalDateTime.parse(finCursada, formatter);
                                    String comClase = elementOrg.getElementsByTagName("ComienzoDeClase").item(0).getTextContent();
                                    LocalDateTime comienzoClase = LocalDateTime.parse(comClase, formatter);
                                    String finClase = elementOrg.getElementsByTagName("FinDeClase").item(0).getTextContent();
                                    LocalDateTime finalClase = LocalDateTime.parse(finClase, formatter);
                                    if (comienzoClase.isBefore(LocalDateTime.now())||comienzoClase.isEqual(LocalDateTime.now()))
                                        throw new RuntimeException("Fecha de inicio menor o igual a la actual");
                                    else if (finalClase.isBefore(comienzoClase)||finalClase.isEqual(comienzoClase))
                                        throw new RuntimeException("Fecha de fin menor o igual a la inicial");
                                    byte dia = Byte.parseByte(elementOrg.getElementsByTagName("DiaDeLaSemana").item(0).getTextContent());
                                    int cantAlumnos = Integer.parseInt(elementOrg.getElementsByTagName("CantidadDeAlumnos").item(0).getTextContent());
                                    organization = new Subject(CodOrg, nomAsign, comienzoCursada, finalCursada, comienzoClase, finalClase, dia, cantAlumnos);
                                }
                                case 2 -> {
                                    String DescripcionOrg = elementOrg.getElementsByTagName("Descipcion").item(0).getTextContent();
                                    int EstudiantesInscriptosStr = Integer.parseInt(elementOrg.getElementsByTagName("EstudiantesInscriptos").item(0).getTextContent());
                                    int CantDeClasesStr = Integer.parseInt(elementOrg.getElementsByTagName("CantidadDeClases").item(0).getTextContent());
                                    double CostoPorEstudiante = Double.parseDouble(elementOrg.getElementsByTagName("CostoPorEstudiante").item(0).getTextContent());
                                    organization = new ExtensionCourse(CodOrg, DescripcionOrg, EstudiantesInscriptosStr, CantDeClasesStr, CostoPorEstudiante);
                                }
                                case 3 -> {
                                    String DescripcionOrg = elementOrg.getElementsByTagName("Descipcion").item(0).getTextContent();
                                    int CantidadMaximaDeEstudiantesStr = Integer.parseInt(elementOrg.getElementsByTagName("CantidadMaximaDeEstudiantes").item(0).getTextContent());
                                    organization = new Event(CodOrg, DescripcionOrg, CantidadMaximaDeEstudiantesStr);
                                }
                                case 4 -> {
                                    String DescripcionOrg = elementOrg.getElementsByTagName("Descipcion").item(0).getTextContent();
                                    int CantidadMaximaDeEstudiantesStr = Integer.parseInt(elementOrg.getElementsByTagName("CantidadMaximaDeEstudiantes").item(0).getTextContent());
                                    String nomOrg = elementOrg.getElementsByTagName("Nombre").item(0).getTextContent();
                                    double CostoAlquiler = Double.parseDouble(elementOrg.getElementsByTagName("CostoAlquiler").item(0).getTextContent());
                                    organization = new ExternEvent(CodOrg, DescripcionOrg, CantidadMaximaDeEstudiantesStr, CostoAlquiler, nomOrg);
                                }
                            }
                            booking = new Booking(CodReserva, FechaReserva, FechaInicio, FechaFin, organization);
                            BookingList.add(booking);
                        }
                    }
                    classroom = new Classroom(idAula, CapacidadMax, BookingList);
                    ClassroomList.add(classroom);
                }
            }
        } catch (ParserConfigurationException parserConfigurationException) {
            JOptionPane.showMessageDialog(null,
                    "Error al leer el archivo XML. Por favor, revise el archivo e ingrese" +
                            " los datos correctos.", parserConfigurationException.getMessage(), JOptionPane.ERROR_MESSAGE);
        } catch (SAXException saxException) {
            JOptionPane.showMessageDialog(null,
                    "Error al leer el archivo XML. SAXException", saxException.getMessage(), JOptionPane.ERROR_MESSAGE);
        } catch (IOException ioException) {
            JOptionPane.showMessageDialog(null,
                    "Error al leer el archivo XML. IOException", ioException.getMessage(), JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(null,
                    "Error al leer el archivo XML. Algún valor numérico no es número", numberFormatException.getMessage(), JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException dateTimeParseException) {
            JOptionPane.showMessageDialog(null,
                    "Error al leer el archivo XML. Alguna fecha tiene formato equivocado", dateTimeParseException.getMessage(), JOptionPane.ERROR_MESSAGE);
        } catch (NullPointerException nullPointerException) {
            JOptionPane.showMessageDialog(null, "Error al recuperar la etiqueta", "Error en XML", JOptionPane.WARNING_MESSAGE);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}
