package oldFrame;

import com.mysql.cj.xdevapi.Result;
import factory.Conections;
import utils.PoiExport;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

public class SearchFrame extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    Connection con;
    Connection con2;
    PreparedStatement pstm;
    PreparedStatement pstm2;
    ResultSet rs2;
    ResultSet rs;
    DefaultTableModel df;
    DefaultTableModel df2;
    Timer time;

    public SearchFrame() {
        initComponents();


        jTable1.getTableHeader().setEnabled(false);
        allData();
        getData();
        
        ActionListener task = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // System.out.println("estado "+stateSave());
                if (stateSave() == 1) {
                    
                    getData();
                }
            }
        };
        time = new Timer(1000, task);
        time.setRepeats(true);
        time.start();

    }

    public void getActionData() {
        ActionListener task = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                allData();
            }
        };
        time = new Timer(100, task);
        time.setRepeats(true);
        time.start();

    }

    public void getData() {

        jTable1.setModel(new DefaultTableModel(null, new String[]{"CRACHA", "TAG", "EMPRESA",
            "VISI/PRES", "CPF", "PLACA", "AGREGADO", "P/ACESSO", "L/ACESSO",
            "T/ACESSO", "LIBERADOR", "MOTIVO", " DATA", " HORA"}));
        df = (DefaultTableModel) jTable1.getModel();
        try {
            con = Conections.createConnections();
            pstm = con.prepareStatement("select * from controller");
            rs = pstm.executeQuery();
            while (rs.next()) {

                DateFormat dfOut = new SimpleDateFormat("dd-MM-yyyy");
                DateFormat dfInp = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat hora = new SimpleDateFormat("HH:mm:ss");
                DateFormat horaEn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                String dateFormats = rs.getString("datas");

                Date dates = dfInp.parse(dateFormats);
                String newDate = dfOut.format(dates);

                Date dateH = horaEn.parse(dateFormats);
                String horario = hora.format(dateH);

                Object[] line = {rs.getString("badge"), rs.getString("tag"),
                    rs.getString("company"), rs.getString("visitor"), rs.getString("cpf"),
                    rs.getString("plateCar"), rs.getString("agregate"), rs.getString("localAcess"),
                    rs.getString("typeAcess"), rs.getString("concierge"), rs.getString("liberator"),
                    rs.getString("reason"), newDate, horario};

                df.addRow(line);
            }
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(SearchFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void search(String type, String data) {

        jTable2.setModel(new DefaultTableModel(null, new String[]{"CRACHA", "TAG", "EMPRESA",
            "VISI/PRES", "CPF", "PLACA", "AGREGADO", "P/ACESSO", "L/ACESSO",
            "T/ACESSO", "LIBERADOR", "MOTIVO", " DATA"}));
        df2 = (DefaultTableModel) jTable2.getModel();

        jTable2.setAutoCreateRowSorter(true);
        df2.setRowCount(0);

        switch (type) {
            case "CRACHA":
                type = "badge";
                break;
            case "TAG":
                type = "tag";
                break;
            case "EMPRESA":
                type = "company";
                break;
            case "VISI/PRES":
                type = "visitor";
                break;
            case "CPF":
                type = "cpf";
                break;
            case "PLACA":
                type = "plateCar";
                break;
            case "AGREGADO":
                type = "agregate";
                break;
            case "P/ACESSO":
                type = "localAcess";
                break;
            case "L/ACESSO":
                type = "typeAcess";
                break;
            case "T/ACESSO":
                type = "concierge";
                break;

            case "LIBERADOR":
                type = "liberator";
                break;

            case "MOTIVO":
                type = "reason";
                break;
            case "DATA":
                type = "datas";
                break;
            default:
                throw new AssertionError();
        }

        try {
            con2 = Conections.createConnections();
            pstm2 = con2.prepareStatement("select * from controller where " + type + " like '" + data + "%'");
            rs2 = pstm2.executeQuery();
            while (rs2.next()) {
                Object[] line = {rs2.getString("badge"), rs2.getString("tag"),
                    rs2.getString("company"), rs2.getString("visitor"), rs2.getString("cpf"),
                    rs2.getString("plateCar"), rs2.getString("agregate"), rs2.getString("localAcess"),
                    rs2.getString("typeAcess"), rs2.getString("concierge"), rs2.getString("liberator"),
                    rs2.getString("reason"), rs2.getString("datas")};

                df2.addRow(line);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SearchFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void allData() {

        jTable2.setModel(new DefaultTableModel(null, new String[]{"CRACHA", "TAG", "EMPRESA",
            "VISI/PRES", "CPF", "PLACA", "AGREGADO", "P/ACESSO", "L/ACESSO",
            "T/ACESSO", "LIBERADOR", "MOTIVO", " DATA", "HORARIO"}));
        DefaultTableModel df3 = (DefaultTableModel) jTable2.getModel();

        jTable2.setAutoCreateRowSorter(true);
        df3.setRowCount(0);

        try {
            con2 = Conections.createConnections();
            pstm2 = con2.prepareStatement("select * from controller");
            rs2 = pstm2.executeQuery();
            while (rs2.next()) {

                DateFormat dfOut = new SimpleDateFormat("dd-MM-yyyy");
                DateFormat dfInp = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat hora = new SimpleDateFormat("HH:mm:ss");
                DateFormat horaEn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                String dateFormats = rs2.getString("datas");

                Date dates = dfInp.parse(dateFormats);
                String newDate = dfOut.format(dates);

                Date dateH = horaEn.parse(dateFormats);
                String horario = hora.format(dateH);

                Object[] line = {rs2.getString("badge"), rs2.getString("tag"),
                    rs2.getString("company"), rs2.getString("visitor"), rs2.getString("cpf"),
                    rs2.getString("plateCar"), rs2.getString("agregate"), rs2.getString("localAcess"),
                    rs2.getString("typeAcess"), rs2.getString("concierge"), rs2.getString("liberator"),
                    rs2.getString("reason"), newDate, horario};

                df3.addRow(line);
            }
        } catch (SQLException | ParseException ex) {
            System.out.println("erro " + ex);
        }
    }

    public int stateSave() {
        int state = 0;
        try {
            con = Conections.createConnections();
            pstm = con.prepareStatement("select * from states");
            rs = pstm.executeQuery();
            while (rs.next()) {
                state = rs.getInt("statesSave");
            }
        } catch (SQLException e) {
            System.out.println("erro get states " + e);
        }

        return state;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CRACHA", "TAG", "EMPRESA", "VISI/PRES", "CPF", "PLACA", "AGREGADO", "P/ACESSO", "L/ACESSO", "T/ACESSO", "LIBERADOR", "MOTIVO", "DATA" }));
        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jComboBox1MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(350, 350, 350))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setEnabled(false);
        jTable1.setFocusable(false);
        jTable1.setRequestFocusEnabled(false);
        jTable1.setRowSelectionAllowed(false);
        jTable1.setVerifyInputWhenFocusTarget(false);
        jScrollPane1.setViewportView(jTable1);

        jButton2.setText("SAIR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(14, 14, 14))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jButton2)
                .addGap(0, 7, Short.MAX_VALUE))
        );

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TEMPO REAL");

        jButton1.setText("EXPORTAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1224, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1224, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)))
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

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
        search(jComboBox1.getSelectedItem().toString(), jTextField1.getText());
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        PoiExport p = new PoiExport();
        p.exportar(jTable1);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
        time.stop();
    }//GEN-LAST:event_formMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        MenuJFrame m = new MenuJFrame();
        m.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MousePressed
       allData();
    }//GEN-LAST:event_jComboBox1MousePressed

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
            java.util.logging.Logger.getLogger(SearchFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SearchFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SearchFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SearchFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SearchFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    public static javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
