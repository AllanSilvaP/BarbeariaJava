package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

//outras pastas
import dao.ContaDAO;
import dao.EstoqueDAO;
import dao.BarbeiroDAO;
import dao.ServicoDAO;
import model.Produto;
import model.Servico;

// Painel de Área Funcional do Funcionário
public class CriaPainelAreaFunc extends MontaPainel {
    private JLabel anuncio;
    private JTextField loginUsuarioJ;
    private JPasswordField senhaUsuarioJ;
    private CardLayout mudaTelaFunc;
    private JPanel painelFunc1;
    private JPanel painelPrincipal;

    // bt das funcoes que vou utilizar posteriormente
    private JButton botaoGerenciarAgendamentos = new JButton("Gerenciar Agendamentos");
    private JButton btGerenciarEstoque = new JButton("Gerenciar Estoque");
    private JButton botaoSair = new JButton("Sair");

    // bt metodo gerenciar
    private JButton btAddEstoque = new JButton("Adicionar ao estoque");
    private JButton btExcluirEstoque = new JButton("Retirar do estoque");
    private JButton btVerificarEstoque = new JButton("Verificar Estoque");
    private JButton btGerenciaBarbeiro = new JButton("Gerenciar Barbeiros");
    private JButton btGerenciarServicos = new JButton("Gerenciar Servicos");
    private JButton btSair = new JButton("Sair");

    public CriaPainelAreaFunc(CardLayout mudarTela, JPanel painelReferenciado) {
        this.mudaTelaFunc = mudarTela;
        this.painelPrincipal = painelReferenciado;
    }

