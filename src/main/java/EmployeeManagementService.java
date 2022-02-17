import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class EmployeeManagementService {


    public static Void notifyEmployees() throws ExecutionException, InterruptedException {

        CompletableFuture<Void> completableFuture=  CompletableFuture.supplyAsync(()->{
            try {
                return EmployeeDatabase.getEmployees();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).thenApplyAsync((employees)->{
            return employees.stream().filter(employee -> employee.getNewJoiner().equals("TRUE")).collect(Collectors.toList());
        })
                .thenApplyAsync((employees)->{
                    return employees.stream().filter(employee -> employee.getLearningPending().equals("TRUE")).collect(Collectors.toList());
                })
                .thenApplyAsync((employees)->{
                    return employees.stream().map(employee -> employee.getEmail()).collect(Collectors.toList());
                })
                .thenAcceptAsync((emails)->{
                  emails.stream().forEach(EmployeeManagementService::sendEmail);
                });

        return completableFuture.get();
    }


    public static void sendEmail(String email){
        System.out.println("This is email for :"+email);
    }



    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EmployeeManagementService service = new EmployeeManagementService();
        service.notifyEmployees();
    }
}
