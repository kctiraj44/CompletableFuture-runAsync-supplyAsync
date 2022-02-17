import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class EmployeeDatabase {

public static List<Employee> getEmployees() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
     List<Employee> employees = mapper.readValue( new File("src/employees.json"),new TypeReference<List<Employee>>(){} );
     return employees;
}
}
