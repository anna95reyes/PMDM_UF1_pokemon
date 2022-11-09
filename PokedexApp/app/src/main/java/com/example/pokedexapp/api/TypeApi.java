package com.example.pokedexapp.api;

import android.util.Log;

import com.example.pokedexapp.model.Type;
import com.example.pokedexapp.utils.NetworkUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TypeApi {

    private static List<Type> llistaTypes;

    public static List<Type> getLlistaTypes(File jsonFolder) {

        llistaTypes = new ArrayList<Type>();

        String json = NetworkUtils.getJSon(jsonFolder, "types.json","https://pokeapi.co/api/v2/type");

        Log.d("POKEMON", "JSON: " + json);

        try {
            JSONObject typeObj = new JSONObject(json);
            JSONArray results = typeObj.getJSONArray("results");
            for (int i = 0; i < results.length(); i++){
                JSONObject type = results.getJSONObject(i);
                Type t = new Type(type.getString("name"));
                llistaTypes.add(t);
            }

        } catch (JSONException e) {
            Log.e("POKEMON", "error en el JSON");
        }

        return llistaTypes;
    }
}
