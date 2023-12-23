package style;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

public class JPasswordFieldCustom extends JPasswordField {

    private static final long serialVersionUID = 1L;

    public JPasswordFieldCustom() {

        setOpaque(false);
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setFont(new Font("segoe", 1, 14));
    }

    @Override
    public char[] getPassword() {
        return super.getPassword();
    }
    
    
    
    

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(getBackground());
        graphics2D.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        super.paintComponent(g);
    }

}
