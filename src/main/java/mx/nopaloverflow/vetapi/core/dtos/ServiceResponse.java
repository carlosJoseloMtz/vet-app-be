package mx.nopaloverflow.vetapi.core.dtos;

public class ServiceResponse<DataReturn> {
    private DataReturn data;
    private boolean isError = false;
    private String errorMessage;

    public ServiceResponse() {
    }

    public ServiceResponse(DataReturn data) {
        this.data = data;
    }

    public DataReturn getData() {
        return data;
    }

    public void setData(DataReturn data) {
        this.data = data;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
