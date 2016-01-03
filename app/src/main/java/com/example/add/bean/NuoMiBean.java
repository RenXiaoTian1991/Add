package com.example.add.bean;

import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;

import java.util.List;

/**
 * Created by myself on 15/8/21.
 */
public class NuoMiBean {
    public int errno;
    public String msg;
    public List<Deals> deals;

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Deals> getDeals() {
        return deals;
    }

    public void setDeals(List<Deals> deals) {
        this.deals = deals;
    }

    public class Deals{
        public String deal_id;
        public String image;
        public String tiny_image;
        public String title;
        public String description;
        public String market_price;
        public String current_price;
        public String promotion_price;
        public String sale_num;
        public String score;
        public String comment_num;
        public String deal_url;
        public String deal_murl;

        public String getDeal_id() {
            return deal_id;
        }

        public void setDeal_id(String deal_id) {
            this.deal_id = deal_id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTiny_image() {
            return tiny_image;
        }

        public void setTiny_image(String tiny_image) {
            this.tiny_image = tiny_image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMarket_price() {
            return market_price;
        }

        public void setMarket_price(String market_price) {
            this.market_price = market_price;
        }

        public String getCurrent_price() {
            return current_price;
        }

        public void setCurrent_price(String current_price) {
            this.current_price = current_price;
        }

        public String getPromotion_price() {
            return promotion_price;
        }

        public void setPromotion_price(String promotion_price) {
            this.promotion_price = promotion_price;
        }

        public String getSale_num() {
            return sale_num;
        }

        public void setSale_num(String sale_num) {
            this.sale_num = sale_num;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getComment_num() {
            return comment_num;
        }

        public void setComment_num(String comment_num) {
            this.comment_num = comment_num;
        }

        public String getDeal_url() {
            return deal_url;
        }

        public void setDeal_url(String deal_url) {
            this.deal_url = deal_url;
        }

        public String getDeal_murl() {
            return deal_murl;
        }

        public void setDeal_murl(String deal_murl) {
            this.deal_murl = deal_murl;
        }
    }
}

