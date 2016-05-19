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
public class ShipLocation {
    private int MMSI;
    private double latitude;
    private double longitude;
    private long time;
    
    public ShipLocation(){
        
    }

    public int getMMSI() {
        return MMSI;
    }

    public void setMMSI(int MMSI) {
        this.MMSI = MMSI;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
    
    
    
}
