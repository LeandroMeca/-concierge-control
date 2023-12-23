package frames;

import static factory.Conections.load;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;


public class initProgram {

    public static void main(String[] args) {
        boolean load = load();

        if (!load) {
            ConfigJFrame fs = new ConfigJFrame();
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    try {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                        Logger.getLogger(initProgram.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
            }
            fs.setVisible(true);
        } else {
            LoginJFrame f = new LoginJFrame();
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    try {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                        Logger.getLogger(initProgram.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
            }
            f.setVisible(true);
        }

    }
    
    
}
