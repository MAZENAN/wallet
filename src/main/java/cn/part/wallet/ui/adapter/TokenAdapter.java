package cn.part.wallet.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import org.consenlabs.tokencore.wallet.model.ChainType;
import java.util.List;
import cn.part.wallet.R;
import cn.part.wallet.entity.Token;
import cn.part.wallet.service.IWalletApi;
import cn.part.wallet.service.response.TokenBalance;
import cn.part.wallet.utils.Convert;
import cn.part.wallet.utils.LogUtils;
import cn.part.wallet.utils.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static android.support.v7.widget.RecyclerView.*;

public class TokenAdapter extends RecyclerView.Adapter<TokenAdapter.TokenHolder> {
    private List<Token> mList;
    private TokenItemClickListener mListener;

    public TokenAdapter (List<Token> list) {
        mList = list;
    }

    public void setItemClickListener(TokenItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public TokenHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LogUtils.i("adapter","create_view"+ i);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_token,viewGroup,false);
        return new TokenHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TokenHolder tokenHolder, int i) {
        Token token = mList.get(i);
        tokenHolder.tvTokenName.setText(token.getTokenName());
        tokenHolder.tvTokenNum.setText(token.getTokenNum().toString());
        tokenHolder.tvTokenToCoin.setText(token.getTokenToCoin().toString());

        String type = "";
        switch (token.getType()) {
            case ChainType.ETHEREUM:
                tokenHolder.imgIcon.setImageResource(R.drawable.eth);
                type = "ETH";
                break;
            case ChainType.BITCOIN:
                tokenHolder.imgIcon.setImageResource(R.drawable.btc);
                type = "BTC";
                break;
        }

        IWalletApi service = Util.getRetrofit(IWalletApi.HOST).create(IWalletApi.class);
        Call<TokenBalance> balanceCall =null;
        if (token.getToken()) {
            balanceCall = service.getTokenBalance(type,token.getAddress(),token.getContractaddress());
        }else {
            balanceCall = service.getBalance(type, token.getAddress(), Util.getNetEnv());
        }

        balanceCall.enqueue(new Callback<TokenBalance>(){
            @Override
            public void onResponse(Call<TokenBalance> call, Response<TokenBalance> response) {
                TokenBalance tokenBalance = response.body();
                String tokenNum = "0";
                if (tokenBalance.getStatus()==200){
                    String balance = tokenBalance.getData().getConfirmed_balance();
                    if (token.getType().equals(ChainType.ETHEREUM)){
                        tokenNum = Convert.weiToEther(balance).toString() + " ETH";
                    }else if (token.getType().equals(ChainType.BITCOIN)) {
                        tokenNum = balance + " BTC";
                    }
                    tokenHolder.tvTokenNum.setText(tokenNum);
                } else {
                    tokenHolder.tvTokenNum.setText("~");
                }
            }

            @Override
            public void onFailure(Call<TokenBalance> call, Throwable t) {
                tokenHolder.tvTokenNum.setText("~");
            }
        });
       if (mListener != null) {
           tokenHolder.itemview.setOnClickListener((view)->{
               mListener.itemClick(view,token);
           });
       }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(List<Token> list) {
        mList = list;
        notifyDataSetChanged();
    }

    static class TokenHolder extends ViewHolder {
        TextView tvTokenName;
        TextView tvTokenNum;
        ImageView imgIcon;
        TextView tvTokenToCoin;
        View itemview;

        TokenHolder(@NonNull View itemView) {
            super(itemView);
            tvTokenName = itemView.findViewById(R.id.token_name);
            tvTokenNum = itemView.findViewById(R.id.token_num);
            tvTokenToCoin = itemView.findViewById(R.id.token_to_coin);
            imgIcon = itemView.findViewById(R.id.token_icon);
            this.itemview = itemView;
        }
    }

    public interface TokenItemClickListener{
        void itemClick(View view,Token token);
    }
}
