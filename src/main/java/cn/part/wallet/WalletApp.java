package cn.part.wallet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDexApplication;

import com.squareup.okhttp.OkHttpClient;

import org.consenlabs.tokencore.wallet.KeystoreStorage;
import org.consenlabs.tokencore.wallet.WalletManager;
import org.litepal.LitePal;

import java.io.File;

public class WalletApp extends MultiDexApplication  implements KeystoreStorage {

    private static WalletApp sInstance;


    private static OkHttpClient httpClient;


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        init();

    }


    public static WalletApp getsInstance() {
        return sInstance;
    }

    protected void init() {
        WalletManager.storage = this;
        WalletManager.scanWallets();
        LitePal.initialize(this);
    }


    public static OkHttpClient okHttpClient() {
        return httpClient;
    }


    @Override
    public File getKeystoreDir() {
        return this.getFilesDir();
    }
}
