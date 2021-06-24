
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author wellikson
 */
public class MostrarXML {

    Document doc;

    public MostrarXML(String caminho) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(caminho);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.out.println(ex);
        }
    }

    public String mostrarDados() {
        Element raiz = doc.getDocumentElement();
        NodeList filhos = raiz.getChildNodes();
        String texto = "";
        int tam = filhos.getLength();
        for (int i = 0; i < tam; i++) {
            NodeList netos = filhos.item(i).getChildNodes();
            if (filhos.item(i).getNodeType() == Node.ELEMENT_NODE) {
                for (int y = 0; y < netos.getLength(); y++) {
                    if (netos.item(y).getNodeType() == Node.ELEMENT_NODE) {
                        Node neto = netos.item(y);
                        texto += "<b>" + neto.getNodeName() + "</b>: ";
                        texto += neto.getFirstChild().getNodeValue() + "\n <br>";
                    }
                }
                texto += "<hr>";
            }
        }
        return texto;
    }
    public static void main(String[] args) {
        MostrarXML mostrar = new MostrarXML("web/WEB-INF/livraria.xml");
        System.out.println(mostrar.mostrarDados());
    }
}
