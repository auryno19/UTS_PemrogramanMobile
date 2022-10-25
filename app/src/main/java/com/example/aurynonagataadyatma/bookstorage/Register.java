package com.example.aurynonagataadyatma.bookstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    EditText edtName, edtUsername, edtEmail, edtPassword;
    Button btnRegister;
    DataHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbHelper = new DataHelper(this);
        edtName = (EditText) findViewById(R.id.edt_name);
        edtUsername = (EditText) findViewById(R.id.edt_username);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        btnRegister = (Button) findViewById(R.id.btn_save);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    String name = edtName.getText().toString();
                    String username = edtUsername.getText().toString();
                    String email = edtEmail.getText().toString();
                    String password = edtPassword.getText().toString();

                    if(!dbHelper.isUserExist(username)){
                        dbHelper.addUser(new User(null, name, username, email, password));
                        Toast.makeText(Register.this, "Registrasi Berhasil! Silahkan Login", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register.this, Login.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(Register.this, "Username telah terdaftar", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    public boolean validate() {
        boolean valid = true;

        //Get values from EditText fields
        String name = edtName.getText().toString();
        String username = edtUsername.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        //Handling validation
        if (name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            valid = false;
            Toast.makeText(this, "Data belum terisi", Toast.LENGTH_SHORT).show();
        }

        return valid;
    }
}