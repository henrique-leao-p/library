package model.usuario;

public class Professor extends Usuario {
    private String departamento;
    private int limiteEmprestimos;


    public Professor(String nivelAcesso, String nome, String email, String senha, String departamento) {
        super(nivelAcesso, nome, email, senha);
        this.departamento = departamento;
        this.limiteEmprestimos = 10;
    }

    public String getDepartamento() {
        return departamento;
    }

    public int getLimiteEmprestimos() {
        return limiteEmprestimos;
    }
}
