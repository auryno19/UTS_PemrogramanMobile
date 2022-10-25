package com.example.aurynonagataadyatma.bookstorage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    String [] list;
    ListView ListView01;
    Menu menu;
    protected Cursor cursor;
    DataHelper dbcenter;
    BukuAdapter bukuAdapter;
    ArrayList<Buku> arrayList;
    public static Home ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btn = (Button) findViewById(R.id.button2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //TODO Auto-generate method stub
                Intent inte = new Intent(Home.this,BuatBuku.class);
                startActivity(inte);
            }
        });

        ma = this;
        dbcenter = new DataHelper(Home.this);
        ListView01 = (ListView) findViewById(R.id.listView1);
        arrayList = new ArrayList<>();

        RefreshList();

    }

    public void RefreshList() {
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM buku",null);
        list = new String[cursor.getCount()];
        cursor.moveToFirst();
        for(int cc = 0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            list[cc] = cursor.getString(1).toString();
        }
        ListView01 = (ListView) findViewById(R.id.listView1);
//        ListView01.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, list));
//        ListView01.setSelected(true);
        arrayList = dbcenter.getAllData();
        bukuAdapter = new BukuAdapter(this,arrayList);
        ListView01.setAdapter(bukuAdapter);
        bukuAdapter.notifyDataSetChanged();

        ListView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = list[arg2];
                Intent i = new Intent(getApplicationContext(), LihatBuku.class);
                i.putExtra("judul", selection);
                startActivity(i);

            }
        });

        ListView01.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                final String selection = list[arg2];
                final CharSequence[] dialogitem = {"Update Buku", "Hapus Buku"};
                AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {
                        switch (item) {
                            case 0:
                                Intent in = new Intent(getApplicationContext(), UpdateBuku.class);
                                in.putExtra("judul", selection);
                                startActivity(in);
                                break;
                            case 1:
                                SQLiteDatabase db = dbcenter.getWritableDatabase();
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Home.this);

                                // set title dialog
                                alertDialogBuilder.setTitle("Peringatan !!");

                                // set pesan dari dialog
                                alertDialogBuilder
                                        .setMessage("Apakah Anda yakin ingin menghapus ?")
                                        .setCancelable(false)
                                        .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                // jika tombol diklik, maka akan dihapus
                                                db.execSQL("delete from buku where judul = '" + selection + "'");
                                                Toast.makeText(Home.this, "Buku berhasil dihapus", Toast.LENGTH_SHORT).show();
                                                RefreshList();
                                            }
                                        })
                                        .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // jika tombol ini diklik, akan menutup dialog
                                                // dan tidak terjadi apa2
                                                dialog.cancel();
                                            }
                                        });

                                // membuat alert dialog dari builder
                                AlertDialog alertDialog = alertDialogBuilder.create();

                                // menampilkan alert dialog
                                alertDialog.show();
                                break;
                        }
                    }
                });
                builder.create().show();
                return true;
            }
        });

    }


}