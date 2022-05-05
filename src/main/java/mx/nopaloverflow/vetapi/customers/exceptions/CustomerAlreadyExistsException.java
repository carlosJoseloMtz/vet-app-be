package mx.nopaloverflow.vetapi.customers.exceptions;

public class CustomerAlreadyExistsException extends Exception {

    public CustomerAlreadyExistsException(final String message) {
        super(message);
    }
}
