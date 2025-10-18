package com.upb.agripos.model;

public class Produk {
    protected String kode;
    protected String nama;
    protected double harga;
    protected int stok;

    public Produk(String kode, String nama, double harga, int stok) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    public void tambahStok(int jumlah) {
        this.stok += jumlah;
    }

    public void kurangiStok(int jumlah) {
        if (jumlah <= stok) {
            this.stok -= jumlah;
        } else {
            System.out.println("Stok tidak mencukupi!");
        }
    }

    public void tampilkanInfo() {
        System.out.println("Kode: " + kode + " | Nama: " + nama +
            " | Harga: Rp" + harga + " | Stok: " + stok);
    }
}
