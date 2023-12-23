package factory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conections {

    
    private static String URL;

    public static Connection createConnections() {

        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //localhost
            con = DriverManager.getConnection("jdbc:mysql://" + URL + ":3306/controledeportaria?useTimezone=true&serverTimezone=UTC",
                    "root", "1234");
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERRO DE CONEXÂO " + ex);

        }
        return con;
    }

    public static boolean load() {

        boolean vali = false;

        String file = "log.bin";
        File fileFC = new File(file);

        if (!fileFC.exists()) {
            try {
                fileFC.createNewFile();
            } catch (IOException ex) {

            }
        }

        try {
            FileInputStream fil = new FileInputStream(file);
            InputStreamReader r = new InputStreamReader(fil, "UTF-8");
            BufferedReader br = new BufferedReader(r);
            String s;
            s = br.readLine();

            if (s != null) {

                vali = true;
            }

            URL = s;

        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            System.err.println("nao existe " + ex);
        } catch (IOException ex) {
            System.err.println("entrada " + ex);
        }

        return vali;
    }
}
