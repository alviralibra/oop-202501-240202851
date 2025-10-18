package com.upb.agripos.model;

public class Benih extends Produk {
    private String varietas;

    public Benih(String kode, String nama, double harga, int stok, String varietas) {
        super(kode, nama, harga, stok);
        this.varietas = varietas;
    }

    public void deskripsi() {
        System.out.println("Kode: " + kode + " | Nama: " + nama + 
            " | Harga: Rp" + harga + " | Stok: " + stok + 
            " | Varietas: " + varietas);
    }
}
