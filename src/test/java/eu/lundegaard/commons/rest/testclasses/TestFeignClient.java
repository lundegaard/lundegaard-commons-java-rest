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
