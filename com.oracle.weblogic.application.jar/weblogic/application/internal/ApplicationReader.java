package weblogic.application.internal;

import java.io.InputStream;
import javax.xml.stream.XMLStreamException;
import weblogic.application.descriptor.AbstractDescriptorLoader2;
import weblogic.application.descriptor.VersionMunger;

public class ApplicationReader extends VersionMunger {
   private boolean inWeb;
   private boolean hasSetContextRoot;
   private int crCount = 0;

   public ApplicationReader(InputStream in, AbstractDescriptorLoader2 loader) throws XMLStreamException {
      super(in, loader, "weblogic.j2ee.descriptor.ApplicationBeanImpl$SchemaHelper2");
   }

   public String getDtdNamespaceURI() {
      return "http://xmlns.jcp.org/xml/ns/javaee";
   }

   protected VersionMunger.Continuation onStartElement(String localName) {
      if (!this.hasDTD()) {
         if ("application".equals(localName)) {
            this.checkAndUpdateVersionAttribute();
         }

         return CONTINUE;
      } else {
         if ("application".equals(localName)) {
            this.checkAndUpdateVersionAttribute();
         }

         if ("web".equals(localName)) {
            this.inWeb = true;
            this.hasSetContextRoot = false;
         } else if ("context-root".equals(localName)) {
            this.hasSetContextRoot = true;
         }

         return CONTINUE;
      }
   }

   protected VersionMunger.Continuation onEndElement(String localName) {
      if (!this.hasDTD()) {
         return CONTINUE;
      } else {
         if ("web".equals(localName)) {
            assert this.inWeb;

            this.inWeb = false;
            if (!this.hasSetContextRoot) {
               this.pushStartElementLastEvent("context-root");
               this.pushCharacters("__BEA_WLS_INTERNAL_UNSET_CONTEXT_ROOT_" + this.crCount++);
               this.pushEndElement("context-root");
               this.pushEndElement("web");
               return this.USE_BUFFER;
            }
         }

         return CONTINUE;
      }
   }

   protected boolean isOldSchema() {
      String nameSpaceURI = this.getNamespaceURI();
      if (nameSpaceURI != null) {
         if (nameSpaceURI.indexOf("j2ee") != -1) {
            return true;
         }

         if (nameSpaceURI.contains("http://java.sun.com/xml/ns/javaee")) {
            return true;
         }
      }

      if (this.currentEvent.getElementName().equals("application")) {
         int count = this.currentEvent.getReaderEventInfo().getAttributeCount();

         for(int i = 0; i < count; ++i) {
            String s = this.currentEvent.getReaderEventInfo().getAttributeLocalName(i);
            String attValue = this.currentEvent.getReaderEventInfo().getAttributeValue(i);
            if (attValue.equals("1.4") || attValue.equals("5") || attValue.equals("6") || attValue.equals("7")) {
               return true;
            }
         }
      }

      return false;
   }

   protected void transformOldSchema() {
      if (this.currentEvent.getElementName().equals("application")) {
         int count = this.currentEvent.getReaderEventInfo().getAttributeCount();

         for(int i = 0; i < count; ++i) {
            this.currentEvent.getReaderEventInfo().getAttributeLocalName(i);
            String attValue = this.currentEvent.getReaderEventInfo().getAttributeValue(i);
            if (attValue.equals("1.4") || attValue.equals("5") || attValue.equals("6") || attValue.equals("7")) {
               this.versionInfo = attValue;
               this.currentEvent.getReaderEventInfo().setAttributeValue("8", i);
            }
         }

         this.transformNamespace("http://java.sun.com/xml/ns/javaee", this.currentEvent, "http://java.sun.com/xml/ns/j2ee");
         this.transformNamespace("http://xmlns.jcp.org/xml/ns/javaee", this.currentEvent, "http://java.sun.com/xml/ns/javaee");
      }

      this.tranformedNamespace = "http://xmlns.jcp.org/xml/ns/javaee";
   }

   protected String getLatestSchemaVersion() {
      return "8";
   }

   protected boolean enableCallbacksOnSchema() {
      return true;
   }
}
