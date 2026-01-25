# Laporan Praktikum Minggu 1 (sesuaikan minggu ke berapa?)
Topik: [Tuliskan judul topik, misalnya "Class dan Object"]

## Identitas
- Nama  : Alvira Libra Ramadhani
- NIM   : 240202851
- Kelas : 3IKRA

---

## Tujuan
Praktikum minggu ini bertujuan untuk:
a. Memodelkan sistem menggunakan UML (Use Case, Activity, Sequence, Class).
b. Menerapkan dan menjelaskan prinsip SOLID pada desain arsitektur Agri-POS.
c. Menyusun dokumentasi d

---

## Dasar Teori
a. UML untuk memvisualisasikan arsitektur dan interaksi sistem.
b. Use Case menggambarkan aktor & fungsionalitas sistem.
c. Activity Diagram menunjukan alur bisnis (flow + alternatif).
d. Sequence Diagram menampilkan pertukaran pesan antar objek dari sudut waktu.
e. Class Diagram memodelkan struktur data dan relasi.
f. SOLID: prinsip desain OOP

---

## Langkah Praktikum
a. Analisis kebutuhan sistem (FR & NFR).
b. Buat Use Case Diagram (aktor: Admin, Kasir, Payment Gateway).
c. Buat Activity Diagram untuk proses Checkout (swimlane Kasir / Sistem / Payment
Gateway).
d. Buat Sequence Diagram untuk skenario pembayaran (Tunai & E-Wallet; skenario
gagal).
e. Buat Class Diagram (atribut, method/signature, visibility, multiplicity).
f. Dokumentasikan penerapan SOLID dan traceability.
g. Commit incremental: week6-uml-solid: iterasi-N <d

---

## SOLID PRINSIP UML
(  
---
(UML  
![Screenshot hasil](/praktikum/week6-uml-solid/screenshots/uml%20usecase.png)
)
(UML  
![Screenshot hasil](/praktikum/week6-uml-solid/screenshots/uml%20sequence.png)
)
(
![Screenshot hasil](/praktikum/week6-uml-solid/screenshots/uml%20class.png)
)
![Screenshot hasil](/praktikum/week6-uml-solid/screenshots/uml%20activity.png)



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
