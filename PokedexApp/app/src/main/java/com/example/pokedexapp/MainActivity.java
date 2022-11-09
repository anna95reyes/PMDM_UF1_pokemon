package com.example.pokedexapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.pokedexapp.adapters.PokemonAdapter;
import com.example.pokedexapp.adapters.TypeAdapter;
import com.example.pokedexapp.databinding.ActivityMainBinding;
import com.example.pokedexapp.model.Pokemon;
import com.example.pokedexapp.model.Type;
import com.example.pokedexapp.viewmodel.MainActivityViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    private PokemonAdapter adapter;
    private TypeAdapter adapterType;
    private MainActivityViewModel viewModel;
    private BottomSheetDialog bottomSheetDialog = null;
    private Boolean botoFavoritsClicat = false;
    private Boolean botoFavoritsAbansClicat = false;

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


        //Navigation Drawer
        setSupportActionBar((Toolbar) binding.mtbToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.dwlDrawer, binding.mtbToolbar,
                R.string.app_name, R.string.app_name);
        binding.dwlDrawer.addDrawerListener(toggle);
        toggle.syncState();
        binding.nvwNavigationView.setNavigationItemSelectedListener(this);

        MenuItem menuItem = binding.nvwNavigationView.getMenu().getItem(0);
        onNavigationItemSelected(menuItem);
        menuItem.setChecked(true);

        // Configuració del RecyclerView
        binding.rcyPokemons.setLayoutManager(new LinearLayoutManager(this));
        binding.rcyPokemons.hasFixedSize();

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        //Creacio d'una carpeta dins de la carpeta de l'aplicació
        File jsonFolder = new File(this.getFilesDir(), "jsons");
        jsonFolder.mkdirs(); //per crear la carpeta explicitament

        binding.pgrDownload.setVisibility(View.VISIBLE);

        //descarregar els tipus de pokemons
        viewModel.getTypes(jsonFolder); //Li paso la carpeta a la funcio de descarrega
        viewModel.mGetTypes.observe(this, new Observer<List<Type>>() {
            @Override
            public void onChanged(List<Type> types) {
                binding.pgrDownload.setVisibility(View.INVISIBLE); //TODO

                //descarregar els pokemons
                //viewModel.getPokemons(jsonFolder);
            }
        });

        viewModel.mGetPokemons.observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {
                //Creació de l'adapter
                adapter = new PokemonAdapter(pokemons, MainActivity.this);
                binding.rcyPokemons.setAdapter(adapter);
                //binding.pgrDownload.setVisibility(View.INVISIBLE);
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
                    //TODO: treure filtre per als favorits
                } else {
                    //TODO: fer filtre per als favorits
                }
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
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void showBottomSheetDialog() {
        bottomSheetDialog = new BottomSheetDialog(this);
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
                adapterType = new TypeAdapter(typesFiltre, MainActivity.this, MainActivity.this);
                rcyTypes.setAdapter(adapterType);
            }
        });

        bottomSheetDialog.show();
    }


    @Override
    public void onBackPressed() {
        if (binding.dwlDrawer.isDrawerOpen(GravityCompat.START)) {
            binding.dwlDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.mniPokedex:
                //TODO: t'ha de portar a la finestra de la pokedex
                break;
            case R.id.mniTeamBuilder:
                //TODO: t'ha de portat a la finestra dels equips
                break;
            default:
                throw new IllegalArgumentException("menu option not implemented!");
        }

        binding.dwlDrawer.closeDrawer(GravityCompat.START);

        return true;
    }


    public void canviarFiltreType (String text, int idColor) {
        binding.btnFilterTypes.setText(text);
        binding.btnFilterTypes.getBackground().setTint(ContextCompat.getColor(this,idColor));
        // TODO: agefir filtre del tipus
        bottomSheetDialog.dismiss();
    }


}