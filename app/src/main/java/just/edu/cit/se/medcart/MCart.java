package just.edu.cit.se.medcart;

public class MCart {
    public String name="";
    public String price="";
    public int quantity=1;


    public MCart() {
    }

    public MCart(String name, String price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

}
