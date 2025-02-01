package model;

import model.usuario.Usuario;
import model.usuario.UsuarioAcademico;

import java.time.LocalDate;

public class Emprestimo {
    private int id;
    private ObraLiteraria obra;
    private UsuarioAcademico usuario;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private boolean devolvido;

    public Emprestimo(int id, ObraLiteraria obra, UsuarioAcademico usuario, LocalDate dataEmprestimo, LocalDate dataDevolucao) {
        this.id = id;
        this.obra = obra;
        this.usuario = usuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.devolvido = false;
    }

    public Emprestimo(int id, ObraLiteraria obra, UsuarioAcademico usuario, LocalDate dataEmprestimo, LocalDate dataDevolucao, boolean devolvido) {
        this.id = id;
        this.obra = obra;
        this.usuario = usuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.devolvido = devolvido;
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

