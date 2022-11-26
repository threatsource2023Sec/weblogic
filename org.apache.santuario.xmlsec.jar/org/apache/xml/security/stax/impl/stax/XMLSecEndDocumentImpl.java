package org.apache.xml.security.stax.impl.stax;

import java.io.Writer;
import javax.xml.stream.XMLStreamException;
import org.apache.xml.security.stax.ext.stax.XMLSecEndDocument;

public class XMLSecEndDocumentImpl extends XMLSecEventBaseImpl implements XMLSecEndDocument {
   public int getEventType() {
      return 8;
   }

   public boolean isEndDocument() {
      return true;
   }

   public XMLSecEndDocument asEndEndDocument() {
      return this;
   }

   public void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {
   }
}
