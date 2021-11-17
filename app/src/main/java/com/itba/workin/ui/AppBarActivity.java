package com.itba.workin.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.itba.workin.App;
import com.itba.workin.R;
import com.itba.workin.repository.Resource;
import com.itba.workin.repository.Status;
import com.itba.workin.ui.login.LoginActivity;


public abstract class AppBarActivity extends AppCompatActivity {

    SharedPreferences sp;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.log_out_option) {
            logOut();
            return true;
        } else if (item.getItemId() == R.id.app_bar_share) {
            // share routine
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "jaja");
            intent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(intent, null);
            startActivity(shareIntent);
            return true;
        } else if (item.getItemId() == R.id.app_bar_close) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void goToLogin(){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    public void logOut(){
        App app = (App)this.getApplicationContext();
        app.getUserRepository().logout().observe(this, r -> {
            if (r.getStatus() == Status.SUCCESS) {
                sp = getSharedPreferences("login", MODE_PRIVATE);
                sp.edit().putBoolean("logged",false).apply();
                goToLogin();
            } else {
                Resource.defaultResourceHandler(r);
            }
        });

    }
}
