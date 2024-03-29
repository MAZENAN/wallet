package cn.part.wallet.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.part.wallet.R;
import cn.part.wallet.entity.TxInfo;
import cn.part.wallet.utils.Util;

public class TxlistAdapter extends RecyclerView.Adapter<TxlistAdapter.TxHolder> {
    private List<TxInfo> list;
    private TxItemClickListener listener;
    private Context mContext;

    public TxlistAdapter(Context context, List<TxInfo> list) {
        this.list = list;
        mContext = context;
    }

    public void setList (List<TxInfo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TxHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewTx = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_transaction, viewGroup, false);
        return new TxHolder(viewTx);
    }

    @Override
    public void onBindViewHolder(@NonNull TxHolder txHolder, int i) {
        TxInfo trans = list.get(i);
        String value = "0";
        String prefix = "";
       if(Util.compareValue(trans.getIncoming(),trans.getOutgoing())) {
           value = trans.getIncoming();
           txHolder.tvValue.setTextColor(mContext.getResources().getColor(R.color.trans_in));
           prefix = "+";
           txHolder.imgIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_in));
       }else {
           value = trans.getOutgoing();
           txHolder.tvValue.setTextColor(mContext.getResources().getColor(R.color.trans_out));
           txHolder.imgIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_out));
           prefix = "-";
       }
        txHolder.tvValue.setText(prefix+value);
        txHolder.tvTime.setText(trans.getTime());
        txHolder.txId.setText(trans.getTxid());
        if (listener != null) {
            txHolder.itemview.setOnClickListener((viwe)->{
                //listener.itemClick(txHolder.itemview,trans);
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
        TextView txId;
        TextView tvTime;
        TextView tvValue;
        View itemview;

         TxHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.type_icon);
             txId = itemView.findViewById(R.id.tv_txid);
             tvTime = itemView.findViewById(R.id.tv_time);
            tvValue = itemView.findViewById(R.id.tv_value);
            this.itemview = itemView;
        }
    }

    public interface TxItemClickListener{
        void itemClick(View view, TxInfo transInfo);
    }
}
