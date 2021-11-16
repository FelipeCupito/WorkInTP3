package com.itba.workin.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.itba.workin.App;
import com.itba.workin.MainActivity;
import com.itba.workin.R;
import com.itba.workin.backend.models.Credentials;
import com.itba.workin.databinding.ActivityLoginBinding;
import com.itba.workin.repository.Resource;
import com.itba.workin.repository.Status;
import com.itba.workin.ui.register.Register;
import com.itba.workin.backend.models.Error;


public class LoginActivity extends AppCompatActivity {
    
    private ActivityLoginBinding binding;
    public static final String COMEBACK_URL = "com.itba.workin.COMEBACK_URL";
    SharedPreferences sp;
    Uri uri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        binding.loginBtn.setOnClickListener(this::login);
        binding.textSignIn.setOnClickListener(this::goToRegister);

        sp = getSharedPreferences("login",MODE_PRIVATE);
        if (getIntent().getExtras() == null || getIntent().getExtras().getString(COMEBACK_URL) == null)
            uri = null;
        else
            uri = Uri.parse(getIntent().getExtras().getString(COMEBACK_URL));

        if (sp.getBoolean("logged",false)){
            goToMainActivity();
        }



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

        Credentials credentials = new Credentials(email, pass);
        App app = (App)getApplication();
        app.getUserRepository().login(credentials).observe(this, r -> {
            if (r.getStatus() == Status.SUCCESS) {
                app.getPreferences().setAuthToken(r.getData().getToken());
                sp.edit().putBoolean("logged",true).apply();
                if (uri != null){
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(uri);
                    i.setPackage("com.itba.workin");
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }else {
                    goToMainActivity();
                }
            } else {
                defaultResourceHandler(r);
                if (r.getStatus() == Status.ERROR)
                    Toast.makeText(getApplicationContext(),getText(R.string.invalid_credentials),Toast.LENGTH_LONG).show();
            }
        });

    }

    private void defaultResourceHandler(Resource<?> resource) {
        switch (resource.getStatus()) {
            case LOADING:
                Log.d("LOGIN", "CARGANDO");
                break;
            case ERROR:
                Error error = resource.getError();
                assert error != null;
                Log.d("LOGIN", error.getDescription() + error.getCode() + "");
                break;
        }
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