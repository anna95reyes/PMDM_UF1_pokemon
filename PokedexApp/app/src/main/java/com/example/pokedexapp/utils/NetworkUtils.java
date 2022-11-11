package com.example.pokedexapp.utils;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class NetworkUtils {

    public static String getJSon(File jsonFolder, String fileName, String url) {

        BufferedOutputStream fos = null;
        BufferedReader buffer = null;
        String json = "";

        try
        {
            File arxiuCache = new File(jsonFolder, fileName);

            if (arxiuCache.exists()) {
                FileReader arxiu = new FileReader(arxiuCache);
                buffer = new BufferedReader(arxiu);
                String aux;
                while((aux = buffer.readLine()) != null) {
                    json += aux;
                }
            }
            if (!arxiuCache.exists() || json.length() == 0) {
                fos = new BufferedOutputStream(new FileOutputStream(arxiuCache));
                BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                byte dataBuffer[] = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fos.write(dataBuffer, 0, bytesRead);
                    stream.write(dataBuffer, 0, bytesRead);
                }
                json =  new String(stream.toByteArray());
            }

            return json;
        } catch (Exception e) {
            Log.e("POKEMON","Error descarregant JSON", e);
            return null;
        }
        finally {
            if(fos!=null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    Log.e("POKEMON","Error tancant FOS", e);
                }
            }

        }
    }

    public static String getJSon(String url) {
        try
        {
            BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
            ByteArrayOutputStream  stream = new ByteArrayOutputStream();

            byte dataBuffer[] = new byte[1024];int bytesRead;
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
