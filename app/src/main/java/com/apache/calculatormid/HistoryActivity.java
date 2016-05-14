package com.apache.calculatormid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class HistoryActivity extends Activity {


    TextView message;
    Button btnClearHistory;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        message = (TextView) findViewById(R.id.etmsg);
        btnClearHistory = (Button) findViewById(R.id.btnClearHistory);


        File path = getExternalFilesDir(null);

        final File file = new File(path, "calculationHistory.txt");

        btnClearHistory.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                PrintWriter pw = null;
                try {
                    pw = new PrintWriter(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                pw.close();

                onCreate(savedInstanceState);
            }
        });

        int length = (int) file.length();

        byte[] bytes = new byte[length];

        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            try {
                in.read(bytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        String contents = new String(bytes);
        message.setText(contents);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}

