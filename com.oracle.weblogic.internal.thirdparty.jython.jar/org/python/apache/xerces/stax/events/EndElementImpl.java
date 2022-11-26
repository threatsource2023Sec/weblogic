package org.python.apache.xerces.stax.events;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;

public final class EndElementImpl extends ElementImpl implements EndElement {
   public EndElementImpl(QName var1, Iterator var2, Location var3) {
      super(var1, false, var2, var3);
   }

   public void writeAsEncodedUnicode(Writer var1) throws XMLStreamException {
      try {
         var1.write("</");
         QName var2 = this.getName();
         String var3 = var2.getPrefix();
         if (var3 != null && var3.length() > 0) {
            var1.write(var3);
            var1.write(58);
         }

         var1.write(var2.getLocalPart());
         var1.write(62);
      } catch (IOException var4) {
         throw new XMLStreamException(var4);
      }
   }
}
