package org.apache.xml.security.stax.impl.stax;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.WeakHashMap;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import org.apache.xml.security.stax.ext.stax.XMLSecNamespace;

public class XMLSecNamespaceImpl extends XMLSecEventBaseImpl implements XMLSecNamespace {
   private static final Map xmlSecNamespaceMap = new WeakHashMap();
   private String prefix;
   private final String uri;
   private QName qName;

   private XMLSecNamespaceImpl(String prefix, String uri) {
      this.prefix = prefix;
      this.uri = uri;
   }

   public static XMLSecNamespace getInstance(String prefix, String uri) {
      String prefixToUse = prefix;
      if (prefix == null) {
         prefixToUse = "";
      }

      String uriToUse = uri;
      if (uri == null) {
         uriToUse = "";
      }

      Map nsMap = (Map)xmlSecNamespaceMap.get(prefixToUse);
      XMLSecNamespaceImpl xmlSecNamespace;
      if (nsMap != null) {
         XMLSecNamespace xmlSecNamespace = (XMLSecNamespace)nsMap.get(uriToUse);
         if (xmlSecNamespace != null) {
            return xmlSecNamespace;
         } else {
            xmlSecNamespace = new XMLSecNamespaceImpl(prefixToUse, uriToUse);
            nsMap.put(uriToUse, xmlSecNamespace);
            return xmlSecNamespace;
         }
      } else {
         Map nsMap = new WeakHashMap();
         xmlSecNamespace = new XMLSecNamespaceImpl(prefixToUse, uriToUse);
         nsMap.put(uriToUse, xmlSecNamespace);
         xmlSecNamespaceMap.put(prefixToUse, nsMap);
         return xmlSecNamespace;
      }
   }

   public int compareTo(XMLSecNamespace o) {
      return this.prefix.compareTo(o.getPrefix());
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof XMLSecNamespace)) {
         return false;
      } else {
         XMLSecNamespace comparableNamespace = (XMLSecNamespace)obj;
         return comparableNamespace.hashCode() != this.hashCode() ? false : comparableNamespace.getPrefix().equals(this.prefix);
      }
   }

   public int hashCode() {
      return this.prefix.hashCode();
   }

   public QName getName() {
      if (this.qName == null) {
         this.qName = new QName("http://www.w3.org/2000/xmlns/", this.prefix);
      }

      return this.qName;
   }

   public String getValue() {
      return this.uri;
   }

   public String getDTDType() {
      return "CDATA";
   }

   public boolean isSpecified() {
      return true;
   }

   public String getNamespaceURI() {
      return this.uri;
   }

   public String getPrefix() {
      return this.prefix;
   }

   public boolean isDefaultNamespaceDeclaration() {
      return this.prefix.length() == 0;
   }

   public int getEventType() {
      return 13;
   }

   public boolean isNamespace() {
      return true;
   }

   public void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {
      try {
         writer.write("xmlns");
         if (this.getPrefix() != null && !this.getPrefix().isEmpty()) {
            writer.write(58);
            writer.write(this.getPrefix());
         }

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

   public String toString() {
      return this.prefix != null && !this.prefix.isEmpty() ? "xmlns:" + this.prefix + "=\"" + this.uri + "\"" : "xmlns=\"" + this.uri + "\"";
   }
}
