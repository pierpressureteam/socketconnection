package objectslibrary;

import java.io.Serializable;

/**
 *
 * @author Roy van den Heuvel
 */
public class ShipCarbonFootprint extends SocketObject implements Serializable
{

    final private double carbonFootprint;
    final private int MMSI;
    final private long time;

    public ShipCarbonFootprint(double carbonFootprint, int MMSI, long time)
    {
        this.carbonFootprint = carbonFootprint;
        this.MMSI = MMSI;
        this.time = time;
    }

    public double getCarbonFootprint()
    {
        return carbonFootprint;
    }

    public int getMMSI()
    {
        return MMSI;
    }

    public long getTime()
    {
        return time;
    }
}
