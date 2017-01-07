package com.example.akshay.codeit;

/**
 * Created by SHWETHA on 06-01-2017.
 */
public class ListObject {
    public String category;

    public int image;
    public int id;

    public ListObject(String category, int image) {
        this.category = category;

        this.image = image;
    }
   public String getCategory(){
       return category;
   }

    public int getId() {
        return id;
    }
}
