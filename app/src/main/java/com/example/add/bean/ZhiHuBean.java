package com.example.add.bean;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by myself on 15/9/6.
 */
public class ZhiHuBean {
    public String date;
    public ArrayList<Stories> stories;

    public class Stories{
        public String[] images;
        public String type;
        public int id;
        public String ga_prefix;
        public String title;
        public String time;


        public String[] getImages() {
            return images;
        }

        public void setImages(String[] images) {
            this.images = images;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        @Override
        public String toString() {
            return "Stories{" +
                    "images=" + Arrays.toString(images) +
                    ", type='" + type + '\'' +
                    ", id=" + id +
                    ", ga_prefix='" + ga_prefix + '\'' +
                    ", title='" + title + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ZhiHuBean{" +
                "date='" + date + '\'' +
                ", stories=" + stories.toString() +
                '}';
    }
}
