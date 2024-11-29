package view;

import javax.swing.*;
import java.awt.*;

public abstract class MontaPainel extends JPanel {
    public MontaPainel() {
        setLayout(new GridBagLayout()); // Centraliza os componentes por padrão
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margem
        setBackground(new Color(51, 51, 51)); // Cor de fundo cinza escuro (#333333)
    }

    public abstract void iniciaPainel();

    protected void fundoPainel(Color cor) {
        setBackground(cor);
    }
}