package com.hooneys.basicapitutorial.XmlPack;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

public class Weather {
    private static final String TAG = "Weather XML";

    public static String tempMinMax(String xml){
        String min = "";
        String max = "";
        String res = null;
        try{
            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput(new StringReader(xml));

            xpp.next();
            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        String tag = xpp.getName();
                        if(tag.equals("taMax3")){
                           xpp.next();
                           max = xpp.getText();
                        }

                        if(tag.equals("taMin3")){
                           xpp.next();
                           min = xpp.getText();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType= xpp.next();
            }

            res = "3일 후 \n";
            res += "최고 기온 : " + max + " / 최저 기온 : " + min;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            Log.d(TAG, "result : " + res);
            return res;
        }

    }
}
