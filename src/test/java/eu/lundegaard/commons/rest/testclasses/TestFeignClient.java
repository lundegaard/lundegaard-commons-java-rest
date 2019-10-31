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
package eu.lundegaard.commons.rest.testclasses;

import feign.RequestLine;

/**
 * Test REST client
 *
 * @author ales.nevrela (ales.nevrela@lundegaard.eu)
 */
public interface TestFeignClient {

    @RequestLine("GET /json")
    public TestDto testJsonResponse();

    @RequestLine("GET /byte")
    public byte[] testByteResponse();
}
