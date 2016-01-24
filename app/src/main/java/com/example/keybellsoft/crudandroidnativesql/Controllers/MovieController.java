package com.example.keybellsoft.crudandroidnativesql.Controllers;

/**
 * Created by Fabian Rosales Esquivel on 10/11/2015.
 * Please contact me at www.frosquivel.com
 * fabian7593@gmail.com
 */
public class MovieController {

    // Create the variables statics and names of entity
    public static final String C_TABLE_NAME = "MOVIE";
    public static final String C_ID_MOVIE = "idMovie";
    public static final String C_MOVIENAME = "movieName";
    public static final String C_QUANTITY = "quantity";
    public static final String C_ID_MOVIE_CATEGORY = "idMovieCategory";

    //the getters and setters
    public String getcTableName() {
        return C_TABLE_NAME;
    }

    public String getcIdMovie() {
        return C_ID_MOVIE;
    }

    public String getcMoviename() {
        return C_MOVIENAME;
    }

    public String getcQuantity() {
        return C_QUANTITY;
    }

    public String getcIdMovieCategory() {
        return C_ID_MOVIE_CATEGORY;
    }


    /**
     * Insert a movie
     * @param movieName the movie name
     * @param quantity the quantity of stock
     * @param idMovieCategory the fk of id category
     * @return a string to execute query
     */
    public String insert(String movieName, int quantity, int idMovieCategory) {
        return "INSERT INTO " + C_TABLE_NAME + "(" + C_MOVIENAME + " , " + C_QUANTITY + " , " + C_ID_MOVIE_CATEGORY + ") " +
                "VALUES ('" + movieName + "'," + quantity + "," + idMovieCategory + ");";
    }

    /**
     * Delete a movie
     * @param idMovie the id of the movie to delete this
     * @return a string to execute query
     */
    public String delete(int idMovie) {
        return "DELETE FROM " + C_TABLE_NAME + " WHERE " + C_ID_MOVIE + " = " + idMovie + ";";
    }

    /**
     * Update a movie
     * @param movieName the name of the movie
     * @param quantity the quantity of stock
     * @param idMovieCategory the fk of id category
     * @param idMovie  the id of the movie to update this
     * @return a string to execute query
     */
    public String update(String movieName, int quantity, int idMovieCategory, int idMovie) {
        return "UPDATE " + C_TABLE_NAME +
                " SET " + C_MOVIENAME + " = '" + movieName + "' , " +
                C_QUANTITY + " = " + quantity + " , " +
                C_ID_MOVIE_CATEGORY + " = " + idMovieCategory +
                " WHERE "+ C_ID_MOVIE + " = " + idMovie + ";";
    }

    /**
     * Get all Movies
     *
     * @return a string to raw query
     */
    public String getAll() {
        return "SELECT * FROM " + C_TABLE_NAME;
    }

    /**
     * Get movie by Id
     * @param idMovie set id movie to update
     * @return a string to raw query
     */
    public String getById(int idMovie) {
        return "SELECT * FROM " + C_TABLE_NAME + " WHERE " + C_ID_MOVIE + " = " + idMovie;
    }


}
