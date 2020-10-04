package com.enotessa.calculator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculateTest {
    private final static String VALID_ROW1 = "2\0";
    private final static String VALID_ROW2 = "- 1.2 - 2 - 2 - sqrt2 - 2 + 2 / 2 + 2\0";

    private final static String INVALID_ROW1 = "*2";
    private final static String INVALID_ROW2 = "+- 1.2 - 2 - t2 /-  2  sqrt2 -2 *+ 2 / 2 + 2";
    private final static String EXPRESSION_EVALUATION1 ="2 + 3 * 45.3 * 90 + 20 - 8 / 20 - sqrt4\0";
    private final static String EXPRESSION_EVALUATION2 ="3\0";
    private final static String ROW_IN_STRING = "45.3 ";

    private final static double VALID_RESULT1 = 12250.6;
    private final static double VALID_RESULT2 = 3;
    private final static double ROW_IN_DOUBLE =45.3;
    private final static double VALID_RESULT3 = 3510;
    private final static double VALID_RESULT4 = 123;
    private final static double VALID_RESULT5 = -10;
    private final static double VALID_RESULT6 = 25;

    private final static double VALUE_X1 = 45;
    private final static double VALUE_X2 = 45;
    private final static double VALUE_X3 = 2;
    private final static double VALUE_X4 = 0;
    private final static double VALUE_X5 = 12;
    private final static double VALUE_Y1 = 78;
    private final static double VALUE_Y2 = 78;
    private final static double VALUE_Y3 = 50;
    private final static double VALUE_Y4 = 50;
    private final static double VALUE_Y5 = 2;
    private Calculate calculate;
    private double resultOfOperation;


    @Before
    public void start(){
        calculate = new Calculate();
    }

    @Test
    public void method_that_validates_the_string_to_calculate() {
        String checkValue = calculate.check(INVALID_ROW1);
        assertEquals(VALID_ROW1, checkValue);

        checkValue = calculate.check(INVALID_ROW2);
        assertEquals(VALID_ROW2, checkValue);
    }

    @Test
    public void method_that_checks_expression_evaluation() {
        assertEquals(VALID_RESULT1, calculate.calculate(EXPRESSION_EVALUATION1), 0);
        assertEquals(VALID_RESULT2, calculate.calculate(EXPRESSION_EVALUATION2), 0);
    }

    @Test
    public void method_that_checks_string_conversion_to_double() {
        assertEquals(ROW_IN_DOUBLE, calculate.toDouble(ROW_IN_STRING), 0);
    }

    @Test
    public void method_that_checks_multiplication_of_numbers() {
        calculate.operators.push('*');
        calculate.multiplyNum(VALUE_X1, VALUE_Y1);
        resultOfOperation = calculate.numbers.pop();
        assertEquals(VALID_RESULT3, resultOfOperation, 0);
    }

    @Test
    public void method_that_checks_the_addition_of_numbers() {
        calculate.operators.push('+');
        calculate.addNum(VALUE_X2, VALUE_Y2);
        resultOfOperation = calculate.numbers.pop();
        assertEquals(VALID_RESULT4, resultOfOperation, 0);
    }

    @Test
    public void method_that_checks_division_of_numbers() {
        calculate.operators.push('/');
        calculate.divideNum(VALUE_X3, VALUE_Y3);
        resultOfOperation = calculate.numbers.pop();
        assertEquals(resultOfOperation, VALID_RESULT6, 0);

        calculate.operators.push('/');
        calculate.divideNum(VALUE_X4, VALUE_Y4);
        resultOfOperation = calculate.numbers.pop();
    }

    @Test
    public void method_that_checks_subtraction_of_numbers() {
        calculate.operators.push('-');
        calculate.subtrNum(VALUE_X5, VALUE_Y5);
        resultOfOperation = calculate.numbers.pop();
        assertEquals(VALID_RESULT5, resultOfOperation, 0);
    }
}
