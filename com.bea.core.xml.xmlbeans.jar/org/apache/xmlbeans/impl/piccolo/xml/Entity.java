package org.apache.xmlbeans.impl.piccolo.xml;

import java.io.IOException;
import java.io.Reader;
import org.apache.xmlbeans.impl.piccolo.util.RecursionException;
import org.xml.sax.SAXException;

public interface Entity {
   boolean isOpen();

   void open() throws IOException, SAXException, RecursionException;

   void close() throws IOException;

   String getPublicID();

   String getSystemID();

   boolean isStandalone();

   void setStandalone(boolean var1);

   boolean isInternal();

   boolean isParsed();

   String getDeclaredEncoding();

   boolean isStandaloneDeclared();

   String getXMLVersion();

   Reader getReader();

   String stringValue();

   char[] charArrayValue();
}
