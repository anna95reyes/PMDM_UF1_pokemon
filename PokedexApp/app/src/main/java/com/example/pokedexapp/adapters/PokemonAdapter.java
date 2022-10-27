package com.example.pokedexapp.adapters;

import static com.example.pokedexapp.R.color.all_types;
import static com.example.pokedexapp.R.color.purple_200;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.BlendMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedexapp.R;
import com.example.pokedexapp.model.Pokemon;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {

    private List<Pokemon> mPokemons;
    private ImageLoader mImageLoader;
    private Context mContext;

    public PokemonAdapter(List<Pokemon> pokemons, Context context) {
        mImageLoader = ImageLoader.getInstance();
        this.mPokemons = pokemons;
        mContext = context;
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

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        params.setMargins(7, 5, 7, 5);

        for (int i = 0; i < pokemonActual.getTypes().size(); i++) {
            TextView textView = new TextView(holder.llyAbilityPokemon.getContext());
            textView.setText(pokemonActual.getTypes().get(i).getName().toUpperCase());
            textView.setBackgroundResource(R.drawable.capseta_fila_pokemon_types);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setLayoutParams(params);
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.gray));
            holder.llyAbilityPokemon.addView(textView);

        }
        holder.itemView.getBackground().setTint(ContextCompat.getColor(mContext, R.color.fire));

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
