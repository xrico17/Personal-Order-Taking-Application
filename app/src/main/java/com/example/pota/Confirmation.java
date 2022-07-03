package com.example.pota;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Confirmation extends AppCompatActivity {

    private TextView product;
    private TextView price;
    private TextView TitleBar;
    private EditText total;
    private EditText qty;
    private Button addOrder;
    public ImageView prodImage;
    private ImageButton back;

    public ArrayList<HashMap<String, HashMap<String, String>>> ordersList;
    public HashMap<String, String> item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_confirmation);

        TitleBar = (TextView) findViewById(R.id.txtTitleBar);
        product = (TextView) findViewById(R.id.txtViewProduct);
        price = (TextView) findViewById(R.id.txtViewPrice);
        qty = (EditText) findViewById(R.id.txtQty);
        total = (EditText) findViewById(R.id.txtTotal);
        prodImage = (ImageView) findViewById(R.id.productPic);
        addOrder = (Button) findViewById(R.id.btnAddOrder);
        back = (ImageButton) findViewById(R.id.btnConfirmationBack);

        Bundle extras = getIntent().getExtras();
        ordersList = (ArrayList<HashMap<String, HashMap<String, String>>>) extras.getSerializable("ORDERS_LIST");
        item = (HashMap<String, String>) extras.getSerializable("ITEM");

        int picture = extras.getInt("picture");
        prodImage.setImageResource(picture);
        String itemID = item.get("itemID");
        String itemName = item.get("itemName");
        String itemPrice = item.get("itemPrice");
        String quantity = item.get("quantity");
        Double totalPrice = calculateTotalPrice(Integer.parseInt(quantity), Double.parseDouble(itemPrice));

        // Set initial values of text fields.
        TitleBar.setText(itemName);
        price.setText(itemPrice);
        qty.setText(quantity);
        total.setText(String.valueOf(totalPrice));

        // Add text changed listener to qty field.
        qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              //Empty
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
               //Empty
            }

            @Override
            public void afterTextChanged(Editable s) {
                String quantity = s.toString();
                if (!s.toString().trim().equals("")) {
                    String itemPrice = item.get("itemPrice");
                    Double totalPrice = calculateTotalPrice(Integer.parseInt(quantity), Double.parseDouble(itemPrice));
                    total.setText(String.valueOf(totalPrice));

                } else {
                    total.setText(Double.toString(0.0));
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qty.getText().toString().equals("0") || qty.getText().toString().equals("")) {
                    Toast.makeText(Confirmation.this, "Put quantity", Toast.LENGTH_SHORT).show();
                }
                else {
                    String quantity = qty.getText().toString();
                    item.put("quantity", quantity);
                    item.put("totalPrice", total.getText().toString());

                    int orderCount = ordersList.size() + 1;

                    HashMap<String, HashMap<String, String>> order = new HashMap<String, HashMap<String, String>>();
                    order.put(String.valueOf(orderCount), item);
                    ordersList.add(order);

                    Log.d("itemID", item.get("itemID"));
                    Log.d("itemName", item.get("itemName"));
                    Log.d("itemPrice", item.get("itemPrice"));
                    Log.d("quantity", item.get("quantity"));
                    Log.d("totalPrice", item.get("totalPrice"));

                    Intent intent = new Intent();
                    intent.putExtra("ORDERS_LIST", ordersList);
                    setResult(RESULT_OK, intent);
                    finish();
                    Toast.makeText(Confirmation.this, "Order Added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public Double calculateTotalPrice(int quantity, Double itemPrice) {
        return quantity * itemPrice;
    }
}