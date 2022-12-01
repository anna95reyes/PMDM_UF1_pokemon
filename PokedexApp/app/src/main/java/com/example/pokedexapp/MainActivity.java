package com.example.pokedexapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleRegistryOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

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
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.annotations.NonNull;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        inicialitzarImageLoader();

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

    }

    private void inicialitzarImageLoader() {
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
        for (int i = 0; i < binding.nvwNavigationView.getMenu().size(); i++) {
            binding.nvwNavigationView.getMenu().getItem(i).setChecked(false);
        }
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        switch (menuItem.getItemId()) {
            case R.id.mniPokedex:
                binding.nvwNavigationView.getMenu().getItem(0).setChecked(true);
                navHostFragment.getNavController().navigate(R.id.action_global_pokedexFragment);
                setTitle(R.string.app_name);
                break;
            case R.id.mniTeamBuilder:
                binding.nvwNavigationView.getMenu().getItem(1).setChecked(true);
                navHostFragment.getNavController().navigate(R.id.action_global_teamFragment);
                setTitle(R.string.team_builder);
                break;
            default:
                throw new IllegalArgumentException("menu option not implemented!");
        }



        binding.dwlDrawer.closeDrawer(GravityCompat.START);

        return true;
    }

}