package objectslibrary;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Roy van den Heuvel
 */
public class Ship extends SocketObject implements Serializable
{

    final private int MMSI;
    private double carbonFootprint;
    private double latitude;
    private double longitude;
    private long epochTime;
    private Date dateTime;

    public Ship(int MMSI)
    {
        this.MMSI = MMSI;
    }

    public Ship(int MMSI, double latitude, double longitude, long time, double carbonFootprint)
    {
        this.carbonFootprint = carbonFootprint;
        this.MMSI = MMSI;
        this.latitude = latitude;
        this.longitude = longitude;
        this.epochTime = time;
        this.dateTime = convertEpochToDate(time);
    }

    private Date convertEpochToDate(double epoch)
    {
        return new Date((long) epoch);
    }

    public int getMMSI()
    {
        return MMSI;
    }
    
    public Date getDateTime(){
        return dateTime;
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
        return epochTime;
    }

    public double carbonFootprint()
    {
        return carbonFootprint;
    }

}
