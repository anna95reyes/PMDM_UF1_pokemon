package com.example.pokedexapp.db.daos;



import androidx.room.Dao;
import androidx.room.Query;

import com.example.pokedexapp.db.entities.TeamDB;
import com.example.pokedexapp.model.Team;

import java.util.List;

@Dao
public interface TeamDao {

    @Query("SELECT * FROM team")
    List<TeamDB> getAll();
}
