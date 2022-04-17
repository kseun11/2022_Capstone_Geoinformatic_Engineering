package com.example.capstone;

import static com.example.capstone.array_saving_class.alTMapPoint;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;
import java.util.List;

// 이 액티비티의 역할: 주소를 마커로 표시해주는 액티비티

public class AddressMarkAcitvity extends AppCompatActivity {
    public static ArrayList<TMapPoint> pointOfAll = new ArrayList<TMapPoint>();
    public static List<TMapMarkerItem> markerItem1 = new ArrayList<>();
    TMapView tMapView;
    TextView address_textView;
    TextView name_textView;
    Button yesBtn;
    Button noBtn;
    Bitmap bitmap;
    Bitmap temp;
    TMapPoint centerP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_mark_acitvity);

        LinearLayout linearLayoutTmap = (LinearLayout) findViewById(R.id.mapview);
        tMapView = new TMapView(this);
        name_textView = (TextView) findViewById(R.id.nameOfLocation);   // 주소명
        address_textView = (TextView) findViewById(R.id.nameOfAddress); // 해당 주소

        tMapView.setSKTMapApiKey(getString(R.string.tmap_api_key)); // api key 설정
        linearLayoutTmap.addView(tMapView);

        // res 폴더에 저장된 그림파일을 Bitmap으로 만들어 리턴해줌
        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_assistant_photo_black_24dp);
        temp = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
        ArrayList<String> id_marker = new ArrayList<>();
        ArrayList<String> id_marker_final = new ArrayList<>();

        markReturn();

        if (array_saving_class.centerOfIt == false) {
            //
            name_textView.setText(array_saving_class.nameOfIt.get(array_saving_class.nameOfIt.size() - 1));
            address_textView.setText(array_saving_class.addressOfIt.get(array_saving_class.addressOfIt.size() - 1));

            //
            tMapView.setCenterPoint(alTMapPoint.get(alTMapPoint.size() - 1).getLongitude(), alTMapPoint.get(alTMapPoint.size() - 1).getLatitude());

        } else {
            // 중심지점을 설정해주는 메소드.
            tMapView.setCenterPoint(array_saving_class.center_point.getLatitude(), array_saving_class.center_point.getLongitude());
            Log.d("test", "centerLat : " + array_saving_class.center_point.getLatitude());
            Log.d("test", "centerLon : " + array_saving_class.center_point.getLongitude());

            // 중간지점 아이콘 만들기
            TMapMarkerItem center = new TMapMarkerItem();
            centerP = new TMapPoint(array_saving_class.center_point.getLongitude(), array_saving_class.center_point.getLatitude());

            center.setIcon(bitmap); // 마커 아이콘 지정
            center.setCanShowCallout(true); // 풍선뷰의 사용여부를 설정한다.
            center.setCalloutTitle("중간지점 "); // 풍선뷰 제목 설정
            center.setCalloutSubTitle("중간지점 입니다."); // 풍선뷰 보조메시지 설정
            center.setCalloutRightButtonImage(temp); // 풍선뷰 오른쪽 이미지 설정

            center.setAutoCalloutVisible(true); // 풍선뷰 자동 활성화
            // 위경도에 따른 마커의 좌표 지정
            center.setTMapPoint(centerP); // 마커 위,경도 설정
            center.setVisible(TMapMarkerItem.VISIBLE); // 아이콘 보이게
            //지도에 마커 추가
            tMapView.addMarkerItem("markerItem", center);

            // 중간지점에 자동차경로 추가
            drawline();

        }
        yesBtn = (Button) findViewById(R.id.yesBtn);
        noBtn = (Button) findViewById(R.id.noBtn);

        // 주소 검색 후 YES 버튼 클릭시
        yesBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 검색을 한 주소를 리스트에 나타내고
                array_saving_class.final_location.add(array_saving_class.nameOfIt.get(array_saving_class.addressOfIt.size() - 1));
                array_saving_class.final_Point.add(new TMapPoint(alTMapPoint.get(alTMapPoint.size() - 1).getLongitude(), alTMapPoint.get(alTMapPoint.size() - 1).getLatitude()));
                // 메인화면으로 감
                Intent goToMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(goToMain);
            }
        });

        // 주소 검색 후 NO 버튼 클릭시
        noBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                array_saving_class.addressOfIt.remove(array_saving_class.addressOfIt.get(array_saving_class.addressOfIt.size() - 1));
                // 검색한 주소를 리스트에 저장하지 않고
                alTMapPoint.remove(alTMapPoint.get(alTMapPoint.size() - 1));
                // 메인화면으로 감
                Intent goToMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(goToMain);
            }
        });

    }

    // 자동차경로 호출시 외부에서 Thread를 통해서 호출해줘야 정상적으로 실행
    // 자동차경로를 그리기 위한 코드
    public void drawline() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < alTMapPoint.size(); i++) {
                        TMapPolyLine tMapPolyLine = new TMapData().findPathData(alTMapPoint.get(i), centerP);
                        tMapPolyLine.setLineColor(Color.BLUE);
                        tMapPolyLine.setLineWidth(2);
                        tMapView.addTMapPolyLine("Line1" + i, tMapPolyLine);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    // 마커 아이콘 표시
    public void markReturn() {
        for (int i = 0; i < alTMapPoint.size(); i++) {

            TMapMarkerItem markerItem1 = new TMapMarkerItem();
            // 마커 정보 표시
            markerItem1.setIcon(bitmap);
            markerItem1.setCanShowCallout(true); // 풍선뷰
            markerItem1.setCalloutSubTitle("위치:" + array_saving_class.nameOfIt.get(i));
            markerItem1.setCalloutTitle("좌표 " + (i + 1) + "번");
            markerItem1.setCalloutRightButtonImage(temp);
            markerItem1.setAutoCalloutVisible(true);
            // 마커의 좌표 지정
            markerItem1.setTMapPoint(alTMapPoint.get(i));
            //지도에 마커 추가
            tMapView.addMarkerItem("markerItem" + i, markerItem1);
            tMapView.setCenterPoint(alTMapPoint.get(i).getLatitude(), alTMapPoint.get(i).getLatitude());

        }
    }
}
