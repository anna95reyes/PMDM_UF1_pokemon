package com.example.pokedexapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pokedexapp.adapters.PokemonAdapter;
import com.example.pokedexapp.adapters.PokemonTeamAdapter;
import com.example.pokedexapp.adapters.TeamAdapter;
import com.example.pokedexapp.adapters.TypeAdapter;
import com.example.pokedexapp.databinding.FragmentTeamBinding;
import com.example.pokedexapp.model.Pokemon;
import com.example.pokedexapp.model.Team;
import com.example.pokedexapp.model.Type;
import com.example.pokedexapp.viewmodel.MainActivityViewModel;

import java.io.File;
import java.util.List;

public class TeamFragment extends Fragment {

    private FragmentTeamBinding binding;
    private MainActivityViewModel viewModel;
    private TeamAdapter adapter;

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

        // Configuració del RecyclerView
        binding.rcyTeams.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rcyTeams.hasFixedSize();

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        binding.pgrDownload.setVisibility(View.VISIBLE);

        //Creacio d'una carpeta dins de la carpeta de l'aplicació
        File jsonFolder = new File(requireContext().getFilesDir(), "jsons");
        jsonFolder.mkdirs(); //per crear la carpeta explicitament

        //descarregar els tipus de pokemons
        viewModel.getTypes(jsonFolder); //Li paso la carpeta a la funcio de descarrega
        viewModel.mGetTypes.observe(getViewLifecycleOwner(), new Observer<List<Type>>() {
            @Override
            public void onChanged(List<Type> types) {
                //descarregar els pokemons
                viewModel.getPokemons(jsonFolder);
            }
        });

        viewModel.mGetPokemons.observe(getViewLifecycleOwner(), new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {
                //descarregar els equips
                viewModel.getTeams();
            }
        });

        viewModel.mGetTeams.observe(getViewLifecycleOwner(), new Observer<List<Team>>() {
            @Override
            public void onChanged(List<Team> teams) {
                if (viewModel.mGetTeams.getValue() != null) {
                    binding.pgrDownload.setVisibility(View.INVISIBLE);
                }
                adapter = new TeamAdapter(teams, requireContext());
                binding.rcyTeams.setAdapter(adapter);
                for (int i = 0; i < teams.size(); i++)
                    Log.d("POKEMON", "onCreate: " + teams.get(i).getName() + " - pokemons:" + teams.get(i).getPokemons().size());
            }
        });

        return binding.getRoot();

    }
}