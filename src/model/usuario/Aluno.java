package model.usuario;

public class Aluno extends UsuarioAcademico {
    private String matricula;
    private String curso;

    public Aluno(String nivelAcesso, String nome, String email, String senha, String matricula, String curso) {
        super(nivelAcesso, nome, email, senha, 2);
        this.matricula = matricula;
        this.curso = curso;
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
