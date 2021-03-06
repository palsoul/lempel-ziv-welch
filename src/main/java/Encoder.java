import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

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

    // Old version, doesn't works!!!
    /*private static String encode(String text) {
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
                System.out.println(buffer.toString());

                // ... и добавляем значение ключа предыдущего цикла.
                for (Map.Entry<String, Integer> word : dictionary.entrySet()) {
                    if (previous.equals(word.getKey())) {
                        encodedText.append(word.getValue()).append('\n');
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
    }*/

    /**
     *
     * @param text  Text to encode
     * @return      encoded text
     */

    private static String encode(String text) {
        StringBuilder encodedText = new StringBuilder();
        StringBuilder tempSymbol = new StringBuilder(String.valueOf(text.charAt(0)));
        int tempCode;
        int prevCode = -1;
        int offset = 0;
        if (text.length() < 2) return "0";

        int i = 0;
        while (i + offset < text.length()) {
            if ((tempCode = search(tempSymbol.toString())) == -1) {
                dictionary.put(tempSymbol.toString(), dictionary.size());
                encodedText.append(prevCode).append('\n');
                tempSymbol = new StringBuilder(String.valueOf(text.charAt(i + offset)));
                i += offset;
                offset = 0;
            } else {
                prevCode = tempCode;
                offset++;
                if (i + offset < text.length())
                    tempSymbol.append(String.valueOf(text.charAt(i + offset)));
                else
                    encodedText.append(search(tempSymbol.toString()));
            }
        }
        return encodedText.toString();
    }

    /**
     *
     * @param s     The symbols to search from dictionary
     *
     * @return      Value of code of symbols from dictionary.
     *              If symbols don't exist in dictionary, return -1
     */
    private static int search(String s) {
        int code = -1;
        for (Map.Entry<String, Integer> word : dictionary.entrySet()) {
            if (s.equals(word.getKey())) {
                code = word.getValue();
                break;
            }
        }
        return code;
    }
}
