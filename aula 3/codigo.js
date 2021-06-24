document.getElementById("botao").onclick=()=>
{
    let texto = document.getElementsByTagName("input")[0].value;
    let lista = document.getElementById("lista");
    let li = document.createElement("li");
    let noTexto = document.createTextNode(texto);
    li.appendChild(noTexto);
    lista.appendChild(li);

    let botao = document.createElement("input");
    botao.setAttribute("type","button");
    botao.setAttribute("value","remover")
    botao.onclick = remover;
    li.appendChild(botao)

    let botaoTroca = document.createElement("input");
    botaoTroca.setAttribute("type","button");
    botaoTroca.setAttribute("value","trocar")
    botaoTroca.onclick = trocar;
    li.appendChild(botaoTroca)

    let caixa2 = document.getElementsByTagName("input")[2];
    let atributo = caixa2.getAttributeNode("hidden");
    caixa2.removeAttributeNode(atributo);

}
let remover = function ()
{
    let pai = this.parentNode;
    pai.parentNode.removeChild(pai)
}
let trocar = function ()
{
    let texto = document.getElementsByTagName("input")[2].value;
    let pai = this.parentNode;
    // pai.childNodes[0].nodeValue=texto;
    let t = document.createTextNode(texto)
    pai.replaceChild(t,pai.childNodes[0]);
}