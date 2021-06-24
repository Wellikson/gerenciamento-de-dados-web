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
import org.xml.sax.SAXException;

/**
 *
 * @author vinicius
 */
public class FiltraLivraria {
    private Document doc;
    public FiltraLivraria(String caminho) throws ParserConfigurationException, SAXException, IOException
    {
        DocumentBuilderFactory fabrica=DocumentBuilderFactory.newInstance();
        DocumentBuilder construtor=fabrica.newDocumentBuilder();
        doc=construtor.parse(caminho);
    }
    private String serealizar(Node no) throws TransformerException
    {
        TransformerFactory fabrica=TransformerFactory.newInstance();
        Transformer transformador=fabrica.newTransformer();
        DOMSource fonte=new DOMSource(no);
        ByteArrayOutputStream fluxo=new ByteArrayOutputStream();
        StreamResult saida=new StreamResult(fluxo);
        transformador.transform(fonte, saida);
        return fluxo.toString();
    }
    public String pegarPorTitulo(String titulo) throws TransformerException
    {
        return pegarPorTag("titulo", titulo);
                
    }
    public boolean existeAutor(Element noLivro,String autor)
    {
        NodeList noAutores=noLivro.getElementsByTagName("autor");
        int tam=noAutores.getLength();
        for(int i=0;i<tam;i++)
        {
            Node noAutor=noAutores.item(i);
            if(noAutor.getFirstChild().getNodeValue().equals(autor))
            {
                return true;
            }
        }
        return false;
    }
    public String pegarPorAutor(String autor) throws TransformerException
    {
        NodeList noLivros=doc.getElementsByTagName("livro");
        int tam=noLivros.getLength();
        for(int i=tam-1;i>=0;i--)
        {
            Element noLivro=(Element)noLivros.item(i);
            if(!existeAutor(noLivro,autor))
            {
                noLivro.getParentNode().removeChild(noLivro);
            }
        }
        return serealizar(doc);
    }
    private String pegarPorTag(String tag,String valor) throws TransformerException
    {
        Node noLivro=null;
        NodeList filhos=doc.getElementsByTagName(tag);//autor
        int tam=filhos.getLength();
        for(int i=tam-1;i>=0;i--)
        {
            Node noFilho=filhos.item(i);
            if(noFilho!=null)
            {
                if(!noFilho.getFirstChild().getNodeValue().equals(valor))//James Linn
                {
                    noLivro=noFilho.getParentNode();
                    noLivro.getParentNode().removeChild(noLivro);
                }
            }
        }
        return serealizar(doc);
    }
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        FiltraLivraria filtro=new FiltraLivraria("src/java/XML/livraria.xml");
        System.out.println(filtro.pegarPorAutor("xyz"));        
    }
}
