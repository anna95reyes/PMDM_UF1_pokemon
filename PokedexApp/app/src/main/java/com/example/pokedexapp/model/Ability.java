package com.example.pokedexapp.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ability ability = (Ability) o;
        return is_hidden == ability.is_hidden && name.equals(ability.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, is_hidden);
    }
}
