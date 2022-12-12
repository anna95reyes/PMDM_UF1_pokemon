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
    public int id;
    public String name;
    public boolean favorite;

    public PokemonDB() {
    }
}
