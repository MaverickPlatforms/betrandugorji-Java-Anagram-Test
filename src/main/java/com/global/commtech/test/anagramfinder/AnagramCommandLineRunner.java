package com.global.commtech.test.anagramfinder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@RequiredArgsConstructor
public class AnagramCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(final String... args) throws Exception {
        try {
            Assert.isTrue(args.length == 1, "Please ensure that the input file is provided");

            final File file = new File(args[0]);
            Assert.isTrue(file.exists(), args[0] + " Does not exist");

            // Read all words into a List of strings
            final List<String> words = Files.readAllLines(file.toPath());

            // A Key Value Map to hold a changing set of results in a collection
            final Map<String, Set<String>> map = new HashMap<>();

            // Pass transient Set of results in a collection that avoids duplicates
            words.stream().forEach((var item) -> {
                process(map, new HashSet<>(), words, item);
            });

            // Print the joined values to output for tests CapturedOutput
            map.forEach((k, v) -> System.out.println(String.join(",", v)));

        } catch (IOException e) {
            System.out.println("Exception @AnagramCommandLineRunner process: " + e); //Use a Logger in production and throw relevant Exception
        }
    }

    /**
     * Stream through the collection and put the isAnagram result set into the
     * map of results
     */
    private Map<String, Set<String>> process(Map<String, Set<String>> map, Set<String> set, List<String> words, String word) {
        words.stream()
                .filter(j -> (isAnagram(word, j)))
                .map(j -> {
                    set.add(j);
                    return j;
                }).forEachOrdered(_item -> {
        });

        //Make the set result the key to avoid duplicates
        map.putIfAbsent(String.join("", set), set);
        return map;
    }
    
    /**
     * Return true if firstWord and secondWord are Anagrams 
     * Reuse size so that storage cost is O(1) which is a better solution
     */
    private boolean isAnagram(String firstWord, String secondWord) {

        if (firstWord.length() != secondWord.length()) {
            return false;
        }

        int[] size = new int[26];

        for (int i = 0; i < firstWord.toCharArray().length; i++) {
            size[Character.toLowerCase(firstWord.toCharArray()[i]) - 'a']++;
        }

        for (int i = 0; i < secondWord.toCharArray().length; i++) {
            size[Character.toLowerCase(secondWord.toCharArray()[i]) - 'a']--;
        }

        for (int i = 0; i < size.length; i++) {
            if (size[i] != 0) {
                return false;
            }
        }
        
        return true;
    }

}
