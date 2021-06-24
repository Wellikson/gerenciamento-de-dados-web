function ajax(caminho, funcao) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = funcao;
    xhttp.open("GET", caminho, true)
    xhttp.send();
}
document.getElementById("botao").onclick = () => { ajax("dados.txt", executar) };
function executar() {
    if (this.readyState == 4 && this.status == 200)
        document.getElementById("res").innerHTML = this.responseText;
}
document.getElementById("btXml").onclick = () => { ajax("nota.xml", mostrar) };
function mostrar() {
    if (this.readyState == 4 && this.status == 200) {
        let doc = this.responseXML;
        let filhos = doc.documentElement.childNodes;
        let tam = filhos.length;
        for (let i = 0; i < tam; i++) {
            if (filhos[i].nodeType == 1) {
                let resXml = document.getElementById("resXml");
                let p = document.createElement("p");
                let b = document.createElement("b");
                let pontos = document.createTextNode(": ")
                let tag = document.createTextNode(filhos[i].nodeName);
                let texto = document.createTextNode(filhos[i].firstChild.nodeValue);
                b.appendChild(tag);
                p.appendChild(b);
                p.appendChild(texto)
                p.insertBefore(pontos,texto)
                resXml.appendChild(p)
            }
        }
    }
}
