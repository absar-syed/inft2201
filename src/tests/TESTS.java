package tests;

public class TESTS {

    public static void main(String[] args) {

        String test = "adsfdf";

        long test2;

        try {
            test2 = Long.parseLong(test);
            System.out.println("Success");

        }
        catch (NumberFormatException e){

            System.out.println(e.getMessage());

        }

    }


}
