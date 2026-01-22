# Laporan Praktikum Minggu 1 (sesuaikan minggu ke berapa?)
Topik: [Tuliskan judul topik, misalnya "Class dan Object"]

## Identitas
- Nama  : Haidar Habibi Al Farisi
- NIM   : 240202862
- Kelas : 3IKRA

---

## Tujuan
(Tuliskan tujuan praktikum minggu ini.  
Contoh: *Mahasiswa Dapat Memahani Konsep SOLID.*)

---

## Dasar Teori
(Tuliskan ringkasan teori singkat (3–5 poin) yang mendasari praktikum.  
Contoh:  
1. Class adalah blueprint dari objek.  
2. Object adalah instansiasi dari class.  
3. Enkapsulasi digunakan untuk menyembunyikan data.)

---

## Langkah Praktikum
(Tuliskan Langkah-langkah dalam prakrikum, contoh:
1. Langkah-langkah yang dilakukan (setup, coding, run).  
2. File/kode yang dibuat.  
3. Commit message yang digunakan.)

---

## SOLID PRINSIP UML
(  
---
(UML  
![Screenshot hasil](/praktikum/week6-uml-solid/screenshots/uml%20usecase.png)
)
(UML  
![Screenshot hasil](/praktikum/week6-uml-solid/screenshots//uml%20activity.png)
)
(
![Screenshot hasil](/praktikum/week6-uml-solid/screenshots/uml_classdiagram.png)
)
![Screenshot hasil](/praktikum/week6-uml-solid/screenshots/uml%20seq.png)

![Screenshot hasil](/praktikum/week6-uml-solid/screenshots/class%20diagram.png)

---
)



## Kesimpulan

Desain arsitektur sistem Agri-POS menggunakan UML dan prinsip SOLID berhasil menghasilkan sistem yang mudah diperluas dan dirawat. Dengan menerapkan SRP, OCP, dan DIP, kode menjadi modular dan fleksibel untuk perubahan di masa depan. Diagram UML membantu komunikasi desain yang jelas, sementara prinsip SOLID memastikan kualitas implementation yang baik.

---

## Quiz

1. **Jelaskan perbedaan aggregation dan composition serta berikan contoh penerapannya pada desain Anda.**
   
   **Jawaban:** 
   - **Aggregation**: Relasi HAS-A yang longgar, child dapat exist tanpa parent (diamond kosong di UML). Contoh: Transaksi → Produk.
   - **Composition**: Relasi PART-OF yang kuat, child tidak exist tanpa parent (diamond penuh di UML). Contoh: Transaksi → ItemKeranjang.

2. **Bagaimana prinsip Open/Closed dapat memastikan sistem mudah dikembangkan?**
   
   **Jawaban:** 
   - Sistem terbuka untuk extension (tambah metode pembayaran baru) tetapi tertutup untuk modifikasi (Transaksi class tidak perlu ubah).
   - Dengan interface PaymentMethod, dapat menambah PembayaranTransferBank/QRIS tanpa mengubah kode existing, sehingga mengurangi risk bug dan development lebih cepat.

3. **Mengapa Dependency Inversion Principle (DIP) meningkatkan testability? Berikan contoh penerapannya.**
   
   **Jawaban:** 
   - DIP memungkinkan kita inject mock PaymentMethod saat testing tanpa perlu actual implementation.
   - Contoh: `Transaksi trx = new Transaksi(mockPayment)` → bisa test logic tanpa bergantung pada PembayaranTunai/EWallet yang sebenarnya.
   - Hasilnya: test lebih isolated, mudah verify behavior, dan tidak ada side effect dari external dependencies.
