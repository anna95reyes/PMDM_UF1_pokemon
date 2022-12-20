package com.example.pokedexapp.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.pokedexapp.db.daos.PokemonDao;
import com.example.pokedexapp.db.entities.TeamDB;
import com.example.pokedexapp.db.entities.PokemonDB;
import com.example.pokedexapp.db.daos.TeamDao;
import com.example.pokedexapp.db.entities.TeamPokemonDB;

@Database(entities = {TeamDB.class, PokemonDB.class, TeamPokemonDB.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TeamDao teamDao();
    public abstract PokemonDao pokemonDao();
}
