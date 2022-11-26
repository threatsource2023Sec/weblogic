package weblogic.servlet.internal;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.application.descriptor.AbstractDescriptorLoader2;
import weblogic.application.descriptor.ReaderEvent2;
import weblogic.application.descriptor.VersionMunger;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.OldDescriptorCompatibility;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;

public class WebAppReader2 extends VersionMunger {
   private static boolean enableReordering = Boolean.getBoolean("weblogic.servlet.descriptor.enableReordering");
   private boolean requiresLeadingForwardSlash = false;
   private boolean inEjbRef = false;
   private boolean inEjbLink = false;
   private ArrayList taglibs = new ArrayList();
   private ReaderEvent2 jspConfig = null;

   public WebAppReader2(InputStream in, AbstractDescriptorLoader2 loader) throws XMLStreamException {
      super(in, loader, "weblogic.j2ee.descriptor.WebAppBeanImpl$SchemaHelper2", !enableReordering);
   }

   public String getDtdNamespaceURI() {
      return "http://xmlns.jcp.org/xml/ns/javaee";
   }

   public String getNamespaceURI() {
      String ns = super.getNamespaceURI();
      return ns != null && ns != "" ? ns : this.getDtdNamespaceURI();
   }

   protected VersionMunger.Continuation onStartElement(String localName) {
      if (!this.hasDTD()) {
         if ("web-app".equals(localName)) {
            this.checkAndUpdateVersionAttribute();
         }

         return CONTINUE;
      } else {
         this.requiresLeadingForwardSlash = this.requiresLeadingForwardSlash(this.getLocalName());
         if ("web-app".equals(localName)) {
            this.checkAndUpdateVersionAttribute();
         }

         if ("taglib".equals(localName)) {
            this.getTaglibs().add(this.currentEvent);
         }

         if ("ejb-ref".equals(localName)) {
            this.inEjbRef = true;
         }

         if ("ejb-link".equals(localName)) {
            this.inEjbLink = true;
         }

         return this.inEjbRef && "run-as".equals(localName) ? this.SKIP : CONTINUE;
      }
   }

   protected VersionMunger.Continuation onCharacters(String text) {
      if (!this.hasDTD()) {
         return CONTINUE;
      } else {
         if (this.inEjbLink) {
            this.replaceSlashWithPeriod(this.inEjbLink);
         }

         return CONTINUE;
      }
   }

   protected VersionMunger.Continuation onEndElement(String localName) {
      if (!this.hasDTD()) {
         return CONTINUE;
      } else {
         this.requiresLeadingForwardSlash = false;
         if ("ejb-ref".equals(localName)) {
            this.inEjbRef = false;
         }

         if ("ejb-link".equals(localName)) {
            this.inEjbLink = false;
         }

         if (this.getTaglibs().size() > 0 && "web-app".equals(localName)) {
            if (WebAppModule.DEBUG.isDebugEnabled()) {
               this.p("taglibs = " + this.getTaglibs());
            }

            System.out.flush();
            ReaderEvent2 adoptiveParent = this.lastEvent;
            SchemaHelper parentSchemaHelper = adoptiveParent.getSchemaHelper();
            if (WebAppModule.DEBUG.isDebugEnabled()) {
               this.p("lastEvent = " + this.lastEvent + ", parentSchemaHelper = " + parentSchemaHelper);
            }

            this.jspConfig = new ReaderEvent2(1, "jsp-config", adoptiveParent, adoptiveParent.getLocation());
            int propIndex = parentSchemaHelper.getPropertyIndex(this.jspConfig.getElementName());
            SchemaHelper jspConfigHelper = parentSchemaHelper.getSchemaHelper(propIndex);
            this.jspConfig.setSchemaHelper(jspConfigHelper);

            for(int i = 0; i < this.getTaglibs().size(); ++i) {
               ReaderEvent2 taglib = (ReaderEvent2)this.getTaglibs().get(i);
               taglib.getParent().getChildren().remove(taglib);
               this.jspConfig.adopt(taglib, jspConfigHelper);
            }

            adoptiveParent.adopt(this.jspConfig, parentSchemaHelper);
            this.getTaglibs().clear();
         }

         return CONTINUE;
      }
   }

