package cn.part.wallet.service.response;

public class ETHGas {

    private double fast;
    private double fastest;
    private double safeLow;
    private double average;
    private double block_time;
    private int blockNum;
    private double speed;
    private double safeLowWait;
    private double avgWait;
    private double fastWait;
    private double fastestWait;

    public double getFast() {
        return fast;
    }

    public void setFast(double fast) {
        this.fast = fast;
    }

    public double getFastest() {
        return fastest;
    }

    public void setFastest(double fastest) {
        this.fastest = fastest;
    }

    public double getSafeLow() {
        return safeLow;
    }

    public void setSafeLow(double safeLow) {
        this.safeLow = safeLow;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public double getBlock_time() {
        return block_time;
    }

    public void setBlock_time(double block_time) {
        this.block_time = block_time;
    }

    public int getBlockNum() {
        return blockNum;
    }

    public void setBlockNum(int blockNum) {
        this.blockNum = blockNum;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSafeLowWait() {
        return safeLowWait;
    }

    public void setSafeLowWait(double safeLowWait) {
        this.safeLowWait = safeLowWait;
    }

    public double getAvgWait() {
        return avgWait;
    }

    public void setAvgWait(double avgWait) {
        this.avgWait = avgWait;
    }

    public double getFastWait() {
        return fastWait;
    }

    public void setFastWait(double fastWait) {
        this.fastWait = fastWait;
    }

    public double getFastestWait() {
        return fastestWait;
    }

    public void setFastestWait(double fastestWait) {
        this.fastestWait = fastestWait;
    }
}
