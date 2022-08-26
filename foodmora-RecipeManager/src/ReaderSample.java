import model.MeasureUnit;

import java.util.Scanner;

public class ReaderSample {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNext()) {
            int unit = scanner.nextInt();
            MeasureUnit unitEnum = MeasureUnit.fromIndex(unit);
            System.out.println(unitEnum);
        }
        scanner.close();
    }
}