   protected VersionMunger.Continuation onEndDocument() {
      if (!this.hasDTD()) {
         return CONTINUE;
      } else {
         this.orderChildren();
         return CONTINUE;
      }
   }

   private boolean requiresLeadingForwardSlash(String localName) {
      return "location".equals(localName);
   }

   public String getText() {
      if (WebAppModule.DEBUG.isDebugEnabled()) {
         this.p("** WebAppReader.getText()");
      }

      String txt = super.getText();
      if (!this.hasDTD()) {
         return txt;
      } else {
         if (txt != null) {
            String replaced = OldDescriptorCompatibility.canonicalize(this.currentEvent.getElementName(), txt);
            if (replaced != null) {
               if (WebAppModule.DEBUG.isDebugEnabled()) {
                  this.p("txt = " + txt + ", replaced = " + replaced);
               }

               this.currentEvent.getReaderEventInfo().setCharacters(replaced.toCharArray());
               return replaced;
            }
         }

         if (txt.charAt(0) != '/' && this.requiresLeadingForwardSlash) {
            txt = "/" + txt;
            if (WebAppModule.DEBUG.isDebugEnabled()) {
               this.p("txt, replaced = " + txt + " for " + this.currentEvent.getElementName());
            }

            this.currentEvent.getReaderEventInfo().setCharacters(txt.toCharArray());
         }

         return txt;
      }
   }

   public char[] getTextCharacters() {
      if (WebAppModule.DEBUG.isDebugEnabled()) {
         this.p("** WebAppReader2.getTextCharacters()");
      }

      char[] chars = super.getTextCharacters();
      if (!this.hasDTD()) {
         return chars;
      } else {
         if (chars != null) {
            String replaced = OldDescriptorCompatibility.canonicalize(this.currentEvent.getElementName(), new String(chars));
            if (replaced != null) {
               System.arraycopy(replaced.toCharArray(), 0, chars, 0, replaced.length());
               if (WebAppModule.DEBUG.isDebugEnabled()) {
                  this.p("chars = " + new String(chars) + ", replaced = " + replaced);
               }

               this.currentEvent.getReaderEventInfo().setCharacters(chars);
            }

            if (chars[0] != '/' && this.requiresLeadingForwardSlash) {
               char[] newChars = new char[chars.length + 1];
               System.arraycopy(chars, 0, newChars, 1, chars.length);
               newChars[0] = '/';
               chars = newChars;
               if (WebAppModule.DEBUG.isDebugEnabled()) {
                  this.p("txt, replaced = " + new String(newChars) + " for " + this.currentEvent.getElementName());
               }

               this.currentEvent.getReaderEventInfo().setCharacters(newChars);
            }
         }

         return chars;
      }
   }

   public String getElementText() throws XMLStreamException {
      if (WebAppModule.DEBUG.isDebugEnabled()) {
         this.p("** WebAppReader2.getElementText()");
      }

      String txt = super.getElementText();
      if (!this.hasDTD()) {
         return txt;
      } else {
         if (txt != null) {
            String replaced = OldDescriptorCompatibility.canonicalize(this.currentEvent.getElementName(), txt);
            if (replaced != null) {
               if (WebAppModule.DEBUG.isDebugEnabled()) {
                  this.p("txt = " + txt + ", replaced = " + replaced);
               }

               return replaced;
            }
         }

         return txt;
      }
   }

   private ArrayList getTaglibs() {
      if (this.taglibs == null) {
         this.taglibs = new ArrayList();
      }

      return this.taglibs;
   }

   protected String getLatestSchemaVersion() {
      return "4.0";
   }

   protected boolean enableCallbacksOnSchema() {
      return true;
   }

   protected boolean isOldSchema() {
      String nameSpaceURI = this.getNamespaceURI();
      if (nameSpaceURI != null && nameSpaceURI.indexOf("j2ee") != -1) {
         this.isOldSchema = true;
         this.versionInfo = "2.4";
         return this.isOldSchema;
      } else {
         int versionAttrIndex = this.getVersionAttributeIndex();
         if (versionAttrIndex == -1) {
            return false;
         } else {
            this.versionInfo = this.currentEvent.getReaderEventInfo().getAttributeValue(versionAttrIndex);
            return !this.getLatestSchemaVersion().equals(this.versionInfo);
         }
      }
   }

