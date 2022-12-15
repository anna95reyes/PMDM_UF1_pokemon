package com.example.pokedexapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedexapp.R;
import com.example.pokedexapp.model.Team;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {

    private final Integer MAX_COLUMS = 3;

    private List<Team> mTeams;
    private Context mContext;

    public TeamAdapter(List<Team> teams, Context context) {
        mTeams = teams;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewFila = LayoutInflater.from(parent.getContext()).inflate(R.layout.fitxa_team, parent, false);
        ViewHolder vh = new ViewHolder(viewFila);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Team teamActual = mTeams.get(position);
        holder.edtNameTeam.setText(teamActual.getName());
        if (teamActual.getComplert()) {
            holder.imvImageWarningTeam.setVisibility(View.INVISIBLE);
        } else {
            holder.imvImageWarningTeam.setVisibility(View.VISIBLE);
        }
        holder.btnImageEditTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.rcyPokemonsTeam.setLayoutManager(new GridLayoutManager(mContext, MAX_COLUMS));
        PokemonTeamAdapter adapter = new PokemonTeamAdapter(teamActual.getPokemons(), mContext);
        holder.rcyPokemonsTeam.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return mTeams.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        EditText edtNameTeam;
        ImageView imvImageWarningTeam;
        ImageButton btnImageEditTeam;
        RecyclerView rcyPokemonsTeam;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            edtNameTeam = itemView.findViewById(R.id.edtNameTeam);
            imvImageWarningTeam = itemView.findViewById(R.id.imvImageWarningTeam);
            btnImageEditTeam = itemView.findViewById(R.id.btnImageEditTeam);
            rcyPokemonsTeam = itemView.findViewById(R.id.rcyPokemonsTeam);
        }
    }
}
