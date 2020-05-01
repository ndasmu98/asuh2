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

public class RegisterActivity extends AppCompatActivity {

    private TextView txtMasuk;
    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtRePassword;
    private  EditText edtPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        declareView();
        txtMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasiRegister();
            }
        });
    }

    private void declareView(){
        txtMasuk = findViewById(R.id.txt_reg_masuk);
        edtUsername = findViewById(R.id.edt_reg_username);
        edtPassword = findViewById(R.id.edt_reg_password);
        edtRePassword = findViewById(R.id.edt_reg_password_confirm);
        edtPhoneNumber = findViewById(R.id.edt_reg_phone);
    }

    private void validasiRegister(){

        //Reset error dan fokus menjadi default
        edtUsername.setError(null);
        edtPassword.setError(null);
        edtRePassword.setError(null);
        View fokus = null;
        boolean cancel = false;

        //set input value dari view
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        String rePassword = edtRePassword.getText().toString();
        String phoneNumber = edtPhoneNumber.getText().toString();

        // Jika form user kosong atau memenuhi kriteria di Method cekUser() maka, Set error di Form-
        // User dengan menset variable fokus dan error di Viewnya juga cancel menjadi true*/
        if (TextUtils.isEmpty(username)){
            edtUsername.setError("Username Tidak Boleh Kosong");
            fokus = edtUsername;
            cancel = true;
        }else if (cekUser(username)){
            edtUsername.setError("Username Sudah Terdaftar");
            fokus = edtUsername;
            cancel = true;
        }

        // Jika form password kosong dan memenuhi kriteria di Method cekPassword maka,
        // Reaksinya sama dengan percabangan User di atas. Bedanya untuk Password dan Repassword*/
        if (TextUtils.isEmpty(password)){
            edtPassword.setError("Password Tidak Boleh Kosong");
            fokus = edtPassword;
            cancel = true;
        }else if (!cekPassword(password, rePassword)){
            edtPassword.setError("Password Yang Dimasukkan Tidak Sesuai");
            fokus = edtPassword;
            cancel = true;
        }

        // Jika cancel true, variable fokus mendapatkan fokus. Jika false, maka
        // Kembali ke LoginActivity dan Set User dan Password untuk data yang terdaftar */
        if (cancel){
            fokus.requestFocus();
        }else{
            //Deklarasi model
            UserModel userModel = new UserModel();
            userModel.setUsername(username);
            userModel.setPassword(password);
            userModel.setPhone(phoneNumber);

            //Simpan data ke shared preferences
            Preferences.setUserPreferences(getBaseContext(), userModel);
            Preferences.setLoggedInStatus(getBaseContext(), true);

            //Pindah halaman ke home
            startActivity(new Intent(getBaseContext(), HomeActivity.class));
            finish();
        }
    }

    // True jika parameter user sama dengan data user yang terdaftar dari Preferences */
    private boolean cekUser(String user){
        return user.equals(Preferences.getRegisteredUsername(getBaseContext()));
    }

    // True jika parameter password sama dengan parameter repassword */
    private boolean cekPassword(String password, String rePassword){
        return password.equals(rePassword);
    }
}
