/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import XML.FiltraLivraria;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author wellikson
 */
@WebServlet(name = "Filtrador", urlPatterns = {"/Filtrador"})
public class Filtrador extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/xml;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");
        String categoria = request.getParameter("categoria");
        String menor = request.getParameter("menor");
        String maior = request.getParameter("maior");
        String condicao = null;
        if(menor!=null){
            condicao=menor;
        }else if(maior!=null){
            condicao=maior;
        }
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String caminho = getServletContext().getRealPath("/WEB-INF/livraria.xml");
            try {
                FiltraLivraria filtro = new FiltraLivraria(caminho);
                if (titulo != null) {
                    out.println(filtro.pegarPorTitulo(titulo));
                } else if (autor != null) {
                    out.println(filtro.pegarPorAutor(autor));
                } else if (categoria != null){
                    out.print(filtro.pegarPorCategoria(categoria));
                } else if (condicao!=null) {
                    out.print(filtro.pegarPorPreço(condicao.substring(0,5),condicao.substring(5)));
                }
            } catch (Exception ex) {
                out.println("<erro>" + ex.toString() + "</erro>");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
