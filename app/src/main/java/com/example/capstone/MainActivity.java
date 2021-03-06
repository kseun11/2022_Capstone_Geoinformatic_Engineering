package com.example.capstone;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

// 이 액티비티의 역할: 주소로 검색 버튼을 눌렀을 때 주소를 검색하는 창이 나오고 검색한 문자열을 통해 실제 주소지를 검색하고 다음 액티비티로 넘겨줌

public class MainActivity extends AppCompatActivity {
    static String[] POIResult = new String[100];
    static String[] AddressResult = new String[100];
    static int POIitemSize;
    static double[] POILat = new double[100];
    static double[] POILon = new double[100];
    static String DaumAddressResult = null;
    private final TMapData tMapData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 주소 검색 버튼
        Button findAddressbtn = (Button) findViewById(R.id.searchBtn);

        // 중간지점 찾는 버튼
        Button findCenterBtn = (Button) findViewById(R.id.findCenterBtn);
        findCenterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < array_saving_class.final_Point.size(); i++) {
                    // 주소의 위도, 경도 좌표
                    array_saving_class.tempX += array_saving_class.final_Point.get(i).getLatitude();
                    array_saving_class.tempY += array_saving_class.final_Point.get(i).getLongitude();

                    array_saving_class.recent_point.setLatitude(array_saving_class.final_Point.get(i).getLatitude());
                    array_saving_class.recent_point.setLongitude(array_saving_class.final_Point.get(i).getLongitude());
                }

                // 중간지점의 위도, 경도 좌표
                array_saving_class.center_point.setLatitude(array_saving_class.tempX / array_saving_class.final_Point.size());
                array_saving_class.center_point.setLongitude(array_saving_class.tempY / array_saving_class.final_Point.size());

                array_saving_class.tempX = array_saving_class.tempY = 0;
                array_saving_class.centerOfIt = true;
                // array_saving_class.alTMapPoint.add(array_saving_class.center_point);
                Intent letsGoToCenterPoint = new Intent(getApplicationContext(), AddressMarkAcitvity.class);
                startActivity(letsGoToCenterPoint);

            }

        });

        // TMap API를 사용하기 위한 TMapView 사용
        TMapView tMapView = new TMapView(this); // key값 설정을 위한 tmapView 생성
        tMapView.setSKTMapApiKey(getString(R.string.tmap_api_key)); // api key 설정

        // 주소 검색 버튼 클릭하면 나올 화면
        findAddressbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertTodAddress();
            } // 주소 검색 다이얼로그 띄우는 메소드 호출
        });

        // 검색하여 추가한 주소를 나타냄.
        final ListView final_list;
        final_list = (ListView) findViewById(R.id.saved_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                array_saving_class.final_location);

        final_list.setAdapter(adapter); // 리스트뷰에 어답터 연결
    }


    // 주소 검색으로 위도, 경도 검색하기
    // 명칭 검색을 통한 주소 변환
    public void convertTodAddress() {
        // 주소 검색 다이얼로그가 띄워짐
        AlertDialog.Builder addressSearchBuilder = new AlertDialog.Builder(this);
        addressSearchBuilder.setTitle("주소 검색");

        // 주소 입력 창
        final EditText userInput = new EditText(this);
        addressSearchBuilder.setView(userInput);

        // 검색할 주소명 입력 후 '확인' 클릭시 뜨는 창
        addressSearchBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String strData = userInput.getText().toString();
                TMapData tmapData = new TMapData();

                // 명칭의 주소를 가져오는 함수
                tmapData.findAllPOI(strData, new TMapData.FindAllPOIListenerCallback() {
                    @Override
                    public void onFindAllPOI(ArrayList<TMapPOIItem> poiItem) {
                        // 명칭으로 주소 검색시 그 명칭에 해당하는 주소리스트를 띄우기 위한 코드
                        for (int i = 0; i < poiItem.size(); i++) {
                            TMapPOIItem item = poiItem.get(i);

                            POIResult[i] = item.getPOIName();
                            AddressResult[i] = item.getPOIAddress().replace("null", "");
                            POIitemSize = poiItem.size();
                            POILat[i] = item.getPOIPoint().getLatitude();
                            POILon[i] = item.getPOIPoint().getLongitude();

                            Log.d("POI testing", "POI 검색결과: " + POIResult[i] + " " + AddressResult[i] + POILat[i] + "" + POILon[i] + "\n");
                        }

                        Intent ListViewIntent = new Intent(getApplicationContext(), ListViewActivity.class);
                        startActivity(ListViewIntent); // 리스트뷰 띄우는 액티비티로 이동(명칭 관련 주소리스트 쭉 나오는)

                    }

                });

            }

        });

        // 주소 검색 취소하기
        addressSearchBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        addressSearchBuilder.show();
    }
}

