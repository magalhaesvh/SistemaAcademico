/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Victor
 */
public class Faculdade {

    private ArrayList<Aluno> aluno;
    private ArrayList<Curso> curso;
    private ArrayList<Matricula> matricula;
    private int cod;
    private int cdAluno;
    private int cdCurso;
    private int cdMatricula;

    public Faculdade() {
        this.cod = 0;
        this.aluno = new ArrayList();
        this.curso = new ArrayList();
        this.matricula = new ArrayList();
        this.cdAluno = 0;
        this.cdCurso = 0;
        this.cdMatricula = 0;
    }

    public Faculdade(int c) {
        this.cod = c;
    }

    public void cadastraCurso() {
        Curso aux = new Curso();
        aux.preencher(cdCurso++);
        getCurso().add(aux);
        aux.inserirCursosBD();
    }

    public void cadastraAluno() {
        Aluno aux = new Aluno();
        aux.preencher(cdAluno++);
        getAluno().add(aux);
        aux.inserirAlunosBD();
    }

    public void carregarAlunosBD() {
        try {
            ResultSet rs = null;
            PreparedStatement ps = Persistencia.conexao().prepareStatement("Select * from alunos");
            rs = ps.executeQuery();
            
            this.aluno = new ArrayList();
            while (rs.next()) {
                Aluno aux = new Aluno();
                aux.preencher(rs.getInt("cod"),rs.getString("nome"), rs.getString("cpf"));
                this.aluno.add(aux);
            }

        } catch (SQLException e) {
            System.out.println("Não foi possivel carregar o comendo SQL");
        }
    }
    public void carregarCursosBD() {
        try {
            ResultSet rs = null;
            PreparedStatement ps = Persistencia.conexao().prepareStatement("Select * from cursos");
            rs = ps.executeQuery();
            
            this.curso = new ArrayList();
            while (rs.next()) {
                Curso aux = new Curso();
                aux.preencher(rs.getInt("cod"),rs.getString("nome"), rs.getInt("ch"));
                this.curso.add(aux);
            }

        } catch (SQLException e) {
            System.out.println("Não foi possivel carregar o comendo SQL");
        }
    }

    public void cadastraMatricula() {
        Scanner ler = new Scanner(System.in);
        Matricula aux = new Matricula();
        for (Curso c : getCurso()) { //percorre para mostrar todos os cursos
            System.out.println(c.getCod() + "  " + c.getNome());
        }
        System.out.println("Digite ID do curso: ");
        int cur = ler.nextInt();
        for (Curso c : getCurso()) { //percorre curso para verificar se existe
            if (c.getCod() == cur) {
                System.out.println("Digite ID do aluno");
                int id = ler.nextInt();
                for (Aluno a : getAluno()) { //percorre para procurar o aluno
                    if (a.getCod() == id) {
                        aux.preencher(cdMatricula++, id, cur);
                        getMatricula().add(aux);
                        aux.inserirMatriculasBD();
                    }
                }
            }
        }
    }

    public String getNameAluno(int id) {
        String name = "";
        for (Aluno a : aluno) {
            if (a.getCod() == id) {
                name = a.getNome();
            }
        }
        return name;
    }

    public String getNameCurso(int id) {
        String name = "";
        for (Curso c : curso) {
            if (c.getCod() == id) {
                name = c.getNome();
            }
        }
        return name;
    }

    public void imprimir() {
        int cda = 0, cdc = 0, cdm = 0;
        for (Matricula m : matricula) {
            cda = m.getCodigoAluno();
            cdc = m.getCodigoCurso();
            System.out.println("Nome: " + this.getNameAluno(cda));
            System.out.println("Curso: " + this.getNameCurso(cdc));
            System.out.println("Matricula: " + cdm);
            cdm++;
        }
    }

    public String alunosCSV() { //carrega o que tem q ser salvo no arquivo
        String aux = "ID;NOME;CPF\r\n";
        for (Aluno a : this.aluno) {
            aux += a.criaCSV();
        }
        return aux;
    }

    public String cursosCSV() { //carrega o que tem q ser salvo no arquivo
        String aux = "COD;NOME;CH\r\n";
        for (Curso c : this.curso) {
            aux += c.criaCSVCurso();
        }
        return aux;
    }

    public String matriculasCSV() { //carrega o que tem q ser salvo no arquivo
        String aux = "NUMERO;CODALUNO;CODCURSO;DATA\r\n";
        for (Matricula m : this.matricula) {
            aux += m.criaCSVMatricula();
        }
        return aux;
    }

