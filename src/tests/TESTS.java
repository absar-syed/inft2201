package tests;

public class TESTS {

    public static void main(String[] args) {

        String test = "tes";
        String errorBuffer = "Error: ";

        try
        {
           Long.parseLong(test);
        }
        catch (NumberFormatException e)
        {

            errorBuffer = errorBuffer.concat("\n").concat(e.getMessage());
        }
        errorBuffer = errorBuffer.concat("\n").concat("input error1");

        errorBuffer = errorBuffer.concat("\ninput error2");
        errorBuffer = errorBuffer.concat("\ninput error3");
        errorBuffer = errorBuffer.concat("\ninput error4");
        errorBuffer = errorBuffer.concat("\ninput error5");

        String message = null;

        message = errorBuffer;

        System.out.println(message);
        System.out.print("User ID cannot be blank!"+"\n"+"User ID already exists!");


    }


}
