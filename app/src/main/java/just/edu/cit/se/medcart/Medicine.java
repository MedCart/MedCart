package just.edu.cit.se.medcart;

import java.util.ArrayList;

public class Medicine {
    public String name ;
    public ArrayList<String> dosage= new ArrayList<String>();
    public String usage;
    public String price;

    public Medicine(String name, ArrayList<String> dosage, String usage, String price) {
        this.name = name;
        this.dosage = dosage;
        this.usage = usage;
        this.price = price ;
    }

    public String getDosage()
    {
        String Dosage="";
        for(int i=0;i<dosage.size();i++)
        {
            Dosage+=dosage.get(i)+"\n";
        }
        return Dosage;
    }
    public Medicine() {
    }

}
