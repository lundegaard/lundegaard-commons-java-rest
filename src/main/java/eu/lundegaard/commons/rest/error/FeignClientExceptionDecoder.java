/*
 * Copyright (C) 2019 Lundegaard a.s., All Rights Reserved
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; version 3.0 of the License.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package eu.lundegaard.commons.rest.error;

import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

/**
 * Wraps original {@linkplain FeignException} to Lundegaard's general
 * {@linkplain FeignClientException}
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
