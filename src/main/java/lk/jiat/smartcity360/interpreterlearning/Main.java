package lk.jiat.smartcity360.interpreterlearning;

public class Main {

    public static void main(String[] args) {
        Expression numb1 = new NumberExpression(1);
        Expression numb2 = new NumberExpression(2);

        Expression addition = new AddExpression(numb1, numb2);

        System.out.println("Result : " + addition.interpret());
    }

}
