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
    private double speed;

    public Ship(int MMSI, long epochTime, double speed, double co2)
    {
        this.carbonFootprint = co2;
        this.MMSI = MMSI;
        this.epochTime = epochTime;
        this.speed = speed * 3.6;
        this.dateTime = convertEpochToDate(epochTime);
    }

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
    
    public double getSpeed(){
        return speed;
    }

    private Date convertEpochToDate(double epoch)
    {
        return new Date((long) epoch);
    }

    public int getMMSI()
    {
        return MMSI;
    }

    public Date getDateTime()
    {
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

    public void setCarbonFootprint(double carbonFootprint)
    {
        this.carbonFootprint = carbonFootprint;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public void setEpochTime(long epochTime)
    {
        this.epochTime = epochTime;
    }

    public void setDateTime(Date dateTime)
    {
        this.dateTime = dateTime;
    }

    public void setSpeed(double speed)
    {
        this.speed = speed;
    }

    
    
}