   private int getVersionAttributeIndex() {
      if (this.currentEvent.getElementName().equals("web-app")) {
         int count = this.currentEvent.getReaderEventInfo().getAttributeCount();

         for(int i = 0; i < count; ++i) {
            String s = this.currentEvent.getReaderEventInfo().getAttributeLocalName(i);
            if ("version".equals(s)) {
               return i;
            }
         }
      }

      return -1;
   }

   protected void transformOldSchema() throws XMLStreamException {
      if (this.currentEvent.getElementName().equals("web-app")) {
         int count = this.currentEvent.getReaderEventInfo().getAttributeCount();

         for(int i = 0; i < count; ++i) {
            String s = this.currentEvent.getReaderEventInfo().getAttributeLocalName(i);
            if ("version".equals(s)) {
               String attValue = this.currentEvent.getReaderEventInfo().getAttributeValue(i);
               if (this.versionInfo.equals("2.4")) {
                  if (attValue.equals("2.5")) {
                     throw new XMLStreamException("Unable to transform version 2.4 web application namespace to version 2.5.");
                  }

                  if (attValue.equals("3.0")) {
                     throw new XMLStreamException("Unable to transform version 2.4 web application namespace to version 3.0.");
                  }

                  if (attValue.equals("3.1")) {
                     throw new XMLStreamException("Unable to transform version 2.4 web application namespace to version 3.1.");
                  }

                  if (attValue.equals("4.0")) {
                     throw new XMLStreamException("Unable to transform version 2.4 web application namespace to version 4.0.");
                  }
               }

               if (attValue.equals("2.4") || attValue.equals("2.5") || attValue.equals("3.0") || attValue.equals("3.1")) {
                  this.versionInfo = attValue;
                  this.currentEvent.getReaderEventInfo().setAttributeValue("4.0", i);
               }
            }
         }

         this.transformNamespace("http://java.sun.com/xml/ns/javaee", this.currentEvent, "http://java.sun.com/xml/ns/j2ee");
         this.transformNamespace("http://xmlns.jcp.org/xml/ns/javaee", this.currentEvent, "http://java.sun.com/xml/ns/javaee");
      }

      this.tranformedNamespace = "http://xmlns.jcp.org/xml/ns/javaee";
   }

   public static void main(String[] args) throws Exception {
      if (args.length == 0) {
         usage();
         System.exit(-1);
      }

      String ddPath = args[0];
      String planPath = args.length > 1 && args[1].endsWith("plan.xml") ? args[1] : null;
      File altDD = new File(ddPath);
      InputStream in = null;
      File configDir = new File(".");
      DeploymentPlanBean plan = null;
      String moduleName = args.length > 2 ? args[2] : null;
      String schemaHelperClassName = "weblogic.j2ee.descriptor.WebAppBeanImpl$SchemaHelper2";
      String nameSpace = "http://java.sun.com/xml/ns/j2ee";
      AbstractDescriptorLoader2 loader;
      if (planPath != null) {
         if (moduleName == null) {
            usage();
            System.exit(-1);
         }

         loader = new AbstractDescriptorLoader2(new File(planPath), planPath) {
         };
         plan = (DeploymentPlanBean)loader.loadDescriptorBean();
      }

      loader = new AbstractDescriptorLoader2(altDD, configDir, plan, moduleName, ddPath) {
         protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
            if (WebAppModule.DEBUG.isDebugEnabled()) {
               WebAppModule.DEBUG.debug("call it...");
            }

            return new WebAppReader2(is, this);
         }
      };
      if (WebAppModule.DEBUG.isDebugEnabled()) {
         WebAppModule.DEBUG.debug("stamp out version munger for: " + ddPath);
      }

      System.out.flush();
      DescriptorBean db = loader.loadDescriptorBean();
      Descriptor d = db.getDescriptor();
      d.toXML(System.out);
   }

   private static void usage() {
      if (WebAppModule.DEBUG.isDebugEnabled()) {
         WebAppModule.DEBUG.debug("java weblogic.servlet.internal.WebAppReader2 <dd-filename> || <dd-filename> <plan-filename> <module-name>");
      }

   }

   private void p(String msg) {
      WebAppModule.DEBUG.debug(msg);
   }
}
