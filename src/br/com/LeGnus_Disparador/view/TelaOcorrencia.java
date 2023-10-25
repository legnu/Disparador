/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.LeGnus_Disparador.view;

import br.com.LeGnus_Disparador.model.ModuloConexao;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Ad3ln0r
 */
public class TelaOcorrencia extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaOcorrencia
     */
    public TelaOcorrencia() {
        initComponents();
        conexao = ModuloConexao.conector();
        setIcon();
    }

    private void instanciarTbOcorrencia() {
        String sql = "select idErro as ID, nomeInserido as Nome_Inserido, categoria as Tipo from tbErro";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            tbOcorrencia.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    private void remover(String nome, String tipo) {
        try {
            String confirma = JOptionPane.showInputDialog(null, "Você deseja ?\n 1)Tirar este " + tipo + " da tela de ocorrencias.\n 2)Excluir " + tipo + " do banco de dados.", "Atenção", JOptionPane.YES_NO_OPTION);
            String sql;
            
            if (confirma.equals("1") == true) {

                sql = "delete from tbErro where nomeInserido=? and categoria=?";
                pst = conexao.prepareStatement(sql);
                pst.setString(1, nome);
                pst.setString(2, tipo);
                pst.executeUpdate();

                instanciarTbOcorrencia();

                JOptionPane.showMessageDialog(null, tipo + " removido da tela de ocorrencias com sucesso.");

            } else if (confirma.equals("2") == true) {
                
                if (tipo.equals("Cliente")) {
                    sql = "delete from tbclientes where nomecliente=?";
                } else {
                    sql = "delete from tbgrupos where nomeGrupo=?";
                }

                pst = conexao.prepareStatement(sql);
                pst.setString(1, nome);
                pst.executeUpdate();

                sql = "delete from tbErro where nomeInserido=? and categoria=?";
                pst = conexao.prepareStatement(sql);
                pst.setString(1, nome);
                pst.setString(2, tipo);
                pst.executeUpdate();

                instanciarTbOcorrencia();

                JOptionPane.showMessageDialog(null, tipo + " removido do banco de dados com sucesso.");

            }else{
                JOptionPane.showMessageDialog(null, "Opção desconhecida!\nEscolha a alternativa 1 ou a alternativa 2.");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void removerTudo() {
        try {
            String sql;
            String tipo;
            String nome;
            for (int i = 0; i < tbOcorrencia.getRowCount(); i++) {
                tipo = tbOcorrencia.getModel().getValueAt(i, 2).toString();
                nome = tbOcorrencia.getModel().getValueAt(i, 1).toString();
                
                if (tipo.equals("Cliente")) {
                    sql = "delete from tbclientes where nomecliente=?";
                } else {
                    sql = "delete from tbgrupos where nomeGrupo=?";
                }

                pst = conexao.prepareStatement(sql);
                pst.setString(1, nome);
                pst.executeUpdate();

                sql = "delete from tbErro where nomeInserido=? and categoria=?";
                pst = conexao.prepareStatement(sql);
                pst.setString(1, nome);
                pst.setString(2, tipo);
                pst.executeUpdate();
            }
            
            instanciarTbOcorrencia();
            
            JOptionPane.showMessageDialog(null, "Removidos do banco de dados com sucesso.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void limparHistorico() {
        try {
            String sql = "TRUNCATE TABLE tbErro";
            pst = conexao.prepareStatement(sql);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Ocorrencias limpas com sucesso.");
            instanciarTbOcorrencia();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }

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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbOcorrencia = new javax.swing.JTable();
        botaoArredondado1 = new br.com.LeGnus_Disparador.Swing.botaoArredondado();
        botaoArredondado2 = new br.com.LeGnus_Disparador.Swing.botaoArredondado();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Legnu's_Disparador - TelaOcorrencias");
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
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jScrollPane1.setBackground(java.awt.SystemColor.control);

        tbOcorrencia = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tbOcorrencia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbOcorrencia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbOcorrenciaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbOcorrencia);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
        );

        botaoArredondado1.setText("Apagar Historico");
        botaoArredondado1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        botaoArredondado1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoArredondado1ActionPerformed(evt);
            }
        });

        botaoArredondado2.setForeground(new java.awt.Color(153, 0, 0));
        botaoArredondado2.setText("Apagar Todos do Banco");
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
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(botaoArredondado2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(16, 16, 16)
                        .addComponent(botaoArredondado1, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(botaoArredondado1, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                    .addComponent(botaoArredondado2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void tbOcorrenciaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbOcorrenciaMouseClicked
        // TODO add your handling code here:
        remover(tbOcorrencia.getModel().getValueAt(tbOcorrencia.getSelectedRow(), 1).toString(),
                tbOcorrencia.getModel().getValueAt(tbOcorrencia.getSelectedRow(), 2).toString());
    }//GEN-LAST:event_tbOcorrenciaMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        instanciarTbOcorrencia();
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        TelaPrincipal principal = new TelaPrincipal();
        principal.setVisible(true);
    }//GEN-LAST:event_formWindowClosed

    private void botaoArredondado1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoArredondado1ActionPerformed
        // TODO add your handling code here:
        limparHistorico();
    }//GEN-LAST:event_botaoArredondado1ActionPerformed

    private void botaoArredondado2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoArredondado2ActionPerformed
        // TODO add your handling code here:
        removerTudo();
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
            java.util.logging.Logger.getLogger(TelaOcorrencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaOcorrencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaOcorrencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaOcorrencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaOcorrencia().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.LeGnus_Disparador.Swing.botaoArredondado botaoArredondado1;
    private br.com.LeGnus_Disparador.Swing.botaoArredondado botaoArredondado2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbOcorrencia;
    // End of variables declaration//GEN-END:variables
}
