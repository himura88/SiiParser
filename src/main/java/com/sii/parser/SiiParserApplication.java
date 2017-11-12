package com.sii.parser;

import com.sii.parser.bs.SiiParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SiiParserApplication implements CommandLineRunner
{

    @Autowired
    private SiiParserService siiParserService;

    public static void main(String[] args)
    {
        SpringApplication.run(SiiParserApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception
    {

        siiParserService.textToXml("");
    }
}
