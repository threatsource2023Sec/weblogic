package org.apache.xml.security.stax.impl.stax;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import org.apache.xml.security.stax.ext.stax.XMLSecEndElement;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;

public class XMLSecEndElementImpl extends XMLSecEventBaseImpl implements XMLSecEndElement {
   private final QName elementName;

   public XMLSecEndElementImpl(QName elementName, XMLSecStartElement parentXmlSecStartElement) {
      this.elementName = elementName;
      this.setParentXMLSecStartElement(parentXmlSecStartElement);
   }

   public QName getName() {
      return this.elementName;
   }

   public Iterator getNamespaces() {
      return getEmptyIterator();
   }

   public int getEventType() {
      return 2;
   }

   public boolean isEndElement() {
      return true;
   }

   public XMLSecEndElement asEndElement() {
      return this;
   }

   public void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {
      try {
         writer.write("</");
         String prefix = this.getName().getPrefix();
         if (prefix != null && !prefix.isEmpty()) {
            writer.write(this.getName().getPrefix());
            writer.write(58);
         }

         writer.write(this.getName().getLocalPart());
         writer.write(62);
      } catch (IOException var3) {
         throw new XMLStreamException(var3);
      }
   }
}
