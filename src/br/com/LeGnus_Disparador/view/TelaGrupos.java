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

    private void instanciarTbGrupos() {

        try {
            String sql = "select idgrupo as NºGrupo, nomeGrupo as Grupo,conjunto as Categoria_Grupo from tbgrupos";
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            tbGrupos.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    private void PesquisarGrupo() {
        try {
            String sql = "select idgrupo as NºGrupo, nomeGrupo as Grupo,conjunto as Categoria_Grupo from tbgrupos where nomeGrupo like ?";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtPesquisa.getText() + "%");
            rs = pst.executeQuery();
            tbGrupos.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    private void adicionarGrupo() {
        String sql = "insert into tbgrupos(nomeGrupo,conjunto) values(?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNomeGrupo.getText());
            pst.setString(2, txtCategoriaGrupo.getText());
            if (txtNomeGrupo.getText().isEmpty() == true) {
                JOptionPane.showMessageDialog(null, "Adicione um Nome para o Grupo.");

            } else if (txtCategoriaGrupo.getText().isEmpty() == true) {
                JOptionPane.showMessageDialog(null, "Especifique uma Categoria para o Grupo");
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
        if(tbGrupos.getModel().getValueAt(setar, 2) == null){
            txtCategoriaGrupo.setText("Nulo");
        }else{
        txtCategoriaGrupo.setText(tbGrupos.getModel().getValueAt(setar, 2).toString());
        }

        btnAdicionar.setEnabled(false);
        btnEditar.setEnabled(true);
        btnRemover.setEnabled(true);

    }

    private void editarGrupo() {
        String sql = "update tbgrupos set nomegrupo=?,conjunto=? where idgrupo=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNomeGrupo.getText());
            pst.setString(2, txtCategoriaGrupo.getText());
            pst.setString(3, txtNumeroGrupo.getText());
            if (txtNomeGrupo.getText().isEmpty() == true) {
                JOptionPane.showMessageDialog(null, "Adicione um Nome para o Grupo.");
            } else if (txtCategoriaGrupo.getText().isEmpty() == true) {
                JOptionPane.showMessageDialog(null, "Especifique uma Categoria para o Grupo");
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
                JOptionPane.showMessageDialog(null, "Grupo removido com sucesso.");
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

    public void Limpar() {
        txtCategoriaGrupo.setText(null);
        txtNumeroGrupo.setText(null);
        txtCategoriaGrupo.setText(null);
        
        btnAdicionar.setEnabled(true);
        btnEditar.setEnabled(false);
        btnRemover.setEnabled(false);
        
        instanciarTbGrupos();
       
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtNumeroGrupo = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbGrupos = new javax.swing.JTable();
        txtPesquisa = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtCategoriaGrupo = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        txtNomeGrupo = new javax.swing.JTextField();
        btnAdicionar = new br.com.LeGnus_Disparador.Swing.botaoArredondado();
        btnEditar = new br.com.LeGnus_Disparador.Swing.botaoArredondado();
        btnRemover = new br.com.LeGnus_Disparador.Swing.botaoArredondado();

        txtNumeroGrupo.setEditable(false);
        txtNumeroGrupo.setEnabled(false);
        txtNumeroGrupo.setFocusable(false);

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

        jPanel1.setBackground(java.awt.SystemColor.control);
        jPanel1.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(204, 204, 204))));

        jPanel2.setBackground(java.awt.SystemColor.control);
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Grupos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

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
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPesquisa))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 740, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(java.awt.SystemColor.control);
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Categoria_Grupo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        txtCategoriaGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCategoriaGrupoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtCategoriaGrupo, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtCategoriaGrupo)
        );

        jPanel4.setBackground(java.awt.SystemColor.control);
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nome_Grupo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        txtNomeGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeGrupoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtNomeGrupo)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtNomeGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        btnAdicionar.setForeground(new java.awt.Color(0, 153, 0));
        btnAdicionar.setText("Adicionar");
        btnAdicionar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        btnEditar.setForeground(new java.awt.Color(0, 153, 153));
        btnEditar.setText("Editar");
        btnEditar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnRemover.setForeground(new java.awt.Color(153, 0, 0));
        btnRemover.setText("Remover");
        btnRemover.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAdicionar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(16, 16, 16)
                        .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(16, 16, 16)
                        .addComponent(btnRemover, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(16, 16, 16)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void txtCategoriaGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCategoriaGrupoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCategoriaGrupoActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        TelaPrincipal principal = new TelaPrincipal();
        principal.setVisible(true);
    }//GEN-LAST:event_formWindowClosed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        Limpar();
    }//GEN-LAST:event_formWindowOpened

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

    private void txtNomeGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeGrupoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeGrupoActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        // TODO add your handling code here:
        adicionarGrupo();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        editarGrupo();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        // TODO add your handling code here:
        removerGrupo();
    }//GEN-LAST:event_btnRemoverActionPerformed

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
    private br.com.LeGnus_Disparador.Swing.botaoArredondado btnAdicionar;
    private br.com.LeGnus_Disparador.Swing.botaoArredondado btnEditar;
    private br.com.LeGnus_Disparador.Swing.botaoArredondado btnRemover;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbGrupos;
    private javax.swing.JTextField txtCategoriaGrupo;
    private javax.swing.JTextField txtNomeGrupo;
    private javax.swing.JTextField txtNumeroGrupo;
    private javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables
}
