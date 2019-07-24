package cn.part.wallet.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.List;

import cn.part.wallet.BuildConfig;
import cn.part.wallet.R;
import cn.part.wallet.entity.Token;
import cn.part.wallet.service.ITokenHttp;
import cn.part.wallet.service.response.EthBalance;
import cn.part.wallet.utils.Convert;
import cn.part.wallet.utils.LogUtils;
import cn.part.wallet.utils.RetrofitUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.support.v7.widget.RecyclerView.*;

public class TokenAdapter extends RecyclerView.Adapter<TokenAdapter.TokenHolder> {
    private List<Token> mList;
    private Retrofit retrofit;
    private TokenItemClickListener itemClickListener;

    public TokenAdapter (List<Token> list) {
        mList = list;
        retrofit = RetrofitUtil.getRetrofit(BuildConfig.ETHERSCAN_API);
    }

    public void setItemClickListener(TokenItemClickListener listener) {
        itemClickListener = listener;
    }

    @NonNull
    @Override
    public TokenHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_token,viewGroup,false);
        TokenHolder holder = new TokenHolder(view);
        if (itemClickListener != null){
            view.setOnClickListener((lview)->{
                itemClickListener.itemClick(view,mList.get(i));
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TokenHolder tokenHolder, int i) {
        Token token = mList.get(i);
        Log.i("aaa",token.getTokenName());
        tokenHolder.tvTokenName.setText(token.getTokenName());
        tokenHolder.tvTokenNum.setText(token.getTokenNum().toString());
        tokenHolder.tvTokenToCoin.setText(token.getTokenToCoin().toString());
        tokenHolder.imgIcon.setImageResource(R.drawable.eth);
        ITokenHttp service = retrofit.create(ITokenHttp.class);
        Call<EthBalance> call = service.getBalance("account", "balance", token.getAddress(), "latest");
        call.enqueue(new Callback<EthBalance>() {
            @Override
            public void onResponse(Call<EthBalance> call, Response<EthBalance> response) {
//                tokenHolder.tvTokenNum.setText(response.body().getResult());
                BigDecimal yue = Convert.weiToEther(response.body().getResult());
                tokenHolder.tvTokenNum.setText(yue.toString() + " eth");
                LogUtils.e("trans","余额:"+response.body().getResult());
            }

            @Override
            public void onFailure(Call<EthBalance> call, Throwable t) {
                tokenHolder.tvTokenNum.setText("~");
                Log.i("aaa",t.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class TokenHolder extends ViewHolder {
        TextView tvTokenName;
        TextView tvTokenNum;
        ImageView imgIcon;
        TextView tvTokenToCoin;
        public TokenHolder(@NonNull View itemView) {
            super(itemView);
            tvTokenName = itemView.findViewById(R.id.token_name);
            tvTokenNum = itemView.findViewById(R.id.token_num);
            tvTokenToCoin = itemView.findViewById(R.id.token_to_coin);
            imgIcon = itemView.findViewById(R.id.token_icon);
        }
    }
    public interface TokenItemClickListener{
        void itemClick(View view,Token token);
    }
}
