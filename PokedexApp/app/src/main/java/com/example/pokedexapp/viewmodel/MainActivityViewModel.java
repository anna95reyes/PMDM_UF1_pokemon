package com.example.pokedexapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pokedexapp.api.PokemonApi;
import com.example.pokedexapp.api.TypeApi;
import com.example.pokedexapp.model.Ability;
import com.example.pokedexapp.model.Pokemon;
import com.example.pokedexapp.model.Stat;
import com.example.pokedexapp.model.Type;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivityViewModel extends ViewModel {

    public MutableLiveData<List<Pokemon>> mGetPokemons;
    public MutableLiveData<List<Type>> mGetTypes;
    public MutableLiveData<List<Type>> mGetTypesFiltre;
    private static List<Pokemon> mLlistaPokemons;
    private static List<Type> mLlistaTypes;
    private static List<Type> mLlistaTypesFiltre;

    public MainActivityViewModel () {
        mGetPokemons = new MutableLiveData<List<Pokemon>>();
        mGetTypes = new MutableLiveData<List<Type>>();
        mGetTypesFiltre = new MutableLiveData<List<Type>>();
    }

    public void getPokemons(File jsonFolder){
        if (mLlistaPokemons == null) {
            mLlistaPokemons = new ArrayList<Pokemon>();
            Observable.fromCallable(() -> {
                mLlistaPokemons = PokemonApi.getLlistaPokemons(jsonFolder);
                mGetPokemons.postValue(mLlistaPokemons);
                return 1;
            }).subscribeOn(Schedulers.io()).subscribe();
        }
    }

    public void getTypes(File jsonFolder) {
        if (mLlistaTypes == null) {
            mLlistaTypes = new ArrayList<Type>();
            Observable.fromCallable(() -> {
                mLlistaTypes = TypeApi.getLlistaTypes(jsonFolder);
                mGetTypes.postValue(mLlistaTypes);
                return 1;
            }).subscribeOn(Schedulers.io()).subscribe();
        }
    }

    public List<Type> getTypesFiltre() {
        if (mLlistaTypesFiltre == null) {
            mLlistaTypesFiltre = new ArrayList<Type>();
            mLlistaTypesFiltre.add(new Type("all types"));
            for (int i = 0; i < mLlistaTypes.size(); i++){
                mLlistaTypesFiltre.add(mLlistaTypes.get(i));
            }
        }

        mGetTypesFiltre.postValue(mLlistaTypesFiltre);
        return mLlistaTypesFiltre;
    }

}
