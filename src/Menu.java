import model.usuario.*;

import java.util.Scanner;

public class Menu {
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        SistemaBiblioteca sistema = new SistemaBiblioteca();

        // Carregar dados persistentes
        sistema.carregarDados();

        boolean sair = false;

        while (!sair) {
            System.out.println("\n--- Bem-vindo ao Sistema de Biblioteca ---");
            sistema.getUsuarios().forEach(System.out::println);

            System.out.println("1. Login");
            System.out.println("2. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = input.nextInt();
            input.nextLine(); // Consumir quebra de linha

            switch (opcao) {
                case 1:
                    System.out.print("Digite seu email: ");
                    String email = input.nextLine();
                    System.out.print("Digite sua senha: ");
                    String senha = input.nextLine();

                    sistema.login(email, senha);

                    if (sistema.getUsuarioLogado() != null) {
                        exibirMenuUsuario(sistema);
                    }
                    break;

                case 2:
                    sair = true;
                    sistema.salvarDados();
                    System.out.println("Sistema encerrado. Até logo!");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }

        input.close();
    }

    public static void exibirMenuUsuario(SistemaBiblioteca sistema) {
        boolean sair = false;

        while (!sair) {
            System.out.println("\n--- Menu Principal ---");
            System.out.println("1. Consultar obras");
            if (sistema.getUsuarioLogado() instanceof Aluno || sistema.getUsuarioLogado() instanceof Professor) {
                System.out.println("2. Realizar empréstimo");
            }
            if (sistema.getUsuarioLogado() instanceof Bibliotecario) {
                System.out.println("2. Cadastrar novo usuário");
                System.out.println("3. Registrar devolução");
                System.out.println("4. Gerar relatórios");
            }
            System.out.println("0. Logout");
            System.out.print("Escolha uma opção: ");
            int opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 1:
                    sistema.consultarObra();
                    break;
                case 2:
                    // Caso o user logado seja do tipo Aluno ou Professor
                    if (sistema.getUsuarioLogado() instanceof UsuarioAcademico usuarioAcademico) {

                        if (usuarioAcademico.isBloqueado()){
                            System.out.println("O seu limite de empréstimos foi excedido! Empréstimos ativos: " + usuarioAcademico.getQuantidadeEmprestimosAtivos());
                            return;
                        }
                        sistema.consultarObra();

                        System.out.print("Digite o ID da obra para empréstimo: ");
                        int idObra = input.nextInt();
                        input.nextLine();
                        sistema.emprestarObra(idObra);
                    // Caso o user logado seja do tipo Bibliotecario
                    } else if (sistema.getUsuarioLogado() instanceof Bibliotecario) {
                        System.out.println("\n--CADASTRO DE USUÁRIO");
                        System.out.print("Informe o nome: ");
                        String nome = input.nextLine();

                        System.out.print("Informe o email: ");
                        String email = input.nextLine();

                        System.out.print("Informe a senha: ");
                        String senha = input.nextLine();
                        int nivelAOp = 0;

                        System.out.println("Informe o nivel de acesso: ");
                        while (nivelAOp < 1 || nivelAOp > 3) {
                            System.out.println("1 - Aluno");
                            System.out.println("2 - Professor");
                            System.out.println("3 - Bibliotecario");
                            System.out.print("==> ");
                            nivelAOp = input.nextInt();
                            input.nextLine();
                        }
                        String nivelAcesso = Usuario.NIVEIS_ACESSO[nivelAOp - 1];

                        switch (nivelAcesso) {
                            case "Aluno":
                                System.out.print("Informe a matrícula: ");
                                String matricula = input.nextLine();
                                System.out.print("Informe o curso: ");
                                String curso = input.nextLine();
                                Aluno aluno = new Aluno(nivelAcesso, nome, email, senha, matricula, curso);
                                sistema.addUsuario(aluno);
                                break;

                            case "Professor":
                                System.out.print("Informe o departamento: ");
                                String departamento = input.nextLine();
                                Professor professor = new Professor(nivelAcesso, nome, email, senha, departamento);
                                sistema.addUsuario(professor);
                                break;

                            case "Bibliotecario":
                                System.out.print("Informe o seu telefone: ");
                                String telefone = input.nextLine();
                                Bibliotecario bibliotecario = new Bibliotecario(nivelAcesso, nome, email, senha, telefone);
                                sistema.addUsuario(bibliotecario);
                                break;

                            default:
                                System.out.println("Tipo de usuário desconhecido: " + nivelAcesso);
                        }

                    } else {
                        System.out.println("Opção inválida.");
                    }
                    break;

                case 3:
                    if (sistema.getUsuarioLogado() instanceof Bibliotecario) {
                        System.out.print("Digite o ID do empréstimo para devolução: ");
                        int idEmprestimo = input.nextInt();
                        input.nextLine();
                        sistema.registrarDevolucao(idEmprestimo);
                    } else {
                        System.out.println("Opção inválida.");
                    }
                    break;

                case 4:
                    if (sistema.getUsuarioLogado() instanceof Bibliotecario) {
                        sistema.gerarRelatorios();
                    } else {
                        System.out.println("Opção inválida.");
                    }
                    break;

                case 0:
                    sistema.logout();
                    sair = true;
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }
}
