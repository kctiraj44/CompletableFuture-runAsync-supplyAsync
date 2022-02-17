import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class RunAsyncExample {
    public static Void saveEmployees(File jsonFile) throws ExecutionException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        CompletableFuture<Void> completableFuture= CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.print("Thread Name :" + Thread.currentThread().getName());
                    List<Employee> employeesList = mapper.readValue(jsonFile, new TypeReference<List<Employee>>() {
                    });
                    System.out.println( employeesList.size());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return completableFuture.get();

    }
    //------------------------------------------------------------------------------------------------------------
    public static void saveEmployeesWithExecutors(File jsonfile) throws IOException {
        int count = Runtime.getRuntime().availableProcessors();
        ExecutorService service = Executors.newFixedThreadPool(count);
       CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                    ObjectMapper mapper = new ObjectMapper();
                try {
                    List<Employee> employees= mapper.readValue(jsonfile,new TypeReference<List<Employee>>(){});
                    System.out.print("Thread Name :" + Thread.currentThread().getName());
                    System.out.println(employees.size());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
    },service);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        RunAsyncExample runAsyncExample = new RunAsyncExample();
        runAsyncExample.saveEmployees(new File("src/employees.json"));
        System.out.println("+======================================================================+");
        runAsyncExample.saveEmployeesWithExecutors(new File("src/employees.json"));
    }
}