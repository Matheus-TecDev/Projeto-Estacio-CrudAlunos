package service;

import java.util.ArrayList;
import java.util.List;
import model.Aluno;

public class AlunoService {
    private List<Aluno> alunos = new ArrayList<>();
    private int nextId = 1;

    // Create
    public void adicionar(String nome, String curso, List<Double> notas) {
        alunos.add(new Aluno(nextId++, nome, curso, notas));
    }

    // Read
    public List<Aluno> listar() {
        return alunos;
    }

    // Update
    public boolean atualizar(int id, String nome, String curso, List<Double> notas) {
        for (int i = 0; i < alunos.size(); i++) {
            Aluno a = alunos.get(i);
            if (a.id == id) {
                // substitui o objeto mantendo o mesmo ID
                alunos.set(i, new Aluno(id, nome, curso, notas));
                return true;
            }
        }
        return false;
    }

    // Delete
    public boolean remover(int id) {
        return alunos.removeIf(a -> a.id == id);
    }
}
