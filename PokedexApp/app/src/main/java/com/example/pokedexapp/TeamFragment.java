package com.example.pokedexapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pokedexapp.databinding.FragmentPokedexBinding;
import com.example.pokedexapp.databinding.FragmentTeamBinding;
import com.example.pokedexapp.model.Team;
import com.example.pokedexapp.model.Type;
import com.example.pokedexapp.viewmodel.MainActivityViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamFragment extends Fragment {

    private FragmentTeamBinding binding;
    private MainActivityViewModel viewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    public TeamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTeamBinding.inflate(inflater, container, false);


        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        viewModel.insertTeam(new Team("Team 1"));
        viewModel.mGetTeams.observe(getViewLifecycleOwner(), new Observer<List<Team>>() {
            @Override
            public void onChanged(List<Team> teams) {
                Log.d("POKEMON", "onInsert: " + teams);
            }
        });

        viewModel.getTeams();
        viewModel.mGetTeams.observe(getViewLifecycleOwner(), new Observer<List<Team>>() {
            @Override
            public void onChanged(List<Team> teams) {
                Log.d("POKEMON", "onCreate: " + teams);
            }
        });

        return binding.getRoot();

    }
}