
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

class test {

    @Test
    public void test_getResults_works_correctly() {
        Main.results = new TreeMap<>();

        Main.results.put(10, "Item1");
        Main.results.put(5, "Item2");

        String actualResult = Main.getResults();

        String expectedResult = "Top 5 event contexts in log: \r\n" +
                "1. Item2. Occurrences: 5\r\n" +
                "2. Item1. Occurrences: 10\r\n";
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void test_parseHmineOutput_works_correctly() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("mockInput.txt"))));
        Main.list = Arrays.stream(new String[]{"Item0", "Item1", "Item2", "Item3"}).collect(Collectors.toList());
        Main.parseHmineOutput(br);
        Map<Integer, String> expected = new TreeMap<>();
        expected.put(10, "Item1");
        expected.put(20, "Item2");
        expected.put(30, "Item3");
        Assert.assertEquals(expected, Main.results);
    }

    @Test
    public void test_parseLogs_works_correctly() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("mockInput1.txt"))));
        BufferedWriter bw = new BufferedWriter(new FileWriter("mockOutput.txt"));
        File actualOutput = new File(".//mockOutput.txt");
        File expectedOutput = new File(".//mockExpectedOutput.txt");
        boolean pass = true;

        Main.parseLogs(br, bw);
        FileInputStream fis1 = new FileInputStream(actualOutput);
        FileInputStream fis2 = new FileInputStream(expectedOutput);
        int i1 = fis1.read();
        int i2 = fis2.read();
        while (i1 != -1) {
            if (i1 != i2) {
                pass = false;
                break;
            }
            i1 = fis1.read();
            i2 = fis2.read();
        }
        fis1.close();
        fis2.close();
//        Assert.assertEquals(Files.readAllBytes(expectedOutput.toPath()), Files.readAllBytes(actualOutput.toPath()));
        Assert.assertTrue(pass);
    }

    @Test
    public void test_parseLogs_throws_exception() throws IOException{


        assertThrows(IOException.class, () -> {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("asdasasd.txt"))));
            BufferedWriter bw = new BufferedWriter(new FileWriter("asdasdaz.txt"));
            Main.parseLogs(br, bw);
        });
    }

    @Test
    public void test_parseHMineOutput_throws_IOException() throws IOException{
        assertThrows(IOException.class, () -> {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("asdasasd.txt"))));
            Main.parseHmineOutput(br);
        });
    }
}
