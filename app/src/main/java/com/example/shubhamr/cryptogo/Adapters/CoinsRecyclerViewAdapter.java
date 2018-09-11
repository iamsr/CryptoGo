package com.example.shubhamr.cryptogo.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shubhamr.cryptogo.Interface.ClickListenerInterface;
import com.example.shubhamr.cryptogo.ModelClasses.Coin;
import com.example.shubhamr.cryptogo.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CoinsRecyclerViewAdapter extends RecyclerView.Adapter<CoinsRecyclerViewAdapter.CoinViewHolder>{

    private List<Coin> coinList;
    private String BASE_URL = "https://www.cryptocompare.com";
    private ClickListenerInterface clickListeners = null;

    public CoinsRecyclerViewAdapter(List<Coin> coinList) {
        this.coinList=coinList;
    }

    public class CoinViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

     public  @BindView(R.id.coinIcon) CircleImageView coinIcon;
        @BindView(R.id.coinName) TextView coinName;
        @BindView(R.id.coinChange) TextView coinChange;

        public CoinViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
            view.setOnClickListener(this);
        }

        // View reference and item position is sent to activity or fragment which implements the interface
        @Override
        public void onClick(View view) {
            if (clickListeners != null) {
                clickListeners.itemClicked(view, getAdapterPosition());
            }


        }


    }




    @Override
    public CoinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.coins_recyclerview_layout, parent, false);
        return new CoinViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull CoinViewHolder holder, int position) {

        Coin coin = coinList.get(position);

        //Setting detail in view items
        Picasso.get().load(BASE_URL+coin.getIcon()).into(holder.coinIcon);
        holder.coinName.setText(coin.getName());

    }


    @Override
    public int getItemCount(){
        return coinList.size();
    }

    public void setClickListeners(ClickListenerInterface clickListeners) {
        this.clickListeners = clickListeners;
    }


    public List<Coin> getCoinList(){return coinList;}


}
