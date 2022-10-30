package com.example.pokedexapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pokedexapp.model.Ability;
import com.example.pokedexapp.model.Pokemon;
import com.example.pokedexapp.model.Stat;
import com.example.pokedexapp.model.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivityViewModel extends ViewModel {

    public MutableLiveData<List<Pokemon>> mGetPokemons;
    private static List<Pokemon> mLlistapokemons;

    public MainActivityViewModel () {
        mGetPokemons = new MutableLiveData<List<Pokemon>>();
    }

    public List<Pokemon> getPokemons(){
        if (mLlistapokemons==null) {
            mLlistapokemons = new ArrayList<Pokemon>();
            mLlistapokemons.add(new Pokemon(1,"bulbasaur",
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                    Arrays.asList(Type.getType("grass"), Type.getType("poison")), false,
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
                    Arrays.asList(Type.getType("grass"), Type.getType("poison")), false,
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
                    Arrays.asList(Type.getType("grass"),Type.getType("poison")), false,
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
                    Arrays.asList(Type.getType("fire")), false,
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
                    Arrays.asList(Type.getType("fire")), false,
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
                    Arrays.asList(Type.getType("fire"), Type.getType("flying")), false,
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

}
