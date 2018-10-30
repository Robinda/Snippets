package com.example.robin.demoapp.test;

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

public class RVACustomList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_VEHICLE = 0;
    private final int VIEW_TYPE_SHOP = 1;

    private Context mContext;
    private ArrayList<Object> objectsList;

    /**
     * Constructeur de l'adapter
     * @param context Context de l'activité appelante
     * @param objectsList Liste contenant les informations à afficher
     */
    public RVACustomList(Context context, ArrayList<Object> objectsList) {
        this.mContext = context;
        this.objectsList = objectsList;
    }

    /**
     * Création des vues
     * @param parent ViewGroup du parent
     * @param viewType Type de la vue
     * @return ViewHolder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_VEHICLE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_vehicle, parent, false);
            return new VehicleViewHolder(view);
        }
        else if (viewType == VIEW_TYPE_SHOP) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_shop, parent, false);
            return new ShopViewHolder(view);
        }

        return null;
    }

    /**
     * Bind des vues : suivant la nature de l'item (renvoyé par getItemViewType), on affiche un layout différent
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        // Element de type "Vehicle"
        if (holder instanceof VehicleViewHolder) {

            VehicleViewHolder vehicleViewHolder = (VehicleViewHolder) holder;
            Vehicle vehicle = (Vehicle) objectsList.get(position);

            final String id = vehicle.getId();
            String brand = vehicle.getBrand();
            String model = vehicle.getModel();

            vehicleViewHolder.tvVehicleBrand.setText(brand);
            vehicleViewHolder.tvVehicleModel.setText(model);

            // Set row listener
            vehicleViewHolder.ivVehiclePicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Id du véhicule : " + id, Toast.LENGTH_SHORT).show();
                }
            });
        }
        // Element de type "Shop"
        else if(holder instanceof ShopViewHolder) {
            ShopViewHolder shopViewHolder = (ShopViewHolder) holder;
            Shop shop = (Shop) objectsList.get(position);

            String name = shop.getName();
            String address = shop.getAddress();
            final String shopSpeciality = shop.getShopSpeciality();

            shopViewHolder.tvShopName.setText(name);
            shopViewHolder.tvShopAddress.setText(address);

            // Set row listener
            shopViewHolder.ivShopPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Spécialité du shop : " + shopSpeciality, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * Retour la taille de la liste passée en paramètre du constructeur
     * @return int Taille
     */
    @Override
    public int getItemCount() {
        return objectsList.size();
    }

    /**
     * Retour le type de l'élément en cours
     * @param position Position de l'élément dans la liste
     * @return int Type
     */
    @Override
    public int getItemViewType(int position) {
        return objectsList.get(position) instanceof Vehicle ? VIEW_TYPE_VEHICLE : VIEW_TYPE_SHOP;
    }

    /**
     * ViewHolder pour les item de type "Vehicle"
     */
    private class VehicleViewHolder extends RecyclerView.ViewHolder {
        private TextView tvVehicleBrand, tvVehicleModel;
        private ImageView ivVehiclePicture;

        private VehicleViewHolder(View v) {
            super(v);
            tvVehicleBrand = v.findViewById(R.id.tv_vehicle_brand);
            tvVehicleModel = v.findViewById(R.id.tv_vehicle_model);
            ivVehiclePicture = v.findViewById(R.id.iv_vehicle_picture);
        }
    }

    /**
     * ViewHolder pour les item de type "Shop"
     */
    private class ShopViewHolder extends RecyclerView.ViewHolder {
        private TextView tvShopName, tvShopAddress;
        private ImageView ivShopPicture;

        private ShopViewHolder(View v) {
            super(v);
            tvShopName = v.findViewById(R.id.tv_shop_name);
            tvShopAddress = v.findViewById(R.id.tv_shop_address);
            ivShopPicture = v.findViewById(R.id.iv_shop_picture);
        }
    }
}
