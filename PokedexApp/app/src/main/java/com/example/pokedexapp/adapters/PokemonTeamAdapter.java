package com.example.pokedexapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedexapp.R;
import com.example.pokedexapp.model.Pokemon;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class PokemonTeamAdapter extends RecyclerView.Adapter<PokemonTeamAdapter.ViewHolder> {

    private List<Pokemon> mPokemons;
    private ImageLoader mImageLoader;
    private Context mContext;
    private Integer mPokemonSeleccionat = -1;
    private Integer mPokemonAnticSeleccionat = -1;

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

        viewFila.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = vh.getAdapterPosition();
                Pokemon pokemon = mPokemons.get(pos);
                mPokemonAnticSeleccionat = mPokemonSeleccionat;
                mPokemonSeleccionat = pos;

                if (mPokemonSeleccionat != -1) {
                    notifyItemChanged(mPokemonAnticSeleccionat);
                }
                notifyItemChanged(mPokemonSeleccionat);
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonTeamAdapter.ViewHolder holder, int position) {
        Pokemon pokemonActual = mPokemons.get(position);

        if (pokemonActual != null) {
            mImageLoader.displayImage(pokemonActual.getImageURL(), holder.imvImagePokemonTeam);

            int idColor = mContext.getResources().getIdentifier(pokemonActual.getTypes().get(0).getName(), "color", mContext.getPackageName());
            holder.ctlPokemonTeam.getBackground().setTint(ContextCompat.getColor(mContext, idColor));
        } else {
            holder.imvImagePokemonTeam.setImageResource(R.drawable.question);
            holder.ctlPokemonTeam.getBackground().setTint(ContextCompat.getColor(mContext, R.color.pokemon_no_afegit));
        }

        Integer color = R.color.transparent;
        if (mPokemonSeleccionat == position) {
            color = R.color.pokemon_seleccionat;
        }
        holder.ctlPokemonTeamSeleccionat.getBackground().setTint(ContextCompat.getColor(mContext, color));

    }

    @Override
    public int getItemCount() {
        return mPokemons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imvImagePokemonTeam;
        ConstraintLayout ctlPokemonTeam;
        ConstraintLayout ctlPokemonTeamSeleccionat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imvImagePokemonTeam = itemView.findViewById(R.id.imvImagePokemonTeam);
            ctlPokemonTeam = itemView.findViewById(R.id.ctlPokemonTeam);
            ctlPokemonTeamSeleccionat = itemView.findViewById(R.id.ctlPokemonTeamSeleccionat);
        }
    }
}
