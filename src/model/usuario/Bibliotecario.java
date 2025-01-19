package model.usuario;

public class Bibliotecario extends Usuario {
    private String telefone;
    private int devolucoesRealizadas;

    public Bibliotecario(String nivelAcesso, String nome, String email, String senha, String telefone) {
        super(nivelAcesso, nome, email, senha);
        this.telefone = telefone;
        this.devolucoesRealizadas = 0;
    }

    public String getTelefone() {
        return telefone;
    }

    public void cadastrarUsuario(Usuario usuario) {
        System.out.println("Usuário " + usuario.nome + " cadastrado com sucesso.");
    }

    public void registrarDevolucao() {
        devolucoesRealizadas++;
    }

    public int getDevolucoesRealizadas() {
        return devolucoesRealizadas;
    }
}
