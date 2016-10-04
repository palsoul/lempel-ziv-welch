import java.io.FileNotFoundException;
import java.util.Map;

/**
 * @author: Bauka23
 * @date: 03.10.2016
 */
public class Decoder {
    private static Map<String, Integer> dictionary;

    public static void main(String[] args) {
        try {
            FileWorker.write("decoded.txt", decode(FileWorker.read("encoded.txt").split("\n")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Input file doesn't exist");
        }
    }

    private static String decode(String[] encoded) {
        int[] codes = new int[encoded.length];
        for (int i = 0; i < codes.length; i++) {
            codes[i] = Integer.parseInt(encoded[i]);
        }
        StringBuilder decoded = new StringBuilder();

        // Получаем словарь
        try {
            dictionary = DictionaryBuilder.get("dictionary.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Dictionary file doesn't exist");
        }
        StringBuilder buffer = new StringBuilder();

        // Добавляем первую букву в декодированный текст
        for (Map.Entry<String, Integer> word : dictionary.entrySet()) {
            if (word.getValue() == codes[0]) {
                decoded.append(word.getKey());
                buffer.append(word.getKey());
                break;
            }
        }

        // Добавляем остальное
        for (int i = 1; i < codes.length; i++) {
            for (Map.Entry<String, Integer> word : dictionary.entrySet()) {
                if (word.getValue() == codes[i]) {
                    decoded.append(word.getKey());
                    buffer.append(word.getKey().charAt(0));
                    dictionary.put(buffer.toString(), dictionary.size());
                    buffer = new StringBuilder(word.getKey());
                    break;
                }
            }

        }

        System.out.println(decoded);
        return decoded.toString();
    }
}
