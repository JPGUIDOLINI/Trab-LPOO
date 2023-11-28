package TrabLPOO;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Concessionaria {
    public static void main(String[] args) {
        // atribui a caminhoArquivo o arquvio de carros e preenche a list com os carros
        // que estão la com o metodo lerArquivos
        String caminhoArquivo = "Carros.txt";
        ArrayList<Carro> list = lerArquivo(caminhoArquivo);
        int opcao;
        do {
            opcao = InOut.leInt("1 - Catálogo\n" +
                    "2 - Testar Carro\n" +
                    "3 - Alugar\n" +
                    "4 - Devolver\n" +
                    "5 - Sair");
            switch (opcao) {
                case 1:
                    catalogo(list);
                    break;
                case 2:
                    testar(list);
                    break;
                case 3:
                    alugar(list);
                    break;
                case 4:
                    devolver(list);
                    break;
                case 5:
                    System.out.println("Fim do programa!");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        } while (opcao != 5);
    }
    // retorna o catalogo de carros da concessionaria
    static void catalogo(ArrayList<Carro> list) {
        for (Carro carro : list) {
            System.out.println("Modelo: " + carro.getModelo() + ", Placa: " + carro.getPlaca() +
                    ", Tipo: " + carro.getTipo() + ", status: " + carro.getStatus());
        }
    }
    // permite que teste algum carro da lista e retorna caracteristicas deles de
    // acordo com o tipo
    static void testar(ArrayList<Carro> list) {
        String placa = InOut.leString("Digite a placa do carro que deseja testar:");
        int posicao = pesquisar(list, placa);
        if (posicao != -1) {
            Carro carro = list.get(posicao);
            System.out.println("Resultado do teste: " + carro.teste());
        } else {
            System.out.println("Carro não encontrado!");
        }
    }
    // aluga o carro desejado e atualiza no arquivo Carros de disponivel para
    // alugado
    static void alugar(ArrayList<Carro> list) {
        String placa = InOut.leString("Digite a placa do carro que deseja locar:");
        int posicao = pesquisar(list, placa);
        if (posicao != -1) {
            Carro carro = list.get(posicao);
            if (!carro.isLocado()) {
                int numDias = InOut.leInt("Digite o número de dias que o carro será locado: ");
                carro.locar(numDias);
                atualizarStatusNoArquivo(carro.getPlaca(), "Alugado");
                carro.setStatus("Alugado");
                System.out.println("Carro <" + carro.getModelo() + "> Placa <" + carro.getPlaca() + "> Locado");
            } else {
                System.out.println("Carro já está locado!");
            }
        } else {
            System.out.println("Carro não encontrado!");
        }
    }
    // devovle o carro desejado e atualiza no arquivo Carros de disponivel para
    // alugado
    static void devolver(ArrayList<Carro> list) {
        String placa = InOut.leString("Digite a placa do carro que deseja Devolver:");
        int posicao = pesquisar(list, placa);
        double valorLocacao = 0;
        if (posicao != -1) {
            Carro carro = list.get(posicao);
            if (carro.isLocado()) {
                valorLocacao = carro.devolver();
                atualizarStatusNoArquivo(carro.getPlaca(), "Disponivel");
                carro.setStatus("Disponivel");
                System.out.println(
                        "Carro <" + carro.getModelo() + "> Devolvido, o valor a ser pago é: R$" + valorLocacao);
            } else {
                System.out.println("Carro não está locado!");
            }
        } else {
            System.out.println("Carro não encontrado!");
        }
    }
    // metodo de pesquisa que é usado em alguns metodo que retorna a posição qeu se
    // encontra o carro desejado
    static int pesquisar(ArrayList<Carro> list, String placa) {
        placa = placa.toUpperCase();
        for (int i = 0; i < list.size(); i++) {
            if ((list.get(i).getPlaca()).equalsIgnoreCase(placa)) {
                return i;
            }
        }
        return -1;
    }
    // metodo para ler o arquivo Carros, separando apos cada virgula os atributos e
    // criando o objeto de cada carro
    public static ArrayList<Carro> lerArquivo(String caminhoArquivo) {
        ArrayList<Carro> carros = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(caminhoArquivo)))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                // Dividir a linha usando a vírgula como delimitador
                String[] partes = linha.split(",");
                if (partes.length == 4) {
                    String modelo = partes[0];
                    String placa = partes[1];
                    String tipo = partes[2];
                    String status = partes[3];

                    switch (tipo.toLowerCase()) {
                        case "seda":
                            carros.add(new CarroSeda(placa, modelo, tipo, status));
                            break;
                        case "suv":
                            carros.add(new CarroSuv(placa, modelo, tipo, status));
                            break;
                        case "sport":
                            carros.add(new CarroSport(placa, modelo, tipo, status));
                            break;
                        default:
                            System.out.println("Tipo de carro inválido na linha: " + linha);
                            break;
                    }
                } else {
                    System.out.println("Formato inválido na linha: " + linha);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return carros;
    }
    // este metodo atualiza o status no arquivo para poder ver os carros disponiveis
    // para locação
    static void atualizarStatusNoArquivo(String placa, String novoStatus) {
        String caminhoArquivo = "Carros.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(new File(caminhoArquivo)))) {
            ArrayList<String> linhas = new ArrayList<>();
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length == 4 && partes[1].trim().equalsIgnoreCase(placa)) {
                    linhas.add(partes[0] + "," + partes[1] + "," + partes[2] + "," + novoStatus);
                } else {
                    linhas.add(linha);
                }
            }
            // Escrever as linhas atualizadas de volta no arquivo
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(caminhoArquivo)))) {
                for (String l : linhas) {
                    bw.write(l);
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}