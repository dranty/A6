import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;
import java.util.Hashtable;


public class Words {
    public static void main(String[] args) throws FileNotFoundException {

        String cmdArg = args[0];
        String fileArg = args[1];
        String stopFile = args[2];

        if (cmdArg.equals("list")) {
            List<String> words = readFileWords(fileArg);
            Set<String> stopWordsSet = readStopWords(stopFile);
            Set<String> wordSet = convertToSet(words); // remove dupes
            Set<String> wordSetNoDupesNoStops = removeStopWords(wordSet, stopWordsSet); // removes stopwords
            List<String> wordSetNoDupesNoStopsList = convertToList(wordSetNoDupesNoStops); 
            Collections.sort(wordSetNoDupesNoStopsList); // organize alpha.
            System.out.println(wordSetNoDupesNoStopsList);
        }

        if (cmdArg.equals("freq")) {
            List<String> words = readFileWords(fileArg);
            Set<String> stopWordsSet = readStopWords(stopFile);
            List<String> stopWordsList = convertToList(stopWordsSet);

            List<String> wordListNoStops = removeStopWordsList(words, stopWordsList); // removes stopwords
            wordFrequency(wordListNoStops); 
            wordFrequencyPrinter(wordFrequency(wordListNoStops));
        }

        if (cmdArg.equals("list") == false && cmdArg.equals("freq") == false) {
            System.out.println("Please input valid arguments.");
            System.out.println("Usage: (freq or list) File StopWordFile.");
        }

    }

    private static List<String> readFileWords(String Filename) throws FileNotFoundException {
        List<String> words = new ArrayList<String>();
            if (Filename.equals("test") == true) { // debug
                    words.add("apple");
                    words.add("bannana");
                    words.add("corn");
                        return words;
                } else {
            try (Scanner scan = new Scanner(new File(Filename))) {
                while (scan.hasNext()) {
                    words.add(scan.next());
            }
        }
            return words;
        }
    }

    private static Set<String> readStopWords(String stopWordsFile) throws FileNotFoundException {
        Set<String> stopWords = new HashSet<String>();
            if (stopWordsFile.equals("test") == true) { // debug
                stopWords.add("apple");
                stopWords.add("burrito");
                stopWords.add("cobble");
                    return stopWords;
                } else {
            try (Scanner scan = new Scanner(new File(stopWordsFile))) {
                while (scan.hasNext()) {
                    stopWords.add(scan.next());
            }
        }
            return stopWords;
        }
    }

    private static Set<String> convertToSet(List<String> words) { // removes duped entries.
        Set<String> wordSet = new HashSet<String>();
        for (int i = 0; i < words.size(); i++) {
            wordSet.add(words.get(i));
        }
        return wordSet;
    }

    private static Set<String> removeStopWords(Set<String> words, Set<String> stopWords) {
        words.removeAll(stopWords);
            return words;
        }

    private static List<String> removeStopWordsList(List<String> words, List<String> stopWords) {
        words.removeAll(stopWords);
            return words;
        }

    private static List<String> convertToList(Set<String> words) {
        List<String> wordList = new ArrayList<String>();
        for (String word : words) {
            wordList.add(word);
        }
        return wordList;
    }

    private static Hashtable<String, Integer> wordFrequency(List<String> wordList) {
        Hashtable<String, Integer> frequencyCounter = new Hashtable<String, Integer>();
            for (int i = 0; i < wordList.size(); i++) {
               if (frequencyCounter.containsKey(wordList.get(i))) {
                    frequencyCounter.put(wordList.get(i), frequencyCounter.get(wordList.get(i)) + 1);
                } else {
                    frequencyCounter.put(wordList.get(i), 1);
               }
            }
            return frequencyCounter;
        }

    private static void wordFrequencyPrinter(Hashtable<String, Integer> wordFrequency) {
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
            entryList.add(entry);
        }

        Collections.sort(entryList, (entry1, entry2) -> {
            int freqCompare = entry1.getValue().compareTo(entry2.getValue());
            if (freqCompare != 0) {
                return freqCompare;
            } else {
                return entry1.getKey().compareTo(entry2.getKey());
            }
        });

        for (Map.Entry<String, Integer> entry : entryList) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
    