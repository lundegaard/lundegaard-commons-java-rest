package eu.lundegaard.commons.rest;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * @author ales.nevrela (ales.nevrela@lundegaard.eu)
 */
public class FeignClientFactoryTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9090);

    private TestFeignClient testSubject = FeignClientFactory.create(TestFeignClient.class, "http://localhost:9090");


    @Test
    public void whenRequestIsJson_thenDeserializeJavaTypes() throws Exception {
        stubFor(get(urlEqualTo("/json")).willReturn(
            aResponse().withBody(FULL_RESPONSE))
        );

        TestDto result = testSubject.testJsonResponse();

        Assertions.assertThat(TEXT)
            .isEqualTo(result.getStringProperty());
        Assertions.assertThat(NUMBER)
            .isEqualTo(result.getIntProperty());
        Assertions.assertThat(DECIMAL)
            .isEqualTo(result.getBigDecimalProperty());
        Assertions.assertThat(DATETIME.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant())
            .isEqualTo(result.getDateProperty().toInstant());
        Assertions.assertThat(DATETIME.toLocalDate())
            .isEqualTo(result.getLocalDateProperty());
        Assertions.assertThat(DATETIME.toLocalTime())
            .isEqualTo(result.getLocalTimeProperty());
        Assertions.assertThat(DATETIME)
            .isEqualTo(result.getLocalDateTimeProperty());
        Assertions.assertThat(DATETIME.atOffset(ZoneOffset.ofHours(-1)))
            .isEqualTo(result.getOffsetDateTimeProperty());
        Assertions.assertThat(DATETIME.atOffset(ZoneOffset.ofHours(-1)))
            .isEqualTo(result.getZonedDateTimeProperty().toOffsetDateTime());
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

}
