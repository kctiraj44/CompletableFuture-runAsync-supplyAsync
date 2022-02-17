import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class r {

    public static Void saveEmployees(File file) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(5);
        ObjectMapper mapper = new ObjectMapper();
        CompletableFuture<Void> completableFuture= CompletableFuture.runAsync(()->{
            try {
              List<Employee> employees =  mapper.readValue(file , new TypeReference<List<Employee>>(){});
                System.out.println(employees.size());

            } catch (IOException e) {
                e.printStackTrace();
            }
        },service);
      return completableFuture.get();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        r r1 =new r();
       r1.saveEmployees(new File("src/employees.json"));
    }
}
