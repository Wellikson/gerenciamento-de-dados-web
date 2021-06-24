function ajax(caminho, funcao) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = funcao;
    xhttp.open("GET", caminho, true);
    xhttp.send();
}
let doc;
let pgcorpo = document.getElementById("corpo");
function iniciarMenu() {
    if (this.readyState === 4 && this.status === 200) {
        doc = this.responseXML;
        let listas = doc.getElementsByTagName("lista");
        let corpo = "";
        for (let l = 0; l < listas.length; l++) {
            corpo += criaListaItens(listas[l]);
        }
        document.getElementById("corpo").innerHTML = corpo;
        //        mostrar(listas);
    }
}


// function mostrar(listas) {
//     let corpo = "";
//     for (let l = 0; l < listas.length; l++) {
//         corpo += criaListaItens(listas[l]);
//     }
//     document.getElementById("corpo").innerHTML = corpo;
// }
function pegaTitulo(lista) {
    return lista.getElementsByTagName("titulo")[0].firstChild.nodeValue;
}
function mostrarBusca(id) {
    let corpo = "";
    let listas = doc.getElementsByTagName("lista");
    for (let lista of listas) {
        if (lista.getAttribute("id") == id) {
            corpo += criaListaItens(lista);
        }
    }
    return corpo;
}

function criaListaItens(lista) {
    let itens = lista.getElementsByTagName("item");
    let textoNaoFeito = "", textoFeito = "", corpo = "";
    for (let item of itens) {
        let texto = item.firstChild.nodeValue;
        if (item.getAttribute("prioridade") == "sim")
            texto = `<b>${texto}</b>`;
        if (item.getAttribute("feito") == "sim")
            textoFeito += `<li><del>${texto}</del></li>`;
        else
            textoNaoFeito += `<li>${texto}</li>`;
    }
    return corpo = `<h1>${pegaTitulo(lista)}</h1>
            <ul id="listanaofeito">${textoNaoFeito}</ul>`
        + `<ul id="listafeito">${textoFeito}</ul>`;
}

function filtros() {

    var select = document.querySelector('select');
    var option = select.children[select.selectedIndex];
    var texto = option.textContent;

    var busca = document.getElementById('busca').value.toString().toLowerCase();
    let listas = doc.getElementsByTagName("lista")[0].parentNode;

    if (busca === "não") {
        busca = "nao";
    }
    if (!busca == "") {
        // PESQUISA POR TITULO
        if (texto == "Titulo") {
            let titulos = listas.getElementsByTagName("titulo");
            let corpo = "";
            for (i = 0; i < titulos.length; i++) {
                let textoTitulo = titulos[i].firstChild.textContent;
                if (textoTitulo.toString().substring(0, busca.length).toUpperCase()
                    == busca.toString().toUpperCase()) {
                    corpo += mostrarBusca(titulos[i].parentNode.getAttribute("id"));
                }
                ;
            }
            pgcorpo.innerHTML = corpo;
        } 
        // PESQUISA POR ITEM FEITO OU NAO
        else if (texto == "Feito" && (busca == "sim" || busca == "nao")) {
            itens = listas.getElementsByTagName("item");
            let textoNaoFeito = "", textoFeito = "";
            for (i = 0; i < itens.length; i++) {
                let texto = itens[i].firstChild.nodeValue;
                if (busca == "sim") {
                    if (itens[i].getAttribute("feito") == busca) {
                        textoFeito += `<li><del>${texto}</del></li>`;
                    }
                } else if (itens[i].getAttribute("feito") == null ||
                    itens[i].getAttribute("feito") == busca) {
                    textoNaoFeito += `<li>${texto}</li>`;
                }
            }
            pgcorpo.innerHTML = `<ul id="listanaofeito">${textoNaoFeito}</ul>`
                + `<ul id="listafeito">${textoFeito}</ul>`;
        } 
        // PESQUISA POR PRIORIDADE
        else if (texto == "Prioridade" && (busca == "sim" || busca == "nao")) {
            prioridade = listas.getElementsByTagName("item");
            let textoNaoFeito = "", textoFeito = "";
            for (i = 0; i < prioridade.length; i++) {
                let texto = prioridade[i].firstChild.nodeValue;
                if (busca === "sim") {
                    if (prioridade[i].getAttribute("prioridade") === busca) {
                        if (prioridade[i].getAttribute("feito") === "sim") {
                            textoFeito += `<li><del><b>${texto}</b></del></li>`;
                        } else {
                            textoNaoFeito += `<li><b>${texto}</b></li>`;
                        }
                    }
                } else if (busca === "nao") {
                    if (prioridade[i].getAttribute("prioridade") === busca ||
                        prioridade[i].getAttribute("prioridade") === null) {
                        if (prioridade[i].getAttribute("feito") === "sim") {
                            textoFeito += `<li><del>${texto}</del></li>`;
                        } else {
                            textoNaoFeito += `<li>${texto}</li>`;
                        }
                    }
                }
            }
            pgcorpo.innerHTML = `<ul id="listanaofeito">${textoNaoFeito}</ul>`
                + `<ul id="listafeito">${textoFeito}</ul>`;
        }
    } else if (texto == "Tudo") {
        ajax("listas.xml", iniciarMenu);
    }
}
document.getElementById("botao").onclick = () => {
    ajax("listas.xml", filtros);
};
ajax("listas.xml", iniciarMenu);