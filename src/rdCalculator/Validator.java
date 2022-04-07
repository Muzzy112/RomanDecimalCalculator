package rdCalculator;

public class Validator {

    protected static void preValidate(String textInput) throws Exception {
        // не уверен насчет пробелов, в примере они есть, но в задании конкретики нет
        // входящие значения не больше 10(X), но на всякий случай проверяю по всем римским цифрам
        // можно было бы и проверку на соответствие типов (арабские\римские) здесь же сделать.. хотя наверное, лучше использовать enum
        if (!textInput.matches("^(\\d+|[IVXLCDM]+) *[+*/-] *(\\d+|[IVXLCDM]+)$")) {
            throw new Exception("Некорректный запрос: " + textInput);
        }
    }

    // проверка на соответствие типов
    // если текущий тип не null, значит он должен соответствовать предлагаемому типу
    protected static void checkType(Parser parser, Type assumedType) throws Exception {
        Type currentType = parser.getType();
        if ((currentType != null) && (currentType != assumedType)) {
            throw new Exception(String.format("Разные типы операндов: %s и %s", currentType.name(), assumedType.name()));
        }
        parser.setType(assumedType);
    }

    // соответствует ли операнд условию (от 1 до 10)
    protected static void isValueInRangeFromOneToTen(int operand) throws Exception {
        if (operand < 1 || operand > 10) throw new Exception("Число меньше 1 или больше 10: " + operand);
    }

    // проверка римского результата, положительный он или нет
    protected static boolean isRomanResultPositive (int romanResult) {
        return romanResult > 0;
    }

    // проверка корректности римского операнда
    protected static void isRomanOperandCorrect(String romanOperand) throws Exception{
        if (!romanOperand.matches("^(M{0,3})(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$")) {
            throw new Exception("Некорректное римское чило: " + romanOperand);
        }
    }
}
