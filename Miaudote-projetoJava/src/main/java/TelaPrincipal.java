import javax.swing.*; // Importa os componentes visuais do Swing.
import java.awt.*; // Importa recursos de layout, cores, fontes e dimensões.

public class TelaPrincipal extends JFrame { // Define a janela principal do Miaudote.

    // Cores usadas na interface para manter o visual padronizado.
    private final Color rosaFundo = new Color(255, 247, 250);
    private final Color rosaBotao = new Color(217, 107, 150);
    private final Color rosaTexto = new Color(213, 5, 87);

    public TelaPrincipal() { // Construtor: configu+ra a janela principal.
        setTitle("Miaudote - Adoção de Gatinhos"); // Define o título da janela.
        setSize(600, 400); // Define largura e altura da janela.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Encerra o programa ao fechar esta janela.
        setLocationRelativeTo(null); // Centraliza a janela na tela.

        JPanel painel = new JPanel(); // Cria um painel para organizar os componentes.
        painel.setBackground(rosaFundo); // Define a cor de fundo do painel.
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS)); // Organiza os componentes verticalmente.
        painel.setBorder(
                BorderFactory.createEmptyBorder(35, 45, 35, 45) // Cria margens internas no painel.
        );

        JLabel titulo = new JLabel("Bem-vindo ao Miaudote! ^•ﻌ•^ ♡"); // Cria o título.
        titulo.setFont(new Font("SansSerif", Font.BOLD, 34)); // Define fonte, estilo e tamanho.
        titulo.setForeground(rosaTexto); // Define a cor do texto.
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza o título horizontalmente.

        JLabel subtitulo = new JLabel(
                "Um novo lar, um novo amor!" // Texto de apoio exibido abaixo do título.
        );
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 16)); // Define a aparência do subtítulo.
        subtitulo.setForeground(rosaTexto); // Define a cor do subtítulo.
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza o subtítulo.

        JLabel mensagem = new JLabel(
                "Todo gatinho merece amor!" // Mensagem de destaque na tela.
        );
        mensagem.setFont(new Font("SansSerif", Font.BOLD, 20)); // Define fonte e tamanho da mensagem.
        mensagem.setForeground(rosaTexto); // Define a cor da mensagem.
        mensagem.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza a mensagem.

        JButton botaoGatinhos = new JButton(
                "Conhecer os gatinhos" // Texto do botão que abre a listagem.
        );

        JButton botaoCadastro = new JButton(
                "Cadastrar gatinho" // Texto do botão que abre o formulário.
        );

        estilizarBotao(botaoGatinhos); // Aplica o estilo ao botão de listagem.
        estilizarBotao(botaoCadastro); // Aplica o estilo ao botão de cadastro.
        botaoGatinhos.addActionListener(e -> abrirTelaGatinhos()); // Executa a listagem ao clicar.
        botaoCadastro.addActionListener(e -> abrirTelaCadastro()); // Abre o cadastro ao clicar.

        titulo.setFont(new Font("SansSerif", Font.BOLD, 24)); // Ajusta novamente o tamanho do título.
        titulo.setForeground(rosaTexto); // Mantém a cor do título.

        // Adiciona os componentes ao painel na ordem em que devem aparecer.
        painel.add(titulo);
        painel.add(Box.createVerticalStrut(5)); // Adiciona um pequeno espaço vertical.
        painel.add(subtitulo);
        painel.add(Box.createVerticalStrut(55)); // Cria espaço entre o subtítulo e a mensagem.
        painel.add(mensagem);
        painel.add(Box.createVerticalStrut(30)); // Cria espaço antes dos botões.
        painel.add(botaoGatinhos);
        painel.add(Box.createVerticalStrut(15)); // Cria espaço entre os botões.
        painel.add(botaoCadastro);

        add(painel); // Adiciona o painel à janela principal.
    }

    private void estilizarBotao(JButton botao) { // Centraliza e padroniza a aparência dos botões.
        botao.setFont(new Font("SansSerif", Font.BOLD, 15)); // Define fonte e tamanho do texto.
        botao.setForeground(Color.WHITE); // Define a cor do texto como branco.
        botao.setBackground(rosaBotao); // Define a cor de fundo do botão.
        botao.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza o botão.
        botao.setMaximumSize(new Dimension(300, 45)); // Limita o tamanho máximo do botão.
        botao.setPreferredSize(new Dimension(300, 45)); // Define o tamanho preferido.
        botao.setFocusPainted(false); // Remove o contorno de foco visual.
        botao.setBorderPainted(false); // Remove a borda padrão.
        botao.setOpaque(true); // Faz o fundo colorido ser exibido.
    }


    private void abrirTelaGatinhos() {
        try {
            GatoDAO dao = new GatoDAO(ConexaoBanco.obterTemplate());

            // Busca os gatinhos cadastrados.
            java.util.List<Gato> gatos = dao.buscarTodos();

            // Define as colunas da tabela.
            String[] colunas = {
                    "Nome", "Idade (meses)", "Sexo",
                    "Personalidade","Aparência", "Vacinado", "Cuidados Especiais", "Adotado"
            };

            // Cria o modelo da tabela.
            javax.swing.table.DefaultTableModel modelo =
                    new javax.swing.table.DefaultTableModel(colunas, 0) {
                        @Override
                        public boolean isCellEditable(int linha, int coluna) {
                            return false;
                        }
                    };

            // Adiciona os dados dos gatinhos à tabela.
            for (Gato gato : gatos) {
                modelo.addRow(new Object[]{
                        gato.getNome(),
                        gato.getIdadeMeses(),
                        gato.getSexo(),
                        gato.getPersonalidade(),
                        gato.getDescricaoAparencia(),
                        gato.getVacinado() ? "Sim" : "Não",
                        gato.getPrecisaCuidadosEspeciais() ? "Sim" : "Não",
                        gato.getAdotado() ? "Sim" : "Não"
                });
            }

            // Cria a tabela e a janela.
            JTable tabela = new JTable(modelo);
            tabela.setRowHeight(28);
            tabela.setAutoCreateRowSorter(true);

            JFrame janela = new JFrame("Miaudote - Gatinhos");
            janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            janela.setSize(800, 400);
            janela.setLocationRelativeTo(this);



            // Cria o botão de adoção.
            JButton botaoAdotar = new JButton("Quero adotar ♡");
            estilizarBotao(botaoAdotar);

            // Define o que acontece quando o botão é clicado.
            botaoAdotar.addActionListener(e -> {
                int linhaSelecionada = tabela.getSelectedRow();

                // Verifica se algum gatinho foi selecionado.
                if (linhaSelecionada == -1) {
                    JOptionPane.showMessageDialog(
                            janela,
                            "Selecione um gatinho para adotar! ♡",
                            "Atenção",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                // Descobre qual gatinho corresponde à linha selecionada.
                int linhaModelo =
                        tabela.convertRowIndexToModel(linhaSelecionada);

                Gato gatoSelecionado = gatos.get(linhaModelo);

                // Impede que um gatinho seja adotado novamente.
                if (gatoSelecionado.getAdotado()) {
                    JOptionPane.showMessageDialog(
                            janela,
                            "Esse gatinho já foi adotado! ♡",
                            "Atenção",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                // Pede confirmação antes de registrar a adoção.
                int confirmacao = JOptionPane.showConfirmDialog(
                        janela,
                        "Deseja realmente adotar "
                                + gatoSelecionado.getNome() + "?",
                        "Confirmar adoção",
                        JOptionPane.YES_NO_OPTION
                );

                // Registra a adoção se a pessoa confirmar.
                if (confirmacao == JOptionPane.YES_OPTION) {
                    dao.adotarGatinho(gatoSelecionado);

                    // Atualiza o objeto na lista e a tabela.
                    gatoSelecionado.setAdotado(true);
                    modelo.setValueAt("Sim", linhaModelo, 7);

                    JOptionPane.showMessageDialog(
                            janela,
                            "Adoção realizada com sucesso! ♡",
                            "Miaudote",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            });

            // Organiza os componentes da janela.
            JScrollPane rolagem = new JScrollPane(tabela);

            JPanel painel = new JPanel(new BorderLayout(0, 10));
            painel.setBackground(new Color(255, 247, 250));
            painel.setBorder(
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)
            );

            JLabel titulo = new JLabel("Nossos gatinhos ♡");

            String[] opcoesFiltro = {
                    "Todos os gatinhos",
                    "Cuidados especiais",
                    "Filhotes",
                    "Adultos"
            };

            JComboBox<String> filtro = new JComboBox<>(opcoesFiltro);

            java.util.List<Gato> gatosExibidos =
                    new java.util.ArrayList<>(gatos);

            filtro.addActionListener(e -> {

                String opcao = filtro.getSelectedItem().toString();

                java.util.List<? extends Gato> resultado;

                switch (opcao) {
                    case "Cuidados especiais":
                        resultado = dao.buscarGatinhosEspeciais();
                        break;

                    case "Filhotes":
                        resultado = dao.buscarGatosFilhotes();
                        break;

                    case "Adultos":
                        resultado = dao.buscarGatosAdultos();
                        break;

                    default:
                        resultado = dao.buscarTodos();
                        break;
                }

                gatosExibidos.clear();
                gatosExibidos.addAll(resultado);

                atualizarTabela(gatosExibidos, modelo);
            });

            JPanel painelTitulo = new JPanel(new BorderLayout());
            painelTitulo.setBackground(new Color(255, 247, 250));

            painelTitulo.add(titulo, BorderLayout.WEST);
            painelTitulo.add(filtro, BorderLayout.EAST);

            painel.add(painelTitulo, BorderLayout.NORTH);

            titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
            titulo.setForeground(new Color(213, 5, 87));

            painel.add(rolagem, BorderLayout.CENTER);
            painel.add(botaoAdotar, BorderLayout.SOUTH);

            janela.add(painel);
            janela.setVisible(true);

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(
                    this,
                    "Não foi possível carregar os gatinhos.\n"
                            + erro.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void atualizarTabela(
            java.util.List<? extends Gato> gatos,
            javax.swing.table.DefaultTableModel modelo) {

        modelo.setRowCount(0); // Limpa as linhas q estavam aparecendo

        for (Gato gato : gatos) { // percorre a lista nova de gatinhos
            modelo.addRow(new Object[]{
                    gato.getNome(),
                    gato.getIdadeMeses(),
                    gato.getSexo(),
                    gato.getPersonalidade(),
                    gato.getDescricaoAparencia(),
                    gato.getVacinado() ? "Sim" : "Não",
                    gato.getPrecisaCuidadosEspeciais() ? "Sim" : "Não",
                    gato.getAdotado() ? "Sim" : "Não"
            });
        }
    }


    private void abrirTelaCadastro() { // Cria e processa o formulário de cadastro.
        JTextField nome = new JTextField(); // Campo para digitar o nome.
        JComboBox<String> sexo = new JComboBox<>(
                new String[]{"Fêmea", "Macho"} // Opções disponíveis para sexo.
        );
        JTextField idade = new JTextField(); // Campo para a idade em meses.
        JTextField personalidade = new JTextField(); // Campo para a personalidade.
        JCheckBox cuidados = new JCheckBox("Precisa de cuidados especiais"); // Marca se precisa de cuidados.
        JTextField descricaoCuidados = new JTextField(); // Campo para detalhar os cuidados.
        JTextField aparencia = new JTextField(); // Campo para descrever a aparência.
        JCheckBox vacinado = new JCheckBox("Já está vacinado"); // Marca se está vacinado.

        JComboBox<String> tipo = new JComboBox<>(
                new String[]{"Filhote", "Adulto"} // Permite escolher o tipo de gato.
        );

        JCheckBox desmamado = new JCheckBox("Já foi desmamado"); // Informação específica do filhote.
        JCheckBox castrado = new JCheckBox("Já foi castrado"); // Informação específica do adulto.
        JTextField historico = new JTextField(); // Campo para o histórico comportamental.

        // Cria o formulário com duas colunas e espaços entre os componentes.
        JPanel formulario = new JPanel(new GridLayout(0, 2, 8, 8));
        formulario.setBackground(new Color(255, 247, 250)); // Define o fundo do formulário.

        // Adiciona rótulos e campos ao formulário, na ordem em que serão exibidos.
        formulario.add(new JLabel("Tipo de gatinho:"));
        formulario.add(tipo);
        formulario.add(new JLabel("Nome:"));
        formulario.add(nome);
        formulario.add(new JLabel("Sexo:"));
        formulario.add(sexo);
        formulario.add(new JLabel("Idade em meses:"));
        formulario.add(idade);
        formulario.add(new JLabel("Personalidade:"));
        formulario.add(personalidade);
        formulario.add(cuidados);
        formulario.add(new JLabel("")); // Espaço vazio para manter o alinhamento das colunas.
        formulario.add(new JLabel("Descrição dos cuidados:"));
        formulario.add(descricaoCuidados);
        formulario.add(new JLabel("Aparência:"));
        formulario.add(aparencia);
        formulario.add(vacinado);
        formulario.add(new JLabel("")); // Espaço vazio para alinhar os componentes.
        formulario.add(desmamado);
        formulario.add(new JLabel("")); // Espaço vazio para alinhar os componentes.
        formulario.add(castrado);
        formulario.add(new JLabel("")); // Espaço vazio para alinhar os componentes.
        formulario.add(new JLabel("Histórico comportamental:"));
        formulario.add(historico);

        JLabel titulo = new JLabel("Cadastrar novo gatinho ♡"); // Título do formulário.
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20)); // Define fonte e tamanho.
        titulo.setForeground(new Color(213, 5, 87)); // Define a cor do título.

        JPanel painel = new JPanel(new BorderLayout(10, 15)); // Organiza título e formulário.
        painel.setBackground(new Color(255, 247, 250)); // Define o fundo do painel.
        painel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20) // Cria margens internas.
        );
        painel.add(titulo, BorderLayout.NORTH); // Coloca o título na parte superior.
        painel.add(formulario, BorderLayout.CENTER); // Coloca o formulário no centro.

        // Exibe o formulário com os botões OK e Cancelar.
        int resposta = JOptionPane.showConfirmDialog(
                this,
                painel,
                "Miaudote - Cadastro",
                JOptionPane.OK_CANCEL_OPTION, // Oferece confirmar ou cancelar.
                JOptionPane.PLAIN_MESSAGE // Exibe sem ícone de alerta.
        );

        if (resposta != JOptionPane.OK_OPTION) { // Se não clicar em OK...
            return; // ...encerra o método sem cadastrar.
        }

        try { // Valida os dados e tenta cadastrar o gato.
            String nomeDigitado = nome.getText().trim(); // Obtém o nome sem espaços nas pontas.
            String personalidadeDigitada = personalidade.getText().trim(); // Obtém a personalidade.
            String descricaoCuidadosDigitada =
                    descricaoCuidados.getText().trim(); // Obtém a descrição dos cuidados.
            String aparenciaDigitada = aparencia.getText().trim(); // Obtém a descrição da aparência.

            // Impede o cadastro se algum campo obrigatório estiver vazio.
            if (nomeDigitado.isEmpty()
                    || personalidadeDigitada.isEmpty()
                    || aparenciaDigitada.isEmpty()) {
                throw new IllegalArgumentException(
                        "Preencha o nome, a personalidade e a aparência."
                );
            }

            int idadeMeses = Integer.parseInt(idade.getText().trim()); // Converte a idade para inteiro.

            if (idadeMeses < 0) { // Verifica se a idade é negativa.
                throw new IllegalArgumentException(
                        "A idade não pode ser negativa."
                );
            }

            // Se marcou cuidados especiais, a descrição também precisa ser preenchida.
            if (cuidados.isSelected()
                    && descricaoCuidadosDigitada.isEmpty()) {
                throw new IllegalArgumentException(
                        "Descreva os cuidados especiais necessários."
                );
            }

            Gato gato; // Declara uma variável que pode receber um Gato ou uma subclasse.

            if (tipo.getSelectedItem().equals("Filhote")) { // Verifica se foi escolhido filhote.
                gato = new GatoFilhote( // Cria um objeto da classe GatoFilhote.
                        nomeDigitado,
                        sexo.getSelectedItem().toString(),
                        idadeMeses,
                        personalidadeDigitada,
                        cuidados.isSelected(),
                        descricaoCuidadosDigitada,
                        aparenciaDigitada,
                        vacinado.isSelected(),
                        desmamado.isSelected()
                );
            } else { // Se não for filhote, cria um gato adulto.
                gato = new GatoAdulto(
                        nomeDigitado,
                        sexo.getSelectedItem().toString(),
                        idadeMeses,
                        personalidadeDigitada,
                        cuidados.isSelected(),
                        descricaoCuidadosDigitada,
                        aparenciaDigitada,
                        vacinado.isSelected(),
                        castrado.isSelected(),
                        historico.getText().trim()
                );
            }

            // Cria o DAO para acessar o banco e salva o gato cadastrado.
            GatoDAO dao = new GatoDAO(
                    ConexaoBanco.obterTemplate() // Obtém o recurso de acesso ao banco.
            );
            dao.save(gato); // Persiste o gato no banco de dados.

            JOptionPane.showMessageDialog(
                    this,
                    "Gatinho cadastrado com sucesso! ♡",
                    "Miaudote",
                    JOptionPane.INFORMATION_MESSAGE // Mostra mensagem de sucesso.
            );

        } catch (NumberFormatException erro) { // Ocorre se a idade não for um número inteiro válido.
            JOptionPane.showMessageDialog(
                    this,
                    "Digite uma idade válida em meses.",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE // Mostra um aviso ao usuário.
            );
        } catch (Exception erro) { // Captura outros erros durante a validação ou o cadastro.
            JOptionPane.showMessageDialog(
                    this,
                    "Não foi possível cadastrar o gatinho.\n"
                            + erro.getMessage(), // Exibe a mensagem do erro ocorrido.
                    "Erro",
                    JOptionPane.ERROR_MESSAGE // Mostra uma caixa de diálogo de erro.
            );
        }
    }
}
