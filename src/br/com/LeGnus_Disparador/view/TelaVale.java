/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.LeGnus_Disparador.view;

import br.com.LeGnus_Disparador.model.ModuloConexao;
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Ad3ln0r
 */
public class TelaVale extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String Senha;

    /**
     * Creates new form TelaVale
     */
    private void GerenciarSenha() {
        try {
            String sqo = "select senha from tbvale where idsenha=1";
            pst = conexao.prepareStatement(sqo);
            rs = pst.executeQuery();
            tbAuxilio.setModel(DbUtils.resultSetToTableModel(rs));

            if (tbAuxilio.getModel().getValueAt(0, 0).toString().equals("vazio") == true) {
                criarSenha();
            } else {
                Date data = new Date();
                String sqy = "select vencimento from tbvale where idsenha=1";
                pst = conexao.prepareStatement(sqy);
                rs = pst.executeQuery();
                tbAuxilio.setModel(DbUtils.resultSetToTableModel(rs));
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

                if (data.after(df.parse(tbAuxilio.getModel().getValueAt(0, 0).toString())) == true) {

                    criarSenha();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void criarSenha() {
        try {

            Date d = new Date();
            DateFormat df = new SimpleDateFormat("yyyy-MM-07");
            Senha = df.format(d).replace("-", "").replace("1", "G").replace("2", "Q").replace("3", "Z").replace("4", "W").replace("5", "S").replace("6", "N").replace("7", "B").replace("8", "L").replace("9", "T").replace("0", "H");

            Date data = new Date();
            DateFormat dq = new SimpleDateFormat("yyyy-MM-dd");

            String mes = new SimpleDateFormat("MM").format(data);
            String ano = new SimpleDateFormat("yyyy").format(data);

            int limite = Integer.parseInt(mes) + 1;

            if (limite > 12) {
                limite = limite - 12;
                ano = String.valueOf(Integer.parseInt(ano) + 1);
            }

            String dataLimite = ano + "-" + limite + "-07";

            Date nova = dq.parse(dataLimite);
            java.sql.Date dSqt = new java.sql.Date(nova.getTime());
            dq.format(dSqt);

            String sql = "update tbvale set senha=?, vencimento=? where idsenha=1";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, Senha);
            pst.setDate(2, dSqt);
            pst.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void validarSenha() {
        try {
            String sqo = "select senha from tbvale where idsenha=1";
            pst = conexao.prepareStatement(sqo);
            rs = pst.executeQuery();
            tbAuxilio.setModel(DbUtils.resultSetToTableModel(rs));
            
            Date data = new Date();
            DateFormat Dia = new SimpleDateFormat("dd");

            if (txtSenha.getText().equals(tbAuxilio.getModel().getValueAt(0, 0)) == true) {
                if (Dia.format(data).equals("04") == true) {
                    JOptionPane.showMessageDialog(null, "Antepenúltimo dia para o vencimento da Senha Mensal.");
                }
                if (Dia.format(data).equals("05") == true) {
                    JOptionPane.showMessageDialog(null, "Penúltimo dia para o vencimento da Senha Mensal.");
                }
                if (Dia.format(data).equals("06") == true) {
                    JOptionPane.showMessageDialog(null, "Último dia para o vencimento da Senha Mensal.");
                }
                TelaPrincipal principal = new TelaPrincipal();
                principal.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Senha Invalida, Entre em contato com o nosso suporte...");
                txtSenha.setText(null);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);

        }
    }

    public TelaVale() {

        initComponents();
        conexao = ModuloConexao.conector();
        //A linha abaixo serve de apoio ao Status da conexão
        //System.out.println(conexao);
        if (conexao != null) {
            lblStatus.setText("Conectado ao Banco de Dados.");
            lblStatus.setForeground(Color.BLUE);
        } else {
            lblStatus.setText("Não Conectado ao MySQL.");
            lblStatus.setForeground(Color.RED);
        }
        setIcon();
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

        scAuxilio = new javax.swing.JScrollPane();
        tbAuxilio = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        lblStatus = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        botaoArredondado1 = new br.com.LeGnus_Disparador.Swing.botaoArredondado();
        txtSenha = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        tbAuxilio.setModel(new javax.swing.table.DefaultTableModel(
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
        scAuxilio.setViewportView(tbAuxilio);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LeGnu`s_Disparador - Tela de Login");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(java.awt.SystemColor.control);
        jPanel1.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(204, 204, 204))));

        lblStatus.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblStatus.setText("Status Conexao");

        jButton1.setBackground(new java.awt.Color(204, 204, 204));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/LeGnus_Disparador/util/Logo 450p.png"))); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);

        botaoArredondado1.setForeground(new java.awt.Color(0, 0, 204));
        botaoArredondado1.setText("Logar");
        botaoArredondado1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        botaoArredondado1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoArredondado1ActionPerformed(evt);
            }
        });

        txtSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSenhaActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("Senha do Mes:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(botaoArredondado1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(95, 95, 95))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblStatus)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(161, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(137, 137, 137))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(lblStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(51, 51, 51)
                .addComponent(botaoArredondado1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
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

    private void txtSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSenhaActionPerformed
        // TODO add your handling code here:]
        validarSenha();
    }//GEN-LAST:event_txtSenhaActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        GerenciarSenha();
    }//GEN-LAST:event_formWindowOpened

    private void botaoArredondado1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoArredondado1ActionPerformed
        // TODO add your handling code here:
        validarSenha();
    }//GEN-LAST:event_botaoArredondado1ActionPerformed

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
            java.util.logging.Logger.getLogger(TelaVale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaVale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaVale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaVale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaVale().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.LeGnus_Disparador.Swing.botaoArredondado botaoArredondado1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JScrollPane scAuxilio;
    private javax.swing.JTable tbAuxilio;
    private javax.swing.JTextField txtSenha;
    // End of variables declaration//GEN-END:variables
}
