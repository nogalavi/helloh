import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by nogalavi on 27/12/2016.
 */
public class CategoryFinder {

    private HashSet<String> ignoredWords;
    private String delimiters = "\\W";
    private HashMap<String, HashMap<String, Integer>> categoriesWordscount;

    public CategoryFinder(List<String[]> namesCategoriesTable, List<String> ignoredWords) {
        this.ignoredWords = createIgnoredWords(ignoredWords);
        this.categoriesWordscount = createCategoriesAndWords(namesCategoriesTable);
    }


    private HashSet<String> createIgnoredWords(List<String> ignoredWords) {
        HashSet<String> result = new HashSet<>();
        for (String word : ignoredWords) {
            result.add(word.toLowerCase());
        }
        return result;
    }

    private HashMap<String, HashMap<String, Integer>> createCategoriesAndWords(List<String[]> namesCategoriesTable) {
        HashMap<String, HashMap<String, Integer>> categoriesWordscount = new HashMap<>();

        for (int i = 0; i < namesCategoriesTable.size(); i++) {
            String name = namesCategoriesTable.get(i)[0];
            String category = namesCategoriesTable.get(i)[1];
            String[] wordsInName = name.split(delimiters);

            if (!categoriesWordscount.containsKey(category)) {
                categoriesWordscount.put(category, new HashMap<String, Integer>());
            }
            HashMap<String, Integer> wordscount = categoriesWordscount.get(category);

            for (String word : wordsInName) {
                String wordLowerCase = word.toLowerCase();
                boolean isIgnoredWord = this.ignoredWords.contains(wordLowerCase);
                boolean isAnEmptyWord = wordLowerCase.length() == 0; //Happens when splitting (HDL) Cholesterol, for example
                if (isIgnoredWord || isAnEmptyWord) {
                    continue;
                }
                if (!wordscount.containsKey(wordLowerCase)) {
                    wordscount.put(wordLowerCase, 1);
                } else {
                    wordscount.put(wordLowerCase, wordscount.get(wordLowerCase) + 1);
                }
            }
        }
        return categoriesWordscount;
    }


    public String findCategory(String testName) {
        String[] testnameWords = testName.split(delimiters);
        int maxScore = 0;
        String maxScoreCategory = "No category matches the test name";

        for (String category : this.categoriesWordscount.keySet()) {
            HashMap<String, Integer> categoryWordscount = this.categoriesWordscount.get(category);
            int categoryScore = 0;
            for (String word : testnameWords) {
                if (categoryWordscount.containsKey(word.toLowerCase())) {
                    categoryScore += categoryWordscount.get(word.toLowerCase());
                }
            }
            if (categoryScore > maxScore) {
                maxScore = categoryScore;
                maxScoreCategory = category;
            }
        }

        return maxScoreCategory;

    }

}
