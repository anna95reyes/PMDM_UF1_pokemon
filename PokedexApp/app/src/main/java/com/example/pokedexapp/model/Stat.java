package com.example.pokedexapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Stat implements Serializable {
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

    public String getStatName() {
        String text;
        if (name.equals("hp")){
            text = name.toUpperCase();
        } else if (name.contains("special")) {
            text = "Sp. ";
            text += name.substring(8, 9).toUpperCase();
            text += name.substring(9,name.length());
        } else {
            text = name.substring(0, 1).toUpperCase();
            text += name.substring(1, name.length());
        }

        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stat stat = (Stat) o;
        return base_stat == stat.base_stat && name.equals(stat.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, base_stat);
    }

    @Override
    public String toString() {
        return "Stat{" +
                "name='" + name + '\'' +
                ", base_stat=" + base_stat +
                '}';
    }
}
