package com.example.qr_code;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class MainActivity extends AppCompatActivity {
    Button btndelay,btnNo_delay;
    ImageView imageView;
    EditText edituser,editpass,editdelay;
    public final static int QRcodeWidth = 500 ;
    Bitmap bitmap ;
    Handler handler = new Handler();
    final String exprired = "expired";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edituser = (EditText)findViewById(R.id.edituser);
        editpass = (EditText)findViewById(R.id.editpass);
        editdelay = (EditText)findViewById(R.id.editdelay);
        btndelay = (Button)findViewById(R.id.btn1);
        btnNo_delay = (Button)findViewById(R.id.btn2);
        imageView = (ImageView)findViewById(R.id.imageView);

        String textuser = edituser.getText().toString();
        String textpass = editpass.getText().toString();
        String user = "userName:";
        btndelay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tmp = edituser.getText().toString()+"\n"+"Password: "+editpass.getText().toString();
                final int delay = Integer.parseInt(editdelay.getText().toString());
                try {
                    bitmap = TextToImageEncode(tmp);
                    imageView.setImageBitmap(bitmap);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                bitmap = TextToImageEncode(exprired);
                            } catch (WriterException e) {
                                e.printStackTrace();
                            }
                            imageView.setImageBitmap(bitmap);
                        }
                    },delay);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
        btnNo_delay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tmp = edituser.getText().toString() + "\n" + "Password: " + editpass.getText().toString();
                try {
                    bitmap = TextToImageEncode(tmp);
                    imageView.setImageBitmap(bitmap);

                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.QRCodeBlackColor):getResources().getColor(R.color.QRCodeWhiteColor);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}
