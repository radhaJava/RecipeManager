package model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
/*
Measure unit is a enum for sorting the unit of measuremnt recipe in Quantity, litres or kilograms
 */
public enum MeasureUnit {

    Quantity("pc", 1),
    Liters("l", 2),
    Kilogram("kg", 3);

    private String unit;
    private int index;

    MeasureUnit(String unit, int index) {
        this.unit = unit;
        this.index = index;
    }

    public String getUnit() {
        return unit;
    }

    public static MeasureUnit fromUnit(String unitInput) {
        for (MeasureUnit unit : MeasureUnit.values()) {
            if (unit.unit.equals(unitInput)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Invalid input");
    }

    public static MeasureUnit fromIndex(int index) {
        for (MeasureUnit unit : MeasureUnit.values()) {
            if (unit.index == index) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Invalid input");
    }

    public static final List<String> UNIT_OF_MEASURE = Arrays.stream(MeasureUnit.values())
            .map(measureUnit -> measureUnit.name() + "(" + measureUnit.unit + ")")
            .collect(Collectors.toList());
}
