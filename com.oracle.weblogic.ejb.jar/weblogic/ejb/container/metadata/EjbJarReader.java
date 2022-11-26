package weblogic.ejb.container.metadata;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import weblogic.application.descriptor.AbstractDescriptorLoader2;
import weblogic.application.descriptor.ReaderEvent2;
import weblogic.application.descriptor.VersionMunger;
import weblogic.j2ee.OldDescriptorCompatibility;

final class EjbJarReader extends VersionMunger {
   private static final String LATEST_SCHEMA_VERSION = "3.2";
   private static final Map JMS_NAME_CONVERSION = new HashMap(4);
   private boolean doneVersion = false;
   private boolean inIcon = false;
   private boolean isEJB11 = false;
   private boolean inEnvEntryValue = false;
   private boolean inEjbName = false;
   private boolean inEjbLink = false;
   private boolean inActivationConfig = false;
   private boolean inQueryMethod = false;
   private boolean inMethodIntf = false;

   public EjbJarReader(InputStream delegate, AbstractDescriptorLoader2 loader) throws XMLStreamException {
      super(delegate, loader, "weblogic.j2ee.descriptor.EjbJarBeanImpl$SchemaHelper2");
   }

   private boolean isIcon(String localName) {
      return "small-icon".equals(localName) || "large-icon".equals(localName);
   }

   public String getDtdNamespaceURI() {
      return "http://xmlns.jcp.org/xml/ns/javaee";
   }

   public void initDtdText(String dtdText) {
      if (dtdText.lastIndexOf("DTD Enterprise JavaBeans 1.1") > 0) {
         this.isEJB11 = true;
      }

   }

   private boolean needsCMPVersion(String localName) {
      if (this.isEJB11 && !this.doneVersion && "cmp-field".equals(localName)) {
         this.doneVersion = true;
         return true;
      } else {
         return false;
      }
   }

   private VersionMunger.Continuation startIcon(String localName) {
      this.currentEvent.discard();
      this.inIcon = true;
      this.forceSkipParent = true;
      this.pushStartElement("icon");
      this.pushStartElementWithStackAsParent(localName);
      return CONTINUE;
   }

   private VersionMunger.Continuation continueIcon(String localName) {
      this.currentEvent.discard();
      this.pushStartElementWithStackAsParent(localName);
      return CONTINUE;
   }

   private VersionMunger.Continuation endIcon() {
      this.inIcon = false;
      this.pushEndElement("icon");
      this.forceSkipParent = false;
      return this.USE_BUFFER;
   }

   private VersionMunger.Continuation cmp11Version() {
      this.pushStartElement("cmp-version");
      this.pushCharacters("1.x".toCharArray());
      this.pushEndElement("cmp-version");
      return this.USE_BUFFER;
   }

   private boolean isJMSProperty(String localName) {
      return JMS_NAME_CONVERSION.containsKey(localName);
   }

   private String convertJMSName(String n) {
      String s = (String)JMS_NAME_CONVERSION.get(n);
      return s == null ? n : s;
   }

   private VersionMunger.Continuation startJMSProperty(String localName) {
      if (!this.inActivationConfig) {
         this.forceSkipParent = true;
         this.pushStartElement("activation-config");
         this.inActivationConfig = true;
      }

      this.pushStartElementWithStackAsParent("activation-config-property");
      this.pushStartElementWithStackAsParent("activation-config-property-name");
      this.pushCharacters(this.convertJMSName(localName).toCharArray());
      this.pushEndElement("activation-config-property-name");
      this.pushStartElementWithStackAsParent("activation-config-property-value");
      this.currentEvent.discard();
      return this.USE_BUFFER;
   }

   private VersionMunger.Continuation endActivationConfig() {
      this.inActivationConfig = false;
      this.forceSkipParent = false;
      this.pushEndElement("activation-config");
      return this.USE_BUFFER;
   }

   public VersionMunger.Continuation onStartElement(String localName) {
      if ("query-method".equals(localName)) {
         this.inQueryMethod = true;
      }

      if (this.inQueryMethod && "method-intf".equals(localName)) {
         this.inMethodIntf = true;
         return this.SKIP;
      } else {
         if ("ejb-name".equals(localName)) {
            this.doneVersion = false;
         }

         if (this.isIcon(localName)) {
            return !this.inIcon ? this.startIcon(localName) : this.continueIcon(localName);
         } else if (this.inIcon) {
            return this.endIcon();
         } else if (this.needsCMPVersion(localName)) {
            return this.cmp11Version();
         } else if ("message-driven-destination".equals(localName)) {
            return this.SKIP;
         } else if (this.isJMSProperty(localName)) {
            return this.startJMSProperty(localName);
         } else if (this.inActivationConfig) {
            return this.endActivationConfig();
         } else {
            if ("env-entry-value".equals(localName)) {
               this.inEnvEntryValue = true;
            } else if ("ejb-name".equals(localName)) {
               this.inEjbName = true;
            } else if ("ejb-link".equals(localName)) {
               this.inEjbLink = true;
            }

            return CONTINUE;
         }
      }
   }

   protected VersionMunger.Continuation onCharacters(String s) {
      if (this.inMethodIntf) {
         return this.SKIP;
      } else {
         if (this.currentEvent.isDiscarded() && this.stack.size() > 0) {
            ReaderEvent2 stackEvent = (ReaderEvent2)this.stack.peek();
            stackEvent.getReaderEventInfo().setCharacters(s.toCharArray());
         }

         if (this.inEnvEntryValue && s != null && s.length() > 0 && (this.hasDTD() || this.isOldSchema())) {
            this.currentEvent.getReaderEventInfo().setCharacters(s.trim().toCharArray());
         }

         if (this.inEjbName || this.inEjbLink) {
            this.replaceSlashWithPeriod(this.inEjbLink);
         }

         return CONTINUE;
      }
   }

