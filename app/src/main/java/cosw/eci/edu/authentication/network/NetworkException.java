package cosw.eci.edu.authentication.network;

/**
 * Created by LauraRB on 28/04/2018.
 */

public class NetworkException extends Exception {

    public NetworkException(Object o, Exception e){
        e.printStackTrace();
    }
}
