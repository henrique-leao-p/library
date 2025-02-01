import model.Emprestimo;
import model.ObraLiteraria;
import model.usuario.*;

import java.io.*;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class SistemaBiblioteca {
    private ArrayList<Usuario> usuarios;
    private ArrayList<ObraLiteraria> obras;
    private ArrayList<Emprestimo> emprestimos;
    private Usuario usuarioLogado;
    Scanner input = new Scanner(System.in);

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
        emprestimos = carregarEmprestimos();
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

    public ArrayList<Emprestimo> carregarEmprestimos() {
        ArrayList<Emprestimo> emprestimos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/db_library/emprestimos.csv"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                int id = Integer.parseInt(dados[0]);
                String email = (dados[1]);
                int idObra = Integer.parseInt(dados[2]);
                LocalDate dataEmprestimo = LocalDate.parse(dados[3]);
                LocalDate dataDevolucao = LocalDate.parse(dados[4]);
                boolean devolvido = Boolean.parseBoolean(dados[5]);

                ObraLiteraria obra = obras.stream()
                        .filter(o -> o.getId() == idObra)
                        .findFirst()
                        .orElse(null);

                UsuarioAcademico usuario = (UsuarioAcademico) usuarios.stream()
                        .filter(u -> u.getEmail().equals(email))
                        .findFirst()
                        .orElse(null);

                if (obra != null && usuario != null) {
                    Emprestimo emprestimo = new Emprestimo(id, obra, usuario, dataEmprestimo, dataDevolucao, devolvido);
                    emprestimos.add(emprestimo);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar os empréstimos: " + e.getMessage());
        }
        return emprestimos;
    }


    public void salvarDados() {
        salvarUsuarios();
        salvarEmprestimos();
        salvarObras();
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
                String linha = emprestimo.getId() + "," +
                        emprestimo.getUsuario().getEmail() + "," +
                        emprestimo.getObra().getId() + "," +
                        emprestimo.getDataEmprestimo() + "," +
                        emprestimo.getDataDevolucao() + "," +
                        emprestimo.isDevolvido();

                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar os empréstimos: " + e.getMessage());
        }
    }

    public void salvarObras() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/db_library/acervo.csv"))) {
            for (ObraLiteraria obra : obras) {
                String linha = obra.getId() + "," +
                        obra.getTitulo() + "," +
                        obra.getQuantidadeDisponivel() + "," +
                        obra.getAutor();
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar as obras literárias: " + e.getMessage());
        }
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

    public void consultarObra() {
        String consulta;

        while (true) {
            System.out.print("Digite o título ou ID da obra: ");
            consulta = input.nextLine();
            try {
                int id = Integer.parseInt(consulta);
                obras.stream()
                        .filter(obra -> obra.getId() == id)
                        .findFirst()
                        .ifPresent(System.out::println);


            } catch (Exception e) {
                String titulo = consulta;
                obras.stream()
                        .filter(obra -> obra.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                        .forEach(System.out::println);
            }
            System.out.println("Deseja consultar outro livro? [s/n]");

            if (input.nextLine().toLowerCase().equals("n")) {
                break;
            }
        }

    }

    public void emprestarObra(int idObra) {
        ObraLiteraria obra = obras.stream()
                .filter(o -> o.getId() == idObra)
                .findFirst()
                .get();

        if (obra.getQuantidadeDisponivel() <= 0) {
            System.out.println("Obra indisponivel. Tente com outra obra.");
            return;
        }

        Emprestimo emprestimo = new Emprestimo(
                emprestimos.getLast().getId() + 1, // ID do empréstimo
                obra, // Obtendo a obra filtrada corretamente
                (UsuarioAcademico) usuarioLogado, // Cast para UsuarioAcademico
                LocalDate.now(), // Data do empréstimo
                LocalDate.now().plusWeeks(2) // Data da devolução
        );

        emprestimos.add(emprestimo);
        obra.alterarQuantidadeDisponivel(-1);
        System.out.println("Emprestimo realizado com sucesso.\n");

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



}
