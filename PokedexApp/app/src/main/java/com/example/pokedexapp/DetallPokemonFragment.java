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

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pokedexapp.databinding.FragmentDetallPokemonBinding;
import com.example.pokedexapp.model.Ability;
import com.example.pokedexapp.model.Pokemon;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetallPokemonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetallPokemonFragment extends Fragment {

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

        for (int i = 0; i < mPokemon.getAbilities().size(); i++) {
            Ability ability = mPokemon.getAbilities().get(i);

            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 10);
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

        binding.txvBaseStats.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(idColor)));

        return binding.getRoot();
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



}