package com.sii.parser;

import com.sii.parser.bs.SiiParserService;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.xmlunit.builder.Input;

import java.io.*;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.xmlunit.matchers.CompareMatcher.isSimilarTo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SiiParserApplicationTests
{


    @Autowired
    private SiiParserService siiParserService;


    @Test
    public void textToXmlTest()
    {
        ClassLoader classLoader = getClass().getClassLoader();

        String inputTestFile = classLoader.getResource("small.in").getPath();
        siiParserService.textToXml(inputTestFile);


        String testResult = classLoader.getResource("small.inoutput.xml").getPath();

        String testCaseXml = classLoader.getResource("smallTestOutput.xml").getPath();


        assertThat(
                Input.fromFile(testResult), isSimilarTo(Input.fromFile(testCaseXml)));
    }

    @Test
    public void textToCsvTest() throws IOException
    {
        ClassLoader classLoader = getClass().getClassLoader();

        String inputTestFile = classLoader.getResource("small.in").getPath();
        siiParserService.textToCsv(inputTestFile);
        String testResultFile = classLoader.getResource("small.inoutput.csv").getPath();
        String controlTestFile = classLoader.getResource("smallCsvOutputTestResult.csv").getPath();

        File csvGeneratedFile = new File(testResultFile);
        File csvControlTestFile = new File(controlTestFile);
        assertTrue(FileUtils.contentEquals(csvGeneratedFile, csvControlTestFile));


    }


}
