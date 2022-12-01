package com.example.pokedexapp.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedexapp.R;
import com.example.pokedexapp.model.Pokemon;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class EvolucioAdapter extends RecyclerView.Adapter<EvolucioAdapter.ViewHolder> {

    private List<Pokemon> mEvolucions;
    private ImageLoader mImageLoader;
    private Context mContext;
    private EvolucioSelectedListener mListener;

    public interface EvolucioSelectedListener {
        public void onEvolucioSeleccionat (String NamePokemonEvolution, List<Pokemon> evolucions);
    }

    public EvolucioAdapter(List<Pokemon> evolucions, Context context, EvolucioSelectedListener listener) {
        mImageLoader = ImageLoader.getInstance();
        mEvolucions = evolucions;
        mContext = context;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewFila = LayoutInflater.from(parent.getContext()).inflate(R.layout.fitxa_evolution_pokemon, parent, false);
        ViewHolder vh = new ViewHolder(viewFila);

        viewFila.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posicio = vh.getAdapterPosition();
                Pokemon pokemon = mEvolucions.get(posicio);
                if (mListener != null) mListener.onEvolucioSeleccionat(pokemon.getPokemonName(), pokemon.getEvolutions());
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon evolucioActual = mEvolucions.get(position);

        mImageLoader.displayImage(evolucioActual.getImageURL(), holder.imvImageEvolutionPokemon);
        holder.txvIdPokemonEvolution.setText(evolucioActual.getPokemonId());
        holder.txvNamePokemonEvolution.setText(evolucioActual.getPokemonName());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(7, 5, 7, 5);

        holder.llyAbilityPokemonEvolution.removeAllViews();
        for (int i = 0; i < evolucioActual.getTypes().size(); i++) {
            TextView textView = new TextView(holder.llyAbilityPokemonEvolution.getContext());
            textView.setText(evolucioActual.getTypes().get(i).getName().toUpperCase());
            textView.setBackgroundResource(R.drawable.capseta_fila_pokemon_types);
            textView.setTextSize(16);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setLayoutParams(params);
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.gray));
            holder.llyAbilityPokemonEvolution.addView(textView);
        }

        int idColor = mContext.getResources().getIdentifier(evolucioActual.getTypes().get(0).getName(), "color", mContext.getPackageName());
        holder.llyPokemonEvolution.getBackground().setTint(ContextCompat.getColor(mContext, idColor));

    }

    @Override
    public int getItemCount() {
        return mEvolucions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llyPokemonEvolution;
        ImageView imvImageEvolutionPokemon;
        TextView txvIdPokemonEvolution;
        TextView txvNamePokemonEvolution;
        LinearLayout llyAbilityPokemonEvolution;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            llyPokemonEvolution = itemView.findViewById(R.id.llyPokemonEvolution);
            imvImageEvolutionPokemon = itemView.findViewById(R.id.imvImageEvolutionPokemon);
            txvIdPokemonEvolution = itemView.findViewById(R.id.txvIdPokemonEvolution);
            txvNamePokemonEvolution = itemView.findViewById(R.id.txvNamePokemonEvolution);
            llyAbilityPokemonEvolution = itemView.findViewById(R.id.llyAbilityPokemonEvolution);
        }
    }
}
