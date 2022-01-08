package ndk.utils_android16;

public class DoubleUtils {

    public static int[] doubleArrayToIntegerArray(double[] doubles) {
        int[] integers = new int[doubles.length];
        for (int i = 0; i < integers.length; ++i) {
            integers[i] = (int) doubles[i];
        }
        return integers;
    }

    public static double roundOff_to_two_positions(double x) {
        double a = x;
        double temp = Math.pow(10.0, 2);
        a *= temp;
        a = Math.round(a);
        return (a / temp);
    }
}
