package eu.lundegaard.commons.rest.error;

import eu.lundegaard.commons.exception.ApplicationException;
import feign.FeignException;

/**
 * Exception is thrown when error response is returned for a Feign client request. Extends Lundegaard general {@linkplain ApplicationException} and replaces {@linkplain FeignException}.
 *
 * @author ales.nevrela (ales.nevrela@lundegaard.eu)
 */
public class FeignClientException extends ApplicationException {
    private int responseStatus;

    public FeignClientException(String message, int responseStatus) {
        super(message);
        this.responseStatus = responseStatus;
    }

    public FeignClientException(String message, Throwable cause, int responseStatus) {
        super(message, cause);
        this.responseStatus = responseStatus;
    }

    /**
     * Returns response HTTP status code
     *
     * @return HTTP status code
     */
    public int getResponseStatus() {
        return responseStatus;
    }
}
