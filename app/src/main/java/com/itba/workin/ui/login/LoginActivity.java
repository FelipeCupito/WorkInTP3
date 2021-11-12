package com.itba.workin.ui.login;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.itba.workin.MainActivity;
import com.itba.workin.R;
import com.itba.workin.databinding.ActivityLoginBinding;
import com.itba.workin.ui.register.Register;

public class LoginActivity extends AppCompatActivity {
    
    private ActivityLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        binding.loginBtn.setOnClickListener(this::login);
        binding.textSignIn.setOnClickListener(this::goToRegister);

    }

    private void login(View view) {
        EditText emailView = binding.loginName;
        String email = emailView.getText().toString();
        EditText passView = binding.loginPassword;
        String pass = passView.getText().toString();
        boolean error = false;

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.trim().length() == 0){
            error = true;
            emailView.setError(getText(R.string.invalid_email));
        }

        if (pass.trim().length() == 0){
            error = true;
            passView.setError(getText(R.string.invalid_password));
        }
        if (error){
            Toast toast=Toast.makeText(getApplicationContext(),getText(R.string.error_params),Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        //TODO: conectar con la api

    }

    private void goToRegister(View view) {
        Intent i = new Intent(this, Register.class);
        startActivity(i);
        finish();
    }
    public void goToMainActivity(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }



}