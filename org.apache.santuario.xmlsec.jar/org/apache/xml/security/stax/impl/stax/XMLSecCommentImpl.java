package org.apache.xml.security.stax.impl.stax;

import java.io.IOException;
import java.io.Writer;
import javax.xml.stream.XMLStreamException;
import org.apache.xml.security.stax.ext.stax.XMLSecComment;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;

public class XMLSecCommentImpl extends XMLSecEventBaseImpl implements XMLSecComment {
   private final String text;

   public XMLSecCommentImpl(String text, XMLSecStartElement parentXmlSecStartElement) {
      this.text = text;
      this.setParentXMLSecStartElement(parentXmlSecStartElement);
   }

   public String getText() {
      return this.text;
   }

   public int getEventType() {
      return 5;
   }

   public void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {
      try {
         writer.write("<!--");
         writer.write(this.getText());
         writer.write("-->");
      } catch (IOException var3) {
         throw new XMLStreamException(var3);
      }
   }
}
