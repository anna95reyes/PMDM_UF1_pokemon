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
        viewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTeamBinding.inflate(inflater, container, false);

        // ConfiguraciĆ³ del RecyclerView
        binding.rcyTeams.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rcyTeams.hasFixedSize();



        binding.pgrDownload.setVisibility(View.VISIBLE);

        //Creacio d'una carpeta dins de la carpeta de l'aplicaciĆ³
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

        TeamAdapter.TeamEditableListener listener = this;

        viewModel.mGetTeams.observe(getViewLifecycleOwner(), new Observer<List<Team>>() {
            @Override
            public void onChanged(List<Team> teams) {
                if (viewModel.mGetTeams.getValue() != null) {
                    binding.pgrDownload.setVisibility(View.INVISIBLE);
                }
                adapter = new TeamAdapter(teams, requireContext(), listener);
                binding.rcyTeams.setAdapter(adapter);
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