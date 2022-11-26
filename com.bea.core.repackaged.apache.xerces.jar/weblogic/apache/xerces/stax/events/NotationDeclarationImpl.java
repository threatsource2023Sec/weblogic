package weblogic.apache.xerces.stax.events;

import java.io.IOException;
import java.io.Writer;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.NotationDeclaration;

public final class NotationDeclarationImpl extends XMLEventImpl implements NotationDeclaration {
   private final String fSystemId;
   private final String fPublicId;
   private final String fName;

   public NotationDeclarationImpl(String var1, String var2, String var3, Location var4) {
      super(14, var4);
      this.fName = var1;
      this.fPublicId = var2;
      this.fSystemId = var3;
   }

   public String getName() {
      return this.fName;
   }

   public String getPublicId() {
      return this.fPublicId;
   }

   public String getSystemId() {
      return this.fSystemId;
   }

   public void writeAsEncodedUnicode(Writer var1) throws XMLStreamException {
      try {
         var1.write("<!NOTATION ");
         if (this.fPublicId != null) {
            var1.write("PUBLIC \"");
            var1.write(this.fPublicId);
            var1.write(34);
            if (this.fSystemId != null) {
               var1.write(" \"");
               var1.write(this.fSystemId);
               var1.write(34);
            }
         } else {
            var1.write("SYSTEM \"");
            var1.write(this.fSystemId);
            var1.write(34);
         }

         var1.write(this.fName);
         var1.write(62);
      } catch (IOException var3) {
         throw new XMLStreamException(var3);
      }
   }
}
