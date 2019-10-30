package eu.lundegaard.commons.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.lundegaard.commons.jackson.ObjectMapperFactory;
import eu.lundegaard.commons.rest.decoder.ByteArrayAwareDecoder;
import feign.Feign;
import feign.codec.Decoder;
import feign.jackson.JacksonDecoder;

/**
 * Factory class creates preconfigured {@link feign.Feign Feign clients}
 *
 * @author ales.nevrela (ales.nevrela@lundegaard.eu)
 */
public class FeignClientFactory {

    private static final Decoder jacksonDecoder;

    static {
        // Preconfigured Lundegaard mapper
        ObjectMapper mapper = ObjectMapperFactory.createObjectMapper();
        jacksonDecoder = new ByteArrayAwareDecoder(new JacksonDecoder(mapper));
    }

    /**
     * Creates preconfigured Feign client implementation and binds it to the target URL address
     *
     * @param clientType - Feign client interface
     * @param target - target URL address
     * @param <T> - Feign client interface type
     * @return Preconfigured Feign client
     */
    public static <T> T create(Class<T> clientType, String target) {
        return builder()
            .target(clientType, target);
    }

    /**
     * Creates preconfigured {@link Feign.Builder Feign builder} for additional configuration and building Feign clients
     *
     * @return
     */
    public static Feign.Builder builder() {
        return Feign.builder()
            .decoder(jacksonDecoder);
    }
}
