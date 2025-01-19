package model.usuario;

public abstract class Usuario {
    protected String nivelAcesso;
    protected String nome;
    protected String email;
    protected String senha;
    protected boolean bloqueado;

    public static final String[] NIVEIS_ACESSO = {"Aluno", "Professor", "Bibliotecario"};

    public Usuario() {
    }

    public Usuario(String nivelAcesso, String nome, String email, String senha) {
        this.nivelAcesso = nivelAcesso;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.bloqueado = false;
    }

    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public boolean login(String email, String senha) {
        return this.email.equals(email) && this.senha.equals(senha);
    }

    public void logout() {
        System.out.println("Logout realizado com sucesso.");
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void desbloquear() {
        this.bloqueado = false;
    }


    @Override
    public String toString() {
        return "Usuario{" +
                "nivelAcesso='" + nivelAcesso + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", bloqueado=" + bloqueado +
                '}';
    }
}
