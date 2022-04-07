package rdCalculator;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RomanDecimalConverter {

    TreeMap<Integer, String> decimalRomanKeys; // для конвертации в римские числа
    Map<String, Integer> romanDecimalKeys; // для конвертации в десятичные числа

    // на основе этих массивов будут заполняться TreeMap (String,Integer и Integer,String) для конвертации
    // десятичные числа -> римские числа и римские -> десятичные
    private final int[] decimalKeys = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private final String[] romeKeys = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    protected RomanDecimalConverter() {
        // при конвертации в римские числа нужен TreeMap, т.к. будет использоваться метод floorKey()
        this.decimalRomanKeys = IntStream.range(0, decimalKeys.length).boxed()
                .collect(Collectors.toMap(k -> decimalKeys[k], v -> romeKeys[v], (oldVal, newVal) -> oldVal, TreeMap::new));
        // для конвертации в десятичные числа HashMap - норм
        this.romanDecimalKeys = IntStream.range(0, romeKeys.length).boxed()
                .collect(Collectors.toMap(k -> romeKeys[k], v -> decimalKeys[v]));
    }

    // конвертирует десятичное число в римское
    // рекурсивно ищет самый близкий десятичный ключ, который меньше ткущего числа, отнимает его и добавляет к строке соответствующее значение
    private String decimalToRoman(int decimalOperand) {
        int temp =  decimalRomanKeys.floorKey(decimalOperand);
        if ( decimalOperand == temp ) {
            return decimalRomanKeys.get(decimalOperand);
        }
        return decimalRomanKeys.get(temp) + decimalToRoman(decimalOperand-temp);
    }

    // это самый простой и быстрый способ конвертации римских цифр в десятичные для данной задачи,
    // потому что конвертация римские -> десятичные испльзуется только один раз для входящих данных
    // и точно известно, что входящие значения будут в диапозоне от 1 до 10
    protected int romanToDecimal(String romanOperand) throws Exception{
        Map<String, Integer> romanValuez = new HashMap<>();
        romanValuez.put("I", 1);    romanValuez.put("II", 2);
        romanValuez.put("III", 3);  romanValuez.put("IV", 4);
        romanValuez.put("V", 5);    romanValuez.put("VI", 6);
        romanValuez.put("VII", 7);  romanValuez.put("VIII", 8);
        romanValuez.put("IX", 9);   romanValuez.put("X", 10);
        try {
            return romanValuez.get(romanOperand); // если такого ключа нет - NullPointerException
        } catch (NullPointerException e) {
            throw new Exception("Римское число должно быть от I до X: " + romanOperand);
        }
    }

    // на всякий случай метод для нормальной конвертации римских чисел
    protected int romanToDecimalVariantB(String romanOperand)  throws Exception{
        Validator.isRomanOperandCorrect(romanOperand); // проверка римского числа на корректность, если нет - исключение
        // разбираю строку на римские числа по шаблону (шаблон от большего числа к меньшему, чтобы небыло проблем с распознованием (например "IX" как 11, а не 9 )
        Matcher matcher = Pattern.compile("M|CM|D|CD|C|XC|L|XL|X|IX|V|IV|I").matcher(romanOperand);
        int result = 0;
        while (matcher.find())
            result += romanDecimalKeys.get(matcher.group(0));
        return result;
    }

    protected Object convertResult(Type type, int result) throws Exception {
        // если тип результата должен быть десятичным - ничего не делает, возвращает это же число
        if (type == Type.DECIMAL) {
            return result;
        }
        // если не дясятичный
        else {
            // число проверяется на положительность
            if (Validator.isRomanResultPositive(result)) {
                return decimalToRoman(result); // конвертитируется и возвращается
            }
            else throw new Exception("Римское число должно быть положительным"); // если число меньше 1 - исключение
        }
    }
}
