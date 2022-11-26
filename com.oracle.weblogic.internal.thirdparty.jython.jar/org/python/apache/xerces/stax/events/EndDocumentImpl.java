package org.python.apache.xerces.stax.events;

import java.io.Writer;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndDocument;

public final class EndDocumentImpl extends XMLEventImpl implements EndDocument {
   public EndDocumentImpl(Location var1) {
      super(8, var1);
   }

   public void writeAsEncodedUnicode(Writer var1) throws XMLStreamException {
   }
}
