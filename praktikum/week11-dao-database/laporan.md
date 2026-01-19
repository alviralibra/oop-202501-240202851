# Laporan Praktikum Minggu 11
Topik: Data Access Object (DAO) dan CRUD Database dengan JDBC

## Identitas
- Nama  : ALVIRA LIBRA RAMADHANI
- NIM   : 240202852
- Kelas : 3IKRA

---

## Tujuan
1. Memahami konsep Data Access Object (DAO) untuk memisahkan logika bisnis dengan logika akses data.
2. Mampu menghubungkan aplikasi Java ke database PostgreSQL menggunakan JDBC.
3. Mengimplementasikan operasi CRUD (Create, Read, Update, Delete) pada tabel produk.
4. Menerapkan prinsip Loose Coupling dengan menggunakan Interface DAO.

---

## Dasar Teori
1. DAO (Data Access Object): Sebuah pola desain yang menyediakan interface abstrak ke beberapa jenis database atau mekanisme persistensi lainnya.
2. JDBC (Java Database Connectivity): API standar Java yang memungkinkan aplikasi berinteraksi dengan basis data relasional.
3. PreparedStatement: Bagian dari JDBC yang digunakan untuk mengeksekusi query SQL yang terparameter, meningkatkan keamanan dari SQL Injection.
4. CRUD: Empat fungsi dasar penyimpanan persisten (Create, Read, Update, Delete).

---

## Langkah Praktikum
1. Konfigurasi Database: Membuat database agripos dan tabel products di PostgreSQL.
2. Membuat Class Model: Membuat class Product.java untuk merepresentasikan data produk sebagai objek.
3. Mendefinisikan Interface: Membuat ProductDAO.java sebagai kontrak fungsi (insert, find, update, delete).
4. Implementasi DAO: Membuat class ProductDAOImpl.java yang berisi logika JDBC (SQL query).
5. Testing: Menjalankan pengujian CRUD melalui MainDAOTest.java.
6. Dokumentasi: Mengambil screenshot hasil eksekusi dan melakukan commit ke GitHub.

---

## Kode Program
```java
// Contoh Implementasi Insert pada ProductDAOImpl
@Override
public void insert(Product p) throws Exception {
    String sql = "INSERT INTO products(code, name, price, stock) VALUES (?, ?, ?, ?)";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, p.getCode());
        ps.setString(2, p.getName());
        ps.setDouble(3, p.getPrice());
        ps.setInt(4, p.getStock());
        ps.executeUpdate();
    }
}
```

---

## Hasil Eksekusi
(Sertakan screenshot hasil eksekusi program.  
![Screenshot hasil](screenshots/image.png)
)
---

## Analisis
1. Analisis Kode: Dengan menggunakan DAO, class MainDAOTest tidak perlu tahu bagaimana perintah SQL ditulis. Cukup memanggil method seperti dao.insert(). Hal ini membuat kode lebih bersih.
2. Perbedaan: Jika pada minggu lalu data hanya ada di memori (hilang saat program mati), minggu ini data bersifat permanen karena tersimpan di PostgreSQL.
3. Kendala: Muncul error "No suitable driver found" saat mencoba koneksi.
4. Solusi: Menambahkan file JAR PostgreSQL Driver (postgresql-42.x.x.jar) ke dalam Library/Dependencies proyek.
---

## Kesimpulan
Penerapan pola DAO sangat efektif dalam menjaga keteraturan kode. Logika SQL dikumpulkan dalam satu tempat (DAOImpl), sehingga jika di masa depan database diganti (misalnya dari PostgreSQL ke MySQL), kita hanya perlu mengubah isi class Implementasinya saja tanpa merusak logika utama aplikasi.

---


