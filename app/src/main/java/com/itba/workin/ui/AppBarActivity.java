package com.itba.workin.ui;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.itba.workin.R;

public abstract class AppBarActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.app_bar_profile) {
            // launch profile activity
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
}
