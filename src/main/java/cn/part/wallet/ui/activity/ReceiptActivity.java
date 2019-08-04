package cn.part.wallet.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import org.consenlabs.tokencore.wallet.Wallet;
import org.consenlabs.tokencore.wallet.WalletManager;
import org.consenlabs.tokencore.wallet.model.ChainType;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import cn.part.wallet.R;
import cn.part.wallet.base.BaseActivity;
import cn.part.wallet.utils.MyThreadPool;
import cn.part.wallet.utils.ToastUtil;
import cn.part.wallet.view.CopyWalletPopWindow;

public class ReceiptActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_wallet_name)
    TextView tvWalletName;
    @BindView(R.id.tv_wallet_addr)
    TextView tvWalletAddr;
    @BindView(R.id.img_qr)
    ImageView qrImg;
    private Wallet wallet;
    private CopyWalletPopWindow popWindow;
    private String codeAddr;
    private String copyAddr;

    @Override
    protected void onBeforeSetContentLayout() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_receipt;
    }

    @Override
    public void initToolBar() {
        tvTitle.setText(R.string.receipt);
        mCommonToolbar.setBackground(getResources().getDrawable(R.color.transparent));
    }

    @Override
    public void initDatas() {
        Intent intent = getIntent();
        String wallegtId = intent.getStringExtra("wallet_id");
        wallet = WalletManager.mustFindWalletById(wallegtId);
    }

    @Override
    public void configViews() {
        tvWalletName.setText(wallet.getMetadata().getName());
        copyAddr = codeAddr = wallet.getAddress();
        if (wallet.getMetadata().getChainType().equals(ChainType.ETHEREUM)){
            codeAddr = "ethereum:"+"0x" + codeAddr;
            copyAddr = "0x" + copyAddr;
        }else if (wallet.getMetadata().getChainType().equals(ChainType.BITCOIN)) {
            codeAddr = "bitcoin:"+ codeAddr;
        }
        tvWalletAddr.setText(copyAddr);
        initQrCode();
    }

    private void initQrCode() {
        MyThreadPool.execute(()->{
            Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode(codeAddr, BGAQRCodeUtil.dp2px(ReceiptActivity.this, 150));
            MyThreadPool.runOnUiThread(()->{
                Glide.with(ReceiptActivity.this).load(bitmap).fitCenter().into(qrImg);
            });
        });
    }

    @OnClick({R.id.tv_wallet_addr})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_wallet_addr:
//                if (popWindow==null){
//                    popWindow = new CopyWalletPopWindow(mContext);
//                }
//                if (!popWindow.isShowing()){
//                    popWindow.showAsDropDown(tvWalletAddr);
//                }
                copyAddress();
                break;
        }
    }

    private void copyAddress() {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        if (cm != null) {
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", copyAddr);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
        }
        ToastUtil.showToast(R.string.copy_wallet);
    }
}
