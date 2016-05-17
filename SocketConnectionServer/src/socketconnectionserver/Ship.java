/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketconnectionserver;

import java.io.Serializable;

/**
 *
 * @author Roy van den Heuvel
 */
public class Ship implements Serializable {
    static final long serialVersionUID = 42L;
    double MMSI;
    String type;

    public double getMMSI() {
        return MMSI;
    }

    public void setMMSI(double MMSI) {
        this.MMSI = MMSI;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
    public Ship(double MMSI, String type){
        this.MMSI = MMSI;
        this.type = type;
    }
}