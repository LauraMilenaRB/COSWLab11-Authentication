package cosw.eci.edu.authentication.network;

import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cosw.eci.edu.authentication.model.LoginWrapper;
import cosw.eci.edu.authentication.model.ToDo;
import cosw.eci.edu.authentication.model.Token;
import cosw.eci.edu.authentication.services.NetworkService;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LauraRB on 28/04/2018.
 */

public class RetrofitNetwork
        implements Network
{

    private static final String BASE_URL = "http://10.0.2.2:8080/";

    private NetworkService networkService;

    private ExecutorService backgroundExecutor = Executors.newFixedThreadPool( 1 );

    public RetrofitNetwork() {
        Retrofit retrofit =
                new Retrofit.Builder().baseUrl( BASE_URL ).addConverterFactory( GsonConverterFactory.create() ).build();
        networkService = retrofit.create( NetworkService.class );
    }

    @Override
    public void login( final LoginWrapper loginWrapper, final RequestCallback<Token> requestCallback )    {
        backgroundExecutor.execute( new Runnable()        {
            @Override
            public void run()            {
                Call<Token> call = networkService.login( loginWrapper );
                try                {
                    Response<Token> execute = call.execute();
                    requestCallback.onSuccess( execute.body() );
                }
                catch ( IOException e )                {
                    requestCallback.onFailed( new NetworkException(null, e ) );
                }
            }
        } );

    }

    @Override
    public void getTodos(final RequestCallback<List<ToDo>> requestCallback, String token) {
        addSecureTokenInterceptor(token);
        backgroundExecutor.execute( new Runnable()        {
            @Override
            public void run()            {
                Call<List<ToDo>> call = networkService.getTodos();
                try                {
                    Response<List<ToDo>> execute = call.execute();
                    requestCallback.onSuccess(execute.body());
                }
                catch ( IOException e )                {
                    requestCallback.onFailed( new NetworkException(null, e ) );
                }
            }
        } );
    }

    @Override
    public void createTodo(final ToDo todo, final RequestCallback<ResponseBody> requestCallback, String token) {
        addSecureTokenInterceptor(token);
        backgroundExecutor.execute( new Runnable()        {
            @Override
            public void run()            {
                Call<ResponseBody> call = networkService.createTodo(todo);
                try                {
                    Response<ResponseBody> execute = call.execute();
                    requestCallback.onSuccess( execute.body() );
                }
                catch ( IOException e )                {
                    requestCallback.onFailed( new NetworkException(null, e ) );
                }
            }
        } );
    }


    public void addSecureTokenInterceptor( final String token )    {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor( new Interceptor(){
            @Override
            public okhttp3.Response intercept( Chain chain )throws IOException{
                Request original = chain.request();
                Request request = original.newBuilder().header( "Accept", "application/json" ).header( "Authorization","Bearer "
                                + token ).method(
                        original.method(), original.body() ).build();
                return chain.proceed( request );
            }
        } );
        Retrofit retrofit =
                new Retrofit.Builder().baseUrl( BASE_URL ).addConverterFactory( GsonConverterFactory.create() ).client(
                        httpClient.build() ).build();
        networkService = retrofit.create( NetworkService.class );
    }

}