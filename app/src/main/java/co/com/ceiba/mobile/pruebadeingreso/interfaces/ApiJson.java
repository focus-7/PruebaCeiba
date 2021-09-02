package co.com.ceiba.mobile.pruebadeingreso.interfaces;


import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.entities.Post;
import co.com.ceiba.mobile.pruebadeingreso.entities.User;
import co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiJson {
    @GET(Endpoints.GET_USERS)
    Observable<List<User>> getUsers();

    @GET(Endpoints.GET_POST_USER)
    Observable<List<Post>> getPosts(@Query("userId") int userId);
}
