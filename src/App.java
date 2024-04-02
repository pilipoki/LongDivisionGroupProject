
import java.math.BigInteger;
import java.util.Scanner;

public class App {
    // global scope varaibles
    public static int testCases;
    // public static int offset = 0;
    public static String[] divisors, dividends, quotients, remainders;
    public static String quotient = "";

    public static void main(String[] args) throws Exception {
        // initialize scanner
        Scanner sc = new Scanner(System.in);
        // obtain test cases
        testCases = sc.nextInt();
        // initialize size of arrays given the test cases
        dividends = new String[testCases];
        divisors = new String[testCases];
        quotients = new String[testCases];
        remainders = new String[testCases];

        // testcases will be the amount of times we will have to execute the long
        // division method (How many times we call it)

        for (int i = 0; i < testCases; i++) {
            // initialize string arrays with test cases
            dividends[i] = sc.next();
            divisors[i] = sc.next();
        }

        for (int i = 0; i < testCases; i++) {
            // for each test case, call longDivision method
            longDivision(i);
        }
        System.out.println();
        print();
    }

    public static void longDivision(int testCase) {
        // special case divide by 1 or 0
        if (divisors[testCase].equals("1")) {
            quotients[testCase] = dividends[testCase];
            remainders[testCase] = "0";
        } else if (divisors[testCase].equals("0")) {
            quotients[testCase] = "division by 0 not possible";
            remainders[testCase] = "division by 0 not possible";
        } else if (divisors[testCase].length() > dividends[testCase].length()) {
            // no point in doing calculations, just return remainder
            quotients[testCase] = "0";
            remainders[testCase] = dividends[testCase];
        } else {

            int counter = 0;
            int offset = 0;
            // keeping track of how long our string representation should be in case we lose
            // place values in calculations
            int dividendLength = dividends[testCase].length();
            // stop case is testing to see when our offset(how many times we have shifted) +
            // the length of the divisor is equal to our dividend length using the variable
            // above.

            // Convert the input strings to BigInteger for calculations

            // we will always want the full version of the divisor, but will be shifting
            // along the dividend
            BigInteger tempDivisor = new BigInteger(divisors[testCase]);
            // dividend will vary in calculations
            BigInteger tempDividend;

            while (true) {

                // condition to exit
                if (offset + tempDivisor.toString().length() == dividendLength) {
                    // if we have shifted our divisor to the end of the dividend...
                    // then no more splitting string
                    // perform subtractions until no longer possible
                    tempDividend = new BigInteger(dividends[testCase]);

                    while (tempDividend.subtract(tempDivisor).intValue() >= 0) {
                        tempDividend = tempDividend.subtract(tempDivisor);
                        counter++;
                    }
                    // once exited the subtractions, store the counter as first digit in quotient
                    // concatonation
                    quotient += counter;
                    counter = 0;
                    // store remainder + quotient
                    quotients[testCase] = quotient;
                    remainders[testCase] = tempDividend.toString();
                    quotient = "";
                    break;
                }

                // this will only take the digits that line up with the divisor...
                String dividend = splitString(dividends[testCase], 0, (tempDivisor.toString().length() + offset));
                tempDividend = new BigInteger(dividend);
                int tempDividendLength = dividend.length();

                // perform subtractions until no longer possible
                while (tempDividend.subtract(tempDivisor).intValue() >= 0) {
                    tempDividend = tempDividend.subtract(tempDivisor);
                    counter++;
                }
                // once exited the subtractions, store the counter as first digit in quotient
                // by concatonation
                quotient += counter;
                counter = 0;

                // find out how much has changed and replace the place value with the appropiate
                // amount of zeros.
                int difference = tempDividendLength - tempDividend.toString().length();
                dividends[testCase] = numOfZeros(difference) + tempDividend
                        + splitString(dividends[testCase], (tempDivisor.toString().length() + offset),
                                dividendLength);

                // add to the offset, and combine the remainder with the rest of the dividend.
                offset++;
            }
        }

    }

    public static String numOfZeros(int number) {
        String zeros = "";
        for (int i = 0; i < number; i++) {
            // concatonate to return a string of zeros.
            zeros += "0";
        }
        return zeros;
    }

    public static String splitString(String number, int start, int end) {
        if (end > number.length()) {
            //prevents index out of bounds errors
            return number;
        }
        if (start > number.length()) {
            //prevents index out of bounds errors
            return number;
        }
        String splitString = number.substring(start, end);
        return splitString;
    }

    public static void print() {
        for (int i = 0; i < testCases; i++) {

            //fix leading zeros problem
            char[] temp = quotients[i].toCharArray();
            int numberStartIndex=0;
            if (temp[0] == '0') {
                //it has leading zeros, take care of them
                for (int j = 0; j < temp.length; j++) {
                    if (temp[j] != '0') {
                        numberStartIndex =j;
                        quotients[i] = splitString(quotients[i], numberStartIndex, quotients[i].length());
                        break;
                    }
                }
            }

            System.out.println(quotients[i]);
            System.out.println(remainders[i]);
            System.out.println();
        }
    }
}
