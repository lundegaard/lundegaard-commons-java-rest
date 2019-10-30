package eu.lundegaard.commons.rest;

import java.math.BigDecimal;
import java.time.*;
import java.util.Date;
import java.util.Objects;

/**
 * Used to test deserialization from JSON to supported Java types
 *
 * @author ales.nevrela (ales.nevrela@lundegaard.eu)
 */
public class TestDto {
    private String stringProperty;
    private int intProperty;
    private BigDecimal bigDecimalProperty;
    private Date dateProperty;
    private LocalDate localDateProperty;
    private LocalTime localTimeProperty;
    private LocalDateTime localDateTimeProperty;
    private OffsetDateTime offsetDateTimeProperty;
    private ZonedDateTime zonedDateTimeProperty;

    public String getStringProperty() {
        return stringProperty;
    }

    public void setStringProperty(String stringProperty) {
        this.stringProperty = stringProperty;
    }

    public TestDto stringProperty(String stringProperty) {
        this.stringProperty = stringProperty;
        return this;
    }

    public int getIntProperty() {
        return intProperty;
    }

    public void setIntProperty(int intProperty) {
        this.intProperty = intProperty;
    }

    public TestDto intProperty(int intProperty) {
        this.intProperty = intProperty;
        return this;
    }

    public BigDecimal getBigDecimalProperty() {
        return bigDecimalProperty;
    }

    public void setBigDecimalProperty(BigDecimal bigDecimalProperty) {
        this.bigDecimalProperty = bigDecimalProperty;
    }

    public TestDto bigDecimalProperty(BigDecimal bigDecimalProperty) {
        this.bigDecimalProperty = bigDecimalProperty;
        return this;
    }

    public Date getDateProperty() {
        return dateProperty;
    }

    public void setDateProperty(Date dateProperty) {
        this.dateProperty = dateProperty;
    }

    public TestDto dateProperty(Date dateProperty) {
        this.dateProperty = dateProperty;
        return this;
    }

    public LocalDate getLocalDateProperty() {
        return localDateProperty;
    }

    public void setLocalDateProperty(LocalDate localDateProperty) {
        this.localDateProperty = localDateProperty;
    }

    public TestDto localDateProperty(LocalDate localDateProperty) {
        this.localDateProperty = localDateProperty;
        return this;
    }

    public LocalTime getLocalTimeProperty() {
        return localTimeProperty;
    }

    public void setLocalTimeProperty(LocalTime localTimeProperty) {
        this.localTimeProperty = localTimeProperty;
    }

    public TestDto localTimeProperty(LocalTime localTimeProperty) {
        this.localTimeProperty = localTimeProperty;
        return this;
    }

    public LocalDateTime getLocalDateTimeProperty() {
        return localDateTimeProperty;
    }

    public void setLocalDateTimeProperty(LocalDateTime localDateTimeProperty) {
        this.localDateTimeProperty = localDateTimeProperty;
    }


    public TestDto localDateTimeProperty(LocalDateTime localDateTimeProperty) {
        this.localDateTimeProperty = localDateTimeProperty;
        return this;
    }

    public OffsetDateTime getOffsetDateTimeProperty() {
        return offsetDateTimeProperty;
    }

    public void setOffsetDateTimeProperty(OffsetDateTime offsetDateTimeProperty) {
        this.offsetDateTimeProperty = offsetDateTimeProperty;
    }

    public TestDto offsetDateTimeProperty(OffsetDateTime offsetDateTimeProperty) {
        this.offsetDateTimeProperty = offsetDateTimeProperty;
        return this;
    }

    public ZonedDateTime getZonedDateTimeProperty() {
        return zonedDateTimeProperty;
    }

    public void setZonedDateTimeProperty(ZonedDateTime zonedDateTimeProperty) {
        this.zonedDateTimeProperty = zonedDateTimeProperty;
    }

    public TestDto zonedDateTimeProperty(ZonedDateTime zonedDateTimeProperty) {
        this.zonedDateTimeProperty = zonedDateTimeProperty;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestDto that = (TestDto) o;
        return intProperty == that.intProperty &&
            Objects.equals(stringProperty, that.stringProperty) &&
            Objects.equals(bigDecimalProperty, that.bigDecimalProperty) &&
            Objects.equals(dateProperty, that.dateProperty) &&
            Objects.equals(localDateProperty, that.localDateProperty) &&
            Objects.equals(localTimeProperty, that.localTimeProperty) &&
            Objects.equals(localDateTimeProperty, that.localDateTimeProperty) &&
            Objects.equals(offsetDateTimeProperty, that.offsetDateTimeProperty) &&
            Objects.equals(zonedDateTimeProperty, that.zonedDateTimeProperty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stringProperty, intProperty, bigDecimalProperty, dateProperty, localDateProperty, localTimeProperty, localDateTimeProperty, offsetDateTimeProperty, zonedDateTimeProperty);
    }

    @Override
    public String toString() {
        return "TestDto{" +
            "stringProperty='" + stringProperty + '\'' +
            ", intProperty=" + intProperty +
            ", bigDecimalProperty=" + bigDecimalProperty +
            ", dateProperty=" + dateProperty +
            ", localDateProperty=" + localDateProperty +
            ", localTimeProperty=" + localTimeProperty +
            ", localDateTimeProperty=" + localDateTimeProperty +
            ", offsetDateTimeProperty=" + offsetDateTimeProperty +
            ", zonedDateTimeProperty=" + zonedDateTimeProperty +
            '}';
    }
}
