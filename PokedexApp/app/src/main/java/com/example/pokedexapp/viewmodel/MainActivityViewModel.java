package com.example.pokedexapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.example.pokedexapp.api.PokemonApi;
import com.example.pokedexapp.api.TypeApi;
import com.example.pokedexapp.db.AppDatabase;
import com.example.pokedexapp.db.daos.TeamDao;
import com.example.pokedexapp.model.Ability;
import com.example.pokedexapp.model.Pokemon;
import com.example.pokedexapp.model.Stat;
import com.example.pokedexapp.model.Team;
import com.example.pokedexapp.model.Type;

import java.io.Closeable;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivityViewModel extends AndroidViewModel {

    public MutableLiveData<List<Pokemon>> mGetPokemons;
    public MutableLiveData<List<Type>> mGetTypes;
    public MutableLiveData<List<Type>> mGetTypesFiltre;
    public MutableLiveData<List<Team>> mGetTeams;
    private static List<Pokemon> mLlistaPokemons;
    private static List<Type> mLlistaTypes;
    private static List<Type> mLlistaTypesFiltre;
    private static List<Team> mLlistaTeams;

    public MainActivityViewModel (@NonNull Application application) {
        super(application);
        mGetPokemons = new MutableLiveData<List<Pokemon>>();
        mGetTypes = new MutableLiveData<List<Type>>();
        mGetTypesFiltre = new MutableLiveData<List<Type>>();
    }

    public void getPokemons(File jsonFolder){
        if (mLlistaPokemons == null || mLlistaPokemons.size() == 0) {
            mLlistaPokemons = new ArrayList<Pokemon>();
            Observable.fromCallable(() -> {
                mLlistaPokemons = PokemonApi.getLlistaPokemons(jsonFolder);
                mGetPokemons.postValue(mLlistaPokemons);
                return 1;
            }).subscribeOn(Schedulers.io()).subscribe();
        } else {
            mGetPokemons.postValue(mLlistaPokemons);
        }

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


    public List<Team> getTeams(){
        if (mGetTeams.getValue() != null) {
            mLlistaTeams = new ArrayList<Team>();
            Observable.fromCallable(() -> {
                TeamDao dao = appDatabase();
                mGetTeams.postValue(mLlistaTeams);
                return 1;
            }).subscribeOn(Schedulers.io()).subscribe();
        }
        return mLlistaTeams;
    }

    public TeamDao appDatabase(){
        AppDatabase db = Room.databaseBuilder(getApplication().getApplicationContext(),
                AppDatabase.class, "database-name").build();

        return db.teamDao();
    }

}
