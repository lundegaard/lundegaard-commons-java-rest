package eu.lundegaard.commons.rest.decoder;

import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * Supports deserialization of byte arrays in response body
 *
 * @author ales.nevrela (ales.nevrela@lundegaard.eu)
 */
public class ByteArrayAwareDecoder implements Decoder {
    private static final String BYTE_ARRAY_CLASS_NAME = "byte[]";

    private Decoder decoder;

    public ByteArrayAwareDecoder(Decoder decoder) {
        this.decoder = decoder;
    }


    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        if (type.getTypeName().equals(BYTE_ARRAY_CLASS_NAME)) {
            return passResponseBodyToResult(response);
        } else {
            return decoder.decode(response, type);
        }
    }

    private Object passResponseBodyToResult(Response response) throws IOException {
        InputStream inputStream = response.body().asInputStream();
        return IOUtils.toByteArray(inputStream);
    }
}
