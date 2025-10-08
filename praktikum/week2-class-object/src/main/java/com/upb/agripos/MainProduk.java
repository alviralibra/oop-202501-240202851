package com.upb.agripos;

import com.upb.agripos.model.Produk;

public class MainProduk {
    public static void main(String[] args) {

        Produk p1 = new Produk("ALR-010", "Benih Cabai AL10", 80000.0, 100);
        Produk p2 = new Produk("LBR-005", "Pupuk Organik 10kg", 90000.0, 100);
        Produk p3 = new Produk("AVR-020", "Cangkul Kecil", 50000.0, 100);

        System.out.println("=== Info Awal Produk ===");
        p1.tampilkanInfo();
        p2.tampilkanInfo();
        p3.tampilkanInfo();

        System.out.println("\n=== Menambah Stok Produk ===");
        System.out.println("Menambah stok Benih Cabai AL10 sebanyak 10");
        p1.tambahStok(10);
        p1.tampilkanInfo();

        System.out.println("\n=== Mengurangi Stok Produk ===");
        System.out.println("Mengurangi stok Cangkul Kecil sebanyak 5");
        p3.kurangiStok(5);
        p3.tampilkanInfo();
    }
}