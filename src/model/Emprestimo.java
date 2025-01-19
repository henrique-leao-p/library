package model;

import model.usuario.Usuario;
import java.time.LocalDate;

public class Emprestimo {
    private int id;
    private ObraLiteraria obra;
    private Usuario usuario;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private boolean devolvido;

    public Emprestimo(int id, ObraLiteraria obra, Usuario usuario, LocalDate dataEmprestimo, LocalDate dataDevolucao) {
        this.id = id;
        this.obra = obra;
        this.usuario = usuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.devolvido = false;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public ObraLiteraria getObra() {
        return obra;
    }

    public boolean isDevolvido() {
        return devolvido;
    }



    public void registrarDevolucao() {
        this.devolvido = true;
        obra.devolver();
    }

    public boolean verificarAtraso(LocalDate dataAtual) {
        return !devolvido && dataAtual.isAfter(dataDevolucao);
    }


}

