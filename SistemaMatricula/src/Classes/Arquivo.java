/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author vh_ma
 */
public class Arquivo {

    public static void escreverArq(String texto, String caminho) {
        File arquivo = new File(caminho); //classe para escrita
        FileWriter fw; // instancia a classe
        try {
            fw = new FileWriter(arquivo); //define para escrever no final
            fw.write(texto); //escreve o texto selecionado no arquivo
            fw.flush();
            fw.close(); //fecha o arquivo
        } catch (Exception e) {
        }
    }
    
    /*public static void lerArquivo(String caminho){
        File arquivo = new File(caminho);
        FileReader fr;
        try{
        fr = new FileReader(arquivo);
        BufferedReader br = new BufferedReader(fr);
        br.readLine();
        }catch(Exception e){ }
        
    }*/        
        
    
    public static String lerArquivo(String caminho){ //usando delimitador
        try{
            String lido = "";
            FileReader fr = new FileReader(caminho); //classe pra leitura
            Scanner arquivoEntrada = new Scanner(fr); //scanea o arquivo carregado
            arquivoEntrada.useDelimiter("\n"); //define delimitador
            String aux = arquivoEntrada.next(); //pega posição inicial do arquivo
            while(arquivoEntrada.hasNext()){ //loop enquanto existir texto
                lido += arquivoEntrada.next()+"\n"; //vai pegando cada palavra do texto e concatena
                //System.out.println(lido); //printa cada palavra
            }
            arquivoEntrada.close();
            return lido;
        }catch(Exception e){
            return "";
        }
        
    }
    
    public static String[] getLinhas(String lido){
        String facul = lido;
        String[] arrayFacul = facul.split("\r\n");
        return arrayFacul;        
    }
}
