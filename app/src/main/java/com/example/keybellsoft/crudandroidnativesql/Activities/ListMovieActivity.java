package com.example.keybellsoft.crudandroidnativesql.Activities;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.keybellsoft.crudandroidnativesql.Controllers.MovieController;
import com.example.keybellsoft.crudandroidnativesql.DataBaseHelper;
import com.example.keybellsoft.crudandroidnativesql.R;

import java.util.ArrayList;


/**
 * Created by Fabian Rosales Esquivel on 10/11/2015.
 * Please contact me at www.frosquivel.com
 * fabian7593@gmail.com
 */

public class ListMovieActivity extends AppCompatActivity {

    //global variables
    private static DataBaseHelper dbHelper;
    private static MovieController movieC;
    private static ListView movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_movie);

        //instance variables
        movieList = (ListView) findViewById(R.id.movieList);
        dbHelper = new DataBaseHelper(this);
        movieC = new MovieController();

        //click in item of list view
        movieList.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {

                        //obtain an Id to selected
                        int realId = obtainSelectedId(position);

                        //change the activity, and send parameters, true if is update
                        //and false if isn't
                        Intent i = new Intent (ListMovieActivity.this, MovieActivity.class);
                        i.putExtra("isEdit", true);
                        i.putExtra("realId", realId);
                        startActivity(i);
                    }
                }
        );

        //if the user press long in a item selected, the system delete this row
        movieList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                //obtain an Id to selected
                int realId = obtainSelectedId(pos);

                //delete the item selected
                dbHelper.executeSql(movieC.delete(realId));

                //refresh the listview
                refreshListView();
                return true;
            }
        });
    }

    /**
     * This method refresh list view
     */
    private void refreshListView(){
        ArrayList<String> movieResults = new ArrayList<String>();

        //obtain the cursor of get all
        Cursor getMovies = dbHelper.rawQuery(movieC.getAll());

        if (getMovies != null ) {
            //Move cursor to first row
            if  (getMovies.moveToFirst()) {
                do {
                    //Get version from Cursor
                    String firstName = getMovies.getString(getMovies.getColumnIndex(movieC.getcMoviename()));
                    //Add the version to Arraylist 'results'
                    movieResults.add(firstName);
                }while (getMovies.moveToNext()); //Move to next row
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, movieResults);
        movieList.setAdapter(adapter);
    }


    /**
     * Obtain the id of selected item
     * if position is equals to listViewPosition
     * obtain the respective Id
     * @param position the int position of list view
     * @return id of real id
     */
    private int obtainSelectedId(int position){
        int toStop=0;
        int returnId=0;
        Cursor getMovies = dbHelper.rawQuery(movieC.getAll());
        if (getMovies != null ) {
            //Move cursor to first row
            if  (getMovies.moveToFirst()) {
                do {
                    if(position==toStop) {
                        //Get version from Cursor
                         returnId = getMovies.getInt(getMovies.getColumnIndex(movieC.getcIdMovie()));
                    }
                    toStop++;
                }while (getMovies.moveToNext()); //Move to next row
            }
        }
        return returnId;
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_movie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.gotocreatemovie) {
            Intent intent = new Intent(this,MovieActivity.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.gotocreatecategory) {
            Intent intent = new Intent(this,CategoryActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
