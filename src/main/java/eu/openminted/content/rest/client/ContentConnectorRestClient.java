package eu.openminted.content.rest.client;

import eu.openminted.content.connector.ContentConnector;
import eu.openminted.content.connector.Query;
import eu.openminted.content.connector.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;

@Component
public class ContentConnectorRestClient implements ContentConnector {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${content.connector.rest.host}")
    private String restHost;

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
}
