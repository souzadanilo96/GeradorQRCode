package br.com.geradorqrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class MainActivity extends AppCompatActivity {

    EditText editQRCode;
    Button btnGerarQRCode;
    ImageView imgQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponentes();

        btnGerarQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(editQRCode.getText().toString())) {
                    editQRCode.setError("*");
                    editQRCode.requestFocus();
                } else {
                    gerarQRCode(editQRCode.getText().toString());
                }
            }
        });
    }

    private void initComponentes() {

        editQRCode = findViewById(R.id.editQRCode);
        btnGerarQRCode = findViewById(R.id.btnGerarQRCode);
        imgQRCode = findViewById(R.id.imgQRCode);

    }


    private void gerarQRCode(String conteudoDoQRCode) {
        // zxing-android-embedded

        QRCodeWriter qrCode = new QRCodeWriter();

        try {

            BitMatrix bitMatrix = qrCode.encode(conteudoDoQRCode, BarcodeFormat.QR_CODE, 196, 196);

            int tamanho = bitMatrix.getHeight();
            int altura = bitMatrix.getWidth();

            Bitmap bitmap = Bitmap.createBitmap(tamanho,altura,Bitmap.Config.RGB_565);

            for (int x = 0; x < tamanho; x++) {

                for (int y = 0; y < altura; y++) {

                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.RED : Color.WHITE);
                    
                }
                
            }

            imgQRCode.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }

    }
}