package model.usuario;

public class Professor extends UsuarioAcademico {
    private String departamento;


    public Professor(String nivelAcesso, String nome, String email, String senha, String departamento) {
        super(nivelAcesso, nome, email, senha, 10);
        this.departamento = departamento;
    }

    public String getDepartamento() {
        return departamento;
    }

    public int getLimiteEmprestimos() {
        return limiteEmprestimos;
    }
}
