package view;

import javax.swing.*;
import java.awt.*;
// Imports para os vídeos
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

        // Título centralizado
        JLabel titulo = new JLabel("Bem-vindo ao Nosso Salão!");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(255, 255, 255));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0)); // Espaçamento inferior
        add(titulo, BorderLayout.NORTH);

        // Painel de vídeo
        painelVideo = new JFXPanel();
        iniciaVideo(painelVideo);
        JPanel videoPanel = new JPanel(new BorderLayout());
        videoPanel.setBackground(new Color(51, 51, 51));
        videoPanel.add(painelVideo, BorderLayout.CENTER);
        add(videoPanel, BorderLayout.CENTER);

        // Texto descritivo com estilo
        String mensagem = "<html><body style='text-align: justify; font-family: Arial; font-size: 14px; color: white;'>" +
                "<p>No nosso salão, oferecemos cortes de alta qualidade, feitos por profissionais experientes que estão " +
                "sempre atentos às últimas tendências. Valorizamos cada detalhe para garantir que você saia sempre " +
                "satisfeito, com o visual impecável e renovado.</p>" +
                "<p>Estamos localizados na <b>Ceilândia, QNN 22, LOTE 18</b>, em um ponto fácil de encontrar: em frente " +
                "ao CEISHOP e ao lado do ULTRABOX, na via da Fundação Bradesco.</p>" +
                "<p>Venha nos visitar e experimente um serviço de qualidade em um ambiente acolhedor e profissional!</p>" +
                "</body></html>";

        JTextPane textoM = new JTextPane();
        textoM.setContentType("text/html");
        textoM.setText(mensagem);
        textoM.setEditable(false);
        textoM.setOpaque(false);

        JScrollPane scrollTexto = new JScrollPane(textoM);
        scrollTexto.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        scrollTexto.setOpaque(false);
        scrollTexto.getViewport().setOpaque(false);
        add(scrollTexto, BorderLayout.SOUTH);
    }

    @Override
    public void iniciaVideo(JFXPanel video) {
        javafx.application.Platform.runLater(() -> {
            try {
                URL mediaUrl = getClass().getResource("/resources/VideoFundo2.mp4");
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