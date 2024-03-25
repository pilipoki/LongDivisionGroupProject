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

        print();
    }

    public static void longDivision(int testCase) {
        int counter = 0;
        int offset = 0;
        int stopCase = dividends[testCase].length();
        // Convert the input strings to BigInteger for calculations

        // we will always want the full version of the divisor, but will be shifting
        // along the dividend
        BigInteger tempDivisor = new BigInteger(divisors[testCase]);

        while (true) {

            // condition to exit
            if (offset + tempDivisor.toString().length() == stopCase) {
                // then no more splitting string
                // perform subtractions until no longer possible
                BigInteger tempDividend = new BigInteger(dividends[testCase]);

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
            BigInteger tempDividend = new BigInteger(dividend);

            // perform subtractions until no longer possible
            while (tempDividend.subtract(tempDivisor).intValue() > 0) {
                tempDividend = tempDividend.subtract(tempDivisor);
                counter++;
            }
            // once exited the subtractions, store the counter as first digit in quotient
            // concatonation
            quotient += counter;
            counter = 0;
            // modify dividend so that on next iteration, we string split the remainder +
            // the following digits we havent touched.
            dividends[testCase] = tempDividend
                    + splitString(dividends[testCase], (tempDivisor.toString().length() + offset),
                            (dividends[testCase].length()));
            // add to the offset, and combine the remainder with the rest of the dividend.
            offset++;
        }

    }

    public static String splitString(String number, int start, int end) {
        String splitString = number.substring(start, end);
        return splitString;
    }

    public static void print() {
        for (int i = 0; i < testCases; i++) {
            System.out.println(quotients[i]);
            System.out.println(remainders[i]);
            System.out.println();
        }
    }
}
