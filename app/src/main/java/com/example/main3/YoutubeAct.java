package com.example.main3;


import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;

public class YoutubeAct<privated> extends AppCompatActivity {

    private Youtube_RecyclerAdapter youtube_adapter;
    private ArrayList<Youtube_Data> youtube_Datalist = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_activity_main);


        new YoutubeAsyncTask().execute();


    }




    private class YoutubeAsyncTask extends AsyncTask<Void, Void, Void> {

        //진행바표시
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //진행다일로그 시작
            progressDialog = new ProgressDialog(YoutubeAct.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("잠시 기다려 주세요.");
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(Void result) {


            RecyclerView recyclerView = findViewById(R.id.recyclerView);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(linearLayoutManager);

            youtube_adapter = new Youtube_RecyclerAdapter(youtube_Datalist);
            recyclerView.setAdapter(youtube_adapter);


            progressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect("https://www.google.com/search?newwindow=1&hl=ko&biw=1097&bih=554&tbm=vid&sxsrf=ALeKk03UppIXv9dvnUEb0e8niCifs9806Q%3A1590838295351&ei=F0TSXqvuFNPM-Qa5wYvoBw&q=site%3Awww.youtube.com+%EA%B1%B4%EA%B0%95+or+%EC%95%BD&oq=site%3Awww.youtube.com+%EA%B1%B4%EA%B0%95+or+%EC%95%BD&gs_l=psy-ab.3...784.784.0.1818.1.1.0.0.0.0.143.143.0j1.1.0....0...1c.1.64.psy-ab..0.0.0....0.ZahyN2Bz4TY").get();
                Elements mElementDataSize = doc.select("div#rso").select("div.rc"); //필요한 녀석만 꼬집어서 지정
                //int mElementSize = mElementDataSize.size(); //목록이 몇개인지 알아낸다. 그만큼 루프를 돌려야 하나깐.

                for(Element elem : mElementDataSize){ //이렇게 요긴한 기능이
                    //영화목록 <li> 에서 다시 원하는 데이터를 추출해 낸다.
                    String my_title = elem.select("h3.LC20lb.MMgsKf").text();  //.attr("title");
                    String my_link = elem.select("a").attr("href");

                    String my_imgUrl = elem.select("img.rISBZc.M4dUYb").attr("src");
                    //특정하기 힘들다... 저 앞에 있는집의 오른쪽으로 두번째집의 건너집이 바로 우리집이야 하는 식이다.

                    youtube_Datalist.add(new Youtube_Data(my_title, my_link,  my_imgUrl));
                }

                //추출한 전체 <li> 출력해 보자.

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}