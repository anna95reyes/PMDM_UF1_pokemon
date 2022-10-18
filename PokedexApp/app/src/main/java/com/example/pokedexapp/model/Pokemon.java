package com.example.pokedexapp.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Pokemon {

    private static List<Pokemon> _pokemons;

    private int id; //id
    private String name; //name
    private String imageURL; //sprites/front_default
    private List<Type> types = new ArrayList<>(); //types/0/type/name
    private boolean favorite;
    private String definition; //species/url -> flavor_text_entries/0/flavor_text
    private int height; //height
    private int weight; //weight
    private List<Ability> abilities = new ArrayList<>(); //abilities/0/ability/name i //abilities/0/is_hidden
    private List<Stat> stats = new ArrayList<>(); //stats/0/base_stats i stats/0/stat/name
    private int totalStats; // camp calculat
    private Pokemon evolution; //evolution_chain/url

    private Bitmap bitmap;
    private boolean selected;


    public Pokemon(int id, String name, String imageURL, List<Type> types, boolean favorite, String definition,
                   int height, int weight, List<Ability> abilities, List<Stat> stats) {
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
        this.types = types;
        this.favorite = favorite;
        this.definition = definition;
        this.height = height;
        this.weight = weight;
        this.abilities = abilities;
        this.stats = stats;
    }

    public Pokemon(int id, String name, String imageURL, List<Type> types, boolean favorite, String definition,
                   int height, int weight, List<Ability> abilities, List<Stat> stats, Pokemon evolution) {
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
        this.types = types;
        this.favorite = favorite;
        this.definition = definition;
        this.height = height;
        this.weight = weight;
        this.abilities = abilities;
        this.stats = stats;
        this.evolution = evolution;
    }

    public static List<Pokemon> getPokemons(){
        if (_pokemons==null) {
            _pokemons = new ArrayList<>();
            //_pokemons.add(new Pokemon());
        }

        return _pokemons;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    public List<Stat> getStats() {
        return stats;
    }

    public void setStats(List<Stat> stats) {
        this.stats = stats;
    }

    public int getTotalStats() {
        return totalStats;
    }

    private void setTotalStats(int totalStats) {
        this.totalStats = totalStats;
    }

    public Pokemon getEvolution() {
        return evolution;
    }

    public void setEvolution(Pokemon evolution) {
        this.evolution = evolution;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        imageURL = null;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


}
