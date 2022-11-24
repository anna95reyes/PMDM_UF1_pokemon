package com.example.pokedexapp.api;

import android.util.Log;

import com.example.pokedexapp.model.Ability;
import com.example.pokedexapp.model.Pokemon;
import com.example.pokedexapp.model.Stat;
import com.example.pokedexapp.model.Type;
import com.example.pokedexapp.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PokemonApi {

    private static Integer maxPokemons = 386; //Fins a la tecera generacio de pokemons
    private static List<Pokemon> llistaPokemons;
    private static Pokemon pokemon;

    public static List<Pokemon> getLlistaPokemons(File jsonFolder) {

        llistaPokemons = new ArrayList<Pokemon>();

        //Quan al json hi haiguin mes pokemons dels que hi actualment, la llista de pokemons s'actualitzara
        //ja que mira si ha aumentat el numero de pokemons i si es aixi torna a descarregar el json dels pokemons

        //Ho deixo comentat perque si no ha de descarregar mols pokemons i triga bastant encara que estigui els jsons el local
        /*
        Integer qtatPokemons = 0;

        String jsonNumPokemons = NetworkUtils.getJSon(jsonFolder, "qtat_pokemons.json","https://pokeapi.co/api/v2/pokemon");
        try {
            JSONObject typeObj = new JSONObject(jsonNumPokemons);
            qtatPokemons = typeObj.getInt("count");

        } catch (JSONException e) {
            Log.e("POKEMON", "error en el JSON LLISTA POKEMONS", e);
        }

        if (maxPokemons != qtatPokemons) {
            File arxiuCache = new File(jsonFolder, "pokemons.json");
            if (arxiuCache.exists()) {
                arxiuCache.delete();
            }
            maxPokemons = qtatPokemons;
        }
        */
        String json = NetworkUtils.getJSon(jsonFolder, "pokemons.json","https://pokeapi.co/api/v2/pokemon?offset=0&limit="+maxPokemons);

        try {
            JSONObject typeObj = new JSONObject(json);
            JSONArray results = typeObj.getJSONArray("results");
            for (int i = 0; i < results.length(); i++){
                JSONObject pokemon = results.getJSONObject(i);
                llistaPokemons.add(getPokemon(jsonFolder, pokemon.getString("name"), pokemon.getString("url")));
            }

        } catch (JSONException e) {
            Log.e("POKEMON", "error en el JSON LLISTA POKEMONS", e);
        }

        return llistaPokemons;
    }

    private static Pokemon getPokemon(File jsonFolder, String nomPokemon, String url) {

        String json = NetworkUtils.getJSon(jsonFolder, "pokemon_" + nomPokemon + ".json", url);

        try {
            JSONObject pokemonObj = new JSONObject(json);
            Integer id = pokemonObj.getInt("id");
            String name = pokemonObj.getString("name");
            JSONObject sprites = pokemonObj.getJSONObject("sprites");
            JSONObject other = sprites.getJSONObject("other");
            JSONObject official_artwork = other.getJSONObject("official-artwork");
            String imageURL = official_artwork.getString("front_default");
            JSONArray types = pokemonObj.getJSONArray("types");
            List<Type> llistaTypes = new ArrayList<Type>();
            for (int i = 0; i < types.length(); i++){
                JSONObject typesObject = types.getJSONObject(i);
                JSONObject type = typesObject.getJSONObject("type");
                llistaTypes.add(TypeApi.getType(type.getString("name")));
            }
            JSONObject species = pokemonObj.getJSONObject("species");
            String urlSpecies = species.getString("url");
            String jsonSpecies = NetworkUtils.getJSon(jsonFolder,
                    "species_" + species.getString("name") + ".json", urlSpecies);
            JSONObject speciesnObj = new JSONObject(jsonSpecies);
            JSONArray flavor_text_entries = speciesnObj.getJSONArray("flavor_text_entries");
            String definition = "";
            for (int i = 0; i < flavor_text_entries.length(); i++) {
                JSONObject  flavor_text_entries_object = flavor_text_entries.getJSONObject(i);
                JSONObject language = flavor_text_entries_object.getJSONObject("language");
                String nameLanguage = language.getString("name");

                if (nameLanguage.equals("en")) {
                    definition = flavor_text_entries.getJSONObject(i).getString("flavor_text");
                    break;
                }
            }
            Integer height = pokemonObj.getInt("height");
            Integer weight = pokemonObj.getInt("weight");

            JSONArray abilities = pokemonObj.getJSONArray("abilities");
            List<Ability> llistaAbilities = new ArrayList<Ability>();
            for (int i = 0; i < abilities.length(); i++){
                JSONObject abilitiesObject = abilities.getJSONObject(i);
                JSONObject ability = abilitiesObject.getJSONObject("ability");
                String nameAbility = ability.getString("name");
                Boolean is_hidden = abilitiesObject.getBoolean("is_hidden");
                llistaAbilities.add(new Ability(nameAbility, is_hidden));
            }

            JSONArray stats = pokemonObj.getJSONArray("stats");
            List<Stat> llistaStats = new ArrayList<Stat>();
            for (int i = 0; i < stats.length(); i++){
                JSONObject statsObject = stats.getJSONObject(i);
                Integer base_stat = statsObject.getInt("base_stat");
                JSONObject stat = statsObject.getJSONObject("stat");
                String nameStat = stat.getString("name");
                llistaStats.add(new Stat(nameStat, base_stat));
            }

            pokemon = new Pokemon(id, name, imageURL, llistaTypes, false, definition,
                    height, weight, llistaAbilities, llistaStats);

        } catch (JSONException e) {
            Log.e("POKEMON", "error en el JSON POKEMON", e);
        }

        return pokemon;

    }

}
