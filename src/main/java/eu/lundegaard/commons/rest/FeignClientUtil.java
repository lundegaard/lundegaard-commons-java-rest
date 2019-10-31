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
package eu.lundegaard.commons.rest;

import feign.Feign;

/**
 * Util class for simple building preconfigured feign clients
 *
 * @author ales.nevrela (ales.nevrela@lundegaard.eu)
 */
public class FeignClientUtil {

    private static final FeignClientFactory feignClientFactory = new FeignClientFactory();

    /**
     * Creates preconfigured Feign client implementation and binds it to the target
     * URL address
     *
     * @param clientType - Feign client interface
     * @param target - target URL address
     * @param <T> - Feign client interface type
     * @return Preconfigured Feign client
     */
    public static <T> T client(Class<T> clientType, String target) {
        return feignClientFactory.create(clientType, target);
    }

    /**
     * Creates preconfigured {@link Feign.Builder Feign builder} for additional
     * configuration and building Feign clients
     *
     * @return Preconfigured Feign client builder
     */
    public static <T> Feign.Builder builder(Class<T> clientType) {
        return feignClientFactory.builder(clientType);
    }
}
