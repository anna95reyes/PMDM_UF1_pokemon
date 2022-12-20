package com.example.pokedexapp.db.entities;

import androidx.room.ColumnInfo;
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
    public int id;
    public String name;
    public boolean favorite;

    public PokemonDB() {
    }

    public PokemonDB(int id, String name, boolean favorite) {
        this.id = id;
        this.name = name;
        this.favorite = favorite;
    }
}
