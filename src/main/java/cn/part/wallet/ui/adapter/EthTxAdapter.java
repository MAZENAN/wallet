package cn.part.wallet.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.part.wallet.R;
import cn.part.wallet.entity.TransInfo;
import cn.part.wallet.service.response.EthTxInfo;
import cn.part.wallet.utils.Convert;

public class EthTxAdapter extends RecyclerView.Adapter<EthTxAdapter.TxHolder> {
    private List<TransInfo> list;
    private TxItemClickListener listener;
    private String selefAddress;

    public EthTxAdapter(List<TransInfo> list,String selefAddress) {
        this.list = list;
        this.selefAddress = selefAddress;
    }

    public void setList (List<TransInfo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TxHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewTx = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_transaction, viewGroup, false);
        TxHolder holder = new TxHolder(viewTx);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TxHolder txHolder, int i) {
        TransInfo trans = list.get(i);

        txHolder.tvValue.setText(Convert.weiToEther(trans.getValue()).toString() + " eth");

        if (selefAddress.equals(trans.getFrom())) {
            txHolder.imgIcon.setImageResource(R.drawable.ic_arrow_upward_red_500_48dp);
            txHolder.tvType.setText("转出");
            txHolder.tvAddress.setText(trans.getTo());
        }else {
            txHolder.imgIcon.setImageResource(R.drawable.ic_arrow_downward_green_500_48dp);
            txHolder.tvType.setText("转入");
            txHolder.tvAddress.setText(trans.getFrom());
        }

        if (listener != null) {
            txHolder.itemview.setOnClickListener((viwe)->{
                listener.itemClick(txHolder.itemview,trans);
            });
        }
    }

    @Override
    public int getItemCount() {
        return list==null ? 0 : list.size();
    }

    public void setListener(TxItemClickListener listener) {
        this.listener = listener;
    }

     static class TxHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView tvType;
        TextView tvAddress;
        TextView tvValue;
        RelativeLayout itemview;

         TxHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.type_icon);
            tvType = itemView.findViewById(R.id.tv_type);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvValue = itemView.findViewById(R.id.tv_value);
            this.itemview = itemView.findViewById(R.id.itemview);
        }
    }

    public interface TxItemClickListener{
        void itemClick(View view, TransInfo transInfo);
    }
}
