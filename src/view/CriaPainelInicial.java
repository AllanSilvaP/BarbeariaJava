package view;

import javax.swing.*;
import java.awt.*;
//imports para os videos
import javafx.embed.swing.JFXPanel;
import java.net.URL;
import javafx.geometry.Pos;
import javafx.scene.media.MediaPlayer;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.scene.layout.StackPane;
 
 // Painel Inicial com Vídeo
 public class CriaPainelInicial extends MontaPainel implements Video {
    private JFXPanel painelVideo;

    @Override
    public void iniciaPainel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(51, 51, 51));

        painelVideo = new JFXPanel();
        iniciaVideo(painelVideo);
        JPanel videoPanel = new JPanel(new BorderLayout());
        videoPanel.setBackground(new Color(51, 51, 51));
        videoPanel.add(painelVideo, BorderLayout.CENTER);
        add(videoPanel, BorderLayout.CENTER);

        // Texto descritivo
        String mensagem = "Bem-vindo ao Nosso Salão!\n" +
                "No nosso salão, oferecemos cortes de alta qualidade, feitos por profissionais experientes que estão sempre atentos às últimas tendências.\n"
                +
                "Valorizamos cada detalhe para garantir que você saia sempre satisfeito, com o visual impecável e renovado.\n"
                +
                "Estamos localizados na Ceilândia, QNN 22, LOTE 18, em um ponto fácil de encontrar: em frente ao CEISHOP e ao lado do ULTRABOX, na via da Fundação Bradesco.\n"
                +
                "Venha nos visitar e experimente um serviço de qualidade em um ambiente acolhedor e profissional!";

        JTextArea textoM = new JTextArea(mensagem);
        textoM.setEditable(false);
        textoM.setLineWrap(true);
        textoM.setWrapStyleWord(true);
        textoM.setFont(new Font("Arial", Font.PLAIN, 14));
        textoM.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        textoM.setBackground(new Color(51, 51, 51));
        textoM.setForeground(Color.WHITE);

        add(new JScrollPane(textoM), BorderLayout.SOUTH);
    }

    @Override
    public void iniciaVideo(JFXPanel video) {
        javafx.application.Platform.runLater(() -> {
            try {
                URL mediaUrl = getClass().getResource("VideoFundo2.mp4");
                if (mediaUrl == null) {
                    throw new IllegalArgumentException("Arquivo não encontrado");
                }

                Media media = new Media(mediaUrl.toString());
                MediaPlayer mediaPlayer = new MediaPlayer(media);

                // Configurações do MediaView
                MediaView mediaView = new MediaView(mediaPlayer);
                mediaView.setPreserveRatio(false); // Permite que o vídeo ocupe todo o espaço
                mediaView.setFitWidth(1200); // Ajuste a largura desejada
                mediaView.setFitHeight(400); // Ajuste a altura desejada
                mediaView.setStyle("-fx-background-color: transparent;"); // Remove fundo

                // Configurações do StackPane
                StackPane root = new StackPane(mediaView);
                root.setStyle("-fx-background-color: black;"); // Fundo preto para evitar bordas visíveis
                StackPane.setAlignment(mediaView, Pos.CENTER); // Centraliza o vídeo

                Scene scene = new Scene(root, 0, 0);
                video.setScene(scene);

                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.setVolume(0.0);
                mediaPlayer.play();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        });
    }
}