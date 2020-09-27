import java.util.Scanner;

import static java.lang.System.out;

public class MainClass {
    public static void main(String[] args) {
        String str;
        for(;;) {
            out.println("1 : Ввести строку");
            out.println("0 : Выход");

            out.println("Введите пункт:");
            Scanner in = new Scanner(System.in);
            int num = in.nextInt();
            switch(num){
                case 1:
                    out.println("\n\n");
                    Calculate calculate = new Calculate();
                    Scanner inn = new Scanner(System.in);
                    out.println("Введите строку :");
                    str=inn.nextLine();

                    str+='\0';
                    str = calculate.check(str);
                    out.println("Выражение : " + str);
                    out.println("Результат выражения: " + calculate.calculate(str));

                    out.println("\n\n");
                    break;

                case 0:
                    out.println("\n\n");
                    System.exit(0);
                    out.println("\n\n");
                    break;

                default:
                    out.println("\n\n");
                    out.println("Неизвестный ввод");
                    out.println("\n");
                    break;
            }
        }
    }
}
