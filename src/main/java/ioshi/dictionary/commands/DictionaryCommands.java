package ioshi.dictionary.commands;

import org.springframework.core.io.ResourceLoader;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@ShellComponent
public class DictionaryCommands {

    private final ResourceLoader resourceLoader;

    public DictionaryCommands(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @ShellMethod(key = "dict", value = "Give the definition for the word")
    public String Dict(@ShellOption String word) {
        List response = apiRequest(word);

        if (response == null || response.isEmpty()) {
            return "Not found :(";
        }

        try {
            String definition = serializeJson(response);
            return "Meaning: " + definition;
        } catch (Exception e) {
            return "Api error";
        }
    }

    private List apiRequest(String word) {
        WebClient.Builder webClientBuilder = WebClient.builder();
        var response = webClientBuilder.build()
                .get()
                .uri("https://api.dictionaryapi.dev/api/v2/entries/en/" + word)
                .header("Accept", "application/json")
                .retrieve()
                .bodyToMono(List.class)
                .block();

        return response;
    }

    private String serializeJson(List response) {
        LinkedHashMap<String, ArrayList> responseItem = (LinkedHashMap<String, ArrayList>) response.get(0);
        ArrayList meaning = responseItem.get("meanings");
        LinkedHashMap definitionsMap = (LinkedHashMap) meaning.get(0);
        ArrayList definitions = (ArrayList) definitionsMap.get("definitions");
        LinkedHashMap definitionMap = (LinkedHashMap) definitions.get(0);
        String result = definitionMap.get("definition").toString();

        return result;
    }
}
