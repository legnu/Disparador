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
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Ad3ln0r
 */
public class TelaConfiguracao extends javax.swing.JFrame {

    /**
     * Creates new form TelaConfiguração
     */
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaConfiguracao() {
        initComponents();
        conexao = ModuloConexao.conector();
        setIcon();
    }

    private void instaciarConfig() {

        try {

            String sql = "select * from tbconfig";
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            tbAux.setModel(DbUtils.resultSetToTableModel(rs));

            txtDriver.setText(tbAux.getModel().getValueAt(0, 1).toString());
            txtExe.setText(tbAux.getModel().getValueAt(0, 2).toString());
            txtProfile.setText(tbAux.getModel().getValueAt(0, 3).toString());
            txtSleepInicio.setText(tbAux.getModel().getValueAt(0, 4).toString());
            txtVelocidadeInicioScroll.setText(tbAux.getModel().getValueAt(0, 5).toString());
            txtScroll.setText(tbAux.getModel().getValueAt(0, 6).toString());
            txtPesquisa.setText(tbAux.getModel().getValueAt(0, 7).toString());
            txtPesquisaHTML.setText(tbAux.getModel().getValueAt(0, 8).toString());
            txtMidiaClick.setText(tbAux.getModel().getValueAt(0, 9).toString());
            txtMidiaSendFile.setText(tbAux.getModel().getValueAt(0, 10).toString());
            txtMidiaMensagem.setText(tbAux.getModel().getValueAt(0, 11).toString());
            txtMidiaMensagemHTML.setText(tbAux.getModel().getValueAt(0, 12).toString());
            txtMidiaSend.setText(tbAux.getModel().getValueAt(0, 13).toString());
            txtMensagem.setText(tbAux.getModel().getValueAt(0, 14).toString());
            txtMensagemHTML.setText(tbAux.getModel().getValueAt(0, 15).toString());
            txtFecharPath.setText(tbAux.getModel().getValueAt(0, 16).toString());
            txtSleepMensagens.setText(tbAux.getModel().getValueAt(0, 17).toString());

        } catch (java.lang.NullPointerException e) {
            setDefault();
            salvarConfiguração();
            instaciarConfig();

        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            adcionarID();
            instaciarConfig();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "config: " + e);

        }

    }

    private void adcionarID() {
        try {
            String sql = "insert into tbconfig(idconf) values(1);";
            pst = conexao.prepareStatement(sql);
            pst.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Adcionar: " + e);

        }
    }

    private void buscarExecutavel() {
        try {
            JFileChooser arquivo = new JFileChooser();
            arquivo.setDialogTitle("EXECUTAVEL");
            arquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int op = arquivo.showOpenDialog(this);
            if (op == JFileChooser.APPROVE_OPTION) {
                File file = new File("");
                file = arquivo.getSelectedFile();
                String fileName = file.getAbsolutePath();
                txtExe.setText(fileName);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void buscarDriver() {
        try {
            JFileChooser arquivo = new JFileChooser();
            arquivo.setDialogTitle("DRIVER");
            arquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int op = arquivo.showOpenDialog(this);
            if (op == JFileChooser.APPROVE_OPTION) {
                File file = new File("");
                file = arquivo.getSelectedFile();
                String fileName = file.getAbsolutePath();
                txtDriver.setText(fileName);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void salvarConfiguração() {
        try {
            String sql = "update tbconfig set Driver=?,Exe=?,Prof=?,Sleep_Inicio=?,Velocidade_Inicio=?,Scroll=?,Caixa_Pesquisa=?,Caixa_Pesquisa_HTML=?,Midia_Click=?,Midia_Send_File=?,Midia_Mensagem=?,Midia_Mensagem_HTML=?,Midia_Send=?,Mensagem_Path=?,Mensagem_HTML_Path=?,BotaoFecharPath=?,SleepMensagens=? where idconf=1";

            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtDriver.getText());
            pst.setString(2, txtExe.getText());
            pst.setString(3, txtProfile.getText());
            pst.setString(4, txtSleepInicio.getText());
            pst.setString(5, txtVelocidadeInicioScroll.getText());
            pst.setString(6, txtScroll.getText());
            pst.setString(7, txtPesquisa.getText());
            pst.setString(8, txtPesquisaHTML.getText());
            pst.setString(9, txtMidiaClick.getText());
            pst.setString(10, txtMidiaSendFile.getText());
            pst.setString(11, txtMidiaMensagem.getText());
            pst.setString(12, txtMidiaMensagemHTML.getText());
            pst.setString(13, txtMidiaSend.getText());
            pst.setString(14, txtMensagem.getText());
            pst.setString(15, txtMensagemHTML.getText());
            pst.setString(16, txtFecharPath.getText());
            pst.setString(17, txtSleepMensagens.getText());
            pst.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Salvar" + e);

        }

    }

    private void setDefault() {
        txtDriver.setText("");
        txtExe.setText("");
        txtMensagem.setText("div[title='Mensagem']");
        txtMensagemHTML.setText("document.getElementsByClassName(\"selectable-text copyable-text\")[parseInt(document.getElementsByClassName(\"selectable-text copyable-text\").length) - 1].firstChild.data = '");
        txtMidiaClick.setText("span[data-icon='clip']");
        txtMidiaMensagem.setText("div[title='Mensagem']");
        txtMidiaMensagemHTML.setText("document.getElementsByClassName(\"selectable-text copyable-text\")[1].firstChild.data = '");
        txtMidiaSend.setText("span[data-icon='send']");
        txtMidiaSendFile.setText("input[type='file']");
        txtPesquisa.setText("div[title='Caixa de texto de pesquisa']");
        txtPesquisaHTML.setText("document.getElementsByClassName(\"selectable-text copyable-text\")[1].firstChild.data = '");
        txtProfile.setText("");
        txtScroll.setText("document.querySelector('[id=\"pane-side\"]').scroll(0,");
        txtSleepInicio.setText("300000");
        txtVelocidadeInicioScroll.setText("150");
        txtFecharPath.setText("span[data-icon='x']");
        txtSleepMensagens.setText("3000");
    }

     
     
    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/br/com/LeGnus_Disparador/util/ERPGestao64.png")));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbAux = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtDriver = new javax.swing.JTextField();
        btnDriver = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        txtProfile = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        txtExe = new javax.swing.JTextField();
        btnExe = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        txtVelocidadeInicioScroll = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        txtScroll = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        txtSleepInicio = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        txtPesquisa = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        txtPesquisaHTML = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        txtMidiaClick = new javax.swing.JTextField();
        jPanel21 = new javax.swing.JPanel();
        txtMensagemHTML = new javax.swing.JTextField();
        jPanel22 = new javax.swing.JPanel();
        txtMensagem = new javax.swing.JTextField();
        jPanel23 = new javax.swing.JPanel();
        txtMidiaSend = new javax.swing.JTextField();
        jPanel24 = new javax.swing.JPanel();
        txtMidiaMensagemHTML = new javax.swing.JTextField();
        jPanel25 = new javax.swing.JPanel();
        txtMidiaMensagem = new javax.swing.JTextField();
        jPanel26 = new javax.swing.JPanel();
        txtMidiaSendFile = new javax.swing.JTextField();
        jPanel27 = new javax.swing.JPanel();
        txtFecharPath = new javax.swing.JTextField();
        jPanel28 = new javax.swing.JPanel();
        txtSleepMensagens = new javax.swing.JTextField();
        botaoArredondado1 = new br.com.LeGnus_Disparador.Swing.botaoArredondado();
        botaoArredondado2 = new br.com.LeGnus_Disparador.Swing.botaoArredondado();

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
        jScrollPane2.setViewportView(tbAux);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Legnu's_Disparador - TelaConfiguração");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(java.awt.SystemColor.control);
        jPanel1.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(204, 204, 204))));

        jPanel2.setBackground(java.awt.SystemColor.control);
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Chrome Driver / Gecko Driver", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(260, 60));

        btnDriver.setText("Buscar");
        btnDriver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDriverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(txtDriver, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                .addGap(24, 24, 24)
                .addComponent(btnDriver)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDriver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDriver))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel5.setBackground(java.awt.SystemColor.control);
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Profile Path", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N
        jPanel5.setPreferredSize(new java.awt.Dimension(260, 60));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtProfile, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtProfile, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jPanel6.setBackground(java.awt.SystemColor.control);
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Chrome.exe / Firefox.exe", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N
        jPanel6.setPreferredSize(new java.awt.Dimension(260, 60));

        btnExe.setText("Buscar");
        btnExe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(txtExe, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                .addGap(24, 24, 24)
                .addComponent(btnExe)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtExe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExe))
                .addGap(0, 1, Short.MAX_VALUE))
        );

        jPanel8.setBackground(java.awt.SystemColor.control);
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Velocidade Scroll Inicio", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N
        jPanel8.setPreferredSize(new java.awt.Dimension(260, 60));

        txtVelocidadeInicioScroll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVelocidadeInicioScrollActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtVelocidadeInicioScroll)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtVelocidadeInicioScroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel9.setBackground(java.awt.SystemColor.control);
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Scroll Path", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N
        jPanel9.setPreferredSize(new java.awt.Dimension(260, 60));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtScroll)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtScroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel10.setBackground(java.awt.SystemColor.control);
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Sleep Inicio (Milissegundo)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N
        jPanel10.setPreferredSize(new java.awt.Dimension(260, 60));

        txtSleepInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSleepInicioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSleepInicio)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSleepInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel13.setBackground(java.awt.SystemColor.control);
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Caixa de Pesquisa Path", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N
        jPanel13.setPreferredSize(new java.awt.Dimension(260, 60));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPesquisa)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jPanel14.setBackground(java.awt.SystemColor.control);
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Caixa de Pesquisa HTML Path", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N
        jPanel14.setPreferredSize(new java.awt.Dimension(260, 60));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPesquisaHTML)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPesquisaHTML)
        );

        jPanel15.setBackground(java.awt.SystemColor.control);
        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Midia Clip / Midia Cross (Click)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N
        jPanel15.setPreferredSize(new java.awt.Dimension(260, 60));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMidiaClick)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMidiaClick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel21.setBackground(java.awt.SystemColor.control);
        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Mensagem HTLM Path", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N
        jPanel21.setPreferredSize(new java.awt.Dimension(260, 60));

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMensagemHTML)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMensagemHTML, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel22.setBackground(java.awt.SystemColor.control);
        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Mensagem Path", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N
        jPanel22.setPreferredSize(new java.awt.Dimension(260, 60));

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMensagem)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel23.setBackground(java.awt.SystemColor.control);
        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Midia Mensagem Send Path", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N
        jPanel23.setPreferredSize(new java.awt.Dimension(260, 60));

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMidiaSend)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMidiaSend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel24.setBackground(java.awt.SystemColor.control);
        jPanel24.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Midia Mensagem HTML Path", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N
        jPanel24.setPreferredSize(new java.awt.Dimension(260, 60));

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMidiaMensagemHTML)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(txtMidiaMensagemHTML, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel25.setBackground(java.awt.SystemColor.control);
        jPanel25.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Midia Mensagem Path", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N
        jPanel25.setPreferredSize(new java.awt.Dimension(260, 60));

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMidiaMensagem)
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMidiaMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel26.setBackground(java.awt.SystemColor.control);
        jPanel26.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Midia Send File Path", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N
        jPanel26.setPreferredSize(new java.awt.Dimension(260, 60));

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMidiaSendFile)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMidiaSendFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel27.setBackground(java.awt.SystemColor.control);
        jPanel27.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Botão Fechar Path", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N
        jPanel27.setPreferredSize(new java.awt.Dimension(260, 60));

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtFecharPath, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(txtFecharPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel28.setBackground(java.awt.SystemColor.control);
        jPanel28.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Sleep Mensagens", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N
        jPanel28.setPreferredSize(new java.awt.Dimension(260, 60));

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSleepMensagens)
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSleepMensagens, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        botaoArredondado1.setForeground(new java.awt.Color(0, 153, 51));
        botaoArredondado1.setText("Aplicar");
        botaoArredondado1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        botaoArredondado1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoArredondado1ActionPerformed(evt);
            }
        });

        botaoArredondado2.setForeground(new java.awt.Color(153, 153, 0));
        botaoArredondado2.setText("Default");
        botaoArredondado2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        botaoArredondado2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoArredondado2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                            .addComponent(jPanel28, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                            .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                            .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                            .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(botaoArredondado1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(16, 16, 16)
                        .addComponent(botaoArredondado2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(16, 16, 16))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                    .addComponent(jPanel22, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE))
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                    .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE))
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE))
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE))
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                    .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoArredondado2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botaoArredondado1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
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

    private void txtSleepInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSleepInicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSleepInicioActionPerformed

    private void txtVelocidadeInicioScrollActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVelocidadeInicioScrollActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtVelocidadeInicioScrollActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        instaciarConfig();
    }//GEN-LAST:event_formWindowOpened

    private void btnDriverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDriverActionPerformed
        // TODO add your handling code here:
        buscarDriver();
    }//GEN-LAST:event_btnDriverActionPerformed

    private void btnExeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExeActionPerformed
        // TODO add your handling code here:
        buscarExecutavel();
    }//GEN-LAST:event_btnExeActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:        
        TelaPrincipal principal = new TelaPrincipal();
        principal.setVisible(true);
    }//GEN-LAST:event_formWindowClosed

    private void botaoArredondado1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoArredondado1ActionPerformed
        // TODO add your handling code here:
        salvarConfiguração();
    }//GEN-LAST:event_botaoArredondado1ActionPerformed

    private void botaoArredondado2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoArredondado2ActionPerformed
        // TODO add your handling code here:
        setDefault();
    }//GEN-LAST:event_botaoArredondado2ActionPerformed

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
            java.util.logging.Logger.getLogger(TelaConfiguracao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaConfiguracao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaConfiguracao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaConfiguracao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaConfiguracao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.LeGnus_Disparador.Swing.botaoArredondado botaoArredondado1;
    private br.com.LeGnus_Disparador.Swing.botaoArredondado botaoArredondado2;
    private javax.swing.JButton btnDriver;
    private javax.swing.JButton btnExe;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbAux;
    private javax.swing.JTextField txtDriver;
    private javax.swing.JTextField txtExe;
    private javax.swing.JTextField txtFecharPath;
    private javax.swing.JTextField txtMensagem;
    private javax.swing.JTextField txtMensagemHTML;
    private javax.swing.JTextField txtMidiaClick;
    private javax.swing.JTextField txtMidiaMensagem;
    private javax.swing.JTextField txtMidiaMensagemHTML;
    private javax.swing.JTextField txtMidiaSend;
    private javax.swing.JTextField txtMidiaSendFile;
    private javax.swing.JTextField txtPesquisa;
    private javax.swing.JTextField txtPesquisaHTML;
    private javax.swing.JTextField txtProfile;
    private javax.swing.JTextField txtScroll;
    private javax.swing.JTextField txtSleepInicio;
    private javax.swing.JTextField txtSleepMensagens;
    private javax.swing.JTextField txtVelocidadeInicioScroll;
    // End of variables declaration//GEN-END:variables
}
