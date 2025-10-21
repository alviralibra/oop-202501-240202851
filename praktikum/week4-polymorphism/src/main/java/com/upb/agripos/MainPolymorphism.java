package com.upb.agripos;

import com.upb.agripos.model.*;
import com.upb.agripos.util.CreditBy;

public class MainPolymorphism {
    public static void main(String[] args) {

        Produk[] daftarProduk = {
            new Benih("ALR-002", "Benih Cabai AL10", 80000, 100, "ALR"),
            new Pupuk("LBR-005", "Pupuk Organik 10kg", 90000, 100, "Organik"),
            new AlatPertanian("AVR-025", "Cangkul Kecil", 50000, 100, "Baja"),
            new ObatHama("AWA-555", "Obat Hama Kutu", 50000, 10, "Chlorantraniliprole")
        };

        for (Produk p : daftarProduk) {
            System.out.println(p.getInfo());
        }

        CreditBy.print();
    }
}

