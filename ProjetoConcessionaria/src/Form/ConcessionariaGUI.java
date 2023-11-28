package Form;

import TrabLPOO.Carro;
import TrabLPOO.CarroSeda;
import TrabLPOO.CarroSport;
import TrabLPOO.CarroSuv;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ConcessionariaGUI {
    private JFrame frame;
    private ArrayList<Carro> carList;

    public ConcessionariaGUI(ArrayList<Carro> carList) {
        this.carList = carList;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Concessionaria");
        frame.setSize(900 , 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Centralizar o JFrame no meio da tela
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        int x = (screenWidth - frameWidth) / 2;
        int y = (screenHeight - frameHeight) / 2;

        frame.setLocation(x, y);
        
        // Criar um gradiente para o background
        Color color1 = new Color(105,105,105);  // Cor mais escura
        Color color2 = new Color(128,128,128);  // Cor mais clara
        frame.setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setPaint(new GradientPaint(
                        new Point2D.Float(0, 0), color1,
                        new Point2D.Float(0, getHeight()), color2));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        });
        
        JPanel panel = new JPanel();
        panel.setOpaque(false); // Torna o painel transparente para exibir o gradiente do fundo
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Adiciona o texto "VIX VEICULOS" acima da logo
        JLabel vixLabel = new JLabel("CONCESSIONARIA VIX VEICULOS", JLabel.CENTER);
        vixLabel.setForeground(Color.white);
        vixLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        panel.add(vixLabel, gbc);

        JButton catalogButton = createStyledButton("Catálogo", "catalogo.png");
        catalogButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                catalogo();
            }
        });
        panel.add(catalogButton, gbc);

        JButton testButton = createStyledButton("Testar Carro", "test.png");
        testButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                testar();
            }
        });
        panel.add(testButton, gbc);

        JButton rentButton = createStyledButton("Alugar", "alugar.png");
        rentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                alugar();
            }
        });
        panel.add(rentButton, gbc);

        JButton returnButton = createStyledButton("Devolver", "devolver.png");
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                devolver();
            }
        });
        panel.add(returnButton, gbc);

        JButton exitButton = createStyledButton("Sair", "sair.png");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel.add(exitButton, gbc);

        frame.setVisible(true);
    }

    private JButton createStyledButton(String text, String iconFileName) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(250, 80));
        button.setBackground(new Color(255,250,250));  // Cor de fundo do botão (um pouco mais clara)
        button.setForeground(Color.black); //cor do texto
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        
          if (iconFileName != null) {
            ImageIcon icon = new ImageIcon(getClass().getResource(iconFileName));
            Image scaledImage = icon.getImage().getScaledInstance(-1, 40, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
            // Posiciona o ícone no canto esquerdo
            button.setHorizontalAlignment(SwingConstants.LEFT);
             // Adiciona um espaço entre o ícone e o texto
            button.setIconTextGap(10);
        }
        return button;
    }
  
    

    private void catalogo() {
        StringBuilder catalog = new StringBuilder();
        for (Carro carro : carList) {
            catalog.append("Modelo: ").append(carro.getModelo()).append(", Placa: ").append(carro.getPlaca())
                    .append(", Tipo: ").append(carro.getTipo()).append(", status: ").append(carro.getStatus())
                    .append("\n");
        }
        JOptionPane.showMessageDialog(frame, catalog.toString(), "Catálogo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void testar() {
        String placa = JOptionPane.showInputDialog(frame, "Digite a placa do carro que deseja testar:");
        int position = pesquisar(carList, placa);
        if (position != -1) {
            Carro carro = carList.get(position);
            JOptionPane.showMessageDialog(frame, "Resultado do teste: " + carro.teste(), "Testar Carro",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "Carro não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alugar() {
        String placa = JOptionPane.showInputDialog(frame, "Digite a placa do carro que deseja locar:");
        int position = pesquisar(carList, placa);
        if (position != -1) {
            Carro carro = carList.get(position);
            if (!carro.isLocado()) {
                int numDias = Integer.parseInt(
                        JOptionPane.showInputDialog(frame, "Digite o número de dias que o carro será locado:"));
                carro.locar(numDias);
                atualizarStatusNoArquivo(carro.getPlaca(), "Alugado");
                carro.setStatus("Alugado");
                JOptionPane.showMessageDialog(frame,
                        "Carro <" + carro.getModelo() + "> Placa <" + carro.getPlaca() + "> Locado",
                        "Aluguel Efetuado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Carro já está locado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Carro não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void devolver() {
        String placa = JOptionPane.showInputDialog(frame, "Digite a placa do carro que deseja devolver:");
        int position = pesquisar(carList, placa);
        double valorLocacao = 0;
        if (position != -1) {
            Carro carro = carList.get(position);
            if (carro.isLocado()) {
                valorLocacao = carro.devolver();
                atualizarStatusNoArquivo(carro.getPlaca(), "Disponivel");
                carro.setStatus("Disponivel");
                JOptionPane.showMessageDialog(frame,
                        "Carro <" + carro.getModelo() + "> Devolvido, o valor a ser pago é: R$" + valorLocacao,
                        "Devolução Efetuada", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Carro não está locado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Carro não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int pesquisar(ArrayList<Carro> list, String placa) {
        placa = placa.toUpperCase();
        for (int i = 0; i < list.size(); i++) {
            if ((list.get(i).getPlaca()).equalsIgnoreCase(placa)) {
                return i;
            }
        }
        return -1;
    }

    private void atualizarStatusNoArquivo(String placa, String novoStatus) {
        // Implementar a atualização do status no arquivo aqui, se necessário
    }

    public static void main(String[] args) {
        String caminhoArquivo = "Carros.txt";
        ArrayList<Carro> list = lerArquivo(caminhoArquivo);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ConcessionariaGUI(list).frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

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

    
}
