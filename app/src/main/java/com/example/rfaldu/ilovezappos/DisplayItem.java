package com.example.rfaldu.ilovezappos;

/**
 * Created by rfaldu on 2/5/2017.
 */

public class DisplayItem {

    String itemName;
    String brandName;
    String imageURL;
    String price;
    String percentOff;

    public DisplayItem(String itemName, String brandName, String imageURL, String price, String percentOff){
        this.itemName = itemName;
        this.brandName = brandName;
        this.imageURL = imageURL;
        this.price = price;
        this.percentOff = percentOff;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
