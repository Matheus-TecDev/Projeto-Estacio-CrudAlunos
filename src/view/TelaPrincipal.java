package view;

import model.Aluno;
import service.AlunoService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class TelaPrincipal extends JFrame {

    private AlunoService alunoService = new AlunoService();

    // Campos de entrada
    private JTextField campoNome      = new JTextField(10);
    private JTextField campoCurso     = new JTextField(10);
    private JTextField campoNotas     = new JTextField(15);
    private JTextField campoIdSelecionado = new JTextField(5);

    // Modelo e lista para exibição
    private DefaultListModel<Aluno> listModel = new DefaultListModel<>();
    private JList<Aluno> listAlunos = new JList<>(listModel);

    public TelaPrincipal() {
        setTitle("CRUD de Alunos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // === Painel de Entrada + Botões ===
        JPanel painelEntrada = new JPanel();
        painelEntrada.add(new JLabel("Nome:"));
        painelEntrada.add(campoNome);
        painelEntrada.add(new JLabel("Curso:"));
        painelEntrada.add(campoCurso);
        painelEntrada.add(new JLabel("Notas (vírgula):"));
        painelEntrada.add(campoNotas);

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(this::cadastrarAluno);
        painelEntrada.add(btnCadastrar);

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(this::atualizarAluno);
        painelEntrada.add(btnAtualizar);

        painelEntrada.add(new JLabel("ID sel.:"));
        painelEntrada.add(campoIdSelecionado);

        JButton btnRemover = new JButton("Remover");
        btnRemover.addActionListener(this::removerAluno);
        painelEntrada.add(btnRemover);

        add(painelEntrada, BorderLayout.NORTH);

        // === Lista com seleção ===
        listAlunos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listAlunos.addListSelectionListener(evt -> {
            Aluno sel = listAlunos.getSelectedValue();
            if (sel != null) {
                campoNome.setText(sel.getNome());
                campoCurso.setText(sel.getCurso());
                // remove colchetes e espaços
                campoNotas.setText(sel.getNotas()
                        .toString()
                        .replaceAll("[\\[\\]\\s]", ""));
                campoIdSelecionado.setText(String.valueOf(sel.id));
            }
        });

        add(new JScrollPane(listAlunos), BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // carrega inicialmente (vazio)
        atualizarListaAlunos();
    }

    private void cadastrarAluno(ActionEvent e) {
        if (!validarPreenchimento()) return;
        try {
            List<Double> notas = parseNotas(campoNotas.getText());
            alunoService.adicionar(
                    campoNome.getText(),
                    campoCurso.getText(),
                    notas
            );
            limparCampos();
            atualizarListaAlunos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Formato de nota inválido.");
        }
    }

    private void atualizarAluno(ActionEvent e) {
        if (!validarPreenchimento() || campoIdSelecionado.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um aluno primeiro.");
            return;
        }
        try {
            int id = Integer.parseInt(campoIdSelecionado.getText());
            List<Double> notas = parseNotas(campoNotas.getText());
            boolean ok = alunoService.atualizar(
                    id,
                    campoNome.getText(),
                    campoCurso.getText(),
                    notas
            );
            if (!ok) JOptionPane.showMessageDialog(this, "ID não encontrado.");
            limparCampos();
            atualizarListaAlunos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID ou nota inválidos.");
        }
    }

    private void removerAluno(ActionEvent e) {
        try {
            int id = Integer.parseInt(campoIdSelecionado.getText());
            boolean ok = alunoService.remover(id);
            if (!ok) JOptionPane.showMessageDialog(this, "ID não encontrado.");
            limparCampos();
            atualizarListaAlunos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        }
    }

    private boolean validarPreenchimento() {
        if (campoNome.getText().trim().isEmpty()
                || campoCurso.getText().trim().isEmpty()
                || campoNotas.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            return false;
        }
        return true;
    }

    private List<Double> parseNotas(String texto) {
        List<Double> notas = new ArrayList<>();
        for (String s : texto.split(",")) {
            notas.add(Double.parseDouble(s.trim()));
        }
        return notas;
    }

    private void atualizarListaAlunos() {
        listModel.clear();
        for (Aluno a : alunoService.listar()) {
            listModel.addElement(a);
        }
    }

    private void limparCampos() {
        campoNome.setText("");
        campoCurso.setText("");
        campoNotas.setText("");
        campoIdSelecionado.setText("");
        listAlunos.clearSelection();
    }
}
