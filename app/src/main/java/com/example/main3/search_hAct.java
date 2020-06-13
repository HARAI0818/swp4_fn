package com.example.main3;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.algo.NonHierarchicalDistanceBasedAlgorithm;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;


import java.util.ArrayList;



public class search_hAct extends FragmentActivity
        implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback {

    private static final int MY_LOCATION_REQUEST_CODE = 1;
    private static final int TAG_CODE_PERMISSION_LOCATION = 1;
    public static Context context_main;
    public String Review_hos = "";
    private GoogleMap mgoogleMap;
    private ClusterManager<MyItem> clusterManager;
    ArrayList<Clinic> clinics;
    ArrayList<Clinic> Marker;
    ArrayList<Location> clinic_address;
    ArrayList<Location> Maker_address;
    Context context = this;
    final String TAG = "LogMainActivity";
    IconGenerator mIconGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_h);

        final EditText et_name = (EditText) findViewById(R.id.editText2);
        ImageButton btn_search = (ImageButton) findViewById(R.id.btn_search);
        ImageButton btn_del = (ImageButton) findViewById(R.id.button2);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = et_name.getText().toString();
                if (text.equals("")) {
                    Toast.makeText(getApplicationContext(), "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    boolean check = false;
                    int check2 = 0;
                    LatLng latLng1 = null;
                    LatLng latLng2 = null;
                    for (int i = 0; i < 100; i++) {
                        if (clinics.get(i).getName().contains(text)) {
                            if (check == false) {
                                clusterManager.clearItems();
                            }

                            MyItem clinicItem = new MyItem(Maker_address.get(i).getLatitude(), Maker_address.get(i).getLongitude(),
                                    Marker.get(i).getName());
                            clusterManager.addItem(clinicItem);
                            check2++;

                            if (check == false) {
                               latLng2 = new LatLng(Maker_address.get(i).getLatitude(), Maker_address.get(i).getLongitude());
                                check = true;
                            }
                        }
                    }

                    if (check) {
                        if(check2 == 1) {
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng2, (float) 15);
                            mgoogleMap.animateCamera(cameraUpdate);
                        }
                        else {
                            if (mgoogleMap.getCameraPosition().zoom == 11.5) {
                                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng2, (float) 12);
                                mgoogleMap.animateCamera(cameraUpdate);
                            } else {
                                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng2, (float) 11.5);
                                mgoogleMap.animateCamera(cameraUpdate);
                            }

                        }
                        Toast.makeText(getApplicationContext(),"총"+check2+"개의 결과가 있습니다.",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "정보를 찾을 수 없습니다. \n 다시 검색해주세요.", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clusterManager.clearItems();

                for (int i = 0; i < Marker.size(); i++) {
                    MyItem clinicItem = new MyItem(Maker_address.get(i).getLatitude(), Maker_address.get(i).getLongitude(),
                            Marker.get(i).getName());
                    clusterManager.addItem(clinicItem);
                    LatLng latLng = new LatLng(35.201978, 128.112746);
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 12);
                    mgoogleMap.animateCamera(cameraUpdate);
                    Toast.makeText(getApplicationContext(), "지도가 초기화되었습니다.", Toast.LENGTH_SHORT).show();

                }
            }
        });


        context_main = this;
        clinics = (ArrayList<Clinic>) getIntent().getSerializableExtra("clinic");
        Marker = clinics;
        clinic_address = (ArrayList<Location>) getIntent().getSerializableExtra("clinic_addr");
        Maker_address = clinic_address;
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);


    }

    public class MarkerClusterRenderer extends DefaultClusterRenderer<MyItem> {

        public MarkerClusterRenderer(Context context, GoogleMap map,
                                     ClusterManager<MyItem> clusterManager) {
            super(context, map, clusterManager);

        }

        @Override
        protected void onClusterItemRendered(MyItem clusterItem, Marker marker) {
            super.onClusterItemRendered(clusterItem, marker);
        }

        @Override
        protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {

            markerOptions.icon(BitmapDescriptorFactory.
                    fromResource(R.drawable.marker2));
        }

        @Override
        protected boolean shouldRenderAsCluster(Cluster<MyItem> cluster) {
            return cluster.getSize() > 1;
        }


       /* @Override
        protected void onBeforeClusterRendered(Cluster<MyItem> cluster, MarkerOptions markerOptions){
            BitmapDescriptor descriptor;
            descriptor = BitmapDescriptorFactory.fromBitmap(mIconGenerator.makeIcon(getClusterText(3)));
            markerOptions.icon(descriptor);
        }*/
    }





    @Override
    public void onMapReady(GoogleMap googleMap) {
        mgoogleMap = googleMap;
        clusterManager = new ClusterManager<>(this, mgoogleMap);

        mgoogleMap.setOnCameraIdleListener(clusterManager);
        mgoogleMap.setOnMarkerClickListener(clusterManager);
        mgoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mgoogleMap.setMyLocationEnabled(true);
        mgoogleMap.setOnMyLocationButtonClickListener(this);
        mgoogleMap.setOnMyLocationClickListener(this);

        NonHierarchicalDistanceBasedAlgorithm<MyItem> algorithm = new NonHierarchicalDistanceBasedAlgorithm<>();
        algorithm.setMaxDistanceBetweenClusteredItems(60);
        clusterManager.setAlgorithm(algorithm);


       clusterManager.setRenderer(new MarkerClusterRenderer(this, mgoogleMap, clusterManager));
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    TAG_CODE_PERMISSION_LOCATION);
        }


        mgoogleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                Log.d(TAG, "Load");
                LatLng latLng = new LatLng(35.201978, 128.112746);
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 12);
                mgoogleMap.animateCamera(cameraUpdate);

                for (int i = 0; i < Marker.size(); i++) {
                    MyItem clinicItem = new MyItem(Maker_address.get(i).getLatitude(), Maker_address.get(i).getLongitude(),
                            Marker.get(i).getName());
                    clusterManager.addItem(clinicItem);
                } // 병원 개수만큼 item 추가

            }
        });

        clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {
            @Override
            public boolean onClusterClick(Cluster<MyItem> cluster) {
              LatLng latLng = new LatLng(cluster.getPosition().latitude, cluster.getPosition().longitude);
                float a = mgoogleMap.getCameraPosition().zoom+1;
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, a);
                mgoogleMap.moveCamera(cameraUpdate);
                return false;
            }
        });

        mgoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String marker_number = null;
                for (int i = 0; i < Marker.size(); i++) {
                    if (Marker.get(i).findIndex(marker.getTitle()) != null) {
                        marker_number = Marker.get(i).findIndex(marker.getTitle());
                        Log.d(TAG, "marker_number " + marker_number);
                    }
                } // marker title로 clinic을 검색하여 number 반환받아옴
                final int marker_ID_number = Integer.parseInt(marker_number);
                Log.d(TAG, "marker number = " + String.valueOf(marker_ID_number));
                Log.d(TAG, "marker clinic name = " + Marker.get(marker_ID_number - 1).getName());
                String monday = Marker.get(marker_ID_number - 1).getMonday();
                String tuesday = Marker.get(marker_ID_number - 1).getTuesday();
                String wednesday = Marker.get(marker_ID_number - 1).getWednesday();
                String thursday = Marker.get(marker_ID_number - 1).getThursday();
                String friday = Marker.get(marker_ID_number - 1).getFriday();
                String saturday = Marker.get(marker_ID_number - 1).getSaturday();
                String sunday = Marker.get(marker_ID_number - 1).getSunday();
                String holiday = Marker.get(marker_ID_number - 1).getHoliday();

                if (monday.equals("정보없음")) {
                    monday = "휴무";
                } else {
                    monday = monday.substring(0, 2) + ":" + monday.substring(2, 4) + " ~ " + monday.substring(4, 6) + ":" + monday.substring(6, 8);
                }

                if (tuesday.equals("정보없음")) {
                    tuesday = "휴무";
                } else {
                    tuesday = tuesday.substring(0, 2) + ":" + tuesday.substring(2, 4) + " ~ " + tuesday.substring(4, 6) + ":" + tuesday.substring(6, 8);
                }

                if (wednesday.equals("정보없음")) {
                    wednesday = "휴무";
                } else {
                    wednesday = wednesday.substring(0, 2) + ":" + wednesday.substring(2, 4) + " ~ " + wednesday.substring(4, 6) + ":" + wednesday.substring(6, 8);
                }

                if (thursday.equals("정보없음")) {
                    thursday = "휴무";
                } else {
                    thursday = thursday.substring(0, 2) + ":" + thursday.substring(2, 4) + " ~ " + thursday.substring(4, 6) + ":" + thursday.substring(6, 8);
                }

                if (friday.equals("정보없음")) {
                    friday = "휴무";
                } else {
                    friday = friday.substring(0, 2) + ":" + friday.substring(2, 4) + " ~ " + friday.substring(4, 6) + ":" + friday.substring(6, 8);
                }

                if (saturday.equals("정보없음")) {
                    saturday = "휴무";
                } else {
                    saturday = saturday.substring(0, 2) + ":" + saturday.substring(2, 4) + " ~ " + saturday.substring(4, 6) + ":" + saturday.substring(6, 8);
                }

                if (sunday.equals("정보없음")) {
                    sunday = "휴무";
                } else {
                    sunday = sunday.substring(0, 2) + ":" + sunday.substring(2, 4) + " ~ " + sunday.substring(4, 6) + ":" + sunday.substring(6, 8);
                }

                if (holiday.equals("정보없음")) {
                    holiday = "휴무";
                } else {
                    holiday = holiday.substring(0, 2) + ":" + holiday.substring(2, 4) + " ~ " + holiday.substring(4, 6) + ":" + holiday.substring(6, 8);
                }


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("병원정보");
                builder.setMessage(
                        "이름 : " + Marker.get(marker_ID_number - 1).getName() +
                                "\n주소 : " + Marker.get(marker_ID_number - 1).getAddress() +
                                "\n전화번호 : " + Marker.get(marker_ID_number - 1).getPhoneNumber() +
                                "\n종류 : " + Marker.get(marker_ID_number - 1).getSample() +
                                "\n※운영시간※ " +
                                "\n월요일 : " + monday +
                                "\n화요일 : " + tuesday +
                                "\n수요일 : " + wednesday +
                                "\n목요일 : " + thursday +
                                "\n금요일 : " + friday +
                                "\n토요일 : " + saturday +
                                "\n일요일 : " + sunday +
                                "\n공휴일 : " + holiday
                );
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("리뷰보기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Review_hos = Marker.get(marker_ID_number - 1).getName();
                        Intent intent = new Intent(search_hAct.this, ReviewshActivity.class);
                        intent.putExtra("Review_hos", Review_hos);
                        startActivity(intent);
                    }
                });

                builder.setNeutralButton("전화걸기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Marker.get(Integer.parseInt(String.valueOf(marker_ID_number - 1))).getPhoneNumber())));
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });// 마커 클릭 시 Alert Dialog가 나오도록 설정

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mgoogleMap.setMyLocationEnabled(true);
            } else {
                // Permission was denied. Display an error message.
            }
        }
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "현재위치:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "현재 위치로 이동", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }
}



