import java.util.Scanner;

public class TestMain {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        long num = fibo(scanner.nextInt());
        System.out.print(num);
    }

    private static long fibo(int i) {
        if( i <= 1){
            System.out.println(i);
            return i;
        }
        else {
            System.out.println(i);
            return fibo(i-1)+fibo(i-2);
        }
    }
}
