package org.python.apache.xerces.stax.events;

import java.io.IOException;
import java.io.Writer;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.ProcessingInstruction;

public final class ProcessingInstructionImpl extends XMLEventImpl implements ProcessingInstruction {
   private final String fTarget;
   private final String fData;

   public ProcessingInstructionImpl(String var1, String var2, Location var3) {
      super(3, var3);
      this.fTarget = var1 != null ? var1 : "";
      this.fData = var2;
   }

   public String getTarget() {
      return this.fTarget;
   }

   public String getData() {
      return this.fData;
   }

   public void writeAsEncodedUnicode(Writer var1) throws XMLStreamException {
      try {
         var1.write("<?");
         var1.write(this.fTarget);
         if (this.fData != null && this.fData.length() > 0) {
            var1.write(32);
            var1.write(this.fData);
         }

         var1.write("?>");
      } catch (IOException var3) {
         throw new XMLStreamException(var3);
      }
   }
}
