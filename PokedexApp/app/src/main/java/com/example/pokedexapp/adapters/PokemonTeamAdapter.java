package com.example.pokedexapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedexapp.R;
import com.example.pokedexapp.model.Pokemon;
import com.example.pokedexapp.model.Team;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class PokemonTeamAdapter extends RecyclerView.Adapter<PokemonTeamAdapter.ViewHolder> {

    private List<Pokemon> mPokemons;
    private ImageLoader mImageLoader;
    private Context mContext;

    public PokemonTeamAdapter(List<Pokemon> pokemons,  Context context) {
        mImageLoader = ImageLoader.getInstance();
        mPokemons = pokemons;
        mContext = context;
    }

    @NonNull
    @Override
    public PokemonTeamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewFila = LayoutInflater.from(parent.getContext()).inflate(R.layout.fitxa_pokemon_team, parent, false);
        ViewHolder vh = new ViewHolder(viewFila);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonTeamAdapter.ViewHolder holder, int position) {
        Pokemon pokemonActual = mPokemons.get(position);

        if (pokemonActual != null) {
            mImageLoader.displayImage(pokemonActual.getImageURL(), holder.imvImagePokemonTeam);

            int idColor = mContext.getResources().getIdentifier(pokemonActual.getTypes().get(0).getName(), "color", mContext.getPackageName());
            holder.rlyPokemonTeam.getBackground().setTint(ContextCompat.getColor(mContext, idColor));
        } else {
            holder.imvImagePokemonTeam.setImageResource(R.drawable.question);
            holder.rlyPokemonTeam.getBackground().setTint(ContextCompat.getColor(mContext, R.color.pokemon_no_afegit));
        }
    }

    @Override
    public int getItemCount() {
        return mPokemons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imvImagePokemonTeam;
        RelativeLayout rlyPokemonTeam;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imvImagePokemonTeam = itemView.findViewById(R.id.imvImagePokemonTeam);
            rlyPokemonTeam = itemView.findViewById(R.id.rlyPokemonTeam);
        }
    }
}
