package com.example.pokedexapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
    private static List<Pokemon> mLlistapokemons;
    private static List<Type> mLlistaTypes;
    private static List<Type> mLlistaTypesFiltre;

    public MainActivityViewModel () {
        mGetPokemons = new MutableLiveData<List<Pokemon>>();
        mGetTypes = new MutableLiveData<List<Type>>();
        mGetTypesFiltre = new MutableLiveData<List<Type>>();
    }

    public List<Pokemon> getPokemons(File jsonFolder){
        return getPokemons();
    }

    public List<Type> getTypes(File jsonFolder) {
        if (mLlistaTypes == null) {
            mLlistaTypes = new ArrayList<Type>();
            Observable.fromCallable(() -> {
                mLlistaTypes = TypeApi.getLlistaTypes(jsonFolder);
                return 1;
            }).subscribeOn(Schedulers.io()).subscribe();
        }

        mGetTypes.postValue(mLlistaTypes);
        return mLlistaTypes;
    }

    public List<Pokemon> getPokemons(){
        if (mLlistapokemons==null) {
            mLlistapokemons = new ArrayList<Pokemon>();
            mLlistapokemons.add(new Pokemon(1,"bulbasaur",
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                    Arrays.asList(getType("grass"), getType("poison")), false,
                    "A strange seed was\nplanted on its\nback at birth.\fThe plant sprouts\nand grows with\nthis POKéMON.",
                    7,69,
                    new ArrayList<Ability>(Arrays.asList(
                            new Ability("overgrow", false),
                            new Ability("chlorophyll", true))),
                    new ArrayList<Stat>(Arrays.asList(
                            new Stat("hp",45),
                            new Stat("attack",49),
                            new Stat("defense",49),
                            new Stat("special-attack",65),
                            new Stat("special-defense",65),
                            new Stat("speed",45)))/*,
                    new ArrayList<Pokemon>(Arrays.asList(Pokemon.getPokemons().get(1)))*/));
            mLlistapokemons.add(new Pokemon(2,"ivysaur",
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/2.png",
                    Arrays.asList(getType("grass"), getType("poison")), false,
                    "When the bulb on\\nits back grows\\nlarge, it appears\\fto lose the\\nability to stand\\non its hind legs.",
                    10,130,
                    new ArrayList<Ability>(Arrays.asList(
                            new Ability("overgrow", false),
                            new Ability("chlorophyll", true))),
                    new ArrayList<Stat>(Arrays.asList(
                            new Stat("hp",60),
                            new Stat("attack",62),
                            new Stat("defense",63),
                            new Stat("special-attack",80),
                            new Stat("special-defense",80),
                            new Stat("speed",60)))/*,
                    Pokemon.getPokemons().get(0), new ArrayList<Pokemon>(Arrays.asList(Pokemon.getPokemons().get(2)))*/));
            mLlistapokemons.add(new Pokemon(3,"venusaur",
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png",
                    Arrays.asList(getType("grass"),getType("poison")), false,
                    "The plant blooms\\nwhen it is\\nabsorbing solar\\fenergy. It stays\\non the move to\\nseek sunlight.",
                    20,1000,
                    new ArrayList<Ability>(Arrays.asList(
                            new Ability("overgrow", false),
                            new Ability("chlorophyll", true))),
                    new ArrayList<Stat>(Arrays.asList(
                            new Stat("hp",80),
                            new Stat("attack",82),
                            new Stat("defense",83),
                            new Stat("special-attack",100),
                            new Stat("special-defense",100),
                            new Stat("speed",80)))/*,
                    Pokemon.getPokemons().get(1)*/));
            mLlistapokemons.add(new Pokemon(4,"charmander",
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png",
                    Arrays.asList(getType("fire")), false,
                    "Obviously prefers\\nhot places. When\\nit rains, steam\\fis said to spout\\nfrom the tip of\\nits tail.",
                    6,85,
                    new ArrayList<Ability>(Arrays.asList(
                            new Ability("blaze", false),
                            new Ability("solar-power", true))),
                    new ArrayList<Stat>(Arrays.asList(
                            new Stat("hp",39),
                            new Stat("attack",52),
                            new Stat("defense",43),
                            new Stat("special-attack",60),
                            new Stat("special-defense",50),
                            new Stat("speed",65)))/*,
                    new ArrayList<Pokemon>(Arrays.asList(Pokemon.getPokemons().get(4)))*/));
            mLlistapokemons.add(new Pokemon(5,"charmeleon",
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/5.png",
                    Arrays.asList(getType("fire")), false,
                    "",
                    11,190,
                    new ArrayList<Ability>(Arrays.asList(
                            new Ability("blaze", false),
                            new Ability("solar-power", true))),
                    new ArrayList<Stat>(Arrays.asList(
                            new Stat("hp",58),
                            new Stat("attack",64),
                            new Stat("defense",58),
                            new Stat("special-attack",80),
                            new Stat("special-defense",65),
                            new Stat("speed",80)))/*,
                    Pokemon.getPokemons().get(3), new ArrayList<Pokemon>(Arrays.asList(Pokemon.getPokemons().get(5)))*/));
            mLlistapokemons.add(new Pokemon(6,"charizard",
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png",
                    Arrays.asList(getType("fire"), getType("flying")), false,
                    "Spits fire that\\nis hot enough to\\nmelt boulders.\\fKnown to cause\\nforest fires\\nunintentionally.",
                    17,905,
                    new ArrayList<Ability>(Arrays.asList(
                            new Ability("blaze", false),
                            new Ability("solar-power", true))),
                    new ArrayList<Stat>(Arrays.asList(
                            new Stat("hp",78),
                            new Stat("attack",84),
                            new Stat("defense",78),
                            new Stat("special-attack",109),
                            new Stat("special-defense",85),
                            new Stat("speed",100)))/*,
                    Pokemon.getPokemons().get(4)*/));

        }

        mGetPokemons.postValue(mLlistapokemons);
        return mLlistapokemons;
    }

    public List<Type> getTypes() {
        if (mLlistaTypes == null) {
            mLlistaTypes = new ArrayList<Type>();
            mLlistaTypes.add(new Type("normal"));
            mLlistaTypes.add(new Type("fighting"));
            mLlistaTypes.add(new Type("flying"));
            mLlistaTypes.add(new Type("poison"));
            mLlistaTypes.add(new Type("ground"));
            mLlistaTypes.add(new Type("rock"));
            mLlistaTypes.add(new Type("bug"));
            mLlistaTypes.add(new Type("ghost"));
            mLlistaTypes.add(new Type("steel"));
            mLlistaTypes.add(new Type("fire"));
            mLlistaTypes.add(new Type("water"));
            mLlistaTypes.add(new Type("grass"));
            mLlistaTypes.add(new Type("electric"));
            mLlistaTypes.add(new Type("psychic"));
            mLlistaTypes.add(new Type("ice"));
            mLlistaTypes.add(new Type("dragon"));
            mLlistaTypes.add(new Type("dark"));
            mLlistaTypes.add(new Type("fairy"));
            mLlistaTypes.add(new Type("unknown"));
            mLlistaTypes.add(new Type("shadow"));
        }

        mGetTypes.postValue(mLlistaTypes);
        return mLlistaTypes;
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


    private Type getType (String type){
        return mLlistaTypes.get(mLlistaTypes.indexOf(new Type(type)));
    }

}
