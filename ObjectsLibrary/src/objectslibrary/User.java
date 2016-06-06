package objectslibrary;

import java.io.Serializable;

/**
 *
 * @author Roy van den Heuvel
 */
public class User extends SocketObject implements Serializable
{

    final private String username;
    private String password = "";
    private String email = "";
    private int MMSI = 0;
    private String shipType = "";

    public User(String uname, String pword)
    {
        this.username = uname;
        this.password = pword;
    }

    public User(String uname)
    {
        this.username = uname;
    }

    public User(String uname, String pword, String email, int MMSI, String shipType)
    {
        this.email = email;
        this.password = pword;
        this.username = uname;
        this.MMSI = MMSI;
        this.shipType = shipType;
    }

    @Override
    public String toString()
    {
        String user = "";

        user += "Username: " + username;
        user += " Password: " + password;
        user += " Email: " + email;
        user += " MMSI: " + MMSI;
        user += " Ship type: " + shipType;

        return user;
    }

    public String getShipType()
    {
        return shipType;
    }

    public String getEmail()
    {
        return email;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public int getMMSI()
    {
        return MMSI;
    }

}
