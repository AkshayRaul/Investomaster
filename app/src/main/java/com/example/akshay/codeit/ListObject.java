package com.example.akshay.codeit;

/**
 * Created by SHWETHA on 06-01-2017.
 */
public class ListObject {
    public String category;

    public String click;
    public int id;

    public ListObject(String category, String click) {
        this.category = category;

        this.click = click;
    }
   public String getCategory(){
       return category;
   }

    public int getId() {
        return id;
    }
}
