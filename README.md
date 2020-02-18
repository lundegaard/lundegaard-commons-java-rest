# Lundegaard Java Commons - Rest Client

This library is used for simple creation of REST clients using the OpenFeign library. 

## Usage

Just add it as dependency, e.g. into Maven (use the latest version)

```xml
<dependency>
    <groupId>eu.lundegaard.commons.java</groupId>
    <artifactId>rest</artifactId>
    <version>${lundegaard.commons.rest.version}</version>
</dependency>
``` 

### Prepare client interface
Define your Feign client interface as you are accustomed to. See [OpenFeign documentation](https://github.com/OpenFeign/feign).
 
```java
public interface GoogleMapsClient {
    @RequestLine("GET /maps/api/geocode/json?address={add}&sensor=false")
    @Headers("Accept: application/json")
    GoogleResponse convertToLatLong(@Param("add") String address);
}
``` 

### Instantiate and use client
Instantiate your Feign client interface implementation using Lundegaard Java Commons Rest and call remote API. Client is preconfigured to throw FeignClientException is case when error is received from the server call (eg. not 2xx success status code). 

```java
public class YourClass {
public Coordinates getLundegaardCoordinates() {
    GoogleMapsClient googleClient = FeignClientUtil.client(GoogleMapsClient.class, "http://maps.googleapis.com");
    GoogleResponse googleResponse = googleClient.convertToLatLong("Sokolovská 651/136a Prague");
    
    return googleResponse.getCoordinates();
}
}
``` 

### Configure client
Lundegaard Commons Java Rest provides preconfigured client implementation. It includes
 * JSON (de)serialization
 * Byte array deserialization
 * Logging
 * Error handling  

You may change or extend default configuration using Feign builder

```java
public GoogleMapsClient createGoogleMapsClient() {
    return FeignClientUtil.builder(GoogleMapsClient.class)
        // Do your configuration. For example retry when service is unavailable
        // .retryer(new MyRetryer())
        .target(GoogleMapsClient.class, "http://maps.googleapis.com");
}
``` 
