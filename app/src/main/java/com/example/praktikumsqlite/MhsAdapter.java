package com.example.praktikumsqlite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MhsAdapter extends RecyclerView.Adapter <MhsAdapter.MhsVH> {

    private ArrayList<MhsModel> mhsList;
    private final OnItemClickListener listener;

    public MhsAdapter(ArrayList<MhsModel> mhsList, OnItemClickListener listener) {

        this.mhsList = mhsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MhsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.item_listmhs, parent,false);

        return new MhsVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MhsVH holder, int position) {
        holder.tvNamaVal.setText(mhsList.get(position).getNama());
        holder.tvNimVal.setText(mhsList.get(position).getNim());
        holder.tvNoHpVal.setText(mhsList.get(position).getNoHp());

        holder.bind(mhsList, position, listener);
    }

    public interface OnItemClickListener{
        void OnItemClickListener(ArrayList<MhsModel> mhsList, int position);
    }

    public void removeItem(int position){
        this.mhsList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {return mhsList.size();
    }

    public class MhsVH extends RecyclerView.ViewHolder{
        private TextView tvNamaVal, tvNimVal, tvNoHpVal;
        private CardView cvItem;
        public MhsVH(@NonNull View itemView) {
            super(itemView);

            tvNamaVal = itemView.findViewById(R.id.tvNamaVal);
            tvNimVal = itemView.findViewById(R.id.tvNimVal);
            tvNoHpVal = itemView.findViewById(R.id.tvNoHpVal);

            cvItem = itemView.findViewById(R.id.cvItem);
        }

        public void bind (final ArrayList<MhsModel> mhsList, int position, OnItemClickListener listener){
            cvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClickListener(mhsList, position);
                    notifyDataSetChanged();
                }
            });
        }

    }
}
