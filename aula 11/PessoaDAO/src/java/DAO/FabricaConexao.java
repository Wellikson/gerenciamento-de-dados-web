/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 *
 * @author wellikson
 */
public class FabricaConexao {

    public Connection pegaConexao() throws ErroBancoException {
        Connection con;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/teste?autoReconnect=true&useSSL=false","root","welldarli");
        } catch (ClassNotFoundException | SQLException ex) {
            throw  new ErroBancoException("Erro ao tentar se conectar",ex);
        }
        return con;
    }
}
