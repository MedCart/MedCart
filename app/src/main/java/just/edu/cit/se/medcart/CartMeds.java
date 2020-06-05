package just.edu.cit.se.medcart;

public class CartMeds {
    public String name="";
    public String price="";
    public int quantity=1;


    public CartMeds() {
    }

    public CartMeds(String name, String price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

}
