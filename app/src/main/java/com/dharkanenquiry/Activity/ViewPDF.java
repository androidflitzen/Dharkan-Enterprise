package com.dharkanenquiry.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.dharkanenquiry.vasudhaenquiry.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;

public class ViewPDF extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {

    private ProgressDialog progressDialog;
    PDFView pdfView;
    String url1;
    Integer pageNumber = 0;
    File myFile;
    ImageView ivBackEnquiry,ivShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_p_d_f);

         //url1 = getIntent().getStringExtra("url").toString();
        myFile = (File)getIntent().getSerializableExtra("MY_FILE");
        System.out.println("=======url  " + url1);
        pdfView = (PDFView) findViewById(R.id.pdfView);
        ivBackEnquiry = findViewById(R.id.ivBackEnquiry);
        ivShare = findViewById(R.id.ivShare);

        ivBackEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                Uri screenshotUri = FileProvider.getUriForFile(ViewPDF.this, "com.dharkanenterprise", myFile);
                //Uri screenshotUri = Uri.parse(myFile.getAbsolutePath());
                sharingIntent.setType("*/*");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });


        displayFromSdcard();
    }

    private void displayFromSdcard() {
        pdfView.fromFile(myFile)
                .defaultPage(pageNumber)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", "", page + 1, pageCount));
    }


    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
       // printBookmarksTree(pdfView.getTableOfContents(), "-");

    }

}