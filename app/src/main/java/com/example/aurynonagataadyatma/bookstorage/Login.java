package com.example.aurynonagataadyatma.bookstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnlogin, btnRegister;
    DataHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = new DataHelper(this);

        edtUsername = (EditText) findViewById(R.id.edt_username);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        btnlogin = (Button) findViewById(R.id.btn_login);

        register();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    String username = edtUsername.getText().toString();
                    String password = edtPassword.getText().toString();

                    User currentUser = dbHelper.Authenticate(new User(null,null, username, null, password));

                    if(currentUser != null){
                        Toast.makeText(Login.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, Home.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(Login.this, "Login Gagal, Silahkan coba lagi", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public boolean validate() {
        boolean valid = true;

        //Get values from EditText fields
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();


        //Handling validation
        if (username.isEmpty() || password.isEmpty()) {
            valid = false;
            Toast.makeText(this, "Silahkan isi Username dan Password", Toast.LENGTH_SHORT).show();
        }


        return valid;
    }

    private void register(){
        btnRegister = (Button) findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }

}