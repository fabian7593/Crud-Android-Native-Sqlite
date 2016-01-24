package com.example.keybellsoft.crudandroidnativesql.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.keybellsoft.crudandroidnativesql.Controllers.CategoryController;
import com.example.keybellsoft.crudandroidnativesql.DataBaseHelper;
import com.example.keybellsoft.crudandroidnativesql.R;

/**
 * Created by Fabian Rosales Esquivel on 10/11/2015.
 * Please contact me at www.frosquivel.com
 * fabian7593@gmail.com
 */
public class CategoryActivity extends AppCompatActivity {

    //global variables
    EditText txtCategoryName;
    EditText txtCode;
    Button btnSaveCategory;

    private static boolean isUpdate;
    private static int realId;

    DataBaseHelper dbHelper;
    CategoryController categoryC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_category);

        //obtain the param of Intent of last activity
        isUpdate = getIntent().getBooleanExtra("isEdit", false);
        realId = getIntent().getIntExtra("realId", 0);

        //create the classes instances
        dbHelper = new DataBaseHelper(this);
        categoryC = new CategoryController();

        //initialized the ui components
        setUIComponents();

        //if is update charge the data, in the form
        if(isUpdate)
        {
            Cursor getCategories = dbHelper.rawQuery(categoryC.getById(realId));

            if (getCategories != null ) {
                //Move cursor to first row
                if  (getCategories.moveToFirst()) {
                    do {
                        //Get version from Cursor
                        txtCategoryName.setText(
                                getCategories.getString(
                                        getCategories.getColumnIndex(
                                                categoryC.getcCategoryname())));

                        txtCode.setText(
                                getCategories.getString(
                                        getCategories.getColumnIndex(
                                                categoryC.getcCode())));

                    }while (getCategories.moveToNext()); //Move to next row
                }
            }
        }
    }

    /**
     * Set the Gui components of layout
     */
    private void setUIComponents(){
        txtCategoryName = (EditText)findViewById(R.id.txtCategoryName);
        txtCode = (EditText)findViewById(R.id.txtCode);
        btnSaveCategory = (Button)findViewById(R.id.btnSaveCategory);

        //set of button click
        btnSaveCategory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //if is create
                if(!isUpdate) {
                    dbHelper.executeSql(categoryC.insert(
                            txtCategoryName.getText().toString(),
                            Integer.parseInt(txtCode.getText().toString())));
                }//if is update
                else
                {
                    dbHelper.executeSql(categoryC.update(
                            txtCategoryName.getText().toString(),
                            Integer.parseInt(txtCode.getText().toString()), realId));
                }

                //go to the list activity
                Intent i = new Intent (CategoryActivity.this, ListMovieActivity.class);
                startActivity(i);
            }
        });

    }
}
