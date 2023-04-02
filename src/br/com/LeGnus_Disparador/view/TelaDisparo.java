/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.LeGnus_Disparador.view;

import br.com.LeGnus_Disparador.model.ModuloConexao;
import static com.google.common.collect.Multimaps.index;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.TimeUnit;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
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
public class TelaDisparo extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String Categoria;
    String Mensagem;
    String lista;
    String listaCliente;
    String listaMensagem;

    /**
     * Creates new form TelaDisparo
     */
    public TelaDisparo() {
        initComponents();
        conexao = ModuloConexao.conector();
        setIcon();

    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/br/com/LeGnus_Disparador/util/ERPGestao64.png")));
    }

    public void buscarExecutavel() {
        JFileChooser arquivo = new JFileChooser();
        arquivo.setDialogTitle("EXECUTAVEL");
        arquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int op = arquivo.showOpenDialog(this);
        if (op == JFileChooser.APPROVE_OPTION) {
            File file = new File("");
            file = arquivo.getSelectedFile();
            String fileName = file.getAbsolutePath();
            txtExecutavel.setText(fileName);
        }
    }

    public void buscarBinario() {
        JFileChooser arquivo = new JFileChooser();
        arquivo.setDialogTitle("BINARIO");
        arquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int op = arquivo.showOpenDialog(this);
        if (op == JFileChooser.APPROVE_OPTION) {
            File file = new File("");
            file = arquivo.getSelectedFile();
            String fileName = file.getAbsolutePath();
            txtBinario.setText(fileName);
        }
    }

    private void Limitar() {
        try {
            if (rbLimitarID.isSelected() == true) {
                txtInicial.setEnabled(true);
                txtFinal.setEnabled(true);
                txtInicial.setText("0");
                txtFinal.setText(String.valueOf(tbExibicao.getRowCount()));

            } else {
                txtInicial.setEnabled(false);
                txtFinal.setEnabled(false);
                txtInicial.setText(null);
                txtFinal.setText(null);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            Limpar();
        }
    }

    public void montarLista() {
        try {
            String Resultado;
            String ResultadoCliente;

            for (int i = 0; tbCategoriaSelecionada.getRowCount() > i; i++) {
                if (i == 0) {
                    Resultado = " conjunto = " + "'" + tbCategoriaSelecionada.getModel().getValueAt(i, 0).toString() + "'";
                    lista = Resultado;
                } else {
                    Resultado = " or conjunto = " + "'" + tbCategoriaSelecionada.getModel().getValueAt(i, 0).toString() + "'";
                    lista = lista + Resultado;
                }
            }
            
            for (int i = 0; tbCategoriaSelecionada.getRowCount() > i; i++) {
                if (i == 0) {
                    ResultadoCliente = " conjunto = " + "'" + tbCategoriaSelecionada.getModel().getValueAt(i, 0).toString() + "'";
                    listaCliente = ResultadoCliente;
                } else {
                    ResultadoCliente = " or conjunto = " + "'" + tbCategoriaSelecionada.getModel().getValueAt(i, 0).toString() + "'";
                    listaCliente = listaCliente + ResultadoCliente;
                }
            }

            instanciarTabela();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            Limpar();
        }
    }
    
    public void montarMensagem() {
        try {
            String Resultado;

            for (int i = 0; tbMensagemSelecionada.getRowCount() > i; i++) {
                if (i == 0) {
                    Resultado = " titulo = " + "'" + tbMensagemSelecionada.getModel().getValueAt(i, 0).toString() + "'";
                    listaMensagem = Resultado;
                } else {
                    Resultado = " or titulo = " + "'" + tbMensagemSelecionada.getModel().getValueAt(i, 0).toString() + "'";
                    listaMensagem = listaMensagem + Resultado;
                }
            }

            instanciarTabela();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            Limpar();
        }
    }

    public void instanciarTabela() {
        try {
            if (rbLimitarID.isSelected() == false && rbGrupo.isSelected() == true) {
                if (rbLimitarCategoria.isSelected() == true) {
                    String sql = "select idgrupo as NºGrupo, nomeGrupo as Grupo, conjunto as Categoria from tbgrupos where" + lista;
                    pst = conexao.prepareStatement(sql);
                    rs = pst.executeQuery();
                    tbExibicao.setModel(DbUtils.resultSetToTableModel(rs));
                } else {
                    String sql = "select idgrupo as NºGrupo, nomeGrupo as Grupo, conjunto as Categoria from tbgrupos";
                    pst = conexao.prepareStatement(sql);
                    rs = pst.executeQuery();
                    tbExibicao.setModel(DbUtils.resultSetToTableModel(rs));
                }

            } else if (rbLimitarID.isSelected() == false && rbCliente.isSelected() == true) {
                if (rbLimitarCategoria.isSelected() == true) {
                    String sql = "select idcliente as NºCliente, nomeCliente as Cliente,conjuntocliente as Categoria from tbclientes where" + listaCliente;
                    pst = conexao.prepareStatement(sql);
                    rs = pst.executeQuery();
                    tbExibicao.setModel(DbUtils.resultSetToTableModel(rs));
                } else {
                    String sql = "select idcliente as NºCliente, nomeCliente as Cliente,conjuntocliente as Categoria from tbclientes";
                    pst = conexao.prepareStatement(sql);
                    rs = pst.executeQuery();
                    tbExibicao.setModel(DbUtils.resultSetToTableModel(rs));
                }

            } else if (rbLimitarID.isSelected() == true && rbGrupo.isSelected() == true) {
                if (rbLimitarCategoria.isSelected() == true) {

                    String sql = "select idgrupo as NºGrupo, nomeGrupo as Grupo, conjunto as Categoria from tbgrupos where" + lista + " and idgrupo>= ? and idgrupo <= ?";
                    pst = conexao.prepareStatement(sql);
                    pst.setInt(1, Integer.parseInt(txtInicial.getText()));
                    pst.setInt(2, Integer.parseInt(txtFinal.getText()));
                    rs = pst.executeQuery();
                    tbExibicao.setModel(DbUtils.resultSetToTableModel(rs));

                } else {
                    String sql = "select idgrupo as NºGrupo, nomeGrupo as Grupo, conjunto as Categoria from tbgrupos where idgrupo>= ? and idgrupo <= ?";
                    pst = conexao.prepareStatement(sql);
                    pst.setInt(1, Integer.parseInt(txtInicial.getText()));
                    pst.setInt(2, Integer.parseInt(txtFinal.getText()));
                    rs = pst.executeQuery();
                    tbExibicao.setModel(DbUtils.resultSetToTableModel(rs));
                }

            } else if (rbLimitarID.isSelected() == true && rbCliente.isSelected() == true) {
                if (rbLimitarCategoria.isSelected() == true) {
                    String sql = "select idcliente as NºCliente, nomeCliente as Cliente,conjuntocliente as Categoria from tbclientes where" + listaCliente + " and idcliente>= ? and idcliente <= ?";
                    pst = conexao.prepareStatement(sql);
                    pst.setInt(1, Integer.parseInt(txtInicial.getText()));
                    pst.setInt(2, Integer.parseInt(txtFinal.getText()));
                    rs = pst.executeQuery();
                    tbExibicao.setModel(DbUtils.resultSetToTableModel(rs));

                } else {
                    String sql = "select idcliente as NºCliente, nomeCliente as Cliente,conjuntocliente as Categoria from tbclientes where idcliente>= ? and idcliente <= ?";
                    pst = conexao.prepareStatement(sql);
                    pst.setInt(1, Integer.parseInt(txtInicial.getText()));
                    pst.setInt(2, Integer.parseInt(txtFinal.getText()));
                    rs = pst.executeQuery();
                    tbExibicao.setModel(DbUtils.resultSetToTableModel(rs));
                }

            } else {
                JOptionPane.showMessageDialog(null, "Combinação desconhecida");
            }

        } catch (java.lang.NumberFormatException e) {

            if (txtInicial.getText().isBlank()) {
                txtInicial.setText("0");
            } else if (txtFinal.getText().isBlank()) {
                txtFinal.setText(String.valueOf(tbExibicao.getRowCount()));
            } else {
                JOptionPane.showMessageDialog(null, "O ID inicial/final só aceita numeros.");
            }

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
            txtExecutavel.setText(tbConfig.getModel().getValueAt(0, 1).toString());
            txtBinario.setText(tbConfig.getModel().getValueAt(0, 2).toString());
            txtPerfil.setText(tbConfig.getModel().getValueAt(0, 3).toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    private void PesquisarGrupo() {
        try {
            if (rbLimitarID.isSelected() == false && rbGrupo.isSelected() == true) {
                if (rbLimitarCategoria.isSelected() == true) {

                } else {
                    String sql = "select idgrupo as NºGrupo, nomeGrupo as Grupo, conjunto as Categoria from tbgrupos where nomeGrupo like ?";
                    pst = conexao.prepareStatement(sql);
                    pst.setString(1, txtPesquisa.getText() + "%");
                    rs = pst.executeQuery();
                    tbExibicao.setModel(DbUtils.resultSetToTableModel(rs));
                }

            } else if (rbLimitarID.isSelected() == false && rbCliente.isSelected() == true) {
                if (rbLimitarCategoria.isSelected() == true) {

                } else {
                    String sql = "select idcliente as NºCliente, nomeCliente as Cliente,conjuntocliente as Categoria from tbclientes where nomecliente like ?";
                    pst = conexao.prepareStatement(sql);
                    pst.setString(1, txtPesquisa.getText() + "%");
                    rs = pst.executeQuery();
                    tbExibicao.setModel(DbUtils.resultSetToTableModel(rs));
                }

            } else if (rbLimitarID.isSelected() == true && rbGrupo.isSelected() == true) {
                if (rbLimitarCategoria.isSelected() == true) {

                } else {
                    String sql = "select idgrupo as NºGrupo, nomeGrupo as Grupo, conjunto as Categoria from tbgrupos where idgrupo>= ? and idgrupo <= ? and nomeGrupo like ?";
                    pst = conexao.prepareStatement(sql);
                    pst.setInt(1, Integer.parseInt(txtInicial.getText()));
                    pst.setInt(2, Integer.parseInt(txtFinal.getText()));
                    pst.setString(3, txtPesquisa.getText() + "%");
                    rs = pst.executeQuery();
                    tbExibicao.setModel(DbUtils.resultSetToTableModel(rs));
                }

            } else if (rbLimitarID.isSelected() == true && rbCliente.isSelected() == true) {
                if (rbLimitarCategoria.isSelected() == true) {

                } else {
                    String sql = "select idcliente as NºCliente, nomeCliente as Cliente,conjuntocliente as Categoria from tbclientes where idcliente>= ? and idcliente <= ? and nomecliente like ?";
                    pst = conexao.prepareStatement(sql);
                    pst.setInt(1, Integer.parseInt(txtInicial.getText()));
                    pst.setInt(2, Integer.parseInt(txtFinal.getText()));
                    pst.setString(3, txtPesquisa.getText() + "%");
                    rs = pst.executeQuery();
                    tbExibicao.setModel(DbUtils.resultSetToTableModel(rs));
                }

            } else {
                JOptionPane.showMessageDialog(null, "Combinação desconhecida");
            }

        } catch (java.lang.NumberFormatException e) {
            if (txtInicial.getText().isBlank()) {
                txtInicial.setText("0");
            } else if (txtFinal.getText().isBlank()) {
                txtFinal.setText(String.valueOf(tbExibicao.getRowCount()));
            } else {
                JOptionPane.showMessageDialog(null, "O ID inicial/final só aceita numeros.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void instanciarCategoria() {
        try {
            if (rbGrupo.isSelected() == true && rbLimitarCategoria.isSelected() == true) {

                String sqy = "select distinct conjunto as Categoria from tbgrupos";
                pst = conexao.prepareStatement(sqy);
                rs = pst.executeQuery();
                tbCategoria.setModel(DbUtils.resultSetToTableModel(rs));
                tbCategoria.setEnabled(true);

            } else if (rbCliente.isSelected() == true && rbLimitarCategoria.isSelected() == true) {

                String sqy = "select distinct conjuntocliente as Categoria from tbclientes";
                pst = conexao.prepareStatement(sqy);
                rs = pst.executeQuery();
                tbCategoria.setModel(DbUtils.resultSetToTableModel(rs));
                tbCategoria.setEnabled(true);

            } else {
                ((DefaultTableModel) tbCategoria.getModel()).setRowCount(0);
                ((DefaultTableModel) tbCategoriaSelecionada.getModel()).setRowCount(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    private void selecionarCategoria() {
        int setar = tbCategoria.getSelectedRow();

        if (tbCategoria.getModel().getValueAt(setar, 0) == null) {
            Categoria = "";
        } else {
            Categoria = tbCategoria.getModel().getValueAt(setar, 0).toString();
        }
        btnEnviarCategoria.setEnabled(true);
        btnRemoverCategoria.setEnabled(false);
    }

    private void enviarCategoria() {
        DefaultTableModel model = (DefaultTableModel) tbCategoriaSelecionada.getModel();
        model.addRow(new Object[]{Categoria});
        btnEnviarCategoria.setEnabled(false);
        btnRemoverCategoria.setEnabled(false);
    }

    private void removerCategoria() {
        DefaultTableModel model = (DefaultTableModel) tbCategoriaSelecionada.getModel();
        model.removeRow(tbCategoriaSelecionada.getSelectedRow());
        btnEnviarCategoria.setEnabled(false);
        btnRemoverCategoria.setEnabled(false);
    }

    private void instaciarMensagens() {
        try {
            String sql = "select titulo as Mensagem from tbmensagens";
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            tbMensagem.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    private void selecionarMensagem() {
        int setar = tbMensagem.getSelectedRow();

        if (tbMensagem.getModel().getValueAt(setar, 0) == null) {
            Mensagem = "";
        } else {
            Mensagem = tbMensagem.getModel().getValueAt(setar, 0).toString();
        }
        btnEnviarMensagem.setEnabled(true);
        btnRemoverMensagem.setEnabled(false);
    }

    private void enviarMensagem() {
        DefaultTableModel model = (DefaultTableModel) tbMensagemSelecionada.getModel();
        model.addRow(new Object[]{Mensagem});
        btnEnviarMensagem.setEnabled(false);
        btnRemoverMensagem.setEnabled(false);
    }

    private void removerMensagem() {
        DefaultTableModel model = (DefaultTableModel) tbMensagemSelecionada.getModel();
        model.removeRow(tbMensagemSelecionada.getSelectedRow());
        btnEnviarMensagem.setEnabled(false);
        btnRemoverMensagem.setEnabled(false);

    }

    public void disparar() {
        try {

            String sqo = "update tbconfig set firefoxBinary_path=?,geckoExe_path=?,firefoxProfile_path=? where idconf=1";
            pst = conexao.prepareStatement(sqo);
            pst.setString(1, txtBinario.getText());
            pst.setString(2, txtExecutavel.getText());
            pst.setString(3, txtPerfil.getText());
            pst.executeUpdate();
            
            String sqy = "select mensagem, midia from tbmensagens where " + listaMensagem;
            pst = conexao.prepareStatement(sqy);
            rs = pst.executeQuery();
            tbAux.setModel(DbUtils.resultSetToTableModel(rs));
            

            System.setProperty("webdriver.gecko.driver", tbConfig.getModel().getValueAt(0, 2).toString());
            FirefoxOptions options = new FirefoxOptions();

            ProfilesIni profini = new ProfilesIni();
            FirefoxProfile prof = profini.getProfile(tbConfig.getModel().getValueAt(0, 3).toString());
            options.setBinary(tbConfig.getModel().getValueAt(0, 1).toString());
            options.setProfile(prof);

            WebDriver driver = new FirefoxDriver(options);
            Actions act = new Actions(driver);

            driver.get("https://web.whatsapp.com/");

            Thread.sleep(300000);

            for (int n = 0; n <= 5000; n++) {
                act.keyDown(Keys.CONTROL).keyDown(Keys.ALT).keyDown(Keys.SHIFT).keyDown("]").perform();
            }

            act.keyUp(Keys.CONTROL).keyUp(Keys.ALT).keyUp(Keys.SHIFT).keyUp("]").perform();

            Thread.sleep(59000);

            for (int i = 0; i < tbExibicao.getRowCount(); i++) {
                Thread.sleep(1000);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection selection = new StringSelection(tbExibicao.getModel().getValueAt(i, 1).toString());
                clipboard.setContents(selection, null);

                driver.findElement(By.cssSelector("div[title='Caixa de texto de pesquisa']")).click();
                for (int n = 0; n <= 100; n++) {
                    act.sendKeys(Keys.DELETE).perform();
                    act.sendKeys(Keys.BACK_SPACE).perform();
                }
                Thread.sleep(2000);

                act.keyDown(Keys.CONTROL).perform();
                act.sendKeys("v").perform();
                act.keyUp(Keys.CONTROL).perform();

                Thread.sleep(1000);
                act.sendKeys(Keys.ARROW_DOWN, Keys.ENTER).perform();
                Thread.sleep(6000);
                
                for (int o = 0; o <  tbAux.getRowCount(); o++) {
                    if (tbAux.getModel().getValueAt(o, 1).toString().isBlank() == false) {
                        Thread.sleep(3000);
                        driver.findElement(By.cssSelector("span[data-icon='clip']")).click();
                        Thread.sleep(3000);
                        driver.findElement(By.cssSelector("input[type='file']")).sendKeys(tbAux.getModel().getValueAt(o, 1).toString());
                        Thread.sleep(3000);
                        driver.findElement(By.cssSelector("div[title='Mensagem']")).click();
                        Thread.sleep(3000);
                        act.sendKeys(tbAux.getModel().getValueAt(o, 0).toString()).perform();
                        Thread.sleep(3000);
                        driver.findElement(By.cssSelector("span[data-icon='send']")).click();
                        Thread.sleep(3000);
                    } else {
                        driver.findElement(By.cssSelector("div[title='Mensagem']")).click();
                        Thread.sleep(1000);
                        act.sendKeys(tbAux.getModel().getValueAt(o, 0).toString()).perform();
                        Thread.sleep(2000);
                        act.sendKeys(Keys.ENTER).perform();
                        Thread.sleep(3000);
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            Limpar();
        }
    }

    public void Limpar() {
        btnEnviarCategoria.setEnabled(false);
        btnRemoverCategoria.setEnabled(false);
        btnEnviarMensagem.setEnabled(false);
        btnRemoverMensagem.setEnabled(false);
        ((DefaultTableModel) tbCategoriaSelecionada.getModel()).setRowCount(0);
        ((DefaultTableModel) tbMensagemSelecionada.getModel()).setRowCount(0);
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

        alvo = new javax.swing.ButtonGroup();
        scConfig = new javax.swing.JScrollPane();
        tbConfig = new javax.swing.JTable();
        scAux = new javax.swing.JScrollPane();
        tbAux = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbExibicao = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtPesquisa = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtBinario = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        txtExecutavel = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        txtPerfil = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        pnCategoriaSelecionada = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbCategoriaSelecionada = new javax.swing.JTable();
        pnCategoria = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbCategoria = new javax.swing.JTable();
        btnRemoverCategoria = new javax.swing.JButton();
        btnEnviarCategoria = new javax.swing.JButton();
        rbLimitarCategoria = new javax.swing.JRadioButton();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbMensagem = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbMensagemSelecionada = new javax.swing.JTable();
        btnEnviarMensagem = new javax.swing.JButton();
        btnRemoverMensagem = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        rbGrupo = new javax.swing.JRadioButton();
        rbCliente = new javax.swing.JRadioButton();
        rbLimitarID = new javax.swing.JRadioButton();
        txtInicial = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtFinal = new javax.swing.JTextField();

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

        tbAux.setModel(new javax.swing.table.DefaultTableModel(
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
        scAux.setViewportView(tbAux);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Legnu's_Disparador - TelaDisparo");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Grupos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        tbExibicao = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tbExibicao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbExibicao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbExibicaoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbExibicao);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Pesquisar:");

        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPesquisa))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE))
        );

        jButton1.setBackground(new java.awt.Color(0, 0, 153));
        jButton1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Disparar");
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Parametros", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Binario", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        txtBinario.setEditable(false);
        txtBinario.setEnabled(false);

        jButton3.setText("🔍");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(txtBinario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txtBinario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Executavel", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        txtExecutavel.setEditable(false);
        txtExecutavel.setEnabled(false);
        txtExecutavel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtExecutavelMouseClicked(evt);
            }
        });
        txtExecutavel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtExecutavelActionPerformed(evt);
            }
        });

        jButton2.setText("🔍");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(txtExecutavel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txtExecutavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(204, 204, 204));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Perfil", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPerfil, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel13.setBackground(new java.awt.Color(204, 204, 204));
        jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        pnCategoriaSelecionada.setBackground(new java.awt.Color(204, 204, 204));
        pnCategoriaSelecionada.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Categorias Selecionadas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        tbCategoriaSelecionada = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tbCategoriaSelecionada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbCategoriaSelecionada.setFocusable(false);
        tbCategoriaSelecionada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbCategoriaSelecionadaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbCategoriaSelecionada);

        javax.swing.GroupLayout pnCategoriaSelecionadaLayout = new javax.swing.GroupLayout(pnCategoriaSelecionada);
        pnCategoriaSelecionada.setLayout(pnCategoriaSelecionadaLayout);
        pnCategoriaSelecionadaLayout.setHorizontalGroup(
            pnCategoriaSelecionadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
        );
        pnCategoriaSelecionadaLayout.setVerticalGroup(
            pnCategoriaSelecionadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        pnCategoria.setBackground(new java.awt.Color(204, 204, 204));
        pnCategoria.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Categorias Disponiveis", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        tbCategoria = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tbCategoria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbCategoria.setFocusable(false);
        tbCategoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbCategoriaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbCategoria);

        javax.swing.GroupLayout pnCategoriaLayout = new javax.swing.GroupLayout(pnCategoria);
        pnCategoria.setLayout(pnCategoriaLayout);
        pnCategoriaLayout.setHorizontalGroup(
            pnCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
        );
        pnCategoriaLayout.setVerticalGroup(
            pnCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        btnRemoverCategoria.setBackground(new java.awt.Color(153, 0, 0));
        btnRemoverCategoria.setForeground(new java.awt.Color(255, 255, 255));
        btnRemoverCategoria.setText("⬅");
        btnRemoverCategoria.setToolTipText("");
        btnRemoverCategoria.setEnabled(false);
        btnRemoverCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverCategoriaActionPerformed(evt);
            }
        });

        btnEnviarCategoria.setBackground(new java.awt.Color(0, 153, 102));
        btnEnviarCategoria.setForeground(new java.awt.Color(255, 255, 255));
        btnEnviarCategoria.setText("➡");
        btnEnviarCategoria.setEnabled(false);
        btnEnviarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarCategoriaActionPerformed(evt);
            }
        });

        rbLimitarCategoria.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        rbLimitarCategoria.setText("Limitar por Categoria");
        rbLimitarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbLimitarCategoriaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(pnCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnRemoverCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                            .addComponent(btnEnviarCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(rbLimitarCategoria))
                .addGap(20, 20, 20)
                .addComponent(pnCategoriaSelecionada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(rbLimitarCategoria)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnCategoriaSelecionada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(btnEnviarCategoria)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(btnRemoverCategoria)
                        .addGap(94, 94, 94))))
        );

        jPanel14.setBackground(new java.awt.Color(204, 204, 204));
        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel15.setBackground(new java.awt.Color(204, 204, 204));
        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Mensagens Disponiveis", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        tbMensagem = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tbMensagem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbMensagem.setFocusable(false);
        tbMensagem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMensagemMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbMensagem);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jPanel16.setBackground(new java.awt.Color(204, 204, 204));
        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Mensagens Selecionadas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        tbMensagemSelecionada = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tbMensagemSelecionada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbMensagemSelecionada.setFocusable(false);
        tbMensagemSelecionada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMensagemSelecionadaMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tbMensagemSelecionada);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
        );

        btnEnviarMensagem.setBackground(new java.awt.Color(0, 153, 102));
        btnEnviarMensagem.setForeground(new java.awt.Color(255, 255, 255));
        btnEnviarMensagem.setText("➡");
        btnEnviarMensagem.setEnabled(false);
        btnEnviarMensagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarMensagemActionPerformed(evt);
            }
        });

        btnRemoverMensagem.setBackground(new java.awt.Color(153, 0, 0));
        btnRemoverMensagem.setForeground(new java.awt.Color(255, 255, 255));
        btnRemoverMensagem.setText("⬅");
        btnRemoverMensagem.setToolTipText("");
        btnRemoverMensagem.setEnabled(false);
        btnRemoverMensagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverMensagemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(26, 26, 26)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEnviarMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemoverMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(btnEnviarMensagem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRemoverMensagem)
                .addGap(31, 31, 31))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(13, 13, 13)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel10.setBackground(new java.awt.Color(204, 204, 204));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Disparar para", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        alvo.add(rbGrupo);
        rbGrupo.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        rbGrupo.setSelected(true);
        rbGrupo.setText("Grupos");
        rbGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbGrupoActionPerformed(evt);
            }
        });

        alvo.add(rbCliente);
        rbCliente.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        rbCliente.setText("Clientes");
        rbCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbClienteActionPerformed(evt);
            }
        });

        rbLimitarID.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        rbLimitarID.setText("Limitar de ");
        rbLimitarID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbLimitarIDActionPerformed(evt);
            }
        });

        txtInicial.setEnabled(false);
        txtInicial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtInicialKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel2.setText("ao");

        txtFinal.setEnabled(false);
        txtFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFinalKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbGrupo)
                .addGap(18, 18, 18)
                .addComponent(rbCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addComponent(rbLimitarID)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbGrupo)
                    .addComponent(rbCliente)
                    .addComponent(rbLimitarID)
                    .addComponent(txtInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
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

    private void tbCategoriaSelecionadaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbCategoriaSelecionadaMouseClicked
        // TODO add your handling code here:
        btnEnviarCategoria.setEnabled(false);
        btnRemoverCategoria.setEnabled(true);
    }//GEN-LAST:event_tbCategoriaSelecionadaMouseClicked

    private void tbCategoriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbCategoriaMouseClicked
        // TODO add your handling code here:
        selecionarCategoria();
    }//GEN-LAST:event_tbCategoriaMouseClicked

    private void tbMensagemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMensagemMouseClicked
        // TODO add your handling code here:
        selecionarMensagem();
    }//GEN-LAST:event_tbMensagemMouseClicked

    private void tbMensagemSelecionadaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMensagemSelecionadaMouseClicked
        // TODO add your handling code here:
        btnEnviarMensagem.setEnabled(false);
        btnRemoverMensagem.setEnabled(true);
    }//GEN-LAST:event_tbMensagemSelecionadaMouseClicked

    private void txtExecutavelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtExecutavelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtExecutavelActionPerformed

    private void rbGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbGrupoActionPerformed
        // TODO add your handling code here:
        Limpar();
        rbLimitarID.setSelected(false);
        rbLimitarCategoria.setSelected(false);
        Limitar();
        instanciarCategoria();
        instanciarTabela();

    }//GEN-LAST:event_rbGrupoActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        instanciarTabela();
        instaciarMensagens();
        DefaultTableModel model = (DefaultTableModel) tbCategoriaSelecionada.getModel();
        model.addColumn("Categorias");
        DefaultTableModel model1 = (DefaultTableModel) tbMensagemSelecionada.getModel();
        model1.addColumn("Mensagens");
        Limpar();
    }//GEN-LAST:event_formWindowOpened

    private void rbLimitarIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbLimitarIDActionPerformed
        // TODO add your handling code here:
        Limitar();
    }//GEN-LAST:event_rbLimitarIDActionPerformed

    private void txtInicialKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInicialKeyReleased
        // TODO add your handling code here:
        instanciarTabela();
    }//GEN-LAST:event_txtInicialKeyReleased

    private void txtFinalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFinalKeyReleased
        // TODO add your handling code here:
        instanciarTabela();
    }//GEN-LAST:event_txtFinalKeyReleased

    private void rbClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbClienteActionPerformed
        // TODO add your handling code here:
        Limpar();
        rbLimitarID.setSelected(false);
        rbLimitarCategoria.setSelected(false);
        Limitar();
        instanciarCategoria();
        instanciarTabela();
    }//GEN-LAST:event_rbClienteActionPerformed

    private void tbExibicaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbExibicaoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbExibicaoMouseClicked

    private void txtPesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyPressed
        // TODO add your handling code here:
        PesquisarGrupo();
    }//GEN-LAST:event_txtPesquisaKeyPressed

    private void rbLimitarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbLimitarCategoriaActionPerformed
        // TODO add your handling code here:
        instanciarCategoria();
    }//GEN-LAST:event_rbLimitarCategoriaActionPerformed

    private void btnEnviarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarCategoriaActionPerformed
        // TODO add your handling code here:
        enviarCategoria();
        montarLista();
    }//GEN-LAST:event_btnEnviarCategoriaActionPerformed

    private void btnRemoverCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverCategoriaActionPerformed
        // TODO add your handling code here:
        removerCategoria();
        montarLista();
    }//GEN-LAST:event_btnRemoverCategoriaActionPerformed

    private void btnEnviarMensagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarMensagemActionPerformed
        // TODO add your handling code here:
        enviarMensagem();
    }//GEN-LAST:event_btnEnviarMensagemActionPerformed

    private void btnRemoverMensagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverMensagemActionPerformed
        // TODO add your handling code here:
        removerMensagem();
    }//GEN-LAST:event_btnRemoverMensagemActionPerformed

    private void txtExecutavelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtExecutavelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtExecutavelMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        montarMensagem();
        disparar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        TelaPrincipal principal = new TelaPrincipal();
        principal.setVisible(true);
    }//GEN-LAST:event_formWindowClosed

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
            java.util.logging.Logger.getLogger(TelaDisparo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaDisparo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaDisparo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaDisparo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaDisparo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup alvo;
    private javax.swing.JButton btnEnviarCategoria;
    private javax.swing.JButton btnEnviarMensagem;
    private javax.swing.JButton btnRemoverCategoria;
    private javax.swing.JButton btnRemoverMensagem;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPanel pnCategoria;
    private javax.swing.JPanel pnCategoriaSelecionada;
    private javax.swing.JRadioButton rbCliente;
    private javax.swing.JRadioButton rbGrupo;
    private javax.swing.JRadioButton rbLimitarCategoria;
    private javax.swing.JRadioButton rbLimitarID;
    private javax.swing.JScrollPane scAux;
    private javax.swing.JScrollPane scConfig;
    private javax.swing.JTable tbAux;
    private javax.swing.JTable tbCategoria;
    private javax.swing.JTable tbCategoriaSelecionada;
    private javax.swing.JTable tbConfig;
    private javax.swing.JTable tbExibicao;
    private javax.swing.JTable tbMensagem;
    private javax.swing.JTable tbMensagemSelecionada;
    private javax.swing.JTextField txtBinario;
    private javax.swing.JTextField txtExecutavel;
    private javax.swing.JTextField txtFinal;
    private javax.swing.JTextField txtInicial;
    private javax.swing.JTextField txtPerfil;
    private javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables
}
