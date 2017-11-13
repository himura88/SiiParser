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
 * Created by tremi on 8/11/2017.
 */
@Service
public class SiiParserServiceImpl implements SiiParserService
{
    private static String PUNCTUATION_MATCHER = "[.!?)$]";
    private static String WORD_MATCHER = "[^\\p{L}\\p{IsDigit}']+";
    private static String CSV_DELIMITER =",";
    private static String SPACE =" ";
    private static String LINE_BREAK ="\n";
    private static String XML_DOCUMENT_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
    private static String XML_TEXT_TAG_OPEN = "<text>";
    private static String XML_TEXT_TAG_CLOSE = "</text>";



    @Override
    public boolean textToCsv(String inputFilePath)
    {
        try
        {

            Scanner sentenceScanner = new Scanner(new File(inputFilePath));
            Pattern sentenceDelimiterPattern = Pattern.compile(PUNCTUATION_MATCHER);
            Pattern wordDelimiterPattern = Pattern.compile(WORD_MATCHER);
            sentenceScanner.useDelimiter(sentenceDelimiterPattern);
            BufferedWriter csvWriter = new BufferedWriter(new FileWriter(inputFilePath + "output" + ".csv"));
            int csvWordsCount = 0;
            int sentenceCount = 0;


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

                int currentSentenceWordCount = currentSentenceWords.size();
                if (currentSentenceWordCount > csvWordsCount)
                {
                    csvWordsCount = currentSentenceWordCount;
                }
                String csvCurrentSentence = "Sentence" + SPACE + sentenceCount + CSV_DELIMITER +  currentSentenceWords.stream().collect(Collectors.joining(CSV_DELIMITER)) + LINE_BREAK;
                csvWriter.write(csvCurrentSentence);

                System.out.println(csvCurrentSentence);

            }

            csvWriter.close();
            return true;

        }
        catch (IOException ex)
        {
            return false;
        }
    }

    @Override
    public boolean textToXml(String inputFilePath) throws IOException
    {


        try
        {

            Scanner sentenceScanner = new Scanner(new File(inputFilePath));
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



    public static void main(String args[])
    {
        SiiParserServiceImpl siiParserService = new SiiParserServiceImpl();

        try
        {
            siiParserService.textToXml("D:\\Downloads\\sample-files\\small.in");
            siiParserService.textToCsv("D:\\Downloads\\sample-files\\small.in");
        }

        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
