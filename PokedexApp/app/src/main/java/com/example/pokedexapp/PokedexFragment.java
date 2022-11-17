package com.example.pokedexapp;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pokedexapp.adapters.PokemonAdapter;
import com.example.pokedexapp.adapters.TypeAdapter;
import com.example.pokedexapp.databinding.ActivityMainBinding;
import com.example.pokedexapp.databinding.FragmentPokedexBinding;
import com.example.pokedexapp.model.Pokemon;
import com.example.pokedexapp.model.Type;
import com.example.pokedexapp.viewmodel.MainActivityViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PokedexFragment extends Fragment {

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
        viewModel.mGetTypes.observe(this, new Observer<List<Type>>() {
            @Override
            public void onChanged(List<Type> types) {
                //descarregar els pokemons
                viewModel.getPokemons(jsonFolder);
            }
        });

        viewModel.mGetPokemons.observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {
                //Creació de l'adapter
                adapter = new PokemonAdapter(pokemons, requireContext());
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
                binding.btnFilterFavorites.setImageResource(R.drawable.cheack_all_gray);
                if (botoFavoritsClicat == botoFavoritsAbansClicat) {
                    binding.btnFilterFavorites.setImageResource(R.drawable.cheack_all_black);
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
                //TODO: fer filtre per a la id i el nom
                nameOrIdFiltre = s.toString();
                filtratgeLlistaPokemons();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return binding.getRoot();
    }


    //Esta a lo guarro pero funciona
    private void filtratgeLlistaPokemons() {
        List<Pokemon> pokemonsSenseFiltrar = viewModel.mGetPokemons.getValue();
        pokemonsFiltrats = new ArrayList<Pokemon>();
        if (nameOrIdFiltre.equals("") && !botoFavoritsClicat && nameTypeFiltre.equals("all types")) {
            pokemonsFiltrats = pokemonsSenseFiltrar;
        } else {
            for (Pokemon p : pokemonsSenseFiltrar) {
                boolean filtreNameOrId = (p.getId() + "").contains(nameOrIdFiltre) || p.getName().contains(nameOrIdFiltre);
                boolean filtreFavorits = p.isFavorite();
                boolean filtreTypes = p.getTypes().contains(new Type(nameTypeFiltre));

                if (!nameOrIdFiltre.equals("") && filtreNameOrId) {
                    if (!pokemonsFiltrats.contains(p)) pokemonsFiltrats.add(p);
                }

                if (botoFavoritsClicat && filtreFavorits) {
                    if (!pokemonsFiltrats.contains(p)) pokemonsFiltrats.add(p);
                }

                if (!nameTypeFiltre.equals("all types") && filtreTypes) {
                    if (!pokemonsFiltrats.contains(p)) pokemonsFiltrats.add(p);
                }
                if (!nameOrIdFiltre.equals("") && botoFavoritsClicat) {
                    if (!filtreNameOrId || !filtreFavorits) {
                        if (pokemonsFiltrats.contains(p)) pokemonsFiltrats.remove(p);
                    }
                }
                if (!nameOrIdFiltre.equals("") && !nameTypeFiltre.equals("all types")) {
                    if (!filtreNameOrId) {
                        if (pokemonsFiltrats.contains(p)) pokemonsFiltrats.remove(p);
                    }
                }
                if (botoFavoritsClicat && !nameTypeFiltre.equals("all types")) {
                    if (!filtreFavorits || !filtreTypes) {
                        if (pokemonsFiltrats.contains(p)) pokemonsFiltrats.remove(p);
                    }
                }
                if (!nameOrIdFiltre.equals("") && botoFavoritsClicat && !nameTypeFiltre.equals("all types")) {
                    if (!filtreFavorits || !filtreNameOrId || !filtreTypes) {
                        if (pokemonsFiltrats.contains(p)) pokemonsFiltrats.remove(p);
                    }
                }


            }
        }
        adapter = new PokemonAdapter(pokemonsFiltrats, requireContext());
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
        // TODO: agefir filtre del tipus
        nameTypeFiltre = text.toLowerCase();
        filtratgeLlistaPokemons();
        bottomSheetDialog.dismiss();

    }

}