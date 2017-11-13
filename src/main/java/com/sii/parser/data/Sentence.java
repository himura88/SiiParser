package com.sii.parser.data;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * Created by Carlos David Mosquera on 8/11/2017.
 * Sii Poland
 */
@XStreamAlias("sentence")
public class Sentence
{



    @XStreamImplicit(itemFieldName = "word")
    private List<String> words;

    public List<String> getWords()
    {
        return words;
    }

    public void setWords(List<String> words)
    {
        this.words = words;
    }
}
