package weblogic.apache.xerces.stax.events;

import java.io.IOException;
import java.io.Writer;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EntityDeclaration;

public final class EntityDeclarationImpl extends XMLEventImpl implements EntityDeclaration {
   private final String fPublicId;
   private final String fSystemId;
   private final String fName;
   private final String fNotationName;

   public EntityDeclarationImpl(String var1, String var2, String var3, String var4, Location var5) {
      super(15, var5);
      this.fPublicId = var1;
      this.fSystemId = var2;
      this.fName = var3;
      this.fNotationName = var4;
   }

   public String getPublicId() {
      return this.fPublicId;
   }

   public String getSystemId() {
      return this.fSystemId;
   }

   public String getName() {
      return this.fName;
   }

   public String getNotationName() {
      return this.fNotationName;
   }

   public String getReplacementText() {
      return null;
   }

   public String getBaseURI() {
      return null;
   }

   public void writeAsEncodedUnicode(Writer var1) throws XMLStreamException {
      try {
         var1.write("<!ENTITY ");
         var1.write(this.fName);
         if (this.fPublicId != null) {
            var1.write(" PUBLIC \"");
            var1.write(this.fPublicId);
            var1.write("\" \"");
            var1.write(this.fSystemId);
            var1.write(34);
         } else {
            var1.write(" SYSTEM \"");
            var1.write(this.fSystemId);
            var1.write(34);
         }

         if (this.fNotationName != null) {
            var1.write(" NDATA ");
            var1.write(this.fNotationName);
         }

         var1.write(62);
      } catch (IOException var3) {
         throw new XMLStreamException(var3);
      }
   }
}
