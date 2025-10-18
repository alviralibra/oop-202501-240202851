package com.upb.agripos;

import com.upb.agripos.model.*;
import com.upb.agripos.util.CreditBy;

public class MainInheritance {
    public static void main(String[] args) {

        Benih b = new Benih("BNH-001", "Benih Cabai AL10", 80000.0, 100, "AL10");
        Pupuk p = new Pupuk("PPK-101", "Pupuk Organik 25kg", 90000.0, 100, "Organik");
        AlatPertanian a = new AlatPertanian("ALT-501", "Cangkul Kecil", 50000.0, 100, "Baja");

        System.out.println("=== Data Produk Pertanian ===");
        b.deskripsi();
        p.deskripsi();
        a.deskripsi();

        System.out.println("\n=== Menambah Stok Produk ===");
        System.out.println("Menambah stok Benih Cabai AL10 sebanyak 50");
        b.tambahStok(50);
        b.deskripsi();

        System.out.println("\n=== Mengurangi Stok Produk ===");
        System.out.println("Mengurangi stok Cangkul Kecil sebanyak 50");
        a.kurangiStok(50);
        a.deskripsi();

        CreditBy.print();
    }
}

