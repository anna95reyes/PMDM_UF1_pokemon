package com.example.pokedexapp.db.entities;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class TeamListWithPokemon {
    @Embedded
    public PokemonDB teamDB;
    @Relation(
            parentColumn = "team_id",
            entityColumn = "pokemon_id",
            associateBy = @Junction(TeamPokemonDB.class)
    )
    public List<TeamDB> pokemonsDB;
}
