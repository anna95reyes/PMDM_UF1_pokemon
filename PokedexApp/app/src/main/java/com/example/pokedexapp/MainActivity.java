package com.example.pokedexapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.pokedexapp.adapters.PokemonAdapter;
import com.example.pokedexapp.databinding.ActivityMainBinding;
import com.example.pokedexapp.model.Pokemon;
import com.example.pokedexapp.viewmodel.MainActivityViewModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PokemonAdapter adapter;
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
    }
}