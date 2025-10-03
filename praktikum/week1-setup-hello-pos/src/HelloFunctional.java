

//functional
import java.util.function.BiConsumer;

public class HelloFunctional {
   
    public static void main(String[] args) {
        BiConsumer<String, String> sapa = 
            (nama, nim) -> System.out.println( nama + " -" + nim);

        // eksekusi fungsi
        sapa.accept("Hello World I am Alvira", "240202851");
        System.out.println("Program Functional Selesai");
    }

}
