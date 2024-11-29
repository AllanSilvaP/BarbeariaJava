package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import dao.AgendaDAO;
//pacotes
import dao.ClienteDAO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//data
import java.util.Date;
//imports calendarios
import java.util.Calendar;
import com.toedter.calendar.JDateChooser;

// Painel de Agendamento
public class CriaPainelAgendamento extends MontaPainel {
    // Telas
    private CardLayout mudaTelaAgendamento;
    private JPanel painelAgendamento;
    private JPanel painelPrincipal;
    // LOGIN E CADASTRO
    private JLabel anuncio;
    private JTextField loginUsuarioJ;
    private JPasswordField senhaUsuarioJ;
    // FORMS AGENDA
    private JTextField nomeUsuarioJ;
    // DE ESCOLHER
    private JComboBox<String> escolhaBarbeiro;
    private JComboBox<String> escolhaHorario;
    private JComboBox<String> tipoCorteJ;
    private String[] barbeiros = { "Samuel", "Pablo Rocha", "Clayton", "Vinicius" };
    private String[] horario = { "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00",
            "18:00", "19:00", "20:00" };
    private String[] Corte = { "Corte", "Barba", "Pigmentação", "Luzes", "Progressiva", "Limpeza de Pele" };
    // DATA E CALENDARIO
    private JLabel data;
    private Calendar calendario;
    private Date dataDoDia;
    private JDateChooser escolhaData;

    public CriaPainelAgendamento(CardLayout mudaTela, JPanel painelPrincipal) {
        this.painelPrincipal = painelPrincipal;
        this.mudaTelaAgendamento = mudaTela;
    }

