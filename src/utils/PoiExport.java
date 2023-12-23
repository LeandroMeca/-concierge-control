package utils;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;




public class PoiExport {

   
   
    public static void main(String[] args) {

    }

    public void exportar(JTable jTable1) {
 
        String currentUsersHomeDir = System.getProperty("user.home");
        File arquivoDoc = new File(currentUsersHomeDir + "/Documents/Relatorios/excel/clientes");
        if (!arquivoDoc.exists()) {

            JOptionPane.showMessageDialog(null, "Foi criado uma pasta de exportação de excel no " + arquivoDoc);
            arquivoDoc.mkdirs();
        }
        File fileSave;

        fileSave = new File(currentUsersHomeDir + "/Documents/Relatorios/excel/clientes/teste.xlsx");
        

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("RELATÓRIO");
       
        Row rowCol = sheet.createRow(0);
        
        
        sheet.setDefaultColumnWidth(13);
        
        CellStyle createCellStyle = workbook.createCellStyle();
        createCellStyle.setAlignment(HorizontalAlignment.CENTER);
        Cell cell = rowCol.createCell(0);
        cell.setCellStyle(createCellStyle);
        cell.setCellValue("   CRACHA  ");
        Cell cell2 = rowCol.createCell(1);
        cell2.setCellValue("TAG");
        Cell cell3 = rowCol.createCell(2);
        cell3.setCellValue("EMPRESA");
        Cell cell4 = rowCol.createCell(3);
        cell4.setCellValue("VIS/PRES");
        Cell cell5 = rowCol.createCell(4);
        cell5.setCellValue("CPF");
        Cell cell6 = rowCol.createCell(5);
        cell6.setCellValue("PLACA");
        Cell cell7 = rowCol.createCell(6);
        cell7.setCellValue("AGRE");
        Cell cell8 = rowCol.createCell(7);
        cell8.setCellValue("PORTARIA");
        Cell cell9 = rowCol.createCell(8);
        cell9.setCellValue("LOCAL/ACESSO");
        Cell cell10 = rowCol.createCell(9);
        cell10.setCellValue("TIPO/ACESSO");
        Cell cell11 = rowCol.createCell(10);
        cell11.setCellValue("LIBERADOR");
        Cell cell12 = rowCol.createCell(11);
        cell12.setCellValue("MOTIVO");
        Cell cell13 = rowCol.createCell(12);
        cell13.setCellValue("DATA");
        Cell cell14 = rowCol.createCell(13);
        cell14.setCellValue("HORA");

        FileOutputStream fileOutputStream;

        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            Cell cell15 = rowCol.createCell(i);
            cell15.setCellValue(jTable1.getColumnName(i));
        }
        for (int j = 0; j < jTable1.getRowCount(); j++) {
            Row row = sheet.createRow(j + 1);
            
            for (int k = 0; k < jTable1.getColumnCount(); k++) {
                Cell cell16 = row.createCell(k);
                if (jTable1.getValueAt(j, k) != null) {
                    cell16.setCellValue(jTable1.getValueAt(j, k).toString());
                }
            }
        }

        try {
            fileOutputStream = new FileOutputStream(new File(fileSave.toString()));
            workbook.write(fileOutputStream);
            workbook.close();
            fileOutputStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PoiExport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PoiExport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
