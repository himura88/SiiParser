package com.sii.parser.bs;


import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by tremi on 8/11/2017.
 */

public interface SiiParserService
{
    boolean textToCsv (String inputFilePath);
    boolean textToXml (String inputFilePath) throws IOException;
}
