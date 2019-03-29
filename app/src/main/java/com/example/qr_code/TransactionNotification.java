package com.example.qr_code;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TransactionNotification extends AppCompatActivity {

    TextView txtNotification;
    Button btnOK;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_notification);
        this.getSupportActionBar().hide();
        txtNotification=(TextView) findViewById(R.id.txtNotification);
        btnOK=(Button) findViewById(R.id.button3);

        txtNotification.setText("Transaction Successfull!!\n Approval ID: 00000099");

        if (getIntent().getStringExtra("account_id") != null) {
            //txtEmailAddress.setText("Recipient : " + getIntent().getStringExtra("email_address"));
        }

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TransactionNotification.this, ScanBarcodeActivity.class));
            }
        });

    }

}
