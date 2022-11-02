package com.example.pokedexapp.adapters;

import android.content.Context;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedexapp.R;
import com.example.pokedexapp.model.Pokemon;
import com.example.pokedexapp.model.Type;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder> {

    private List<Type> mTypes;
    private Context mContext;

    public TypeAdapter(List<Type> types, Context context){
        mTypes = types;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewFila = LayoutInflater.from(parent.getContext()).inflate(R.layout.fitxa_types, parent, false);
        ViewHolder vh = new ViewHolder(viewFila);

        viewFila.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Type actual = mTypes.get(position);
        holder.txvNameType.setText(actual.getName().toUpperCase());
        String nomActual = actual.getName();
        if (nomActual.contains(" ")){
            nomActual = nomActual.replace(" ", "_");
        }
        int idColor = mContext.getResources().getIdentifier(nomActual, "color", mContext.getPackageName());
        holder.itemView.getBackground().setTint(ContextCompat.getColor(mContext, idColor));
    }

    @Override
    public int getItemCount() {
        return mTypes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txvNameType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txvNameType = itemView.findViewById(R.id.txvNameType);
        }
    }
}
