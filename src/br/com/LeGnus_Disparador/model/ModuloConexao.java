/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.LeGnus_Disparador.model;

import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author Ad3ln0r
 */
public class ModuloConexao {

    static Connection conexao = null;
    static PreparedStatement pst = null;
    static ResultSet rs = null;
    static String uri;
            
    public static void ModuloConexao() {
        try {
            
            conexao = ConexaoLocal.conector();
            String sql = "select uri from tbUri where id = 1";
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                uri = rs.getString(1);
            }else{
                JOptionPane.showMessageDialog(null, "Banco Invalido");
            }
        } catch (Exception e) {

        }

    }

    public static Connection conector() {
        ModuloConexao();
        java.sql.Connection conexao = null;
        String driver = "com.mysql.jdbc.Driver";
        String url = uri;
        String user = "dba";
        String password = "Legnu.131807";
        //Legnu.131807

        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            //A Linha abaixo serve para esclarecer o erro.
            //System.out.println(e);
            return null;
        }
    }
}
