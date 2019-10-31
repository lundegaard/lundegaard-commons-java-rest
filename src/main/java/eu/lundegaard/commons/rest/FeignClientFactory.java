package eu.lundegaard.commons.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.lundegaard.commons.jackson.ObjectMapperFactory;
import eu.lundegaard.commons.rest.decoder.ByteArrayAwareDecoder;
import eu.lundegaard.commons.rest.error.FeignClientExceptionDecoder;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;

/**
 * Factory class creates preconfigured {@link feign.Feign Feign clients}
 *
 * @author ales.nevrela (ales.nevrela@lundegaard.eu)
 */
public class FeignClientFactory {

    private final Decoder jacksonDecoder;
    private final Encoder jacksonEncoder;
    private final ErrorDecoder errorDecoder;

    public FeignClientFactory() {
        // Preconfigured Lundegaard mapper
        ObjectMapper mapper = ObjectMapperFactory.createObjectMapper();
        jacksonDecoder = new ByteArrayAwareDecoder(new JacksonDecoder(mapper));
        jacksonEncoder = new JacksonEncoder(mapper);
        errorDecoder = new FeignClientExceptionDecoder();
    }

    public FeignClientFactory(Decoder jacksonDecoder, Encoder jacksonEncoder, ErrorDecoder errorDecoder) {
        this.jacksonDecoder = jacksonDecoder;
        this.jacksonEncoder = jacksonEncoder;
        this.errorDecoder = errorDecoder;
    }

    /**
     * Creates preconfigured Feign client implementation and binds it to the target URL address
     *
     * @param clientType - Feign client interface
     * @param target - target URL address
     * @param <T> - Feign client interface type
     * @return Preconfigured Feign client
     */
    public <T> T create(Class<T> clientType, String target) {
        return builder(clientType)
            .target(clientType, target);
    }

    /**
     * Creates preconfigured {@link Feign.Builder Feign builder} for additional configuration and building Feign clients
     *
     * @return Preconfigured Feign client builder
     */
    public <T> Feign.Builder builder(Class<T> clientType) {
        return Feign.builder()
            .encoder(jacksonEncoder)
            .decoder(jacksonDecoder)
            .logger(new Slf4jLogger(clientType))
            .logLevel(feign.Logger.Level.FULL)
            .errorDecoder(errorDecoder);
    }
}
