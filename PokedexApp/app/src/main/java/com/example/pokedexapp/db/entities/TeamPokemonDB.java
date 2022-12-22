package com.example.pokedexapp.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import java.util.List;

@Entity(tableName = "team_pokemon",
        primaryKeys = {"team_id", "pokemon_id"})
public class TeamPokemonDB {

    @NonNull
    @ColumnInfo(name = "team_id")
    public Integer teamId;
    @NonNull
    @ColumnInfo(name = "pokemon_id")
    public Integer pokemonId;
}
