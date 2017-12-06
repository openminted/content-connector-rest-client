package eu.openminted.content.rest.client;

import eu.openminted.content.RestClientConfiguration;
import eu.openminted.content.connector.Query;
import eu.openminted.content.connector.SearchResult;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RestClientConfiguration.class})
public class TestRestClient {

    @Autowired
    private ContentConnectorRestClient contentConnectorRestClient;

    private Query query;

    @Before
    public void initialize() {
        // The way this test is implemented it supposes all of the following parameters enabled.
        // To alter the query by a parameter or field or facet
        // feel free to comment or add anything

        query = new Query();
        query.setFrom(0);
        query.setTo(1);
        query.setKeyword("");
        query.setParams(new HashMap<>());
        query.getParams().put("sort", new ArrayList<>());
        query.getParams().get("sort").add("__indexrecordidentifier asc");
        query.getParams().put("licence", new ArrayList<>());
        query.getParams().get("licence").add("Open Access");
        query.getParams().put("publicationyear", new ArrayList<>());
        query.getParams().put("documenttype", new ArrayList<>());
        query.getParams().put("documentlanguage", new ArrayList<>());

    }

    @Test
    @Ignore
    public void testSearch() {

        System.out.println("Get search result");

        query.getParams().get("publicationyear").add("1953");
        query.getParams().get("documenttype").add("Fulltext");
        query.getParams().get("documentlanguage").add("Nl");

        SearchResult result = contentConnectorRestClient.search(query);

        assertNotEquals(null,  result);
    }

    @Test
    @Ignore
    public void testGetSourceName() {

        System.out.println("Get source name");

        String sourceName = contentConnectorRestClient.getSourceName();

        assertNotEquals(null,  sourceName);
        assertNotEquals("",  sourceName);
    }

    @Test
    @Ignore
    public void testDownloadFulltext() throws IOException {

        System.out.println("Download fulltext");
        InputStream inputStream = contentConnectorRestClient.downloadFullText("core_ac_uk__::7dbf1b5cf5beabe314c170f2b0fb9357");

        assertNotEquals(null, inputStream);

        FileOutputStream fileOutputStream = new FileOutputStream(new File("download.pdf"));
        IOUtils.copy(inputStream, fileOutputStream);
        fileOutputStream.close();
        inputStream.close();
    }

    @Test
    @Ignore
    public void testFetchMetadata() throws IOException {
        System.out.println("Fetch metadata");
        InputStream inputStream = contentConnectorRestClient.fetchMetadata(query);

        assertNotEquals(null, inputStream);

        FileOutputStream fileOutputStream = new FileOutputStream(new File("metadata.xml"));
        IOUtils.copy(inputStream, fileOutputStream);
        fileOutputStream.close();
        inputStream.close();
    }
}
