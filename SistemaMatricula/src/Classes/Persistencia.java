/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author vh_ma
 */
public class Persistencia {

    private static Connection conn = null;

    private Persistencia() {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/faculdade", "postgres", "victor");

        } catch (ClassNotFoundException e) {
            System.out.println("Não foi possivel encontrar o driver especificado");
        } catch (SQLException ex) {
            System.out.println("Não foi possivel conectar ao BD");
        }
    }

    public static Connection conexao() {
        if (conn == null) {
            new Persistencia();
        }
        return conn;
    }
}
