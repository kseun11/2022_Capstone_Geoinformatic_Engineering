package com.example.capstone;

import static com.example.capstone.MainActivity.AddressResult;
import static com.example.capstone.MainActivity.POILat;
import static com.example.capstone.MainActivity.POILon;
import static com.example.capstone.MainActivity.POIResult;
import static com.example.capstone.MainActivity.POIitemSize;
import static com.example.capstone.array_saving_class.POIName;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.adapter.ListViewAdapter;
import com.skt.Tmap.TMapPoint;

public class ListViewActivity extends AppCompatActivity {
    int size = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        final ListView AddressListView;
        ListViewAdapter listViewAdapter;

        listViewAdapter = new ListViewAdapter();

        AddressListView = (ListView) findViewById(R.id.Addresslistview);
        AddressListView.setAdapter(listViewAdapter); // 리스트뷰에 어답터 연결

        for (int i = 0; i < POIitemSize; i++) {
            listViewAdapter.addItem(POIResult[i], AddressResult[i], POILat[i], POILon[i]);
        } // 어답터에 주소의 이름과 상세주소, 위도 경도 추가
        AddressListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TMapPoint temp = new TMapPoint(POILat[position], POILon[position]);
                array_saving_class.alTMapPoint.add(temp);
                array_saving_class.nameOfIt.add(POIResult[position]);
                array_saving_class.addressOfIt.add(AddressResult[position]);
                POIName[size] = POIResult[position];
                size++;
                Intent MarkIntent = new Intent(getApplicationContext(), AddressMarkAcitvity.class);
                startActivity(MarkIntent);
                // Log.d("Position", "Position : " + position);
            }
        });

    }
}

