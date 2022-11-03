package com.example.pokedexapp.utils;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;

public class NetworkUtils {

    public static String getJSon( String url ) {
        try
        {
            BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
            ByteArrayOutputStream  stream = new ByteArrayOutputStream();

             byte dataBuffer[] = new byte[1024];
             int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                stream.write(dataBuffer, 0, bytesRead);
            }
            return new String(stream.toByteArray());
        } catch (Exception e) {
            Log.e("APP","Error descarregant JSON", e);
            return null;
        }
    }
}
