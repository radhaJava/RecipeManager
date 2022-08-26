package model;
/*
Ingredient class have ingredient name, measure unit from enum and amount
 */
public class Ingredient {

    private final String name;
    private final MeasureUnit unit;
    private final int amount;

    public Ingredient(String name, MeasureUnit unit, int amount) {
        this.name = name;
        this.unit = unit;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public MeasureUnit getUnit() {
        return unit;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return  "name=" + name + ", unit=" + unit + ", amount=" + amount;
    }
}
