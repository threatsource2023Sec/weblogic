package weblogicx.xml.stream;

import java.io.IOException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public interface XMLEventStream {
   XMLEvent next() throws SAXException, SAXParseException;

   boolean hasNext() throws SAXException, SAXParseException;

   XMLEvent peek() throws SAXException, SAXParseException;

   StartElementEvent peekElement() throws SAXException, SAXParseException;

   boolean hasStartElement(String var1) throws SAXException, SAXParseException;

   boolean hasStartElement(String var1, String var2) throws SAXException, SAXParseException;

   boolean hasStartElement() throws SAXException, SAXParseException;

   StartElementEvent startElement(String var1) throws SAXException, SAXParseException;

   StartElementEvent startElement(String var1, String var2) throws SAXException, SAXParseException;

   StartElementEvent startElement() throws SAXException, SAXParseException;

   EndElementEvent endElement(String var1) throws SAXException, SAXParseException;

   EndElementEvent endElement(String var1, String var2) throws SAXException, SAXParseException;

   EndElementEvent endElement() throws SAXException, SAXParseException;

   boolean nextElementIs(String var1) throws SAXException, SAXParseException;

   boolean nextElementIs(String var1, String var2) throws SAXException, SAXParseException;

   String getText() throws SAXException, SAXParseException;

   void setThrowExceptionOnRecoverableError(boolean var1) throws SAXException, SAXParseException;

   void setThrowExceptionOnWarning(boolean var1) throws SAXException, SAXParseException;

   void startDocument(InputSource var1) throws SAXException, SAXParseException, IOException;

   void endDocument(boolean var1) throws SAXException, SAXParseException, IOException;

   XMLEventStream getSubStream() throws SAXException;

   XMLEventStream getSubElementStream() throws SAXException;

   EndElementEvent popElement() throws SAXException;

   EndElementEvent skipElement() throws SAXException;
}
