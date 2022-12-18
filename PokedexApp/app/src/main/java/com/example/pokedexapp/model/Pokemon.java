package com.example.pokedexapp.model;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Pokemon implements Serializable {

    private int id;
    private String name;
    private String imageURL;
    private List<Type> types;
    private boolean favorite;
    private String definition;
    private int height;
    private int weight;
    private List<Ability> abilities;
    private List<Stat> stats;
    private int totalStats;
    private List<Pokemon> evolutions;

    private Bitmap bitmap;

    public Pokemon (Pokemon pokemonCopia) {
        this.id = pokemonCopia.getId();
        this.name = pokemonCopia.getName();
        this.imageURL = pokemonCopia.getImageURL();
        this.types = pokemonCopia.getTypes();
        this.favorite = pokemonCopia.isFavorite();
        this.definition = pokemonCopia.getDefinition();
        this.height = pokemonCopia.getHeight();
        this.weight = pokemonCopia.getWeight();
        this.abilities = pokemonCopia.getAbilities();
        this.stats = pokemonCopia.getStats();
        this.totalStats = pokemonCopia.getTotalStats();
        this.evolutions = pokemonCopia.getEvolutions();
    }

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

    public Integer getId() {
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

    public List<Pokemon> getEvolutions() {
        return evolutions;
    }

    public void setEvolutions(List<Pokemon> evolutions) {
        this.evolutions = evolutions;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        imageURL = null;
    }

    public String getPokemonId() {
        String text;
        text = "#";
        if (id < 10) {
            text += "00";
        } else if (id < 100) {
            text += "0";
        }
        text += id;
        return text;
    }

    public String getPokemonName() {
        String text;
        text = name.substring(0, 1).toUpperCase();
        text += name.substring(1, name.length());

        return text;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return id == pokemon.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
