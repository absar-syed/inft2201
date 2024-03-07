package syeda;

public class NotFoundException extends Exception{

    NotFoundException(){
        super();
    }

    NotFoundException(String message){
        super(message);
    }
}
