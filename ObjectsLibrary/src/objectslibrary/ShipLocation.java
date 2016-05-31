package objectslibrary;

import java.io.Serializable;

/**
 *
 * @author Roy van den Heuvel
 */
public class ShipLocation extends SocketObject implements Serializable
{

    final private int MMSI;
    final private double latitude;
    final private double longitude;
    final private long time;

    public ShipLocation(int MMSI, double latitude, double longitude, long time)
    {
        this.MMSI = MMSI;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
    }

    public int getMMSI()
    {
        return MMSI;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public long getTime()
    {
        return time;
    }

}
