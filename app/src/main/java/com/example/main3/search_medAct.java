package com.example.main3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.net.URL;

public class search_medAct extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_med);

        StrictMode.enableDefaults();

        TextView status1 = (TextView)findViewById(R.id.result); //파싱된 결과확인!

        boolean initem = false, inadtStaDd = false, ininjcPthNm = false, initmNm = false, inmnfEntpNm = false;
        boolean inspcGnlTpNm = false, inunit = false;

        String adtStaDd = null, injcPthNm = null, itmNm = null, mnfEntpNm = null, spcGnlTpNm = null, unit =null;


        try{
            URL url = new URL("http://apis.data.go.kr/B551182/dgamtCrtrInfoService/getDgamtList?"
                    + "ServiceKey="
                    + "fqohCJcaxugSk5xKkNh1C67P65KYFl%2FIMEazJNS%2BAh3hESGxmGfSVRfI1tjNIrt%2F1AAwvkVJTgYXher0dfJTXQ%3D%3D&numOfRows=10&pageNo=1&mdsCd=6568000"
            ); //검색 URL부분

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();
            System.out.println("파싱시작합니다.");

            while (parserEvent != XmlPullParser.END_DOCUMENT){
                switch(parserEvent){
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if(parser.getName().equals("adtStaDd")){ //title 만나면 내용을 받을수 있게 하자
                            inadtStaDd = true;
                        }
                        if(parser.getName().equals("injcPthNm")){ //address 만나면 내용을 받을수 있게 하자
                            ininjcPthNm = true;
                        }
                        if(parser.getName().equals("itmNm")){ //mapx 만나면 내용을 받을수 있게 하자
                            initmNm = true;
                        }
                        if(parser.getName().equals("mnfEntpNm")){ //mapy 만나면 내용을 받을수 있게 하자
                            inmnfEntpNm = true;
                        }
                        if(parser.getName().equals("spcGnlTpNm")){ //mapy 만나면 내용을 받을수 있게 하자
                            inspcGnlTpNm = true;
                        }
                        if(parser.getName().equals("unit")){ //mapy 만나면 내용을 받을수 있게 하자
                            inunit = true;
                        }

                        if(parser.getName().equals("message")){ //message 태그를 만나면 에러 출력
                            status1.setText(status1.getText()+"에러");
                            //여기에 에러코드에 따라 다른 메세지를 출력하도록 할 수 있다.
                        }
                        break;

                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if(inadtStaDd){ //isTitle이 true일 때 태그의 내용을 저장.
                            adtStaDd = parser.getText();
                            inadtStaDd = false;
                        }
                        if(ininjcPthNm){ //isAddress이 true일 때 태그의 내용을 저장.
                            injcPthNm = parser.getText();
                            ininjcPthNm = false;
                        }
                        if(initmNm){ //isMapx이 true일 때 태그의 내용을 저장.
                            itmNm = parser.getText();
                            initmNm = false;
                        }
                        if(inmnfEntpNm){ //isMapy이 true일 때 태그의 내용을 저장.
                            mnfEntpNm = parser.getText();
                            inmnfEntpNm = false;
                        }
                        if(inspcGnlTpNm){ //isMapy이 true일 때 태그의 내용을 저장.
                            spcGnlTpNm = parser.getText();
                            inspcGnlTpNm = false;
                        }
                        if(inunit){ //isMapy이 true일 때 태그의 내용을 저장.
                            unit = parser.getText();
                            inunit = false;
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("item")){
                            status1.setText(status1.getText()+"약품적용시작날짜 : "+ adtStaDd +"\n 투여방법: "+ injcPthNm +"\n 품목 명 : " + itmNm
                                    +"\n 제조업체명 : " + mnfEntpNm +  "\n 전문일반구분 : " + spcGnlTpNm + "\n 단위 : " + unit
                                    +"\n");
                            initem = false;
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch(Exception e){
            status1.setText("에러가..났습니다...");
        }
    }
}
