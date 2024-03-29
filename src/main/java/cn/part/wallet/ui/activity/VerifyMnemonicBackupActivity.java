package cn.part.wallet.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseActivity;
import cn.part.wallet.domain.VerifyMnemonicWordTag;
import cn.part.wallet.ui.adapter.VerifyBackupMnemonicWordsAdapter;
import cn.part.wallet.ui.adapter.VerifyBackupSelectedMnemonicWordsAdapter;
import cn.part.wallet.utils.Conf;
import cn.part.wallet.utils.LogUtils;
import cn.part.wallet.utils.ToastUtil;

// 验证 助记词是否 正确界面
public class VerifyMnemonicBackupActivity extends BaseActivity {
    private static final int VERIFY_SUCCESS_RESULT = 2202;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_selected)
    RecyclerView rvSelected;
    @BindView(R.id.rv_mnemonic)
    RecyclerView rvMnemonic;
    private String walletMnemonic;

    private VerifyBackupMnemonicWordsAdapter verifyBackupMenmonicWordsAdapter;
    private VerifyBackupSelectedMnemonicWordsAdapter verifyBackupSelectedMnemonicWordsAdapter;
    private long walletId;

    @Override
    protected void onBeforeSetContentLayout() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_verify_mnemonic_backup;
    }

    @Override
    public void initToolBar() {
        tvTitle.setText(R.string.mnemonic_backup_title);
    }

    @Override
    public void initDatas() {
//        walletId = getIntent().getLongExtra("walletId", -1);
        walletMnemonic = getIntent().getStringExtra("walletMnemonic");

        LogUtils.d("VerifyMnemonicBackUp", "walletMnemonic:" + walletMnemonic);

        String[] words = walletMnemonic.split("\\s+");
        List<VerifyMnemonicWordTag> mnemonicWords = new ArrayList();
        for (int i = 0; i < words.length; i++) {
            VerifyMnemonicWordTag verifyMnemonicWordTag = new VerifyMnemonicWordTag();
            verifyMnemonicWordTag.setMnemonicWord(words[i]);
            mnemonicWords.add(verifyMnemonicWordTag);
        }
        // 乱序
        Collections.shuffle(mnemonicWords);

        // 未选中单词
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setAlignItems(AlignItems.STRETCH);
        rvMnemonic.setLayoutManager(layoutManager);
        verifyBackupMenmonicWordsAdapter = new VerifyBackupMnemonicWordsAdapter(R.layout.list_item_mnemoic, mnemonicWords);
        rvMnemonic.setAdapter(verifyBackupMenmonicWordsAdapter);


        // 已选中单词
        FlexboxLayoutManager layoutManager2 = new FlexboxLayoutManager(this);
        layoutManager2.setFlexWrap(FlexWrap.WRAP);
        layoutManager2.setAlignItems(AlignItems.STRETCH);
        rvSelected.setLayoutManager(layoutManager2);
        List<String> selectedMnemonicWords = new ArrayList<>();
        verifyBackupSelectedMnemonicWordsAdapter = new VerifyBackupSelectedMnemonicWordsAdapter(R.layout.list_item_mnemoic_selected, selectedMnemonicWords);
        rvSelected.setAdapter(verifyBackupSelectedMnemonicWordsAdapter);
    }

    @Override
    public void configViews() {
        verifyBackupMenmonicWordsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String mnemonicWord = verifyBackupMenmonicWordsAdapter.getData().get(position).getMnemonicWord();
                if (verifyBackupMenmonicWordsAdapter.setSelection(position)) {
                    verifyBackupSelectedMnemonicWordsAdapter.addData(mnemonicWord);
                }
            }
        });
        verifyBackupSelectedMnemonicWordsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                List<VerifyMnemonicWordTag> datas = verifyBackupMenmonicWordsAdapter.getData();
                for (int i = 0; i < datas.size(); i++) {
                    LogUtils.e(TAG,"i>>>>>>:"+i);
                    if (TextUtils.equals(datas.get(i).getMnemonicWord(), verifyBackupSelectedMnemonicWordsAdapter.getData().get(position))) {
                        verifyBackupMenmonicWordsAdapter.setUnselected(i);
                        break;
                    }
                }
                verifyBackupSelectedMnemonicWordsAdapter.remove(position);
            }

        });

    }

    @OnClick(R.id.btn_confirm)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                List<String> data = verifyBackupSelectedMnemonicWordsAdapter.getData();
                int size = data.size();
                if (size == 12) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < size; i++) {
                        stringBuilder.append(data.get(i));
                        if (i != size - 1) {
                            stringBuilder.append(" ");
                        }
                    }
                    String verifyMnemonic = stringBuilder.toString();
                    String trim = verifyMnemonic.trim();
                    LogUtils.d("VerifyMnemonicBackUp", "verifyMnemonic:" + verifyMnemonic);
                    LogUtils.d("VerifyMnemonicBackUp", "trim:" + trim);
                    if (TextUtils.equals(trim, walletMnemonic)) {
                        // TODO 修改该钱包备份标识
                        SharedPreferences.Editor editor = getSharedPreferences(Conf.SETTING_SP_NAME, MODE_PRIVATE).edit();
                        editor.putString("is_backup", "true");
                        editor.apply();
                        setResult(VERIFY_SUCCESS_RESULT, new Intent());
                        Intent intent =  new Intent(mContext,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        ToastUtil.showToast(R.string.verify_backup_failed);
                    }
                } else {
                    ToastUtil.showToast(R.string.verify_backup_failed);
                }
                break;
        }
    }
}
