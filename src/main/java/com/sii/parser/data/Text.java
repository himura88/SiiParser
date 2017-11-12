package com.sii.parser.data;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

/**
 * Created by tremi on 8/11/2017.
 */
public class Text
{
    @JacksonXmlProperty(localName = "sentence")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Sentence> sentences;

    public List<Sentence> getSentences()
    {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences)
    {
        this.sentences = sentences;
    }
}
