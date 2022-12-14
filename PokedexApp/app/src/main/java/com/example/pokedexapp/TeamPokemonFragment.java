package com.example.pokedexapp;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pokedexapp.adapters.PokemonAdapter;
import com.example.pokedexapp.adapters.PokemonTeamAdapter;
import com.example.pokedexapp.adapters.TeamAdapter;
import com.example.pokedexapp.databinding.FragmentTeamBinding;
import com.example.pokedexapp.databinding.FragmentTeamPokemonBinding;
import com.example.pokedexapp.db.entities.PokemonDB;
import com.example.pokedexapp.model.Estat;
import com.example.pokedexapp.model.Pokemon;
import com.example.pokedexapp.model.Team;
import com.example.pokedexapp.viewmodel.MainActivityViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeamPokemonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamPokemonFragment extends Fragment implements PokemonTeamAdapter.PokemonTeamSelectedListener {

    public static final String ARG_PARAM_TEAM = "team";

    private FragmentTeamPokemonBinding binding;
    private MainActivityViewModel viewModel;
    private Team mTeam;
    private Pokemon mPokemonTeam = null;
    private Integer mPos = 0;

    public TeamPokemonFragment() {
        // Required empty public constructor
    }

    public static TeamPokemonFragment newInstance(String param1) {
        TeamPokemonFragment fragment = new TeamPokemonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_TEAM, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        if (getArguments() != null) {
            mTeam = (Team) getArguments().getSerializable(ARG_PARAM_TEAM);
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            mTeam = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentTeamPokemonBinding.inflate(inflater, container, false);

        if (mTeam == null) {
            binding.lytFitxaTeam.edtNameTeam.setText("");
            mTeam = new Team(binding.lytFitxaTeam.edtNameTeam.getText().toString());
            viewModel.insertTeam(mTeam);
        } else {
            binding.lytFitxaTeam.edtNameTeam.setText(mTeam.getName());
        }
        binding.lytFitxaTeam.edtNameTeam.setEnabled(true);
        binding.lytFitxaTeam.btnEditTeam.setVisibility(View.INVISIBLE);

        binding.lytFitxaTeam.rcyPokemonsTeam.setLayoutManager(new GridLayoutManager(requireContext(), TeamAdapter.MAX_COLUMS));
        PokemonTeamAdapter adapter = new PokemonTeamAdapter(mTeam.getPokemons(), requireContext(), this, Estat.UPDATE);
        binding.lytFitxaTeam.rcyPokemonsTeam.setAdapter(adapter);

        if (mTeam.getComplert()) {
            binding.lytFitxaTeam.imvWarningTeam.setVisibility(View.INVISIBLE);
        } else {
            binding.lytFitxaTeam.imvWarningTeam.setVisibility(View.VISIBLE);
        }

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!mTeam.getName().equals(binding.lytFitxaTeam.edtNameTeam.getText().toString())) {
            mTeam.setName(binding.lytFitxaTeam.edtNameTeam.getText().toString());
            viewModel.updateTeam(mTeam);
        }

        int pokemonsNulls = 0;
        for (Pokemon pokemon : mTeam.getPokemons()){
            if (pokemon == null) pokemonsNulls++;
        }

        if (mTeam.getName().equals("") && pokemonsNulls == mTeam.getPokemons().size()) {
            viewModel.deleteTeam(mTeam);
        }
    }

    @Override
    public void onPokemonTeamSeleccionat(Integer posicio, Pokemon pokemon) {
        mPokemonTeam = pokemon;
        mPos = posicio;
        viewModel.seleccioTeamIPokemonTeam(mTeam, mPokemonTeam, mPos);
    }

}