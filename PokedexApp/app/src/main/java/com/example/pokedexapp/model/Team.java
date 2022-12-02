package com.example.pokedexapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Team implements Serializable {

    private final Integer MAX_POKEMONS = 6;

    private Integer id;
    private String name;
    private Boolean complert;
    private List<Pokemon> pokemons;

    public Team(String name) {
        this.name = name;
        this.complert = false;
        this.pokemons = new ArrayList<Pokemon>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getComplert() {
        return complert;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public boolean addPokemon(Pokemon pokemon) {
        boolean afegit = false;
        if (pokemons.size() == MAX_POKEMONS) {
            complert = true;
        }
        if (!complert && !pokemons.contains(pokemon)) {
            pokemons.add(pokemon);
            afegit = true;
        }
        return afegit;
    }

    public boolean removePokemon(Pokemon pokemon) {
        return pokemons.remove(pokemon);
    }

}
