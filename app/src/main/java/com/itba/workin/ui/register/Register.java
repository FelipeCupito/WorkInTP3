package com.itba.workin.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.itba.workin.MainActivity;
import com.itba.workin.R;
import com.itba.workin.databinding.ActivityRegisterBinding;
import com.itba.workin.ui.login.LoginActivity;

public class Register extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.registerBtn.setOnClickListener(this::register);
        binding.textSignIn.setOnClickListener(this::goToLogin);
    }

    private void register(View view) {
        EditText nameView = binding.loginName; //cuidado no se porque no se cambia
        String name = nameView.getText().toString();
        EditText lastNameView = binding.registerLastName;
        String lastName = lastNameView.getText().toString();
        EditText emailView = binding.registerEmail;
        String email = emailView.getText().toString();
        EditText passView = binding.registerPassword;
        String pass = passView.getText().toString();
        EditText passConfView = binding.registerRepeatPassword;
        String passConf = passConfView.getText().toString();
        boolean error = false;

        if (name.trim().length() == 0){
            error = true;
            nameView.setError(getText(R.string.invalid_name));
        }
        if (lastName.trim().length() == 0){
            error = true;
            lastNameView.setError(getText(R.string.invalid_last_name));
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.trim().length() == 0){
            error = true;
            emailView.setError(getText(R.string.invalid_email));
        }
        if (pass.trim().length() == 0){
            error = true;
            passView.setError(getText(R.string.invalid_password));
        }
        if (passConf.trim().length() == 0 || !passConf.equals(pass)){
            error = true;
            passConfView.setError(getText(R.string.invalid_password_conf));
        }
        if (error){
            Toast toast=Toast.makeText(getApplicationContext(),getText(R.string.error_params),Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        //TODO: conectar con la API

    }

    private void goToLogin(View view) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
    public void goToMainActivity(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
