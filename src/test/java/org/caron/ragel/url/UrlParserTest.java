package org.caron.ragel.url;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.caron.ragel.url.URLParser.Url;

import org.junit.Assert;
import org.junit.Test;

public class UrlParserTest {

    @Test
    public void testParsingWithoutHttp() throws Exception {
        Url url = URLParser.parse("stackoverflow.com/questions/3771081/proper-way-to-check-for-url-equality");
        assertEquals("stackoverflow.com", url.host);
        assertEquals("/questions/3771081/proper-way-to-check-for-url-equality", url.path);
        assertNull(url.protocol);
        assertNull(url.port);
        assertNull(url.query);
        assertNull(url.fragment);
    }

    @Test
    public void testParsingWithHttp() throws Exception {
        Url url = URLParser.parse("http://stackoverflow.com/questions/3771081/proper-way-to-check-for-url-equality");
        assertEquals("http", url.protocol);
        assertEquals("stackoverflow.com", url.host);
        assertEquals("/questions/3771081/proper-way-to-check-for-url-equality", url.path);
        assertNull(url.port);
        assertNull(url.query);
        assertNull(url.fragment);
    }

    @Test
    public void testParsingWithSchemeAndDrivepath() throws Exception {
        Url url = URLParser
                .parse("file://D:/Projects/eclipse/runtime-New_configuration/ShinyProject/custom-generation-folder/doc/Customer.html");
        System.out.println("file->" + url);
        assertEquals("file", url.protocol);
        assertEquals(
                "D:/Projects/eclipse/runtime-New_configuration/ShinyProject/custom-generation-folder/doc/Customer.html",
                url.path);
        // @TODO: Unsure if we should consider 'D' as the host. Verify..
        assertEquals("D", url.host);
        assertNull(url.port);
        assertNull(url.query);
        assertNull(url.fragment);
    }

    @Test
    public void testRfcExampleFtp() throws Exception {
        Url url = URLParser.parse("ftp://ftp.is.co.za/rfc/rfc1808.txt");
        assertEquals("ftp", url.protocol);
        assertEquals("/rfc/rfc1808.txt", url.path);
        assertEquals("ftp.is.co.za", url.host);
        assertNull(url.port);
        assertNull(url.query);
        assertNull(url.fragment);
    }

    @Test
    public void testRfcExampleHttp() throws Exception {
        Url url = URLParser.parse("http://www.ietf.org/rfc/rfc2396.txt");
        assertEquals("http", url.protocol);
        assertEquals("/rfc/rfc2396.txt", url.path);
        assertEquals("www.ietf.org", url.host);
        assertNull(url.port);
        assertNull(url.query);
        assertNull(url.fragment);
    }

    @Test
    public void testRfcExampleLdap() throws Exception {
        Url url = URLParser.parse("ldap://www.ldap.org/c=GB?objectClass");
        assertEquals("ldap", url.protocol);
        assertEquals("/c=GB", url.path);
        assertEquals("www.ldap.org", url.host);
        assertEquals("objectClass", url.query);
        assertNull(url.port);
        assertNull(url.fragment);
    }

    @Test
    public void testRfcExampleMailto() throws Exception {
        Url url = URLParser.parse("mailto:John.Doe@example.com");
        assertEquals("mailto", url.protocol);
        assertEquals("example.com", url.host);
        assertNull(url.path);
        assertNull(url.port);
        assertNull(url.query);
        assertNull(url.fragment);
    }

    @Test
    public void testRfcExampleNews() throws Exception {
        Url url = URLParser.parse("news:comp.infosystems.www.servers.unix");
        assertEquals("news", url.protocol);
        assertEquals("comp.infosystems.www.servers.unix", url.host);
        assertNull(url.path);
        assertNull(url.port);
        assertNull(url.query);
        assertNull(url.fragment);
    }

    @Test
    public void testRfcExampleTel() throws Exception {
        Url url = URLParser.parse("tel:+1-816-555-1212");
        assertEquals("tel", url.protocol);
        assertEquals("+1-816-555-1212", url.host);
        assertNull(url.port);
        assertNull(url.path);
        assertNull(url.query);
        assertNull(url.fragment);
    }

    @Test
    public void testRfcExampleTelnet() throws Exception {
        Url url = URLParser.parse("telnet://192.0.2.16:80/");
        assertEquals("telnet", url.protocol);
        assertEquals("192.0.2.16", url.host);
        assertEquals("/", url.path);
        assertEquals("80", url.port);
        assertNull(url.query);
        assertNull(url.fragment);
    }

}
