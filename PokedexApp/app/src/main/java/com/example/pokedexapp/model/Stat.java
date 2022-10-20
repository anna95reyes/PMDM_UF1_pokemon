package com.example.pokedexapp.model;

import java.util.ArrayList;
import java.util.List;

public class Stat {
    private String name;
    private int base_stat;

    public Stat(String name, int base_stat) {
        this.name = name;
        this.base_stat = base_stat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBase_stat() {
        return base_stat;
    }

    public void setBase_stat(int base_stat) {
        this.base_stat = base_stat;
    }
}
