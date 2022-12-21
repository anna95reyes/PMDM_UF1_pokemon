package com.example.pokedexapp.viewmodel;

import android.app.Application;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.example.pokedexapp.api.PokemonApi;
import com.example.pokedexapp.api.TypeApi;
import com.example.pokedexapp.db.AppDatabase;
import com.example.pokedexapp.db.daos.PokemonDao;
import com.example.pokedexapp.db.daos.TeamDao;
import com.example.pokedexapp.db.entities.PokemonDB;
import com.example.pokedexapp.db.entities.TeamDB;
import com.example.pokedexapp.model.Ability;
import com.example.pokedexapp.model.Pokemon;
import com.example.pokedexapp.model.Stat;
import com.example.pokedexapp.model.Team;
import com.example.pokedexapp.model.Type;

import java.io.Closeable;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivityViewModel extends AndroidViewModel {

    public MutableLiveData<List<Pokemon>> mGetPokemons;
    public MutableLiveData<List<Type>> mGetTypes;
    public MutableLiveData<List<Type>> mGetTypesFiltre;
    public MutableLiveData<List<Team>> mGetTeams;
    public MutableLiveData<Integer> mGetMaxIndexTeam;
    private static List<Pokemon> mLlistaPokemons;
    private static List<Type> mLlistaTypes;
    private static List<Type> mLlistaTypesFiltre;
    private static List<Team> mLlistaTeams;

    public MainActivityViewModel (@NonNull Application application) {
        super(application);
        mGetPokemons = new MutableLiveData<List<Pokemon>>();
        mGetTypes = new MutableLiveData<List<Type>>();
        mGetTypesFiltre = new MutableLiveData<List<Type>>();
        mGetTeams = new MutableLiveData<List<Team>>();
        mGetMaxIndexTeam = new MutableLiveData<Integer>();
    }

    public void getPokemons(File jsonFolder){
        if (mLlistaPokemons == null || mLlistaPokemons.size() == 0) {
            mLlistaPokemons = new ArrayList<Pokemon>();
            Observable.fromCallable(() -> {
                mLlistaPokemons = PokemonApi.getLlistaPokemons(jsonFolder);
                insertPokemonsFavorits();
                getPokemonsFavorits();
                mGetPokemons.postValue(mLlistaPokemons);
                return 1;
            }).subscribeOn(Schedulers.io()).subscribe();
        } else {
            mGetPokemons.postValue(mLlistaPokemons);
        }

    }

    public void insertPokemonsFavorits() {
        Observable.fromCallable(() -> {
            PokemonDao dao = appDatabase().pokemonDao();
            for (Pokemon p : mLlistaPokemons) {
                if (dao.getPokemonById(p.getId()) == null) {
                    PokemonDB pokemonDB = new PokemonDB(p.getId(), p.getName(), p.isFavorite());
                    dao.insertPokemon(pokemonDB);
                }
            }
            return 1;
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    public void getPokemonsFavorits() {
        Observable.fromCallable(() -> {
            PokemonDao dao = appDatabase().pokemonDao();
            List<PokemonDB> pokemonsDB = dao.getPokemonsFavorits();

            for (PokemonDB pDB : pokemonsDB) {
                int id = mLlistaPokemons.indexOf(getPokemonPerId(pDB.id));
                mLlistaPokemons.get(id).setFavorite(pDB.favorite);
            }
            return 1;
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    public void updatePokemonFavorit(Pokemon p) {
        Observable.fromCallable(() -> {
            PokemonDao dao = appDatabase().pokemonDao();
            dao.updatePokemonFavorite(p.getId(), p.isFavorite());
            return 1;
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    public void getTypes(File jsonFolder) {
        if (mLlistaTypes == null || mLlistaTypes.size() == 0) {
            mLlistaTypes = new ArrayList<Type>();
            Observable.fromCallable(() -> {
                mLlistaTypes = TypeApi.getLlistaTypes(jsonFolder);
                mGetTypes.postValue(mLlistaTypes);
                return 1;
            }).subscribeOn(Schedulers.io()).subscribe();
        } else {
            mGetTypes.postValue(mLlistaTypes);
        }

    }

    public List<Type> getTypesFiltre() {
        if (mLlistaTypesFiltre == null || mLlistaTypesFiltre.size() == 0) {
            mLlistaTypesFiltre = new ArrayList<Type>();
            mLlistaTypesFiltre.add(new Type("all types"));
            for (int i = 0; i < mLlistaTypes.size(); i++){
                mLlistaTypesFiltre.add(mLlistaTypes.get(i));
            }
        }

        mGetTypesFiltre.postValue(mLlistaTypesFiltre);
        return mLlistaTypesFiltre;
    }

    /*public List<Team> getTeams(){
        if (mLlistaTeams == null) {
            mLlistaTeams = new ArrayList<Team>();
            Observable.fromCallable(() -> {
                Team team = new Team(0, "Team 1");
                team.addPokemon(0,mLlistaPokemons.get(0));
                team.addPokemon(1, mLlistaPokemons.get(1));
                team.addPokemon(2, mLlistaPokemons.get(2));
                team.addPokemon(3, mLlistaPokemons.get(3));
                team.addPokemon(4, mLlistaPokemons.get(4));
                team.addPokemon(5, mLlistaPokemons.get(5));
                mLlistaTeams.add(team);

                team = new Team(1, "Team 2");
                team.addPokemon(0, mLlistaPokemons.get(6));
                team.addPokemon(1, mLlistaPokemons.get(7));
                team.addPokemon(2, mLlistaPokemons.get(8));
                team.addPokemon(3, null);
                team.addPokemon(4, null);
                team.addPokemon(5, null);
                mLlistaTeams.add(team);
                mGetTeams.postValue(mLlistaTeams);
                return 1;
            }).subscribeOn(Schedulers.io()).subscribe();
        } else {
            mGetTeams.postValue(mLlistaTeams);
        }
        return mLlistaTeams;
    }*/

    public void getTeams(){
        if (mLlistaTeams == null) {
            mLlistaTeams = new ArrayList<Team>();
            Observable.fromCallable(() -> {
                TeamDao dao = appDatabase().teamDao();
                List<TeamDB> teams = dao.getTeams();
                for (TeamDB teamDB : teams) {
                    Team team = new Team(teamDB.id, teamDB.name);
                    List<PokemonDB> pokemons = dao.getPokemonsByTeamId(teamDB.id);
                    for (int i = 0; i < pokemons.size(); i++) {
                        PokemonDB pokemonDB = pokemons.get(i);
                        Pokemon pokemon = new Pokemon(getPokemonPerId(pokemonDB.id));
                        team.addPokemon(i, pokemon);
                    }
                    if (!mLlistaTeams.contains(team)) {
                        mLlistaTeams.add(team);
                    }
                }
                mGetTeams.postValue(mLlistaTeams);
                return 1;
            }).subscribeOn(Schedulers.io()).subscribe();
        } else {
            mGetTeams.postValue(mLlistaTeams);
        }
    }

    public Integer insertTeam(Team team){
        if (mLlistaTeams == null) mLlistaTeams = new ArrayList<Team>();
        Observable.fromCallable(() -> {
            TeamDao dao = appDatabase().teamDao();
            if (dao.getTeamById(team.getId()) == null) {
                TeamDB teamDB = new TeamDB(team.getName());
                dao.insertTeam(teamDB);
                Log.d("XXX", "mLlistaTeams: " + mLlistaTeams +
                        " - " + mLlistaTeams.contains(team));
                Log.d("XXX", "team: " + team);
                if (!mLlistaTeams.contains(team)) mLlistaTeams.add(team);
            }
            return 1;
        }).subscribeOn(Schedulers.io()).subscribe();
        return team.getId();
    }

    public void insertPokemonTeam (Integer posicio, Team team, Pokemon pokemon) {
        Log.d("XXX", "team: " + team);
        Log.d("XXX", "pokemon: " + pokemon);
        if (mLlistaTeams.contains(team)) {
            Observable.fromCallable(() -> {
                TeamDao teamDao = appDatabase().teamDao();
                PokemonDao pokemonDao = appDatabase().pokemonDao();
                TeamDB teamDB = teamDao.getTeamById(team.getId());
                PokemonDB pokemonDB = pokemonDao.getPokemonById(pokemon.getId());
                Log.d("XXX", "teamDB: " + teamDB);
                Log.d("XXX", "pokemonDB: " + pokemonDB);
                if (teamDB != null) {
                    teamDao.insertPokemonByTeam(teamDB.id, pokemonDB.id);
                    mLlistaTeams.get(mLlistaTeams.indexOf(team)).addPokemon(posicio, pokemon);
                }
                return 1;
            }).subscribeOn(Schedulers.io()).subscribe();
        }
    }

    public static Pokemon getPokemonPerId(Integer idPokemon) {
        Pokemon pokemon = null;
        int i = 0;
        while (i < mLlistaPokemons.size() && !mLlistaPokemons.get(i).getId().equals(idPokemon)) {
            i++;
        }

        if (i < mLlistaPokemons.size()){
            pokemon = mLlistaPokemons.get(i);
        }

        return pokemon;
    }

    public AppDatabase appDatabase(){
        AppDatabase db = Room.databaseBuilder(getApplication().getApplicationContext(),
                AppDatabase.class, "database-name").build();

        return db;
    }

}
