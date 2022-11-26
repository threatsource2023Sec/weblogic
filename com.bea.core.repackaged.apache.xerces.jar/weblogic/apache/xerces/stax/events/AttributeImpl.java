package weblogic.apache.xerces.stax.events;

import java.io.IOException;
import java.io.Writer;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;

public class AttributeImpl extends XMLEventImpl implements Attribute {
   private final boolean fIsSpecified;
   private final QName fName;
   private final String fValue;
   private final String fDtdType;

   public AttributeImpl(QName var1, String var2, String var3, boolean var4, Location var5) {
      this(10, var1, var2, var3, var4, var5);
   }

   protected AttributeImpl(int var1, QName var2, String var3, String var4, boolean var5, Location var6) {
      super(var1, var6);
      this.fName = var2;
      this.fValue = var3;
      this.fDtdType = var4;
      this.fIsSpecified = var5;
   }

   public final QName getName() {
      return this.fName;
   }

   public final String getValue() {
      return this.fValue;
   }

   public final String getDTDType() {
      return this.fDtdType;
   }

   public final boolean isSpecified() {
      return this.fIsSpecified;
   }

   public final void writeAsEncodedUnicode(Writer var1) throws XMLStreamException {
      try {
         String var2 = this.fName.getPrefix();
         if (var2 != null && var2.length() > 0) {
            var1.write(var2);
            var1.write(58);
         }

         var1.write(this.fName.getLocalPart());
         var1.write("=\"");
         var1.write(this.fValue);
         var1.write(34);
      } catch (IOException var3) {
         throw new XMLStreamException(var3);
      }
   }
}
