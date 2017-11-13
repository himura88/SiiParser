package com.sii.parser.bs;



/**
 * Created by Carlos David Mosquera on 8/11/2017.
 * Sii Poland
 */

public interface SiiParserService
{
    /**
     * This method takes care of reading an input file containing the input text
     * The file name has to be provided as fully qualified name (it means, including the path to the file)
     * @param inputFilePath fully qualified name of the input file
     * @return true if csv output file is generated, false otherwise.
     */
    boolean textToCsv (String inputFilePath);

    /**
     * This method takes care of reading an input file containing the input text
     * The file name has to be provided as fully qualified name (it means, including the path to the file)
     * The method generates an output XML file containing the text formatted in XML.
     * @param inputFilePath fully qualified name of the input file
     * @return true if XML output file is generated, false otherwise.
     */
    boolean textToXml (String inputFilePath);
}
