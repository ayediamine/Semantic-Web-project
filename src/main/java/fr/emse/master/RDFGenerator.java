package fr.emse.master;

import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDF;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileOutputStream;

public class RDFGenerator {
    private final String namespace = "http://example.org/pokemon#";
    private final String schema = "http://schema.org/";

    public void generateRDFFromWiki() throws Exception {
        WikiExtractor extractor = new WikiExtractor();
        JSONArray pokemonList = extractor.fetchPokemonList();

        // Create an RDF model
        Model model = ModelFactory.createDefaultModel();

        // Loop through each Pokémon
        for (int i = 0; i < pokemonList.length(); i++) {
            JSONObject pokemon = pokemonList.getJSONObject(i);
            String title = pokemon.getString("title");
            JSONObject details = extractor.fetchPokemonDetails(title);

            // Add Pokémon details to RDF model
            Resource pokemonResource = model.createResource(namespace + title.replace(" ", "_"))
                    .addProperty(RDF.type, model.createResource(schema + "Thing"))
                    .addProperty(model.createProperty(schema + "name"), title);
        }

        // Write RDF to file
        try (FileOutputStream out = new FileOutputStream("src/main/resources/rdf/output.ttl")) {
            model.write(out, "TURTLE");
        }

        System.out.println("RDF file generated from Bulbapedia.");
    }
}
