package deshaw.dae.descrypto.domain;

public class Summary24h {

    public Summary24h(double volume, double highPrice, double lowPrice, double absoluteChange, double percentageChange) {
        Volume = volume;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.absoluteChange = absoluteChange;
        this.percentageChange = percentageChange;
    }



    public double getVolume() {
        return Volume;
    }

    public void setVolume(double volume) {
        Volume = volume;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public double getAbsoluteChange() {
        return absoluteChange;
    }

    public void setAbsoluteChange(double absoluteChange) {
        this.absoluteChange = absoluteChange;
    }

    public double getPercentageChange() {
        return percentageChange;
    }

    public void setPercentageChange(double percentageChange) {
        this.percentageChange = percentageChange;
    }

    private double Volume;
    private double highPrice;
    private double lowPrice;
    private double absoluteChange;
    private double percentageChange;



}
