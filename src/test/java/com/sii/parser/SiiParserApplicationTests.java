package com.sii.parser;

import com.sii.parser.bs.SiiParserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SiiParserApplicationTests {

	@Autowired
	private SiiParserService siiParserService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void textToXmlTest()
	{

	}

}
