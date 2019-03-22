package edu.stts.mdpweek5;

import java.util.ArrayList;

public class Order {
    private String type;
    private ArrayList<String> toppings;
    private int qty;
    private long subtotal;

    public Order(String type, ArrayList<String> toppings, int qty, long subtotal) {
        this.type = type;
        this.toppings = toppings;
        this.qty = qty;
        this.subtotal = subtotal;
    }

    public String getType() {return type;}

    public void setType(String type) {this.type = type;}

    public ArrayList<String> getToppings() {return toppings;}

    public void setToppings(ArrayList<String> toppings) {
        this.toppings = toppings;}

    public int getQty() {return qty;}

    public void setQty(int qty) {this.qty = qty;}

    public long getSubtotal() {return subtotal;}

    public void setSubtotal(long subtotal) {this.subtotal = subtotal; }
}
