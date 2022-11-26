package org.apache.xml.security.stax.impl.stax;

import java.io.IOException;
import java.io.Writer;
import javax.xml.stream.XMLStreamException;
import org.apache.xml.security.stax.ext.stax.XMLSecProcessingInstruction;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;

public class XMLSecProcessingInstructionImpl extends XMLSecEventBaseImpl implements XMLSecProcessingInstruction {
   private final String data;
   private final String target;

   public XMLSecProcessingInstructionImpl(String target, String data, XMLSecStartElement parentXmlSecStartElement) {
      this.target = target;
      this.data = data;
      this.setParentXMLSecStartElement(parentXmlSecStartElement);
   }

   public String getTarget() {
      return this.target;
   }

   public String getData() {
      return this.data;
   }

   public int getEventType() {
      return 3;
   }

   public boolean isProcessingInstruction() {
      return true;
   }

   public void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {
      try {
         writer.write("<?");
         writer.write(this.getTarget());
         String data = this.getData();
         if (data != null && !data.isEmpty()) {
            writer.write(32);
            writer.write(data);
         }

         writer.write("?>");
      } catch (IOException var3) {
         throw new XMLStreamException(var3);
      }
   }
}
