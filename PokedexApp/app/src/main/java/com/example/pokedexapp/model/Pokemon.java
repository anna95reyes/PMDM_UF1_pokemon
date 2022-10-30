package com.example.pokedexapp.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Pokemon {

    //private static List<Pokemon> _pokemons;

    private int id; //id
    private String name; //name
    private String imageURL; //sprites/front_default
    private List<Type> types; //types/0/type/name
    private boolean favorite;
    private String definition; //species/url -> flavor_text_entries/0/flavor_text
    private int height; //height
    private int weight; //weight
    private List<Ability> abilities; //abilities/0/ability/name i //abilities/0/is_hidden
    private List<Stat> stats; //stats/0/base_stats i stats/0/stat/name
    private int totalStats; // -- camp calculat
    private Pokemon previousEvolution; //species/url -> evolution_chain/url --> chain/species/name
    private List<Pokemon> nextEvolution; //species/url -> evolution_chain/url --> species/name

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
                   int height, int weight, List<Ability> abilities, List<Stat> stats, Pokemon previousEvolution) {
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
        this.previousEvolution = previousEvolution;
    }

    public Pokemon(int id, String name, String imageURL, List<Type> types, boolean favorite, String definition,
                   int height, int weight, List<Ability> abilities, List<Stat> stats, List<Pokemon> nextEvolution) {
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
        this.nextEvolution = nextEvolution;
    }

    public Pokemon(int id, String name, String imageURL, List<Type> types, boolean favorite, String definition,
                   int height, int weight, List<Ability> abilities, List<Stat> stats, Pokemon previousEvolution,
                   List<Pokemon> nextEvolution) {
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
        this.previousEvolution = previousEvolution;
        this.nextEvolution = nextEvolution;
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
        totalStats = 0;
        for (int i = 0; i < stats.size(); i++){
            totalStats += stats.get(i).getBase_stat();
        }
        return totalStats;
    }

    public Pokemon getPreviousEvolution() {
        return previousEvolution;
    }

    public void setPreviousEvolution(Pokemon previousEvolution) {
        this.previousEvolution = previousEvolution;
    }

    public List<Pokemon> getNextEvolution() {
        return nextEvolution;
    }

    public void setNextEvolution(List<Pokemon> nextEvolution) {
        this.nextEvolution = nextEvolution;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return id == pokemon.id && favorite == pokemon.favorite && height == pokemon.height && weight == pokemon.weight && totalStats == pokemon.totalStats && name.equals(pokemon.name) && imageURL.equals(pokemon.imageURL) && types.equals(pokemon.types) && definition.equals(pokemon.definition) && abilities.equals(pokemon.abilities) && stats.equals(pokemon.stats) && Objects.equals(previousEvolution, pokemon.previousEvolution) && Objects.equals(nextEvolution, pokemon.nextEvolution);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, imageURL, types, favorite, definition, height, weight, abilities, stats, totalStats, previousEvolution, nextEvolution);
    }
}
