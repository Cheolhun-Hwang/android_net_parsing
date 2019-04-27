package com.hooneys.basicapitutorial.NetPack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Get {
    private static final String WEATHER_TEMP_URL = "http://newsky2.kma.go.kr/service/MiddleFrcstInfoService/getMiddleTemperature";

    public static String getTemperature(String key, String regId, String date,
                                        int no, int rows) {
        //중기기온조회
        HttpURLConnection conn = null;
        BufferedReader rd = null;
        String res = null;
        try{
            String get_url = makeUrl(key, regId, date, no, rows);
            URL url = new URL(get_url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/xml");
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                res =  sb.toString();
            }
            rd.close();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            conn.disconnect();
            return res;
        }
    }

    public static String makeUrl(String key, String regId, String date, int no, int rows){
        String url = WEATHER_TEMP_URL;
        url+= "?serviceKey="+key;
        url+= "&regId="+regId;
        url+= "&tmFc="+date;
        url+= "&pageNo="+no;
        url+= "&numOfRows="+rows;
        return url;
    }
}
