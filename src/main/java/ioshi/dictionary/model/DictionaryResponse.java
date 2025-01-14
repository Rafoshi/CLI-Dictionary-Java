package ioshi.dictionary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(value = {"phonetics", "meanings"})
public class DictionaryResponse {
    private String word;
    private String phonetic;
    private List<Phonetic> phonetics;
    private List<Meaning> meanings;

    @JsonProperty("word")
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @JsonProperty("phonetic")
    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    // No need to annotate phonetics and meanings with @JsonIgnore anymore

    @Override
    public String toString() {
        return "Word: " + word + ", Phonetic: " + phonetic;
    }
}

class Phonetic {
    private String text;
    private String audio;
    private String sourceUrl;
    private License license;

    // Getters and Setters

    @Override
    public String toString() {
        return "Phonetic: " + text + ", Audio: " + audio;
    }
}

class Meaning {
    private String partOfSpeech;
    private List<Definition> definitions;

    // Getters and Setters

    @Override
    public String toString() {
        return "Part of Speech: " + partOfSpeech + ", Definitions: " + definitions;
    }
}

class Definition {
    private String definition;
    private List<String> synonyms;
    private List<String> antonyms;

    // Getters and Setters

    @Override
    public String toString() {
        return definition;
    }
}

class License {
    private String name;
    private String url;

    // Getters and Setters

    @Override
    public String toString() {
        return name + " (" + url + ")";
    }
}