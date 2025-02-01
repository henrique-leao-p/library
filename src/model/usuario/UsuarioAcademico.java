package model.usuario;

import model.Emprestimo;

import java.util.ArrayList;

public abstract class UsuarioAcademico extends Usuario {
    protected int limiteEmprestimos;
    protected ArrayList<Emprestimo> emprestimosAtivos;

    public UsuarioAcademico(String nivelAcesso, String nome, String email, String senha, int limiteEmprestimos) {
        super(nivelAcesso, nome, email, senha);
        this.limiteEmprestimos = limiteEmprestimos;
        emprestimosAtivos = new ArrayList<>();
    }

    public int getLimiteEmprestimos() {
        return limiteEmprestimos;
    }

    public boolean isBloqueado(){
        return limiteEmprestimos < emprestimosAtivos.size();
    }

    public int getQuantidadeEmprestimosAtivos(){
        return emprestimosAtivos.size();
    }

    public void realizarEmprestimo(Emprestimo emprestimo){
        this.emprestimosAtivos.add(emprestimo);
    }

}