package meuprojeto;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean escolhaValida = false;

        // Loop que prende o usuário até que ele digite uma resposta válida para o sistema (1 ou 2);
        while (!escolhaValida) {
            System.out.println("O que você deseja fazer? (1 para salvar - 2 para ver contatos) ");
            String escolha = scanner.nextLine();

            if (escolha.equals("1")) {
                System.out.println("Opção escolhida: Salvar");
                salvarContato(scanner);

            } else if (escolha.equals("2")) {
                System.out.println("Opção escolhida: Ver contatos");
                exibirContatos(scanner);
                System.out.println("");

            } else {
                System.out.println("Opção inválida");
                System.out.println("");
            }
        }
    }

    // Retorno caso o usuário escolha a opção "1";
    // Método para salvar um novo contato banco de dados;
    private static void salvarContato(Scanner scanner) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            while (true) {
            	// Solicitação dos dados do novo contato
                System.out.println("Qual o nome do contato? ");
                String nome = scanner.nextLine();

                System.out.println("Qual o telefone do contato? ");
                String telefone = scanner.nextLine();

                System.out.println("Qual o celular do contato? ");
                String celular = scanner.nextLine();

                System.out.println("Qual o email do contato? ");
                String email = scanner.nextLine();

                System.out.println("Qual o tipo do contato? (Ex. Familiar - Amigo - Trabalho - etc.)");
                String tipo = scanner.nextLine();

                Usuario novoUsuario = new Usuario();
                novoUsuario.setNome(nome);
                novoUsuario.setTelefone(telefone);
                novoUsuario.setCelular(celular);
                novoUsuario.setEmail(email);
                novoUsuario.setTipo(tipo);
               
                Transaction transaction = session.beginTransaction();
                session.save(novoUsuario);
                transaction.commit();

                System.out.println("Certo! Os dados do seu novo contato " + nome + " foram salvos!");

                System.out.println("Deseja salvar mais um contato? (S/N)");
                String continuar = scanner.nextLine();
                if (!continuar.equalsIgnoreCase("s") && !continuar.equalsIgnoreCase("sim")) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	// Fecha a sessão do Hibernate
            session.close();
            sessionFactory.close();
        }
    }

    // Retorno caso o usuário escolha a opção "2";
    // Método para consultar os contatos salvos no banco de dados;
    private static void exibirContatos(Scanner scanner) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            // Consulta para buscar todos os contatos salvos;
            Query<Usuario> query = session.createQuery("FROM Usuario", Usuario.class);
            List<Usuario> contatos = query.list();

            if (contatos.isEmpty()) {
                System.out.println("Não há contatos salvos.");
            } else {
                System.out.println("Lista de contatos:");

                for (Usuario contato : contatos) {
                    System.out.println(contato.getNome());
                }

                // Pergunta ao usuário qual contato ele deseja visualizar;
                System.out.println("");
                System.out.println("Digite o nome do contato que deseja visualizar:");
                String nomeContato = scanner.nextLine();

                // Busca o contato pelo nome digitado;
                Usuario contatoSelecionado = contatos.stream()
                        .filter(c -> c.getNome().equalsIgnoreCase(nomeContato))
                        .findFirst()
                        .orElse(null);

                if (contatoSelecionado != null) {
                    System.out.println("Detalhes do contato:");
                    System.out.println("Nome: " + contatoSelecionado.getNome());
                    System.out.println("Telefone: " + contatoSelecionado.getTelefone());
                    System.out.println("Celular: " + contatoSelecionado.getCelular());
                    System.out.println("Email: " + contatoSelecionado.getEmail());
                    System.out.println("Tipo: " + contatoSelecionado.getTipo());
                } else {
                    System.out.println("Contato não encontrado.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	// Fecha a sessão do Hibernate
            session.close();
            sessionFactory.close();
        }
    }
}