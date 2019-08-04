package cn.part.wallet.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Convert {
    public static BigInteger hexStrToBigI(String hex) {
      return  new BigInteger(hex.substring(2),16);
    }

    /**
     * 预估矿工费 单位eth
     * @param gasPrice 单位gwei
     * @param gasLimit
     * @return
     */
    public static BigDecimal calGas(BigDecimal gasPrice,BigDecimal gasLimit) {
        BigDecimal eth = gasPrice.multiply(gasLimit).multiply(new BigDecimal("0.000000001"));
        return eth.setScale(8, RoundingMode.CEILING);
    }

    /**
     * 获取值转换为gwei单位值
     * @param v
     * @return
     */
    public static BigDecimal valueToGwei(double v) {
        BigDecimal gPrice = BigDecimal.valueOf(v).multiply(new BigDecimal("0.1"));
        return gPrice.setScale(2,BigDecimal.ROUND_CEILING);
    }

    public static String formateDate(String timestamp){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(Long.valueOf(timestamp+"000")));

    }

    /**
     * wei 转换为ether
     * @param wei
     * @return
     */
    public static BigDecimal weiToEther(String wei) {
        BigDecimal ether = new BigDecimal(wei).divide(BigDecimal.valueOf(10).pow(18)).setScale(8,BigDecimal.ROUND_CEILING);
        if (ether.compareTo(new BigDecimal("0"))==0)
            ether = new BigDecimal("0");
        return ether;
    }

    /**
     * gwei 转换为 ether
     * @param gwei
     * @return
     */
    public static BigDecimal gweiToEther(String gwei) {
        BigDecimal ether = new BigDecimal(gwei).divide(BigDecimal.valueOf(10).pow(9)).setScale(8,BigDecimal.ROUND_CEILING);
        return ether;
    }

    /**
     * ether 转化为wei
     * @param ether
     * @return
     */
    public static BigInteger etherToWei(String ether) {
        BigInteger wei = BigInteger.valueOf(new BigDecimal(ether).multiply(BigDecimal.valueOf(10).pow(18)).longValue());
        return wei;
    }

    /**
     * gwei 转换为ether
     * @param ether
     * @return
     */
    public static BigInteger etherToGWei(BigDecimal ether) {
        BigInteger wei = BigInteger.valueOf(ether.multiply(BigDecimal.valueOf(10).pow(9)).longValue());
        return wei;
    }

    public static BigInteger gweiToWei(BigDecimal gwei) {
       return gwei.multiply(new BigDecimal(10).pow(9)).toBigInteger();
    }

    //聪换算成btc
    public static BigDecimal satoshiToBTC(long sato) {
        return BigDecimal.valueOf(sato).multiply(BigDecimal.valueOf(0.00000001)).setScale(8,BigDecimal.ROUND_CEILING);
    }

    //btc换算成聪
    public static long BTCToSatoshi(BigDecimal btc) {
        return btc.multiply(BigDecimal.valueOf(100000000)).longValue();
    }

    public static String getTokenTxData(String signature,String address,BigInteger value) {
        address = address.startsWith("0x") ? address.substring(2) : address;
        return "0x" + signature + addZeroForNum(address,64) +addZeroForNum(value.toString(16),64);
    }

    /**
     * 补0成64位
     * @return
     */
    public static String addZeroForNum(String str,int strLength) {
        int strLen =str.length();
        if (strLen <strLength) {
            while (strLen< strLength) {
                StringBuffer sb = new StringBuffer();
                sb.append("0").append(str);
                str= sb.toString();
                strLen= str.length();
            }
        }

        return str;
    }
}
