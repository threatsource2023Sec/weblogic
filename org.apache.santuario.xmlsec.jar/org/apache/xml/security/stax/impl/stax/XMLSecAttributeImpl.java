package org.apache.xml.security.stax.impl.stax;

import java.io.IOException;
import java.io.Writer;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import org.apache.xml.security.stax.ext.stax.XMLSecAttribute;
import org.apache.xml.security.stax.ext.stax.XMLSecNamespace;

public class XMLSecAttributeImpl extends XMLSecEventBaseImpl implements XMLSecAttribute {
   private final QName name;
   private final String value;
   private XMLSecNamespace attributeNamespace;

   public XMLSecAttributeImpl(QName name, String value) {
      this.name = name;
      this.value = value;
   }

   public int compareTo(XMLSecAttribute o) {
      int namespacePartCompare = this.name.getNamespaceURI().compareTo(o.getName().getNamespaceURI());
      return namespacePartCompare != 0 ? namespacePartCompare : this.name.getLocalPart().compareTo(o.getName().getLocalPart());
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof XMLSecAttribute)) {
         return false;
      } else {
         XMLSecAttribute comparableAttribute = (XMLSecAttribute)obj;
         return comparableAttribute.hashCode() != this.hashCode() ? false : comparableAttribute.getName().getLocalPart().equals(this.name.getLocalPart());
      }
   }

   public int hashCode() {
      return this.name.getLocalPart().hashCode();
   }

   public XMLSecNamespace getAttributeNamespace() {
      if (this.attributeNamespace == null) {
         this.attributeNamespace = XMLSecNamespaceImpl.getInstance(this.name.getPrefix(), this.name.getNamespaceURI());
      }

      return this.attributeNamespace;
   }

   public QName getName() {
      return this.name;
   }

   public String getValue() {
      return this.value;
   }

   public String getDTDType() {
      return "CDATA";
   }

   public boolean isSpecified() {
      return true;
   }

   public int getEventType() {
      return 10;
   }

   public boolean isAttribute() {
      return true;
   }

   public void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {
      try {
         String prefix = this.getName().getPrefix();
         if (prefix != null && !prefix.isEmpty()) {
            writer.write(prefix);
            writer.write(58);
         }

         writer.write(this.getName().getLocalPart());
         writer.write("=\"");
         this.writeEncoded(writer, this.getValue());
         writer.write("\"");
      } catch (IOException var3) {
         throw new XMLStreamException(var3);
      }
   }

   private void writeEncoded(Writer writer, String text) throws IOException {
      int length = text.length();
      int i = 0;

      int idx;
      for(idx = 0; i < length; ++i) {
         char c = text.charAt(i);
         switch (c) {
            case '"':
               writer.write(text, idx, i - idx);
               writer.write("&quot;");
               idx = i + 1;
               break;
            case '&':
               writer.write(text, idx, i - idx);
               writer.write("&amp;");
               idx = i + 1;
         }
      }

      writer.write(text, idx, length - idx);
   }
}
