function criaAjax(url, dados, funcao)
{
    let ajax = new XMLHttpRequest();
    ajax.onreadystatechange = funcao;
    ajax.open("GET", url + "?" + dados, true);
    ajax.send();
}
function ajax(url, funcao)
{
    let ajax = new XMLHttpRequest();
    ajax.onreadystatechange = funcao;
    ajax.open("GET", url, true);
    ajax.send();
}
function busca()
{
    var select = document.querySelector('select');
    var option = select.children[select.selectedIndex];
    var texto = option.textContent;
    if (texto === "Titulo") {
        buscarPorParametro("titulo");
    } else if (texto === "Autor") {
        buscarPorParametro("autor");
    } else if (texto === "Preço maior que") {
        buscarPorParametro("maior");
    } else if (texto === "Preço menor que") {
        buscarPorParametro("menor");
    }
}
function buscarPorParametro(paramentro)
{
    let valor = document.getElementById("busca").value;
    if (paramentro === "menor" || paramentro === "maior") {
        criaAjax("http://localhost:8080/FiltroLivraria2/Filtrador", paramentro +"="+paramentro + valor, mostrar);

    } else {
        criaAjax("http://localhost:8080/FiltroLivraria2/Filtrador", paramentro + "=" + valor, mostrar);

    }
//    alert(paramentro+ valor);
}
function mostrar()
{

    if (this.readyState === 4 && this.status === 200)
    {
        let raiz = this.responseXML.documentElement;
        let livros = raiz.getElementsByTagName("livro");
        let texto = "";
        for (let livro of livros)
        {
            texto += pegaLivro(livro);
        }
        document.getElementById("resultado").innerHTML = texto;
    }
}
function pegaLivro(livro)
{
    let texto = "<div>";
    let filhos = livro.childNodes;
    for (let filho of filhos)
    {
        if (filho.nodeType === 1)
            texto += "<p><b>" + filho.nodeName + "</b>: " +
                    filho.firstChild.nodeValue + "</p>";
    }
    return texto + "</div>";
}
function testRadiobuton() {
    var radios = document.getElementsByTagName('input');
    var value;
    for (var i = 0; i < radios.length; i++) {
        if (radios[i].type === 'radio' && radios[i].checked) {
            value = radios[i].value;
        }
    }
    if (value !== "all") {
        criaAjax("http://localhost:8080/FiltroLivraria2/Filtrador", "categoria=" + value, mostrar);
    } else {
        ajax("http://localhost:8080/FiltroLivraria2/Livraria", mostrar);
    }
}
document.getElementById("botao").onclick = busca;
ajax("http://localhost:8080/FiltroLivraria2/Livraria", mostrar);