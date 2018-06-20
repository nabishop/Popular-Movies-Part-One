package com.example.android.popularmovies.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Nick on 6/18/2018.
 */

public class Connection {
    protected static String urlMovieRequest(URL url) {
        try {
            String jsonResponse = null;
            InputStream inputStream = null;
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(15000);
                urlConnection.setConnectTimeout(20000);
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                // 200 is good, 401 is unauthorized
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readStream(inputStream);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return jsonResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String readStream(InputStream inputStream) {
        return null;
    }

}
