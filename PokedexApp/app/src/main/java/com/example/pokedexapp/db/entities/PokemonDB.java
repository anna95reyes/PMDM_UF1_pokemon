package com.example.pokedexapp.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pokedexapp.model.Ability;
import com.example.pokedexapp.model.Pokemon;
import com.example.pokedexapp.model.Stat;
import com.example.pokedexapp.model.Type;

import java.util.List;

@Entity(tableName = "pokemon")
public class PokemonDB {
    @PrimaryKey
    private int id;
    private String name;
    private boolean favorite;

    public PokemonDB() {
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

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
