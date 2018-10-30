package com.example.robin.demoapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robin.demoapp.R;

import java.util.ArrayList;

public class RVAVehicles extends RecyclerView.Adapter<RVAVehicles.VehicleViewHolder> {

    private Context mContext;
    private ArrayList<Vehicle> mVehiclesList;

    /**
     * Constructeur de l'adapter
     * @param context Context de l'activité appelante
     * @param dataList Liste contenant les objets à afficher
     */
    public RVAVehicles(Context context, ArrayList<Vehicle> dataList) {
        this.mContext = context;
        this.mVehiclesList = dataList;
    }

    /**
     * Création des vues
     * @param parent ViewGroup du parent
     * @param viewType Type de la vue
     * @return ViewHolder
     */
    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_vehicle, parent, false);
        return new VehicleViewHolder(v);
    }

    /**
     * Bind de la vue : pour chaque item, on affiche le layout
     * @param holder Holder contenant une ligne
     * @param position Position de l'item dans la liste
     */
    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {

        Vehicle vehicle = mVehiclesList.get(position);

        final String id = vehicle.getId();
        String brand = vehicle.getBrand();
        String model = vehicle.getModel();

        holder.tvVehicleBrand.setText(brand);
        holder.tvVehicleModel.setText(model);

        // Set row listener
        holder.ivVehiclePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Id du véhicule : " + id, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Retourne la taille de la liste fournie en paramètre du constructeur
     * @return int Taille
     */
    @Override
    public int getItemCount() {
        return mVehiclesList.size();
    }

    /**
     * ViewHolder pour chaque ligne correspondant à un objet de la liste
     */
    public class VehicleViewHolder extends RecyclerView.ViewHolder {

        private TextView tvVehicleBrand, tvVehicleModel;
        private ImageView ivVehiclePicture;

        private VehicleViewHolder(View v) {
            super(v);

            tvVehicleBrand = v.findViewById(R.id.tv_vehicle_brand);
            tvVehicleModel = v.findViewById(R.id.tv_vehicle_model);
            ivVehiclePicture = v.findViewById(R.id.iv_vehicle_picture);
        }
    }
}