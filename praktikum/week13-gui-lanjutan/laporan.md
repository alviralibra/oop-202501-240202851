# Laporan Praktikum Minggu 13 
Topik: [Tuliskan judul topik, misalnya "Class dan Object"]

## Identitas
- Nama  : Haidar Habibi Al Farisi
- NIM   : 240202862
- Kelas : 3IKRA

---

## Tujuan
(Tuliskan tujuan praktikum minggu ini.  
Contoh: *Mahasiswa mampu membangun antarmuka Agri-POS yang lebih interaktif dan terstruktur.*)

---

## Dasar Teori
(Tuliskan ringkasan teori singkat (3–5 poin) yang mendasari praktikum.  :  
1. TableView adalah komponen JavaFX yang digunakan untuk menampilkan data berbentuk tabel berbasis objek.  
2.ObservableList digunakan agar perubahan data otomatis terpantau oleh GUI.  
3.Lambda expression menyederhanakan penulisan event handler pada JavaFX.)

---

## Langkah Praktikum
(Tuliskan Langkah-langkah dalam prakrikum, contoh:
1. Melanjutkan project dari praktikum Week 12 tanpa membuat project baru. 
2. Mengganti tampilan daftar produk dari ListView menjadi TableView<Product>.
3. Mendokumentasikan hasil eksekusi dan melakukan commit sesuai ketentuan:
week13-gui-lanjutan: implementasi TableView dan hapus produk dengan lambda
)

---

## Kode Program
(Tuliskan kode utama yang dibuat:  

```java
package com.upb.agripos;

import java.sql.Connection;
import java.sql.DriverManager;

import com.upb.agripos.controller.ProductController;
import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.dao.ProductDAOImpl;
import com.upb.agripos.service.ProductService;
import com.upb.agripos.view.ProductTableView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppJavaFX extends Application {

    @Override
    public void start(Stage stage) {
        try {
            // 1. Setup Database Connection
            Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/agripos", "postgres", "JERUKAGUNG"
            );

            // 2. Setup MVC 
            ProductDAO dao = new ProductDAOImpl(conn);
            ProductService service = new ProductService(dao);
            ProductTableView view = new ProductTableView();
            new ProductController(service, view);

            // 3. Show Scene
            Scene scene = new Scene(view, 800, 600);
            stage.setTitle("Agri-POS Week 13 - Haidar Habibi Al Farisi (240202862)");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```
)
---

## Hasil Eksekusi
(Sertakan screenshot hasil eksekusi program.  
![Screenshot hasil](screenshots/image.png)
)
---

## Analisis
(
- JTableView berfungsi sebagai View murni tanpa mengandung logika bisnis.  
- Method loadData() mengikuti alur View → Controller → Service → DAO → DB. 
- Kendala yang ditemui adalah sinkronisasi data setelah delete, yang diatasi dengan pemanggilan ulang loadData().
)
---

## Kesimpulan
(kesimpulan dari praktikum minggu ini: 
Contoh: *Dengan mengimplementasikan TableView dan lambda expression, aplikasi Agri-POS menjadi lebih interaktif, responsif, dan terintegrasi penuh dengan database. Struktur aplikasi tetap konsisten dengan desain UML dan prinsip SOLID, sehingga siap dikembangkan lebih lanjut sebagai proyek akhir.*)

---

## Quiz
(1.Apa fungsi utama TableView pada JavaFX?
   **Jawaban:** Menampilkan data berbasis objek dalam bentuk tabel yang terintegrasi dengan koleksi data.

2. Mengapa digunakan lambda expression pada event handling?
   **Jawaban:** Untuk menyederhanakan kode dan meningkatkan keterbacaan event handler.  

3. Mengapa GUI tidak boleh memanggil DAO secara langsung?
   **Jawaban:** Agar tetap mematuhi prinsip DIP dan menjaga pemisahan tanggung jawab antar layer aplikasi. )
