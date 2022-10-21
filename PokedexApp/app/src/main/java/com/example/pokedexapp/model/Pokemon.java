package com.example.pokedexapp.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pokemon {

    private static List<Pokemon> _pokemons;

    private int id; //id
    private String name; //name
    private String imageURL; //sprites/front_default
    private List<Type> types; //types/0/type/name
    private boolean favorite;
    private String definition; //species/url -> flavor_text_entries/0/flavor_text
    private int height; //height
    private int weight; //weight
    private List<Ability> abilities; //abilities/0/ability/name i //abilities/0/is_hidden
    private List<Stat> stats; //stats/0/base_stats i stats/0/stat/name
    private int totalStats; // -- camp calculat
    private Pokemon previousEvolution; //species/url -> evolution_chain/url --> chain/species/name
    private List<Pokemon> nextEvolution; //species/url -> evolution_chain/url --> species/name

    private Bitmap bitmap;
    private boolean selected;

    public static List<Pokemon> getPokemons(){
        if (_pokemons==null) {
            _pokemons = new ArrayList<>();
            _pokemons.add(new Pokemon(1,"bulbasaur",
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                    new ArrayList<Type>(Arrays.asList(
                            Type.getType("grass"),
                            Type.getType("poison"))), false,
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
                            new Stat("speed",45))),
                    new ArrayList<Pokemon>(Arrays.asList(Pokemon.getPokemons().get(1)))));
            _pokemons.add(new Pokemon(2,"ivysaur",
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/2.png",
                    new ArrayList<Type>(Arrays.asList(
                            Type.getType("grass"),
                            Type.getType("poison"))), false,
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
                            new Stat("speed",60))),
                    Pokemon.getPokemons().get(0), new ArrayList<Pokemon>(Arrays.asList(Pokemon.getPokemons().get(2)))));
            _pokemons.add(new Pokemon(3,"venusaur",
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png",
                    new ArrayList<Type>(Arrays.asList(
                            Type.getType("grass"),
                            Type.getType("poison"))), false,
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
                            new Stat("speed",80))),
                    Pokemon.getPokemons().get(1)));
            _pokemons.add(new Pokemon(4,"charmander",
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png",
                    new ArrayList<Type>(Arrays.asList(
                            Type.getType("fire"))), false,
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
                            new Stat("speed",65))),
                    new ArrayList<Pokemon>(Arrays.asList(Pokemon.getPokemons().get(4)))
                    ));
            _pokemons.add(new Pokemon(5,"charmeleon",
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/5.png",
                    new ArrayList<Type>(Arrays.asList(
                            Type.getType("fire"))), false,
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
                            new Stat("speed",80))),
                    Pokemon.getPokemons().get(3), new ArrayList<Pokemon>(Arrays.asList(Pokemon.getPokemons().get(5)))));
            _pokemons.add(new Pokemon(6,"charizard",
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png",
                    new ArrayList<Type>(Arrays.asList(
                            Type.getType("fire"),
                            Type.getType("flying"))), false,
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
                            new Stat("speed",100))),
                    Pokemon.getPokemons().get(4)));

        }

        return _pokemons;
    }


    public Pokemon(int id, String name, String imageURL, List<Type> types, boolean favorite, String definition,
                   int height, int weight, List<Ability> abilities, List<Stat> stats) {
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
        this.types = types;
        this.favorite = favorite;
        this.definition = definition;
        this.height = height;
        this.weight = weight;
        this.abilities = abilities;
        this.stats = stats;
    }

    public Pokemon(int id, String name, String imageURL, List<Type> types, boolean favorite, String definition,
                   int height, int weight, List<Ability> abilities, List<Stat> stats, Pokemon previousEvolution) {
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
        this.types = types;
        this.favorite = favorite;
        this.definition = definition;
        this.height = height;
        this.weight = weight;
        this.abilities = abilities;
        this.stats = stats;
        this.previousEvolution = previousEvolution;
    }

    public Pokemon(int id, String name, String imageURL, List<Type> types, boolean favorite, String definition,
                   int height, int weight, List<Ability> abilities, List<Stat> stats, List<Pokemon> nextEvolution) {
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
        this.types = types;
        this.favorite = favorite;
        this.definition = definition;
        this.height = height;
        this.weight = weight;
        this.abilities = abilities;
        this.stats = stats;
        this.nextEvolution = nextEvolution;
    }

    public Pokemon(int id, String name, String imageURL, List<Type> types, boolean favorite, String definition,
                   int height, int weight, List<Ability> abilities, List<Stat> stats, Pokemon previousEvolution,
                   List<Pokemon> nextEvolution) {
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
        this.types = types;
        this.favorite = favorite;
        this.definition = definition;
        this.height = height;
        this.weight = weight;
        this.abilities = abilities;
        this.stats = stats;
        this.previousEvolution = previousEvolution;
        this.nextEvolution = nextEvolution;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    public List<Stat> getStats() {
        return stats;
    }

    public void setStats(List<Stat> stats) {
        this.stats = stats;
    }

    public int getTotalStats() {
        totalStats = 0;
        for (int i = 0; i < stats.size(); i++){
            totalStats += stats.get(i).getBase_stat();
        }
        return totalStats;
    }

    public Pokemon getPreviousEvolution() {
        return previousEvolution;
    }

    public void setPreviousEvolution(Pokemon previousEvolution) {
        this.previousEvolution = previousEvolution;
    }

    public List<Pokemon> getNextEvolution() {
        return nextEvolution;
    }

    public void setNextEvolution(List<Pokemon> nextEvolution) {
        this.nextEvolution = nextEvolution;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        imageURL = null;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


}
