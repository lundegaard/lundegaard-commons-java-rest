package eu.lundegaard.commons.rest;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author ales.nevrela (ales.nevrela@lundegaard.eu)
 */
public class FeignClientFactoryTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9090);

    private TestFeignClient testSubject = FeignClientFactory.create(TestFeignClient.class, "http://localhost:9090");


    @Test
    public void whenRequestIsJson_thenDeserializeJavaTypes() {
        stubFor(get(urlEqualTo("/json")).willReturn(
            aResponse().withBody(FULL_RESPONSE))
        );

        TestDto result = testSubject.testJsonResponse();

        assertThat(result.getStringProperty())
            .isEqualTo(TEXT);
        assertThat(result.getIntProperty())
            .isEqualTo(NUMBER);
        assertThat(result.getBigDecimalProperty())
            .isEqualTo(DECIMAL);
        assertThat(result.getDateProperty().toInstant())
            .isEqualTo(DATETIME.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        assertThat(result.getLocalDateProperty())
            .isEqualTo(DATETIME.toLocalDate());
        assertThat(result.getLocalTimeProperty())
            .isEqualTo(DATETIME.toLocalTime());
        assertThat(result.getLocalDateTimeProperty())
            .isEqualTo(DATETIME);
        assertThat(result.getOffsetDateTimeProperty())
            .isEqualTo(DATETIME.atOffset(ZoneOffset.ofHours(-1)));
        assertThat(result.getZonedDateTimeProperty().toOffsetDateTime())
            .isEqualTo(DATETIME.atOffset(ZoneOffset.ofHours(-1)));
    }

    @Test
    public void whenRequestIsByteArray_thenDeserializeByteArray() {
        stubFor(get(urlEqualTo("/byte")).willReturn(
            aResponse().withBody(BYTE_RESPONSE))
        );

        byte[] result = testSubject.testByteResponse();

        assertThat(BYTE_RESPONSE).isEqualTo(result);
    }

    private static final String TEXT = "Text value";
    private static final int NUMBER = 33;
    private static final BigDecimal DECIMAL = BigDecimal.valueOf(922337203685477583L, 8);
    private static final String DATE_STRING = "2019-11-17";
    private static final String TIME_STRING = "15:45:55.789";
    private static final String DATETIME_STRING = DATE_STRING + "T" + TIME_STRING;
    private static final String ZONE_STRING = "-01";
    private static final LocalDateTime DATETIME = LocalDateTime.parse(DATETIME_STRING);

    private static final String FULL_RESPONSE = new JSONObject()
        .put("stringProperty", TEXT)
        .put("intProperty", NUMBER)
        .put("bigDecimalProperty", DECIMAL)
        .put("dateProperty", DATE_STRING)
        .put("localDateProperty", DATE_STRING)
        .put("localTimeProperty", TIME_STRING)
        .put("localDateTimeProperty", DATETIME_STRING)
        .put("offsetDateTimeProperty", DATETIME_STRING + ZONE_STRING)
        .put("zonedDateTimeProperty", DATETIME_STRING + ZONE_STRING)
        .toString();
    private static final byte[] BYTE_RESPONSE = new byte[]{77};

}
