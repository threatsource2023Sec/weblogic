package org.apache.xml.security.stax.impl.stax;

import java.io.IOException;
import java.io.Writer;
import javax.xml.stream.XMLStreamException;
import org.apache.xml.security.stax.ext.stax.XMLSecEntityDeclaration;

public class XMLSecEntityDeclarationImpl extends XMLSecEventBaseImpl implements XMLSecEntityDeclaration {
   private String name;

   public XMLSecEntityDeclarationImpl(String name) {
      this.name = name;
   }

   public String getPublicId() {
      return null;
   }

   public String getSystemId() {
      return null;
   }

   public String getName() {
      return this.name;
   }

   public String getNotationName() {
      return null;
   }

   public String getReplacementText() {
      return null;
   }

   public String getBaseURI() {
      return null;
   }

   public int getEventType() {
      return 15;
   }

   public boolean isEntityReference() {
      return true;
   }

   public void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {
      try {
         writer.write("<!ENTITY ");
         writer.write(this.getName());
         writer.write(" \"");
         String replacementText = this.getReplacementText();
         if (replacementText != null) {
            writer.write(replacementText);
         }

         writer.write("\">");
      } catch (IOException var3) {
         throw new XMLStreamException(var3);
      }
   }
}
