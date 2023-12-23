package frames;

import oldFrame.SearchFrame;
import factory.Conections;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import raven.scroll.win11.ScrollBarWin11UI;
import style.JOPtionCSwing;
import style.Table;


public class FrameNew extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    static String nome;
    static Object[] ptr = new Object[2];
    Connection con;
    PreparedStatement pstm;
    ResultSet rs;
    DefaultTableModel df;
    DefaultTableModel df2;
    Timer time;
    JOPtionCSwing paneCustom;
    String newDateEntra, crachaRe;
    int rowsTableAll = 0, rowsNew = 0;

    public FrameNew(String nome, Object[] ptrs) {
        initComponents();
        jLabelNome.setText(nome.toUpperCase());
        jLabelPtr.setText(((String) ptrs[1]).toUpperCase());
        ptr[0] = ptrs[0];

        delStateChanged();
        delStateChangedTag();

        saveStateChanged(0);

        allData(table2, df2, "1");
        allData(table1, df, "0");

        ActionListener task = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int stateSa = stateSave();
                int stateTag = stateSaveTag();

                if (stateSa == 1) {
                    allData(table1, df, "0");
                    allData(table2, df, "1");
                }
                if (stateTag == 1) {
                    String dataTag = getTagApi(); //get barcode with database

                    delTag(); //delete tag 
                    delStateChangedTag(); //delete tag state

                    rowsTableAll = downVisitorTable(); //state table before

                    downVisitor(dataTag); // delete data with information de barcode
                    allData(table1, df, "0"); // get data delete in table
                    allData(table2, df, "1"); // get data get data delete in table
                    rowsNew = downVisitorTable(); // state after of delete
                    if (rowsTableAll == rowsNew) {
                        jTextFieldCustomCodigoDeBarra.setText(dataTag); //infla component with information
                        searchForBar(jTextFieldCustomCodigoDeBarra.getText()); // insert in component data
                        paneCustom = new JOPtionCSwing(null, true, "Dados auto preenchidos ".toUpperCase());
                        paneCustom.setVisible(true);
                    } else {

                        paneCustom = new JOPtionCSwing(null, true, "Baixa realizada".toUpperCase());
                        paneCustom.setVisible(true);
                    }
                }
            }
        };

        time = new Timer(500, task);
        time.setRepeats(true);
        time.start();

    }

    public void allData(Table table1, DefaultTableModel df3, String mode) {

        if (mode.equals("1")) {
            table1.setModel(new DefaultTableModel(null, new String[]{"CRACHA", "T/CRACHA", "TAG", "FIXO/SPOT", "EMPRESA",
                "VISI/PRES", "RG", "CPF", "CELULAR", "PLACA", "PLACA-CT", "L/ACESSO",
                "LIBERADOR", "MOTIVO", " D/ENT", "H/ENTRA"}));
        }
        if (mode.equals("0")) {
            table1.setModel(new DefaultTableModel(null, new String[]{"CRACHA", "T/CRACHA", "TAG", "FIXO/SPOT", "EMPRESA",
                "VISI/PRES", "RG", "CPF", "CELULAR", "PLACA", "PLACA-CT", "L/ACESSO",
                "LIBERADOR", "MOTIVO", " D/ENT", "H/ENTRA", " D/SAI", "H/SAI"}));
        }

        df3 = (DefaultTableModel) table1.getModel();

        table1.setAutoCreateRowSorter(true);
        df3.setRowCount(0);

        try {
            con = Conections.createConnections();
            pstm = con.prepareStatement("select * from controller");
            rs = pstm.executeQuery();
            while (rs.next()) {

                DateFormat dfOut = new SimpleDateFormat("dd-MM-yyyy");
                DateFormat dfInp = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat hora = new SimpleDateFormat("HH:mm:ss");
                DateFormat horaEn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                String dateFormatsEntra = rs.getString("dataIn");
                String dateFormatsSaida = rs.getString("dataOut");

                String newDateEntra = "", horarioEntra = "", newDateSai = "", horarioSai = "";

                if (dateFormatsEntra != null) {
                    Date datesEntra = dfInp.parse(dateFormatsEntra);
                    newDateEntra = dfOut.format(datesEntra);

                    Date dateHEntra = horaEn.parse(dateFormatsEntra);
                    horarioEntra = hora.format(dateHEntra);

                } else {
                    newDateEntra = "s/n";
                    horarioEntra = "s/n";
                }

                if (dateFormatsSaida != null) {
                    Date datesSai = dfInp.parse(dateFormatsSaida);
                    newDateSai = dfOut.format(datesSai);

                    Date dateHSai = horaEn.parse(dateFormatsSaida);
                    horarioSai = hora.format(dateHSai);
                } else {
                    newDateSai = "s/n";
                    horarioSai = "s/n";
                }
                if (mode.equals("1") && newDateSai.equals("s/n") && horarioSai.equals("s/n") && rs.getInt("id_fk") == (int) ptr[0]) {
                    Object[] line = {rs.getString("badge"), rs.getString("typeBadge"), rs.getString("tag"), rs.getString("fixoOrSpot"),
                        rs.getString("company"), rs.getString("visitor"), rs.getString("rg"), rs.getString("cpf"),
                        rs.getString("phone"), rs.getString("plateCar"), rs.getString("plateCart"), rs.getString("localAcess"),
                        rs.getString("liberator"),
                        rs.getString("reason"), newDateEntra, horarioEntra};

                    df3.addRow(line);
                }
                if (mode.equals("0") && !newDateSai.equals("s/n") && !horarioSai.equals("s/n")) {
                    Object[] line = {rs.getString("badge"), rs.getString("typeBadge"), rs.getString("tag"), rs.getString("fixoOrSpot"),
                        rs.getString("company"), rs.getString("visitor"), rs.getString("rg"), rs.getString("cpf"),
                        rs.getString("phone"), rs.getString("plateCar"), rs.getString("plateCart"), rs.getString("localAcess"),
                        rs.getString("liberator"),
                        rs.getString("reason"), newDateEntra, horarioEntra, newDateSai, horarioSai};

                    df3.addRow(line);
                }
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

            boolean next = rs.next();
            if (next) {
                state = rs.getInt("statesSave");
                System.out.println("saida " + state);
            }

        } catch (SQLException e) {
            System.out.println("erro get states " + e);
        }

        return state;
    }

    public String getTagApi() {
        String tag = "";
        try {
            con = Conections.createConnections();
            pstm = con.prepareStatement("select * from tags");
            rs = pstm.executeQuery();

            boolean next = rs.next();
            if (next) {
                tag = rs.getString("tag");

            }

        } catch (SQLException e) {
            System.out.println("erro get tags" + e);
        }

        return tag;
    }

    public int stateSaveTag() {
        int state = 0;
        try {
            con = Conections.createConnections();
            pstm = con.prepareStatement("select * from tagstate");
            rs = pstm.executeQuery();

            boolean next = rs.next();
            if (next) {
                state = rs.getInt("state");

            }

        } catch (SQLException e) {
            System.out.println("erro get states " + e);
        }

        return state;
    }

    public void downVisitor(String barCode) {

        delStateChanged();

        String dataStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        String updateSql = "update controller set dataOut = ? WHERE barCode = '" + barCode + "'";

        try {
            TimeZone.setDefault(TimeZone.getTimeZone("GMT-3"));
            con = Conections.createConnections();
            pstm = con.prepareStatement(updateSql);

            pstm.setString(1, dataStamp);

            pstm.executeUpdate();

            saveStateChanged(1);

        } catch (SQLException e) {
            System.err.println("erro ao criar " + e);
        }

        delStateChanged();
        saveStateChanged(0);

//        paneCustom = new JOPtionCSwing(this, true, "Executada saída ".toUpperCase());
//        paneCustom.setVisible(true);
    }

    public int downVisitorTable() {
        DefaultTableModel model = (DefaultTableModel) table2.getModel();
        return model.getRowCount();
    }

    public void searchForBar(String barCode) {

        try {
            con = Conections.createConnections();
            pstm = con.prepareStatement("select * from controller where barCode = '" + barCode + "' ");
            rs = pstm.executeQuery();
            while (rs.next()) {

                Object[] line = {rs.getString("badge"), rs.getString("typeBadge"), rs.getString("tag"), rs.getString("fixoOrSpot"),
                    rs.getString("company"), rs.getString("visitor"), rs.getString("rg"), rs.getString("cpf"),
                    rs.getString("phone"), rs.getString("plateCar"), rs.getString("plateCart"), rs.getString("localAcess"),
                    rs.getString("liberator"),
                    rs.getString("reason")};

                jTextFieldCustomCracha.setText((String) line[0]);
                jTextFieldCustomTCracha.setText((String) line[1]);
                jTextFieldCustomTag.setText((String) line[2]);
                comboboxCustomFixoOrSpot.setSelectedItem((String) line[3]);
                jTextFieldCustomEmpresa.setText((String) line[4]);
                jTextFieldCustomVisiPresta.setText((String) line[5]);
                jTextFieldCustomRg.setText((String) line[6]);
                jTextFieldCustomCpf.setText((String) line[7]);
                jTextFieldCustomCelular.setText((String) line[8]);
                jTextFieldCustomPlaca.setText((String) line[9]);
                jTextFieldCustomPlateCt.setText((String) line[10]);
                comboboxCustomLAcesso.setSelectedItem((String) line[11]);
                jTextFieldCustomLiberador.setText((String) line[12]);
                jTextFieldCustomMotivo.setText((String) line[13]);

            }
        } catch (SQLException ex) {
            Logger.getLogger(SearchFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void search(String type, String data) {

        table1.setModel(new DefaultTableModel(null, new String[]{"CRACHA", "T/CRACHA", "TAG", "FIXO/SPOT", "EMPRESA",
            "VISI/PRES", "RG", "CPF", "CELULAR", "PLACA", "PLACA-CT", "L/ACESSO",
            "LIBERADOR", "MOTIVO", " D/ENT", "H/ENTRA", " D/SAI", "H/SAI"}));
        df = (DefaultTableModel) table1.getModel();

        table1.setAutoCreateRowSorter(true);
        df.setRowCount(0);

        switch (type) {
            case "CRACHA":
                type = "badge";
                break;
            case "T/CRACHA":
                type = "typeBadge";
                break;
            case "TAG":
                type = "tag";
                break;
            case "FIXO/SPOT":
                type = "fixoOrSpot";
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
            case "RG":
                type = "rg";
                break;
            case "PLACA":
                type = "plateCar";
                break;
            case "L/ACESSO":
                type = "typeAcess";
                break;
            case "LIBERADOR":
                type = "liberator";
                break;
            case "MOTIVO":
                type = "reason";
                break;
            case "DATA":
                type = "dataIn";
                break;
            default:
                throw new AssertionError();
        }

        DateFormat dfOut = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat dfInp = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat hora = new SimpleDateFormat("HH:mm:ss");
        DateFormat horaEn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (jTextFieldCustom1.getText().length() > 0) {
            try {

                con = Conections.createConnections();
                pstm = con.prepareStatement("select * from controller where " + type + " like '" + data + "%' ");
                rs = pstm.executeQuery();
                while (rs.next()) {
                    String dateFormatsEntra = rs.getString("dataIn");
                    String dateFormatsSaida = rs.getString("dataOut");

                    Date datesEntra = dfInp.parse(dateFormatsEntra);
                    String newDateEntras = dfOut.format(datesEntra);
                    Date dateHEntra = horaEn.parse(dateFormatsEntra);
                    String horarioEntra = hora.format(dateHEntra);

                    Date datesSaid = dfInp.parse(dateFormatsSaida);
                    String newDateSaid = dfOut.format(datesSaid);
                    Date dateHSaid = horaEn.parse(dateFormatsSaida);
                    String horarioSaid = hora.format(dateHSaid);

                    Object[] line = {rs.getString("badge"), rs.getString("typeBadge"), rs.getString("tag"), rs.getString("fixoOrSpot"),
                        rs.getString("company"), rs.getString("visitor"), rs.getString("rg"), rs.getString("cpf"),
                        rs.getString("phone"), rs.getString("plateCar"), rs.getString("plateCart"), rs.getString("localAcess"),
                        rs.getString("liberator"),
                        rs.getString("reason"), newDateEntras, horarioEntra, newDateSaid, horarioSaid};

                    df.addRow(line);
                }
            } catch (SQLException ex) {
                Logger.getLogger(SearchFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(FrameNew.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            allData(table1, df, "0");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser2 = new com.raven.datechooser.DateChooser();
        dateChooser3 = new com.raven.datechooser.DateChooser();
        tabbedPaneCustom1 = new style.TabbedPaneCustom();
        jPanel1 = new javax.swing.JPanel();
        kGradientPanel2 = new keeptoo.KGradientPanel();
        buttonCustom1 = new style.ButtonCustom();
        jPanel3 = new javax.swing.JPanel();
        jTextFieldCustomCracha = new style.JTextFieldCustom();
        jTextFieldCustomTag = new style.JTextFieldCustom();
        jTextFieldCustomEmpresa = new style.JTextFieldCustom();
        jTextFieldCustomVisiPresta = new style.JTextFieldCustom();
        jTextFieldCustomCpf = new style.JTextFieldCustom();
        jTextFieldCustomPlaca = new style.JTextFieldCustom();
        comboboxCustomLAcesso = new style.comboboxCustom();
        comboboxCustomFixoOrSpot = new style.comboboxCustom();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldCustomLiberador = new style.JTextFieldCustom();
        jLabel20 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jTextFieldCustomMotivo = new style.JTextFieldCustom();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldCustomRg = new style.JTextFieldCustom();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldCustomCelular = new style.JTextFieldCustom();
        jLabel17 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jTextFieldCustomTCracha = new style.JTextFieldCustom();
        jTextFieldCustomPlateCt = new style.JTextFieldCustom();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldCustomCodigoDeBarra = new style.JTextFieldCustom();
        jLabel6 = new javax.swing.JLabel();
        dateChooser1 = new com.raven.datechooser.DateChooser();
        jLabelNome = new javax.swing.JLabel();
        jLabelPtr = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        scrollPaneWin112 = new scroll.ScrollPaneWin11();
        table1 = new style.Table();
        comboboxCustom1 = new style.comboboxCustom();
        jLabel14 = new javax.swing.JLabel();
        scrollPaneWin111 = new scroll.ScrollPaneWin11();
        table2 = new style.Table();
        buttonCustom3 = new style.ButtonCustom();
        jTextFieldCustom1 = new style.JTextFieldCustom();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldCustomDateInit = new style.JTextFieldCustom();
        jTextFieldCustomDateEnd = new style.JTextFieldCustom();
        buttonCustom4 = new style.ButtonCustom();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        kGradientPanel4 = new keeptoo.KGradientPanel();
        buttonCustom2 = new style.ButtonCustom();

        dateChooser2.setTextRefernce(jTextFieldCustomDateInit);

        dateChooser3.setTextRefernce(jTextFieldCustomDateEnd);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1366, 768));

        tabbedPaneCustom1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tabbedPaneCustom1.setNextFocusableComponent(jTextFieldCustomCracha);
        tabbedPaneCustom1.setSelectedColor(new java.awt.Color(34, 211, 54));
        tabbedPaneCustom1.setUnselectedColor(new java.awt.Color(34, 211, 54));

        kGradientPanel2.setkEndColor(new java.awt.Color(255, 255, 255));
        kGradientPanel2.setkStartColor(new java.awt.Color(204, 204, 204));

        buttonCustom1.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom1.setText("SALVAR");
        buttonCustom1.setColor(new java.awt.Color(30, 136, 56));
        buttonCustom1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        buttonCustom1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom1ActionPerformed(evt);
            }
        });

        jTextFieldCustomCracha.setFont(new java.awt.Font("segoe", 0, 11)); // NOI18N

        jTextFieldCustomTag.setFont(new java.awt.Font("segoe", 0, 11)); // NOI18N

        jTextFieldCustomEmpresa.setFont(new java.awt.Font("segoe", 0, 11)); // NOI18N

        jTextFieldCustomVisiPresta.setFont(new java.awt.Font("segoe", 0, 11)); // NOI18N

        jTextFieldCustomCpf.setFont(new java.awt.Font("segoe", 0, 11)); // NOI18N

        jTextFieldCustomPlaca.setFont(new java.awt.Font("segoe", 0, 11)); // NOI18N

        comboboxCustomLAcesso.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Portaria P2", "Portaria P4", "Portaria P6", "Portaria P10", "Portaria P12", "Área Administrativa", "Restaurante" }));
        comboboxCustomLAcesso.setSelectedIndex(-1);
        comboboxCustomLAcesso.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        comboboxCustomLAcesso.setLabeText("LOCAL DE ACESSO");
        comboboxCustomLAcesso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboboxCustomLAcessoActionPerformed(evt);
            }
        });

        comboboxCustomFixoOrSpot.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "FIXO", "SPOT" }));
        comboboxCustomFixoOrSpot.setSelectedIndex(-1);
        comboboxCustomFixoOrSpot.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        comboboxCustomFixoOrSpot.setLabeText("FIXO OU SPOT");
        comboboxCustomFixoOrSpot.setNextFocusableComponent(comboboxCustomLAcesso);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel1.setText("N CRACHÁ");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel2.setText("TAG");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel3.setText("EMPRESA");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel4.setText("VISITANTE / PRESTADOR");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel5.setText("CPF");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel9.setText("PLACA DE VEÍCULO");

        jTextFieldCustomLiberador.setFont(new java.awt.Font("segoe", 0, 11)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel20.setText("LIBERADOR");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel19.setText("MOTIVO");

        jTextFieldCustomMotivo.setFont(new java.awt.Font("segoe", 0, 11)); // NOI18N
        jTextFieldCustomMotivo.setNextFocusableComponent(buttonCustom1);
        jTextFieldCustomMotivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCustomMotivoActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel11.setText("RG");

        jTextFieldCustomRg.setFont(new java.awt.Font("segoe", 0, 11)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel12.setText("CELULAR");

        jTextFieldCustomCelular.setFont(new java.awt.Font("segoe", 0, 11)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel17.setText("PLACA CARRETA");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel21.setText("TIPO CRACHA");

        jTextFieldCustomTCracha.setFont(new java.awt.Font("segoe", 0, 11)); // NOI18N
        jTextFieldCustomTCracha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldCustomTCrachaKeyReleased(evt);
            }
        });

        jTextFieldCustomPlateCt.setFont(new java.awt.Font("segoe", 0, 11)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel8.setText("CÓDIGO DE BARRAS");

        jTextFieldCustomCodigoDeBarra.setFont(new java.awt.Font("segoe", 0, 11)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(comboboxCustomFixoOrSpot, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                        .addComponent(comboboxCustomLAcesso, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldCustomPlaca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldCustomCpf, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldCustomVisiPresta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldCustomEmpresa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldCustomTag, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldCustomCracha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldCustomRg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldCustomCelular, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldCustomPlateCt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldCustomTCracha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(62, 62, 62))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldCustomMotivo, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                            .addComponent(jTextFieldCustomLiberador, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldCustomCodigoDeBarra, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(27, 27, 27))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldCustomCracha, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCustomTCracha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCustomTag, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCustomVisiPresta, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCustomRg, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCustomCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCustomCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCustomEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldCustomPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCustomPlateCt, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(comboboxCustomFixoOrSpot, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                    .addComponent(comboboxCustomLAcesso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldCustomLiberador, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCustomMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextFieldCustomCodigoDeBarra, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabelNome.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelNome.setForeground(new java.awt.Color(51, 51, 51));
        jLabelNome.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelNome.setText("NOME");

        jLabelPtr.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelPtr.setForeground(new java.awt.Color(51, 51, 51));
        jLabelPtr.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPtr.setText("PORTARIA");

        javax.swing.GroupLayout kGradientPanel2Layout = new javax.swing.GroupLayout(kGradientPanel2);
        kGradientPanel2.setLayout(kGradientPanel2Layout);
        kGradientPanel2Layout.setHorizontalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 242, Short.MAX_VALUE)
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelPtr, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelNome, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateChooser1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(33, 33, 33))
        );
        kGradientPanel2Layout.setVerticalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabelNome, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelPtr, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addGap(186, 186, 186)
                        .addComponent(dateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 124, Short.MAX_VALUE)
                        .addComponent(buttonCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addGap(400, 400, 400)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabbedPaneCustom1.addTab("CADASTRAR", jPanel1);

        kGradientPanel1.setkEndColor(new java.awt.Color(204, 204, 204));
        kGradientPanel1.setkStartColor(new java.awt.Color(255, 255, 255));

        scrollPaneWin112.setBorder(null);
        scrollPaneWin112.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N

        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        table1.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        scrollPaneWin112.setViewportView(table1);

        comboboxCustom1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CRACHA", "T/CRACHA", "TAG", "FIXO/SPOT", "EMPRESA", "VISI/PRES", "CPF", "RG", "PLACA", "L/ACESSO", "LIBERADOR", "MOTIVO", "DATA" }));
        comboboxCustom1.setSelectedIndex(-1);
        comboboxCustom1.setLabeText("BUSCAR");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setText("ATIVOS");

        scrollPaneWin111.setBorder(null);
        scrollPaneWin111.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N

        table2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        table2.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        table2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table2MouseClicked(evt);
            }
        });
        scrollPaneWin111.setViewportView(table2);

        buttonCustom3.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom3.setText("BAIXA");
        buttonCustom3.setColor(new java.awt.Color(30, 136, 56));
        buttonCustom3.setColorOver(new java.awt.Color(40, 146, 66));
        buttonCustom3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        buttonCustom3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom3ActionPerformed(evt);
            }
        });

        jTextFieldCustom1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldCustom1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldCustom1KeyReleased(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("BAIXA");

        jTextFieldCustomDateInit.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldCustomDateInit.setFont(new java.awt.Font("segoe", 0, 12)); // NOI18N

        jTextFieldCustomDateEnd.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldCustomDateEnd.setFont(new java.awt.Font("segoe", 0, 12)); // NOI18N

        buttonCustom4.setText("BUSCAR");
        buttonCustom4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom4ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setText("DE");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setText("ATÉ");

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scrollPaneWin112, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, kGradientPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                        .addGap(1069, 1069, 1069)
                        .addComponent(buttonCustom3, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                    .addComponent(scrollPaneWin111, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, kGradientPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(301, 301, 301)
                        .addComponent(comboboxCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 341, Short.MAX_VALUE)
                        .addComponent(buttonCustom4, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldCustomDateInit, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldCustomDateEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)))
                .addGap(19, 19, 19))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCustom3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPaneWin111, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextFieldCustomDateEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextFieldCustomDateInit, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel15)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(buttonCustom4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 13, Short.MAX_VALUE))
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(comboboxCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextFieldCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPaneWin112, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                .addGap(26, 26, 26))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabbedPaneCustom1.addTab("BUSCAR", jPanel2);

        kGradientPanel4.setkEndColor(new java.awt.Color(250, 250, 250));
        kGradientPanel4.setkStartColor(new java.awt.Color(204, 204, 204));

        buttonCustom2.setForeground(new java.awt.Color(255, 255, 255));
        buttonCustom2.setText("PEGAR/SERVER");
        buttonCustom2.setColor(new java.awt.Color(30, 136, 56));
        buttonCustom2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        buttonCustom2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel4Layout = new javax.swing.GroupLayout(kGradientPanel4);
        kGradientPanel4.setLayout(kGradientPanel4Layout);
        kGradientPanel4Layout.setHorizontalGroup(
            kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel4Layout.createSequentialGroup()
                .addContainerGap(1181, Short.MAX_VALUE)
                .addComponent(buttonCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );
        kGradientPanel4Layout.setVerticalGroup(
            kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel4Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(buttonCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(594, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kGradientPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabbedPaneCustom1.addTab("CONFIGURAÇÂO", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPaneCustom1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPaneCustom1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCustom1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom1ActionPerformed
        delStateChanged();
        saveStateChanged(1);

        boolean checkField = checkField();
        if (checkField) {
            String sql = "insert into controller(id_fk,badge, typeBadge,tag, fixoOrSpot,company,"
                    + "visitor, rg, cpf , phone, plateCar, plateCart, localAcess,"
                    + "liberator, reason , barCode, dataIn) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

//        LocalDateTime r = LocalDateTime.now();
//        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dataStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());

            try {
                TimeZone.setDefault(TimeZone.getTimeZone("GMT-3"));
                con = Conections.createConnections();
                pstm = con.prepareStatement(sql);
                pstm.setInt(1, (int) ptr[0]);
                pstm.setString(2, jTextFieldCustomCracha.getText().toUpperCase());
                pstm.setString(3, jTextFieldCustomTCracha.getText().toUpperCase());
                pstm.setString(4, jTextFieldCustomTag.getText().toUpperCase());
                pstm.setString(5, comboboxCustomFixoOrSpot.getSelectedItem().toString().toUpperCase());
                pstm.setString(6, jTextFieldCustomEmpresa.getText().toUpperCase());
                pstm.setString(7, jTextFieldCustomVisiPresta.getText().toUpperCase());
                pstm.setString(8, jTextFieldCustomRg.getText().toUpperCase());
                pstm.setString(9, jTextFieldCustomCpf.getText().toUpperCase());
                pstm.setString(10, jTextFieldCustomCelular.getText().toUpperCase());
                pstm.setString(11, jTextFieldCustomPlaca.getText().toUpperCase());
                pstm.setString(12, jTextFieldCustomPlateCt.getText().toUpperCase());
                pstm.setString(13, comboboxCustomLAcesso.getSelectedItem().toString().toUpperCase());
                pstm.setString(14, jTextFieldCustomLiberador.getText().toUpperCase());
                pstm.setString(15, jTextFieldCustomMotivo.getText().toUpperCase());
                pstm.setString(16, jTextFieldCustomCodigoDeBarra.getText());
                pstm.setString(17, dataStamp);

                pstm.execute();

            } catch (SQLException e) {
                System.err.println("erro ao criar " + e);
            }

            paneCustom = new JOPtionCSwing(this, true, "Cadastrado".toUpperCase());
            paneCustom.setVisible(true);
            delStateChanged();

            clearField();
            saveStateChanged(0);
            delStateChangedTag();
        } else {
            paneCustom = new JOPtionCSwing(this, true, "Preencha todos os campos".toUpperCase());
            paneCustom.setVisible(true);

        }

    }//GEN-LAST:event_buttonCustom1ActionPerformed

    private void jTextFieldCustom1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCustom1KeyReleased
        search(comboboxCustom1.getSelectedItem().toString(), jTextFieldCustom1.getText());
    }//GEN-LAST:event_jTextFieldCustom1KeyReleased

    public void saveStateChanged(int s) {

        String sql = "insert into states(statesSave) values(?) ";
        //String sql = "update states set statesSave=1 where id = 0 ";
        try {

            con = Conections.createConnections();
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, s);
            pstm.execute();
            con.close();
        } catch (SQLException e) {
            System.out.println("erro em inserir " + e);
        }
    }

    public void delStateChanged() {

        String sql = "truncate states ";

        try {

            con = Conections.createConnections();
            pstm = con.prepareStatement(sql);
            pstm.execute();
            con.close();
        } catch (SQLException e) {
            System.out.println("erro em truncate " + e);
        }
    }

    public void delStateChangedTag() {

        String sql = "truncate tagstate ";

        try {

            con = Conections.createConnections();
            pstm = con.prepareStatement(sql);
            pstm.execute();
            con.close();
        } catch (SQLException e) {
            System.out.println("erro em truncate " + e);
        }
    }

    public void delTag() {

        String sql = "truncate tags ";

        try {

            con = Conections.createConnections();
            pstm = con.prepareStatement(sql);
            pstm.execute();
            con.close();
        } catch (SQLException e) {
            System.out.println("erro em truncate " + e);
        }
    }

    public boolean checkField() {

        boolean validate = false;

        if (!jTextFieldCustomCracha.getText().equals("") && !jTextFieldCustomTag.getText().equals("") && !jTextFieldCustomEmpresa.getText().equals("")
                && !jTextFieldCustomVisiPresta.getText().equals("") && !jTextFieldCustomRg.getText().equals("") && !jTextFieldCustomCpf.getText().equals("")
                && !jTextFieldCustomPlaca.getText().equals("") && !jTextFieldCustomCelular.getText().equals("")
                && !jTextFieldCustomPlateCt.getText().equals("") && !jTextFieldCustomTCracha.getText().equals("") && !jTextFieldCustomLiberador.getText().equals("")
                && !jTextFieldCustomMotivo.getText().equals("")) {
            validate = true;
        }

        return validate;
    }

    private void clearField() {
        jTextFieldCustomCodigoDeBarra.setText("");
        jTextFieldCustomCracha.setText("");
        jTextFieldCustomTag.setText("");
        jTextFieldCustomEmpresa.setText("");
        jTextFieldCustomVisiPresta.setText("");
        jTextFieldCustomRg.setText("");
        jTextFieldCustomCpf.setText("");
        jTextFieldCustomCelular.setText("");
        jTextFieldCustomPlaca.setText("");
        comboboxCustomFixoOrSpot.setSelectedIndex(-1);
        comboboxCustomLAcesso.setSelectedIndex(-1);
        jTextFieldCustomTCracha.setText("");
        jTextFieldCustomPlateCt.setText("");
        jTextFieldCustomLiberador.setText("");
        jTextFieldCustomMotivo.setText("");

    }

    public void getDataForDate(String init, String end, DefaultTableModel df3) {

        table1.setModel(new DefaultTableModel(null, new String[]{"CRACHA", "T/CRACHA", "TAG", "FIXO/SPOT", "EMPRESA",
            "VISI/PRES", "RG", "CPF", "CELULAR", "PLACA", "PLACA-CT", "L/ACESSO",
            "LIBERADOR", "MOTIVO", " D/ENT", "H/ENTRA", " D/SAI", "H/SAI"}));

        df3 = (DefaultTableModel) table1.getModel();

        table1.setAutoCreateRowSorter(true);
        df3.setRowCount(0);

        try {

            con = Conections.createConnections();
            pstm = con.prepareStatement("select * from controller where cast(dataIn as date) >= '" + init + "' and cast(dataOut as date) <= '" + end + "' ");
            rs = pstm.executeQuery();
            while (rs.next()) {

                DateFormat dfOut = new SimpleDateFormat("dd-MM-yyyy");
                DateFormat dfInp = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat hora = new SimpleDateFormat("HH:mm:ss");
                DateFormat horaEn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                String dateFormatsEntra = rs.getString("dataIn");
                String dateFormatsSaida = rs.getString("dataOut");

                String newDateEntra = "", horarioEntra = "", newDateSai = "", horarioSai = "";

                if (dateFormatsEntra != null) {
                    Date datesEntra = dfInp.parse(dateFormatsEntra);
                    newDateEntra = dfOut.format(datesEntra);

                    Date dateHEntra = horaEn.parse(dateFormatsEntra);
                    horarioEntra = hora.format(dateHEntra);

                } else {
                    newDateEntra = "s/n";
                    horarioEntra = "s/n";
                }

                if (dateFormatsSaida != null) {
                    Date datesSai = dfInp.parse(dateFormatsSaida);
                    newDateSai = dfOut.format(datesSai);

                    Date dateHSai = horaEn.parse(dateFormatsSaida);
                    horarioSai = hora.format(dateHSai);
                } else {
                    newDateSai = "s/n";
                    horarioSai = "s/n";
                }

                Object[] line = {rs.getString("badge"), rs.getString("typeBadge"), rs.getString("tag"), rs.getString("fixoOrSpot"),
                    rs.getString("company"), rs.getString("visitor"), rs.getString("rg"), rs.getString("cpf"),
                    rs.getString("phone"), rs.getString("plateCar"), rs.getString("plateCart"), rs.getString("localAcess"),
                    rs.getString("liberator"),
                    rs.getString("reason"), newDateEntra, horarioEntra, newDateSai, horarioSai};

                df3.addRow(line);

            }
        } catch (SQLException | ParseException ex) {
            System.out.println("erro " + ex);
        }
    }

    private void jTextFieldCustomMotivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCustomMotivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCustomMotivoActionPerformed

    private void comboboxCustomLAcessoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboboxCustomLAcessoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboboxCustomLAcessoActionPerformed

    private void buttonCustom2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom2ActionPerformed

        try {
            String file = "log.bin";

            FileInputStream fil = new FileInputStream(file);
            InputStreamReader r = new InputStreamReader(fil, "UTF-8");
            BufferedReader br = new BufferedReader(r);
            String s;
            s = br.readLine();

            if (s.equals("localhost")) {
                InetAddress addr = InetAddress.getLocalHost();
                JOptionPane.showMessageDialog(null, addr.getHostAddress());
            } else {
                JOptionPane.showMessageDialog(null, "O SERVIDOR ESTÁ LOCALIZADO EM OUTRA MAQUINA");
            }

        } catch (UnknownHostException e) {
        } catch (IOException ex) {
            Logger.getLogger(FrameNew.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_buttonCustom2ActionPerformed

    private void table2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table2MouseClicked
        int selectedRow = table2.getSelectedRow();
        TableModel model = table2.getModel();
        crachaRe = (String) model.getValueAt(selectedRow, 0);
        String dataRe = (String) model.getValueAt(selectedRow, 14);
        String horaRe = (String) model.getValueAt(selectedRow, 15);

        DateFormat dfOut = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        DateFormat horaEn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date datesEntra;
        try {
            datesEntra = dfOut.parse(dataRe + " " + horaRe);
            newDateEntra = horaEn.format(datesEntra);

        } catch (ParseException ex) {
            Logger.getLogger(FrameNew.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("data in " + newDateEntra + " bagde " + crachaRe);

    }//GEN-LAST:event_table2MouseClicked

    private void buttonCustom3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom3ActionPerformed

        delStateChanged();

        String dataStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        String updateSql = "update controller set dataOut = ? WHERE dataIn = '" + newDateEntra + "'  and badge = '" + crachaRe + "' ";

        try {
            TimeZone.setDefault(TimeZone.getTimeZone("GMT-3"));
            con = Conections.createConnections();
            pstm = con.prepareStatement(updateSql);

            pstm.setString(1, dataStamp);

            pstm.executeUpdate();
            saveStateChanged(1);
        } catch (SQLException e) {
            System.err.println("erro ao criar " + e);
        }
        paneCustom = new JOPtionCSwing(this, true, "saída efetuada".toUpperCase());
        paneCustom.setVisible(true);
        delStateChanged();
        saveStateChanged(0);
    }//GEN-LAST:event_buttonCustom3ActionPerformed

    private void buttonCustom4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom4ActionPerformed

        String dataIn = jTextFieldCustomDateInit.getText();
        String dataOut = jTextFieldCustomDateEnd.getText();

        DateFormat dfOut;
        DateFormat dfIn;
        dfOut = new SimpleDateFormat("dd-MM-yyyy");
        dfIn = new SimpleDateFormat("yyyy-MM-dd");
        Date parseNew;
        String formatDbEnd = null, formatDbIn = null;

        try {
            parseNew = dfOut.parse(dataIn);

            formatDbIn = dfIn.format(parseNew);
            System.out.println("data db ini " + formatDbIn);

            parseNew = dfOut.parse(dataOut);

            formatDbEnd = dfIn.format(parseNew);
            System.out.println("data db out " + formatDbEnd);

        } catch (ParseException ex) {
            Logger.getLogger(FrameNew.class.getName()).log(Level.SEVERE, null, ex);
        }

        getDataForDate(formatDbIn, formatDbEnd, df);

    }//GEN-LAST:event_buttonCustom4ActionPerformed

    private void jTextFieldCustomTCrachaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCustomTCrachaKeyReleased


    }//GEN-LAST:event_jTextFieldCustomTCrachaKeyReleased

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
            java.util.logging.Logger.getLogger(FrameNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        UIDefaults ui = UIManager.getDefaults();
        ui.put("ScrollBarUI", ScrollBarWin11UI.class.getCanonicalName());

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameNew(nome, ptr).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private style.ButtonCustom buttonCustom1;
    private style.ButtonCustom buttonCustom2;
    private style.ButtonCustom buttonCustom3;
    private style.ButtonCustom buttonCustom4;
    private style.comboboxCustom comboboxCustom1;
    private style.comboboxCustom comboboxCustomFixoOrSpot;
    private style.comboboxCustom comboboxCustomLAcesso;
    private com.raven.datechooser.DateChooser dateChooser1;
    private com.raven.datechooser.DateChooser dateChooser2;
    private com.raven.datechooser.DateChooser dateChooser3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelPtr;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private style.JTextFieldCustom jTextFieldCustom1;
    private style.JTextFieldCustom jTextFieldCustomCelular;
    private style.JTextFieldCustom jTextFieldCustomCodigoDeBarra;
    private style.JTextFieldCustom jTextFieldCustomCpf;
    private style.JTextFieldCustom jTextFieldCustomCracha;
    private style.JTextFieldCustom jTextFieldCustomDateEnd;
    private style.JTextFieldCustom jTextFieldCustomDateInit;
    private style.JTextFieldCustom jTextFieldCustomEmpresa;
    private style.JTextFieldCustom jTextFieldCustomLiberador;
    private style.JTextFieldCustom jTextFieldCustomMotivo;
    private style.JTextFieldCustom jTextFieldCustomPlaca;
    private style.JTextFieldCustom jTextFieldCustomPlateCt;
    private style.JTextFieldCustom jTextFieldCustomRg;
    private style.JTextFieldCustom jTextFieldCustomTCracha;
    private style.JTextFieldCustom jTextFieldCustomTag;
    private style.JTextFieldCustom jTextFieldCustomVisiPresta;
    private keeptoo.KGradientPanel kGradientPanel1;
    private keeptoo.KGradientPanel kGradientPanel2;
    private keeptoo.KGradientPanel kGradientPanel4;
    private scroll.ScrollPaneWin11 scrollPaneWin111;
    private scroll.ScrollPaneWin11 scrollPaneWin112;
    private style.TabbedPaneCustom tabbedPaneCustom1;
    private style.Table table1;
    private style.Table table2;
    // End of variables declaration//GEN-END:variables
}
