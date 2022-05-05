package mx.nopaloverflow.vetapi.core.dtos;

public class ServiceFailedResponse<Type> extends ServiceResponse<Type> {

    public ServiceFailedResponse(final String message) {
        super();
        this.setError(true);
        this.setErrorMessage(message);
    }

    public ServiceFailedResponse(final String message, final Type payload) {
        super(payload);
        this.setError(true);
        this.setErrorMessage(message);
    }
}
