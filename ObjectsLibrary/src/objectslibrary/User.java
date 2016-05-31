package objectslibrary;

import java.io.Serializable;

/**
 *
 * @author Roy van den Heuvel
 */
public class User extends SocketObject implements Serializable
{
    final private String username;
    final private String password;
    final private String email;

    public User(String uname, String pword, String email)
    {
        this.email = email;
        this.password = pword;
        this.username = uname;
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

}
