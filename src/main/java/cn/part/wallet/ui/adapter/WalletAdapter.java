package cn.part.wallet.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.part.wallet.R;
import cn.part.wallet.entity.WalletInfo;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.WalletHolder> {
    private List<WalletInfo> walletInfos;
    public WalletAdapter(List<WalletInfo> list){
        walletInfos = list;
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
        WalletInfo walletInfo = walletInfos.get(i);
        walletHolder.tvWalletName.setText(walletInfo.getWalletName());
        String address =walletInfo.getAddress();
        walletHolder.tvWalletAddr.setText(address.substring(0,10)+"..."+address.substring(29));
    }

    @Override
    public int getItemCount() {
        return walletInfos.size();
    }

    public static class WalletHolder extends RecyclerView.ViewHolder {
        TextView tvWalletName;
        TextView tvWalletAddr;
        public WalletHolder(@NonNull View itemView) {
            super(itemView);
            tvWalletName = itemView.findViewById(R.id.tv_wallet_name);
            tvWalletAddr = itemView.findViewById(R.id.tv_wallet_addr);
        }
    }
}
