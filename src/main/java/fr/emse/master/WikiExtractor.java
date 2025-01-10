package fr.emse.master;

import org.json.JSONObject;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WikiExtractor {

    private final String API_URL = "https://bulbapedia.bulbagarden.net/w/api.php";

    public JSONArray fetchPokemonList() throws Exception {
        // MediaWiki API query to fetch Pokémon list
        String query = API_URL + "?action=query&list=categorymembers&cmtitle=Category:Pokémon&format=json";

        HttpURLConnection connection = (HttpURLConnection) new URL(query).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        // Read response
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        // Parse JSON response
        JSONObject jsonResponse = new JSONObject(response.toString());
        return jsonResponse.getJSONObject("query").getJSONArray("categorymembers");
    }

    public JSONObject fetchPokemonDetails(String title) throws Exception {
        // MediaWiki API query to fetch details of a specific Pokémon
        String query = API_URL + "?action=query&prop=revisions&rvprop=content&format=json&titles=" + title;

        HttpURLConnection connection = (HttpURLConnection) new URL(query).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        // Read response
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        // Parse JSON response
        JSONObject jsonResponse = new JSONObject(response.toString());
        return jsonResponse.getJSONObject("query");
    }
}
