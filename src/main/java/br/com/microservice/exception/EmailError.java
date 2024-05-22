package br.com.repassa.exception;

import br.com.backoffice_repassa_utils_lib.error.interfaces.RepassaUtilError;


public class EmailError implements RepassaUtilError {

    private static final String APP_PREFIX = "email";
    private String errorCode;
    private String errorMessage;

    public static final RepassaUtilError ERROR_CONVERTER_MAP =
            new EmailError("001", "Erro ao converter valores do objeto para Map");

    public static final RepassaUtilError ENDPOINT_NAO_VALIDO =
            new EmailError("002", "O endpoint não foi localizado.");

    public EmailError(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorCode() {
        return APP_PREFIX.concat("_").concat(errorCode);
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

}
