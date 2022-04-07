package rdCalculator;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Calculator {

    private static Parser parser;
    private static RomanDecimalConverter converter;

    public static void main(String[] args) {
        converter = new RomanDecimalConverter();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
            // не было уточнения насчет цикла.. пусть будет
            // т.к. тут цикл останавливается только, если поймал исключение,
            // придется пробрасывать все исключения сюда - по условию, если исключение, то программа завершается
            while (true){
                parser = new Parser(converter);
                System.out.println("Для выхода введите \"q\"");
                String textInput = reader.readLine();
                if (textInput.equals("q")) break; // выход, если введено "q"
                String[] splitText = parser.findOperatorAndOperands(textInput); // делит текст на числа и оператор
                int preResult = calculate(splitText); // предварительный результат (если нужен ответ римскими цифрами, то нужно проверить соответствие условию)
                // вернуться может как int, так и String, поэтому тут Object (всеравно нужно тупо вывести в консоль)
                Object result = converter.convertResult(parser.getType(), preResult);
                System.out.println(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int calculate(String[] splitText) throws Exception {
        String operator = splitText[1];
        int operand_1 = parser.parseTxtOperandToInt(splitText[0]);
        int operand_2 = parser.parseTxtOperandToInt(splitText[2]);

        switch (operator){
            case ("+"): return operand_1 + operand_2;
            case ("-"): return operand_1 - operand_2;
            case ("*"): return operand_1 * operand_2;
            case ("/"): return operand_1 / operand_2; // на всякий случай, эту операцию не делаю по дефолту, по дефолту будет исключение
            default: throw new Exception("по идее, до этого исключения программа дойти не должна вообще никак");
        }
    }
}
