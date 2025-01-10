package fr.emse.master;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

public class Main {
    public static void main(String[] args) {
        try {
            // Step 1: Extract data from Bulbapedia
            RDFGenerator rdfGenerator = new RDFGenerator();
            rdfGenerator.generateRDFFromWiki();

            // Step 2: Upload RDF to Fuseki
            FusekiUploader uploader = new FusekiUploader();
            uploader.uploadToFuseki("src/main/resources/rdf/output.ttl");

            // Step 3: Align with DBpedia
            Model model = ModelFactory.createDefaultModel();
            SchemaAlignment aligner = new SchemaAlignment();
            aligner.alignWithDBpedia(model, "Bulbasaur");

            // Step 4: Query data
            SPARQLQueries queries = new SPARQLQueries();
            queries.runQueries();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
