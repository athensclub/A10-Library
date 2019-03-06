package a10lib;

import java.math.BigDecimal;
import java.util.Arrays;

import a10lib.math.anumber.AFraction;
import a10lib.math.anumber.ANumber;
import a10lib.util.ARandom;

public class Test {
    public static void main(String[] args) throws Exception {
	System.out.println(Math.pow(-9, 1/2.0));
	System.out.println(ANumber.valueOf("-121/1.44").pow(ANumber.valueOf("1/2")));
	AFraction[] array = new AFraction[10];
	for (int i = 0; i < array.length; i++) {
	    array[i] = new AFraction(ANumber.valueOf(Double.toString(ARandom.defaultRandom().nextDouble(-100, 100))),
		    ANumber.valueOf(Double.toString(ARandom.defaultRandom().nextDouble(-100, 100))));
	}
	System.out.println(Arrays.deepToString(array));
	Arrays.sort(array);
	System.out.println(Arrays.deepToString(array));
	System.out.print("[");
	for(AFraction f : array) {
	    System.out.print(f.bigDecimalValue() + ", ");
	}
	System.out.println("]");
    }

}
