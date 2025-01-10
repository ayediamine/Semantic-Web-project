package fr.emse.master;

import org.apache.jena.query.*;

public class SPARQLQueries {
    private final String fusekiSPARQLEndpoint = "http://localhost:3030/bulbapedia_kg/sparql";

    public void runQueries() {
        // Define a SPARQL query
        String queryString = "PREFIX schema: <http://schema.org/> " +
                "SELECT ?name WHERE { " +
                "  ?pokemon schema:name ?name . " +
                "}";

        // Execute the query
        Query query = QueryFactory.create(queryString);
        try (QueryExecution qexec = QueryExecutionFactory.sparqlService(fusekiSPARQLEndpoint, query)) {
            ResultSet results = qexec.execSelect();
            ResultSetFormatter.out(System.out, results, query);
        } catch (Exception e) {
            System.err.println("An error occurred during SPARQL query execution: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
