package view;

import javax.swing.*;
import java.awt.*;

public class PainelMain extends JFrame {
    private final JPanel painelConteudo;
    private final JLabel Logo;
    private final JButton Agendar;
    private final JButton Precos;
    private final JButton AreaFunc;
    private final JButton Home;
    private final CardLayout mudaTela;

    public PainelMain(String titulo) {
        super(titulo);

        mudaTela = new CardLayout();
        painelConteudo = new JPanel(mudaTela);

        // Instâncias
        CriaPainelInicial painelInicio = new CriaPainelInicial();
        painelInicio.iniciaPainel();
        CriaPainelAgendamento painelAgendamento = new CriaPainelAgendamento(mudaTela, painelConteudo);
        painelAgendamento.iniciaPainel();
        CriaPainelPrecos painelPrecos = new CriaPainelPrecos();
        painelPrecos.iniciaPainel();
        CriaPainelAreaFunc painelAreaFunc = new CriaPainelAreaFunc(mudaTela, painelConteudo);
        painelAreaFunc.iniciaPainel();


        // Aqui fica a imagem
        ImageIcon icone = new ImageIcon(getClass().getResource("Barbearia-frente.png"));
        Image imgicon = icone.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);

        Logo = new JLabel(new ImageIcon(imgicon));

        // Painéis adicionais para o CardLayout na área de conteúdo
        JPanel divPainelHome = painelInicio;
        painelConteudo.add(divPainelHome, "Inicial");
        JPanel divPainelAgendamento = painelAgendamento;
        painelConteudo.add(divPainelAgendamento, "Agendar");
        JPanel divPainelPrecos = painelPrecos;
        painelConteudo.add(divPainelPrecos, "Precos");
        JPanel divPainelAreaFuncionario = painelAreaFunc;
        painelConteudo.add(divPainelAreaFuncionario, "AreaFunc");

        // Criação dos botões
        Agendar = new JButton("Agende-ja");
        Agendar.setOpaque(false);
        Precos = new JButton("Nossos preços");
        AreaFunc = new JButton("Área do Funcionário");
        Home = new JButton("Página Inicial");

        // Adiciona ActionListeners para mostrar as telas adicionais
        Agendar.addActionListener(e -> mudaTela.show(painelConteudo, "Agendar"));
        Precos.addActionListener(e -> mudaTela.show(painelConteudo, "Precos"));
        AreaFunc.addActionListener(e -> mudaTela.show(painelConteudo, "AreaFunc"));
        Home.addActionListener(e -> mudaTela.show(painelConteudo, "Inicial"));

        // Painel para os botões
        JPanel painelBotoes = new JPanel(new GridLayout(1, 4, 5, 5));
        painelBotoes.add(Logo, BorderLayout.EAST);
        painelBotoes.add(Home);
        painelBotoes.add(Agendar);
        painelBotoes.add(Precos);
        painelBotoes.add(AreaFunc);
        painelBotoes.setBackground(new Color(79, 79, 79));

        // Adiciona tudo ao frame principal
        add(painelBotoes, BorderLayout.NORTH);
        add(painelConteudo, BorderLayout.CENTER);

        mudaTela.show(painelConteudo, "Inicial"); 
    }
}
