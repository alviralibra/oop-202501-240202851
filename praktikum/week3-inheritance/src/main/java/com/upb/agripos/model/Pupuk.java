package com.upb.agripos.model;

public class Pupuk extends Produk {
    private String jenis;

    public Pupuk(String kode, String nama, double harga, int stok, String jenis) {
        super(kode, nama, harga, stok);
        this.jenis = jenis;
    }

    public void deskripsi() {
        System.out.println("Kode: " + kode + " | Nama: " + nama + 
            " | Harga: Rp" + harga + " | Stok: " + stok + 
            " | Jenis: " + jenis);
    }
}