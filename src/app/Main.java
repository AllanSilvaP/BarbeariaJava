package app;

import javax.swing.JFrame;
import view.PainelMain;

public class Main {
    public static void main (String[] args) {
        PainelMain janelaMain = new PainelMain("Barbearia Aguia Real");
        janelaMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janelaMain.setSize(1000, 600);
        janelaMain.setVisible(true);
    }
}