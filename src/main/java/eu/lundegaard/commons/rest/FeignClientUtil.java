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
     * Creates preconfigured Feign client implementation and binds it to the target URL address
     *
     * @param clientType - Feign client interface
     * @param target - target URL address
     * @param <T> - Feign client interface type
     * @return Preconfigured Feign client
     */
    public <T> T client(Class<T> clientType, String target) {
        return feignClientFactory.create(clientType, target);
    }

    /**
     * Creates preconfigured {@link Feign.Builder Feign builder} for additional configuration and building Feign clients
     *
     * @return Preconfigured Feign client builder
     */
    public <T> Feign.Builder builder(Class<T> clientType) {
        return feignClientFactory.builder(clientType);
    }
}
