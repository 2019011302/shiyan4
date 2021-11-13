package com.example.jigsaw;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import android.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DifficultFragment extends Fragment {
    private TextView tv ;
    private ImageView image01,image02,image03,image04, image05,image06,
            image07,image08, image09,image10,image11,image12,
            image13,image14,image15,image16, image17,image18,
            image19,image20, image21,image22,image23,image24,
            image25,image26,image27,image28, image29,image30,
            image31,image32, image33,image34,image35,image36;
    private ImageView[] imageV ;
    private ImageView blankImage;
    private Button reStart ;
    private Button tip;
    private int time;
    private int blankPosition;//空白图片的位置 第一个位置下标为0 ，16张图片， 最后一个位置下标为15
    private int blankImageId;//空白图片的id;
    private int[] imageD={
            R.drawable.difficult_01 , R.drawable.difficult_02 , R.drawable.difficult_03, R.drawable.difficult_04 ,
            R.drawable.difficult_05 , R.drawable.difficult_06, R.drawable.difficult_07 , R.drawable.difficult_08 ,
            R.drawable.difficult_09, R.drawable.difficult_10, R.drawable.difficult_11 , R.drawable.difficult_12 ,
            R.drawable.difficult_13, R.drawable.difficult_14, R.drawable.difficult_15 , R.drawable.difficult_16 ,
            R.drawable.difficult_17 , R.drawable.difficult_18 , R.drawable.difficult_19, R.drawable.difficult_20 ,
            R.drawable.difficult_21 , R.drawable.difficult_22, R.drawable.difficult_23 , R.drawable.difficult_24 ,
            R.drawable.difficult_25, R.drawable.difficult_26, R.drawable.difficult_27 , R.drawable.difficult_28 ,
            R.drawable.difficult_29, R.drawable.difficult_30, R.drawable.difficult_31 , R.drawable.difficult_32 ,
            R.drawable.difficult_33, R.drawable.difficult_34, R.drawable.difficult_35 , R.drawable.difficult_36
    };
    private int[] imageId={
            R.id.image01 , R.id.image02 , R.id.image03, R.id.image04 , R.id.image05 , R.id.image06,
            R.id.image07 , R.id.image08 , R.id.image09, R.id.image10 , R.id.image11 , R.id.image12,
            R.id.image13 , R.id.image14 , R.id.image15, R.id.image16 , R.id.image17 , R.id.image18,
            R.id.image19 , R.id.image20 , R.id.image21, R.id.image22 , R.id.image23 , R.id.image24,
            R.id.image25 , R.id.image26 , R.id.image27, R.id.image28 , R.id.image29 , R.id.image30,
            R.id.image31 , R.id.image32 , R.id.image33, R.id.image34 , R.id.image35 , R.id.image36,
    };
    //private int[] imageIndex = new int[image.length];
    myArray myarray = new myArray(6 ) ;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == 1){
               // tv = (TextView) getActivity().findViewById(R.id.tv);
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_difficult, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //handler.sendEmptyMessageDelayed(1 , 1000);
        myarray.initArray();
        //初始化控件
        initImage();
        //打乱图片顺序
        disturImage();
        //初始化其他基本信息
        initInfo();
    }
    //初始化基本信息
    private void initInfo(){
        //设置空白图片以及位置
        blankPosition = 35;
        blankImage = (ImageView) getActivity().findViewById(R.id.image36);
        blankImageId = R.id.image36;
        blankImage.setVisibility(View.INVISIBLE);
        //时间归零
        time = 0 ;
        handler.removeMessages(1);
        handler.sendEmptyMessageDelayed(1 ,1000);
    }
    private void disturImage() {
        myarray.disturb();//打乱数组
        int[] imageIndex = myarray.getImageIndex();

        for(int i =0 ; i < imageD.length ; i++){
            imageV[i].setImageResource(imageD[imageIndex[i]]);
        }
    }
    //处理点击后是否交换问题
    public void swapImage(int imageid , int clickPosition){
        //如果满足交换条件则设置图片
        if(myarray.isChange(clickPosition, blankPosition)){
            //获取被点击图片,设置成不可见，即空白
            try{
                ImageView onClickImage = (ImageView) getActivity().findViewById(imageid);
                onClickImage.setVisibility(View.INVISIBLE);
            }catch (Exception e){
                e.printStackTrace();
            }
            // blankImageId = R.id.image09;
            //获取空白图片，设置成被点击图片,并显示
            int[] imageIndex = myarray.getImageIndex();
            blankImage.setImageResource(imageD[imageIndex[clickPosition]]);
            blankImage.setVisibility(View.VISIBLE);
            //将改变记录到数组中
            myarray.swap(blankPosition , clickPosition);
            blankPosition = clickPosition;
            blankImageId = imageid;
            blankImage = (ImageView) getActivity().findViewById(blankImageId);
            //交换完图片后判断是否成功
            isSucceess();
        }
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
            int simpleTime = pref.getInt("difficult", -1);
            //如果没有记录 或者 破纪录
            if(simpleTime < 0 || simpleTime > time){
                //写入数据
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("data",
                        MODE_PRIVATE).edit();
                editor.putInt("difficult", time);
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

        //将当前的空白位置的图片显示
        blankImage.setVisibility(View.VISIBLE);

        initInfo();

    }
    private void initImage(){

        tv = (TextView) getActivity().findViewById(R.id.tv);

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
        image17 = (ImageView) getActivity().findViewById(R.id.image17);
        image18 = (ImageView) getActivity().findViewById(R.id.image18);
        image19 = (ImageView) getActivity().findViewById(R.id.image19);
        image20 = (ImageView) getActivity().findViewById(R.id.image20);
        image21 = (ImageView) getActivity().findViewById(R.id.image21);
        image22 = (ImageView) getActivity().findViewById(R.id.image22);
        image23 = (ImageView) getActivity().findViewById(R.id.image23);
        image24 = (ImageView) getActivity().findViewById(R.id.image24);
        image25 = (ImageView) getActivity().findViewById(R.id.image25);
        image26 = (ImageView) getActivity().findViewById(R.id.image26);
        image27 = (ImageView) getActivity().findViewById(R.id.image27);
        image28 = (ImageView) getActivity().findViewById(R.id.image28);
        image29 = (ImageView) getActivity().findViewById(R.id.image29);
        image30 = (ImageView) getActivity().findViewById(R.id.image30);
        image31 = (ImageView) getActivity().findViewById(R.id.image31);
        image32 = (ImageView) getActivity().findViewById(R.id.image32);
        image33 = (ImageView) getActivity().findViewById(R.id.image33);
        image34 = (ImageView) getActivity().findViewById(R.id.image34);
        image35 = (ImageView) getActivity().findViewById(R.id.image35);
        image36 = (ImageView) getActivity().findViewById(R.id.image36);

        imageV = new ImageView[]{
                image01,image02,image03,image04, image05,image06,
                image07,image08, image09,image10,image11,image12,
                image13,image14,image15,image16, image17,image18,
                image19,image20, image21,image22,image23,image24,
                image25,image26,image27,image28, image29,image30,
                image31,image32, image33,image34,image35,image36
        };
        blankImage = (ImageView) getActivity().findViewById(R.id.image16);
        reStart = (Button)getActivity().findViewById(R.id.reStart) ;

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