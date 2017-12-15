# content-connector-rest-client


Due to the need to expand the content connector service interoperability to accept content from RESTful services,
this project implements a REST client that connects to REST endpoints.

## Notes,

- The ContentConnector implemented in the content-connector-rest-client project is not annotated with as Component and its instantiation will be manageable by plain spring xml configuration and the necessary property, without the need to create separate configuration classes.
- For the time being, the endpoints of the REST services have to have specific naming:

    i.e. for host http://content-connector-rest-service:9999,
    - endpoint for search should be a post method found at http://content-connector-rest-service:9999/search/
    - endpoint for downloadFullText should be a get method found at http://content-connector-rest-service:9999/downloadFullText/?documentId= and the resource identifier
    - endpoint for fetchMetadata should be a post method found at http://content-connector-rest-service:9999/fetchMetadata/
    - endpoint for getSourceName should be a get method found at http://content-connector-rest-service:9999/getSourceName/


## Instantiation example

In order to instantiate a rest client as a ContentConnector a developer needs to create a Bean
out of the ContentConnectorRestClient class.

This can be done either with a separate class annotated as @Configuration or even with a simple spring configuration xml.

Following the latter approach:

- create an xml file named i.e. rest-clients.xml
- add beans necessary for creating the instance of the ContentConnectorRestClient

        <bean name="restTemplate" class="org.springframework.web.client.RestTemplate"/>

        <bean id="contentConnectorClient1" name="contentConnectorClient1" class="eu.openminted.content.rest.client.ContentConnectorRestClient">
            <constructor-arg type="java.lang.String" name="restHost" value="${content.connector.rest.client1.host}"/>
            <constructor-arg name="restTemplate" ref="restTemplate"/>
        </bean>

- add the name of the file as resource in the springrest-servlet.xml, beneath corpus-builder-config.xml

        <import resource="corpus-builder-config.xml"/>
        <import resource="classpath*:rest-clients.xml"/>


In case of multiple REST clients that listen to different, you need to set separate names to the corresponding property.
I.e.

        <bean id="client1" name="contentConnectorClient1" class="eu.openminted.content.rest.client.ContentConnectorRestClient">
            <constructor-arg type="java.lang.String" name="restHost" value="${content.connector.rest.client1.host}"/>
            <constructor-arg name="restTemplate" ref="restTemplate"/>
        </bean>

        <bean id="client2" name="contentConnectorClient2" class="eu.openminted.content.rest.client.ContentConnectorRestClient">
            <constructor-arg type="java.lang.String" name="restHost" value="${content.connector.rest.client2.host}"/>
            <constructor-arg name="restTemplate" ref="restTemplate"/>
        </bean>


In that way both properties can be set in the same file and the two separate instances of the clients will listen to different hosts.

You will find an example of the rest-clients.xml under the test resources of content-connector-service.