    @Override
    public void iniciaPainel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(51, 51, 51));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título do painel
        anuncio = new JLabel("Seja bem-vindo ao menu do funcioanario, digite sua conta ou se cadastre");
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
        // Listerners para bd

        botaoLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = loginUsuarioJ.getText();
                String senha = new String(senhaUsuarioJ.getPassword());

                ContaDAO contaDAO = new ContaDAO();
                if (contaDAO.autenticarConta(email, senha)) {
                    JOptionPane.showMessageDialog(null, "Login foi um sucesso!");
                    // muda para nova tela
                    painelPrincipal.add(criaPainelFunc1(), "AreaFunc");
                    painelPrincipal.add(criaGerenciaAgenda(), "Agenda");
                    painelPrincipal.add(criaGerenciaEstoque(), "Estoque");
                    painelPrincipal.add(criaGerenciaBarbeiro(), "Barbeiro");
                    painelPrincipal.add(criaGerenciaServico(), "Servico");
                    painelPrincipal.revalidate();
                    painelPrincipal.repaint();
                    mudaTelaFunc.show(painelPrincipal, "AreaFunc");

                    botaoGerenciarAgendamentos.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            mudaTelaFunc.show(painelPrincipal, "Agenda");
                        }
                    });

                    btGerenciarEstoque.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            mudaTelaFunc.show(painelPrincipal, "Estoque");
                        }
                    });

                    btGerenciaBarbeiro.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            mudaTelaFunc.show(painelPrincipal, "Barbeiro");
                        }
                    });

                    btGerenciarServicos.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            mudaTelaFunc.show(painelPrincipal, "Servico");
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Email ou senha errado(s)");
                }
            }
        });

        botaoCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = loginUsuarioJ.getText();
                String senha = new String(senhaUsuarioJ.getPassword());

                ContaDAO contaDAO = new ContaDAO();

                try {
                    contaDAO.salvarConta(email, senha);
                    JOptionPane.showMessageDialog(null, "Cadastrado com sucesso");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro");
                }
            }
        });
    }

    private JPanel criaPainelFunc1() {
        setBackground(new Color(51, 51, 51));
        painelFunc1 = new JPanel();
        painelFunc1.setLayout(new BoxLayout(painelFunc1, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Selecione o que desejar!");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        painelFunc1.add(titulo);
        painelFunc1.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botões de navegação
        botaoGerenciarAgendamentos.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelFunc1.add(botaoGerenciarAgendamentos);
        painelFunc1.add(Box.createRigidArea(new Dimension(0, 10)));

        btGerenciarEstoque.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelFunc1.add(btGerenciarEstoque);
        painelFunc1.add(Box.createRigidArea(new Dimension(0, 10)));

        btGerenciaBarbeiro.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelFunc1.add(btGerenciaBarbeiro);
        painelFunc1.add(Box.createRigidArea(new Dimension(0, 10)));

        btGerenciarServicos.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelFunc1.add(btGerenciarServicos);
        painelFunc1.add(Box.createRigidArea(new Dimension(0, 10)));

        botaoSair.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoSair.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Você saiu da área funcional.");

            CriaPainelAreaFunc novoPainelFunc = new CriaPainelAreaFunc(mudaTelaFunc, painelPrincipal);
            novoPainelFunc.iniciaPainel();
            painelPrincipal.add(novoPainelFunc, "AreaFunc");

            // Revalida e repinta o painel principal
            painelPrincipal.revalidate();
            painelPrincipal.repaint();

            mudaTelaFunc.show(painelPrincipal, "Inicial");
        });
        painelFunc1.add(botaoSair);
        return painelFunc1;
    }

    private JPanel criaGerenciaAgenda() {
        setBackground(new Color(51, 51, 51));
        JPanel gerenciaAgenda = new JPanel();
        return gerenciaAgenda;
    }

    private JPanel criaGerenciaEstoque() {
        setBackground(new Color(51, 51, 51));
        JPanel painelEstoque = new JPanel();
        painelEstoque.setBackground(getBackground());
        painelEstoque.setLayout(new BoxLayout(painelEstoque, BoxLayout.Y_AXIS));

        btVerificarEstoque.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelEstoque.add(btVerificarEstoque);
        painelEstoque.add(Box.createRigidArea(new Dimension(0, 10)));

        btVerificarEstoque.addActionListener(e -> {
            try {
                // Criação do painel para exibir a tabela
                JPanel painelTabela = new JPanel(new BorderLayout());

                // Criação do modelo da tabela
                EstoqueDAO estoqueDAO = new EstoqueDAO();
                List<Produto> produtos = estoqueDAO.listarEstoque(); // Método para listar todos os produtos

                // Configuração das colunas da tabela
                String[] colunas = { "ID", "Nome", "Quantidade", "Preço de Compra", "Preço de Venda",
                        "Data de Entrada" };
                String[][] dados = new String[produtos.size()][6];

                // Preenchendo os dados da tabela
                for (int i = 0; i < produtos.size(); i++) {
                    Produto produto = produtos.get(i);
                    dados[i][0] = String.valueOf(produto.getId());
                    dados[i][1] = produto.getNome();
                    dados[i][2] = String.valueOf(produto.getQuantidade());
                    dados[i][3] = String.format("%.2f", produto.getPrecoCompra());
                    dados[i][4] = String.format("%.2f", produto.getPrecoVenda());
                    dados[i][5] = produto.getDataEntrada();
                }

                // Criando JTable com os dados
                JTable tabelaEstoque = new JTable(dados, colunas);
                tabelaEstoque.setEnabled(false); // Desativa edição direta
                JScrollPane scrollPane = new JScrollPane(tabelaEstoque);

                painelTabela.add(scrollPane, BorderLayout.CENTER);

                // Exibindo a tabela em um JOptionPane
                JOptionPane.showMessageDialog(null, painelTabela, "Estoque Atual", JOptionPane.PLAIN_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao verificar o estoque: " + ex.getMessage());
            }
        });

        // Exibir Estoque
        btVerificarEstoque.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Verificando Estoque...");
        });

        // Adicionar Produto ao Estoque
        btAddEstoque.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelEstoque.add(btAddEstoque);
        painelEstoque.add(Box.createRigidArea(new Dimension(0, 10)));

        btAddEstoque.addActionListener(e -> {
            JPanel painelAdd = new JPanel(new GridLayout(6, 2, 10, 10));
            painelAdd.add(new JLabel("Nome do Produto:"));
            JTextField txtNomeProduto = new JTextField();
            painelAdd.add(txtNomeProduto);

            painelAdd.add(new JLabel("Quantidade:"));
            JTextField txtQuantidade = new JTextField();
            painelAdd.add(txtQuantidade);

            painelAdd.add(new JLabel("Preço de Compra:"));
            JTextField txtPrecoCompra = new JTextField();
            painelAdd.add(txtPrecoCompra);

            painelAdd.add(new JLabel("Preço de Venda:"));
            JTextField txtPrecoVenda = new JTextField();
            painelAdd.add(txtPrecoVenda);

            painelAdd.add(new JLabel("Data de Entrada:"));
            JTextField txtDataEntrada = new JTextField("yyyy-MM-dd HH:mm:ss");
            painelAdd.add(txtDataEntrada);

            int result = JOptionPane.showConfirmDialog(null, painelAdd, "Adicionar ao Estoque",
                    JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String nomeProduto = txtNomeProduto.getText();
                    int quantidade = Integer.parseInt(txtQuantidade.getText());
                    double precoCompra = Double.parseDouble(txtPrecoCompra.getText());
                    double precoVenda = Double.parseDouble(txtPrecoVenda.getText());
                    String dataEntrada = txtDataEntrada.getText();

                    EstoqueDAO estoqueDAO = new EstoqueDAO();
                    estoqueDAO.addEstoque(nomeProduto, quantidade, precoCompra, precoVenda, dataEntrada);

                    JOptionPane.showMessageDialog(null, "Produto adicionado com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao adicionar produto: " + ex.getMessage());
                }
            }
        });

        // Excluir Produto do Estoque
        btExcluirEstoque.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelEstoque.add(btExcluirEstoque);
        painelEstoque.add(Box.createRigidArea(new Dimension(0, 10)));

        btExcluirEstoque.addActionListener(e -> {
            JTextField txtIdProdutoExcluir = new JTextField();
            int result = JOptionPane.showConfirmDialog(null, new Object[] {
                    "ID do Produto:", txtIdProdutoExcluir }, "Excluir Produto", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    int idProduto = Integer.parseInt(txtIdProdutoExcluir.getText());
                    EstoqueDAO estoqueDAO = new EstoqueDAO();
                    estoqueDAO.excluirEstoque(idProduto);

                    JOptionPane.showMessageDialog(null, "Produto excluído com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao excluir produto: " + ex.getMessage());
                }
            }
        });

        // Botão Sair
        btSair.setAlignmentX(Component.CENTER_ALIGNMENT);
        btSair.addActionListener(e -> mudaTelaFunc.show(painelPrincipal, "AreaFunc"));
        painelEstoque.add(btSair);

        return painelEstoque;
    }

    private JPanel criaGerenciaBarbeiro() {
        BarbeiroDAO barbeiroDAO = new BarbeiroDAO();
        setBackground(new Color(51, 51, 51));
        JPanel painelBarbeiro = new JPanel();
        painelBarbeiro.setBackground(getBackground());
        painelBarbeiro.setLayout(new BorderLayout());

        JLabel tituloLabel = new JLabel("Gerenciar Barbeiro", JLabel.CENTER);
        tituloLabel.setForeground(Color.WHITE);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 18));
        painelBarbeiro.add(tituloLabel, BorderLayout.NORTH);

        // Painel para adicionar novo barbeiro
        JPanel painelFormulario = new JPanel(new GridLayout(6, 2, 5, 10));
        painelFormulario.setBackground(new Color(51, 51, 51));

        // Campos de texto para os dados do barbeiro
        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setForeground(Color.WHITE); // Cor do texto do label
        painelFormulario.add(nomeLabel);
        JTextField nomeField = new JTextField();
        nomeField.setPreferredSize(new Dimension(200, 20)); // Definir o tamanho do campo
        painelFormulario.add(nomeField);

        JLabel especialidadeLabel = new JLabel("Especialidade:");
        especialidadeLabel.setForeground(Color.WHITE);
        painelFormulario.add(especialidadeLabel);
        JTextField especialidadeField = new JTextField();
        especialidadeField.setPreferredSize(new Dimension(200, 20));
        painelFormulario.add(especialidadeField);

        JLabel telefoneLabel = new JLabel("Telefone:");
        telefoneLabel.setForeground(Color.WHITE);
        painelFormulario.add(telefoneLabel);
        JTextField telefoneField = new JTextField();
        telefoneField.setPreferredSize(new Dimension(200, 20));
        painelFormulario.add(telefoneField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        painelFormulario.add(emailLabel);
        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(200, 20));
        painelFormulario.add(emailField);

        JLabel senhaLabel = new JLabel("Senha:");
        senhaLabel.setForeground(Color.WHITE);
        painelFormulario.add(senhaLabel);
        JPasswordField senhaField = new JPasswordField();
        senhaField.setPreferredSize(new Dimension(200, 20));
        painelFormulario.add(senhaField);

        // Botão para cadastrar barbeiro
        JButton btnCadastrar = new JButton("Cadastrar Barbeiro");
        btnCadastrar.setBackground(new Color(0, 153, 0));
        btnCadastrar.setForeground(Color.WHITE);
        painelFormulario.add(btnCadastrar);

        // Botão para excluir barbeiro
        JButton btnExcluir = new JButton("Excluir Barbeiro");
        btnExcluir.setBackground(new Color(204, 0, 0));
        btnExcluir.setForeground(Color.WHITE);
        painelFormulario.add(btnExcluir);

        // Ação para cadastrar barbeiro
        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String especialidade = especialidadeField.getText();
                String telefone = telefoneField.getText();
                String email = emailField.getText();
                String senha = new String(senhaField.getPassword());

                // Chama o método para cadastrar barbeiro e conta
                barbeiroDAO.cadastrarBarbeiroEConta(nome, especialidade, telefone, email, senha);
            }
        });

        // Ação para excluir barbeiro
        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idBarbeiroStr = JOptionPane.showInputDialog(null, "Informe o ID do Barbeiro a ser excluído:");

                try {
                    int idBarbeiro = Integer.parseInt(idBarbeiroStr);
                    barbeiroDAO.excluirBarbeiro(idBarbeiro);
                    JOptionPane.showMessageDialog(null, "Barbeiro excluído com sucesso!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "ID inválido!");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao excluir barbeiro.");
                    ex.printStackTrace();
                }
            }
        });

        painelBarbeiro.add(painelFormulario, BorderLayout.CENTER);

        return painelBarbeiro;
    }

    private JPanel criaGerenciaServico() {
        setBackground(new Color(51, 51, 51));
        JPanel painelServico = new JPanel();
        painelServico.setBackground(getBackground());
        painelServico.setLayout(new BoxLayout(painelServico, BoxLayout.Y_AXIS));
    
        // Título
        JLabel tituloLabel = new JLabel("Gerenciar Serviços", JLabel.CENTER);
        tituloLabel.setForeground(Color.WHITE);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 18));
        painelServico.add(tituloLabel);
        painelServico.add(Box.createRigidArea(new Dimension(0, 10)));
    
        // Botão para adicionar serviço
        JButton btAdicionarServico = new JButton("Adicionar Serviço");
        btAdicionarServico.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelServico.add(btAdicionarServico);
        painelServico.add(Box.createRigidArea(new Dimension(0, 10)));
    
        // Ação do botão Adicionar
        btAdicionarServico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel painelAdd = new JPanel(new GridLayout(5, 2, 10, 10));
                painelAdd.add(new JLabel("Nome do Serviço:"));
                JTextField txtNomeServico = new JTextField();
                painelAdd.add(txtNomeServico);
    
                painelAdd.add(new JLabel("Descrição:"));
                JTextField txtDescricaoServico = new JTextField();
                painelAdd.add(txtDescricaoServico);
    
                painelAdd.add(new JLabel("Duração (HH:mm):"));
                JTextField txtDuracaoServico = new JTextField();
                painelAdd.add(txtDuracaoServico);
    
                painelAdd.add(new JLabel("Preço:"));
                JTextField txtPrecoServico = new JTextField();
                painelAdd.add(txtPrecoServico);
    
                int result = JOptionPane.showConfirmDialog(null, painelAdd, "Adicionar Serviço", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String nome = txtNomeServico.getText();
                    String descricao = txtDescricaoServico.getText();
                    String duracao = txtDuracaoServico.getText();
                    double preco = Double.parseDouble(txtPrecoServico.getText());
    
                    try {
                        ServicoDAO servicoDAO = new ServicoDAO();
                        servicoDAO.adicionarServico(nome, descricao, duracao, preco);
                        JOptionPane.showMessageDialog(null, "Serviço adicionado com sucesso!");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao adicionar serviço: " + ex.getMessage());
                    }
                }
            }
        });
    
        // Botão para excluir serviço
        JButton btExcluirServico = new JButton("Excluir Serviço");
        btExcluirServico.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelServico.add(btExcluirServico);
        painelServico.add(Box.createRigidArea(new Dimension(0, 10)));
    
        // Ação do botão Excluir
        btExcluirServico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField txtIdServicoExcluir = new JTextField();
                int result = JOptionPane.showConfirmDialog(null, new Object[] {
                    "ID do Serviço:", txtIdServicoExcluir }, "Excluir Serviço", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        int idServico = Integer.parseInt(txtIdServicoExcluir.getText());
                        ServicoDAO servicoDAO = new ServicoDAO();
                        servicoDAO.excluirServico(idServico);
                        JOptionPane.showMessageDialog(null, "Serviço excluído com sucesso!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao excluir serviço: " + ex.getMessage());
                    }
                }
            }
        });
    
        // Botão para visualizar serviços
        JButton btVerificarServico = new JButton("Verificar Serviços");
        btVerificarServico.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelServico.add(btVerificarServico);
        painelServico.add(Box.createRigidArea(new Dimension(0, 10)));
    
        // Ação do botão Verificar
        btVerificarServico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Exibir a tabela de serviços
                    JPanel painelTabela = new JPanel(new BorderLayout());
                    ServicoDAO servicoDAO = new ServicoDAO();
                    List<Servico> servicos = servicoDAO.listarServicos();
    
                    String[] colunas = { "ID", "Nome", "Descrição", "Duração", "Preço" };
                    String[][] dados = new String[servicos.size()][5];
    
                    for (int i = 0; i < servicos.size(); i++) {
                        Servico servico = servicos.get(i);
                        dados[i][0] = String.valueOf(servico.getId());
                        dados[i][1] = servico.getNome();
                        dados[i][2] = servico.getDescricao();
                        dados[i][3] = servico.getDuracao();
                        dados[i][4] = String.format("%.2f", servico.getPreco());
                    }
    
                    JTable tabelaServico = new JTable(dados, colunas);
                    tabelaServico.setEnabled(false);
                    JScrollPane scrollPane = new JScrollPane(tabelaServico);
    
                    painelTabela.add(scrollPane, BorderLayout.CENTER);
                    JOptionPane.showMessageDialog(null, painelTabela, "Serviços Cadastrados", JOptionPane.PLAIN_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao verificar serviços: " + ex.getMessage());
                }
            }
        });
    
        // Botão para alterar serviço (integrado ao painel de serviços)
        JButton btAlterarServico = new JButton("Alterar Serviço");
        btAlterarServico.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelServico.add(btAlterarServico);
        painelServico.add(Box.createRigidArea(new Dimension(0, 10)));
    
        btAlterarServico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField txtIdServicoAlterar = new JTextField();
                int result = JOptionPane.showConfirmDialog(null, new Object[] {
                    "ID do Serviço a Alterar:", txtIdServicoAlterar }, "Alterar Serviço", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        int idServico = Integer.parseInt(txtIdServicoAlterar.getText());
                        ServicoDAO servicoDAO = new ServicoDAO();
                        Servico servico = servicoDAO.buscarServicoPorId(idServico);
    
                        if (servico != null) {
                            // Exibe os dados do serviço para alteração
                            JPanel painelAlterar = new JPanel(new GridLayout(5, 2, 10, 10));
                            painelAlterar.add(new JLabel("Nome do Serviço:"));
                            JTextField txtNomeAlterar = new JTextField(servico.getNome());
                            painelAlterar.add(txtNomeAlterar);
    
                            painelAlterar.add(new JLabel("Descrição:"));
                            JTextField txtDescricaoAlterar = new JTextField(servico.getDescricao());
                            painelAlterar.add(txtDescricaoAlterar);
    
                            painelAlterar.add(new JLabel("Duração (HH:mm):"));
                            JTextField txtDuracaoAlterar = new JTextField(servico.getDuracao());
                            painelAlterar.add(txtDuracaoAlterar);
    
                            painelAlterar.add(new JLabel("Preço:"));
                            JTextField txtPrecoAlterar = new JTextField(String.valueOf(servico.getPreco()));
                            painelAlterar.add(txtPrecoAlterar);
    
                            int alterResult = JOptionPane.showConfirmDialog(null, painelAlterar, "Alterar Serviço", JOptionPane.OK_CANCEL_OPTION);
                            if (alterResult == JOptionPane.OK_OPTION) {
                                servicoDAO.alterarServico(idServico, txtNomeAlterar.getText(), txtDescricaoAlterar.getText(),
                                                          txtDuracaoAlterar.getText(), Double.parseDouble(txtPrecoAlterar.getText()));
                                JOptionPane.showMessageDialog(null, "Serviço alterado com sucesso!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Serviço não encontrado!");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao alterar serviço: " + ex.getMessage());
                    }
                }
            }
        });
    
        return painelServico;
    }    
}