import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class S {

    public static List<Employee> fetchEmployees() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Employee>> completableFuture = CompletableFuture.supplyAsync(()->{
            ObjectMapper mapper = new ObjectMapper();
            try {
               List<Employee> employee=  mapper.readValue(new File("src/employees.json"),new TypeReference<List<Employee>>(){});

               return  employee;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        })  ;
       return completableFuture.get();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        S s1 =new S();
        List<Employee> employees=s1.fetchEmployees();
        employees.stream().forEach(System.out::println);
    }
}
