import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author: Bauka23
 * @date: 03.10.2016
 */
public class DictionaryBuilder {
    public static Map<String, Integer> build(String text) {
        Map<String, Integer> dictionary = new HashMap<String, Integer>();
        StringBuilder sb = new StringBuilder();
        int code = 0;
        for (int i = 0; i < text.length(); i++) {
            Set<Map.Entry<String , Integer>> set = dictionary.entrySet();
            int count = 0;
            String temp = String.valueOf(text.charAt(i));
            for (Map.Entry<String, Integer> word : set) {
                if (word.getKey().equals(temp)) break;
                count++;
            }
            if (count == dictionary.size()) {
                dictionary.put(String.valueOf(text.charAt(i)), code);
                sb.append(text.charAt(i)).append(code).append('\n');
                code++;
            }

        }
        FileWorker.write("dictionary.txt", sb.toString());
        return dictionary;
    }
}
