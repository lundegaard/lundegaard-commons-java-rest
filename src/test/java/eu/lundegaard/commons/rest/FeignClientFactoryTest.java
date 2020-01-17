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

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import eu.lundegaard.commons.rest.error.FeignClientException;
import eu.lundegaard.commons.rest.testclasses.TestDto;
import eu.lundegaard.commons.rest.testclasses.TestFeignClient;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author ales.nevrela (ales.nevrela@lundegaard.eu)
 */
public class FeignClientFactoryTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9090); // Mock server running on http://localhost:9090

    private TestFeignClient testSubject = FeignClientUtil.client(TestFeignClient.class, "http://localhost:9090");


    @Test
    public void whenResponseIsJson_thenDeserializeJavaTypes() {
        stubFor(get(urlEqualTo(JSON_ENDPOINT)).willReturn(
                aResponse().withBody(FULL_RESPONSE)));

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
    public void whenResponseIsByteArray_thenDeserializeByteArray() {
        stubFor(get(urlEqualTo(BYTE_ENDPOINT)).willReturn(
                aResponse().withBody(BYTE_RESPONSE)));

        byte[] result = testSubject.testByteResponse();

        assertThat(BYTE_RESPONSE).isEqualTo(result);
    }


    @Test
    public void whenResponseIs3xx_thenThrowException() {
        stubFor(get(urlEqualTo(JSON_ENDPOINT)).willReturn(
                aResponse().withStatus(HttpStatus.SC_MOVED_PERMANENTLY)));

        assertThatThrownBy(() -> testSubject.testJsonResponse())
                .isInstanceOf(FeignClientException.class)
                .hasFieldOrPropertyWithValue(FIELD_RESPONSE_STATUS, HttpStatus.SC_MOVED_PERMANENTLY);
    }


    @Test
    public void whenResponseIs4xxError_thenThrowException() {
        stubFor(get(urlEqualTo(JSON_ENDPOINT)).willReturn(
                aResponse().withStatus(HttpStatus.SC_NOT_FOUND)));

        assertThatThrownBy(() -> testSubject.testJsonResponse())
                .isInstanceOf(FeignClientException.class)
                .hasFieldOrPropertyWithValue(FIELD_RESPONSE_STATUS, HttpStatus.SC_NOT_FOUND);
    }


    @Test
    public void whenResponseIs5xxError_thenThrowException() {
        stubFor(get(urlEqualTo(JSON_ENDPOINT)).willReturn(
                aResponse().withStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR)));

        assertThatThrownBy(() -> testSubject.testJsonResponse())
                .isInstanceOf(FeignClientException.class)
                .hasFieldOrPropertyWithValue(FIELD_RESPONSE_STATUS, HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }


    private static final String JSON_ENDPOINT = "/json";
    private static final String BYTE_ENDPOINT = "/byte";
    private static final String FIELD_RESPONSE_STATUS = "responseStatus";

    private static final String TEXT = "Text value";
    private static final int NUMBER = 33;
    private static final BigDecimal DECIMAL = BigDecimal.valueOf(922337203685477583L, 8);
    private static final String DATE_STRING = "2019-11-17";
    private static final String TIME_STRING = "15:45:55.777888999";
    private static final String DATETIME_STRING = DATE_STRING + "T" + TIME_STRING;
    private static final String ZONE_STRING = "-01:00";
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
    private static final byte[] BYTE_RESPONSE = new byte[] {77};

}
