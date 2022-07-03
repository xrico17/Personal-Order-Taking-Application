package com.example.pota;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class QRGenerator extends AppCompatActivity {

    private TextView prodName;
    private ImageView QRpic;
    private TextView List;
    private ImageButton back;
    private Button show;
    private Button Save;

    public ArrayList<HashMap<String, HashMap<String, String>>> ordersList = new ArrayList<HashMap<String, HashMap<String, String>>>();

    StringBuffer formattedOrderList = new StringBuffer("");
    //OutputStream outputStream = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_q_r_generator);

        prodName = (TextView) findViewById(R.id.txtViewProduct);
        QRpic = (ImageView) findViewById(R.id.imgQr);
        List = (TextView) findViewById(R.id.txtShowList);
        show = (Button) findViewById(R.id.btnGenerateQR);
        back = (ImageButton) findViewById(R.id.btnQRGenBack);
        Save = (Button) findViewById(R.id.btnSave);

        ActivityCompat.requestPermissions(QRGenerator.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        ActivityCompat.requestPermissions(QRGenerator.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        List.setKeyListener(null);
        Bundle extras = getIntent().getExtras();
        ordersList = (ArrayList<HashMap<String, HashMap<String, String>>>) extras.getSerializable("ORDERS_LIST");

        Double totalPrice = 0.0;

        for (int i = 0; i < ordersList.size(); i++) {
            StringBuffer formattedText = new StringBuffer("");

            String orderID = String.valueOf(i + 1);
            HashMap<String, HashMap<String, String>> order = ordersList.get(i);
            HashMap<String, String> item = order.get(orderID);

            formattedText.append("\n Item ID: ");
            formattedText.append(item.get("itemID"));
            formattedText.append("\n Item Name: ");
            formattedText.append(item.get("itemName"));
            formattedText.append("\n Unit Price: ");
            formattedText.append(item.get("itemPrice"));
            formattedText.append("\n Quantity: ");
            formattedText.append(item.get("quantity"));
            formattedText.append("\n Total: ");
            formattedText.append(item.get("totalPrice"));
            formattedText.append("\n");

            totalPrice = totalPrice + Double.parseDouble(item.get("totalPrice"));

            // Append Total Price of all items on end of loop.
            if (i == (ordersList.size() - 1)) {
                formattedText.append("\nTotal Price (ALL): ");
                formattedText.append(totalPrice.toString());
                formattedText.append("\n");
            }
            formattedOrderList.append(formattedText);
        }

        // Note: Should always be outside loop
        List.setText(formattedOrderList.toString());

        AlertDialog.Builder MakeNewOrder = new AlertDialog.Builder(QRGenerator.this);
        MakeNewOrder.setMessage("Order is Saved to Sweet Avenue folder" + "\n"
        + "Would you like to make a new order?");

        MakeNewOrder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(QRGenerator.this, Orders.class);
                startActivity(intent);
            }
        });
        MakeNewOrder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(QRGenerator.this, MainActivity.class);
                startActivity(intent);
            }
        });

      /*  AlertDialog.Builder alertDialog = new AlertDialog.Builder(QRGenerator.this);
        alertDialog.setMessage("Orders made will be reset" + "\n"
        + "Are you sure you want to go back?");

        alertDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
               // Intent intent = new Intent(QRGenerator.this, Orders.class);
              //  startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }); */

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
           //     alertDialog.show();
               /* Intent intent = new Intent(QRGenerator.this, Orders.class);
                startActivity(intent); */
            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                /*BitmapDrawable drawable = (BitmapDrawable) QRpic.getDrawable();
                Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                file = new File(Environment.getExternalStorageDirectory()+"/.png");
                try {
                    file.createNewFile();
                    fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(byteArrayOutputStream.toByteArray());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(QRGenerator.this, "QR Saved", Toast.LENGTH_SHORT).show();*/

                OutputStream outputStream = null;

                BitmapDrawable drawable = (BitmapDrawable) QRpic.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                File filepath = Environment.getExternalStorageDirectory();
                File dir = new File(filepath.getAbsoluteFile()+"/SweetAve/");
                dir.mkdir();

                String fileName = String.format("%d.png", System.currentTimeMillis());
                File outfile = new File(dir, fileName);
                try {
                    outputStream = new FileOutputStream(outfile);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                try {
                    outputStream.flush();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    outputStream.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                Toast.makeText(QRGenerator.this, "QR Saved", Toast.LENGTH_SHORT).show();
                MakeNewOrder.show();
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(List.getText().toString().equals("")) {
                    Toast.makeText(QRGenerator.this, "Create Orders First", Toast.LENGTH_SHORT).show();
                }
                else{
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    try {
                        BitMatrix bitMatrix = multiFormatWriter.encode(List.getText().toString(), BarcodeFormat.QR_CODE, 500, 500);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        
                        QRpic.setImageBitmap(bitmap);
                        Save.setEnabled(true);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}