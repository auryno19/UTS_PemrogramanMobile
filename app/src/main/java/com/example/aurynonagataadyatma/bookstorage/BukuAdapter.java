package com.example.aurynonagataadyatma.bookstorage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BukuAdapter extends BaseAdapter {

    Context context;
    ArrayList<Buku> arrayList;

    public BukuAdapter(Context context, ArrayList<Buku> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_home,null);
            TextView txJudul = (TextView) view.findViewById(R.id.textJudul);
            TextView txPengarang = (TextView) view.findViewById(R.id.textPengarang);
            TextView txStok = (TextView) view.findViewById(R.id.textStok);

            Buku buku = arrayList.get(i);

            txJudul.setText(buku.getJudul());
            txPengarang.setText(buku.getPengarang());
            txStok.setText(buku.getStok());

        return view;
    }

}
