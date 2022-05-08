package model;

import java.io.Serializable;

public class Item implements Serializable {
    private String id; //termék firestore-os id-ja
    private String name; //termék neve
    private String desc; //termék leírása
    private String cost; //termék ára
    private int cartCount; //termék mennyiszer szerepel a kosárban

    public Item() {
        this.cost = "0";
        this.desc = "";
        this.name = "";
    }

    public Item(String name, String desc,String cost, int cartCount) {
        this.name = name;
        this.desc = desc;
        this.cost = cost;
        this.cartCount = cartCount;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCost() {
        return cost;
    }
    public void setCost(String cost) {
        this.cost = cost;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String _getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getCartCount() {
        return cartCount;
    }
}
