package objectslibrary;

import java.io.Serializable;

/**
 *
 * @author Roy van den Heuvel
 */
public class User extends SocketObject implements Serializable
{

    private String username;
    private String password;
    private String email;

    public User()
    {

    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

}
