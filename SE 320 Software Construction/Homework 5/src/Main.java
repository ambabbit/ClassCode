
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {
        List<Apple> apples = new ArrayList<Apple>();

        for (int i = 0; i< 10; i++) {
            apples.add(new Apple( (new Random().nextInt(200-100) + 100),"red"));
        }


        prettyPrintApple(apples, new PrintAppleWeight());
        prettyPrintApple(apples, new ColorAndComparedWeight());
    }

    public static void prettyPrintApple(List<Apple> inventory, ApplePredicate aplString){
        for (Apple apple: inventory) {
            String output = aplString.printApple(apple);
            System.out.println(output);
        }
    }

}

class PrintAppleWeight implements ApplePredicate {

    @Override
    public String printApple(Apple apl) {
        // TODO Auto-generated method stub
        return String.valueOf(apl.getWeight());
    }
    
}
class ColorAndComparedWeight implements ApplePredicate {

    @Override
    public String printApple(Apple apl) {
        // TODO Auto-generated method stub
        return apl.getColor() + ", " + ((apl.getWeight() > 150) ? "Weighty" : "Light");
    }
    
}