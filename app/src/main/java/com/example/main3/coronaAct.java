package com.example.main3;

import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class coronaAct extends AppCompatActivity implements OnMapReadyCallback {


    private FragmentManager fragmentManager;
    private MapFragment mapFragment;
    private PolylineOptions polylineOptions;
    private GoogleMap mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coroan);

        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.googlemap);
        mapFragment.getMapAsync(this);

    }
        // 마커 생성
    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap mMap = googleMap;
        MarkerOptions markerOptions;
        mapView = googleMap;

        // 확진자들에 대한 정보
        Double[] lat = {35.192023, 35.192034, 35.168936, 35.260902, 35.151954, 35.164856, 35.260742, 35.208467, 35.160133, 35.165010, 35.214854, 35.191523, 35.198328};
        Double[] lon = {128.118732, 128.120321, 128.063600, 127.995301, 128.054129, 128.126973, 127.995719, 128.154312, 128.106705, 128.128923, 128.122958, 128.089404, 128.070920};
        String[] title = {"진주 1번 확진자 / 완치", "진주 2번 확진자 / 완치", "진주 3번 확진자 / 완치", "진주 4번 확진자 / 완치", "진주 5번 확진자(4번과 부부) / 완치", "진주 6번 확진자(5번과 직장동료) / 완치", "진주 7번 확진자(4번과 차량 동승자) / 완치", "진주 8번 확진자(7번 확진자의 며느리) / 완치", "진주 9번 확진자(윙스 스파 이용자) / 완치", "진주 10번 확진자(8번 확진자의 딸) / 완치", "진주 보건소", "진주 11번 확진자(신촌 주점관련)", "진주 12번 확진자(감염 원인 확인중) / 완치" };
        String[] information = {"대구 신천지 교회 방문(동선X)", "대구 신천지 교회 방문(동선X)", "평거동 문타이", "진주 스파랜드", "성지원 골프연습장", "혁신도시 윙스타워", "진주 스파랜드 관련", "일노브 식당", "가호동 행정복지센터", "특이사항 없음(동선 X)", "코로나 검진소", "진주시외버스터미널", "상봉 아파트"};

        // 확진자들이 다녀간 장소에 대한 정보
        Double[] lat1 = {35.155571, 35.166450, 35.167537, 35.209834, 35.185253, 35.164323, 35.205577, 35.192995, 35.176481};
        Double[] lon1 = {128.111501, 128.128628, 128.127749, 128.123728, 128.087492, 128.125996, 128.111741, 128.116581, 128.095627};
        String[] title1 = {"3번 확진자","4번, 5번 확진자", "4번, 5번 확진자", "8번 확진자", "8번 확진자", "9번 확진자", "11번 확진자", "12번 확진자", "12번 확진자"};
        String[] information1 = {"가좌 센트럴 웰가", "한일 병원", "옵티마 미소 약국", "다이소 초전점", "강남동 새미래약국", "탑 유황스파", "초전 푸르지오 2단지", "선학 사거리", "경상대학교병원"};

        for (int i = 0; i < title.length; i++) {

            markerOptions = new MarkerOptions();
            LatLng name = new LatLng(lat[i], lon[i]);
            markerOptions.position(name);

            markerOptions.title(title[i]);
            markerOptions.snippet(information[i]);
            //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(name));

            // 1번과 2번 확진자의 동선은 대구 신천지 방문이후 공개가 되지 않아 표기하지 않음


           // 3번 확진자 동선
           Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                    .clickable(true)
                    .add(
                            new LatLng(35.168936, 128.063600), // 평거동 문타이
                            new LatLng(35.155571, 128.111501), // 가좌 센트럴 웰가
                            new LatLng(35.214854, 128.122958)) // 진주 보건소
                    .color(Color.RED));


            // 4번 확진자 동선
            Polyline polyline2 = googleMap.addPolyline(new PolylineOptions()
                    .clickable(true)
                    .add(
                            new LatLng(35.260902, 127.995301), // 진주 스파랜드
                            new LatLng(35.166450, 128.128628), // 한일 병원
                            new LatLng(35.167537, 128.127749)) // 옵티마 미소 약국
                    .color(Color.BLUE));

            // 5번 확진자 동선
            Polyline polyline3 = googleMap.addPolyline(new PolylineOptions()
                    .clickable(true)
                    .add(
                            new LatLng(35.151954, 128.054129), // 성지원 골프 연습장
                            new LatLng(35.166450, 128.128628), // 한일 병원
                            new LatLng(35.167537, 128.127749)) // 옵티마 미소
                    .color(Color.GREEN));

            // 6번 확진자 동선
            Polyline polyline4 = googleMap.addPolyline(new PolylineOptions()
                    .clickable(true)
                    .add(
                            new LatLng(35.164856, 128.126973), // 윙스 타워
                            new LatLng(35.214872, 128.122958))); // 진주 보건소

            // 7번 확진자 동선
            Polyline polyline5 = googleMap.addPolyline(new PolylineOptions()
                    .clickable(true)
                    .add(
                            new LatLng(35.260902,  127.995301), // 진주 스파랜드
                            new LatLng(35.214872, 128.122958)) // 진주 보건소
                    .color(Color.GRAY));

            // 8번 확진자 동선
            Polyline polyline6 = googleMap.addPolyline(new PolylineOptions()
                    .clickable(true)
                    .add(
                            new LatLng(35.208467,  128.154312), // 일노브 식당
                            new LatLng(35.209834, 128.123728), // 다이소 초전점
                            new LatLng(35.185253, 128.087492)) // 강남동 새미래 약국
                    .color(Color.CYAN));

            // 9번 확진자 동선
            Polyline polyline7 = googleMap.addPolyline(new PolylineOptions()
                    .clickable(true)
                    .add(
                            new LatLng(35.160133,  128.106705), // 가호동 행정복지센터
                            new LatLng(35.164323, 128.125996), // 탑 유황스파
                            new LatLng(35.214872, 128.122958)) // 진주 보건소
            .color(Color.YELLOW));

            // 10번 확진자는 영유아이고 8번 확진자의 딸이고, 8번 확진자의 영향덕에 걸리기 전후의 동선이 없으므로 표기하지 않음

            // 11번쨰 확진자
            Polyline polyline8 = googleMap.addPolyline(new PolylineOptions()
                    .clickable(true)
                    .add(
                            new LatLng(35.191523,  128.089404), // 진주시외버스터미널
                            new LatLng(35.205577, 128.111741), // 초전 프루지오 2단지
                            new LatLng(35.214872, 128.122958)) // 진주 보건소
                    .color(Color.DKGRAY));

            // 12번쨰 확진자 추가
            Polyline polyline9 = googleMap.addPolyline(new PolylineOptions()
                    .clickable(true)
                    .add(
                            new LatLng(35.198328,  128.070920), // 상봉아파트 정류장
                            // new LatLng(35.190714, 128.079886), //  유정장어 (인사동 커피향 거의 주변)
                            // new LatLng(35.180197, 128.110372), // 자유 시장 정류장
                            // new LatLng(35.189181, 128.127296), // 한국 폴리텍 대학 정류장
                            // new LatLng(35.184764, 128.088563), // 천전시장 정류장
                            // new LatLng(35.185519, 128.116954), // 동부시장 정류장
                            new LatLng(35.214872, 128.122958), // 진주보건소
                            // new LatLng(35.195438, 128.075315), // 서부시장 정류장
                            new LatLng(35.192995, 128.116581), // 선학사거리
                            new LatLng(35.176481, 128.095627)) // 경상대학병원
                    .color(Color.RED));

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.153356, 128.099415), 16)); // 경상대학교 좌표
        }

        for (int j = 0; j < title1.length; j++) {
            markerOptions = new MarkerOptions();
            LatLng name1 = new LatLng(lat1[j], lon1[j]);
            markerOptions.position(name1);
            markerOptions.title(title1[j]);
            markerOptions.snippet(information1[j]);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mMap.addMarker(markerOptions);
        }



    }


}