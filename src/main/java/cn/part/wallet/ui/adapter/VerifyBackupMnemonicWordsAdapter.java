package cn.part.wallet.ui.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.Collections;
import java.util.List;
import cn.part.wallet.R;
import cn.part.wallet.domain.VerifyMnemonicWordTag;

public class VerifyBackupMnemonicWordsAdapter extends BaseQuickAdapter<VerifyMnemonicWordTag, BaseViewHolder> {

    public VerifyBackupMnemonicWordsAdapter(int layoutResId, @Nullable List<VerifyMnemonicWordTag> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VerifyMnemonicWordTag verifyMnemonicWordTag) {
        if (verifyMnemonicWordTag.isSelected()) {
            helper.setBackgroundColor(R.id.lly_tag, mContext.getResources().getColor(R.color.search_ico_upload_token));
            helper.setBackgroundRes(R.id.lly_tag,R.drawable.btn_red);
            helper.setTextColor(R.id.tv_mnemonic_word,mContext.getResources().getColor(R.color.white));
        } else {
            helper.setBackgroundRes(R.id.lly_tag,R.drawable.btn_gray);
            helper.setTextColor(R.id.tv_mnemonic_word,mContext.getResources().getColor(R.color.discovery_application_text_color));
        }
        helper.setText(R.id.tv_mnemonic_word, verifyMnemonicWordTag.getMnemonicWord());
    }

    public boolean setSelection(int position) {

        VerifyMnemonicWordTag verifyMnemonicWordTag = getData().get(position);
        if (verifyMnemonicWordTag.isSelected()) {
            return false;
        }
        verifyMnemonicWordTag.setSelected(true);
        Collections.shuffle(getData());
        notifyDataSetChanged();
        return true;
    }

    public boolean setUnselected(int position) {

        VerifyMnemonicWordTag verifyMnemonicWordTag = getData().get(position);
        if (!verifyMnemonicWordTag.isSelected()) {
            return false;
        }
        verifyMnemonicWordTag.setSelected(false);
        Collections.shuffle(getData());
        notifyDataSetChanged();
        return true;
    }

}
