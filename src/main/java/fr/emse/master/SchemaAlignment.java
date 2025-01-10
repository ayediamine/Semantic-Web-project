package fr.emse.master;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.OWL;

public class SchemaAlignment {

    public void alignWithDBpedia(Model model, String pokemonName) {
        String dbpediaURL = "http://dbpedia.org/resource/" + pokemonName.replace(" ", "_");

        Resource pokemonResource = model.getResource("http://example.org/pokemon#" + pokemonName.replace(" ", "_"));
        pokemonResource.addProperty(OWL.sameAs, model.createResource(dbpediaURL));

        System.out.println("Aligned " + pokemonName + " with DBpedia.");
    }
}
