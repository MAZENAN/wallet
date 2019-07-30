package cn.part.wallet.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.consenlabs.tokencore.wallet.Wallet;
import org.consenlabs.tokencore.wallet.model.ChainType;

import java.util.List;

import cn.part.wallet.R;

public class SwitchWalletAdapter extends RecyclerView.Adapter<SwitchWalletAdapter.WalletHolder> {
    private List<Wallet> mList;
    private WalletClickListener mListener;
    private Context mContext;

    public SwitchWalletAdapter(Context context,List<Wallet> list){
        mList = list;
        mContext = context;
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
        walletHolder.tvWalletAddr.setText(wallet.getAddress());
        switch (wallet.getMetadata().getChainType()) {
            case ChainType.ETHEREUM:
                walletHolder.imgIcon.setImageDrawable(mContext.getDrawable(R.drawable.eth));
                break;
            case ChainType.BITCOIN:
                walletHolder.imgIcon.setImageDrawable(mContext.getDrawable(R.drawable.btc));
                break;
        }
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
