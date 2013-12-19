package org.caron.ragel.url;

import static org.junit.Assert.assertEquals;
import org.caron.ragel.url.URLParser.Url;

import org.junit.Assert;
import org.junit.Test;

public class UrlParserTest {

    @Test
    public void testParsingWithoutHttp() throws Exception {
        Url url = URLParser.parse("stackoverflow.com/questions/3771081/proper-way-to-check-for-url-equality");
        assertEquals(null, url.protocol);
        assertEquals("stackoverflow.com", url.host);
        assertEquals(null, url.port);
        assertEquals("/questions/3771081/proper-way-to-check-for-url-equality", url.path);
        assertEquals(null, url.query);
        assertEquals(null, url.fragment);
    }

    @Test
    public void testParsingWithHttp() throws Exception {
        Url url = URLParser.parse("http://stackoverflow.com/questions/3771081/proper-way-to-check-for-url-equality");
        assertEquals("http", url.protocol);
        assertEquals("stackoverflow.com", url.host);
        assertEquals(null, url.port);
        assertEquals("/questions/3771081/proper-way-to-check-for-url-equality", url.path);
        assertEquals(null, url.query);
        assertEquals(null, url.fragment);
    }

    @Test
    public void testParsingWithHttpAndExtraSchemeSlashes() throws Exception {
        Url url = URLParser.parse("http:///stackoverflow.com/questions/3771081/proper-way-to-check-for-url-equality");
        assertEquals("http", url.protocol);
        assertEquals("stackoverflow.com", url.host);
        assertEquals(null, url.port);
        assertEquals("/questions/3771081/proper-way-to-check-for-url-equality", url.path);
        assertEquals(null, url.query);
        assertEquals(null, url.fragment);
    }

    @Test
    public void testParsingWithSchemeAndDrivepath() throws Exception {
        Url url = URLParser
                .parse("file://D:/Projects/eclipse/runtime-New_configuration/ShinyProject/custom-generation-folder/doc/Customer.html");
        assertEquals("file", url.protocol);
        assertEquals(null, url.host);
        assertEquals(null, url.port);
        assertEquals(
                "D:/Projects/eclipse/runtime-New_configuration/ShinyProject/custom-generation-folder/doc/Customer.html",
                url.path);
        assertEquals(null, url.query);
        assertEquals(null, url.fragment);
    }

    @Test
    public void testParsingWithoutSchemeAndDrivepath() throws Exception {
        Url url = URLParser
                .parse("D:/Projects/eclipse/runtime-New_configuration/ShinyProject/custom-generation-folder/doc/Customer.html");
        System.out.println(url);
    }

    @Test
    public void testUrlEquality2() throws Exception {
        Url url1 = URLParser
                .parse("file:/D:/Projects/eclipse/runtime-New_configuration/ShinyProject/custom-generation-folder/doc/Customer.html");
        Url url3 = URLParser
                .parse("file:///D:/Projects/eclipse/runtime-New_configuration/ShinyProject/custom-generation-folder/doc/Customer.html");
        Assert.assertTrue(url1.equals(url3));
    }

}
