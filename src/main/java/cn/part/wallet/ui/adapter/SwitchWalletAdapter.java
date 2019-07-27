package cn.part.wallet.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.consenlabs.tokencore.wallet.Wallet;

import java.util.List;

import cn.part.wallet.R;

public class SwitchWalletAdapter extends RecyclerView.Adapter<SwitchWalletAdapter.WalletHolder> {
    private List<Wallet> mList;
    private WalletClickListener mListener;

    public SwitchWalletAdapter(List<Wallet> list){
        mList = list;
    }
    @NonNull
    @Override
    public WalletHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_wallet_switch,viewGroup,false);
        return new WalletHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletHolder walletHolder, int i) {
        Wallet wallet = mList.get(i);
        String address =wallet.getAddress();
        walletHolder.tvWalletAddr.setText(address.substring(0,10)+"..."+address.substring(29));
        //TODO 设置icon
        if (mListener!=null){
            walletHolder.rootview.setOnClickListener((view)->{
                mListener.itemClick(walletHolder.rootview,wallet);
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setItemClickListener(WalletClickListener listener) {
        this.mListener = listener;
    }

    public void setmList(List<Wallet> wallets) {
        mList = wallets;
        notifyDataSetChanged();
    }

    static class WalletHolder extends RecyclerView.ViewHolder {
        TextView tvWalletAddr;
        ImageView imgIcon;
        ViewGroup rootview;
        WalletHolder(@NonNull View itemView) {
            super(itemView);
            tvWalletAddr = itemView.findViewById(R.id.tv_wallet_address);
            imgIcon = itemView.findViewById(R.id.img_wallet_icon);
            rootview = itemView.findViewById(R.id.item_view);
        }
    }

    public interface WalletClickListener{
        void itemClick(View view, Wallet wallet);
    }
}
