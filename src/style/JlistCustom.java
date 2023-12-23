package style;

import com.raven.datechooser.EventDateChooser;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JTextField;




public class JlistCustom extends javax.swing.JList{
    
    
    private JTextField textRefernce;
    
    public JTextField getTextRefernce() {
        return textRefernce;
    }

   
    public JlistCustom() {
         
    }
    
    
    
    
    public void setTextRefernce(JTextField txt) {
        this.textRefernce = txt;
        this.textRefernce.setEditable(false);
        this.textRefernce.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (textRefernce.isEnabled()) {
                   
                }
            }
        });
        
    }
    
     

}


