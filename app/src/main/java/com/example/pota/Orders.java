package com.example.pota;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.textclassifier.ConversationActions;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Orders extends AppCompatActivity {

  //  private ImageView imageView;
    private ImageButton Milktea1;
    private ImageButton Milktea2;
    private ImageButton Milktea3;
    private ImageButton Milktea4;
    private ImageButton Milktea5;
    private ImageButton Milktea6;
    private ImageButton list;
    private ImageButton back;
    private Button confirm;
    private Button delete;


    public ArrayList<HashMap<String, HashMap<String, String>>> ordersList = new ArrayList<HashMap<String, HashMap<String, String>>>();
    StringBuffer formattedOrderList = new StringBuffer("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_orders);

        back = (ImageButton) findViewById(R.id.btnOrdersBack);
        Milktea1 = (ImageButton) findViewById(R.id.imgMilktea1);
        Milktea2 = (ImageButton) findViewById(R.id.imgMilktea2);
        Milktea3 = (ImageButton) findViewById(R.id.imgMilktea3);
        Milktea4 = (ImageButton) findViewById(R.id.imgMilktea4);
        Milktea5 = (ImageButton) findViewById(R.id.imgMilktea5);
        Milktea6 = (ImageButton) findViewById(R.id.imgMilktea6);
        delete = (Button) findViewById(R.id.btnDeleteOrder);
        confirm = (Button) findViewById(R.id.btnConfirmOrders);


        AlertDialog.Builder Message = new AlertDialog.Builder(Orders.this);

        Message.setMessage("All orders will be canceled, go back?");

        Message.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Orders.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Message.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //nothing
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message.show();
            }
        });

        Milktea1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> item = new HashMap<String, String>();

                item.put("itemID", "1");
                item.put("itemName", "Dark Chocolate");
                item.put("itemPrice", "95");
                item.put("quantity", "1");

                Intent intent = new Intent(Orders.this, Confirmation.class);
                intent.putExtra("picture", R.drawable.milktea1);
                intent.putExtra("ORDERS_LIST", ordersList);
                intent.putExtra("ITEM", item);
                startActivityForResult(intent, 1);
            }
        });

        Milktea2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> item = new HashMap<String, String>();
                item.put("itemID", "2");
                item.put("itemName", "Taro Cream Cheese");
                item.put("itemPrice", "95");
                item.put("quantity", "1");

                Intent intent = new Intent(Orders.this, Confirmation.class);
                intent.putExtra("picture", R.drawable.milktea2);
                intent.putExtra("ORDERS_LIST", ordersList);
                intent.putExtra("ITEM", item);
                startActivityForResult(intent, 1);
            }
        });

        Milktea3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> item = new HashMap<String, String>();
                item.put("itemID", "3");
                item.put("itemName", "Okinawa");
                item.put("itemPrice", "115");
                item.put("quantity", "1");

                Intent intent = new Intent(Orders.this, Confirmation.class);
                intent.putExtra("picture", R.drawable.milktea3);
                intent.putExtra("ORDERS_LIST", ordersList);
                intent.putExtra("ITEM", item);
                startActivityForResult(intent, 1);
            }
        });

        Milktea4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> item = new HashMap<String, String>();
                item.put("itemID", "4");
                item.put("itemName", "Matcha");
                item.put("itemPrice", "110");
                item.put("quantity", "1");

                Intent intent = new Intent(Orders.this, Confirmation.class);
                intent.putExtra("picture", R.drawable.milktea4);
                intent.putExtra("ORDERS_LIST", ordersList);
                intent.putExtra("ITEM", item);
                startActivityForResult(intent, 1);
            }
        });

        Milktea5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> item = new HashMap<String, String>();
                item.put("itemID", "5");
                item.put("itemName", "Cookies & Cream Cream Cheese");
                item.put("itemPrice", "100");
                item.put("quantity", "1");

                Intent intent = new Intent(Orders.this, Confirmation.class);
                intent.putExtra("picture", R.drawable.milktea5);
                intent.putExtra("ORDERS_LIST", ordersList);
                intent.putExtra("ITEM", item);
                startActivityForResult(intent, 1);
            }
        });

        Milktea6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> item = new HashMap<String, String>();
                item.put("itemID", "6");
                item.put("itemName", "Chocolate Cream Cheese");
                item.put("itemPrice", "120");
                item.put("quantity", "1");

                Intent intent = new Intent(Orders.this, Confirmation.class);
                intent.putExtra("picture", R.drawable.milktea6);
                intent.putExtra("ORDERS_LIST", ordersList);
                intent.putExtra("ITEM", item);
                startActivityForResult(intent, 1);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ordersList.isEmpty()) {
                    Toast.makeText(Orders.this, "Make Order First", Toast.LENGTH_SHORT).show();
                }
                else {
                    for (int i = 0; i < ordersList.size(); i++) {
                        String orderID = String.valueOf(i + 1);
                        HashMap<String, HashMap<String, String>> order = ordersList.get(i);
                        HashMap<String, String> item = order.get(orderID);

                        Log.d("orderID", orderID);
                        Log.d("itemID", item.get("itemID"));
                        Log.d("itemName", item.get("itemName"));
                        Log.d("itemPrice", item.get("itemPrice"));
                        Log.d("quantity", item.get("quantity"));
                        Log.d("totalPrice", item.get("totalPrice"));
                        Log.d("ordersList.size()", String.valueOf(ordersList.size()));
                    }
                    Intent intent = new Intent(Orders.this, QRGenerator.class);
                    intent.putExtra("ORDERS_LIST", ordersList);
                    startActivity(intent);
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if (ordersList.size() == 0)
                {
                    Toast.makeText(Orders.this, "No Orders Found", Toast.LENGTH_SHORT).show();
                }
                else {
                    ordersList.remove(ordersList.get(ordersList.size() - 1));
                    Toast.makeText(Orders.this, "Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                ordersList = (ArrayList<HashMap<String, HashMap<String, String>>>) data.getSerializableExtra("ORDERS_LIST");
            }
        }
    }
}