package weblogic.xml.stream.util;

import java.io.IOException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/** @deprecated */
@Deprecated
public interface XMLPullReader extends XMLReader {
   boolean parseSomeSetup(InputSource var1) throws IOException, SAXException;

   boolean parseSomeSetup(String var1) throws IOException, SAXException;

   boolean parseSome() throws SAXException;
}
