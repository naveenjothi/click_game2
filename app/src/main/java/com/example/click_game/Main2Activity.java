package com.example.click_game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.icu.text.Edits;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    DatabaseHelper myDB;
    TextView txview,txtview;
    private String text="";
    public  int count=0,value;
    private int score;
    List<Integer> list;
    List<String>list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        txview=(TextView) findViewById(R.id.TIMER_text);
        txtview=(TextView) findViewById(R.id.textView);
        Button btn=findViewById(R.id.button);
        myDB=new DatabaseHelper(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value=count++;
                txtview.setText(String.valueOf(value));
            }
        });
        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                txview.setText(""+ millisUntilFinished / 1000);
            }

            public void onFinish() {
                checkData();
            }
        }.start();
    }
    private void AddData() {
        boolean isInserted = myDB.insertData(text.toString(),
                count);
        if(isInserted == true)
            Toast.makeText(Main2Activity.this,"Data Inserted",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(Main2Activity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
    }
    private void getText() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Name");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                text = input.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(Main2Activity.this,MainActivity.class);
                Toast.makeText(Main2Activity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                startActivity(intent);
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void checkData() {
        Cursor res = myDB.getAllData();
        if(res.getCount() == 0) {
            // show message
            //showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        StringBuffer buffer1=new StringBuffer();
        StringBuffer buffer2=new StringBuffer();
        while (res.moveToNext()) {
            buffer.append(res.getString(0)+"\n");
            //buffer1.append(res.getString(1)+"\n");
            list= new ArrayList<>();
            list1=new ArrayList<>();
            list1.add(res.getString(1));
            list.add(Integer.parseInt(res.getString(2)));
            for(int i=0;i<list.size();i++){
                if(value>list.get(i)){
                    getText();
                    if(list1.get(i)==text) {
                        UpdateData(text, value);
                        Intent intent=new Intent(Main2Activity.this,MainActivity.class);
                        //Toast.makeText(Main2Activity.this,value,Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    }
                }
            }
        }
    }
    private void UpdateData(String name,int value) {
        boolean isUpdated = myDB.updateData(name,
                value);
        if(isUpdated == true)
            Toast.makeText(Main2Activity.this,"Data Updated",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(Main2Activity.this,"Data not Updated",Toast.LENGTH_LONG).show();
    }
}
