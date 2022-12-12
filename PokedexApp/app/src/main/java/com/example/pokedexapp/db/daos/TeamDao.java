package com.example.pokedexapp.db.daos;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.pokedexapp.db.entities.PokemonDB;
import com.example.pokedexapp.db.entities.TeamDB;
import com.example.pokedexapp.model.Pokemon;

import java.util.List;

@Dao
public interface  TeamDao {

    @Query("SELECT * FROM team")
    List<TeamDB> getTeams();

    @Query("SELECT * FROM team WHERE id = :idTeam")
    TeamDB getTeamById(int idTeam);

    @Query("SELECT p.id, p.name, p.favorite FROM team t " +
            "INNER JOIN pokemon p on p.id = t.pokemon_id " +
            "WHERE t.id = :idTeam")
    List<PokemonDB> getPokemonsByTeamId(int idTeam);

    @Update
    void updateTeam(TeamDB teamDB);

    @Insert
    void insertTeam(TeamDB teamDB);

    @Insert
    void insertPokemonByTeam(TeamDB teamDB, PokemonDB pokemonDB);

    @Delete
    void deleteTeam (TeamDB teamDB);
}
