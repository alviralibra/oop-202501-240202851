package com.upb.agripos.model;

public class AlatPertanian extends Produk {
    private String material;

    public AlatPertanian(String kode, String nama, double harga, int stok, String material) {
        super(kode, nama, harga, stok);
        this.material = material;
    }

    public void deskripsi() {
        System.out.println("Kode: " + kode + " | Nama: " + nama + 
            " | Harga: Rp" + harga + " | Stok: " + stok + 
            " | Material: " + material);
    }
}


