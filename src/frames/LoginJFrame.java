package frames;

import factory.Conections;
import static factory.Conections.load;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import style.JOPtionCSwing;

public class LoginJFrame extends javax.swing.JFrame {

    Connection con;
    PreparedStatement pstm;
    ResultSet rs;
    FrameNew f;
    JOPtionCSwing p;

    public LoginJFrame() {

        initComponents();
        load();

    }

    public boolean get(String name, String pass) {
        boolean valid = false;
        try {
            con = Conections.createConnections();
            pstm = con.prepareStatement("select * from login");
            
            rs = pstm.executeQuery();
            while (rs.next()) {
                if (rs.getString("name").equals(name) && rs.getString("pass").equals(pass)) {

                    valid = true;
                }

            }
        } catch (SQLException e) {
            System.out.println("erro login " + e);
        }

        return valid;
    }
    
    public Object[] getPtr(String name, String pass) {
        Object ptr[] = new Object[2];
        try {
            con = Conections.createConnections();
            pstm = con.prepareStatement("select * from login");
            rs = pstm.executeQuery();
            while (rs.next()) {
                if (rs.getString("name").equals(name) && rs.getString("pass").equals(pass)) {

                   ptr[0] =  rs.getInt("id");
                   ptr[1] =  rs.getString("ptr");
                  
                }

            }
        } catch (SQLException e) {
            System.out.println("erro login " + e);
        }

        return ptr;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        kGradientPanel1 = new keeptoo.KGradientPanel();
        jPanel2 = new javax.swing.JPanel();
        jTextFieldCustomName = new style.JTextFieldCustom();
        buttonCustom1 = new style.ButtonCustom();
        jPasswordFieldCustom1 = new style.JPasswordFieldCustom();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        kGradientPanel1.setkStartColor(new java.awt.Color(51, 153, 255));

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(jTextFieldCustomName, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, 159, 32));

        buttonCustom1.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom1.setText("LOGAR");
        buttonCustom1.setColor(new java.awt.Color(30, 136, 56));
        buttonCustom1.setColorOver(new java.awt.Color(50, 166, 96));
        buttonCustom1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom1ActionPerformed(evt);
            }
        });
        jPanel2.add(buttonCustom1, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 260, 120, 37));

        jPasswordFieldCustom1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jPanel2.add(jPasswordFieldCustom1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 189, 160, 30));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("LOGIN_______________");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, 150, -1));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("SENHA_______________");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 170, 150, -1));

        jPanel1.setOpaque(false);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 110, 110));

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
                .addContainerGap())
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCustom1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom1ActionPerformed
        char[] password = jPasswordFieldCustom1.getPassword();
        String pass = new String(password);
        
        boolean valid = get(jTextFieldCustomName.getText(),pass);
        

        if (!jTextFieldCustomName.getText().equals("") && !pass.equals("")) {
            if (valid) {
                Object[] ptr = getPtr(jTextFieldCustomName.getText(), pass);
                
                p = new JOPtionCSwing(this, true, "logado".toUpperCase());
                p.setVisible(true);
                f = new FrameNew(jTextFieldCustomName.getText(),ptr);
                dispose();
                f.setVisible(true);
            } else {

                p = new JOPtionCSwing(this, true, "Login ou Senha errado".toUpperCase());
                p.setVisible(true);
            }
        } else {

            p = new JOPtionCSwing(this, true, "Preencha todos os campos".toUpperCase());
            p.setVisible(true);
        }
    }//GEN-LAST:event_buttonCustom1ActionPerformed

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
            java.util.logging.Logger.getLogger(LoginJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private style.ButtonCustom buttonCustom1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private style.JPasswordFieldCustom jPasswordFieldCustom1;
    private style.JTextFieldCustom jTextFieldCustomName;
    private keeptoo.KGradientPanel kGradientPanel1;
    // End of variables declaration//GEN-END:variables
}
