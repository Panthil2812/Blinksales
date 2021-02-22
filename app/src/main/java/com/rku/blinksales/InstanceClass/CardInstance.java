package com.rku.blinksales.InstanceClass;

public class CardInstance {
    private String p_name;
    private String p_price;
    private Integer p_image;

    public CardInstance(String p_name, String p_price, Integer p_image) {
        this.p_name = p_name;
        this.p_price = p_price;
        this.p_image = p_image;
    }

    public String getP_name() {  return p_name;    }

    public void setP_name(String p_name) {  this.p_name = p_name;  }

    public String getP_price() {  return p_price;   }

    public void setP_price(String p_price) { this.p_price = p_price;  }

    public Integer getP_image() {   return p_image;   }

    public void setP_image(Integer p_image) { this.p_image = p_image; }

}
