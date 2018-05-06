package cosw.eci.edu.authentication.network;

/**
 * Created by LauraRB on 28/04/2018.
 */

public interface RequestCallback<T> {
    void onSuccess( T response );

    void onFailed( NetworkException e );
}
