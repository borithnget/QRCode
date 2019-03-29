package com.example.qr_code;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TransferTransactionActivity extends AppCompatActivity {

    TextView txtEmailAddress;
    EditText txtTransactionAmount;
    Button btnTransfer;

    private String accountID,accountPassword;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_transaction);

        txtEmailAddress = findViewById(R.id.textView4);
        txtTransactionAmount=findViewById(R.id.editText);
        btnTransfer=findViewById(R.id.button2);

        //txtEmailAddress.setText("Account : Acc2019909090");
        /*
        if (getIntent().getStringExtra("email_address") != null) {
            accountID=getIntent().getStringExtra("email_address");
        }
        */
        accountID="Acc090909\n999999";

        String[] splitStr=accountID.split("\n");
        txtEmailAddress.setText("Account ID : " + splitStr[0]);
        accountPassword=splitStr[1];

        btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtTransactionAmount.length()>0){
                    Intent intent= new Intent(TransferTransactionActivity.this,PinActivity.class);
                    intent.putExtra("account_id",txtEmailAddress.getText());
                    intent.putExtra("transaction_amount",txtTransactionAmount.getText());
                    intent.putExtra("account_password",accountPassword);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

}
