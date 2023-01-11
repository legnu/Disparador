/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.LeGnus_Disparador.view;

import br.com.LeGnus_Disparador.model.ModuloConexao;
import java.awt.Toolkit;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import net.proteanit.sql.DbUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.interactions.Actions;

/**
 *
 * @author Ad3ln0r
 */
public class TelaGrupos extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaPrincipal
     */
    public TelaGrupos() {
        initComponents();
        conexao = ModuloConexao.conector();
        setIcon();
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/br/com/LeGnus_Disparador/util/ERPGestao64.png")));
    }

    public void buscarFoto() {
        JFileChooser arquivo = new JFileChooser();
        arquivo.setDialogTitle("SELECIONE UMA IMAGEM");
        arquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int op = arquivo.showOpenDialog(this);
        if (op == JFileChooser.APPROVE_OPTION) {
            File file = new File("");
            file = arquivo.getSelectedFile();
            String fileName = file.getAbsolutePath();
            txtMidia.setText(fileName);
        }
    }

    private void instanciarTbGrupos() {
        String sql = "select idgrupo as NºGrupo, nomeGrupo as Grupo from tbgrupos";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            tbGrupos.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    private void instanciarAuxGrupos() {
        String sql = "select idgrupo as NºGrupo, nomeGrupo as Grupo from tbgrupos";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            auxGrupos.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    private void instanciarTbConfig() {
        String sql = "select * from tbconfig";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            tbConfig.setModel(DbUtils.resultSetToTableModel(rs));
            taMensagem.setText(tbConfig.getModel().getValueAt(0, 4).toString());
            txtMidia.setText(tbConfig.getModel().getValueAt(0, 5).toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    private void PesquisarGrupo() {
        String sql = "select idgrupo as NºGrupo, nomeGrupo as Grupo from tbgrupos where nomeGrupo like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtPesquisa.getText() + "%");
            rs = pst.executeQuery();
            tbGrupos.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    private void adicionarGrupo() {
        String sql = "insert into tbgrupos(nomeGrupo) values(?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNomeGrupo.getText());
            if (txtNomeGrupo.getText().isEmpty() == true) {
                JOptionPane.showMessageDialog(null, "Adicione um Nome para o Grupo.");

            } else {
                //A linha abaixo atualiza os dados do novo usuario
                int adicionado = pst.executeUpdate();
                //A Linha abaixo serve de apoio ao entendimento da logica
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Grupo adicionado com sucesso.");
                    Limpar();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    private void selecionarGrupo() {
        int setar = tbGrupos.getSelectedRow();
        txtNumeroGrupo.setText(tbGrupos.getModel().getValueAt(setar, 0).toString());

        txtNomeGrupo.setText(tbGrupos.getModel().getValueAt(setar, 1).toString());

        //A Linha Abaixo desabilita o botão adicionar
        btnAdicionar.setEnabled(false);
        btnEditar.setEnabled(true);

        btnRemover.setEnabled(true);

    }

    private void editarGrupo() {
        String sql = "update tbgrupos set nomegrupo=? where idgrupo=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNomeGrupo.getText());
            pst.setString(2, txtNumeroGrupo.getText());
            if (txtNomeGrupo.getText().isEmpty() == true) {
                JOptionPane.showMessageDialog(null, "Adicione um Nome para o Grupo.");
            } else {
                //A linha abaixo atualiza os dados do novo usuario
                int adicionado = pst.executeUpdate();
                //A Linha abaixo serve de apoio ao entendimento da logica
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Grupo editado com sucesso.");
                    Limpar();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    private void removerGrupo() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este Grupo?", "Atenção", JOptionPane.YES_NO_OPTION);

        if (confirma == JOptionPane.YES_OPTION) {

            try {
                String sql = "delete from tbgrupos where idgrupo=?";
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtNumeroGrupo.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Clique no OK e Aguarde.");
                tirarId();
                criarId();
                JOptionPane.showMessageDialog(null, "Cliente removido com sucesso.");
                Limpar();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                Limpar();
            }
        }

    }

    private void tirarId() {

        String sql = "alter table tbgrupos drop idgrupo";
        try {
            pst = conexao.prepareStatement(sql);
            pst.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            Limpar();
        }

    }

    private void criarId() {
        String sql = "alter table tbgrupos add idgrupo MEDIUMINT NOT NULL AUTO_INCREMENT Primary key";
        try {
            pst = conexao.prepareStatement(sql);
            pst.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            Limpar();
        }
    }

    private void configurar() {

        JLabel rotulo = new JLabel("Digite a senha:");
        JPasswordField password = new JPasswordField(10);
        password.setEchoChar('*');
        JPanel entUsuario = new JPanel();
        entUsuario.add(rotulo);
        entUsuario.add(password);

        JOptionPane.showMessageDialog(null, entUsuario, "Cabeçario de configurações", JOptionPane.PLAIN_MESSAGE);

        String senha = password.getText();

        if (senha.equals("13180720") == true) {

            String firefox_path = JOptionPane.showInputDialog("Firefox Binary Path.");
            String gecko_path = JOptionPane.showInputDialog("Gecko Executable Path.");
            String profile = JOptionPane.showInputDialog("Profile Path/Name.");

            String sql = "update tbconfig set firefoxBinary_path=?,geckoExe_path=?,firefoxProfile_path=? where idconf=1";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, firefox_path);
                pst.setString(2, gecko_path);
                pst.setString(3, profile);

                //A linha abaixo atualiza os dados do novo usuario
                int adicionado = pst.executeUpdate();
                //A Linha abaixo serve de apoio ao entendimento da logica
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Configurado com sucesso.");
                    Limpar();
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);

            }

        } else {
            JOptionPane.showMessageDialog(null, "Senha Incorreta.");
            Limpar();
        }
    }

    public void disparar() {
        try {
            String sql = "update tbconfig set mensagem=?,midia_path=? where idconf=1";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, taMensagem.getText());
            pst.setString(2, txtMidia.getText());
            pst.executeUpdate();
            instanciarTbConfig();

            System.setProperty("webdriver.gecko.driver", tbConfig.getModel().getValueAt(0, 2).toString());
            FirefoxOptions options = new FirefoxOptions();

            ProfilesIni profini = new ProfilesIni();
            FirefoxProfile prof = profini.getProfile(tbConfig.getModel().getValueAt(0, 3).toString());
            options.setBinary(tbConfig.getModel().getValueAt(0, 1).toString());
            options.setProfile(prof);

            WebDriver driver = new FirefoxDriver(options);
            Actions act = new Actions(driver);

            driver.get("https://web.whatsapp.com/");

            Thread.sleep(120000);

            for (int n = 0; n <= auxGrupos.getRowCount(); n++) {
                act.keyDown(Keys.CONTROL).keyDown(Keys.ALT).keyDown(Keys.SHIFT).keyDown("]").perform();
            }

            act.keyUp(Keys.CONTROL).keyUp(Keys.ALT).keyUp(Keys.SHIFT).keyUp("]").perform();

            Thread.sleep(3000);

            for (int i = 0; i < auxGrupos.getRowCount(); i++) {
                driver.findElement(By.xpath("//div[contains(@class,'copyable-text selectable-text')]")).click();
                Thread.sleep(1000);
                act.sendKeys(auxGrupos.getModel().getValueAt(i, 1).toString()).perform();
                Thread.sleep(1000);
                act.sendKeys(Keys.ARROW_DOWN, Keys.ENTER).perform();
                driver.findElement(By.cssSelector("div[title='Mensagem']")).click();
                Thread.sleep(1000);
                act.sendKeys(tbConfig.getModel().getValueAt(0, 4).toString()).perform();
                if (txtMidia.getText().isBlank() == false) {
                    driver.findElement(By.cssSelector("span[data-icon='clip']")).click();
                    Thread.sleep(3000);
                    driver.findElement(By.cssSelector("input[type='file']")).sendKeys(tbConfig.getModel().getValueAt(0, 5).toString());
                    Thread.sleep(3000);
                    driver.findElement(By.cssSelector("span[data-icon='send']")).click();
                    Thread.sleep(3000);
                } else {
                    Thread.sleep(1000);
                    act.sendKeys(Keys.ENTER).perform();
                }
                Thread.sleep(1000);
                driver.findElement(By.xpath("//div[contains(@class,'copyable-text selectable-text')]")).click();
                for (int n = 0; n <= 100; n++) {
                    act.sendKeys(Keys.DELETE).perform();
                    act.sendKeys(Keys.BACK_SPACE).perform();
                }
                Thread.sleep(3000);
                act.sendKeys(Keys.DELETE).perform();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            Limpar();
        }
    }

    public void Limpar() {
        txtNomeGrupo.setText(null);
        txtNumeroGrupo.setText(null);
        btnAdicionar.setEnabled(true);
        btnEditar.setEnabled(false);
        btnRemover.setEnabled(false);
        instanciarTbGrupos();
        instanciarAuxGrupos();
        instanciarTbConfig();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scConfig = new javax.swing.JScrollPane();
        tbConfig = new javax.swing.JTable();
        scAuxGrupos = new javax.swing.JScrollPane();
        auxGrupos = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbGrupos = new javax.swing.JTable();
        txtPesquisa = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtNomeGrupo = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        txtNumeroGrupo = new javax.swing.JTextField();
        btnAdicionar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnConfigurar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        taMensagem = new javax.swing.JTextArea();
        jPanel8 = new javax.swing.JPanel();
        txtMidia = new javax.swing.JTextField();
        btnLupa = new javax.swing.JButton();
        btnDisparar = new javax.swing.JButton();

        tbConfig.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scConfig.setViewportView(tbConfig);

        auxGrupos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scAuxGrupos.setViewportView(auxGrupos);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Legnu's_Disparador - TelaGrupos");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Grupos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        tbGrupos = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tbGrupos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbGrupos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbGruposMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbGrupos);

        txtPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisaActionPerformed(evt);
            }
        });
        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Pesquisar:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPesquisa)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 656, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nome_Grupo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        txtNomeGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeGrupoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtNomeGrupo)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtNomeGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nº Grupo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        txtNumeroGrupo.setEditable(false);
        txtNumeroGrupo.setEnabled(false);
        txtNumeroGrupo.setFocusable(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtNumeroGrupo, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtNumeroGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        btnAdicionar.setBackground(new java.awt.Color(0, 204, 51));
        btnAdicionar.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnAdicionar.setForeground(new java.awt.Color(255, 255, 255));
        btnAdicionar.setText("Adicionar");
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        btnEditar.setBackground(new java.awt.Color(0, 153, 153));
        btnEditar.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(255, 255, 255));
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnConfigurar.setBackground(new java.awt.Color(51, 51, 51));
        btnConfigurar.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnConfigurar.setForeground(new java.awt.Color(255, 255, 255));
        btnConfigurar.setText("Configurar");
        btnConfigurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfigurarActionPerformed(evt);
            }
        });

        btnRemover.setBackground(new java.awt.Color(153, 0, 0));
        btnRemover.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnRemover.setForeground(new java.awt.Color(255, 255, 255));
        btnRemover.setText("Remover");
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Prefixos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mensagem", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        taMensagem.setColumns(20);
        taMensagem.setRows(5);
        jScrollPane2.setViewportView(taMensagem);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
        );

        jPanel8.setBackground(new java.awt.Color(204, 204, 204));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mídia", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        txtMidia.setEditable(false);
        txtMidia.setEnabled(false);
        txtMidia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMidiaMouseClicked(evt);
            }
        });
        txtMidia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMidiaActionPerformed(evt);
            }
        });

        btnLupa.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnLupa.setForeground(new java.awt.Color(255, 255, 255));
        btnLupa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/LeGnus_Disparador/util/lupa.png"))); // NOI18N
        btnLupa.setBorderPainted(false);
        btnLupa.setContentAreaFilled(false);
        btnLupa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLupaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(txtMidia, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLupa, javax.swing.GroupLayout.PREFERRED_SIZE, 33, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLupa, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(txtMidia, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        btnDisparar.setBackground(new java.awt.Color(0, 0, 204));
        btnDisparar.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnDisparar.setForeground(new java.awt.Color(255, 255, 255));
        btnDisparar.setText("Disparar");
        btnDisparar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDispararActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDisparar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6))
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(29, 29, 29)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDisparar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnAdicionar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnRemover, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnConfigurar, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(18, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfigurar, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomeGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeGrupoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeGrupoActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        TelaPrincipal principal = new TelaPrincipal();
        principal.setVisible(true);
    }//GEN-LAST:event_formWindowClosed

    private void txtMidiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMidiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMidiaActionPerformed

    private void btnLupaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLupaActionPerformed
        // TODO add your handling code here:
        buscarFoto();
    }//GEN-LAST:event_btnLupaActionPerformed

    private void btnDispararActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDispararActionPerformed
        // TODO add your handling code here:
        disparar();
    }//GEN-LAST:event_btnDispararActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        Limpar();
    }//GEN-LAST:event_formWindowOpened

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        // TODO add your handling code here:
        adicionarGrupo();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        // TODO add your handling code here:
        removerGrupo();
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        editarGrupo();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnConfigurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfigurarActionPerformed
        // TODO add your handling code here:
        configurar();
    }//GEN-LAST:event_btnConfigurarActionPerformed

    private void tbGruposMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbGruposMouseClicked
        // TODO add your handling code here:
        selecionarGrupo();
    }//GEN-LAST:event_tbGruposMouseClicked

    private void txtPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesquisaActionPerformed

    private void txtPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyReleased
        // TODO add your handling code here:
        PesquisarGrupo();
    }//GEN-LAST:event_txtPesquisaKeyReleased

    private void txtMidiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMidiaMouseClicked
        // TODO add your handling code here:
        txtMidia.setText(null);

    }//GEN-LAST:event_txtMidiaMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaGrupos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaGrupos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaGrupos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaGrupos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaGrupos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable auxGrupos;
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnConfigurar;
    private javax.swing.JButton btnDisparar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnLupa;
    private javax.swing.JButton btnRemover;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane scAuxGrupos;
    private javax.swing.JScrollPane scConfig;
    private javax.swing.JTextArea taMensagem;
    private javax.swing.JTable tbConfig;
    private javax.swing.JTable tbGrupos;
    private javax.swing.JTextField txtMidia;
    private javax.swing.JTextField txtNomeGrupo;
    private javax.swing.JTextField txtNumeroGrupo;
    private javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables
}
