/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

/**
 *
 * @author wellikson
 */
public class ErroBancoException extends Exception{

    public ErroBancoException() {
        super("Erro na base de dados");
    }

    public ErroBancoException(String string) {
        super(string);
    }

    public ErroBancoException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public ErroBancoException(Throwable thrwbl) {
        super(thrwbl);
    }

    public ErroBancoException(String string, Throwable thrwbl, boolean bln, boolean bln1) {
        super(string, thrwbl, bln, bln1);
    }
    
}
