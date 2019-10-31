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

import eu.lundegaard.commons.exception.ApplicationException;
import feign.FeignException;

/**
 * Exception is thrown when error response is returned for a Feign client
 * request. Extends Lundegaard general {@linkplain ApplicationException} and
 * replaces {@linkplain FeignException}.
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
