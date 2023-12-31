package style;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;



public class ButtonCustom extends JButton{

    public ButtonCustom() {
        
        setColor(new Color(217,255,203));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setRadius(15);
        colorOver = new Color(179, 250, 160);
        colorClick = new Color(152, 184, 144);
        colorBorder = new Color(30, 136, 56);
        setContentAreaFilled(false);
        
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
               setBackground(colorOver);
               over = true;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(colorClick);
            }           

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(color);
                over = false;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(over){
                    setBackground(colorOver);
                }else{
                    setBackground(color);
                }
            }
            
            
        });
    }
    
    
    private boolean over;
    private Color color;
    private Color colorOver;
    private Color colorClick;
    private Color colorBorder;
    private int radius = 0;

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        setBackground(color);
    }

    public Color getColorOver() {
        return colorOver;
    }

    public void setColorOver(Color colorOver) {
        this.colorOver = colorOver;
    }

    public Color getColorClick() {
        return colorClick;
    }

    public void setColorClick(Color colorClick) {
        this.colorClick = colorClick;
    }

    public Color getColorBorder() {
        return colorBorder;
    }

    public void setColorBorder(Color colorBorder) {
        this.colorBorder = colorBorder;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(colorBorder);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.setColor(getBackground());
        g2.fillRoundRect(2, 2, getWidth() -4, getHeight() -4, radius, radius);
        super.paintComponent(g);
    }
    
    
    
    
   
    
    
    
}
