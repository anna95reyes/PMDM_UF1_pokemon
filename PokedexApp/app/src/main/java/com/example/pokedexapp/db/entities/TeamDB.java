package com.example.pokedexapp.db.entities;


import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Relation;

import com.example.pokedexapp.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "team")
public class TeamDB {
    @PrimaryKey(autoGenerate = true)
    public Integer id;
    public String name;
    public Boolean complert;
    @ColumnInfo(name = "pokemon_id")
    public Integer pokemonId;

    public TeamDB() {
    }

    public TeamDB(String name) {
        this.name = name;
        this.complert = false;
    }
}
