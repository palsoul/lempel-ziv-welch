import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @author: Bauka23
 * @date: 03.10.2016
 */
public class Encoder {
    private static Map<String, Integer> dictionary;

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String text;
        try {
            text = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            text = "";
        }
        dictionary = DictionaryBuilder.build(text);
        FileWorker.write("encoded.txt", encode(text));
    }

    private static String encode(String text) {
        StringBuilder encodedText = new StringBuilder();
        String previous;
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            previous = buffer.toString();
            buffer.append(text.charAt(i));
            int count = 0;

            // Пробегаемся по словарю, если ключ соответствует, прерываем итератирование.
            for (Map.Entry<String, Integer> word : dictionary.entrySet()) {
                if (buffer.toString().equals(word.getKey())) break;
                count++;
            }

            // Если мы перерыли весь словарь, не найдя соответствующий ключ, добавляем текущий буфер в словарь...
            if (count == dictionary.size()) {
                dictionary.put(buffer.toString(), count);
                buffer = new StringBuilder().append(text.charAt(i));

                // ... и добавляем значение ключа предыдущего цикла.
                for (Map.Entry<String, Integer> word : dictionary.entrySet()) {
                    if (previous.equals(word.getKey())) {
                        encodedText.append(word.getValue()).append(" ");
                        break;
                    }
                }

                // Если мы достигли конца циклов, то добавляем код последнего ключа
                if (text.length() - 1 == i) {
                    for (Map.Entry<String, Integer> word : dictionary.entrySet()) {
                        if (String.valueOf(text.charAt(i)).equals(word.getKey())) {
                            encodedText.append(word.getValue());
                            break;
                        }
                    }
                }
            }
        }
        return encodedText.toString();
    }
}
