package com.ibrahim.rozoshab.Bean;

import java.util.ArrayList;

/**
 * Created by w-t- on 18-Oct-17.
 */

public class CategoryDataBean {

    private TaskBean taskBean;
    private  CategoryBean categoryBean;

    public CategoryDataBean(TaskBean taskBean, CategoryBean categoryBean) {
        this.taskBean = taskBean;
        this.categoryBean = categoryBean;
    }

    public TaskBean getTaskBean() {
        return taskBean;
    }

    public void setTaskBean(TaskBean taskBean) {
        this.taskBean = taskBean;
    }

    public CategoryBean getCategoryBean() {
        return categoryBean;
    }

    public void setCategoryBean(CategoryBean categoryBean) {
        this.categoryBean = categoryBean;
    }
}
