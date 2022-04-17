package com.example.capstone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.capstone.R;

import java.util.ArrayList;

// 사용자지정 레이아웃을 적용시킨 아이템을 담는 리스트뷰인 '커스텀 리스트뷰'를 만들기 위해 생성된 액티비티.

// BaseAdapter를 상속받는 ListViewAdapter를 정의함.
public class ListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private final ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();

    // ListViewAdapter의 생성자
    public ListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override    //******************1************************
    // 각 아이템에 들어갈 데이터의 전체 개수를 리턴하는 메소드
    public int getCount() {
        return listViewItemList.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override    //******************2************************
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)로부터 위젯에 대한 참조 획득
        TextView POITextView = (TextView) convertView.findViewById(R.id.textView1);
        TextView AddressTextView = (TextView) convertView.findViewById(R.id.textView2);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        POITextView.setText(listViewItem.getPOIStr());
        AddressTextView.setText(listViewItem.getAddressStr());

        return convertView;    // listview_item 리턴
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴 : 필수 구현
    @Override    //******************3************************
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override    //******************4************************
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    // addItem 메서드를 생성 -> ListViewActivity에서 사용.
    public void addItem(String POI, String Address, double Lat, double Lon) {
        ListViewItem item = new ListViewItem();

        // 주소명, 상세주소, 위도, 경도
        item.setPOIStr(POI);
        item.setAddressStr(Address);
        item.setLat(Lat);
        item.setLon(Lon);

        listViewItemList.add(item);
    }

    public void getLvLat(int position) {
    }

    public void getLvLon(int position) {

    }
}