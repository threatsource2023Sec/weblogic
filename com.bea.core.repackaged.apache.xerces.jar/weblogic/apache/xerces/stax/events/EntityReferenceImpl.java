package weblogic.apache.xerces.stax.events;

import java.io.IOException;
import java.io.Writer;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EntityDeclaration;
import javax.xml.stream.events.EntityReference;

public final class EntityReferenceImpl extends XMLEventImpl implements EntityReference {
   private final String fName;
   private final EntityDeclaration fDecl;

   public EntityReferenceImpl(EntityDeclaration var1, Location var2) {
      this(var1 != null ? var1.getName() : "", var1, var2);
   }

   public EntityReferenceImpl(String var1, EntityDeclaration var2, Location var3) {
      super(9, var3);
      this.fName = var1 != null ? var1 : "";
      this.fDecl = var2;
   }

   public EntityDeclaration getDeclaration() {
      return this.fDecl;
   }

   public String getName() {
      return this.fName;
   }

   public void writeAsEncodedUnicode(Writer var1) throws XMLStreamException {
      try {
         var1.write(38);
         var1.write(this.fName);
         var1.write(59);
      } catch (IOException var3) {
         throw new XMLStreamException(var3);
      }
   }
}
