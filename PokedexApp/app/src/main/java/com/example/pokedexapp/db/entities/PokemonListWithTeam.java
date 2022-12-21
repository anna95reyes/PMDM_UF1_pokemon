package com.example.pokedexapp.db.entities;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class PokemonListWithTeam {
    @Embedded
    public TeamDB teamDB;
    @Relation(
            parentColumn = "team_id",
            entityColumn = "pokemon_id",
            associateBy = @Junction(TeamPokemonDB.class)
    )
    public List<PokemonDB> pokemonsDB;
}
