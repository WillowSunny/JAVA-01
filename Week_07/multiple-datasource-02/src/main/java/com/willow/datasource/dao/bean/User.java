package com.willow.datasource.dao.bean;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    private String account;
    private String name;


//    public static void main(String[] args) {
//        int[] arr = new int[]{3,5,1,7,5,7,2,4};
//        if(arr.length<=1){
//            return;
//        }
//        int n = arr.length;
//        for(int i = 0 ; i<n-1 ; ++i){
//            int minIdx = i;
//            int value = arr[i];
//            int j = i+1;
//            for(; j<n ; ++j){
//                if(arr[j]<value){
//                    value = arr[j];
//                    minIdx = j;
//                }
//            }
//            if(i != minIdx){
//                arr[minIdx] = arr[i];
//                arr[i] = value;
//            }
//        }
//        System.out.println(arr.toString());
//    }

    public static void main(String[] args) {
        String s = "abcdedcba";
        char[] chars = s.toCharArray();
        int slow =1;
        int fast =2;
        int mod = chars.length % 2;
        int midLength = chars.length / 2;
        for(int i = 0; i<chars.length; i++){

        }
    }

}
