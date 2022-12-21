package com.example.pokedexapp.db.daos;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.pokedexapp.db.entities.PokemonDB;
import com.example.pokedexapp.db.entities.TeamDB;
import com.example.pokedexapp.model.Pokemon;

import java.util.List;

@Dao
public interface  TeamDao {

    @Transaction
    @Query("SELECT * FROM team")
    List<TeamDB> getTeams();

    @Transaction
    @Query("SELECT * FROM team WHERE id = :idTeam")
    TeamDB getTeamById(int idTeam);

    @Transaction
    @Query("SELECT p.id, p.name, p.favorite FROM pokemon p " +
            "INNER JOIN team_pokemon tp on tp.pokemon_id = p.id " +
            "WHERE tp.team_id = :idTeam")
    List<PokemonDB> getPokemonsByTeamId(int idTeam);

    @Transaction
    @Update
    void updateTeam(TeamDB teamDB);

    @Update
    void updatePokemonByTeam(TeamDB teamDB, PokemonDB pokemonDB);

    @Transaction
    @Insert
    void insertTeam(TeamDB teamDB);

    @Transaction
    @Query("INSERT INTO team_pokemon (team_id, pokemon_id) VALUES (:idTeam,:idPokemon)")
    void insertPokemonByTeam(Integer idTeam, Integer idPokemon);
}
