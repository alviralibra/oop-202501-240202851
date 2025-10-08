package com.upb.agripos;

import com.upb.agripos.model.Produk;
import com.upb.agripos.util.CreditBy;

public class MainProduk {
    public static void main(String[] args) {

        Produk p1 = new Produk("ALR-002", "Benih Cabai AL10", 80000.0, 100);
        Produk p2 = new Produk("LBR-005", "Pupuk Organik 10kg", 90000.0, 100);
        Produk p3 = new Produk("AVR-025", "Cangkul Kecil", 50000.0, 100);

        System.out.println("=== Info Awal Produk ===");
        p1.tampilkanInfo();
        p2.tampilkanInfo();
        p3.tampilkanInfo();

        System.out.println("\n=== Menambah Stok Produk ===");
        System.out.println("Menambah stok Benih Cabai AL10 sebanyak 50");
        p1.tambahStok(50);
        p1.tampilkanInfo();

        System.out.println("\n=== Mengurangi Stok Produk ===");
        System.out.println("Mengurangi stok Cangkul Kecil sebanyak 50");
        p3.kurangiStok(50);
        p3.tampilkanInfo();

        CreditBy.print();
    }
}