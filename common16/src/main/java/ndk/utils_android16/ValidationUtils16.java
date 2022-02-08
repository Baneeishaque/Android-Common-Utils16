package ndk.utils_android16;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.core.util.Pair;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.Objects;

public class ValidationUtils16 {

    public static void resetErrors(EditText[] editTexts) {

        for (EditText editText : editTexts) {
            editText.setError(null);
        }
    }

    public static boolean emptyCheckEditText(EditText editText) {

        return TextUtils.isEmpty(editText.getText().toString());
    }

    public static boolean nonEmptyCheckEditText(EditText editText) {

        return !emptyCheckEditText(editText);
    }

    public static boolean emptyCheckEditTextArray(EditText[] editTexts) {

        for (EditText editText : editTexts) {

            if (nonEmptyCheckEditText(editText)) {

                return false;
            }
        }
        return true;
    }

    public static boolean nonEmptyCheckEditTextArray(EditText[] editTexts) {

        for (EditText editText : editTexts) {

            if (emptyCheckEditText(editText)) {

                return false;
            }
        }
        return true;
    }

    public static boolean zeroCheckInteger(int integer) {
        return integer == 0;
    }

    public static boolean nonZeroCheckInteger(int integer) {
        return !zeroCheckInteger(integer);
    }

    public static boolean zeroCheckIntegerArray(int[] integers) {

        for (int integer : integers) {

            if (nonZeroCheckInteger(integer)) {

                return false;
            }
        }
        return true;
    }

    public static boolean nonZeroCheckIntegerArray(int[] integers) {

        return !zeroCheckIntegerArray(integers);
    }

    public static boolean zeroCheckDouble(double aDouble) {

        return aDouble == 0;
    }

    public static boolean zeroCheckDoubleArray(double[] doubles) {

        return zeroCheckIntegerArray(DoubleUtils.doubleArrayToIntegerArray(doubles));
    }

    public static boolean zeroCheckEditText(EditText editText) {

        return zeroCheckDouble(Double.parseDouble(editText.getText().toString()));
    }

    public static boolean nonZeroCheckEditText(EditText editText) {

        return !zeroCheckEditText(editText);
    }

    public static Pair<Boolean, EditText> zeroCheckEditTextPairs(Pair<EditText, String>[] editTextErrorPairs) {

        for (Pair<EditText, String> editTextErrorPair : editTextErrorPairs) {

            if (nonZeroCheckEditText(Objects.requireNonNull(editTextErrorPair.first))) {

                editTextErrorPair.first.setError(editTextErrorPair.second);
                return new Pair<>(false, editTextErrorPair.first);
            }
        }

        return new Pair<>(true, null);
    }

    public static org.javatuples.Pair<Boolean, EditText> zeroCheckEditTextPairs(ArrayList<org.javatuples.Pair<EditText, String>> editTextErrorPairs) {

        // true & null - if all zero
        // false & editText - if one is non-zero

        for (org.javatuples.Pair<EditText, String> editTextErrorPair : editTextErrorPairs) {

            if (nonZeroCheckEditText(editTextErrorPair.getValue0())) {

                editTextErrorPair.getValue0().setError(editTextErrorPair.getValue1());
                return org.javatuples.Pair.with(false, editTextErrorPair.getValue0());
            }
        }

        return org.javatuples.Pair.with(true, null);
    }

    public static org.javatuples.Pair<Boolean, EditText> nonZeroCheckEditTextPairs(ArrayList<org.javatuples.Pair<EditText, String>> editTextErrorPairs) {

        // true & null - if all non-zero
        // false & editText - if one is zero

        for (org.javatuples.Pair<EditText, String> editTextErrorPair : editTextErrorPairs) {

            if (zeroCheckEditText(editTextErrorPair.getValue0())) {

                editTextErrorPair.getValue0().setError(editTextErrorPair.getValue1());
                return org.javatuples.Pair.with(false, editTextErrorPair.getValue0());
            }
        }
        return org.javatuples.Pair.with(true, null);
    }

