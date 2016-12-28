import java.util.ArrayList;
import java.util.List;

/**
 * Created by nogalavi on 27/12/2016.
 */
public class UsageExample {
    public static void main(String[] args) {
        // Create name and category "table"
        List<String[]> NameAndCategory = new ArrayList<>();
        String[] first = {"Total HDL Cholesterol", "HDL Cholesterol"};
        String[] second = {"(HDL), Cholesterol", "HDL Cholesterol"};
        String[] third = {"HM Hemoglobin", "A1C"};
        NameAndCategory.add(first);
        NameAndCategory.add(second);
        NameAndCategory.add(third);

        // Create ignored words list
        List<String> ignoredWords = new ArrayList<>();
        ignoredWords.add("Level");
        ignoredWords.add("Total");
        ignoredWords.add("Blood");

        // Create a Category finder and search the relevant category
        CategoryFinder cf = new CategoryFinder(NameAndCategory, ignoredWords);
        System.out.println(cf.findCategory("(Cholesterol), (),-:/ HDL"));

    }
}
