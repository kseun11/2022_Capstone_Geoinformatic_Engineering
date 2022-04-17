package com.example.capstone;

import com.skt.Tmap.TMapPoint;

import java.util.ArrayList;

public class array_saving_class {

    // static 변수는 클래스 변수로, 객체를 생성하지 않고도 static 자원에 접근이 가능.
    // static 변수와 static 메소드는 static 메모리 영역에 존재하므로 객체가 생성되기 이전에 이미 할당되어 있음
    // 따라서 객체의 생성없이 바로 사용할 수 있음.
    public static ArrayList<TMapPoint> alTMapPoint = new ArrayList<>();
    public static ArrayList<String> nameOfIt = new ArrayList<>();
    public static ArrayList<String> addressOfIt = new ArrayList<>();
    public static ArrayList<String> final_location = new ArrayList<>();
    public static ArrayList<TMapPoint> final_Point = new ArrayList<>();

    // 중간지점을 구하기 위함
    public static String[] POIName = new String[100];
    public static TMapPoint recent_point = new TMapPoint(0, 0);
    public static TMapPoint center_point = new TMapPoint(0, 0);
    public static String center_location;
    public static double tempX;
    public static double tempY;
    public static boolean centerOfIt = false;
}
