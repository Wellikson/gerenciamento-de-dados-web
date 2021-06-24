/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class FiltroReceita {

    private Document doc;

    public FiltroReceita(String caminho) throws ParserConfigurationException, SAXException, IOException {
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

    private Document criaReceita() throws ParserConfigurationException {
        Document doc2;
        DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
        DocumentBuilder construtor = fabrica.newDocumentBuilder();
        doc2 = construtor.newDocument();
        Element receitas = doc2.createElement("receitas");
        doc2.appendChild(receitas);
        return doc2;
    }

    private void copiaReceita(Node original, Document doc2) {
        NodeList filhos = original.getChildNodes();
        int tam = filhos.getLength();
        Element nvReceita = doc2.createElement("receita");
        for (int i = 0; i < tam; i++) {
            Node filho = filhos.item(i);
            if (filho.getNodeType() == Node.ELEMENT_NODE) {
                Element novaTag = doc2.createElement(filho.getNodeName());
                Text novoTexto = doc2.createTextNode(filho.getFirstChild().getNodeValue());
                novaTag.appendChild(novoTexto);
                for (int y = 0; y < filho.getChildNodes().getLength(); y++) {
                    Node neto = filho.getChildNodes().item(y);
                    if (neto.getNodeType() == Node.ELEMENT_NODE) {
                        Element novaTagNeto = doc2.createElement(neto.getNodeName());
                        Text novoTextoNeto = doc2.createTextNode(neto.getFirstChild().getNodeValue());
                        novaTagNeto.appendChild(novoTextoNeto);
                        novaTag.appendChild(novaTagNeto);
                    }
                }
                nvReceita.appendChild(novaTag);
            }
        }
        doc2.getDocumentElement().appendChild(nvReceita);
    }

    public void copiarNomes(Node original, Document doc2) {
        NodeList filhos = original.getChildNodes();
        Element novaTag = doc2.createElement("nome");
        int tam = filhos.getLength();
        for (int i = 0; i < tam; i++) {
            Node filho = filhos.item(i);
            if (filho.getNodeType() == Node.ELEMENT_NODE) {
                Text novoTexto = doc2.createTextNode(filho.getFirstChild().getNodeValue());
                novaTag.appendChild(novoTexto);
            }
        }
        doc2.getDocumentElement().appendChild(novaTag);
    }

    public boolean existeReceita(Element noReceita, String nome) {
        NodeList noReceitas = noReceita.getElementsByTagName("nome");
        int tam = noReceitas.getLength();
        for (int i = 0; i < tam; i++) {
            Node noNome = noReceitas.item(i);
            if (noNome.getFirstChild().getNodeValue().equals(nome)) {
                return true;
            }
        }
        return false;
    }

    public String pegarPorReceita(String nome) throws TransformerException, ParserConfigurationException {
        NodeList noReceitas = doc.getElementsByTagName("receita");
        int tam = noReceitas.getLength();
        Document doc2 = criaReceita();
        for (int i = tam - 1; i >= 0; i--) {
            Element noReceita = (Element) noReceitas.item(i);
            if (existeReceita(noReceita, nome)) {
                copiaReceita(noReceita, doc2);
            }
        }
        return serealizar(doc2);
    }

    public String pegarNomes() throws TransformerException, ParserConfigurationException {
        NodeList noReceitas = doc.getElementsByTagName("receita");
        int tam = noReceitas.getLength();
        Document doc2 = criaReceita();
        for (int i = 0; i < tam;  i++) {
            Element noReceita = (Element) noReceitas.item(i);
            if (noReceita.getNodeType() == Node.ELEMENT_NODE) {
                copiarNomes(noReceita, doc2);
            }
        }
        return serealizar(doc2);
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        FiltroReceita filtro = new FiltroReceita("src/java/XML/receitas.xml");
        System.out.println(filtro.pegarNomes());
    }
}
