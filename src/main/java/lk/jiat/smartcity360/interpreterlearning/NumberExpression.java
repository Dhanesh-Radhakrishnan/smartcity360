package lk.jiat.smartcity360.interpreterlearning;

//for numbers like 1, 2, 4 etc.
public class NumberExpression implements Expression{

    private int number;

    public NumberExpression(int number){
        this.number = number;
    }

    @Override
    public int interpret() {
        return number;
    }
}
