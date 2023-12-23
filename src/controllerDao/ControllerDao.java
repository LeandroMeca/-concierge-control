package controllerDao;

import employee.Employee;
import factory.Conections;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

public class ControllerDao extends factory.Conections implements interfaceController.InterfaceController {

    Connection con = createConnections();
    PreparedStatement pstm;
    ResultSet rs;

    @Override
    public void insert(Object[] ptr,Employee employee) {
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
            pstm.setString(2, employee.getBadge().toUpperCase());
            pstm.setString(3, employee.getTypeBadge().toUpperCase());
            pstm.setString(4, employee.getTag().toUpperCase());
            pstm.setString(5, employee.getFixoOrSpot().toUpperCase());
            pstm.setString(6, employee.getCompany().toUpperCase());
            pstm.setString(7, employee.getVisitor().toUpperCase());
            pstm.setString(8, employee.getRg().toUpperCase());
            pstm.setString(9, employee.getCpf().toUpperCase());
            pstm.setString(10, employee.getPhone().toUpperCase());
            pstm.setString(11, employee.getPlateCar().toUpperCase());
            pstm.setString(12, employee.getPlateCart().toUpperCase());
            pstm.setString(13, employee.getLocalAcess().toUpperCase());
            pstm.setString(14, employee.getLiberator().toUpperCase());
            pstm.setString(15, employee.getReason().toUpperCase());
            pstm.setString(16, employee.getBarCode());
            pstm.setString(17, dataStamp);

            pstm.execute();

        } catch (SQLException e) {
            System.err.println("erro ao criar " + e);
        }

    }

    @Override
    public void update() {

    }

    @Override
    public List<String> getData(int id) {

        return null;
    }

    @Override
    public List<String> getData(String name) {
        return null;
    }

}
