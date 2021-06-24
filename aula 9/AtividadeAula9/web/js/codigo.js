/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
     
function criaAjax(url, dados, funcao){
    let ajax = new XMLHttpRequest();
    ajax.onreadystatechange = funcao;
    ajax.open("GET", url + "?" + dados, true);
    ajax.send();
}
function ajax(url, funcao){
    let ajax = new XMLHttpRequest();
    ajax.onreadystatechange = funcao;
    ajax.open("GET", url, true);
    ajax.send();
}

function buscarPorParametro(){
//    criaAjax("http://localhost:8080/AtividadeAula9/Filtrador", "titulo=Torta de Frango", mostrar);
     var descendentes = document.querySelectorAll("a");
    for (var i = 0; i < descendentes.length; i++) {
        descendentes[i].addEventListener("click", function (e) {
            criaAjax("http://localhost:8080/AtividadeAula9/Filtrador", "titulo="+this.innerHTML, mostrar);
        });
    } 
}

function mostrar(){
    if (this.readyState === 4 && this.status === 200){
        let raiz = this.responseXML.documentElement;
        let receitas = raiz.getElementsByTagName("receita");
        let texto = "";

        for (let receita of receitas){
            texto += pegaReceita(receita);
        }
        document.getElementById("corpo").innerHTML = texto;
    }
}

function mostrarMenu(){

    if (this.readyState === 4 && this.status === 200){
        let raiz = this.responseXML.documentElement;
        let nomes = raiz.getElementsByTagName("nome");
        let texto = "";
        for (let nome of nomes){
            let nReceita = nome.firstChild.nodeValue;
            texto += '<a onclick="buscarPorParametro()">'+nReceita+'</a>';
        }
        document.getElementById("navegacao").innerHTML = texto;
    }
   
}


function pegaReceita(receita){
    let texto = "<div>";
    let filhos = receita.childNodes;
    
    for (let filho of filhos){
        if (filho.nodeType === 1){
            if(filho.childNodes.length <= 1){
                 texto += "<h1>" + filho.firstChild.nodeValue + "</h1>";
            }
            else{
                let netos = filho.childNodes;
                texto+="<br><h2>"+selecionarTitulo(filho.nodeName)+"</h2><br>";
               
                for(let neto of netos){
                      texto +="<ul>";
                        if(neto.nodeType === 1){
                           if(neto.parentNode.nodeName ==="ilustracao"){
                             texto += "<img src=img/"+neto.firstChild.nodeValue+" alt="+neto.firstChild.nodeValue+">";
                           }else
                            texto += "<li>" + neto.firstChild.nodeValue + "</li>";
                       }
                     texto+="</ul>";
                }
              
            }

        }
        
    }
    return texto + "</div>";
}
function selecionarTitulo(titulo){
   let texto;
    if(titulo ==="ingredientes"){
        texto ="Ingredientes";
    }else if(titulo ==="modoPreparo"){
        texto ="Modo de Preparo";
    }else if(titulo ==="ilustracao"){
        texto ="Fotos";
    }
    
    return texto;
}
ajax("http://localhost:8080/AtividadeAula9/NReceitas", mostrarMenu);
criaAjax("http://localhost:8080/AtividadeAula9/Filtrador", "titulo=" + "Bolo de Milho sem Farinha", mostrar);
buscarPorParametro();