package com.multimediainformatika.bukabengkel.adapter;

/**
 * Created by eksmud on 30/04/2017.
 */

public class Bengkel {
    public String nama;
    public String alamat;
    public String jarak;
    public int jenis;
    public Bengkel(){

    }

    public Bengkel(String nama,String alamat,String jarak, int jenis)
    {
        this.nama = nama;
        this.alamat = alamat;
        this.jenis = jenis;
        this.jarak = jarak;
    }
}
