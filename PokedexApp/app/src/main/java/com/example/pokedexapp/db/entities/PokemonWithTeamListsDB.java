package com.example.pokedexapp.db.entities;

import androidx.room.Dao;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import java.util.List;

public class PokemonWithTeamListsDB {

    @Embedded public TeamDB team;
    @Relation(
            parentColumn = "id",
            entityColumn = "pokemon_id"
    )
    public List<PokemonDB> pokemons;
}
