import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WordleBot {

    private Set<String> wordleLA;
    private Set<String> wordleTA;
    private Set<String> possibleWords;
    private String lastGuess;
    private List<Set<Character>> knownInfo;
    //private StringBuilder knownPattern;
    //private List<Character> yellows;
    //private Set<Character> blacks;
    private final int GREEN_POINTS = 5;
    private final int YELLOW_POINTS = 2;
    private final char GREEN_CHAR = 'g';
    private final char YELLOW_CHAR = 'y';
    private final char BLACK_CHAR = 'b';

    public WordleBot(String laFile, String taFile) throws FileNotFoundException {

        wordleLA = new HashSet<>();
        wordleTA = new HashSet<>();
        possibleWords = new HashSet<>();

        //knownPattern = new StringBuilder("-----");
        //yellows = new ArrayList<>();
        //blacks = new HashSet<>();
        knownInfo = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Set<Character> allLetters = new HashSet<>();
            for (int j = 0; j < 26; j++) {
                allLetters.add((char) ('a' + j));
            }
            knownInfo.add(allLetters);
        }

        Scanner scanner = new Scanner(new File(laFile));
        while (scanner.hasNext()) {
            String toAdd = scanner.next();
            wordleLA.add(toAdd);
            wordleTA.add(toAdd);
            possibleWords.add(toAdd);
        }

        scanner = new Scanner(new File(taFile));
        while (scanner.hasNext()) wordleTA.add(scanner.next());

    }

    public String submit(String pattern) {

        if (pattern != null) {
            for (int i = 0; i < pattern.length(); i++) {
                if (pattern.charAt(i) == GREEN_CHAR) makeGreenSet(knownInfo.get(i), lastGuess.charAt(i));
                else if (pattern.charAt(i) == YELLOW_CHAR) knownInfo.get(i).remove(lastGuess.charAt(i));
                else removeCharFromKnownInfo(lastGuess.charAt(i));
            }
            filterPossibleWords(pattern);
        }

        //System.out.println("Possible words: " + possibleWords.size());

        return determineBestWord();
    }

    private String determineBestWord() {

        int max = 0;
        String bestWord = null;

        for (String word : wordleTA) {
            int totalPoints = 0;
            for (String actual : possibleWords) {
                totalPoints += getPoints(word, actual);
            }
            if (totalPoints > max) {
                max = totalPoints;
                bestWord = word;
            }
        }

        lastGuess = bestWord;
        return bestWord;
    }

    private int getPoints(String guess, String actual) {

        String pattern = getPattern(guess, actual);
        int total = 0;
        for (Character c : pattern.toCharArray()) {
            if (c == GREEN_CHAR) total += GREEN_POINTS;
            else if (c == YELLOW_CHAR) total += YELLOW_POINTS;
        }

        return total;
    }

    public String getPattern(String guess, String actual) {

        StringBuilder pattern = new StringBuilder("-----");

        // Set greens and blacks
        for (int i = 0; i < guess.length(); i++) {

            // If the characters match, it's green
            if (guess.charAt(i) == actual.charAt(i)) pattern.setCharAt(i, GREEN_CHAR);

            // If this character isn't in the word, it's black
            else if (!actual.contains(String.valueOf(guess.charAt(i)))) pattern.setCharAt(i, BLACK_CHAR);
        }

        // Set yellows
        for (int i = 0; i < guess.length(); i++) {
            if (pattern.charAt(i) != '-') continue;

            int instances = countBlankInstances(guess, actual, guess.charAt(i), pattern.toString());
            for (int j = 0; j < guess.length(); j++) {
                if (pattern.charAt(j) == '-' && guess.charAt(i) == guess.charAt(j)) {
                    if (instances > 0) {
                        pattern.setCharAt(j, YELLOW_CHAR);
                        instances--;
                    } else break;
                }
            }
        }

        for (int i = 0; i < guess.length(); i++) {
            if (pattern.charAt(i) == '-') pattern.setCharAt(i, BLACK_CHAR);
        }

        return pattern.toString();
    }

    private int countBlankInstances(String guess, String actual, char ch, String pattern) {

        int instances = 0;
        for (int i = 0; i < actual.length(); i++) {
            if (actual.charAt(i) == ch) {
                instances++;
            }
        }

        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == ch) {
                if (pattern.charAt(i) == GREEN_CHAR || pattern.charAt(i) == YELLOW_CHAR) instances--;
            }
        }

        if (instances < 0) instances = 0;
        return instances;
    }

    private void filterPossibleWords(String pattern) {

        Set<String> newWords = new HashSet<>();
        for (String s : possibleWords) {
            if (wordMatchesKnownInfo(s)) newWords.add(s);
        }

        possibleWords = newWords;
    }

    private boolean wordMatchesKnownInfo(String word) {

        for (int i = 0; i < word.length(); i++) {
            if (!knownInfo.get(i).contains(word.charAt(i))) return false;
        }

        /*for (int i = 0; i < word.length(); i++) {
            if (knownPattern.charAt(i) != '-' && word.charAt(i) != knownPattern.charAt(i)) return false;
        }

        for (Character c : yellows) {
            if (!word.contains(String.valueOf(c))) return false;
        }

        for (Character c : blacks) {
            if (word.contains(String.valueOf(c))) return false;
        }*/

        return true;
    }

    public boolean isSolved() {
        for (Set<Character> set : knownInfo) if (set.size() != 1) return false;
        return true;
    }

    /*private boolean patternMatchesWord(String pattern, String guess, String actual) {

        // Validate greens and blacks first for efficiency
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == GREEN_CHAR) {
                if (guess.charAt(i) != actual.charAt(i)) return false;
            } else if (pattern.charAt(i) == BLACK_CHAR) {
                if (actual.contains(String.valueOf(guess.charAt(i)))) return false;
            } else { // yellow
                if (countBlankInstances(guess, actual, guess.charAt(i), pattern) != 0) return false;
            }
        }

        return true;
    }*/

    private void makeGreenSet(Set<Character> set, char ch) {
        for (int i = 0; i < 26; i++) {
            char c = (char) ('a' + i);
            if (c != ch) set.remove(c);
        }
    }

    private void removeCharFromKnownInfo(char ch) {
        for (Set<Character> set : knownInfo) {
            set.remove(ch);
        }
    }

}
