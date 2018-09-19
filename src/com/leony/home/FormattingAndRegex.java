package com.leony.home;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Formattable;
import java.util.Formatter;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormattingAndRegex implements Formattable {
    public void joinStringsUsingStringJoiner(String... strings) {
        StringJoiner sj = new StringJoiner(" | ", "-= ", " =-");
        sj.setEmptyValue("EMPTY");
        for (String word: strings) {
            sj.add(word);
        }
        System.out.println(sj.toString());
    }

    public void formatStringUsingStringFormat(String... words) {
        System.out.printf("My name is %s %s\n", "Leon", "Yalin");
        if (words.length > 0) {
            System.out.printf("Those input words are: first - %s, second - %s\n", words[0], words[1]);
            System.out.printf("Those float formatted value is : %.1f\n", Float.valueOf(words[2]));
        }
        System.out.printf("Print a HEX value with radix using #: %#X\n", 32);
        System.out.printf("Print a fixed width values:\nFirst name:%9s\nLast name:%10s\n", "Leon", "Yalin");
        System.out.printf("Print a formatted number with separators: %,.2f\n", 1234.2);
        System.out.printf("Print a signed number: %+d\n", 457689);
        System.out.printf("Print strings in custom order: %2$s %1$s\n", "Leon", "Yalin");
    }

    /**
     * The Formattable order: %[argument_index$][flags][width][.precision]conversion
     */
    public void printClassObjectUsingFormattableInterface(Formattable object) {
        System.out.printf("Object formatted: %5s\n", object);
    }

    @Override
    public void formatTo(Formatter formatter, int flags, int width, int precision) {
        formatter.format("formatted object - flags: %d, width: %d, precision: %d", flags, width, precision);
    }

    public void writeFormattedContentFoFileUsingFormatter(String filePath) {
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
            try(Formatter formatter = new Formatter(writer)) {
                formatter.format("%s %s", "Leon", "Yalin");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void useRegexMethodsFromStringClass() {
        String sentence = "My name is Leon Yalin";
        System.out.printf("The initial sentence is: %s\n", sentence);
        System.out.printf("The sentence after replaceFirst() is: %s\n", sentence.replaceFirst("name", "GREAT name"));
        System.out.printf("The sentence after replaceAll() is: %s\n", sentence.replaceAll("n", "n__n"));
        System.out.printf("The sentence after split(\" \") is: %s\n", Arrays.toString(sentence.split(" ")));
        System.out.printf("The sentence after matching (\"name\") is: %s\n", sentence.matches("(.*)name(.*)"));
    }

    public void usePatternAndMatcherClasses() {
        String sentence = "My name is Leon Yalin";
        Pattern pattern = Pattern.compile("\\w+");
        Matcher matcher = pattern.matcher(sentence);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}
