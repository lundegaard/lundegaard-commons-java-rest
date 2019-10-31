package eu.lundegaard.commons.rest.error;

import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Wraps original {@linkplain FeignException} to Lundegaard's general {@linkplain FeignClientException}
 *
 * @author ales.nevrela (ales.nevrela@lundegaard.eu)
 */
public class FeignClientExceptionDecoder implements ErrorDecoder {
    private static final Logger LOG = LoggerFactory.getLogger(FeignClientExceptionDecoder.class);

    @Override
    public Exception decode(String methodKey, Response response) {
        LOG.debug("Feign client error occurred: {}", response);

        return errorStatus(methodKey, response);
    }

    // Construct FeignClientException from response
    // See FeignException#errorStatus
    private FeignClientException errorStatus(String methodKey, Response response) {
        String message = String.format("status %s reading %s", response.status(), methodKey);
        try {
            if (response.body() != null) {
                String body = Util.toString(response.body().asReader());
                message += "; content:\n" + body;
            }
        } catch (IOException ignored) { // NOPMD
        }
        return new FeignClientException(message, response.status());
    }
}
