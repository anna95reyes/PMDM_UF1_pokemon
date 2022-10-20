package com.example.pokedexapp.model;

import java.util.ArrayList;
import java.util.List;

public class Type {
    private String name;

    private static List<Type> _types;

    public Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Type> getTypes() {
        if (_types == null) {
            _types = new ArrayList<>();
            _types.add(new Type("normal"));
            _types.add(new Type("fighting"));
            _types.add(new Type("flying"));
            _types.add(new Type("poison"));
            _types.add(new Type("ground"));
            _types.add(new Type("rock"));
            _types.add(new Type("bug"));
            _types.add(new Type("ghost"));
            _types.add(new Type("steel"));
            _types.add(new Type("fire"));
            _types.add(new Type("water"));
            _types.add(new Type("grass"));
            _types.add(new Type("electric"));
            _types.add(new Type("psychic"));
            _types.add(new Type("ice"));
            _types.add(new Type("dragon"));
            _types.add(new Type("dark"));
            _types.add(new Type("fairy"));
            _types.add(new Type("unknown"));
            _types.add(new Type("shadow"));
        }

        return _types;
    }

    public static Type getType (String type){
        return _types.get(_types.indexOf(type));
    }
}