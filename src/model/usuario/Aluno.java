package model.usuario;

public class Aluno extends Usuario {
    private String matricula;
    private String curso;
    private int limiteEmprestimos;

    public Aluno(String nivelAcesso, String nome, String email, String senha, String matricula, String curso) {
        super(nivelAcesso, nome, email, senha);
        this.matricula = matricula;
        this.curso = curso;
        this.limiteEmprestimos = 2;
    }

    public int getLimiteEmprestimos() {
        return limiteEmprestimos;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getCurso() {
        return curso;
    }
}
