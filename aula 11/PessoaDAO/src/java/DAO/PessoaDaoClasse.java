/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.Pessoa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaDaoClasse implements PessoaDaoInterface {

    Connection con;

    public PessoaDaoClasse() throws ErroBancoException {
        FabricaConexao fc = new FabricaConexao();
        con = fc.pegaConexao();
    }

    @Override
    public void criarPessoa(Pessoa p) throws SQLException {
        PreparedStatement ps = con.prepareStatement("insert into Pessoa values(null,?,?)");
        ps.setString(1, p.getNome());
        ps.setInt(2, p.getIdade());
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public List<Pessoa> pegaPessoas() throws SQLException {
        ArrayList<Pessoa> grupo = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement("select * from Pessoa");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Pessoa p = new Pessoa();
            p.setCodigo(rs.getInt(1));
            p.setNome(rs.getString(2));
            p.setIdade(rs.getInt(3));
            grupo.add(p);
        }

        return grupo;
    }

    public void sair() throws ErroBancoException {
        try {
            con.close();
        } catch (SQLException ex) {
            throw new ErroBancoException("Erro ao sair", ex);
        }
    }

    @Override
    public Pessoa pegaPessoa(int codigo) throws SQLException {
        PreparedStatement ps = con.prepareStatement("select * from Pessoa where codigo=?");
        ps.setInt(1, codigo);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Pessoa p = new Pessoa();
            p.setCodigo(rs.getInt(1));
            p.setNome(rs.getString(2));
            p.setIdade(rs.getInt(3));
            return p;
        }
        return null;
    }

    @Override
    public void deletarPessoa(int codigo) throws SQLException {
        PreparedStatement ps = con.prepareStatement("delete from Pessoa where codigo=?");
        ps.setInt(1, codigo);
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public void deletarPessoa(Pessoa p) throws SQLException {
        deletarPessoa(p.getCodigo());
    }

    @Override
    public void editarPessoa(Pessoa p) throws SQLException {
        PreparedStatement ps = con.prepareStatement("update Pessoa set nome = ?, idade = ?  where codigo=?");
        ps.setString(1, p.getNome());
        ps.setInt(2, p.getIdade());
        ps.setInt(3, p.getCodigo());
        ps.executeUpdate();
        ps.close();
    }

    public static void main(String[] args) throws SQLException {
        try {
            PessoaDaoClasse dao = new PessoaDaoClasse();
            System.out.println("conectado com sucesso");
            Pessoa p1 = new Pessoa();
//            p.setNome("Izadora");
//            p.setIdade(16);
//            dao.criarPessoa(p);

            List<Pessoa> pessoas = dao.pegaPessoas();
            for (Pessoa p : pessoas) {
                System.out.println(p.toString());
            }

            System.out.println("\n\nPessoa de codigo :7");
            System.out.println(dao.pegaPessoa(7));

//            dao.deletarPessoa(6);
//            p1.setCodigo(5);
//            dao.deletarPessoa(p1);
            p1.setCodigo(dao.pegaPessoa(8).getCodigo());
            p1.setNome(dao.pegaPessoa(8).getNome());
            p1.setIdade(18);
            dao.editarPessoa(p1);
            dao.sair();

            System.out.println("fechado com sucesso");
        } catch (ErroBancoException ex) {
            System.out.println(ex.toString());
        }

    }

}
