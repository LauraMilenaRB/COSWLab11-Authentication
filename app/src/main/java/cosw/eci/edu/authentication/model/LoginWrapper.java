package cosw.eci.edu.authentication.model;

/**
 * Created by LauraRB on 28/04/2018.
 */

public class LoginWrapper {
    private final String username;

    private final String password;

    public LoginWrapper( String username, String password )    {
        this.username = username;
        this.password = password;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }
}
