package org.python.apache.xerces.stax.events;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.DTD;

public final class DTDImpl extends XMLEventImpl implements DTD {
   private final String fDTD;

   public DTDImpl(String var1, Location var2) {
      super(11, var2);
      this.fDTD = var1 != null ? var1 : null;
   }

   public String getDocumentTypeDeclaration() {
      return this.fDTD;
   }

   public Object getProcessedDTD() {
      return null;
   }

   public List getNotations() {
      return Collections.EMPTY_LIST;
   }

   public List getEntities() {
      return Collections.EMPTY_LIST;
   }

   public void writeAsEncodedUnicode(Writer var1) throws XMLStreamException {
      try {
         var1.write(this.fDTD);
      } catch (IOException var3) {
         throw new XMLStreamException(var3);
      }
   }
}
