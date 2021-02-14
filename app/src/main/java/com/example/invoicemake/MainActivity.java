package com.example.invoicemake;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button btn;
    EditText name,price,quantity,total;
    String sname,sprice,squantity,stotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn =findViewById(R.id.IdButton);

        name =findViewById(R.id.IdName);
        price =findViewById(R.id.IdPrice);
        quantity =findViewById(R.id.IdQuantity);
        total =findViewById(R.id.IdTotal);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sname = name.getText().toString().trim();
                sprice = price.getText().toString().trim();
                stotal = total.getText().toString().trim();
                squantity = quantity.getText().toString().trim();


                Start();
            }
        });




    }

    private void Start() {

        Dexter.withContext(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {

                        Work();

                    }
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {

                        Toast.makeText(MainActivity.this, "Not Working ", Toast.LENGTH_SHORT).show();

                    }
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                }).check();
    }

    private void Work() {




        String mFileName = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());

        String mFilePath = Environment.getExternalStorageDirectory()+"/"+mFileName+".pdf";


        try {

            Document document = new Document(PageSize.A4);

            PdfWriter.getInstance(document,new FileOutputStream(mFilePath));

            document.open();

            Paragraph intro = new Paragraph("ROBO TECH VALLEY ");
            intro.setAlignment(Element.ALIGN_CENTER);
            Paragraph space = new Paragraph("\n");

//            PdfPTable table = new PdfPTable(4);

            PdfPTable table = new PdfPTable(new float[]{6,2,2,4});

            table.setWidthPercentage(100);

            PdfPCell c1 = new PdfPCell(new Paragraph("Product Name"));
            PdfPCell c2 = new PdfPCell(new Paragraph("Per price"));
            PdfPCell c3 = new PdfPCell(new Paragraph("Quantity"));
            PdfPCell c4 = new PdfPCell(new Paragraph("Total"));

            c1.setBackgroundColor(BaseColor.GRAY);
            c2.setBackgroundColor(BaseColor.GRAY);
            c3.setBackgroundColor(BaseColor.GRAY);
            c4.setBackgroundColor(BaseColor.ORANGE);


            table.addCell(c1);
            table.addCell(c2);
            table.addCell(c3);
            table.addCell(c4);

            c1 = new PdfPCell(new Paragraph("Robot"));
            c2 = new PdfPCell(new Paragraph("150"));
            c3 = new PdfPCell(new Paragraph("2"));
            c4 = new PdfPCell(new Paragraph("300"));

            table.addCell(c1);
            table.addCell(c2);
            table.addCell(c3);
            table.addCell(c4);


            document.add(intro);
            document.add(space);
            document.add(table);

            document.close();



            Toast.makeText(this, ""+mFilePath+"is done", Toast.LENGTH_SHORT).show();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}