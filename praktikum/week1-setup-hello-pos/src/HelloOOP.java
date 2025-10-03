


    // HelloOOP
class Mahasiswa{
   String nama;
   int nim;
   Mahasiswa(String m, int n) {
      this.nama = m ;
      this.nim = n;

   }
   void sapa(){
    System.out.println("Hello world I am "+ nama + ","+nim);
   }
}

public class HelloOOP {
   public static void main(String[] args) {
    Mahasiswa m = new Mahasiswa("Alvira",240202851);
    m.sapa();
   }
}
