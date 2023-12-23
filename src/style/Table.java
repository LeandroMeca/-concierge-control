package style;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;







public class Table extends JTable {
   
    public Table() {
        
        setShowHorizontalLines(true);
        setGridColor(new Color(230, 230, 230));
      //  setFont(new Font("sansserif", 3, 14));
        setRowHeight(40);
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setDefaultRenderer( new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
                TableHeader header = new TableHeader(o+"");
                if(i1==4){
                   header.setHorizontalAlignment(JLabel.CENTER);
                }
                return header;
            }
            
        });
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                //if(column!=4){
                    Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    com.setBackground(Color.WHITE);
                    setBorder(noFocusBorder);
                    if(isSelected){
                        com.setForeground(new Color(13,150,255));
                    }else{
                        com.setForeground(new Color(102, 102, 102));
                    }
                    return com;
//                }else{
//                    StatusType type = (StatusType) value;
//                    CellStatus cell = new CellStatus(type);
//                    return cell;
//                }
                
            }
        });
    }
    
    
    public void addRow(Object[] row){
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.addRow(row);
    }
    
}

