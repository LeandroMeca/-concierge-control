package interfaceController;

import employee.Employee;
import java.util.List;



public interface InterfaceController {
     public void insert(Object[] ptr, Employee employee);
     public void update();
     public List<String> getData(int id);
     public List<String> getData(String name);
}
