package com.multimediainformatika.bukabengkel.adapter;

/**
 * Created by eksmud on 30/03/2017.
 */

public class BookingItem {
    public String tgl_booking;
    public String no_antrian;
    public String no_paspor;
    public String nama;

    public BookingItem(String tgl_booking, String no_paspor, String nama, String no_antrian)
    {
        this.tgl_booking = tgl_booking;
        this.no_antrian =no_antrian;
        this.no_paspor =no_paspor;
        this.nama =nama;
    }
}
