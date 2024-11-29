package view;

import javax.swing.*;
import java.awt.*;

// Painel de Preços
public class CriaPainelPrecos extends MontaPainel {
    private final JLabel Corte;
    private final JLabel Barba;
    private final JLabel Luzes;
    private final JLabel Pigmentado;
    private final JLabel Progressiva;
    private final JLabel LimpPele;
    private JLabel valor1;
    private JLabel valor2;
    private JLabel valor3;
    private JLabel valor4;
    private JLabel valor5;
    private JLabel valor6;

    public CriaPainelPrecos() {
        Corte = new JLabel();
        Barba = new JLabel();
        Luzes = new JLabel();
        Pigmentado = new JLabel();
        Progressiva = new JLabel();
        LimpPele = new JLabel();
    }

    @Override
    public void iniciaPainel() {
        setLayout(new GridLayout(4, 3, 10, 10));
        setBackground(new Color(51, 51, 51));

        // Configurar ícones de preços
        colocarIcone(Corte, "9009.jpg", 75);
        colocarIcone(Barba, "9009.jpg", 75);
        colocarIcone(Pigmentado, "9009.jpg", 75);
        colocarIcone(Luzes, "9009.jpg", 75);
        colocarIcone(Progressiva, "9009.jpg", 75);
        colocarIcone(LimpPele, "9009.jpg", 75);

        // Textos descritivos
        valor1 = customizarEstiloLabel("R$35\nCorte");
        valor2 = customizarEstiloLabel("R$35\nBarba");
        valor3 = customizarEstiloLabel("R$50\nPigmentado");
        valor4 = customizarEstiloLabel("R$100\nLuzes");
        valor5 = customizarEstiloLabel("R$100\nProgressiva");
        valor6 = customizarEstiloLabel("R$70\nLimpeza de Pele");

        // Adicionando componentes ao painel
        add(Corte);
        add(Barba);
        add(Pigmentado);
        add(valor1);
        add(valor2);
        add(valor3);
        add(Luzes);
        add(Progressiva);
        add(LimpPele);
        add(valor4);
        add(valor5);
        add(valor6);
    }

    private void colocarIcone(JLabel label, String caminhoArquivo, int tamanho) {
        ImageIcon icon = new ImageIcon(getClass().getResource("barbearia-frente.png"));
        Image img = icon.getImage().getScaledInstance(tamanho, tamanho, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(img));
        label.setHorizontalAlignment(JLabel.CENTER);
    }

    private JLabel customizarEstiloLabel(String text) {
        JLabel label = new JLabel("<html>" + text.replace("\n", "<br>") + "</html>");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 17));
        label.setForeground(Color.WHITE);
        return label;
    }
}
