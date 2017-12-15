package eu.openminted.content.rest.client;

import eu.openminted.content.connector.ContentConnector;
import eu.openminted.content.connector.Query;
import eu.openminted.content.connector.SearchResult;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;

public class ContentConnectorRestClient implements ContentConnector {

    private RestTemplate restTemplate;

    private String restHost;

    private ContentConnectorRestClient() {}

    public ContentConnectorRestClient(RestTemplate restTemplate, String restHost) {
        this.restTemplate = restTemplate;
        this.restHost = restHost;
    }

    @Override
    public SearchResult search(Query query) {

        ResponseEntity<SearchResult> response = restTemplate.postForEntity(restHost + "/search/", query, SearchResult.class);
        return response.getBody();
    }

    @Override
    public InputStream downloadFullText(String documentId) {

        ResponseEntity<Resource> response = restTemplate.getForEntity(restHost + "/downloadFullText/?documentId=" + documentId, Resource.class);
        try {
            return response.getBody().getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public InputStream fetchMetadata(Query query) {

        ResponseEntity<Resource> response = restTemplate.postForEntity(restHost + "/fetchMetadata/", query, Resource.class);
        try {
            return response.getBody().getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getSourceName() {

        ResponseEntity<String> sourceName = restTemplate.getForEntity(restHost + "/getSourceName/", String.class);
        return sourceName.getBody();
    }

    public String getRestHost() {
        return restHost;
    }

    public void setRestHost(String restHost) {
        this.restHost = restHost;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
