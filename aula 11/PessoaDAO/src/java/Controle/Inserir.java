/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import DAO.ErroBancoException;
import DAO.PessoaDaoClasse;
import Modelo.Pessoa;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author wellikson
 */
@WebServlet(name = "Inserir", urlPatterns = {"/inserir"})
public class Inserir extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Inserir</title>");
            out.println("</head>");
            out.println("<body>");

            String nome = request.getParameter("nome");
            int idade = Integer.parseInt(request.getParameter("idade"));
            String codigo = request.getParameter("codigo");
            Pessoa p = new Pessoa();
            p.setIdade(idade);
            p.setNome(nome);

            PessoaDaoClasse dao = null;
            if (codigo == null) {
                try {
                    dao = new PessoaDaoClasse();
                    dao.criarPessoa(p);
                    out.print("Inserido com sucesso");
                } catch (ErroBancoException | SQLException ex) {
                    out.print("Erro ao tentar inserir");
                }
            }else{
                try {
                    p.setCodigo(Integer.parseInt(codigo));
                    dao = new PessoaDaoClasse();
                    dao.editarPessoa(p);
                    out.print("Pessoa Atualizada com Sucesso");
                } catch (ErroBancoException | SQLException ex) {
                    out.print("Erro ao tentar inserir");
                }
            }
            List<Pessoa> pessoas = null;
            try {
                pessoas = dao.pegaPessoas();
                out.print("<ul>");
                for (int i = 0; i < pessoas.size(); i++) {
                    Pessoa p1 = pessoas.get(i);
                    out.println("<li>" + p1.getNome() + " " + p1.getIdade() +" "+
                            "<a href='http://localhost:8080/PessoaDAO/servletEditar?codigo="+ p1.getCodigo()+"'>Editar</a></li>");
                }
                out.print("</ul>");
            } catch (SQLException ex) {
                out.print("<p>Erro ao tentar ler os dados</p>");
            }

            out.println("</body>");
            out.println("</html>");
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
