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

    private static Integer maxPokemons = 151; //Fins a la primera generacio de pokemons
    private static List<Pokemon> llistaPokemons;
    private static List<Pokemon> llistaEvolucions;
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

        for (int i = 0; i < llistaPokemons.size(); i++){
            llistaPokemons.get(i).setEvolutions(getEvolucions(jsonFolder, llistaPokemons.get(i), "https://pokeapi.co/api/v2/pokemon/"));
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
            JSONObject speciesObj = new JSONObject(jsonSpecies);
            JSONArray flavor_text_entries = speciesObj.getJSONArray("flavor_text_entries");
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

    private static List<Pokemon> getEvolucions (File jsonFolder, Pokemon pokemon, String url) {

        llistaEvolucions = new ArrayList<Pokemon>();

        String json = NetworkUtils.getJSon(jsonFolder, "pokemon_" + pokemon.getName() + ".json", url+pokemon.getId());

        try {
            JSONObject pokemonObj = new JSONObject(json);

            JSONObject species = pokemonObj.getJSONObject("species");
            String urlSpecies = species.getString("url");
            String jsonSpecies = NetworkUtils.getJSon(jsonFolder,
                    "species_" + species.getString("name") + ".json", urlSpecies);

            JSONObject speciesObj = new JSONObject(jsonSpecies);
            JSONObject evolution_chain = speciesObj.getJSONObject("evolution_chain");
            String urlEvolucions = evolution_chain.getString("url");

            String jsonEvolucions = NetworkUtils.getJSon(jsonFolder, "evolution_" + pokemon.getName() + ".json", urlEvolucions);

            JSONObject evolucionsObj = new JSONObject(jsonEvolucions);
            JSONObject chain = evolucionsObj.getJSONObject("chain");
            JSONObject speciesEvolucions = chain.getJSONObject("species");
            String evolucioBabyName = speciesEvolucions.getString("name");

            if (getPokemonPerNom(evolucioBabyName) != null) llistaEvolucions.add(getPokemonPerNom(evolucioBabyName));

            JSONArray primera_evolves_to = chain.getJSONArray("evolves_to");
            for (int i = 0; i < primera_evolves_to.length(); i++){
                JSONObject primera_evolves_toObject = primera_evolves_to.getJSONObject(i);
                JSONObject primeraSpeciesEvolucions = primera_evolves_toObject.getJSONObject("species");
                String primeraEvolucioName = primeraSpeciesEvolucions.getString("name");
                if (getPokemonPerNom(primeraEvolucioName) != null) llistaEvolucions.add(getPokemonPerNom(primeraEvolucioName));
                JSONArray segona_evolves_to = primera_evolves_toObject.getJSONArray("evolves_to");
                for (int j = 0; j < segona_evolves_to.length(); j++){
                    JSONObject segona_evolves_toObject = segona_evolves_to.getJSONObject(j);
                    JSONObject segonaSpeciesEvolucions = segona_evolves_toObject.getJSONObject("species");
                    String segonaEvolucioName = segonaSpeciesEvolucions.getString("name");
                    if (getPokemonPerNom(segonaEvolucioName) != null) llistaEvolucions.add(getPokemonPerNom(segonaEvolucioName));
                }

            }



        } catch (JSONException e) {
            Log.e("POKEMON", "error en el JSON POKEMON", e);
        }

        return llistaEvolucions;

    }

    public static Pokemon getPokemonPerNom(String name) {
        Pokemon pokemon = null;
        int i = 0;
        while (i < llistaPokemons.size() && !llistaPokemons.get(i).getName().equals(name)) {
            i++;
        }

        if (i < llistaPokemons.size()){
            pokemon = llistaPokemons.get(i);
        }

        return pokemon;
    }

}