    public void salvarArq(String alunos, String cursos, String matriculas) {
        Arquivo.escreverArq(this.alunosCSV(), alunos);
        Arquivo.escreverArq(this.cursosCSV(), cursos);
        Arquivo.escreverArq(this.matriculasCSV(), matriculas);
    }

    public void lerArquivo(String nomes, String cursos, String matriculas) {
        Arquivo.lerArquivo(nomes);
        Arquivo.lerArquivo(cursos);
        Arquivo.lerArquivo(matriculas);
    }

    public void carregarDadosAluno(String alunos) {
        String[] aux = Arquivo.getLinhas(alunos);
        for (int i = 0; i < aux.length; i++) {
            Aluno a = new Aluno();
            a.loadCSVAluno(aux[i]);
            this.aluno.add(a);
        }
    }

    public void carregarDadosCursos(String cursos) {
        String aux[] = Arquivo.getLinhas(cursos);
        for (int i = 0; i < aux.length; i++) {
            Curso c = new Curso();
            c.loadCSVCurso(aux[i]);
            this.curso.add(c);
        }
    }

    public void carregarDadosMatriculas(String matriculas) {
        String[] aux = Arquivo.getLinhas(matriculas);
        for (int i = 0; i < aux.length; i++) {
            Matricula m = new Matricula();
            m.loadCSVMatricula(aux[i]);
            this.matricula.add(m);
        }
    }


    /*public boolean verificar(String nome) {
        boolean resultado = false;
        for (int i = 0; i < 10; i++) {
            if (curso[i].getNome().equals(nome)) {
                resultado = true;
            }
        }
        return resultado;
    }
    
    public void matricular(int t) {
        Scanner ler = new Scanner(System.in);
        String cur = ler.next();
        int c = 0;
        if (verificar(cur)) {
            for (int j = 0; j < curso.length; j++) {
                if (curso[j].getNome().equals(cur)) {
                    c = curso[j].getCod();
                }
            }
            for (int i = 0; i < t; i++) {
                cod++;
                aluno[i].inserirAluno();
                aluno[i].setCod(this.cod);
                matricula[i].setCodigoAluno(cod);
                matricula[i].addData();
                matricula[i].setNumero(cod);
                matricula[i].setCodigoCurso(c);
            }
        } else {
            System.out.println("Este curso não existe!");
        }
    }

    public void imprimirMatricula() {
        for (int i = 0; i < cod; i++) {
            System.out.println("--------------------------------");
            aluno[i].imprimirAluno();
            matricula[i].imprimirMat();
            int c = matricula[i].getCodigoCurso();
            // busca o nome do curso através do código da matrícula
            for (int j = 0; j < curso.length; j++) {
                if(c==curso[j].getCod())
                    System.out.println(curso[j].getNome());
            }
        }
    }*/
    public void Menu() {
        Scanner ler = new Scanner(System.in);
        int op = 0;
        do {
            System.out.println("1 - Cadastrar curso\n2 - Cadastra Aluno\n3 - Matricular\n4 - Imprimir\n5 - Salvar arquivos\n6 - Carregar dados Arquivo\n7 - Carregat dados BD\n0 - Sair");
            op = ler.nextInt();

            switch (op) {

                case 1:
                    this.cadastraCurso();
                    break;
                case 2:
                    this.cadastraAluno();
                    break;

                case 3:
                    this.cadastraMatricula();
                    break;

                case 4:
                    this.imprimir();
                    break;

                case 5:
                    this.salvarArq("alunos.txt", "cursos.txt", "matriculas.txt");
                    break;

                case 6:
                    this.carregarDadosAluno(Arquivo.lerArquivo("alunos.txt"));
                    this.carregarDadosCursos(Arquivo.lerArquivo("cursos.txt"));
                    break;
                    
                case 7:
                    this.carregarAlunosBD();
                    this.carregarCursosBD();
                    break;
                default:
                    System.out.println("Essa opção não existe");
            }

        } while (op != 0);

    }

    public ArrayList<Aluno> getAluno() {
        return aluno;
    }

    public void setAluno(ArrayList<Aluno> aluno) {
        this.aluno = aluno;
    }

    public ArrayList<Curso> getCurso() {
        return curso;
    }

    public void setCurso(ArrayList<Curso> curso) {
        this.curso = curso;
    }

    public ArrayList<Matricula> getMatricula() {
        return matricula;
    }

    public void setMatricula(ArrayList<Matricula> matricula) {
        this.matricula = matricula;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }
}
