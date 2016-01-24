package com.example.keybellsoft.crudandroidnativesql.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.keybellsoft.crudandroidnativesql.Controllers.CategoryController;
import com.example.keybellsoft.crudandroidnativesql.Controllers.MovieController;
import com.example.keybellsoft.crudandroidnativesql.DataBaseHelper;
import com.example.keybellsoft.crudandroidnativesql.R;

import java.util.ArrayList;

/**
 * Created by Fabian Rosales Esquivel on 10/11/2015.
 * Please contact me at www.frosquivel.com
 * fabian7593@gmail.com
 */
public class MovieActivity extends AppCompatActivity {

    //global variables
    EditText txtMovieName;
    EditText txtQuantity;
    Spinner spinnerCategories;
    Button btnSaveMovie;

    private static boolean isUpdate;
    private static int realId;
    private static int realCategoryId=0;

    DataBaseHelper dbHelper;
    MovieController movieC;
    CategoryController categoryC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_movie);

        //obtain the param of Intent of last activity
        isUpdate = getIntent().getBooleanExtra("isEdit", false);
        realId = getIntent().getIntExtra("realId", 0);

        //create the classes instances
        dbHelper = new DataBaseHelper(this);
        movieC = new MovieController();
        categoryC =  new CategoryController();

        //initialized the ui components
        setUIComponents();
        addItemsOnSpinnerCategories();


        //if is update charge the data, in the form
        if(isUpdate)
        {
            Cursor getMovies = dbHelper.rawQuery(movieC.getById(realId));

            if (getMovies != null ) {
                //Move cursor to first row
                if  (getMovies.moveToFirst()) {
                    do {
                        //Get version from Cursor
                        txtMovieName.setText(
                                getMovies.getString(
                                        getMovies.getColumnIndex(
                                                movieC.getcMoviename())));

                        txtQuantity.setText(
                                getMovies.getString(
                                        getMovies.getColumnIndex(
                                                movieC.getcQuantity())));

                        //TODO spinner to update
                        spinnerCategories.setSelection(
                                getMovies.getInt(
                                        getMovies.getColumnIndex(
                                            movieC.getcIdMovieCategory()))-1);

                    }while (getMovies.moveToNext()); //Move to next row
                }
            }
        }



    }

    /**
     * Set the Gui components of layout
     */
    private void setUIComponents(){
        txtMovieName = (EditText)findViewById(R.id.txtMovieName);
        txtQuantity = (EditText)findViewById(R.id.txtQuantity);
        btnSaveMovie = (Button)findViewById(R.id.btnSaveMovie);
        spinnerCategories = (Spinner)findViewById(R.id.spinnerCategories);

        //set of button click
        btnSaveMovie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //if is create
                if (!isUpdate) {
                    dbHelper.executeSql(movieC.insert(
                            txtMovieName.getText().toString(),
                            Integer.parseInt(txtQuantity.getText().toString()), realCategoryId));
                }//if is update
                else {
                    dbHelper.executeSql(movieC.update(
                            txtMovieName.getText().toString(),
                            Integer.parseInt(txtQuantity.getText().toString()), realCategoryId, realId));
                }

                //go to the list activity
                Intent i = new Intent(MovieActivity.this, ListMovieActivity.class);
                startActivity(i);
            }
        });



        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                       long arg3) {
                realCategoryId =  obtainCategorySelectedId(arg2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //optionally do something here
            }
        });
    }

    /**
     * Obtain the id of selected item
     * if position is equals to listViewPosition
     * obtain the respective Id
     * @param position the int position of list view
     * @return id of real id
     */
    private int obtainCategorySelectedId(int position){
        int toStop=0;
        int returnId=0;
        Cursor getCategories = dbHelper.rawQuery(categoryC.getAll());
        if (getCategories != null ) {
            //Move cursor to first row
            if  (getCategories.moveToFirst()) {
                do {
                    if(position==toStop) {
                        //Get version from Cursor
                        returnId = getCategories.getInt(getCategories.getColumnIndex(categoryC.getcIdCategory()));
                    }
                    toStop++;
                }while (getCategories.moveToNext()); //Move to next row
            }
        }
        return returnId;
    }


    /**
     * add items into spinner dynamically
     */
    private void addItemsOnSpinnerCategories(){
        ArrayList<String> categoryResults = new ArrayList<String>();

        //obtain the cursor of get all
        Cursor getCategories = dbHelper.rawQuery(categoryC.getAll());

        if (getCategories != null ) {
            //Move cursor to first row
            if  (getCategories.moveToFirst()) {
                do {
                    //Get version from Cursor
                    String firstName = getCategories.getString(getCategories.getColumnIndex(categoryC.getcCategoryname()));
                    //Add the version to Arraylist 'results'
                    categoryResults.add(firstName);
                }while (getCategories.moveToNext()); //Move to next row
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, categoryResults);
        spinnerCategories.setAdapter(adapter);
    }
}
