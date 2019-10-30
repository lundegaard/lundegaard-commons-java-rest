package eu.lundegaard.commons.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.lundegaard.commons.jackson.ObjectMapperFactory;
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
        jacksonDecoder = new JacksonDecoder(mapper);
    }

    public static <T> T create(Class<T> type, String target) {
        return builder()
            .target(type, target);
    }

    public static Feign.Builder builder() {
        return Feign.builder()
            .decoder(jacksonDecoder);
    }
}
