package ndk.utils_android16;


public class Float_Utils {
    public static float roundOff(float x, int position) {
        float a = x;
        double temp = Math.pow(10.0, position);
        a *= temp;
        a = Math.round(a);
        return (a / (float) temp);
    }

    public static float roundOff_to_two_positions(float x) {
        float a = x;
        double temp = Math.pow(10.0, 2);
        a *= temp;
        a = Math.round(a);
        return (a / (float) temp);
    }

    public static float roundOff_to_two_positions(String floatString) {

        return roundOff_to_two_positions(Float.parseFloat(floatString));
    }
}
