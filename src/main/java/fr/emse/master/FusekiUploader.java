package fr.emse.master;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.WebContent;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FusekiUploader {
    private final String fusekiDataURL = "http://localhost:3030/bulbapedia_kg/data";

    public void uploadToFuseki(String rdfFilePath) throws Exception {
        // Load the RDF file
        Model model = ModelFactory.createDefaultModel();
        try (InputStream in = new FileInputStream(new File(rdfFilePath))) {
            RDFDataMgr.read(model, in, org.apache.jena.riot.Lang.TURTLE);
        }

        // Upload RDF data to Fuseki using HTTP POST
        HttpURLConnection connection = (HttpURLConnection) new URL(fusekiDataURL).openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", WebContent.contentTypeTurtle);
        RDFDataMgr.write(connection.getOutputStream(), model, org.apache.jena.riot.RDFFormat.TURTLE);

        // Check server response
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            System.out.println("RDF data successfully uploaded to Fuseki.");
        } else {
            System.err.println("Failed to upload RDF data to Fuseki. Response code: " + responseCode);
        }

        connection.disconnect();
    }
}
