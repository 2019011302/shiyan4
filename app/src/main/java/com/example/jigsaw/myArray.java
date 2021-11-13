package com.example.jigsaw;

import java.util.ArrayList;
import java.util.List;

public class myArray {
    private int size;//难度模式 ： 简单 3 ； 一般 4 ； 困难 6 ；
    private int[] imageIndex ;

    //构造函数
    public myArray(int size){
        this.size = size ;
        this.imageIndex = new int[size*size];
    }

    public int[] getImageIndex() {
        return imageIndex;
    }

    //数组按顺序赋值(初始化)
    public void initArray(){
        for(int i = 0 ; i< imageIndex.length ; i++){
            imageIndex[i] = i ;
        }
    }
    //交换两个位置的数值
    public void swap(int index1 , int index2){
        //交换下标
        int temp = imageIndex[index1];
        imageIndex[index1] = imageIndex[index2];
        imageIndex[index2] = temp;
    }

    //打乱数组
    public void disturb() {
        int random1 , random2 ;
        //给顺序列imageIndex赋值
        for(int i = 0 ; i< imageIndex.length ; i++){
            imageIndex[i] = i ;
        }
        //交换40次
        int COUNT = 40;
        for(int i = 0 ; i < COUNT ; i++){
            //生成随机数 范围为[0,SIZE],最后一块空白部分不用交换
            random1 = (int)(Math.random() * (imageIndex.length-1));
            do{
                random2 = (int)(Math.random() * (imageIndex.length-1));
                if( random1 != random2)break;
            }while (true);
            //交换下标
            swap(random1 , random2);
        }
    }
    //判断是否可以交换 ，参数分别为被点击位置图片 和 空白位置图片
    //简单模式 3 ， 一般模式 4 ， 困难模式 6
    public Boolean isChange(int clickPosition ,int blankPosition) {
        //能交换的条件：被点击的图片与空白图片相邻
        //判断条件：同行列数相差一，或 同列行数相差一
        //首先，判断空白位置所在的行与列
        int blankRow = blankPosition / size; //所在行
        int blankCol = blankPosition % size; //所在列

        //其次，判断被点击图片所在的行与列
        int clickRow = clickPosition / size;
        int clickCol = clickPosition % size;

        //做减法
        int row = Math.abs(blankRow - clickRow);
        int col = Math.abs(blankCol - clickCol);

        //如果满足交换条件则返回true
        if ((row == 0 && col == 1) || (col == 0 && row == 1)) {
            return true;
        }
        return false;
    }
    //判断成功
    public Boolean isSucceess() {
        boolean flag = true;
        for (int i = 0; i < imageIndex.length; i++) {
            if (imageIndex[i] != i) {
                flag = false;
                break;
            }
        }
        return flag ;
    }
    //获取提示下标
    public int findTipPosition(int blankPosition){
        //int tipPosition;
        List<Integer> tipPosition = new ArrayList<Integer>();
        //提示图片的位置与空白位置同列相邻行，或，同行相邻列
        //获取blankPosition的坐标，即第几行第几列 ，从0开始
        int blankRow = blankPosition / size; //所在行
        int blankCol = blankPosition % size; //所在列

        //上：同列，行数少1
        int upRow = blankRow - 1 ;
        int upCol = blankCol;
        if(upRow >= 0){
            int upPosition = upRow * size + upCol ;
            tipPosition.add(upPosition);
        }

        //下：同列，行数多1
        int downRow = blankRow + 1 ;
        int downCol = blankCol;
        if(downRow < size){
            int downPosition = downRow * size + downCol ;
            tipPosition.add(downPosition);
        }

        //左：同行 ， 列数减1
        int leftRow  = blankRow;
        int leftCol  = blankCol -1 ;
        if(leftCol >= 0){
            int leftPosition = leftRow * size + leftCol ;
            tipPosition.add(leftPosition);
        }

        //右：同行 ， 列数加1
        int rightRow = blankRow ;
        int rightCol = blankCol + 1 ;
        if(rightCol < size){
            int rightPosition = rightRow * size + rightCol ;
            tipPosition.add(rightPosition);
        }
        //此时tipPosition中已经存好能移动的位置
        //根据位置获取imageIndex中对应位置的内容，如果imageIndex的下标和内容一一对应，则不换，否则将要进行更换
        //找出是否有差一步就恢复原位的图片
        for(int i = 0 ; i<tipPosition.size() ; i++){
//            if(Math.abs(imageIndex[blankPosition]-tipPosition.get(i)) == 1){
//                return  tipPosition.get(i);
//            }
            if(blankPosition == imageIndex[tipPosition.get(i)] ){
                return  tipPosition.get(i);
            }
        }
        //若都没有差一步就位的图片，则更换不对应位置的图片
        for(int i = 0 ; i<tipPosition.size() ; i++){
            if(imageIndex[tipPosition.get(i)] != tipPosition.get(i) ){//不对应，更换
                return  tipPosition.get(i);
            }
        }
        return tipPosition.get(0);
    }
}
