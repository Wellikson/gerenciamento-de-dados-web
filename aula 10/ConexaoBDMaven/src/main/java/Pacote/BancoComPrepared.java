/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pacote;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author wellikson
 */
@WebServlet(name = "BancoComPrepared", urlPatterns = {"/bancoP"})
public class BancoComPrepared extends HttpServlet {

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
        String apagar = request.getParameter("apagar");
//        String idade = request.getParameter("idade");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet BancoComPrepared</title>");

            out.println("</head>");
            out.println("<h1>Teste Banco de Dados com PreparedStatement</h1>");
            out.println("<body>");
            Connection con = null;
            PreparedStatement stm = null;
            ResultSet result = null;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/teste?autoReconnect=true&useSSL=false", "root", "welldarli");
                out.print("Conectado com sucesso");
                if (apagar != null) {
                    stm = con.prepareStatement("delete from Pessoa where codigo= "
                            +Integer.parseInt(apagar)+";");
//                    result = stm.executeUpdate();
                }
                stm = con.prepareStatement("select * from Pessoa");
                result = stm.executeQuery();
                out.print("<table border=1><tr>");
                ResultSetMetaData md = result.getMetaData();
                int quant = md.getColumnCount();
                for (int i = 1; i <= quant; i++) {
                    out.print("<th>" + md.getColumnName(i) + "</th>");
                }
                out.print("<th>deletar</th>");
                out.print("</tr>");
                while (result.next()) {
                    int codigo = result.getInt("codigo");
                    String nome = result.getString("nome");
                    int idade = result.getInt("idade");

                    out.print("<tr><td>" + codigo + "</td><td>" + nome + "</td><td>"
                            + idade
                            + "</td><td><a href=\"http://localhost:8080/ConexaoBDMaven/ServletApagar?apagar="+ codigo + "\">apagar</a></td></tr>");
                }
                out.print("</table>");
            } catch (ClassNotFoundException ex) {
                out.print(ex.toString());
            } catch (SQLException ex) {
                out.print(ex.toString());
            } catch (Exception ex) {
                out.print(ex.toString());
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
