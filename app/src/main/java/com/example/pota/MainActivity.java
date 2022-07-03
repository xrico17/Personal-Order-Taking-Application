package com.example.pota;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private Button login;
    private Button MakeOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        login = (Button) findViewById(R.id.btnScanner);
        MakeOrder = (Button) findViewById(R.id.btnMakeOrder);

        MakeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Orders.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {

            public void onClick (View view)
            {
                Intent intent = new Intent(MainActivity.this, QRScanner.class);
                startActivity(intent);
            }
        });
    }
}