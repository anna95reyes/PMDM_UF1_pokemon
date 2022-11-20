package com.example.pokedexapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pokedexapp.databinding.FragmentDetallPokemonBinding;
import com.example.pokedexapp.model.Pokemon;
import com.example.pokedexapp.viewmodel.MainActivityViewModel;
import com.nostra13.universalimageloader.core.ImageLoader;

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


    private FragmentDetallPokemonBinding binding;
    private ImageLoader mImageLoader;

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
        binding.llyPokemon.txvIdPokemon.setText(getPokemonActualId(mPokemon));
        binding.llyPokemon.txvNamePokemon.setText(getPokemonActualName(mPokemon));
        binding.llyPokemon.rdbFavoritePokemon.setChecked(mPokemon.isFavorite());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
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

        return binding.getRoot();
    }

    public String getPokemonActualId(Pokemon pokemonActual) {
        String text;
        int id = pokemonActual.getId();
        text = "#";
        if (id < 10) {
            text += "00";
        } else if (id < 100) {
            text += "0";
        }
        text += id;
        return text;
    }

    public String getPokemonActualName(Pokemon pokemonActual) {
        String name = pokemonActual.getName();
        String text;
        text = name.substring(0, 1).toUpperCase();
        text += name.substring(1, name.length());

        return text;
    }

}