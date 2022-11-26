package org.apache.xmlbeans;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

public interface XmlTokenSource {
   Object monitor();

   XmlDocumentProperties documentProperties();

   XmlCursor newCursor();

   /** @deprecated */
   XMLInputStream newXMLInputStream();

   XMLStreamReader newXMLStreamReader();

   String xmlText();

   InputStream newInputStream();

   Reader newReader();

   Node newDomNode();

   Node getDomNode();

   void save(ContentHandler var1, LexicalHandler var2) throws SAXException;

   void save(File var1) throws IOException;

   void save(OutputStream var1) throws IOException;

   void save(Writer var1) throws IOException;

   /** @deprecated */
   XMLInputStream newXMLInputStream(XmlOptions var1);

   XMLStreamReader newXMLStreamReader(XmlOptions var1);

   String xmlText(XmlOptions var1);

   InputStream newInputStream(XmlOptions var1);

   Reader newReader(XmlOptions var1);

   Node newDomNode(XmlOptions var1);

   void save(ContentHandler var1, LexicalHandler var2, XmlOptions var3) throws SAXException;

   void save(File var1, XmlOptions var2) throws IOException;

   void save(OutputStream var1, XmlOptions var2) throws IOException;

   void save(Writer var1, XmlOptions var2) throws IOException;

   void dump();
}
