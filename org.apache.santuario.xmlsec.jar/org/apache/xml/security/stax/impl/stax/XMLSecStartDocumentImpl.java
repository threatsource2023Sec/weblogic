package org.apache.xml.security.stax.impl.stax;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import javax.xml.stream.XMLStreamException;
import org.apache.xml.security.stax.ext.stax.XMLSecStartDocument;

public class XMLSecStartDocumentImpl extends XMLSecEventBaseImpl implements XMLSecStartDocument {
   private final String systemId;
   private final String characterEncodingScheme;
   private final Boolean isStandAlone;
   private String version = "1.0";

   public XMLSecStartDocumentImpl(String systemId, String characterEncodingScheme, Boolean standAlone, String version) {
      this.systemId = systemId;
      this.characterEncodingScheme = characterEncodingScheme;
      this.isStandAlone = standAlone;
      this.version = version;
   }

   public int getEventType() {
      return 7;
   }

   public String getSystemId() {
      return this.systemId != null ? this.systemId : "";
   }

   public String getCharacterEncodingScheme() {
      return this.characterEncodingScheme != null ? this.characterEncodingScheme : StandardCharsets.UTF_8.name();
   }

   public boolean encodingSet() {
      return this.characterEncodingScheme != null;
   }

   public boolean isStandalone() {
      return this.isStandAlone != null && this.isStandAlone;
   }

   public boolean standaloneSet() {
      return this.isStandAlone != null;
   }

   public String getVersion() {
      return this.version;
   }

   public boolean isStartDocument() {
      return true;
   }

   public XMLSecStartDocument asStartDocument() {
      return this;
   }

   public void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {
      try {
         writer.write("<?xml version=\"");
         if (this.getVersion() != null && !this.getVersion().isEmpty()) {
            writer.write(this.getVersion());
         } else {
            writer.write("1.0");
         }

         writer.write(34);
         if (this.encodingSet()) {
            writer.write(" encoding=\"");
            writer.write(this.getCharacterEncodingScheme());
            writer.write(34);
         }

         if (this.standaloneSet()) {
            if (this.isStandalone()) {
               writer.write(" standalone=\"yes\"");
            } else {
               writer.write(" standalone=\"no\"");
            }
         }

         writer.write(" ?>");
      } catch (IOException var3) {
         throw new XMLStreamException(var3);
      }
   }
}
