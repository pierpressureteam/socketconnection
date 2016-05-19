/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objectslibrary;

/**
 *
 * @author Roy van den Heuvel
 */
public class ShipCarbonFootprint {
    private double carbonFootprint;
    private int MMSI;
    private long time;
    
    public ShipCarbonFootprint(){
        
    }

    public double getCarbonFootprint() {
        return carbonFootprint;
    }

    public void setCarbonFootprint(double CarbonFootprint) {
        this.carbonFootprint = CarbonFootprint;
    }

    public int getMMSI() {
        return MMSI;
    }

    public void setMMSI(int MMSI) {
        this.MMSI = MMSI;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
    
    
}
