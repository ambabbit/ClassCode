public class Apple{

    private String color;
    private int weight;

    public Apple() {
        weight = 0;
        color = "no color defineed";
    }

    public Apple(int weight, String color) {
        this.weight = weight;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public int getWeight() {
        return weight;
    }
}