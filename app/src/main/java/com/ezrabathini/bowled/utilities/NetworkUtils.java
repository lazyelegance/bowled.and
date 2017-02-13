package com.ezrabathini.bowled.utilities;

/**
 * Created by ezra on 13/02/17.
 */

import android.net.Uri;

import com.ezrabathini.bowled.data.StaticData;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class NetworkUtils {


    final static String MATCHES_URL = StaticData.BW_MATCHES_URL;
    final static String API_KEY = StaticData.BW_API_KEY;

    public static URL buildUrl() {
        Uri builtUri = Uri.parse(MATCHES_URL).buildUpon().build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.addRequestProperty("X-Mashape-Key", API_KEY);
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
