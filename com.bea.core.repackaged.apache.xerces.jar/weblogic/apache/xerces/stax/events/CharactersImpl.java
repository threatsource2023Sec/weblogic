package weblogic.apache.xerces.stax.events;

import java.io.IOException;
import java.io.Writer;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import weblogic.apache.xerces.util.XMLChar;

public final class CharactersImpl extends XMLEventImpl implements Characters {
   private final String fData;

   public CharactersImpl(String var1, int var2, Location var3) {
      super(var2, var3);
      this.fData = var1 != null ? var1 : "";
   }

   public String getData() {
      return this.fData;
   }

   public boolean isWhiteSpace() {
      int var1 = this.fData != null ? this.fData.length() : 0;
      if (var1 == 0) {
         return false;
      } else {
         for(int var2 = 0; var2 < var1; ++var2) {
            if (!XMLChar.isSpace(this.fData.charAt(var2))) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean isCData() {
      return 12 == this.getEventType();
   }

   public boolean isIgnorableWhiteSpace() {
      return 6 == this.getEventType();
   }

   public void writeAsEncodedUnicode(Writer var1) throws XMLStreamException {
      try {
         var1.write(this.fData);
      } catch (IOException var3) {
         throw new XMLStreamException(var3);
      }
   }
}
