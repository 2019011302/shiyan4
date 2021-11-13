package com.example.jigsaw;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn1:
                Intent intent = new Intent(MenuActivity.this,
                        ContentActivity.class);
                intent.putExtra("id" , "simple");
                startActivity(intent);
                break;
            case R.id.btn2:
                Intent intent1 = new Intent(MenuActivity.this,
                        ContentActivity.class);
                intent1.putExtra("id" , "medium");
                startActivity(intent1);
                break;
            case R.id.btn3:
                Intent intent2 = new Intent(MenuActivity.this,
                        ContentActivity.class);
                intent2.putExtra("id" , "difficult");
                startActivity(intent2);
                break;
            case R.id.btn4:
                //显示历史记录
                showRecord();
        }
    }

    public void showRecord(){
        //读出数据
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        //
        int simpleTime = pref.getInt("simple", -1);
        int mediumTime = pref.getInt("medium", -1);
        int difficultTime = pref.getInt("difficult", -1);
        String simple = "简单模式：";
        String medium = "一般模式：";
        String difficult = "困难模式：";
        if(simpleTime > 0) {
            simple += String.valueOf(simpleTime) + "秒" ;
        }
        if(mediumTime > 0){
            medium += String.valueOf(mediumTime) + "秒";
        }
        if(difficultTime > 0){
            difficult += String.valueOf(difficultTime)+ "秒";
        }
        //弹出对话框显示历史记录
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(simple + "\n" + medium + "\n" + difficult+ "\n");
        builder.setPositiveButton("确认" , null);
        builder.setTitle("历史记录");
        builder.create().show();
    }
}