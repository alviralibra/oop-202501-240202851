# Laporan Praktikum Minggu 1 (sesuaikan minggu ke berapa?)
Topik: [Tuliskan judul topik, misalnya "Class dan Object"]

## Identitas
- Nama  :  Haidar Habibi Al Farisi
- NIM   : 240202862
- Kelas : 3IKRA

---

## Tujuan
(Tuliskan tujuan praktikum minggu ini.  
: *Mahasiswa mampu membangun antarmuka grafis sederhana menggunakan JavaFX.*)

---

## Dasar Teori
(Tuliskan ringkasan teori singkat (3–5 poin) yang mendasari praktikum.  
Contoh:  
1. Event-driven programming adalah paradigma pemrograman yang alur eksekusinya ditentukan oleh event seperti klik tombol atau input pengguna.
2. JavaFX merupakan framework Java untuk membangun aplikasi desktop berbasis GUI.
3. Arsitektur MVC (Model–View–Controller) memisahkan tampilan, logika aplikasi, dan data.)

---

## Langkah Praktikum
(Tuliskan Langkah-langkah dalam prakrikum, contoh:
1. Menyiapkan project JavaFX dengan struktur direktori sesuai ketentuan praktikum. 
2. Menggunakan kembali class Product, ProductDAO, dan ProductService dari praktikum sebelumnya (Bab 11).
3.Membuat form GUI JavaFX untuk input data produk (kode, nama, harga, stok)
4.Menambahkan event handler pada tombol Tambah Produk.
5.Menghubungkan controller GUI dengan ProductService.
6.Menampilkan data produk ke ListView setelah berhasil ditambahkan.
7.Menjalankan aplikasi JavaFX dan mendokumentasikan hasilnya.
8.Melakukan commit dengan format pesan yang ditentukan:week12-gui-dasar: implementasi GUI JavaFX input dan daftar produk

)

---

## Kode Program
(Tuliskan kode utama yang dibuat, contoh:  

```java
package com.upb.agripos;

import java.sql.Connection;
import java.sql.DriverManager;

import com.upb.agripos.controller.ProductController;
import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.dao.ProductDAOImpl;
import com.upb.agripos.service.ProductService;
import com.upb.agripos.view.ProductFormView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppJavaFX extends Application {

    @Override
    public void start(Stage stage) {
        try {
            Class.forName("org.postgresql.Driver");
            //  Database Connection
            Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/agripos", "postgres", "JERUKAGUNG"
            );

            // 2.  MVC Components
            ProductDAO dao = new ProductDAOImpl(conn);
            ProductService service = new ProductService(dao);
            ProductFormView view = new ProductFormView();
            new ProductController(service, view); // Controller menghubungkan View & Service

            // 3. Setup Scene & Stage
            Scene scene = new Scene(view, 400, 500);
            stage.setTitle("Agri-POS - Week 12 (GUI Dasar)");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
```
)
---

## Hasil Eksekusi
( hasil eksekusi program.  
![Screenshot hasil](screenshots/image.png)
)
---

## Analisis
(
- GUI bekerja dengan pendekatan event-driven, di mana aksi pengguna memicu eksekusi kode.
- Layer View hanya menangani tampilan dan event, tanpa logika CRUD.  
- Kendala yang dihadapi adalah validasi input angka, yang diatasi dengan konversi tipe data dan pengecekan input.
)
---

## Kesimpulan
(Tuliskan kesimpulan dari praktikum minggu ini.  
Contoh: *Dengan menggunakan JavaFX dan konsep event-driven programming, aplikasi menjadi lebih interaktif dan mudah digunakan. Integrasi GUI dengan service dan DAO menghasilkan aplikasi yang terstruktur, sesuai prinsip MVC dan SOLID, serta mudah dikembangkan pada tahap selanjutnya.*)

---

## Quiz
(1. Apa yang dimaksud dengan event-driven programming? 
   **Jawaban:** Paradigma pemrograman yang alur eksekusinya dipicu oleh event seperti klik tombol atau input pengguna.

2.Mengapa GUI tidak boleh memanggil DAO secara langsung? 
   **Jawaban:** Agar tidak melanggar prinsip DIP dan menjaga pemisahan tanggung jawab antar layer aplikasi. 

3. Sebutkan komponen utama MVC pada praktikum ini! 
   **Jawaban:** Model (Product), View (JavaFX Form), Controller (ProductController), dan Service (ProductService).  )
