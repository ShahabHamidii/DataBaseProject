package util;

public class ValidationUtil {

    public static boolean isEmpty(
            String value
    ) {

        return value == null ||
                value.trim().isEmpty();
    }

    public static boolean isInteger(
            String value
    ) {

        try {

            Integer.parseInt(value);

            return true;

        } catch (Exception e) {

            return false;
        }
    }

    public static boolean isDouble(
            String value
    ) {

        try {

            Double.parseDouble(value);

            return true;

        } catch (Exception e) {

            return false;
        }
    }

    public static boolean isValidYear(
            String value
    ) {

        if(!isInteger(value))
            return false;

        int year =
                Integer.parseInt(value);

        return year >= 2000 &&
                year <= 2100;
    }

    public static boolean isValidSemester(
            String semester
    ) {

        return semester.equalsIgnoreCase("Fall")
                ||
                semester.equalsIgnoreCase("Spring")
                ||
                semester.equalsIgnoreCase("Summer");
    }
}