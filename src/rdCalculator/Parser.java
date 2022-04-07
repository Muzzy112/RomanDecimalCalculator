package rdCalculator;

public class Parser {

    private Type type;
    private RomanDecimalConverter converter;

    public Parser(RomanDecimalConverter converter) {
        this.type = null;
        this.converter = converter;
    }

    protected void setType(Type type) {
        this.type = type;
    }

    protected Type getType() {
        return type;
    }

    // нахожу оператор и операнды (не делю по пробелам, т.к. нет точной информации об их обязательном наличии)
    protected String[] findOperatorAndOperands(String textInput) throws Exception {
        textInput = textInput.toUpperCase().replaceAll(" ", ""); // хз что делать с пробелами и регистром, поэтому вот
        Validator.preValidate(textInput); // предварительная валидация, чтобы можно было спокойно найти оператор
        return textInput.split("(?<=\\W)|(?=\\W)"); // делю строку по НЕ цифробуквенному символу включительно
    }

    protected int parseTxtOperandToInt(String txtOperand) throws Exception {
        int val;
        // проверка по первому символу строки - десятичное или нет
        if (Character.isDigit(txtOperand.charAt(0))){
            val = Integer.parseInt(txtOperand);
            Validator.checkType(this, Type.DECIMAL); // проверка на тип значения
        }
        // если первый символ - не число, то скорее всего это римские цифры (превалидация должна была пропустить только их)
        else {
            val = converter.romanToDecimalVariantB(txtOperand);
            Validator.checkType(this, Type.ROMAN); // проверка на тип значения, если не не совпадают - исключение
        }
        Validator.isValueInRangeFromOneToTen(val); // проверка на соответсвие условию (1-10), если нет - исключение
        return val;
    }
}
