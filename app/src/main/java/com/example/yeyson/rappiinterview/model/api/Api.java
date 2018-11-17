package com.example.yeyson.rappiinterview.model.api;

import com.example.yeyson.rappiinterview.entity.popular.PopularRes;
import com.example.yeyson.rappiinterview.entity.search.MoviesRes;
import com.example.yeyson.rappiinterview.entity.toprated.TopRatedRes;
import com.example.yeyson.rappiinterview.entity.upcoming.UpcomingRes;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface Api {
    // Popular Movies
    @GET("movie/popular")
    Observable<PopularRes> getPopularMovies(@Query("api_key") String apiKey,
                                            @Query("language") String language);

    // Top rated Movies
    @GET("movie/top_rated")
    Observable<TopRatedRes> getTopRatedMovies(@Query("api_key") String apiKey,
                                              @Query("language") String language);

    // Upcoming movies
    @GET("movie/upcoming")
    public Observable<UpcomingRes> getUpcomingMovies(@Query("api_key") String apiKey,
                                                     @Query("language") String language);

    @GET("search/movie")
    Observable<MoviesRes> searchMovie(@Query("api_key") String apiKey,
                                      @Query("language") String language, @Query("query") String query);
}
