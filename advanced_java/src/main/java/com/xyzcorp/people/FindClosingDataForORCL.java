package com.xyzcorp.people;

import sun.tools.java.ScannerInputReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by danno on 10/12/16.
 */
public class FindClosingDataForORCL {


    public static void main(String[] args) throws IOException {

        URL url = new URL("http://ichart.finance.yahoo.com/table.csv?s=ORCL&a=00&b=01&c=2015");

        try (
            InputStream is = url.openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            ) {

            System.out.println(bufferedReader.lines()
                    .skip(1)
                    .map(ln -> Arrays.asList(ln.split(",")))
                    .map(ls -> Arrays.asList(ls.get(0), ls.get(4)))
                    .filter(ls -> LocalDate.parse(ls.get(0)).getMonth().equals(Month.JULY))
                    .mapToDouble(ls -> Double.parseDouble(ls.get(1)))
                    .average().orElseGet(() -> 0.0));
        }
    }
}
