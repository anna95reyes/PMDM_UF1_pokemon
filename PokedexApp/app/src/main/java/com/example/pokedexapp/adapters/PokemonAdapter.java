package com.example.pokedexapp.adapters;

import android.content.Context;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedexapp.R;
import com.example.pokedexapp.databinding.ActivityMainBinding;
import com.example.pokedexapp.databinding.FitxaPokemonPokedexBinding;
import com.example.pokedexapp.model.Pokemon;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {

    private List<Pokemon> mPokemons;
    private ImageLoader mImageLoader;

    public PokemonAdapter(List<Pokemon> pokemons) {
        mImageLoader = ImageLoader.getInstance();
        this.mPokemons = pokemons;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewFila = LayoutInflater.from(parent.getContext()).inflate(R.layout.fitxa_pokemon_pokedex, parent, false);
        ViewHolder vh = new ViewHolder(viewFila);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon pokemonActual = mPokemons.get(position);
        mImageLoader.displayImage(pokemonActual.getImageURL(), holder.imvImagePokemon);
        holder.txvIdPokemon.setText(getPokemonActualId(pokemonActual));
        holder.txvNamePokemon.setText(getPokemonActualName(pokemonActual));
        holder.rdbFavoritePokemon.setChecked(pokemonActual.isFavorite());

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10, 10, 10, 10);

        for (int i = 0; i < pokemonActual.getAbilities().size(); i++) {
            TextView textView = new TextView(holder.llyAbilityPokemon.getContext());
            textView.setText(pokemonActual.getAbilities().get(i).getName());

            textView.setLayoutParams(lp);

            holder.llyAbilityPokemon.addView(textView);
        }

    }

    private String getPokemonActualId(Pokemon pokemonActual) {
        String text;
        int id = pokemonActual.getId();
        text = "#";
        if (id < 10) {
            text += "00";
        } else if (id < 100) {
            text += "0";
        }
        text += id;
        return text;
    }

    private String getPokemonActualName(Pokemon pokemonActual) {
        String name = pokemonActual.getName();
        String text;
        text = name.substring(0, 1).toUpperCase();
        text += name.substring(1, name.length());

        return text;
    }

    @Override
    public int getItemCount() {
        return mPokemons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txvIdPokemon;
        TextView txvNamePokemon;
        RadioButton rdbFavoritePokemon;
        LinearLayout llyAbilityPokemon;
        ImageView imvImagePokemon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txvIdPokemon = itemView.findViewById(R.id.txvIdPokemon);
            txvNamePokemon = itemView.findViewById(R.id.txvNamePokemon);
            rdbFavoritePokemon = itemView.findViewById(R.id.rdbFavoritePokemon);
            llyAbilityPokemon = itemView.findViewById(R.id.llyAbilityPokemon);
            imvImagePokemon = itemView.findViewById(R.id.imvImagePokemon);
        }
    }
}
