/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Victor
 */
public class Curso {

    private int cod;
    private String nome;
    private int ch;

    public Curso() {
        this.cod = 0;
        this.nome = "";
        this.ch = 0;
    }

    public Curso(int cd, String n, int ch) {
        this.cod = cd;
        this.nome = n;
        this.ch = ch;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCh() {
        return ch;
    }

    public void setCh(int ch) {
        this.ch = ch;
    }
    
    public void preencher(int cd) {
        Scanner ler = new Scanner(System.in);
        System.out.println("Digite nome do Curso");
        nome = ler.nextLine();
        System.out.println("Digite CH");
        ch = ler.nextInt();
        this.cod = cd;
    }
    public void preencher(int cd, String n, int c) {
        nome = n;
        ch = c;
        this.cod = cd;
    }

    public void imprimirCurso() {
        System.out.println("Curso: " + nome);
    }
    
    public String criaCSVCurso(){
        String saida = this.cod+";"+this.nome+";"+this.ch+"\r\n";
        return saida;
    }
    
     public void inserirCursosBD(){
        try{
            PreparedStatement ps = Persistencia.conexao().prepareStatement("INSERT INTO cursos (nome, ch) VALUES(?,?)");
            ps.setString(1, this.nome);          
            ps.setInt(2, this.ch);
            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            System.out.println("Não foi possivel executar o comando sql. "+e);
        }
    }
    
    public void loadCSVCurso(String lido){
        String[] array = lido.split(";");
        this.cod = Integer.parseInt(array[0]);
        this.nome = array[1];
        this.ch = Integer.parseInt(array[2]);
    }
}
