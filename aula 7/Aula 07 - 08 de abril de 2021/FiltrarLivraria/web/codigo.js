function criaAjax(url,dados,funcao)
{
    let ajax=new XMLHttpRequest();
    ajax.onreadystatechange=funcao;
    ajax.open("GET",url+"?"+dados,true);
    ajax.send();
}
function buscarPorTitulo()
{
    nomeTitulo=document.getElementById("titulo").value;
    criaAjax("http://localhost:8080/FiltrarLivraria/Filtrador","titulo="+nomeTitulo,mostrar);
}
function mostrar()
{
   
    if(this.readyState===4&&this.status===200)
    {
        let raiz=this.responseXML.documentElement;
        let livros=raiz.getElementsByTagName("livro");
        let texto="";
        for(let livro of livros)
        {
            texto+=pegaLivro(livro);
        }
        document.getElementById("resultado").innerHTML=texto;
    }
}
function pegaLivro(livro)
{
    let texto="<div>";
    let filhos=livro.childNodes;
    for(let filho of filhos)
    {
        if(filho.nodeType===1)
            texto+="<p><b>"+filho.nodeName+"</b>: "+
                filho.firstChild.nodeValue+"</p>";
    }
    return texto+"</div>";
}
document.getElementById("botao").onclick=buscarPorTitulo;
