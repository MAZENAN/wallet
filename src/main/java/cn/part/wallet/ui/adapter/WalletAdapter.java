package cn.part.wallet.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.consenlabs.tokencore.wallet.Wallet;

import java.util.List;

import cn.part.wallet.R;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.WalletHolder> {
    private List<Wallet> mList;
    private WalletClickListener mListener;

    public WalletAdapter(List<Wallet> list){
        mList = list;
    }
    @NonNull
    @Override
    public WalletHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_walletinfo,viewGroup,false);
        WalletHolder holder = new WalletHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WalletHolder walletHolder, int i) {
        Wallet wallet = mList.get(i);
        walletHolder.tvWalletName.setText(wallet.getMetadata().getName());
        String address =wallet.getAddress();
//        walletHolder.tvWalletAddr.setText(address.substring(0,10)+"..."+address.substring(29));
        walletHolder.tvWalletAddr.setText(address);
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

    public void setListener(WalletClickListener listener) {
        this.mListener = listener;
    }

    static class WalletHolder extends RecyclerView.ViewHolder {
        TextView tvWalletName;
        TextView tvWalletAddr;
        CardView rootview;
        WalletHolder(@NonNull View itemView) {
            super(itemView);
            tvWalletName = itemView.findViewById(R.id.tv_wallet_name);
            tvWalletAddr = itemView.findViewById(R.id.tv_wallet_addr);
            rootview = itemView.findViewById(R.id.item_view);
        }
    }

    public interface WalletClickListener{
        void itemClick(View view, Wallet wallet);
    }
}
