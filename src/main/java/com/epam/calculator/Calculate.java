import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * class that calculate expression
 */
public class Calculate {
    private char ch;
    Stack<Double> numbers = new Stack<>();
    Stack<Character> operators = new Stack<>();
    private int i_str =0;
    private String initialRow;


    private double doubleNum;
    private String full="";     // целая часть числа
    private String tenths="";   // нецелая часть числа


    /**
     * method that calculate expression
     * @param str
     * @return result of expression
     */
    public double calculate(String str) {
        double result;
        i_str=0;
        String Flag = "number";
        while (str.charAt(i_str)!='\0'){    // выполняем * и /, доходя до конца строки
            ch=str.charAt(i_str);
            if(ch==' ') i_str++;
            ch=str.charAt(i_str);
            if (Flag.equals("number")) {          // значит, число
                numbers.push(toDouble(str));
                if (!operators.empty()) {
                    if (operators.peek() == '*') multiplyNum(numbers.pop(), numbers.pop());
                    else if (operators.peek() == '/') divideNum(numbers.pop(), numbers.pop());
                }
                Flag="operator";
            }
            else  {                  // значит оператоp
                operators.push(ch);
                i_str++;
                Flag="number";
            }
        }

        while (!operators.empty()){     // выполняем все оставшиеся операторы + и -
            if (operators.peek()=='+') addNum(numbers.pop(), numbers.pop());
            else if (operators.peek()=='-') subtrNum(numbers.pop(), numbers.pop());
        }
        result = (double) Math.round(numbers.pop() * 100) / 100;
        return result;
    }

    /**
     *checking for the correct string or taking the correct substring
     * @param str
     * @return
     */
    public String check (String str){
        int y;
        initialRow = str;
        str = str.replaceAll("( {2} *)", " ");  //////////////////////////////////////////////////////////////////
        str = str.replaceAll("[^0-9\\-\\+\\/\\*\\ sqrt.]", "");  // удаляем плохие символы

        /* добавляем пробелы, где не хватает */
        StringBuffer  strBuild = new StringBuffer(str);
        String regex1 = "(((\\d+)(\\/|\\-|\\*|\\+)))";
        Pattern pattern = Pattern.compile( regex1 );
        Matcher matcher = pattern.matcher(str);
        int addInd = 0;
        while (matcher.find()){
            y=matcher.end()+addInd;
            strBuild.insert(y-1, ' ');
            addInd++;
        }
        str = strBuild.toString();
        regex1 = "(?=((\\/|\\-|\\*|\\+)(\\d+|(sqrt))))";
        pattern = Pattern.compile( regex1 );
        matcher = pattern.matcher(str);
        addInd = 0;
        while (matcher.find()){
            y=matcher.start()+addInd;
            strBuild.insert(y+1, ' ');
            addInd++;
        }
        str = strBuild.toString();

        /* приводим к правильному виду */
        String regex2 = "(((\\d+.\\d+)|[\\/\\+\\*\\-]|\\d+|sqrt\\d+) )|(\\d+|sqrt\\d+)";
        pattern = Pattern.compile( regex2 );
        matcher = pattern.matcher(str);
        str = "";
        while (matcher.find()){
            str += matcher.group(0);
        }

        String regex3 = "((^(- ))?(\\d+ \\D |sqrt\\d+ \\D |\\d+.\\d+ \\D )((sqrt\\d+|\\d+)$)?)|((^(- ))?(\\d+|sqrt\\d+))$";
        pattern = Pattern.compile( regex3 );
        matcher = pattern.matcher(str);
        str = "";
        while (matcher.find()){
            str += matcher.group(0);
        }
        str+='\0';
        if (!str.equals(initialRow)) System.out.println("Возможно, вы ввели неправильное выражение");

        return str;
    }

    /**
     * method that converts a string number to double number
     * @param x String with expression
     * @return double number
     */
    public double toDouble(String x){
        if (x.charAt(i_str)=='s'){  // if sqrt
            doubleNum = sqrtNum(x);
        }
        else if(x.charAt(i_str)=='-'){
            doubleNum = 0;
        }
        else {                  // if number
            doubleNum = numberToDouble (x);
        }
        return (double) Math.round(doubleNum * 100) / 100;
    }


    /**
     *
     * @param x
     * @return double number
     */
    public double numberToDouble( String x ){
        while (x.charAt(i_str)!=' ' & x.charAt(i_str)!='\0'){
            if (x.charAt(i_str)=='.'){  // counts the fractional part
                i_str++;
                while (x.charAt(i_str)!='\0' & x.charAt(i_str)!=' '){
                    tenths +=x.charAt(i_str);
                    i_str++;
                }
            }
            else {  // count the whole part
                full+=x.charAt(i_str);
                i_str++;
            }
        }
        doubleNum = Long.valueOf(full);
        if (tenths!=""){
            double intTenths = Integer.valueOf(tenths);
            doubleNum += intTenths/pow(10,tenths.length());
        }
        full="";
        tenths="";
        return doubleNum;
    }


    /**
     * multiply numbers
     * @param x
     * @param y
     */
    public void multiplyNum(double x, double y){
        numbers.push(x*y);
        operators.pop();
    }


    /**
     * sqrt(number)
     * @param x
     * @return result of sqrt(number)
     */
    public double sqrtNum(String x){
        i_str +=4;
        double sqrtN = numberToDouble(x);
        sqrtN=sqrt(sqrtN);
        return (double) Math.round(sqrtN * 100) / 100;
    }


    /**
     * addition numbers
     * @param x
     * @param y
     */
    public void addNum(double x, double y){
        operators.pop();
        if (operators.empty())numbers.push(y+x);
        else if (operators.peek()=='+' )numbers.push(y+x);
        else {
            numbers.push(x-y);
            operators.pop();
            operators.push('+');
        }
    }


    /**
     * divide numbers
     * @param x
     * @param y
     */
    public void divideNum(double x, double y){
        if (x==0) {
            System.out.println("ERROR : деление на ноль");
            System.exit(0);
        }
        else numbers.push(y/x);
        operators.pop();
    }


    /**
     * subtraction numbers
     * @param x
     * @param y
     */
    public void subtrNum(double x, double y){
        operators.pop();
        if (operators.empty() ) numbers.push(y-x);
        else if (operators.peek()=='+')numbers.push(y-x);
        else if (operators.peek()=='-') numbers.push(y+x);
    }
}
