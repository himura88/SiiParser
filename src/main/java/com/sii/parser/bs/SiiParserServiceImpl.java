package com.sii.parser.bs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import com.sii.parser.data.Sentence;
import com.sii.parser.data.Text;
import com.thoughtworks.xstream.XStream;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by tremi on 8/11/2017.
 */
@Service
public class SiiParserServiceImpl implements SiiParserService
{
    private static String PUNCTUATION_MATCHER = "[.!?)$]";
    private static String WORD_MATCHER = "[^\\p{L}\\p{IsDigit}']+";

    @Override
    public boolean textToCsv(String inputFilePath)
    {
        return false;
    }

    @Override
    public boolean textToXml(String inputFilePath) throws IOException
    {


        Scanner sentenceScanner = new Scanner(new File(inputFilePath));
        Pattern sentenceDelimiterPattern = Pattern.compile(PUNCTUATION_MATCHER);
        Pattern wordDelimiterPattern = Pattern.compile(WORD_MATCHER);
        sentenceScanner.useDelimiter(sentenceDelimiterPattern);
        BufferedWriter xmlWriter = new BufferedWriter(new FileWriter(inputFilePath + "output" + ".xml"));
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

        xmlWriter.close();



        return false;
    }

    public static void main(String args[])
    {
        SiiParserServiceImpl siiParserService = new SiiParserServiceImpl();

        try
        {
            siiParserService.textToXml("D:\\Downloads\\sample-files\\small.in");
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
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
