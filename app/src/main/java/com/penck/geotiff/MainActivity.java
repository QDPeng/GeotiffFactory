package com.penck.geotiff;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.beyka.tiffbitmapfactory.IProgressListener;
import org.beyka.tiffbitmapfactory.TiffBitmapFactory;
import org.beyka.tiffbitmapfactory.TiffConverter;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    String path = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image_show);

    }

    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.show_tiff:
                path = Environment.getExternalStorageDirectory() + "/tiff/礼县.tif";
                imageView.setImageBitmap(TiffBitmapFactory.decodeFile(new File(path)));
                break;
            case R.id.tiff_to_png:
                String pngPath = Environment.getExternalStorageDirectory() + "/tiff/礼县.png";
                TiffConverter.convertTiffPng(path, pngPath, new TiffConverter.ConverterOptions(), new IProgressListener() {
                    @Override
                    public void reportProgress(long processedPixels, long totalPixels) {
                        Log.i("nfpp", processedPixels + "/" + totalPixels);
                    }
                });
                break;
        }
    }


}
