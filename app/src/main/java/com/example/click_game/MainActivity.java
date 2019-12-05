package com.example.click_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    private Button btn,button;
    private LinearLayout layout;
    private TextView textView;
    private TextView textView1;
    private TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button) findViewById(R.id.button);
        textView=findViewById(R.id.id_text);
        textView1=findViewById(R.id.name_text);
        textView2=findViewById(R.id.score_text);
        layout=(LinearLayout)findViewById(R.id.message_layout);
        myDb = new DatabaseHelper(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });
       viewAll();
    }
    public void viewAll() {
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            // show message
            showMessage("Error","Nothing found");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        StringBuffer buffer1=new StringBuffer();
        StringBuffer buffer2=new StringBuffer();
        while (res.moveToNext()) {
            buffer.append(res.getString(0)+"\n");
            buffer1.append(res.getString(1)+"\n");
            buffer2.append(res.getString(2)+"\n");
        }
    }

    public void showMessage(String title,String Message){
    }
}
