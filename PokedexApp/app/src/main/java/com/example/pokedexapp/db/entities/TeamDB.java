package com.example.pokedexapp.db.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "team")
public class TeamDB {
    @PrimaryKey
    private Integer id;
    private String name;
    private Boolean complert;
    //private List<PokemonDB> pokemons;

    public TeamDB() {
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

    public Boolean getComplert() {
        return complert;
    }

    public void setComplert(Boolean complert) {
        this.complert = complert;
    }

    /*public List<PokemonDB> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<PokemonDB> pokemons) {
        this.pokemons = pokemons;
    }*/
}
