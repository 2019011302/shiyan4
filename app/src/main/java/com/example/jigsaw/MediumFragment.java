package com.example.jigsaw;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import android.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MediumFragment extends Fragment {
    private TextView tv ;
    private ImageView image01,image02,image03,image04,
            image05,image06,image07,image08,
            image09,image10,image11,image12,
            image13,image14,image15,image16;
    private ImageView[] imageV ;
    private ImageView blankImage ;
    private Button reStart ;
    private Button tip;
    private int time = 0 ;
    private int blankPosition = 15;//空白图片的位置 第一个位置下标为0 ，16张图片， 最后一个位置下标为15
    private int blankImageId = R.id.image15;//空白图片的id;
    private int[] imageD={
            R.drawable.medium_01 , R.drawable.medium_02 , R.drawable.medium_03, R.drawable.medium_04 ,
            R.drawable.medium_05 , R.drawable.medium_06, R.drawable.medium_07 , R.drawable.medium_08 ,
            R.drawable.medium_09, R.drawable.medium_10, R.drawable.medium_11 , R.drawable.medium_12 ,
            R.drawable.medium_13, R.drawable.medium_14, R.drawable.medium_15 , R.drawable.medium_16 ,
    };
    private int[] imageId={
            R.id.image01 , R.id.image02 , R.id.image03, R.id.image04 , R.id.image05 , R.id.image06,
            R.id.image07 , R.id.image08 , R.id.image09, R.id.image10 , R.id.image11 , R.id.image12,
            R.id.image13 , R.id.image14 , R.id.image15, R.id.image16
    };
    //private int[] imageIndex = new int[image.length];
   myArray myarray = new myArray(4 ) ;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == 1){
                //tv = (TextView) getActivity().findViewById(R.id.tv);
                time++;
                tv.setText("时间 : "+ time +"秒");
                handler.sendEmptyMessageDelayed(1 , 1000);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_medium, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv = (TextView) getActivity().findViewById(R.id.tv);
        blankImageId = R.drawable.difficult_36;
        handler.sendEmptyMessageDelayed(1 , 1000);
        myarray.initArray();
        //初始化控件
        initImage();
        //打乱图片顺序
        disturImage();
    }

    private void disturImage() {
        myarray.disturb();//打乱数组
        int[] imageIndex = myarray.getImageIndex();
        //赋值
        for(int i =0 ; i < imageD.length ; i++){
            imageV[i].setImageResource(imageD[imageIndex[i]]);
        }
    }
    //处理点击后是否交换问题
    public void swapImage(int imageId , int clickPosition){
        //如果满足交换条件则设置图片
        if(myarray.isChange(clickPosition, blankPosition)){
            //获取被点击图片,设置成不可见，即空白
            ImageView onClickImage = (ImageView) getActivity().findViewById(imageId);
            // blankImageId = R.id.image09;
            onClickImage.setVisibility(View.INVISIBLE);
            //获取空白图片，设置成被点击图片,并显示
            int[] imageIndex = myarray.getImageIndex();
            blankImage.setImageResource(imageD[imageIndex[clickPosition]]);
            blankImage.setVisibility(View.VISIBLE);
            //将改变记录到数组中
            myarray.swap(blankPosition , clickPosition);
            blankPosition = clickPosition;
            blankImageId = imageId;
            blankImage = (ImageView) getActivity().findViewById(blankImageId);
        }
        //交换完图片后判断是否成功
        isSucceess();
    }

    //成功
    public void isSucceess(){
        if(myarray.isSucceess()){//拼图成功
            //时间停止
            handler.removeMessages(1);
            //图片不可再点击
            for(int i = 0 ; i < imageId.length ; i++){
                imageV[i].setClickable(false);
            }

            //准备对话框
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            //读出数据
            SharedPreferences pref = getActivity().getSharedPreferences("data", MODE_PRIVATE);
            int simpleTime = pref.getInt("medium", -1);
            //如果没有记录 或者 破纪录
            if(simpleTime < 0 || simpleTime > time){
                //写入数据
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("data",
                        MODE_PRIVATE).edit();
                editor.putInt("medium", time);
                editor.commit();
                //提示用户破纪录
                builder.setMessage("拼图成功！新纪录！用时 " + time + "秒").setPositiveButton("确认", null);
            }else {
                //提示用户拼图成功
                builder.setMessage("拼图成功！用时 " + time + "秒").setPositiveButton("确认", null);
            }
            builder.create().show();
        }
    }
    //重新开始
    public void reStart() {
        //将图片恢复点击
        for(int i = 0 ; i < imageId.length ; i++){
            imageV[i].setClickable(true);
        }
        //恢复初始状态
        //下标数组重新赋值
        myarray.initArray();
        //打乱 图片重新赋值
        disturImage();

        blankImage.setVisibility(View.VISIBLE);
        //获取当前的空白图片位置并设置成可见
        //设置空白图片以及位置
        blankPosition = 15;
        blankImage = (ImageView) getActivity().findViewById(R.id.image16);
        blankImageId = R.id.image16;
        blankImage.setVisibility(View.INVISIBLE);

        //时间归零
        time = 0 ;
        handler.removeMessages(1);
        handler.sendEmptyMessageDelayed(1 ,1000);
    }
    private void initImage(){
        image01 = (ImageView) getActivity().findViewById(R.id.image01);
        image02 = (ImageView) getActivity().findViewById(R.id.image02);
        image03 = (ImageView) getActivity().findViewById(R.id.image03);
        image04 = (ImageView) getActivity().findViewById(R.id.image04);
        image05 = (ImageView) getActivity().findViewById(R.id.image05);
        image06 = (ImageView) getActivity().findViewById(R.id.image06);
        image07 = (ImageView) getActivity().findViewById(R.id.image07);
        image08 = (ImageView) getActivity().findViewById(R.id.image08);
        image09 = (ImageView) getActivity().findViewById(R.id.image09);
        image10 = (ImageView) getActivity().findViewById(R.id.image10);
        image11 = (ImageView) getActivity().findViewById(R.id.image11);
        image12 = (ImageView) getActivity().findViewById(R.id.image12);
        image13 = (ImageView) getActivity().findViewById(R.id.image13);
        image14 = (ImageView) getActivity().findViewById(R.id.image14);
        image15 = (ImageView) getActivity().findViewById(R.id.image15);
        image16 = (ImageView) getActivity().findViewById(R.id.image16);
        blankImage = (ImageView) getActivity().findViewById(R.id.image16);

        imageV = new ImageView[]{
                image01,image02,image03,image04, image05,image06,
                image07,image08, image09,image10,image11,image12,
                image13,image14,image15,image16
        };
        //监听
        for(int i = 0 ; i< imageD.length ; i++){
            int finalI = i;
            imageV[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    swapImage(imageId[finalI] , finalI);
                }
            });
        }

        reStart = (Button)getActivity().findViewById(R.id.reStart) ;
        reStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reStart();
            }
        });
        tip = (Button) getActivity().findViewById(R.id.tip) ;
        tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //提示功能
                //首先，找到能交换的位置
                int tipPosition = myarray.findTipPosition(blankPosition);
                //其次，交换两个位置的图片
                swapImage(imageId[tipPosition] , tipPosition);
            }
        });
    }
}