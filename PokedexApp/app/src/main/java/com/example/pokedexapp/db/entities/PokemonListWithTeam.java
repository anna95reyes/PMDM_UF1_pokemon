package com.example.pokedexapp.db.entities;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class PokemonListWithTeam {
    @Embedded public PokemonDB pokemonDB;
    @Relation(
            parentColumn = "pokemon_id",
            entityColumn = "team_id",
            associateBy = @Junction(TeamPokemonDB.class)
    )
    public List<TeamDB> teamsDB;
}
