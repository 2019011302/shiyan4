package com.example.jigsaw;


import androidx.appcompat.app.AppCompatActivity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionStart();
    }
    public void actionStart(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //获取关卡模式
        String id = getIntent().getStringExtra("id");
        switch (id){
            case "simple"://显示简单模式
                SimpleFragment simpleFragment = new SimpleFragment();
                transaction.replace(R.id.fragment_layout, simpleFragment);
                this.setTitle("简单模式");
                break;
            case "medium":
                MediumFragment mediumFragment = new MediumFragment();
                transaction.replace(R.id.fragment_layout, mediumFragment);
                this.setTitle("一般模式");
                break;
            case "difficult":
                DifficultFragment difficultFragment = new DifficultFragment();
                transaction.replace(R.id.fragment_layout, difficultFragment);
                this.setTitle("困难模式");
                break;
        }
        transaction.commit();
    }
}

