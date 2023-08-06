/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.LeGnus_Disparador.view;

import br.com.LeGnus_Disparador.model.ModuloConexao;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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

    JavascriptExecutor js;
    WebElement Certificar;
    WebDriver driver;
    Actions act;

    int aux = 0;
    int contagemMensagem;

    int forScroll = 0;

    String confDriver;
    String confExe;
    String confProf;
    String confSleepInicio;
    String confVelocidadeInicio;
    String confScroll;
    String confCaixaPesquisa;
    String confCaixaPesquisaHTML;
    String confMidiaClick;
    String confMidiaSendFile;
    String confMidiaMensagem;
    String confMidiaMensagemHTLM;
    String confMidiaSend;
    String confMensagemPath;
    String confMensagemPathHTLM;
    String confBotaoFecharPath;
    String confSleepMensagens;

    /**
     * O conjunto de objetos abaixo são responsaves pelo funcionamento da tela.
     */
    public TelaDisparo() {
        initComponents();
        conexao = ModuloConexao.conector();
        setIcon();

    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/br/com/LeGnus_Disparador/util/ERPGestao64.png")));
    }

    private void setCliente() {
        pnExibição.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Clientes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N
        Limpar();
        rbLimitarID.setSelected(false);
        rbLimitarCategoria.setSelected(false);
        Limitar();
        instanciarCategoria();
        instanciarTabela();
    }

    private void setGrupo() {
        pnExibição.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Grupos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N
        Limpar();
        rbLimitarID.setSelected(false);
        rbLimitarCategoria.setSelected(false);
        Limitar();
        instanciarCategoria();
        instanciarTabela();
    }

    private void Limitar() {
        try {
            if (rbLimitarID.isSelected() == true) {
                txtInicial.setEnabled(true);
                txtFinal.setEnabled(true);
                txtInicial.setText("0");
                txtFinal.setText("100000");

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

    private void montarLista() {
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
                    ResultadoCliente = " conjuntocliente = " + "'" + tbCategoriaSelecionada.getModel().getValueAt(i, 0).toString() + "'";
                    listaCliente = ResultadoCliente;
                } else {
                    ResultadoCliente = " or conjuntocliente = " + "'" + tbCategoriaSelecionada.getModel().getValueAt(i, 0).toString() + "'";
                    listaCliente = listaCliente + ResultadoCliente;
                }
            }

            instanciarTabela();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            Limpar();
        }
    }

    private void instanciarTabela() {
        try {
            String sql;
            int i = 0;

            if (rbGrupo.isSelected() == true) {
                sql = "select idgrupo as NºGrupo, nomeGrupo as Grupo, conjunto as Categoria from tbgrupos";

                if (rbLimitarCategoria.isSelected() == true || rbLimitarID.isSelected() == true || rbNaoEnviados.isSelected() == true) {

                    sql = sql + " where";

                    if (rbLimitarID.isSelected() == true && i < 1) {
                        sql = sql + "  idgrupo>= " + txtInicial.getText() + " and idgrupo <= " + txtFinal.getText();
                        i++;
                    } else if (rbLimitarID.isSelected() == true && i >= 1) {
                        sql = sql + "  and idgrupo>= " + txtInicial.getText() + " and idgrupo <= " + txtFinal.getText();
                    }

                    if (rbLimitarCategoria.isSelected() == true && i < 1) {
                        sql = sql + " " + lista;
                        i++;
                    } else if (rbLimitarCategoria.isSelected() == true && i >= 1) {
                        sql = sql + " and" + lista;
                    }

                    if (rbNaoEnviados.isSelected() == true && i < 1) {
                        sql = sql + " ultimoEnvioG != current_date()";
                        i++;
                    } else if (rbNaoEnviados.isSelected() == true && i >= 1) {
                        sql = sql + " and ultimoEnvioG != current_date()";
                    }
                }

                System.out.println(i);
                System.out.println(sql);

                pst = conexao.prepareStatement(sql);
                rs = pst.executeQuery();
                tbExibicao.setModel(DbUtils.resultSetToTableModel(rs));

            } else if (rbCliente.isSelected() == true) {
                sql = "select idcliente as NºCliente, nomeCliente as Cliente,conjuntocliente as Categoria from tbclientes ";

                if (rbLimitarCategoria.isSelected() == true || rbLimitarID.isSelected() == true || rbNaoEnviados.isSelected() == true) {

                    sql = sql + " where";

                    if (rbLimitarID.isSelected() == true && i < 1) {
                        sql = sql + "  idcliente>= " + txtInicial.getText() + " and idcliente <= " + txtFinal.getText();
                        i++;
                    } else if (rbLimitarID.isSelected() == true && i >= 1) {
                        sql = sql + "  and idcliente>= " + txtInicial.getText() + " and idcliente <= " + txtFinal.getText();
                    }

                    if (rbLimitarCategoria.isSelected() == true && i < 1) {
                        sql = sql + " " + listaCliente;
                        i++;
                    } else if (rbLimitarCategoria.isSelected() == true && i >= 1) {
                        sql = sql + " and" + listaCliente;
                    }

                    if (rbNaoEnviados.isSelected() == true && i < 1) {
                        sql = sql + " ultimoEnvioC != current_date()";
                        i++;
                    } else if (rbNaoEnviados.isSelected() == true && i >= 1) {
                        sql = sql + " and ultimoEnvioC != current_date()";
                    }
                }

                System.out.println(i);
                System.out.println(sql);

                pst = conexao.prepareStatement(sql);
                rs = pst.executeQuery();
                tbExibicao.setModel(DbUtils.resultSetToTableModel(rs));

            } else {
                JOptionPane.showMessageDialog(null, "Combinação desconhecida");
            }

            i = 0;

        } catch (java.lang.NumberFormatException e) {

            if (txtInicial.getText().isBlank()) {
                txtInicial.setText("0");
            } else if (txtFinal.getText().isBlank()) {
                txtFinal.setText("100000");
            } else {
                JOptionPane.showMessageDialog(null, "O ID inicial/final só aceita numeros.");
            }

        } catch (java.sql.SQLSyntaxErrorException e) {

            if (txtInicial.getText().isBlank()) {
                txtInicial.setText("0");
            } else if (txtFinal.getText().isBlank()) {
                txtFinal.setText("100000");
            } else {
                txtInicial.setText("0");
                txtFinal.setText("100000");
                JOptionPane.showMessageDialog(null, "LIMITAR ID SÓ ACEITA NUMEROS \n" + "Erro de sintaxe: " + e);
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

            confDriver = tbConfig.getModel().getValueAt(0, 1).toString();
            confExe = tbConfig.getModel().getValueAt(0, 2).toString();
            confProf = tbConfig.getModel().getValueAt(0, 3).toString();
            confSleepInicio = tbConfig.getModel().getValueAt(0, 4).toString();
            confVelocidadeInicio = tbConfig.getModel().getValueAt(0, 5).toString();
            confScroll = tbConfig.getModel().getValueAt(0, 6).toString();
            confCaixaPesquisa = tbConfig.getModel().getValueAt(0, 7).toString();
            confCaixaPesquisaHTML = tbConfig.getModel().getValueAt(0, 8).toString();
            confMidiaClick = tbConfig.getModel().getValueAt(0, 9).toString();
            confMidiaSendFile = tbConfig.getModel().getValueAt(0, 10).toString();
            confMidiaMensagem = tbConfig.getModel().getValueAt(0, 11).toString();
            confMidiaMensagemHTLM = tbConfig.getModel().getValueAt(0, 12).toString();
            confMidiaSend = tbConfig.getModel().getValueAt(0, 13).toString();
            confMensagemPath = tbConfig.getModel().getValueAt(0, 14).toString();
            confMensagemPathHTLM = tbConfig.getModel().getValueAt(0, 15).toString();
            confBotaoFecharPath = tbConfig.getModel().getValueAt(0, 16).toString();
            confSleepMensagens = tbConfig.getModel().getValueAt(0, 17).toString();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    private void PesquisarGrupo() {
        try {
            String sql;

            if (rbGrupo.isSelected() == true) {
                sql = "select idgrupo as NºGrupo, nomeGrupo as Grupo, conjunto as Categoria from tbgrupos where nomeGrupo like ?";

                if (rbLimitarID.isSelected() == true) {
                    sql = sql + " and idgrupo>= " + txtInicial.getText() + " and idgrupo <= " + txtFinal.getText();
                }

                if (rbLimitarCategoria.isSelected() == true) {
                    sql = sql + " and" + lista;
                }

                if (rbNaoEnviados.isSelected() == true) {
                    sql = sql + " and ultimoEnvioG != current_date()";
                }

                System.out.println(sql);

                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtPesquisa.getText() + "%");
                rs = pst.executeQuery();
                tbExibicao.setModel(DbUtils.resultSetToTableModel(rs));

            } else if (rbCliente.isSelected() == true) {
                sql = "select idcliente as NºCliente, nomeCliente as Cliente,conjuntocliente as Categoria from tbclientes where nomecliente like ?";

                if (rbLimitarID.isSelected() == true) {
                    sql = sql + " and idcliente>= " + txtInicial.getText() + " and idcliente <= " + txtFinal.getText();
                }

                if (rbLimitarCategoria.isSelected() == true) {
                    sql = sql + " and" + listaCliente;
                }

                if (rbNaoEnviados.isSelected() == true) {
                    sql = sql + " and ultimoEnvioC != current_date()";
                }

                System.out.println(sql);

                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtPesquisa.getText() + "%");
                rs = pst.executeQuery();
                tbExibicao.setModel(DbUtils.resultSetToTableModel(rs));

            } else {
                JOptionPane.showMessageDialog(null, "Combinação desconhecida");
            }

        } catch (java.lang.NumberFormatException e) {

            if (txtInicial.getText().isBlank()) {
                txtInicial.setText("0");
            } else if (txtFinal.getText().isBlank()) {
                txtFinal.setText("100000");
            } else {
                JOptionPane.showMessageDialog(null, "O ID inicial/final só aceita numeros.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    private void instanciarCategoria() {
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

    private void Limpar() {
        btnEnviarCategoria.setEnabled(false);
        btnRemoverCategoria.setEnabled(false);
        btnEnviarMensagem.setEnabled(false);
        btnRemoverMensagem.setEnabled(false);
        ((DefaultTableModel) tbCategoriaSelecionada.getModel()).setRowCount(0);
        ((DefaultTableModel) tbMensagemSelecionada.getModel()).setRowCount(0);
        instanciarTbConfig();

    }

    /**
     * O conjunto de objetos abaixo são responsaveis pelo disparo
     */
    private void disparar() {
        try {

            /**
             * Configuração do Navegador*
             */
            
            
            System.setProperty("webdriver.gecko.driver", confDriver);
            FirefoxOptions options = new FirefoxOptions();
            options.setBinary(confExe);
            
            File firefoxProfileFolder = new File(confProf);
            FirefoxProfile profile = new FirefoxProfile(firefoxProfileFolder); 
            profile.setAcceptUntrustedCertificates(true);
            options.setProfile(profile);            
            
            /**
             * Configuração do Driver && Lista de Mensagens selecionadas*
             */
            montarMensagem();
            driver = new FirefoxDriver(options);
            act = new Actions(driver);
            driver.get("https://web.whatsapp.com/");

            /**
             * Configuração do Driver && Lista de Mensagens selecionadas*
             */
            descerScroll();

            if (forScroll >= 500000) {

                /**
                 * Disparo*
                 */
                for (int i = 0; i < tbExibicao.getRowCount(); i++) {

                    Thread.sleep(1000);
                    pesquisarNome();
                    Thread.sleep(3000);
                    mensagemDisparo();
                    

                    aux++;

                }
            }

        } catch (org.openqa.selenium.remote.UnreachableBrowserException e) {
            System.out.println("Disparo: " + e);

        } catch (org.openqa.selenium.WebDriverException e) {
            JOptionPane.showMessageDialog(null, "Disparo error: " + e);
            diferenciarFechamento();
            
            System.out.println("Disparo: " + e);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Disparo error: " + e);
            System.out.println("Disparo: " + e);
            Limpar();
        }
    }

    private void diferenciarFechamento() {
        try {
            int o = JOptionPane.showConfirmDialog(null, "O disparo foi interrompido, você deseja continua-lo?");
            if (o == 0) {
                driver.quit();
                disparar();
            } else if (o == 1) {
                driver.quit();
            } else {
                driver.quit();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Decida error: " + e);
            System.out.println("Decida: " + e);
            Limpar();
        }
    }

    private void descerScroll() {
        try {
            Thread.sleep(Integer.parseInt(confSleepInicio));
            js = (JavascriptExecutor) driver;           

            for (forScroll = 1; forScroll <= 400000; forScroll = forScroll + Integer.parseInt(confVelocidadeInicio)) {
                js.executeScript(confScroll + forScroll + ");");
            }
            
            for (forScroll = 400000; forScroll <= 0; forScroll = forScroll - Integer.parseInt(confVelocidadeInicio)) {
                js.executeScript(confScroll + forScroll + ");");
            }
            
            for (forScroll = 1; forScroll <= 500000; forScroll = forScroll + Integer.parseInt(confVelocidadeInicio)) {
                js.executeScript(confScroll + forScroll + ");");
            }

        } catch (org.openqa.selenium.JavascriptException e) {
            JOptionPane.showMessageDialog(null, "Scroll não encontrado, aumente o tempo (SleepInicio)");
            System.out.println("Scroll: " + e);
            driver.quit();
            Limpar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Decida error: " + e);
            System.out.println("Scroll: " + e);
            Limpar();
        }
    }

    private void montarMensagem() {
        try {
            String Resultado;

            String sql = "select * from tbmensagens";
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            tbAux.setModel(DbUtils.resultSetToTableModel(rs));

            for (int i = 0; tbAux.getRowCount() > i; i++) {

                sql = "update tbmensagens set ordem=? where idmensagem=?";
                pst = conexao.prepareStatement(sql);
                pst.setInt(1, 0);
                pst.setInt(2, i + 1);
                pst.executeUpdate();

            }

            for (int i = 0; tbMensagemSelecionada.getRowCount() > i; i++) {
                if (i == 0) {
                    Resultado = " titulo = " + "'" + tbMensagemSelecionada.getModel().getValueAt(i, 0).toString() + "'";
                    listaMensagem = Resultado;
                    sql = "update tbmensagens set ordem=? where mensagem=?";
                    pst = conexao.prepareStatement(sql);
                    pst.setInt(1, i);
                    pst.setString(2, tbMensagemSelecionada.getModel().getValueAt(i, 0).toString());
                    pst.executeUpdate();

                } else {
                    Resultado = " or titulo = " + "'" + tbMensagemSelecionada.getModel().getValueAt(i, 0).toString() + "'";
                    listaMensagem = listaMensagem + Resultado;
                    sql = "update tbmensagens set ordem=? where mensagem=?";
                    pst = conexao.prepareStatement(sql);
                    pst.setInt(1, i);
                    pst.setString(2, tbMensagemSelecionada.getModel().getValueAt(i, 0).toString());
                    pst.executeUpdate();
                }
            }

            String sqy = "select mensagem, midia from tbmensagens where " + listaMensagem + " ORDER BY ordem asc";
            pst = conexao.prepareStatement(sqy);
            rs = pst.executeQuery();
            tbAux.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            System.out.println("Montar Mensagem : " + e);
            Limpar();
        }
    }

    private void apagarPesquisa() {
        try {
            driver.findElement(By.cssSelector(confCaixaPesquisa)).click();
            for (int n = 0; n <= 100; n++) {
                Thread.sleep(0, 5);
                act.sendKeys(Keys.DELETE).perform();
            }
            for (int n = 0; n <= 100; n++) {
                Thread.sleep(0, 5);
                act.sendKeys(Keys.BACK_SPACE).perform();
            }

        } catch (org.openqa.selenium.remote.UnreachableBrowserException e) {

        } catch (org.openqa.selenium.WebDriverException e) {

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Apagar Erro:" + e);
            System.out.println("Apagar Pesquisa : " + e);
            Limpar();
        }
    }

    private void pesquisarNome() {
        try {
            apagarPesquisa();
            driver.findElement(By.cssSelector(confCaixaPesquisa)).click();
            Thread.sleep(500);
            act.sendKeys(".").perform();
            Thread.sleep(Integer.parseInt(confSleepMensagens));
            js = (JavascriptExecutor) driver;
            js.executeScript(confCaixaPesquisaHTML + tbExibicao.getModel().getValueAt(aux, 1).toString() + "';");

        } catch (org.openqa.selenium.remote.UnreachableBrowserException e) {
            System.out.println("Pesquisar Nome: " + e);

        } catch (org.openqa.selenium.WebDriverException e) {
            System.out.println("Pesquisar Nome: " + e);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Pesquisar Erro:" + e);
            System.out.println("Pesquisar Nome: " + e);
            Limpar();
        }
    }

    private void validarNome() {
        try {
            Thread.sleep(Integer.parseInt(confSleepMensagens));
            Certificar = driver.findElement(By.cssSelector("span[title='" + tbExibicao.getModel().getValueAt(aux, 1).toString() + "']"));
        } catch (org.openqa.selenium.remote.UnreachableBrowserException e) {

        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.out.println("Validar Nome: " + e);
            Certificar = null;
        } catch (org.openqa.selenium.WebDriverException e) {
            System.out.println("Validar Nome: " + e);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Nome Erro:" + e);
            System.out.println("Validar Nome: " + e);
            Limpar();
        }
    }

    private void listaNegra() {
        try {
            String sql = "insert into tbErro(nomeInserido,categoria) values(?,?)";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tbExibicao.getModel().getValueAt(aux, 1).toString());
            if (rbCliente.isSelected() == true) {
                pst.setString(2, "Cliente");
            } else {
                pst.setString(2, "Grupo");
            }

            pst.executeUpdate();

        } catch (org.openqa.selenium.remote.UnreachableBrowserException e) {
            System.out.println("Lista Negra: " + e);
        } catch (org.openqa.selenium.WebDriverException e) {
            System.out.println("Lista Negra: " + e);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lista Negra Error:" + e);
            System.out.println("Lista Negra: " + e);
        }
    }

    private void mensagemDisparo() {
        try {
            driver.findElement(By.cssSelector("span[title='" + tbExibicao.getModel().getValueAt(aux, 1).toString() + "']")).click();
            Thread.sleep(2000);

            js = (JavascriptExecutor) driver;

            for (int o = 0; o < tbAux.getRowCount(); o++) {
                contagemMensagem = o;
                if (tbAux.getModel().getValueAt(o, 1).toString().isBlank() == false) {
                    Thread.sleep(1000);
                    driver.findElement(By.cssSelector(confMidiaClick)).click();
                    Thread.sleep(1000);
                    driver.findElement(By.cssSelector(confMidiaSendFile)).sendKeys(tbAux.getModel().getValueAt(o, 1).toString());
                    Thread.sleep(10000);
                    driver.findElement(By.cssSelector(confMidiaMensagem)).click();
                    Thread.sleep(500);
                    act.sendKeys(".").perform();
                    Thread.sleep(Integer.parseInt(confSleepMensagens));
                    js.executeScript(confMidiaMensagemHTLM + tbAux.getModel().getValueAt(o, 0).toString() + "';");
                    Thread.sleep(1000);
                    driver.findElement(By.cssSelector(confMidiaSend)).click();
                    Thread.sleep(1000);

                } else if (tbAux.getModel().getValueAt(o, 1).toString().isBlank() == true) {
                    Thread.sleep(1000);
                    driver.findElement(By.cssSelector(confMensagemPath)).click();
                    Thread.sleep(500);
                    act.sendKeys(".").perform();
                    Thread.sleep(Integer.parseInt(confSleepMensagens));
                    js.executeScript(confMensagemPathHTLM + tbAux.getModel().getValueAt(o, 0).toString() + "';");
                    Thread.sleep(1000);
                    act.sendKeys(Keys.ENTER).perform();
                    Thread.sleep(1000);
                }
            }
            ultimoEnvio();

        } catch (org.openqa.selenium.remote.UnreachableBrowserException e) {
            System.out.println("MensagemDisparo: " + e);

        } catch (org.openqa.selenium.ElementNotInteractableException e) {
            if (tbAux.getModel().getValueAt(contagemMensagem, 1).toString().isBlank() == false) {
                driver.findElement(By.cssSelector(confBotaoFecharPath)).click();
            } else if (tbAux.getModel().getValueAt(contagemMensagem, 1).toString().isBlank() == true) {
                JOptionPane.showMessageDialog(null, "Mensagem Escrita Erro:" + e);
            }
            System.out.println("MensagemDisparo: " + e);

        } catch (org.openqa.selenium.WebDriverException e) {
            System.out.println("MensagemDisparo: " + e);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Mensagem Erro:" + e);
            System.out.println("MensagemDisparo: " + e);
            Limpar();
        }
    }

    private void ultimoEnvio() {
        try {
            Date data = new Date();

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            java.sql.Date dSql = new java.sql.Date(data.getTime());
            df.format(dSql);

            if (rbCliente.isSelected() == true) {
                String sql = "update tbclientes set ultimoEnvioC=? where nomecliente=?";

                pst = conexao.prepareStatement(sql);
                pst.setDate(1, dSql);
                pst.setString(2, tbExibicao.getModel().getValueAt(aux, 1).toString());
                pst.executeUpdate();

            } else if (rbGrupo.isSelected() == true) {
                String sql = "update tbgrupos set ultimoEnvioG=? where nomeGrupo=?";

                pst = conexao.prepareStatement(sql);
                pst.setDate(1, dSql);
                pst.setString(2, tbExibicao.getModel().getValueAt(aux, 1).toString());
                pst.executeUpdate();
            }
        } catch (org.openqa.selenium.remote.UnreachableBrowserException e) {
            System.out.println("ultimoEnvio: " + e);

        } catch (org.openqa.selenium.WebDriverException e) {
            System.out.println("ultimoEnvio: " + e);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "UltimoEnvio error: " + e);
            System.out.println("ultimoEnvio: " + e);

        }
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
        pnExibição = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbExibicao = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtPesquisa = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
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
        btnDisparar = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        rbNaoEnviados = new javax.swing.JRadioButton();
        jPanel6 = new javax.swing.JPanel();
        rbCliente = new javax.swing.JRadioButton();
        rbGrupo = new javax.swing.JRadioButton();
        jPanel7 = new javax.swing.JPanel();
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

        pnExibição.setBackground(new java.awt.Color(204, 204, 204));
        pnExibição.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Grupos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

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

        javax.swing.GroupLayout pnExibiçãoLayout = new javax.swing.GroupLayout(pnExibição);
        pnExibição.setLayout(pnExibiçãoLayout);
        pnExibiçãoLayout.setHorizontalGroup(
            pnExibiçãoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnExibiçãoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnExibiçãoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pnExibiçãoLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPesquisa)))
                .addContainerGap())
        );
        pnExibiçãoLayout.setVerticalGroup(
            pnExibiçãoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnExibiçãoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnExibiçãoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Parametros", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

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
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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
                        .addComponent(rbLimitarCategoria)
                        .addGap(0, 235, Short.MAX_VALUE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(pnCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEnviarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRemoverCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnCategoriaSelecionada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(rbLimitarCategoria)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEnviarCategoria)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoverCategoria)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnCategoriaSelecionada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
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
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEnviarMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemoverMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnEnviarMensagem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemoverMensagem)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        btnDisparar.setBackground(new java.awt.Color(0, 0, 153));
        btnDisparar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnDisparar.setForeground(new java.awt.Color(255, 255, 255));
        btnDisparar.setText("Disparar");
        btnDisparar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDispararActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDisparar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDisparar, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel10.setBackground(new java.awt.Color(204, 204, 204));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtros", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        rbNaoEnviados.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        rbNaoEnviados.setText("Somente não enviados");
        rbNaoEnviados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbNaoEnviadosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbNaoEnviados)
                .addContainerGap(8, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rbNaoEnviados)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        alvo.add(rbCliente);
        rbCliente.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        rbCliente.setText("Clientes");
        rbCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbClienteActionPerformed(evt);
            }
        });

        alvo.add(rbGrupo);
        rbGrupo.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        rbGrupo.setSelected(true);
        rbGrupo.setText("Grupos");
        rbGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbGrupoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbGrupo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbGrupo)
                    .addComponent(rbCliente))
                .addGap(15, 15, 15))
        );

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        rbLimitarID.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        rbLimitarID.setText("Limitar de ");
        rbLimitarID.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbLimitarIDMouseClicked(evt);
            }
        });
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

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbLimitarID)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbLimitarID)
                    .addComponent(txtInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnExibição, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnExibição, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(6, 6, 6))
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

    private void rbGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbGrupoActionPerformed
        // TODO add your handling code here:
        setGrupo();

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
        setCliente();
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

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        TelaPrincipal principal = new TelaPrincipal();
        principal.setVisible(true);
    }//GEN-LAST:event_formWindowClosed

    private void btnDispararActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDispararActionPerformed
        // TODO add your handling code here:
        disparar();
    }//GEN-LAST:event_btnDispararActionPerformed

    private void rbNaoEnviadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbNaoEnviadosActionPerformed
        // TODO add your handling code here:
        instanciarTabela();
    }//GEN-LAST:event_rbNaoEnviadosActionPerformed

    private void rbLimitarIDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbLimitarIDMouseClicked
        // TODO add your handling code here:
        instanciarTabela();
    }//GEN-LAST:event_rbLimitarIDMouseClicked

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
    private javax.swing.JButton btnDisparar;
    private javax.swing.JButton btnEnviarCategoria;
    private javax.swing.JButton btnEnviarMensagem;
    private javax.swing.JButton btnRemoverCategoria;
    private javax.swing.JButton btnRemoverMensagem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPanel pnCategoria;
    private javax.swing.JPanel pnCategoriaSelecionada;
    private javax.swing.JPanel pnExibição;
    private javax.swing.JRadioButton rbCliente;
    private javax.swing.JRadioButton rbGrupo;
    private javax.swing.JRadioButton rbLimitarCategoria;
    private javax.swing.JRadioButton rbLimitarID;
    private javax.swing.JRadioButton rbNaoEnviados;
    private javax.swing.JScrollPane scAux;
    private javax.swing.JScrollPane scConfig;
    private javax.swing.JTable tbAux;
    private javax.swing.JTable tbCategoria;
    private javax.swing.JTable tbCategoriaSelecionada;
    private javax.swing.JTable tbConfig;
    private javax.swing.JTable tbExibicao;
    private javax.swing.JTable tbMensagem;
    private javax.swing.JTable tbMensagemSelecionada;
    private javax.swing.JTextField txtFinal;
    private javax.swing.JTextField txtInicial;
    private javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables
}
