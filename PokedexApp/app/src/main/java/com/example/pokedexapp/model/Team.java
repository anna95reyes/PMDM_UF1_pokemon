package com.example.pokedexapp.model;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Team implements Serializable {

    private final Integer MAX_POKEMONS = 6;

    private Integer id;
    private String name;
    private Boolean complert;
    private List<Pokemon> pokemons;

    public Team(String name) {
        this.id = -1;
        this.name = name;
        this.complert = false;
        this.pokemons = new ArrayList<Pokemon>();
        for (int i = 0; i < MAX_POKEMONS; i++){
            this.pokemons.add(i, null);
        }
    }

    public Team(Integer id, String name) {
        this(name);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComplert(Boolean complert) {
        this.complert = complert;
    }

    public Boolean getComplert() {
        int i = 0;
        while (i < MAX_POKEMONS && pokemons.get(i) != null) {
            i++;
        }
        if (i == MAX_POKEMONS) {
            setComplert(true);
        }
        return complert;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public boolean addPokemon(int posicio, Pokemon pokemon) {
        boolean afegit = false;
        if (!getComplert() && !pokemons.contains(pokemon)) {
            pokemons.set(posicio, pokemon);
            afegit = true;
        }
        return afegit;
    }

    public boolean removePokemon(Pokemon pokemon) {
        return pokemons.remove(pokemon);
    }

}
