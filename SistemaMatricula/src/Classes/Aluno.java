/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Victor
 */
public class Aluno {
    private int cod;
    private String nome;
    private String cpf;
    
    public Aluno(){
        this.cod = 0;
        this.nome = "";
        this.cpf = "";
    }
    
    public Aluno(int cd, String n, String c){
        this.cod = cd;
        this.nome = n;
        this.cpf = c;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public void preencher(int cd){
        Scanner ler = new Scanner(System.in);
        System.out.println("Digite nome do aluno");
        nome = ler.next();
        System.out.println("Digite o CPF");
        cpf = ler.next();
        this.cod = cd;
    }
    public void preencher(int cd, String n, String c){
        nome = n;
        cpf = c;
        this.cod = cd;
    }
    
    public String criaCSV(){ //cria o que necessário para ser salvo
        String saida = this.cod+";"+this.nome+";"+this.cpf+"\r\n";
        return saida;
    }
    
    public void loadCSVAluno(String lido){
        String [] array = lido.split(";");
        this.cod = Integer.parseInt(array[0]);
        this.nome = array[1];
        this.cpf = array[2];
    }
    
    public void imprimirAluno(){
        System.out.println("Nome: " + nome);
        System.out.println("CPF: "+ cpf);
    }
    
    public void inserirAlunosBD(){
        try{
            PreparedStatement ps = Persistencia.conexao().prepareStatement("INSERT INTO alunos (cpf, nome) VALUES(?,?)");
            ps.setString(2, this.nome);          
            ps.setString(1, this.cpf);
            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            System.out.println("Não foi possivel executar o comando sql. "+e);
        }
    }
}
