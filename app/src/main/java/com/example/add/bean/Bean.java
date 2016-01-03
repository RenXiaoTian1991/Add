package com.example.add.bean;

/**
 * Created by myself on 15/8/17.
 */
public class Bean {
    private String title;
    private boolean checked;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getTitle() {

        return title;
    }



    public Bean(String title, boolean checked) {

        this.title = title;
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }
}
