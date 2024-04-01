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
        }else if (divisors[testCase].equals("0")) {
            quotients[testCase] = "division by 0 not possible";
            remainders[testCase] = "division by 0 not possible";
        }else if (divisors[testCase].length()>dividends[testCase].length()) {
            //no point in doing calculations, just return remainder
            quotients[testCase] = "0";
            remainders[testCase] = dividends[testCase];
        }else{

        int counter = 0;
        int offset = 0;
        // stop case is testing to see when our offset(how many times we have shifted) +
        // the length of the divisor is equal to our dividend length
        int stopCase = dividends[testCase].length();
        // Convert the input strings to BigInteger for calculations

        // we will always want the full version of the divisor, but will be shifting
        // along the dividend
        BigInteger tempDivisor = new BigInteger(divisors[testCase]);
        BigInteger tempDividend;

        while (true) {

            // condition to exit
            if (offset + tempDivisor.toString().length() == stopCase) {
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

            // perform subtractions until no longer possible
            while (tempDividend.subtract(tempDivisor).intValue() >= 0) {
                tempDividend = tempDividend.subtract(tempDivisor);
                counter++;
            }
            // once exited the subtractions, store the counter as first digit in quotient
            // concatonation
            quotient += counter;
            counter = 0;

            if (tempDividend.toString().equals("0")) {
                // special case
                dividends[testCase] = splitString(dividends[testCase], (tempDivisor.toString().length() + offset),
                        (dividends[testCase].length()));
            } else if (dividends[testCase].equals(tempDividend.toString())) {
                // if dividend has not changed, just skip
            } else {
                // modify dividend so that on next iteration, we string split the remainder +
                // the following digits we havent touched.
                dividends[testCase] = tempDividend
                        + splitString(dividends[testCase], (tempDivisor.toString().length() + offset),
                                (dividends[testCase].length()));
            }

            // add to the offset, and combine the remainder with the rest of the dividend.
            offset++;
        }
    }

    }

    public static String splitString(String number, int start, int end) {
        if (end > number.length()) {
            // this means that our divisor is bigger than our remaining dividend.
            return number;
        }
        if (start > number.length()) {
            // this means that our divisor is bigger than our remaining dividend.
            return number;
        }
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
