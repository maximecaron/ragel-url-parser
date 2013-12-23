package org.caron.ragel.url;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * Unit-tests that replicate the ones in the {@link UrlParserTest} class. Equality tests were excluded though, because
 * {@link Matcher}s do not provide this functionality.
 * 
 * @author jimleroyer
 */
public class RegexTest {

    String regex = "^(([^:/?#.]+):)?(//)?(([^:/]*)?(\\:([^/]*))?\\@)?(([^/:]+)|\\[[^/\\]]+\\])?(:(\\d*))?(/[^?#]*)(\\?([^#]*))?(#(.*))?";
    Pattern p = Pattern.compile(regex);

    Map<String, Integer> GROUP_NAMES = new HashMap<String, Integer>();
    {
        GROUP_NAMES.put("scheme", 2);
        GROUP_NAMES.put("username", 5);
        GROUP_NAMES.put("password", 7);
        GROUP_NAMES.put("server", 8);
        GROUP_NAMES.put("port", 11);
        GROUP_NAMES.put("path", 12);
        GROUP_NAMES.put("query", 14);
        GROUP_NAMES.put("fragment", 16);
    }

    protected String group(Matcher m, String groupName) {
        return m.group(GROUP_NAMES.get(groupName));
    }

    @Test
    public void testParsingWithoutHttp() throws Exception {
        Matcher m = p.matcher("stackoverflow.com/questions/3771081/proper-way-to-check-for-url-equality");
        assertTrue(m.find());
        assertEquals("stackoverflow.com", group(m, "server"));
        assertEquals("/questions/3771081/proper-way-to-check-for-url-equality", group(m, "path"));
        assertNull(group(m, "scheme"));
        assertNull(group(m, "port"));
        assertNull(group(m, "query"));
        assertNull(group(m, "fragment"));
    }

    @Test
    public void testParsingWithHttp() throws Exception {
        Matcher m = p.matcher("http://stackoverflow.com/questions/3771081/proper-way-to-check-for-url-equality");
        assertTrue(m.find());
        assertEquals("http", group(m, "scheme"));
        assertEquals("stackoverflow.com", group(m, "server"));
        assertEquals("/questions/3771081/proper-way-to-check-for-url-equality", group(m, "path"));
        assertNull(group(m, "port"));
        assertNull(group(m, "query"));
        assertNull(group(m, "fragment"));
    }

    @Test
    public void testRfcExampleFtp() throws Exception {
        Matcher m = p.matcher("ftp://ftp.is.co.za/rfc/rfc1808.txt");
        assertTrue(m.find());
        assertEquals("ftp", group(m, "scheme"));
        assertEquals("ftp.is.co.za", group(m, "server"));
        assertEquals("/rfc/rfc1808.txt", group(m, "path"));
        assertNull(group(m, "port"));
        assertNull(group(m, "query"));
        assertNull(group(m, "fragment"));
    }

    @Test
    public void testRfcExampleHttp() throws Exception {
        Matcher m = p.matcher("http://www.ietf.org/rfc/rfc2396.txt");
        assertTrue(m.find());
        assertEquals("http", group(m, "scheme"));
        assertEquals("www.ietf.org", group(m, "server"));
        assertEquals("/rfc/rfc2396.txt", group(m, "path"));
        assertNull(group(m, "port"));
        assertNull(group(m, "query"));
        assertNull(group(m, "fragment"));
    }

    @Test
    public void testRfcExampleLdap() throws Exception {
        Matcher m = p.matcher("ldap://www.ldap.org/c=GB?objectClass");
        assertTrue(m.find());
        assertEquals("ldap", group(m, "scheme"));
        assertEquals("www.ldap.org", group(m, "server"));
        assertEquals("/c=GB", group(m, "path"));
        assertEquals("objectClass", group(m, "query"));
        assertNull(group(m, "port"));
        assertNull(group(m, "fragment"));
    }

    @Test
    public void testRfcExampleMailto() throws Exception {
        Matcher m = p.matcher("mailto:John.Doe@example.com");
        assertTrue(m.find());
        assertEquals("mailto", group(m, "scheme"));
        assertEquals("example.com", group(m, "server"));
        assertNull(group(m, "path"));
        assertNull(group(m, "port"));
        assertNull(group(m, "query"));
        assertNull(group(m, "fragment"));
    }

    @Test
    public void testRfcExampleNews() throws Exception {
        Matcher m = p.matcher("news:comp.infosystems.www.servers.unix");
        assertTrue(m.find());
        assertEquals("news", group(m, "scheme"));
        assertEquals("comp.infosystems.www.servers.unix", group(m, "server"));
        assertNull(group(m, "path"));
        assertNull(group(m, "port"));
        assertNull(group(m, "query"));
        assertNull(group(m, "fragment"));
    }

    @Test
    public void testRfcExampleTel() throws Exception {
        // More of a URI than a URL though..
        Matcher m = p.matcher("tel:+1-816-555-1212");
        assertTrue(m.find());
        assertEquals("tel", group(m, "scheme"));
        assertEquals("+1-816-555-1212", group(m, "server"));
        assertNull(group(m, "path"));
        assertNull(group(m, "port"));
        assertNull(group(m, "query"));
        assertNull(group(m, "fragment"));
    }

    @Test
    public void testRfcExampleTelnet() throws Exception {
        Matcher m = p.matcher("telnet://192.0.2.16:80/");
        assertTrue(m.find());
        assertEquals("telnet", group(m, "scheme"));
        assertEquals("192.0.2.16", group(m, "server"));
        assertEquals("/", group(m, "path"));
        assertEquals("80", group(m, "port"));
        assertNull(group(m, "query"));
        assertNull(group(m, "fragment"));
    }

    @Test
    public void testParsingWithSchemeAndDrivepath() throws Exception {
        // The 'file' scheme is a mess, but more of a URI than a URL:
        // http://en.wikipedia.org/wiki/File_URI_scheme
        Matcher m = p
                .matcher("file:///D:/Projects/eclipse/runtime-New_configuration/ShinyProject/custom-generation-folder/doc/Customer.html");
        assertTrue(m.find());
        assertEquals("file", group(m, "scheme"));
        assertEquals(
                "D:/Projects/eclipse/runtime-New_configuration/ShinyProject/custom-generation-folder/doc/Customer.html",
                group(m, "path"));
        assertNull(group(m, "server"));
        assertNull(group(m, "port"));
        assertNull(group(m, "query"));
        assertNull(group(m, "fragment"));
    }

    @Test
    public void testParsingWithSchemeAndDrivepathVerticalBar() throws Exception {
        // The 'file' scheme is a mess, but more of a URI than a URL:
        // http://en.wikipedia.org/wiki/File_URI_scheme
        Matcher m = p
                .matcher("file://D|/Projects/eclipse/runtime-New_configuration/ShinyProject/custom-generation-folder/doc/Customer.html");
        assertTrue(m.find());
        assertEquals("file", group(m, "scheme"));
        assertEquals(
                "D:|Projects/eclipse/runtime-New_configuration/ShinyProject/custom-generation-folder/doc/Customer.html",
                group(m, "path"));
        assertNull(group(m, "server"));
        assertNull(group(m, "port"));
        assertNull(group(m, "query"));
        assertNull(group(m, "fragment"));
    }

}
