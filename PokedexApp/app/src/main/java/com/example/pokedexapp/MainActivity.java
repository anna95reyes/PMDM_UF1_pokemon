package com.example.pokedexapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.pokedexapp.adapters.PokemonAdapter;
import com.example.pokedexapp.databinding.ActivityMainBinding;
import com.example.pokedexapp.model.Pokemon;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PokemonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configuració del Universal Image Loader

        DisplayImageOptions options =
                new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.drawable.pokedex)
                        .showImageForEmptyUri(R.drawable.pokedex)
                        .showImageOnFail(R.drawable.pokedex)
                        .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .build();

        ImageLoader.getInstance().init(config);

        // Configuració del RecyclerView
        binding.rcyPokemons.setLayoutManager(new LinearLayoutManager(this));
        binding.rcyPokemons.hasFixedSize();

        //Creació de l'adapter
        adapter = new PokemonAdapter(Pokemon.getPokemons());
        binding.rcyPokemons.setAdapter(adapter);

    }
}