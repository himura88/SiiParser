package com.sii.parser;

import com.sii.parser.bs.SiiParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Carlos David Mosquera on 8/11/2017.
 * Sii Poland
 */
@SpringBootApplication
public class SiiParserApplication implements CommandLineRunner
{

    @Autowired
    private SiiParserService siiParserService;

    public static void main(String[] args)
    {

        SpringApplication applicationRunner = new SpringApplication(SiiParserApplication.class);

        applicationRunner.run(args);

        SpringApplication.run(SiiParserApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception
    {

        if (args.length >= 1)
        {

            String fullyQualifiedFileName = args[0];
            siiParserService.textToXml(fullyQualifiedFileName);
            siiParserService.textToCsv(fullyQualifiedFileName);
        }



    }
}
