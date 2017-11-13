package com.sii.parser.bs;

import com.sii.parser.data.Sentence;
import com.thoughtworks.xstream.XStream;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by Carlos David Mosquera on 8/11/2017.
 * Sii Poland
 */
@Service
public class SiiParserServiceImpl implements SiiParserService
{
    private static String PUNCTUATION_MATCHER = "[.!?)$]";
    private static String WORD_MATCHER = "[^\\p{L}\\p{IsDigit}']+";
    private static String CSV_DELIMITER = ",";
    private static String SPACE = " ";
    private static String LINE_BREAK = "\n";
    private static String XML_DOCUMENT_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
    private static String XML_TEXT_TAG_OPEN = "<text>";
    private static String XML_TEXT_TAG_CLOSE = "</text>";


    @Override
    public boolean textToCsv(String inputFilePath)
    {
        int sentenceMaxWordCountForCsvHeader = 0;


        /*
         * This first try/catch block is used to traverse the inputFile looking for the sentence which has the greater
         * number of words. The only way to accomplish this is by looking the whole file and counting the words
         * in each sentence.
         */
        try (Scanner sentenceScanner = new Scanner(new File(inputFilePath)))
        {
            Pattern sentenceDelimiterPattern = Pattern.compile(PUNCTUATION_MATCHER);
            Pattern wordDelimiterPattern = Pattern.compile(WORD_MATCHER);
            sentenceScanner.useDelimiter(sentenceDelimiterPattern);

            while (sentenceScanner.hasNext())
            {

                String currentSentence = sentenceScanner.next();
                Scanner wordScanner = new Scanner(currentSentence);
                wordScanner.useDelimiter(wordDelimiterPattern);
                List<String> currentSentenceWords = new ArrayList<>();
                while (wordScanner.hasNext())
                {
                    currentSentenceWords.add(wordScanner.next());
                }

                if (currentSentenceWords.isEmpty())
                {
                    continue;
                }

                int currentSentenceWordCount = currentSentenceWords.size();
                if (currentSentenceWordCount > sentenceMaxWordCountForCsvHeader)
                {
                    sentenceMaxWordCountForCsvHeader = currentSentenceWordCount;
                }

            }

        }

        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return false;
        }



        /*
         * This block takes care of actual CSV generation. It will traverse the input file, extract the sentences
         * generate the proper format and write the CSV output file.
         */
        try (Scanner sentenceScanner = new Scanner(new File(inputFilePath)))
        {

            Pattern sentenceDelimiterPattern = Pattern.compile(PUNCTUATION_MATCHER);
            Pattern wordDelimiterPattern = Pattern.compile(WORD_MATCHER);
            sentenceScanner.useDelimiter(sentenceDelimiterPattern);
            String fullyQualifiedOutputFileName = inputFilePath + "output" + ".csv";
            BufferedWriter csvWriter = new BufferedWriter(new FileWriter(fullyQualifiedOutputFileName));
            int sentenceCount = 0;

            List<String> wordCountList = new ArrayList<>();
            for (int i = 1; i <= sentenceMaxWordCountForCsvHeader; i++)
            {
                wordCountList.add("Word" + SPACE + String.valueOf(i));

            }

            String csvHeader = CSV_DELIMITER + wordCountList.stream().collect(Collectors.joining(CSV_DELIMITER));
            csvWriter.write(csvHeader);
            csvWriter.newLine();


            while (sentenceScanner.hasNext())
            {

                String currentSentence = sentenceScanner.next();
                Scanner wordScanner = new Scanner(currentSentence);
                wordScanner.useDelimiter(wordDelimiterPattern);
                List<String> currentSentenceWords = new ArrayList<>();
                while (wordScanner.hasNext())
                {
                    currentSentenceWords.add(wordScanner.next());
                }

                Collections.sort(currentSentenceWords, String.CASE_INSENSITIVE_ORDER);

                if (currentSentenceWords.isEmpty())
                {
                    continue;
                }
                ++sentenceCount;

                String csvCurrentSentence = "Sentence" + SPACE + sentenceCount + CSV_DELIMITER + currentSentenceWords.stream().collect(Collectors.joining(CSV_DELIMITER)) + LINE_BREAK;
                csvWriter.write(csvCurrentSentence);
            }

            csvWriter.close();


            return true;

        }
        catch (IOException ex)
        {
            System.out.println("FAILING TO FINISH CSV FILE GENERATION");
            return false;
        }

    }

    @Override
    public boolean textToXml(String inputFilePath)
    {


        try (Scanner sentenceScanner = new Scanner(new File(inputFilePath)))
        {
            Pattern sentenceDelimiterPattern = Pattern.compile(PUNCTUATION_MATCHER);
            Pattern wordDelimiterPattern = Pattern.compile(WORD_MATCHER);
            sentenceScanner.useDelimiter(sentenceDelimiterPattern);
            BufferedWriter xmlWriter = new BufferedWriter(new FileWriter(inputFilePath + "output" + ".xml"));
            xmlWriter.write(XML_DOCUMENT_HEADER);
            xmlWriter.newLine();
            xmlWriter.write(XML_TEXT_TAG_OPEN);
            xmlWriter.newLine();
            XStream xstream = new XStream();
            xstream.processAnnotations(Sentence.class);

            while (sentenceScanner.hasNext())
            {

                String currentSentence = sentenceScanner.next();
                Scanner wordScanner = new Scanner(currentSentence);
                wordScanner.useDelimiter(wordDelimiterPattern);
                List<String> currentSentenceWords = new ArrayList<>();
                while (wordScanner.hasNext())
                {
                    currentSentenceWords.add(wordScanner.next());
                }
                Collections.sort(currentSentenceWords, String.CASE_INSENSITIVE_ORDER);
                if (currentSentenceWords.isEmpty())
                {
                    continue;
                }

                Sentence currentOutputSentence = new Sentence();

                currentOutputSentence.setWords(currentSentenceWords);


                xmlWriter.write(xstream.toXML(currentOutputSentence));

            }
            xmlWriter.newLine();
            xmlWriter.write(XML_TEXT_TAG_CLOSE);
            xmlWriter.close();
            return true;

        }
        catch (IOException ex)
        {
            return false;
        }

    }


}
