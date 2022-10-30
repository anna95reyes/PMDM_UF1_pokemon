package com.example.pokedexapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.pokedexapp.adapters.PokemonAdapter;
import com.example.pokedexapp.adapters.TypeAdapter;
import com.example.pokedexapp.databinding.ActivityMainBinding;
import com.example.pokedexapp.model.Pokemon;
import com.example.pokedexapp.model.Type;
import com.example.pokedexapp.viewmodel.MainActivityViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PokemonAdapter adapter;
    private TypeAdapter adapterType;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configuració del Universal Image Loader

        DisplayImageOptions options =
                new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.drawable.pokeball)
                        .showImageForEmptyUri(R.drawable.pokeball)
                        .showImageOnFail(R.drawable.pokeball)
                        .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .build();

        ImageLoader.getInstance().init(config);

        // Configuració del RecyclerView
        binding.rcyPokemons.setLayoutManager(new LinearLayoutManager(this));
        binding.rcyPokemons.hasFixedSize();

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        viewModel.getPokemons();
        viewModel.mGetPokemons.observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {
                //Creació de l'adapter
                adapter = new PokemonAdapter(pokemons, MainActivity.this);
                binding.rcyPokemons.setAdapter(adapter);
            }
        });
        
        binding.btnFilterTypes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });
    }

    private void showBottomSheetDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_layout);

        RecyclerView rcyTypes = bottomSheetDialog.findViewById(R.id.rcyTypes);

        // Configuració del RecyclerView
        rcyTypes.setLayoutManager(new LinearLayoutManager(this));
        rcyTypes.hasFixedSize();

        viewModel.getTypesFiltre();
        viewModel.mGetTypesFiltre.observe(this, new Observer<List<Type>>() {
            @Override
            public void onChanged(List<Type> typesFiltre) {
                //Creació de l'adapter
                adapterType = new TypeAdapter(typesFiltre, MainActivity.this);
                rcyTypes.setAdapter(adapterType);
            }
        });

        bottomSheetDialog.show();
    }
}