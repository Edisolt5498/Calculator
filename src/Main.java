import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    static String[] BookRoman = {"O", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV",
            "XV", "XVI", "XVII", "XVIII", "XIX", "XX", "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI", "XXVII", "XXVIII", "XXIX",
            "XXX", "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI", "XXXVII", "XXXVIII", "XXXIX", "XL", "XLI", "XLII",
            "XLIII", "XLIV", "XLV", "XLVI", "XLVII", "XLVIII", "XLIX", "L", "LI", "LII", "LIII", "LIV", "LV", "LVI", "LVII", "LVIII",
            "LIX", "LX", "LXI", "LXII", "LXIII", "LXIV", "LXV", "LXVI", "LXVII", "LXVIII", "LXIX", "LXX", "LXXI", "LXXII", "LXXIII",
            "LXXIV", "LXXV", "LXXVI", "LXXVII", "LXXVIII", "LXXIX", "LXXX", "LXXXI", "LXXXII", "LXXXIII", "LXXXIV", "LXXXV", "LXXXVI",
            "LXXXVII", "LXXXVIII", "LXXXIX", "XC", "XCI", "XCII", "XCIII", "XCIV", "XCV", "XCVI", "XCVII", "XCVIII", "XCIX", "C"
    };

    public static void main(String[] args) throws InputMismatchException {

        Scanner in = new Scanner(System.in);
        System.out.println("Можно и нужно ввести выражение из двух арабских чисел от 0 до 10 либо римских чисел от 0 до X. Разрешенны операции:");
        System.out.println("деления /\nумножения *\nсложения +\nвычитания -\nК примеру 4 + 8 или III * IX");

        System.out.print("Input:");

        String InputUser = in.nextLine().replaceAll(" ", "").trim();    // строка без пробелов

        try {
            System.out.println("Получается " + calc(InputUser));
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
            System.out.println();
        } catch (ArithmeticException a){
            System.out.println("Тут явное деление на ноль...Неправильный ввод");
        }

    }

    public static String calc(String InputUser) {

        for (int i = 0; i < InputUser.length(); i++) {

            if ((InputUser.charAt(i)) == '+' || (InputUser.charAt(i)) == '-' || (InputUser.charAt(i)) == '*' || (InputUser.charAt(i)) == '/') {

                char operator = InputUser.charAt(i); // проверка на наличие знака и сохранение знака и потенциальных чисел
                String num1 = InputUser.substring(0, i);
                String num2 = InputUser.substring(i + 1, InputUser.length());

                if ((checker(new String[]{num1, num2}, BookRoman)) == 1) { // проверка на арабские и на ошибки (return = 1 - араб. числа; 2 - рим. числа;
                    return ArabCalc(new String[]{num1, num2}, operator); // калькулятор для арабских чисел               3 - разные; 4 - ни то ни другое)
                } else if ((checker(new String[]{num1, num2}, BookRoman)) == 2) {
                    return RomanCalc(new String[]{num1, num2}, BookRoman, operator); // калькулятор для римских чисел
                } else if ((checker(new String[]{num1, num2}, BookRoman)) == 3) {
                    throw new InputMismatchException("Тут слишком разное, давай как я просил...Неправильный ввод..."); // ловим 3 и 4 ошибки
                } else {
                    throw new InputMismatchException ("Мне кажется, или тут нет никаких чисел...или я не знаю...Неправильный ввод...");
                }

            }

        }

        throw new InputMismatchException("Где знак?...Неправильный ввод..."); //ГДЕ ОН?. ловим отсутствие знака

    }

    public static int checker(String[] numbs, String[] Book) {

        byte check = 0;
        //boolean Arab = false;

        for (int i = 0; i < 2; i++) { // недопустить разные системы счета

            if        ((int)(numbs[i].charAt(0)) >= 48 && (int)(numbs[i].charAt(0)) <= 57 && numbs[i].length() == 1 || numbs[i].contentEquals("10"))  {
                if (check == 2) {
                    check = 3;
                } else {
                    check = 1;
                }
            }
            else if (isThereRoman(numbs[i], Book)) {
                if (check == 1) {
                    check = 3;
                } else {
                    check = 2;
                }
            }
            else {
                return 4; //check = 4 значит ошибку в вводе
            }

        }

        return check;

    }

    public static String RomanCalc(String[] numbs, String[] Book, char operator) {

        int sum = 0;
        int num1 = 0;
        int num2 = 0;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j <= 10; j++) {
                if (numbs[i].contentEquals(Book[j])) {
                    switch (i) {
                        case 0:
                            num1 = j;
                        case 1:
                            num2 = j;
                    }
                }
            }
        }

        switch (operator) {
            case '+' -> sum = num1 + num2;
            case '-' -> sum = num1 - num2;
            case '*' -> sum = num1 * num2;
            case '/' -> sum = num1 / num2;
        }

        if (sum < 0) {
            throw new InputMismatchException("Впринципе ответ " + sum + " БЫЛ БЫ, но в римских ответ дать не смогу. Неправильный ввод...");
        }

        return Book[sum];
    }

    public static String ArabCalc(String[] numbs, char operator) {

        int num1 = Integer.parseInt(numbs[0]);
        int num2 = Integer.parseInt(numbs[1]);
        int sum = 0;

        switch (operator) {
            case '+' -> sum = num1 + num2;
            case '-' -> sum = num1 - num2;
            case '*' -> sum = num1 * num2;
            case '/' -> sum = num1 / num2;
        }

        return Integer.toString(sum);

    }

    public static boolean isThereRoman(String Input, String[] Book){
        for (int i = 0; i <= 10; i++) {
            if (Input.contains(Book[i])) {
                return true;
            }
        }
        return false;
    }

}

