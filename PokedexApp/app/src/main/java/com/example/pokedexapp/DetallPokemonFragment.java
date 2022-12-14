package com.example.pokedexapp;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pokedexapp.adapters.EvolucioAdapter;
import com.example.pokedexapp.adapters.PokemonAdapter;
import com.example.pokedexapp.databinding.FragmentDetallPokemonBinding;
import com.example.pokedexapp.model.Ability;
import com.example.pokedexapp.model.Pokemon;
import com.example.pokedexapp.model.Stat;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.DecimalFormat;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetallPokemonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetallPokemonFragment extends Fragment implements EvolucioAdapter.EvolucioSelectedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_PARAM_POKEMON = "pokemon";
    // TODO: Rename and change types of parameters
    private Pokemon mPokemon;

    private final Integer MAX_BASE_STATS = 100;
    private final float METRES_A_PEUS = 3.2808f;
    private final float KG_A_LBS = 2.2046226218f;
    private DecimalFormat df;
    private FragmentDetallPokemonBinding binding;
    private ImageLoader mImageLoader;
    private LinearLayout.LayoutParams params;
    private RelativeLayout.LayoutParams relativeParams;
    private EvolucioAdapter adapter;

    public DetallPokemonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment DetallPokemonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetallPokemonFragment newInstance(String param1, String param2) {
        DetallPokemonFragment fragment = new DetallPokemonFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_POKEMON, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mImageLoader = ImageLoader.getInstance();
            mPokemon = (Pokemon) getArguments().getSerializable(ARG_PARAM_POKEMON);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentDetallPokemonBinding.inflate(inflater, container, false);

        mImageLoader.displayImage(mPokemon.getImageURL(), binding.llyPokemon.imvImagePokemon);
        binding.llyPokemon.txvIdPokemon.setText(mPokemon.getPokemonId());
        binding.llyPokemon.txvNamePokemon.setText(mPokemon.getPokemonName());
        binding.llyPokemon.rdbFavoritePokemon.setChecked(mPokemon.isFavorite());

        params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        params.setMargins(7, 5, 7, 5);

        binding.llyPokemon.llyAbilityPokemon.removeAllViews();
        for (int i = 0; i < mPokemon.getTypes().size(); i++) {
                TextView textView = new TextView(binding.llyPokemon.llyAbilityPokemon.getContext());
                textView.setText(mPokemon.getTypes().get(i).getName().toUpperCase());
                textView.setBackgroundResource(R.drawable.capseta_fila_pokemon_types);
                textView.setTextSize(14);
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView.setLayoutParams(params);
                textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray));
                binding.llyPokemon.llyAbilityPokemon.addView(textView);
        }
        int idColor = requireContext().getResources().getIdentifier("mig_transparent", "color", requireContext().getPackageName());
        binding.llyPokemon.llyPokemonPokedex.getBackground().setTint(ContextCompat.getColor(requireContext(), idColor));

        idColor = requireContext().getResources().getIdentifier("background_"+mPokemon.getTypes().get(0).getName(), "color", requireContext().getPackageName());

        binding.llyFragmentDetallPokemon.setBackgroundColor(ContextCompat.getColor(requireContext(), idColor));

        binding.txvDescriptionPokemon.setText(mPokemon.getDefinition().replace("\n", " "));

        binding.txvHeightPokemon.setText(getHeight());
        binding.txvWeightPokemon.setText(getWeight());

        getAbilities(idColor);
        getBaseStats(idColor);
        binding.txvTotalBaseStats.setText(""+mPokemon.getTotalStats());

        binding.txvBaseStats.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(idColor)));

        getEvolicions();

        return binding.getRoot();
    }

    EvolucioAdapter.EvolucioSelectedListener listener = this;

    private void getEvolicions() {
        // Configuraci?? del RecyclerView
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.rcyPokemonEvolutions.setLayoutManager(horizontalLayoutManager);
        binding.rcyPokemonEvolutions.hasFixedSize();
        adapter = new EvolucioAdapter(mPokemon.getEvolutions(), requireContext(), listener);
        binding.rcyPokemonEvolutions.setAdapter(adapter);
    }

    private void getBaseStats(int idColor) {

        binding.llyBaseStats.removeAllViews();

        for (int i = 0; i < mPokemon.getStats().size(); i++) {

            Stat stat = mPokemon.getStats().get(i);

            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout linearLayout = new LinearLayout(binding.llyBaseStats.getContext());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setLayoutParams(params);

            int tamanyCapsetaNom = 310;
            int tamanyMinimCapsetaNumero = 90;
            int base_stat = stat.getBase_stat() >= MAX_BASE_STATS? MAX_BASE_STATS : stat.getBase_stat();
            params = new LinearLayout.LayoutParams(tamanyCapsetaNom + tamanyMinimCapsetaNumero, LinearLayout.LayoutParams.WRAP_CONTENT, base_stat);
            params.setMargins(0, 15, 0, 15);

            int idColorRelativeLayout = requireContext().getResources().getIdentifier(mPokemon.getTypes().get(0).getName(), "color", requireContext().getPackageName());

            RelativeLayout relativeLayout = new RelativeLayout(linearLayout.getContext());
            relativeLayout.setBackgroundResource(R.drawable.capseta_fila_pokemon_types);
            relativeLayout.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(idColorRelativeLayout)));
            relativeLayout.setBackgroundTintMode(PorterDuff.Mode.ADD);
            relativeLayout.setPadding(0,0,0,0);
            relativeLayout.setLayoutParams(params);

            relativeParams = new RelativeLayout.LayoutParams(tamanyCapsetaNom, RelativeLayout.LayoutParams.WRAP_CONTENT);
            relativeParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

            TextView textViewStat = new TextView(relativeLayout.getContext());
            textViewStat.setText(stat.getStatName());
            textViewStat.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textViewStat.setBackgroundResource(R.drawable.capseta_abilities_hidden);
            textViewStat.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(idColor)));
            textViewStat.setPadding(45, 15, 30, 15);
            textViewStat.setLayoutParams(relativeParams);

            relativeParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            relativeParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            relativeParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            TextView textView = new TextView(relativeLayout.getContext());
            textView.setText(""+ stat.getBase_stat());
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(0,0,45,0);
            textView.setLayoutParams(relativeParams);

            params = new LinearLayout.LayoutParams(0, 1, MAX_BASE_STATS- base_stat);
            View view = new View(linearLayout.getContext());
            view.setLayoutParams(params);

            relativeLayout.addView(textViewStat);
            relativeLayout.addView(textView);
            linearLayout.addView(relativeLayout);
            linearLayout.addView(view);
            binding.llyBaseStats.addView(linearLayout);

        }
    }

    private void getAbilities(int idColor) {
        binding.llyAbilitiesPokemon.removeAllViews();
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 10, 10, 10);
        for (int i = 0; i < mPokemon.getAbilities().size(); i++) {
            Ability ability = mPokemon.getAbilities().get(i);
            if (ability.isIs_hidden()) {
                RelativeLayout relativeLayout = new RelativeLayout(binding.llyAbilitiesPokemon.getContext());
                relativeLayout.setBackgroundResource(R.drawable.capseta_fila_pokemon_types);
                relativeLayout.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(idColor)));
                relativeLayout.setPadding(0,0,0,0);
                relativeLayout.setLayoutParams(params);

                relativeParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                relativeParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

                TextView textViewHidden = new TextView(relativeLayout.getContext());
                textViewHidden.setText("Hidden");
                textViewHidden.setBackgroundResource(R.drawable.capseta_abilities_hidden);
                textViewHidden.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(idColor)));
                textViewHidden.setPadding(45, 15, 30, 15);
                textViewHidden.setLayoutParams(relativeParams);

                relativeParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                relativeParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                TextView textView = new TextView(relativeLayout.getContext());
                textView.setText(ability.getAbilityName());
                textView.setLayoutParams(relativeParams);

                relativeLayout.addView(textViewHidden);
                relativeLayout.addView(textView);
                binding.llyAbilitiesPokemon.addView(relativeLayout);
            } else {
                TextView textView = new TextView(binding.llyAbilitiesPokemon.getContext());
                textView.setText(ability.getAbilityName());
                textView.setBackgroundResource(R.drawable.capseta_fila_pokemon_pokedex);
                textView.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(idColor)));
                textView.setBackgroundTintMode(PorterDuff.Mode.ADD);
                textView.setPadding(15, 15, 15, 15);
                textView.setGravity(Gravity.CENTER);
                textView.setLayoutParams(params);
                binding.llyAbilitiesPokemon.addView(textView);
            }

        }
    }

    private String getWeight() {
        float weightKg = mPokemon.getWeight()/10f;
        df = new DecimalFormat("0.00");
        String weightLbs = df.format(weightKg*KG_A_LBS);
        return weightLbs + " lbs (" + weightKg + " kg)";
    }


    private String getHeight() {
        float heightMetres = mPokemon.getHeight()/10f;
        df = new DecimalFormat("0.0");
        String heightPies = df.format(heightMetres * METRES_A_PEUS);
        return heightPies.substring(0,1) + "\'" + heightPies.substring(2,3) + "\"" + " (" + heightMetres + " m)";
    }

    public Pokemon getPokemonPerNom(String name, List<Pokemon> llistaPokemons) {
        Pokemon pokemon = null;
        name = name.toLowerCase();
        int i = 0;
        while (i < llistaPokemons.size() && !llistaPokemons.get(i).getName().equals(name)) {
            i++;
        }

        if (i < llistaPokemons.size()){
            pokemon = llistaPokemons.get(i);
        }

        return pokemon;
    }


    @Override
    public void onEvolucioSeleccionat(String namePokemonEvolution, List<Pokemon> evolucions) {
        Bundle args = new Bundle();

        args.putSerializable(DetallPokemonFragment.ARG_PARAM_POKEMON, getPokemonPerNom(namePokemonEvolution, evolucions));
        NavController navController =  NavHostFragment.findNavController(this);
        navController.navigate(R.id.detallPokemonFragment, args);
    }
}