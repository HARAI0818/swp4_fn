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


public class newsAct<privated> extends AppCompatActivity {

    private NewsRecyclerAdapter news_adapter;
    private ArrayList<NewsData> NewsDatalist = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.news_activity_main);

        new NewsAsyncTask().execute();


    }



    private class NewsAsyncTask extends AsyncTask<Void, Void, Void> {

        //진행바표시
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //진행다일로그 시작
            progressDialog = new ProgressDialog(newsAct.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("잠시 기다려 주세요.");
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect("https://news.naver.com/main/list.nhn?mode=LS2D&mid=shm&sid1=103&sid2=241").get();
                Elements mElementDataSize = doc.select("ul.type06_headline").select("li"); //필요한 녀석만 꼬집어서 지정
                //int mElementSize = mElementDataSize.size(); //목록이 몇개인지 알아낸다. 그만큼 루프를 돌려야 하나깐.

                for(Element elem : mElementDataSize){ //이렇게 요긴한 기능이
                    //영화목록 <li> 에서 다시 원하는 데이터를 추출해 낸다.
                    String my_title = elem.select("a").text();
                    String my_link = elem.select("a").attr("href");
                    String my_imgUrl = elem.select("img").attr("src");


                    //특정하기 힘들다... 저 앞에 있는집의 오른쪽으로 두번째집의 건너집이 바로 우리집이야 하는 식이다.

                    Document sub_doc = Jsoup.connect(my_link).get();


                    String my_body = sub_doc.select("div#articleBodyContents").text();//.select("br");


                    NewsDatalist.add(new NewsData(my_title, my_body,  my_imgUrl/*R.drawable.custom_image_background*/));
                }

                //추출한 전체 <li> 출력해 보자.
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //ArraList를 인자로 해서 어답터와 연결한다.


            RecyclerView recyclerView = findViewById(R.id.recyclerView);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(linearLayoutManager);

            news_adapter = new NewsRecyclerAdapter(NewsDatalist);
            recyclerView.setAdapter(news_adapter);


            progressDialog.dismiss();
        }
    }


}
