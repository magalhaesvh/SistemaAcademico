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
public class Matricula {
    private int numero;
    private String data;
    private int codigoAluno;
    private int codigoCurso;
    
    public Matricula(){
        this.numero = 0;
        this.data = null;
        this.codigoAluno = 0;
        this.codigoCurso = 0;
    }
        
    public Matricula(int n, String d, int ca, int cc){
        this.numero = n;
        this.data = d;
        this.codigoAluno = ca;
        this.codigoCurso = cc;
    }
    
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
    public int getCodigoAluno() {
        return codigoAluno;
    }

    public void setCodigoAluno(int codigo) {
        this.codigoAluno = codigo;
    }
    
    public int getCodigoCurso() {
        return codigoCurso;
    }

    public void setCodigoCurso(int codigo) {
        this.codigoCurso = codigo;
    }
    
    public void preencher(int cd, int cdA, int cdC){
        Scanner ler = new Scanner(System.in);
        this.codigoAluno = cdA;
        this.codigoCurso = cdC;
        System.out.println("Digite a data");
        data = ler.next();
        this.numero = cd;
    }
    
    public void addData(){
        Scanner ler = new Scanner(System.in);
        System.out.println("Digite a data");
        data = ler.next();
    }
    
    public void imprimirMat(){
        System.out.println("Matricula: "+ numero);
    }
    
    public String criaCSVMatricula(){
        String saida = this.numero+";"+this.codigoAluno+";"+this.codigoCurso+";"+this.data+"\r\n";
        return saida;
    }
    
    public void inserirMatriculasBD(){
        try{
            PreparedStatement ps = Persistencia.conexao().prepareStatement("INSERT INTO matriculas (idaluno, idcurso, data) VALUES(?,?,?)");      
            ps.setInt(1, this.codigoAluno);
            ps.setInt(2, this.codigoCurso);
            ps.setString(3, this.data);
            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            System.out.println("Não foi possivel executar o comando sql. "+e);
        }
    }
    
    public void loadCSVMatricula(String lido){
        String[] array = new String[4];
        this.numero = Integer.parseInt(array[0]);
        this.codigoAluno = Integer.parseInt(array[1]);
        this.codigoCurso = Integer.parseInt(array[2]);
        this.data = array[3];
    }
}