    public static Pair<Boolean, EditText> emptyCheckEditTextPairs(Pair<EditText, String>[] editTextErrorPairs) {

        // true & null - if all empty
        // false & editText - if one is non-empty

        for (Pair<EditText, String> editTextErrorPair : editTextErrorPairs) {

            if (nonEmptyCheckEditText(editTextErrorPair.first)) {

                Objects.requireNonNull(editTextErrorPair.first).setError(editTextErrorPair.second);
                return new Pair<>(false, editTextErrorPair.first);
            }
        }
        return new Pair<>(true, null);
    }

    public static org.javatuples.Pair<Boolean, EditText> nonEmptyCheckEditTextPairs(ArrayList<org.javatuples.Pair<EditText, String>> editTextErrorPairs) {

        // true & null - if all are non-empty
        // false & editText - if one is empty

        for (org.javatuples.Pair<EditText, String> editTextErrorPair : editTextErrorPairs) {

            if (emptyCheckEditText(editTextErrorPair.getValue0())) {

                editTextErrorPair.getValue0().setError(editTextErrorPair.getValue1());
                return org.javatuples.Pair.with(false, editTextErrorPair.getValue0());
            }
        }
        return org.javatuples.Pair.with(true, null);
    }

    public static org.javatuples.Pair<Boolean, EditText> mobileNumberCheckEditTextPairs(ArrayList<org.javatuples.Pair<EditText, String>> editTextErrorPairs) {

        // true & null - if all contains valid phone numbers
        // false & editText - if one didn't contain valid phone number

        for (org.javatuples.Pair<EditText, String> editTextErrorPair : editTextErrorPairs) {

            org.javatuples.Pair<Boolean, EditText> validationResult = mobileNumberCheckEditText(editTextErrorPair);
            if (!validationResult.getValue0()) {
                return org.javatuples.Pair.with(false, validationResult.getValue1());
            }
        }
        return org.javatuples.Pair.with(true, null);
    }

    public static org.javatuples.Pair<Boolean, EditText> mobileNumberCheckEditText(org.javatuples.Pair<EditText, String> editTextErrorPair) {

        // true & null - if editText contains valid phone numbers
        // false & editText - if editText didn't contain valid phone number

        String phoneNumber = editTextErrorPair.getValue0().getText().toString();
        if (!android.util.Patterns.PHONE.matcher(phoneNumber).matches() || phoneNumber.length() < 10) {

            editTextErrorPair.getValue0().setError(editTextErrorPair.getValue1());
            return org.javatuples.Pair.with(false, editTextErrorPair.getValue0());
        }
        return org.javatuples.Pair.with(true, null);
    }

    public static org.javatuples.Pair<Boolean, EditText> mobileNumberCheckEditTextWithRequestFocus(org.javatuples.Pair<EditText, String> editTextErrorPair) {

        // true & null - if editText contains valid phone numbers
        // false & editText - if editText didn't contain valid phone number

        org.javatuples.Pair<Boolean, EditText> validationResult = mobileNumberCheckEditText(editTextErrorPair);
        if (!validationResult.getValue0()) {

            validationResult.getValue1().requestFocus();
            return org.javatuples.Pair.with(false, validationResult.getValue1());
        }
        return org.javatuples.Pair.with(true, null);
    }

    public static Pair<Integer, EditText> countrySpecificMobileNumberCheckEditTextPairs(Triplet<EditText, String, String>[] editTextErrorPairs, Context activityContext, String countryIsoCode) {

        // Arguments
        // editTextErrorPairs[x].first - editText
        // editTextErrorPairs[x].second - invalid Phone Number Error Message
        // editTextErrorPairs[x].first - invalid Phone Number Based on Country Error Message

        // Results
        // 1 & null - if all contains valid country specific phone numbers
        // 0 & editText - if one didn't contain country specific phone number
        // -1 & editText - if one didn't contain valid phone number

        for (Triplet<EditText, String, String> editTextErrorPair : editTextErrorPairs) {

//            boolean matchResult = android.util.Patterns.PHONE.matcher(editTextErrorPair.getValue0().getText().toString()).matches();

            org.javatuples.Pair<Boolean, EditText> validationResult = mobileNumberCheckEditText(editTextErrorPair.removeFrom2());
            if (!validationResult.getValue0()) {
                return new Pair<>(-1, validationResult.getValue1());
            }

            int countrySpecificMobileNumberCheckResult = countrySpecificMobileNumberCheck(activityContext, countryIsoCode, editTextErrorPair.getValue0().getText().toString());

            if (countrySpecificMobileNumberCheckResult == -1) {

                editTextErrorPair.getValue0().setError(editTextErrorPair.getValue1());
                return new Pair<>(-1, editTextErrorPair.getValue0());

            } else if (countrySpecificMobileNumberCheckResult == 0) {

                editTextErrorPair.getValue0().setError(editTextErrorPair.getValue2());
                return new Pair<>(0, editTextErrorPair.getValue0());
            }
        }
        return new Pair<>(1, null);
    }

