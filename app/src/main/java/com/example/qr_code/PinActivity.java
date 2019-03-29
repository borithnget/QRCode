package com.example.qr_code;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.kevalpatel.passcodeview.PinView;
import com.kevalpatel.passcodeview.authenticator.PasscodeViewPinAuthenticator;
import com.kevalpatel.passcodeview.indicators.CircleIndicator;
import com.kevalpatel.passcodeview.interfaces.AuthenticationListener;
import com.kevalpatel.passcodeview.keys.KeyNamesBuilder;
import com.kevalpatel.passcodeview.keys.RoundKey;

public class PinActivity extends AppCompatActivity {
    private static final String ARG_CURRENT_PIN = "current_pin";

    private PinView mPinView;

    private String accountId,accountPasscode,transactionAmount;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code);
        this.getSupportActionBar().hide();

        accountId=getIntent().getStringExtra("account_id");
        accountPasscode=getIntent().getStringExtra("account_password");
        transactionAmount=getIntent().getStringExtra("transaction_amount");

        mPinView = findViewById(R.id.pattern_view);
        //Set the correct pin code.
        //REQUIRED
        //final int[] correctPattern = new int[]{1, 2, 3, 4};
        final int[] correctPattern =this.convertStringtoIntArray(accountPasscode);
        mPinView.setPinAuthenticator(new PasscodeViewPinAuthenticator(correctPattern));

        //Build the desired key shape and pass the theme parameters.
        //REQUIRED
        mPinView.setKey(new RoundKey.Builder(mPinView)
                .setKeyPadding(R.dimen.key_padding)
                .setKeyStrokeColorResource(R.color.colorAccent)
                .setKeyStrokeWidth(R.dimen.key_stroke_width)
                .setKeyTextColorResource(R.color.colorAccent)
                .setKeyTextSize(R.dimen.key_text_size));

        //Build the desired indicator shape and pass the theme attributes.
        //REQUIRED
        mPinView.setIndicator(new CircleIndicator.Builder(mPinView)
                .setIndicatorRadius(R.dimen.indicator_radius)
                .setIndicatorFilledColorResource(R.color.colorAccent)
                .setIndicatorStrokeColorResource(R.color.colorAccent)
                .setIndicatorStrokeWidth(R.dimen.indicator_stroke_width));

        mPinView.setPinLength(PinView.DYNAMIC_PIN_LENGTH);

        //Set the name of the keys based on your locale.
        //OPTIONAL. If not passed key names will be displayed based on english locale.
        mPinView.setKeyNames(new KeyNamesBuilder()
                .setKeyOne(this, R.string.key_1)
                .setKeyTwo(this, R.string.key_2)
                .setKeyThree(this, R.string.key_3)
                .setKeyFour(this, R.string.key_4)
                .setKeyFive(this, R.string.key_5)
                .setKeySix(this, R.string.key_6)
                .setKeySeven(this, R.string.key_7)
                .setKeyEight(this, R.string.key_8)
                .setKeyNine(this, R.string.key_9)
                .setKeyZero(this, R.string.key_0));

        mPinView.setTitle("Enter the PIN");

        mPinView.setAuthenticationListener(new AuthenticationListener() {
            @Override
            public void onAuthenticationSuccessful() {
                //User authenticated successfully.
                //Navigate to secure screens.
                Intent intent=new Intent(PinActivity.this,TransactionNotification.class);
                intent.putExtra("account_id",accountId);
                intent.putExtra("transaction_amount",transactionAmount);
                startActivity(intent);
                //startActivity(new Intent(PinActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                //Calls whenever authentication is failed or user is unauthorized.
                //Do something
            }
        });

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putIntArray(ARG_CURRENT_PIN, mPinView.getCurrentTypedPin());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPinView.setCurrentTypedPin(savedInstanceState.getIntArray(ARG_CURRENT_PIN));
    }

    private int[] convertStringtoIntArray(String passcode){
        int[] intArray = new int[passcode.length()];
        char [] asChar = passcode.toCharArray();
        int i=0;
        for (char c : asChar) {
            intArray[i++] = Character.digit(c,10);
        }
        return intArray;
    }
}
