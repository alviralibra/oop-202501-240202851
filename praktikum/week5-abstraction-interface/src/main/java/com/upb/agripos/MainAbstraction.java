package com.upb.agripos;

import com.upb.agripos.model.kontrak.*;
import com.upb.agripos.model.pembayaran.*;
import com.upb.agripos.util.CreditBy;

public class MainAbstraction {
    public static void main(String[] args) {
        Pembayaran cash = new Cash("ALR-001", 200000, 250000);
        System.out.println(((Receiptable) cash).cetakStruk());

        Pembayaran ew = new EWallet("LBR-002", 221300, "viraa@ewallet", "020505");
        System.out.println(((Receiptable) ew).cetakStruk());

        Pembayaran transfer = new TransferBank("VRA-003", 200000, "viraa@bank", "112255");
        System.out.println(((Receiptable) transfer).cetakStruk());

        CreditBy.print();
    }
}
