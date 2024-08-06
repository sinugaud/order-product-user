import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyThread implements Runnable  {


    public static void main(String[] args) {

//        MyThread thread = new MyThread();

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        int sum = numbers.stream()
                .reduce(0, (a, b) -> a + b);
        System.out.println("Sum of all numbers: " + sum); // Output: Sum of all numbers: 15

        List<Integer> collect = numbers.stream().filter(f -> f == 0).collect(Collectors.toList());

        for(int inyy : collect)
        {
            System.out.println(inyy);

        }

        Thread threadd =new Thread(() ->System.out.println(""));
        // Calling run() directly
//        thread.run();
        
        // Starting the thread
//        thread.start();
    }

    @Override
    public void run() {

    }
}


