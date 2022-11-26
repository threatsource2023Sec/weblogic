package weblogic.servlet.internal;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.application.descriptor.AbstractDescriptorLoader2;
import weblogic.application.descriptor.VersionMunger;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.J2EELogger;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.servlet.internal.session.SessionConfigManager;

public class WlsWebAppReader2 extends VersionMunger {
   private static final Map nameChanges = new HashMap(3);
   private boolean inSessionParam = false;
   private boolean inJspParam = false;
   private boolean inContainerDescriptor = false;
   private String containerDescName = null;
   private final StringBuffer containerDescValue = new StringBuffer();
   private final List containerDescElements = new ArrayList();
   private Map servletDescriptors = new HashMap();
   private boolean inDeletedElement = false;
   private boolean isDeprecated = false;
   private boolean lookupFailed = false;
   boolean inParamName = false;
   String lastParamName = null;

   public WlsWebAppReader2(InputStream in, AbstractDescriptorLoader2 loader) throws XMLStreamException {
      super(in, loader, "weblogic.j2ee.descriptor.wl.WeblogicWebAppBeanImpl$SchemaHelper2", nameChanges, "http://xmlns.oracle.com/weblogic/weblogic-web-app");
   }

   public String getNamespaceURI() {
      String ns = super.getNamespaceURI();
      return ns != null && ns != "" ? ns : this.getDtdNamespaceURI();
   }

   protected VersionMunger.Continuation onStartElement(String localName) {
      if ("reference-descriptor".equals(localName)) {
         return this.SKIP;
      } else {
         if ("preprocessor".equals(localName) || "preprocessor-mapping".equals(localName)) {
            this.inDeletedElement = true;
         }

         if (this.inDeletedElement) {
            return this.SKIP;
         } else if ("session-param".equals(localName)) {
            this.inSessionParam = true;
            return this.SKIP;
         } else if ("jsp-param".equals(localName)) {
            this.inJspParam = true;
            return this.SKIP;
         } else if ("param-name".equals(localName)) {
            this.inParamName = true;
            return this.SKIP;
         } else if ("param-value".equals(localName)) {
            if (this.isDeprecated) {
               J2EELogger.logDeprecatedWeblogicParam(this.currentEvent.getParent().getElementName(), this.lastParamName);
               return this.SKIP;
            } else {
               if (this.lookupFailed) {
                  J2EELogger.logUnknownWeblogicParam(this.currentEvent.getParent().getElementName(), this.lastParamName);
                  if (this.inSessionParam) {
                     return this.SKIP;
                  }
               }

               this.currentEvent.setElementName(this.lastParamName);
               this.currentEvent.setDiscard(false);
               return CONTINUE;
            }
         } else {
            return CONTINUE;
         }
      }
   }

   protected VersionMunger.Continuation onCharacters(String text) {
      if (this.inDeletedElement) {
         return this.SKIP;
      } else if (this.inParamName) {
         this.lastParamName = this.getSessionElementName2(text);
         return this.SKIP;
      } else {
         return CONTINUE;
      }
   }

   String getSessionElementName2(String s) {
      String element = null;
      if (s != null) {
         if (s.trim().toLowerCase().length() == 0) {
            return s.trim();
         }

         if (this.inSessionParam) {
            element = (String)SessionConfigManager.SESSION_ELEMENTS_MAP.get(s.trim().toLowerCase());
            if (element == null) {
               this.lookupFailed = true;
            }
         }

         if (this.inJspParam) {
            element = (String)JSPManager.JSP_DESC_ELEMENTS_MAP.get(s.trim().toLowerCase());
            if (element == "warning") {
               this.isDeprecated = true;
            }

            if (element == null) {
               this.lookupFailed = true;
            }
         }
      }

      return element == null ? s.trim() : element;
   }

   protected VersionMunger.Continuation onEndElement(String localName) {
      if ("reference-descriptor".equals(localName)) {
         return this.SKIP;
      } else if (this.inParamName && "param-name".equals(localName)) {
         this.inParamName = false;
         return this.SKIP;
      } else if ("param-value".equals(localName)) {
         this.currentEvent.setElementName(this.lastParamName);
         this.isDeprecated = false;
         this.lookupFailed = false;
         this.lastParamName = null;
         return CONTINUE;
      } else if (!this.inDeletedElement) {
         if ("session-param".equals(localName)) {
            this.inSessionParam = false;
            return this.SKIP;
         } else if ("jsp-param".equals(localName)) {
            this.inJspParam = false;
            return this.SKIP;
         } else {
            if ("session-descriptor".equals(localName)) {
               this.lastEvent.orderChildren();
            }

            if ("jsp-descriptor".equals(localName)) {
               this.lastEvent.orderChildren();
            }

            return CONTINUE;
         }
      } else {
         if ("preprocessor".equals(localName) || "preprocessor-mapping".equals(localName)) {
            this.inDeletedElement = false;
         }

         return this.SKIP;
      }
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
      String schemaHelperClassName = "weblogic.j2ee.descriptor.wl.WeblogicWebAppBeanImpl$SchemaHelper2";
      String nameSpace = "http://www.bea.com/ns/weblogic/90";
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
            return new WlsWebAppReader2(is, this);
         }
      };
      if (WebAppModule.DEBUG.isDebugEnabled()) {
         WebAppModule.DEBUG.debug("stamp out version munger...");
      }

      System.out.flush();
      DescriptorBean db = loader.loadDescriptorBean();
      Descriptor d = db.getDescriptor();
      d.toXML(System.out);
   }

   private static void usage() {
      if (WebAppModule.DEBUG.isDebugEnabled()) {
         WebAppModule.DEBUG.debug("java weblogic.servlet.internal.WlsWebAppReader2 <dd-filename> || <dd-filename> <plan-filename> <module-name>");
      }

   }

   static {
      nameChanges.put("global-role", "externally-defined");
      nameChanges.put("resource-env-descriptor", "resource-env-description");
      nameChanges.put("res-env-ref-name", "resource-env-ref-name");
   }
}
