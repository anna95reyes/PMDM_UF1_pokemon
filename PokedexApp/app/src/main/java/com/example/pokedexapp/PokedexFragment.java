package com.example.pokedexapp;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pokedexapp.adapters.PokemonAdapter;
import com.example.pokedexapp.adapters.TypeAdapter;
import com.example.pokedexapp.databinding.FragmentPokedexBinding;
import com.example.pokedexapp.model.Pokemon;
import com.example.pokedexapp.model.Type;
import com.example.pokedexapp.viewmodel.MainActivityViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PokedexFragment extends Fragment implements PokemonAdapter.PokemonSelectedListener {

    private FragmentPokedexBinding binding;

    private PokemonAdapter adapter;
    private TypeAdapter adapterType;
    private MainActivityViewModel viewModel;
    private BottomSheetDialog bottomSheetDialog = null;
    private List<Pokemon> pokemonsFiltrats;
    private Boolean botoFavoritsClicat = false;
    private Boolean botoFavoritsAbansClicat = false;
    private String nameOrIdFiltre = "";
    private String nameTypeFiltre;

    public PokedexFragment() {
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
        binding = FragmentPokedexBinding.inflate(inflater, container, false);

        // Configuració del RecyclerView
        binding.rcyPokemons.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rcyPokemons.hasFixedSize();

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        nameTypeFiltre = binding.btnFilterTypes.getText().toString().toLowerCase(Locale.ROOT);

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

        PokemonAdapter.PokemonSelectedListener listener = this;

        viewModel.mGetPokemons.observe(getViewLifecycleOwner(), new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {
                //Creació de l'adapter
                adapter = new PokemonAdapter(pokemons, requireContext(), listener);
                binding.rcyPokemons.setAdapter(adapter);
                binding.pgrDownload.setVisibility(View.INVISIBLE);
            }
        });

        binding.btnFilterTypes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });

        binding.btnFilterFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                botoFavoritsClicat = true;
                binding.btnFilterFavorites.setImageResource(R.drawable.filter_favorite_black);
                if (botoFavoritsClicat == botoFavoritsAbansClicat) {
                    binding.btnFilterFavorites.setImageResource(R.drawable.filter_favorite_transparent);
                    botoFavoritsClicat = false;
                }
                filtratgeLlistaPokemons();
                botoFavoritsAbansClicat = botoFavoritsClicat;

            }
        });

        binding.edtFilterNameOrNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nameOrIdFiltre = s.toString();
                filtratgeLlistaPokemons();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return binding.getRoot();
    }

    private void filtratgeLlistaPokemons() {
        List<Pokemon> pokemonsSenseFiltrar = viewModel.mGetPokemons.getValue();
        pokemonsFiltrats = new ArrayList<Pokemon>();

        //-------------------------------------------------------------------------------
        // filtre per nom o número
        boolean isFiltreNameOrIdActive = !nameOrIdFiltre.equals("");
        // filtre per favorit
        boolean isFiltreFavoritActive = botoFavoritsClicat;
        // filtre per tipus
        boolean isFiltreTipusActive = !nameTypeFiltre.equals("all types");
        //-------------------------------------------------------------------------------


        if (!isFiltreNameOrIdActive && !isFiltreFavoritActive && !isFiltreTipusActive) {
            pokemonsFiltrats = pokemonsSenseFiltrar;
        } else {
            for (Pokemon p : pokemonsSenseFiltrar) {

                boolean filtreNameOrId = (p.getId() + "").contains(nameOrIdFiltre) || p.getName().contains(nameOrIdFiltre);
                boolean filtreFavorits = p.isFavorite();
                boolean filtreTypes = p.getTypes().contains(new Type(nameTypeFiltre));

                boolean filtratgeNameOrIdOk =  !isFiltreNameOrIdActive ||  filtreNameOrId;
                boolean filtratgeFavoritsOk =  !isFiltreFavoritActive ||  filtreFavorits;
                boolean filtratgeTipusOk =  !isFiltreTipusActive ||  filtreTypes;

                if(filtratgeNameOrIdOk && filtratgeFavoritsOk && filtratgeTipusOk) pokemonsFiltrats.add(p);
            }
        }
        adapter = new PokemonAdapter(pokemonsFiltrats, requireContext(), this);
        binding.rcyPokemons.setAdapter(adapter);
    }

    private void showBottomSheetDialog() {
        bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_layout);

        RecyclerView rcyTypes = bottomSheetDialog.findViewById(R.id.rcyTypes);

        // Configuració del RecyclerView
        rcyTypes.setLayoutManager(new LinearLayoutManager(requireContext()));
        rcyTypes.hasFixedSize();

        viewModel.getTypesFiltre();
        viewModel.mGetTypesFiltre.observe(this, new Observer<List<Type>>() {
            @Override
            public void onChanged(List<Type> typesFiltre) {
                //Creació de l'adapter
                adapterType = new TypeAdapter(typesFiltre, requireContext(), PokedexFragment.this);
                rcyTypes.setAdapter(adapterType);
            }
        });

        bottomSheetDialog.show();
    }

    public void canviarFiltreType(String text, int idColor) {
        binding.btnFilterTypes.setText(text);
        binding.btnFilterTypes.getBackground().setTint(ContextCompat.getColor(requireContext(), idColor));
        nameTypeFiltre = text.toLowerCase();
        filtratgeLlistaPokemons();
        bottomSheetDialog.dismiss();

    }

    @Override
    public void onPokemonSeleccionat(Pokemon pokemon) {
        Bundle args = new Bundle();
        args.putSerializable(DetallPokemonFragment.ARG_PARAM_POKEMON, pokemon);
        NavController navController =  NavHostFragment.findNavController(this);
        navController.navigate(R.id.action_pokedexFragment_to_detallPokemonFragment, args);
        //Fico aixo perque si no quan del fragment de detall tiro cap enrrere, es queda guardat el filtre
        binding.edtFilterNameOrNumber.setText("");
        binding.btnFilterFavorites.setSelected(false);
    }
}