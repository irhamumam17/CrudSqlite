package com.kejarkoding.crudsqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kejarkoding.crudsqlite.adapter.UserModelAdapter;
import com.kejarkoding.crudsqlite.model.Usermodel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Usermodel> usermodelList = new ArrayList<Usermodel>();
    UserModelAdapter adapter;
    LinearLayoutManager layoutManager;
    RecyclerView recyclerView;
    EditText ednm, edtl;
    TextView idn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DatabaseHandler db = DatabaseHandler.getInstance(MainActivity.this);

        ednm = (EditText) findViewById(R.id.nama);
        edtl = (EditText) findViewById(R.id.tinggis);
        idn = (TextView) findViewById(R.id.id_n);
        btntambah = (Button) findViewById(R.id.tambah);
        btnubah = (Button) findViewById(R.id.ubah);
        btnhapus = (Button) findViewById(R.id.hapus);
        String id = idn.getText().toString().trim();

        btntambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nma = ednm.getText().toString().trim();
                String tinggi = edtl.getText().toString().trim();

                if (btntambah.getText().toString() == "TAMBAH") {
                    ednm.setText("");
                    edtl.setText("");
                    idn.setText("");
                    btntambah.setText("SIMPAN");
                    Log.d("tambah", "g");
                } else {

                    if (TextUtils.isEmpty(nma) || TextUtils.isEmpty(tinggi)) {
                        if (TextUtils.isEmpty(nma)) {
                            ednm.setError("Nama Belum diisi !");
                        }
                        if (TextUtils.isEmpty(tinggi) ) {
                            edtl.setError("Tinggi belum diisi !");
                        }

                    } else {
                        Usermodel modelusers = new Usermodel();
                        modelusers.setName(nma);
                        modelusers.setTall(tinggi);

                        db.addRecord(modelusers);
                        btntambah.setText("TAMBAH");
                        ednm.setText("");
                        edtl.setText("");
                        idn.setText("");
                        list();
                        Log.d("simpan", "g");
                    }
                }
            }
        });
        btnubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nma = ednm.getText().toString().trim();
                String tinggi = edtl.getText().toString().trim();
                String ids= idn.getText().toString().trim();
                int idg = Integer.parseInt(ids);
                if (btntambah.getText().toString() == "TAMBAH") {
                    ednm.setText("");
                    edtl.setText("");
                    idn.setText("");
                    btntambah.setText("SIMPAN");
                    Log.d("tambah", "g");
                } else {

                    if (TextUtils.isEmpty(nma) || TextUtils.isEmpty(tinggi)) {
                        if (TextUtils.isEmpty(nma)) {
                            ednm.setError("Nama Belum diisi !");
                        }
                        if (TextUtils.isEmpty(tinggi) ) {
                            edtl.setError("Tinggi belum diisi !");
                        }

                    } else {

                        Usermodel modelusers = new Usermodel();
                        modelusers.setId(idg);
                        modelusers.setName(nma);
                        modelusers.setTall(tinggi);

                        db.updateContact(modelusers);

                        ednm.setText("");
                        edtl.setText("");
                        idn.setText("");
                        list();
                        Log.d("simpan", "g");
                    }
                }

            }
        });
        btnhapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ids= idn.getText().toString().trim();
                int idg = Integer.parseInt(ids);
                Usermodel modelusers = new Usermodel();
                modelusers.setId(idg);

                db.deleteModel(modelusers);
                ednm.setText("");
                edtl.setText("");
                idn.setText("");
                list();
            }
        });

        list();
    }
    private void list() {
        DatabaseHandler db = DatabaseHandler.getInstance(MainActivity.this);
        usermodelList = db.getAllrecord();

        for (Usermodel usermodel : usermodelList) {
            Log.d(" " + usermodel.getId(), usermodel.getName() + " " + usermodel.getTall());
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycles);
        adapter = new UserModelAdapter(getApplicationContext(), usermodelList);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        db.close();
    }


    Button btntambah, btnubah, btnhapus;

    @Override
    protected void onStart() {
        super.onStart();
        Intent i = getIntent();
        idn.setText(i.getStringExtra("id"));
        ednm.setText(i.getStringExtra("nama"));
        edtl.setText(i.getStringExtra("tinggi"));
    }
}
