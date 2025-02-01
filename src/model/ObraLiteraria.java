package model;

public class ObraLiteraria {
    private int id;
    private String titulo;
    private int quantidadeDisponivel;
    private String autor;

    public ObraLiteraria(int id, String titulo, int quantidadeDisponivel, String autor) {
        this.id = id;
        this.titulo = titulo;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.autor = autor;
    }

    public boolean emprestar() {
        if (quantidadeDisponivel > 0) {
            quantidadeDisponivel--;
            return true;
        }
        return false;
    }

    public void devolver() {
        quantidadeDisponivel++;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public String getAutor() {
        return autor;
    }

    public void alterarQuantidadeDisponivel(int alteracao) {
        this.quantidadeDisponivel += alteracao;
    }


    @Override
    public String toString() {
        String disponivel = (quantidadeDisponivel > 0) ? "Disponível" : "Não Disponível";
        return
                "(" + disponivel + ")" +
                "   ID: " + id +
                        ", " + titulo +
                        ", " + autor +
                        ", Número de exemplares: " + quantidadeDisponivel;

    }
}
