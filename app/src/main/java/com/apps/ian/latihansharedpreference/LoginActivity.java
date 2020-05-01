package com.apps.ian.latihansharedpreference;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.apps.ian.R;
import com.apps.ian.com.apps.ian.latihansharedpreference.model.UserModel;
import com.apps.ian.utils.Preferences;

/*
    Developed by Ian Herdiansyah - 10117194 - IF5
    on 01-05-2020
 */

public class LoginActivity extends AppCompatActivity {

    private TextView txtMasuk;
    private TextView txtRegister;
    private EditText edtUsername;
    private EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        declareView();

        txtMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasiLogin();
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), RegisterActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Preferences.getLoggedInStatus(getBaseContext())) {
            startActivity(new Intent(getBaseContext(), HomeActivity.class));
            finish();
        }
    }

    private void declareView(){
        txtMasuk = findViewById(R.id.txt_login_masuk);
        txtRegister = findViewById(R.id.txt_login_register);
        edtUsername = findViewById(R.id.edt_login_username);
        edtPassword = findViewById(R.id.edt_login_password);
    }

    private void validasiLogin(){
        //reset semua error dan fokus menjadi default
        edtUsername.setError(null);
        edtPassword.setError(null);
        View fokus = null;
        boolean cancel = false;

        //set input value dari view
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        /* Jika form user kosong atau memenuhi kriteria di Method cekUser() maka, Set error di Form-
        User dengan menset variable fokus dan error di Viewnya juga cancel menjadi true*/
        if (TextUtils.isEmpty(username)){
            edtUsername.setError("Username Belum diisi");
            fokus = edtUsername;
            cancel = true;
        }else if (!cekUser(username)){
            edtUsername.setError("Username Tidak Ditemukan");
        }

        if (TextUtils.isEmpty(password)){
            edtPassword.setError("Password Belum Diisi");
            fokus = edtPassword;
            cancel = true;
        }else if (!cekPassword(password)){
            edtPassword.setError("Password Tidak sesuai");
            fokus = edtPassword;
            cancel = true;
        }

        /* Jika cancel true, maka variabel fokus mendapatkan fokus. Jika false, maka kembali
           ke LoginActivity dan set User dan Password untuk data yang terdaftar */
        if (cancel){
            fokus.requestFocus();
        }else{
            //Deklarasi Model
            UserModel userModel = new UserModel();
            userModel.setUsername(username);
            userModel.setPassword(password);

            //Simpan data ke shared preferences
            Preferences.setUserPreferences(getBaseContext(), userModel);
            Preferences.setLoggedInStatus(getBaseContext(), true);

            //Pindah halaman ke home
            startActivity(new Intent(getBaseContext(), HomeActivity.class));
            finish();
        }
    }

    private boolean cekUser(String user){
        return user.equals(Preferences.getRegisteredUsername(getBaseContext()));
    }

    private boolean cekPassword(String password){
        return password.equals(Preferences.getRegisteredPassword(getBaseContext()));
    }
}
