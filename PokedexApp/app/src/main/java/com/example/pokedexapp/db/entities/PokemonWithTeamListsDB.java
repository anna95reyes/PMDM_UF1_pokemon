package com.example.pokedexapp.db.entities;

import androidx.room.Dao;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class PokemonWithTeamListsDB {

    public PokemonWithTeamListsDB() {
    }

    @Embedded public TeamDB team;
    @Relation(
            parentColumn = "id",
            entityColumn = "pokemon_id",
            entity = PokemonDB.class
    )
    public List<PokemonDB> pokemons;

    public TeamDB getTeam() {
        return team;
    }

    public void setTeam(TeamDB team) {
        this.team = team;
    }

    public List<PokemonDB> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<PokemonDB> pokemons) {
        this.pokemons = pokemons;
    }
}
