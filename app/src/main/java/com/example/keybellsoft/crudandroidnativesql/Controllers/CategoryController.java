package com.example.keybellsoft.crudandroidnativesql.Controllers;

/**
 * Created by Fabian Rosales Esquivel on 10/11/2015.
 * Please contact me at www.frosquivel.com
 * fabian7593@gmail.com
 */
public class CategoryController {

    // Create the variables statics and names of entity
    public static final String C_TABLE_NAME = "CATEGORY";
    public static final String C_ID_CATEGORY = "idCategory";
    public static final String C_CATEGORYNAME = "categoryName";
    public static final String C_CODE = "code";

    //the getters and setters
    public String getcTableName() {
        return C_TABLE_NAME;
    }

    public String getcIdCategory() {
        return C_ID_CATEGORY;
    }

    public String getcCategoryname() {
        return C_CATEGORYNAME;
    }

    public String getcCode() {
        return C_CODE;
    }

    /**
     * Insert a category
     *
     * @param categoryName the category name
     * @param code         the code
     * @return a string to execute query
     */
    public String insert(String categoryName, int code) {
        return "INSERT INTO " + C_TABLE_NAME + "(" + C_CATEGORYNAME + " , " + C_CODE + ") " +
                "VALUES ('" + categoryName + "'," + code + ");";
    }

    /**
     * Delete a category
     *
     * @param idCategory set id category to delete
     * @return a string to execute query
     */
    public String delete(int idCategory) {
        return "DELETE FROM " + C_TABLE_NAME + " WHERE " + C_ID_CATEGORY + " = " + idCategory + ";";
    }

    /**
     * This method update the category
     *
     * @param categoryName the category name
     * @param code         the code
     * @param idCategory   set id category to update
     * @return a string to execute query
     */
    public String update(String categoryName, int code, int idCategory) {
        return "UPDATE " + C_TABLE_NAME +
                " SET " + C_CATEGORYNAME + " = '" + categoryName + "' , " +
                C_CODE + " = " + code +
                " WHERE " + C_ID_CATEGORY + " = " + idCategory + ";";
    }

    /**
     * Get all categories
     *
     * @return a string to raw query
     */
    public String getAll() {
        return "SELECT * FROM " + C_TABLE_NAME;
    }

    /**
     * Get the category by Id
     *
     * @param idCategory set id category to update
     * @return a string to raw query
     */
    public String getById(int idCategory) {
        return "SELECT * FROM " + C_TABLE_NAME + " WHERE " + C_ID_CATEGORY + " = " + idCategory;
    }
}
