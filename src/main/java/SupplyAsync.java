import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public class SupplyAsync {


    public static List<Employee> fetchEmployees() throws ExecutionException, InterruptedException {
       CompletableFuture<List<Employee>> completableFuture =  CompletableFuture.supplyAsync(()->{
            try {
               List<Employee> employees =EmployeeDatabase.getEmployees();
               return employees;
            } catch (IOException e) {
                e.printStackTrace();
            }

           return null;
       });
       return  completableFuture.get();
    }



    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SupplyAsync supplyAsync = new SupplyAsync();
        List<Employee> employeeList =supplyAsync.fetchEmployees();
        employeeList.stream().forEach(System.out::println);
    }
}
