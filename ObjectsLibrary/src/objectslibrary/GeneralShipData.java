/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objectslibrary;

import java.io.Serializable;

/**
 *
 * @author Roy van den Heuvel
 */
public class GeneralShipData extends SocketObject implements Serializable
{

    private double lowest;
    private double highest;
    private double average;

    public GeneralShipData()
    {

    }

    public double getLowest()
    {
        return lowest;
    }

    public void setLowest(double lowest)
    {
        this.lowest = lowest;
    }

    public double getHighest()
    {
        return highest;
    }

    public void setHighest(double highest)
    {
        this.highest = highest;
    }

    public double getAverage()
    {
        return average;
    }

    public void setAverage(double average)
    {
        this.average = average;
    }

}
