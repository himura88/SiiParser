package com.sii.parser.data;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * Created by tremi on 8/11/2017.
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
