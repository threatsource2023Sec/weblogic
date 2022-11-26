package weblogic.apache.xerces.util;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.apache.xerces.xni.parser.XMLInputSource;

public final class StAXInputSource extends XMLInputSource {
   private final XMLStreamReader fStreamReader;
   private final XMLEventReader fEventReader;
   private final boolean fConsumeRemainingContent;

   public StAXInputSource(XMLStreamReader var1) {
      this(var1, false);
   }

   public StAXInputSource(XMLStreamReader var1, boolean var2) {
      super((String)null, getStreamReaderSystemId(var1), (String)null);
      if (var1 == null) {
         throw new IllegalArgumentException("XMLStreamReader parameter cannot be null.");
      } else {
         this.fStreamReader = var1;
         this.fEventReader = null;
         this.fConsumeRemainingContent = var2;
      }
   }

   public StAXInputSource(XMLEventReader var1) {
      this(var1, false);
   }

   public StAXInputSource(XMLEventReader var1, boolean var2) {
      super((String)null, getEventReaderSystemId(var1), (String)null);
      if (var1 == null) {
         throw new IllegalArgumentException("XMLEventReader parameter cannot be null.");
      } else {
         this.fStreamReader = null;
         this.fEventReader = var1;
         this.fConsumeRemainingContent = var2;
      }
   }

   public XMLStreamReader getXMLStreamReader() {
      return this.fStreamReader;
   }

   public XMLEventReader getXMLEventReader() {
      return this.fEventReader;
   }

   public boolean shouldConsumeRemainingContent() {
      return this.fConsumeRemainingContent;
   }

   public void setSystemId(String var1) {
      throw new UnsupportedOperationException("Cannot set the system ID on a StAXInputSource");
   }

   private static String getStreamReaderSystemId(XMLStreamReader var0) {
      return var0 != null ? var0.getLocation().getSystemId() : null;
   }

   private static String getEventReaderSystemId(XMLEventReader var0) {
      try {
         if (var0 != null) {
            return var0.peek().getLocation().getSystemId();
         }
      } catch (XMLStreamException var2) {
      }

      return null;
   }
}