    @Override
    public void iniciaPainel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(51, 51, 51));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título do painel
        anuncio = new JLabel("Seja bem-vindo cliente, digite sua conta ou se cadastre");
        anuncio.setForeground(Color.WHITE);
        anuncio.setFont(new Font("Arial", Font.BOLD, 18)); // Negrito e maior tamanho de fonte

        // Componentes de login
        loginUsuarioJ = new JTextField(20);
        senhaUsuarioJ = new JPasswordField(20);

        JLabel loginUsuario = new JLabel("E-MAIL:");
        loginUsuario.setForeground(Color.WHITE);
        JLabel senhaUsuario = new JLabel("Senha:");
        senhaUsuario.setForeground(Color.WHITE);

        JButton botaoLogin = new JButton("Login");
        botaoLogin.setPreferredSize(new Dimension(100, 20));

        JButton botaoCadastro = new JButton("Cadastre-se");
        botaoCadastro.setPreferredSize(new Dimension(100, 20));

        // Adicionando o título acima do formulário de login
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Título ocupa as duas colunas
        gbc.anchor = GridBagConstraints.CENTER;
        add(anuncio, gbc);

        // Adicionando o campo de E-MAIL
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(loginUsuario, gbc);

        gbc.gridx = 1;
        add(loginUsuarioJ, gbc);

        // Adicionando o campo de Senha
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(senhaUsuario, gbc);

        gbc.gridx = 1;
        add(senhaUsuarioJ, gbc);

        // Adicionando o botão de login
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(botaoLogin, gbc);

        // Adicionando o botão de cadastro
        gbc.gridy = 4;
        add(botaoCadastro, gbc);

        // Ação do botão de login
        botaoLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = loginUsuarioJ.getText();
                String senha = new String(senhaUsuarioJ.getPassword());

                ClienteDAO clienteDAO = new ClienteDAO();
                if (clienteDAO.autenticarConta(email, senha)) {
                    JOptionPane.showMessageDialog(null, "Login foi um sucesso!");

                    // Criar e adicionar o painel de agendamento
                    CriaPainelAgendamento criaPainelAgendamento = new CriaPainelAgendamento(mudaTelaAgendamento,
                            painelPrincipal);
                    JPanel painelAgendamento = criaPainelAgendamento.criaPainelAgendamento();

                    // Adiciona o painel de agendamento ao painelPrincipal
                    painelPrincipal.add(painelAgendamento, "Agendamento");

                    // Força o layout e exibe a tela de agendamento
                    painelPrincipal.revalidate();
                    painelPrincipal.repaint();
                    mudaTelaAgendamento.show(painelPrincipal, "Agendamento");
                } else {
                    JOptionPane.showMessageDialog(null, "Email ou senha errado(s)");
                }
            }
        });

        // Ação do botão de cadastro
        botaoCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Coleta dos dados através de JOptionPane
                String nome = JOptionPane.showInputDialog("Digite seu nome:");
                String email = loginUsuarioJ.getText();
                String telefone = JOptionPane.showInputDialog("Digite seu telefone:");
                String senha = new String(senhaUsuarioJ.getPassword());

                // Obter data e hora atuais (sem a necessidade de digitação)
                String dataHoraRegistro = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .format(new java.util.Date());

                ClienteDAO clienteDAO = new ClienteDAO();

                try {
                    // Chamada para o método de cadastro com nome, telefone, e data/hora automáticos
                    clienteDAO.cadastrarClienteEConta(nome, dataHoraRegistro, telefone, email, senha);
                    JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar.");
                }
            }
        });
    }

    // Método para criar painel de agendamento
    private JPanel criaPainelAgendamento() {
        // Criação do painel de agendamento
        JPanel painelAgenda = new JPanel();
        painelAgenda.setLayout(new GridBagLayout()); // Setando o layout aqui diretamente
        painelAgenda.setBackground(new Color(51, 51, 51));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Demais inicializações de componentes...
        calendario = Calendar.getInstance();
        dataDoDia = calendario.getTime();

        escolhaBarbeiro = new JComboBox<>(barbeiros);
        escolhaHorario = new JComboBox<>(horario);
        tipoCorteJ = new JComboBox<>(Corte);

        // Data a ser escolhida
        data = new JLabel("Escolha a data:");
        data.setForeground(Color.WHITE);
        escolhaData = new JDateChooser();
        escolhaData.setDate(dataDoDia);
        escolhaData.setDateFormatString("dd/MM/yyyy");
        escolhaData.setSelectableDateRange(dataDoDia, null);

        nomeUsuarioJ = new JTextField(20);

        JLabel textoAgendamento = new JLabel("Agende aqui - Marque seu horário");
        textoAgendamento.setHorizontalAlignment(JLabel.CENTER);
        textoAgendamento.setFont(new Font("Arial", Font.BOLD, 16));
        textoAgendamento.setForeground(Color.WHITE);

        JLabel nomeUsuario = new JLabel("Digite seu nome:");
        nomeUsuario.setForeground(Color.WHITE);
        JLabel barbeiro = new JLabel("Selecione o seu barbeiro:");
        barbeiro.setForeground(Color.WHITE);
        JLabel tipoCorte = new JLabel("Selecione o seu corte:");
        tipoCorte.setForeground(Color.WHITE);
        JLabel horarioUsuario = new JLabel("Horário:");
        horarioUsuario.setForeground(Color.WHITE);

        JButton btAgendar = new JButton("Agendar");
        btAgendar.setPreferredSize(new Dimension(100, 30));

        // Adicionando os componentes ao painelAgenda com GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        painelAgenda.add(textoAgendamento, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        painelAgenda.add(nomeUsuario, gbc);

        gbc.gridx = 1;
        painelAgenda.add(nomeUsuarioJ, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        painelAgenda.add(barbeiro, gbc);

        gbc.gridx = 1;
        painelAgenda.add(escolhaBarbeiro, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        painelAgenda.add(tipoCorte, gbc);

        gbc.gridx = 1;
        painelAgenda.add(tipoCorteJ, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        painelAgenda.add(horarioUsuario, gbc);

        gbc.gridx = 1;
        painelAgenda.add(escolhaHorario, gbc);

        gbc.gridy = 5;
        gbc.gridx = 0;
        painelAgenda.add(data, gbc);

        gbc.gridx = 1;
        painelAgenda.add(escolhaData, gbc);

        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        painelAgenda.add(btAgendar, gbc);

        // Definindo o horário disponível
        btAgendar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizaHorariosDisponiveis(); // Atualiza os horários
            }
        });

        return painelAgenda;
    }

    // Método para atualizar horários disponíveis
   private void atualizaHorariosDisponiveis() {
    AgendaDAO agendaDAO = new AgendaDAO();
    String nomeBarbeiroSelecionado = (String) escolhaBarbeiro.getSelectedItem();
    String dataSelecionada = new java.text.SimpleDateFormat("yyyy-MM-dd").format(escolhaData.getDate());
    int idBarbeiro = agendaDAO.getIdBarbeiroPeloNome(nomeBarbeiroSelecionado); 
    java.sql.Date data = java.sql.Date.valueOf(dataSelecionada);
    List<String> horariosDisponiveis = agendaDAO.getHorariosDisponiveis(idBarbeiro, data);

    escolhaHorario.removeAllItems();
    for (String horario : horariosDisponiveis) {
        escolhaHorario.addItem(horario);
    }
}
}