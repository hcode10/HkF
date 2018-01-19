package com.example.hcode10.hkf;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.EnumMap;
import java.util.Hashtable;
import java.util.Map;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class MainActivity extends AppCompatActivity {

    final int cl = 40;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button newQr = (Button)findViewById(R.id.btnNew);

        newQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newQRCode();
                Toast.makeText(MainActivity.this, "C'est prêt :)", Toast.LENGTH_SHORT).show();
            }
        });

        newQRCode();
    }

    void newQRCode(){
        ImageView QRCode = (ImageView)findViewById(R.id.QRCode);
        long id = System.currentTimeMillis() - 1489270000000L;
        try {
            QRCode.setImageBitmap(encodeAsBitmap("WBCB;SERVICE;1;" + id + ";" + cl));
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        Map<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
        hints.put(EncodeHintType.QR_VERSION, 3);
        hints.put(EncodeHintType.MARGIN, 0);
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, 500, 500, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 500, 0, 0, w, h);
        return bitmap;
    }
}
