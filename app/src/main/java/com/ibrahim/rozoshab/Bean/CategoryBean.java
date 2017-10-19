package com.ibrahim.rozoshab.Bean;

import java.util.ArrayList;

/**
 * Created by w-t- on 17-Sep-17.
 */

public class CategoryBean {

    private String categoryId;
    private String categoryName;
    private String categoryStatus;


    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryStatus() {
        return categoryStatus;
    }

    public void setCategoryStatus(String categoryStatus) {
        this.categoryStatus = categoryStatus;
    }

    public CategoryBean(String categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