    public static Pair<Integer, EditText> indianMobileNumberCheckEditTextPairs(Triplet<EditText, String, String>[] editTextErrorPairs, Context activityContext) {

        return countrySpecificMobileNumberCheckEditTextPairs(editTextErrorPairs, activityContext, "IND");
    }

    public static int indianMobileNumberCheck(Context activityContext, String passedMobileNumber) {

        return countrySpecificMobileNumberCheck(activityContext, "IND", passedMobileNumber);
    }

    public static int countrySpecificMobileNumberCheck(Context activityContext, String countryIsoCode, String passedMobileNumber) {

        // Returns
        // -1 on exception
        // 0 Invalid
        // 1 Valid

        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

//        if (passedMobileNumber.length() == 10) {
//
//            passedMobileNumber = phoneNumberUtil.getCountryCodeForRegion(countryIsoCode) + passedMobileNumber;
//        }

        Phonenumber.PhoneNumber mobileNumber;
        try {

            mobileNumber = phoneNumberUtil.parse(passedMobileNumber, countryIsoCode);
            return phoneNumberUtil.isValidNumberForRegion(mobileNumber, countryIsoCode) ? 1 : 0;

        } catch (NumberParseException e) {

            return -1;
        }

//        boolean isValid = phoneNumberUtil.isValidNumber(mobileNumber);
//        if (isValid) {
//            String internationalFormat = phoneNumberUtil.format(mobileNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
//            Toast.makeText(this, "Phone Number is Valid " + internationalFormat, Toast.LENGTH_LONG).show();
//            return true;
//        } else {
//            Toast.makeText(this, "Phone Number is Invalid " + mobileNumber, Toast.LENGTH_LONG).show();
//            return false;
//        }
    }

    public static boolean doubleCheckString(String string) {

        try {

            Double.parseDouble(string);
            return true;

        } catch (NumberFormatException e) {

            return false;
        }
    }

    public static boolean doubleCheckEditText(EditText editText) {

        return doubleCheckString(editText.getText().toString());
    }

    public static boolean nonDoubleCheckString(String string) {
        return !doubleCheckString(string);
    }

    public static boolean nonDoubleCheckEditText(EditText editText) {

        return !doubleCheckEditText(editText);
    }

    public static Pair<Boolean, EditText> doubleCheckEditTextPairs(Pair<EditText, String>[] editTextErrorPairs) {

        for (Pair<EditText, String> editTextErrorPair : editTextErrorPairs) {

            if (nonDoubleCheckEditText(editTextErrorPair.first)) {

                Objects.requireNonNull(editTextErrorPair.first).setError(editTextErrorPair.second);
                return new Pair<>(false, editTextErrorPair.first);
            }
        }
        return new Pair<>(true, null);
    }

    public static boolean integerCheckString(String string) {

        try {

            Integer.parseInt(string);
            return true;

        } catch (NumberFormatException e) {

            return false;
        }
    }

    public static boolean integerCheckEditText(EditText editText) {

        return integerCheckString(editText.getText().toString());
    }

    public static boolean nonIntegerCheckString(String string) {

        return !integerCheckString(string);
    }

    public static boolean nonIntegerCheckEditText(EditText editText) {

        return !integerCheckEditText(editText);
    }

    public static Pair<Boolean, EditText> integerCheckEditTextPairs(Pair<EditText, String>[] editTextErrorPairs) {

        for (Pair<EditText, String> editTextErrorPair : editTextErrorPairs) {

            if (nonIntegerCheckEditText(editTextErrorPair.first)) {

                Objects.requireNonNull(editTextErrorPair.first).setError(editTextErrorPair.second);
                return new Pair<>(false, editTextErrorPair.first);
            }
        }
        return new Pair<>(true, null);
    }
}
