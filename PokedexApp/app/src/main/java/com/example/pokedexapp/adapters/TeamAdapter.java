package com.example.pokedexapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedexapp.PokedexFragment;
import com.example.pokedexapp.R;
import com.example.pokedexapp.model.Estat;
import com.example.pokedexapp.model.Pokemon;
import com.example.pokedexapp.model.Team;

import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> implements PokemonTeamAdapter.PokemonTeamSelectedListener {

    public static final Integer MAX_COLUMS = 3;

    private List<Team> mTeams;
    private Context mContext;
    private TeamEditableListener mListenerTeamEditable;

    public interface TeamEditableListener {
        public void onTeamEditable (Team team);
    }

    public TeamAdapter(List<Team> teams, Context context, TeamEditableListener listenerTeamEditable) {
        mTeams = teams;
        mContext = context;
        mListenerTeamEditable = listenerTeamEditable;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewFila = LayoutInflater.from(parent.getContext()).inflate(R.layout.fitxa_team, parent, false);
        ViewHolder vh = new ViewHolder(viewFila);
        vh.btnEditTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posicio = vh.getAdapterPosition();
                Team team = mTeams.get(posicio);
                if (mListenerTeamEditable != null) mListenerTeamEditable.onTeamEditable(team);
                notifyItemChanged(posicio);
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Team teamActual = mTeams.get(position);
        holder.edtNameTeam.setText(teamActual.getName());
        if (teamActual.getComplert()) {
            holder.imvWarningTeam.setVisibility(View.INVISIBLE);
        } else {
            holder.imvWarningTeam.setVisibility(View.VISIBLE);
        }

        holder.rcyPokemonsTeam.setLayoutManager(new GridLayoutManager(mContext, MAX_COLUMS));
        PokemonTeamAdapter adapter = new PokemonTeamAdapter(teamActual.getPokemons(), mContext, this, Estat.VIEW);
        holder.rcyPokemonsTeam.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return mTeams.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        EditText edtNameTeam;
        ImageView imvWarningTeam;
        ImageButton btnEditTeam;
        RecyclerView rcyPokemonsTeam;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            edtNameTeam = itemView.findViewById(R.id.edtNameTeam);
            imvWarningTeam = itemView.findViewById(R.id.imvWarningTeam);
            btnEditTeam = itemView.findViewById(R.id.btnEditTeam);
            rcyPokemonsTeam = itemView.findViewById(R.id.rcyPokemonsTeam);
        }
    }

    @Override
    public void onPokemonTeamSeleccionat(Pokemon pokemon) {

    }
}
