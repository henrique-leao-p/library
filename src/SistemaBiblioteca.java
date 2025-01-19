import model.Emprestimo;
import model.ObraLiteraria;
import model.usuario.Aluno;
import model.usuario.Bibliotecario;
import model.usuario.Professor;
import model.usuario.Usuario;

import java.io.*;
import java.util.ArrayList;

public class SistemaBiblioteca {
    private ArrayList<Usuario> usuarios;
    private ArrayList<ObraLiteraria> obras;
    private ArrayList<Emprestimo> emprestimos;
    private Usuario usuarioLogado;

    public SistemaBiblioteca() {
        this.usuarios = new ArrayList<>();
        this.obras = new ArrayList<>();
        this.emprestimos = new ArrayList<>();
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public ArrayList<ObraLiteraria> getObras() {
        return obras;
    }

    public void setObras(ArrayList<ObraLiteraria> obras) {
        this.obras = obras;
    }

    public ArrayList<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(ArrayList<Emprestimo> emprestimos) {
        this.emprestimos = emprestimos;
    }

    public void carregarDados() {
        usuarios = carregarUsuarios();
        obras = carregarObras();
    }

    public void addUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    public ArrayList<Usuario> carregarUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/db_library/usuarios.csv"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                String tipoUsuario = dados[0];

                switch (tipoUsuario) {
                    case "Aluno":
                        Aluno aluno = new Aluno(tipoUsuario, dados[1], dados[2], dados[3], dados[4], dados[5]);
                        usuarios.add(aluno);
                        break;

                    case "Professor":
                        Professor professor = new Professor(tipoUsuario, dados[1], dados[2], dados[3], dados[4]);
                        usuarios.add(professor);
                        break;

                    case "Bibliotecario":
                        Bibliotecario bibliotecario = new Bibliotecario(tipoUsuario, dados[1], dados[2], dados[3], dados[4]);
                        usuarios.add(bibliotecario);
                        break;

                    default:
                        System.out.println("Tipo de usuário desconhecido: " + tipoUsuario);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar os usuários: " + e.getMessage());
        }
        return usuarios;
    }

    public ArrayList<ObraLiteraria> carregarObras() {
        ArrayList<ObraLiteraria> obras = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/db_library/acervo.csv"))) {
            String linha;
            // Skipar uma linha
            reader.readLine();
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                int id = Integer.parseInt(dados[0]);
                String titulo = dados[1];
                int quantidadeDisponivel = Integer.parseInt(dados[2]);
                String autor = dados[3];
                ObraLiteraria obraLiteraria = new ObraLiteraria(id, titulo, quantidadeDisponivel, autor);
                obras.add(obraLiteraria);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar as obras literárias: " + e.getMessage());
        }
        return obras;
    }

    public void salvarDados() {
        salvarUsuarios();
        salvarEmprestimos();
    }

    private void salvarUsuarios() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/db_library/usuarios.csv"))) {
            for (Usuario usuario : usuarios) {
                StringBuilder linha = new StringBuilder();
                if (usuario instanceof Aluno) {
                    Aluno aluno = (Aluno) usuario;
                    linha.append("Aluno,")
                            .append(aluno.getNome()).append(",")
                            .append(aluno.getEmail()).append(",")
                            .append(aluno.getSenha()).append(",")
                            .append(aluno.getMatricula()).append(",")
                            .append(aluno.getCurso());
                } else if (usuario instanceof Professor) {
                    Professor professor = (Professor) usuario;
                    linha.append("Professor,")
                            .append(professor.getNome()).append(",")
                            .append(professor.getEmail()).append(",")
                            .append(professor.getSenha()).append(",")
                            .append(professor.getDepartamento());
                } else if (usuario instanceof Bibliotecario) {
                    Bibliotecario bibliotecario = (Bibliotecario) usuario;
                    linha.append("Bibliotecario,")
                            .append(bibliotecario.getNome()).append(",")
                            .append(bibliotecario.getEmail()).append(",")
                            .append(bibliotecario.getSenha()).append(",")
                            .append(bibliotecario.getTelefone())
                    ;
                }

                writer.write(linha.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar os usuários: " + e.getMessage());
        }
    }

    private void salvarEmprestimos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/db_library/emprestimos.csv"))) {
            for (Emprestimo emprestimo : emprestimos) {
                String linha = emprestimo.getId() + ";" +
                        emprestimo.getUsuario().getEmail() + ";" +
                        emprestimo.getObra().getId() + ";" +
                        emprestimo.getDataEmprestimo() + ";" +
                        emprestimo.getDataDevolucao();

                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar os empréstimos: " + e.getMessage());
        }
    }

    private void atualizarQuantObra(int id, int alteracaoQuantObra) {
        obras.stream()
                .filter(obra -> obra.getId() == id)
                .findFirst()
                .ifPresent(obra -> obra.alterarQuantidadeDisponivel(alteracaoQuantObra));
    }

    public void login(String email, String senha) {
        for (Usuario usuario : usuarios) {
            if (usuario.login(email, senha)) {
                usuarioLogado = usuario;
                System.out.println("Login realizado com sucesso.");
                return;
            }
        }
        System.out.println("Credenciais inválidas.");
    }

    public void logout() {
        usuarioLogado = null;
        System.out.println("Logout realizado com sucesso.");
    }

    public void consultarObra(String consulta) {
        try {
            int id = Integer.parseInt(consulta);
            obras.stream()
                    .filter(obra -> obra.getId() == id)
                    .findFirst()
                    .ifPresent(obra -> System.out.println(obra.toString()));


        } catch (Exception e) {
            String titulo = consulta;
            obras.stream()
                    .filter(obra -> obra.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                    .forEach(obra -> {
                        System.out.println(obra.toString());
                    });
        }
    }

    public void emprestarObra(int idObra) {
        if (usuarioLogado instanceof Aluno || usuarioLogado instanceof Professor) {
            // Implementar lógica de empréstimo
        } else {
            System.out.println("Usuário não autorizado a realizar empréstimos.");
        }
    }

    public void registrarDevolucao(int idEmprestimo) {
        if (usuarioLogado instanceof Bibliotecario) {
            // Implementar lógica de devolução
        } else {
            System.out.println("Usuário não autorizado a registrar devoluções.");
        }
    }

    public void gerarRelatorios() {
        if (usuarioLogado instanceof Bibliotecario) {
            // Implementar lógica de geração de relatórios
        } else {
            System.out.println("Usuário não autorizado a gerar relatórios.");
        }
    }

    public void cadastrarUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
    }


}
