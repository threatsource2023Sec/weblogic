package weblogic.apache.xerces.stax.events;

import java.io.IOException;
import java.io.Writer;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartDocument;

public final class StartDocumentImpl extends XMLEventImpl implements StartDocument {
   private final String fCharEncoding;
   private final boolean fEncodingSet;
   private final String fVersion;
   private final boolean fIsStandalone;
   private final boolean fStandaloneSet;

   public StartDocumentImpl(String var1, boolean var2, boolean var3, boolean var4, String var5, Location var6) {
      super(7, var6);
      this.fCharEncoding = var1;
      this.fEncodingSet = var2;
      this.fIsStandalone = var3;
      this.fStandaloneSet = var4;
      this.fVersion = var5;
   }

   public String getSystemId() {
      return this.getLocation().getSystemId();
   }

   public String getCharacterEncodingScheme() {
      return this.fCharEncoding;
   }

   public boolean encodingSet() {
      return this.fEncodingSet;
   }

   public boolean isStandalone() {
      return this.fIsStandalone;
   }

   public boolean standaloneSet() {
      return this.fStandaloneSet;
   }

   public String getVersion() {
      return this.fVersion;
   }

   public void writeAsEncodedUnicode(Writer var1) throws XMLStreamException {
      try {
         var1.write("<?xml version=\"");
         var1.write(this.fVersion != null && this.fVersion.length() > 0 ? this.fVersion : "1.0");
         var1.write(34);
         if (this.encodingSet()) {
            var1.write(" encoding=\"");
            var1.write(this.fCharEncoding);
            var1.write(34);
         }

         if (this.standaloneSet()) {
            var1.write(" standalone=\"");
            var1.write(this.fIsStandalone ? "yes" : "no");
            var1.write(34);
         }

         var1.write("?>");
      } catch (IOException var3) {
         throw new XMLStreamException(var3);
      }
   }
}
