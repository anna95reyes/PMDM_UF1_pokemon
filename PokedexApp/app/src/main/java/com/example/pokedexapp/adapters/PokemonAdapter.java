package com.example.pokedexapp.adapters;

import static com.example.pokedexapp.R.color.all_types;
import static com.example.pokedexapp.R.color.purple_200;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.BlendMode;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedexapp.DetallPokemonFragment;
import com.example.pokedexapp.R;
import com.example.pokedexapp.model.Pokemon;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {

    private List<Pokemon> mPokemons;
    private ImageLoader mImageLoader;
    private Context mContext;
    private PokemonSelectedListener mListener;

    public interface PokemonSelectedListener {
        public void onPokemonSeleccionat (Pokemon pokemon);
    }

    public PokemonAdapter(List<Pokemon> pokemons, Context context, PokemonSelectedListener listener) {
        mImageLoader = ImageLoader.getInstance();
        mPokemons = pokemons;
        mContext = context;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewFila = LayoutInflater.from(parent.getContext()).inflate(R.layout.fitxa_pokemon_pokedex, parent, false);
        ViewHolder vh = new ViewHolder(viewFila);

        viewFila.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posicio = vh.getAdapterPosition();
                Pokemon pokemon = mPokemons.get(posicio);
                if (mListener != null) mListener.onPokemonSeleccionat(pokemon);

            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon pokemonActual = mPokemons.get(position);
        mImageLoader.displayImage(pokemonActual.getImageURL(), holder.imvImagePokemon);
        holder.txvIdPokemon.setText(pokemonActual.getPokemonId());
        holder.txvNamePokemon.setText(pokemonActual.getPokemonName());
        holder.rdbFavoritePokemon.setChecked(pokemonActual.isFavorite());

        holder.rdbFavoritePokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pokemonActual.isFavorite()) {
                    holder.rdbFavoritePokemon.setChecked(false);
                }
                pokemonActual.setFavorite(holder.rdbFavoritePokemon.isChecked());
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        params.setMargins(7, 5, 7, 5);

        holder.llyAbilityPokemon.removeAllViews();
        for (int i = 0; i < pokemonActual.getTypes().size(); i++) {
                TextView textView = new TextView(holder.llyAbilityPokemon.getContext());
                textView.setText(pokemonActual.getTypes().get(i).getName().toUpperCase());
                textView.setBackgroundResource(R.drawable.capseta_fila_pokemon_types);
                textView.setTextSize(14);
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView.setLayoutParams(params);
                textView.setTextColor(ContextCompat.getColor(mContext, R.color.gray));
                holder.llyAbilityPokemon.addView(textView);
        }
        int idColor = mContext.getResources().getIdentifier(pokemonActual.getTypes().get(0).getName(), "color", mContext.getPackageName());
        holder.llyPokemonPokedex.getBackground().setTint(ContextCompat.getColor(mContext, idColor));

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
        LinearLayout llyPokemonPokedex;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txvIdPokemon = itemView.findViewById(R.id.txvIdPokemon);
            txvNamePokemon = itemView.findViewById(R.id.txvNamePokemon);
            rdbFavoritePokemon = itemView.findViewById(R.id.rdbFavoritePokemon);
            llyAbilityPokemon = itemView.findViewById(R.id.llyAbilityPokemon);
            imvImagePokemon = itemView.findViewById(R.id.imvImagePokemon);
            llyPokemonPokedex = itemView.findViewById(R.id.llyPokemonPokedex);
        }
    }
}
