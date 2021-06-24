package XML;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 *
 * @author wellikson
 */
public class FiltraLivraria {

    private Document doc;

    public FiltraLivraria(String caminho) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
        DocumentBuilder construtor = fabrica.newDocumentBuilder();
        doc = construtor.parse(caminho);
    }

    private String serealizar(Node no) throws TransformerException {
        TransformerFactory fabrica = TransformerFactory.newInstance();
        Transformer transformador = fabrica.newTransformer();
        DOMSource fonte = new DOMSource(no);
        ByteArrayOutputStream fluxo = new ByteArrayOutputStream();
        StreamResult saida = new StreamResult(fluxo);
        transformador.transform(fonte, saida);
        return fluxo.toString();
    }

    public String pegarPorTitulo(String titulo) throws TransformerException, ParserConfigurationException {
        return pegarPorTag("titulo", titulo);

    }

    public boolean existeAutor(Element noLivro, String autor) {
        NodeList noAutores = noLivro.getElementsByTagName("autor");
        int tam = noAutores.getLength();
        for (int i = 0; i < tam; i++) {
            Node noAutor = noAutores.item(i);
            if (noAutor.getFirstChild().getNodeValue().equals(autor)) {
                return true;
            }
        }
        return false;
    }

    private Document criaDocumentoLivraria() throws ParserConfigurationException {
        Document doc2;
        DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
        DocumentBuilder construtor = fabrica.newDocumentBuilder();
        doc2 = construtor.newDocument();
        Element livraria = doc2.createElement("livraria");
        doc2.appendChild(livraria);
        return doc2;
    }

    private void copiaParaoDocumento(Node original, Document doc2) {
        NodeList filhos = original.getChildNodes();
        int tam = filhos.getLength();
        Element novoLivro = doc2.createElement("livro");
        for (int i = 0; i < tam; i++) {
            Node filho = filhos.item(i);
            if (filho.getNodeType() == Node.ELEMENT_NODE) {
                Element novaTag = doc2.createElement(filho.getNodeName());
                Text novoTexto = doc2.createTextNode(filho.getFirstChild().getNodeValue());
                novaTag.appendChild(novoTexto);
                novoLivro.appendChild(novaTag);
            }
        }
        doc2.getDocumentElement().appendChild(novoLivro);
    }

    public String pegarPorAutor(String autor) throws TransformerException, ParserConfigurationException {
        NodeList noLivros = doc.getElementsByTagName("livro");
        int tam = noLivros.getLength();
        Document doc2 = criaDocumentoLivraria();
        for (int i = tam - 1; i >= 0; i--) {
            Element noLivro = (Element) noLivros.item(i);
            if (existeAutor(noLivro, autor)) {
                copiaParaoDocumento(noLivro, doc2);
            }
        }
        return serealizar(doc2);
    }

    private String pegarPorTag(String tag, String valor) throws TransformerException, ParserConfigurationException {
        Node noLivro = null;
        Document doc2 = criaDocumentoLivraria();
        NodeList filhos = doc.getElementsByTagName(tag);//autor
        int tam = filhos.getLength();
        for (int i = tam - 1; i >= 0; i--) {
            Node noFilho = filhos.item(i);
            if (noFilho != null) {
                if (noFilho.getFirstChild().getNodeValue().equals(valor)) {
                    noLivro = noFilho.getParentNode();
                    copiaParaoDocumento(noLivro, doc2);
                }
            }
        }
        return serealizar(doc2);
    }

    public String mostrarXML() throws TransformerException {
        return serealizar(doc);
    }

    public String pegarPorCategoria(String categoria) throws TransformerException, ParserConfigurationException {
        Document doc2 = criaDocumentoLivraria();
        NodeList noLivros = doc.getElementsByTagName("livro");
        int tam = noLivros.getLength();
        for (int i = tam - 1; i >= 0; i--) {
            Element noLivro = (Element) noLivros.item(i);
            if (noLivro.getAttribute("categoria").equals(categoria)) {
                copiaParaoDocumento(noLivro, doc2);
            }
        }
        return serealizar(doc2);
    }

    public String pegarPorPreço(String condicao, String preco) throws ParserConfigurationException, TransformerException {
        Node noLivro = null;
        Document doc2 = criaDocumentoLivraria();
        NodeList filhos = doc.getElementsByTagName("preco");//autor
        int tam = filhos.getLength();
        for (int i = tam - 1; i >= 0; i--) {
            Node noFilho = filhos.item(i);
            if (condicao.equals("maior")) {
                double vlr = Double.valueOf(noFilho.getFirstChild().getNodeValue());
                if (vlr > Double.valueOf(preco)) {
                    noLivro = noFilho.getParentNode();
                    copiaParaoDocumento(noLivro, doc2);
                }
            }
            if (condicao.equals("menor")) {
                double vlr = Double.valueOf(noFilho.getFirstChild().getNodeValue());
                if (vlr < Double.valueOf(preco)) {
                    noLivro = noFilho.getParentNode();
                    copiaParaoDocumento(noLivro, doc2);
                }
            }

        }
        return serealizar(doc2);
    }

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        FiltraLivraria filtro = new FiltraLivraria("src/java/XML/livraria.xml");
//System.out.println(filtro.pegarPorAutor("Jose"));
//        System.out.println(filtro.pegarPorCategoria("web"));
        String vlr ="menor50";
        System.out.println(filtro.pegarPorPreço(vlr.substring(0,5),vlr.substring(5)));
    }
}
