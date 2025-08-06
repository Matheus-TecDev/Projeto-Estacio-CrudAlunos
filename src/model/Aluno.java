package model;

import java.util.List;

public class Aluno {
    public final int id;
    private String nome;
    private String curso;
    private List<Double> notas;

    public Aluno(int id, String nome, String curso, List<Double> notas) {
        this.id = id;
        this.nome = nome;
        this.curso = curso;
        this.notas = notas;
    }

    public String getNome() {
        return nome;
    }

    public String getCurso() {
        return curso;
    }

    public List<Double> getNotas() {
        return notas;
    }

    @Override
    public String toString() {
        return id + ": " + nome + " (" + curso + ") – " + notas;
    }
}