   public VersionMunger.Continuation onEndElement(String localName) {
      if (this.inQueryMethod) {
         if ("query-method".equals(localName)) {
            this.inQueryMethod = false;
         } else if ("method-intf".equals(localName)) {
            this.inMethodIntf = false;
            return this.SKIP;
         }
      }

      if ("message-driven-destination".equals(localName)) {
         return this.SKIP;
      } else if (this.isJMSProperty(localName)) {
         this.pushEndElement("activation-config-property-value");
         this.pushEndElement("activation-config-property");
         return this.USE_BUFFER;
      } else if (this.inActivationConfig) {
         this.inActivationConfig = false;
         this.forceSkipParent = false;
         this.pushEndElement("activation-config");
         this.pushEndElement(localName);
         return this.USE_BUFFER;
      } else {
         if (this.isIcon(localName)) {
            this.pushEndElement(localName);
         }

         if ("env-entry-value".equals(localName)) {
            this.inEnvEntryValue = false;
         } else if ("ejb-name".equals(localName)) {
            this.inEjbName = false;
         } else if ("ejb-link".equals(localName)) {
            this.inEjbLink = false;
         }

         return CONTINUE;
      }
   }

   public VersionMunger.Continuation onEndDocument() {
      if (this.inActivationConfig) {
         this.pushEndElement("activation-config");
         return this.USE_BUFFER;
      } else {
         this.orderChildren();
         return CONTINUE;
      }
   }

   protected String getLatestSchemaVersion() {
      return "3.2";
   }

   protected boolean isOldSchema() {
      String nameSpaceURI = this.getNamespaceURI();
      if (nameSpaceURI != null && nameSpaceURI.indexOf("j2ee") != -1) {
         return true;
      } else {
         if (this.currentEvent.getElementName().equals("ejb-jar")) {
            int count = this.currentEvent.getReaderEventInfo().getAttributeCount();

            for(int i = 0; i < count; ++i) {
               String s = this.currentEvent.getReaderEventInfo().getAttributeLocalName(i);
               String attValue = this.currentEvent.getReaderEventInfo().getAttributeValue(i);
               if (attValue.equals("3.0") || attValue.equals("3.1")) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   protected void transformOldSchema() {
      if (this.currentEvent.getElementName().equals("ejb-jar")) {
         int count = this.currentEvent.getReaderEventInfo().getAttributeCount();

         for(int i = 0; i < count; ++i) {
            String s = this.currentEvent.getReaderEventInfo().getAttributeLocalName(i);
            String attValue = this.currentEvent.getReaderEventInfo().getAttributeValue(i);
            if (attValue.equals("2.1") || attValue.equals("3.0") || attValue.equals("3.1")) {
               this.versionInfo = attValue;
               this.currentEvent.getReaderEventInfo().setAttributeValue("3.2", i);
            }
         }

         this.transformNamespace("http://java.sun.com/xml/ns/javaee", this.currentEvent, "http://java.sun.com/xml/ns/j2ee");
         this.transformNamespace("http://xmlns.jcp.org/xml/ns/javaee", this.currentEvent, "http://java.sun.com/xml/ns/javaee");
      }

      this.tranformedNamespace = "http://xmlns.jcp.org/xml/ns/javaee";
   }

   public String getText() {
      if (this.debug) {
         System.out.println("** EjbJarReader.getText() " + this.currentEvent.getElementName());
      }

      String txt = super.getText().trim();
      if (txt != null) {
         String replaced = OldDescriptorCompatibility.canonicalize(this.currentEvent.getElementName(), txt);
         if (replaced != null) {
            if (this.debug) {
               System.out.println("txt = " + txt + ", replaced = " + replaced);
            }

            this.currentEvent.getReaderEventInfo().setCharacters(replaced.toCharArray());
            return replaced;
         }
      }

      return txt;
   }

   public char[] getTextCharacters() {
      if (this.debug) {
         System.out.println("** EjbJarReader.getTextCharacters()" + this.currentEvent.getElementName());
      }

      char[] chars = super.getTextCharacters();
      if (chars != null) {
         String replaced = OldDescriptorCompatibility.canonicalize(this.currentEvent.getElementName(), new String(chars));
         if (replaced != null) {
            System.arraycopy(replaced.toCharArray(), 0, chars, 0, replaced.length());
            if (this.debug) {
               System.out.println("chars = " + new String(chars) + ", replaced = " + replaced);
            }

            this.currentEvent.getReaderEventInfo().setCharacters(chars);
         }
      }

      return chars;
   }

   public String getElementText() throws XMLStreamException {
      if (this.debug) {
         System.out.println("** EjbJarReader.getElementText()" + this.currentEvent.getElementName());
      }

      String txt = super.getElementText();
      if (txt != null) {
         String replaced = OldDescriptorCompatibility.canonicalize(this.currentEvent.getElementName(), txt);
         if (replaced != null) {
            if (this.debug) {
               System.out.println("txt = " + txt + ", replaced = " + replaced);
            }

            return replaced;
         }
      }

      return txt;
   }

   public boolean supportsValidation() {
      return true;
   }

   static {
      JMS_NAME_CONVERSION.put("acknowledge-mode", "acknowledgeMode");
      JMS_NAME_CONVERSION.put("message-selector", "messageSelector");
      JMS_NAME_CONVERSION.put("destination-type", "destinationType");
      JMS_NAME_CONVERSION.put("subscription-durability", "subscriptionDurability");
   }
}
