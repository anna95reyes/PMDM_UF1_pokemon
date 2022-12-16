package com.example.pokedexapp.db.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pokedexapp.db.entities.PokemonDB;
import com.example.pokedexapp.db.entities.TeamDB;

import java.util.List;

@Dao
public interface PokemonDao {
    @Query("SELECT * FROM pokemon")
    List<PokemonDB> getPokemons();

    @Query("SELECT * FROM pokemon WHERE favorite = 1")
    List<PokemonDB> getPokemonsFavorits();

    @Query("SELECT * FROM pokemon WHERE id = :idPokemon")
    PokemonDB getPokemonById(int idPokemon);

    @Query("UPDATE pokemon SET favorite = :favorite WHERE id = :idPokemon")
    void updatePokemonFavorite(Integer idPokemon, Boolean favorite);

    @Insert
    void insertPokemon(PokemonDB pokemonDB);

}
