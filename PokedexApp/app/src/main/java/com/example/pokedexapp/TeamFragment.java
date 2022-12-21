package com.example.pokedexapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pokedexapp.adapters.TeamAdapter;
import com.example.pokedexapp.databinding.FragmentTeamBinding;
import com.example.pokedexapp.model.Pokemon;
import com.example.pokedexapp.model.Team;
import com.example.pokedexapp.model.Type;
import com.example.pokedexapp.viewmodel.MainActivityViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TeamFragment extends Fragment implements View.OnClickListener, TeamAdapter.TeamEditableListener {

    private FragmentTeamBinding binding;
    private MainActivityViewModel viewModel;
    private TeamAdapter adapter;
    private List<Team> teamsFiltrats;
    private String nameFiltre = "";

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
                /*Team team = new Team( "Team 1");

                team.addPokemon(0, pokemons.get(0));
                team.addPokemon(1, pokemons.get(1));
                team.addPokemon(2, pokemons.get(2));
                team.addPokemon(3, pokemons.get(3));
                team.addPokemon(4, pokemons.get(4));
                team.addPokemon(5, pokemons.get(5));

                viewModel.insertTeam(team);
                viewModel.insertPokemonTeam(0, team, pokemons.get(0));
                viewModel.insertPokemonTeam(1, team, pokemons.get(1));
                viewModel.insertPokemonTeam(2, team, pokemons.get(2));
                viewModel.insertPokemonTeam(3, team, pokemons.get(3));
                viewModel.insertPokemonTeam(4, team, pokemons.get(4));
                viewModel.insertPokemonTeam(5, team, pokemons.get(5));

                team = new Team("Team 2");
                team.addPokemon(0, pokemons.get(6));
                team.addPokemon(1, pokemons.get(7));
                team.addPokemon(2, pokemons.get(8));

                viewModel.insertTeam(team);
                viewModel.insertPokemonTeam(0, team, pokemons.get(6));
                viewModel.insertPokemonTeam(1, team, pokemons.get(7));
                viewModel.insertPokemonTeam(2, team, pokemons.get(8));
                */
                viewModel.getTeams();
            }
        });

        TeamAdapter.TeamEditableListener listener = this;

        viewModel.mGetTeams.observe(getViewLifecycleOwner(), new Observer<List<Team>>() {
            @Override
            public void onChanged(List<Team> teams) {
                if (viewModel.mGetTeams.getValue() != null) {
                    binding.pgrDownload.setVisibility(View.INVISIBLE);
                }
                adapter = new TeamAdapter(teams, requireContext(), listener);
                binding.rcyTeams.setAdapter(adapter);
                for (int i = 0; i < teams.size(); i++)
                    Log.d("POKEMON", "onCreate: " + teams.get(i).getName() + " - pokemons:" + teams.get(i).getPokemons().size());
            }
        });

        binding.edtFilterName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nameFiltre = s.toString();
                filtratgeLlistaTeams();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.btnAddTeams.setOnClickListener(this);


        return binding.getRoot();

    }

    private void filtratgeLlistaTeams() {
        List<Team> teamsSenseFiltrar = viewModel.mGetTeams.getValue();
        teamsFiltrats = new ArrayList<Team>();

        //-------------------------------------------------------------------------------
        // filtre per nom
        boolean isFiltreNameActive = !nameFiltre.equals("");
        //-------------------------------------------------------------------------------


        if (!isFiltreNameActive) {
            teamsFiltrats = teamsSenseFiltrar;
        } else {
            for (Team t : teamsSenseFiltrar) {

                boolean filtreName = t.getName().toLowerCase().contains(nameFiltre.toLowerCase());

                boolean filtratgeNameOk =  !isFiltreNameActive ||  filtreName;

                if(filtratgeNameOk) teamsFiltrats.add(t);
            }
        }
        adapter = new TeamAdapter(teamsFiltrats, requireContext(), this);
        binding.rcyTeams.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        Bundle args = new Bundle();
        args.putSerializable(TeamPokemonFragment.ARG_PARAM_TEAM, null);

        NavController navController =  NavHostFragment.findNavController(this);
        navController.navigate(R.id.action_teamFragment_to_teamPokemonFragment, args);
    }

    @Override
    public void onTeamEditable(Team team) {
        Bundle args = new Bundle();
        args.putSerializable(TeamPokemonFragment.ARG_PARAM_TEAM, team);

        NavController navController =  NavHostFragment.findNavController(this);
        navController.navigate(R.id.action_teamFragment_to_teamPokemonFragment, args);
    }
}