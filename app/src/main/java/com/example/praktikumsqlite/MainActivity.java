package com.example.praktikumsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<MhsModel> mhsList;
    MhsModel mm;
    DbHelper dbase;
    boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edNama = (EditText) findViewById(R.id.edNama);
        EditText edNim = (EditText) findViewById(R.id.edNim);
        EditText edNoHp = (EditText) findViewById(R.id.edNoHp);
        Button btnSimpan = (Button) findViewById(R.id.btnSimpan);

        mhsList = new ArrayList<>();

        isEdit = false;

        Intent intent_main = getIntent();
        if(intent_main.hasExtra("mhsData")){
            mm = intent_main.getExtras().getParcelable("mhsData");
            edNama.setText(mm.getNama());
            edNim.setText(mm.getNim());
            edNoHp.setText(mm.getNoHp());

            isEdit = true;

            btnSimpan.setBackgroundColor(Color.YELLOW);
            btnSimpan.setText("Edit");
        }

        dbase = new DbHelper(getApplicationContext());

        boolean finalIsEdit = isEdit;
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String isian_nama = edNama.getText().toString();
                String isian_nim = edNim.getText().toString();
                String isian_noHp = edNoHp.getText().toString();

                if(isian_nama.isEmpty() || isian_nim.isEmpty() || isian_noHp.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Isian Masih Kosong", Toast.LENGTH_SHORT).show();
                }else{
                    //mhsList.add(new MhsModel(-1,isian_nama,isian_nim,isian_noHp));

                    boolean stts;
                    if(!isEdit){
                        mm = new MhsModel(-1,isian_nama, isian_nim, isian_noHp);
                        stts = dbase.simpan(mm);
                        edNama.setText("");
                        edNim.setText("");
                        edNoHp.setText("");

                    }else {
                        mm = new MhsModel(mm.getId(),isian_nama, isian_nim, isian_noHp);
                        stts = dbase.ubah(mm);
                    }

                    if(stts){
                        Toast.makeText(getApplicationContext(),"Data Berhasil Disimpan", Toast.LENGTH_SHORT)
                                .show();
                    }else {
                        Toast.makeText(getApplicationContext(),"Data Gagal Disimpan", Toast.LENGTH_SHORT)
                                .show();
                    }

                    //intent_list.putParcelableArrayListExtra("mhsList",mhsList);
                    //startActivity(intent_list);
                }
            }
        });
        Button btnLihat = (Button) findViewById(R.id.btnLihat);
        btnLihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mhsList = dbase.list();

                if(mhsList.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Belum Ada Data", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent_list = new Intent(MainActivity.this, ListMhsActivity.class);
                    intent_list.putParcelableArrayListExtra("mhsList",mhsList);
                    startActivity(intent_list);
                }
            }
        });
    }
}