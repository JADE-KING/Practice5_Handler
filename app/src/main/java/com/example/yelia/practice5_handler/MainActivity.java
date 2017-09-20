package com.example.yelia.practice5_handler;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btnStart;
    private TextView textContent;

    private LayoutInflater inflater;
    private View viewDialog;
    private EditText editMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inflater = getLayoutInflater();
        viewDialog = inflater.inflate(R.layout.dialog_layout, null);

        textContent = (TextView)findViewById(R.id.textContent);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                textContent.setText((String)msg.obj);
            }
        };

        final Runnable myWorker = new Runnable() {
            @Override
            public void run() {
                EditText editMessage = (EditText)viewDialog.findViewById(R.id.editMessage);
                Message msg = new Message();
                msg.obj = editMessage.getText().toString();
                handler.sendMessage(msg);
            }
        };

        Button btnStart = (Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(viewDialog).setTitle("Edit Message")
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Thread workThread = new Thread(null, myWorker, "WorkThread");
                                workThread.start();
                            }
                        });
                builder.show();
            }
        });
    }
}
