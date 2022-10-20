package com.example.pokedexapp.model;

public class Ability {
    private String name;
    private boolean is_hidden;

    public Ability(String name, boolean is_hidden) {
        this.name = name;
        this.is_hidden = is_hidden;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIs_hidden() {
        return is_hidden;
    }

    public void setIs_hidden(boolean is_hidden) {
        this.is_hidden = is_hidden;
    }
}
