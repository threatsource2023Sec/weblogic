package weblogic.ejb.container.metadata;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import weblogic.application.descriptor.AbstractDescriptorLoader2;
import weblogic.application.descriptor.VersionMunger;

final class WeblogicEjbJarReader extends VersionMunger {
   private boolean inJndiName;
   private boolean inLocalJndiName;
   private boolean inJndiBinding;
   private boolean inBuzJndiNameMap;
   private boolean buzRemoteJndiNameFound;
   private boolean inBusniessRemote;
   private String businessIntf;
   private boolean inMapJndiName;
   private String businessJndiName;
   private boolean addBusinessBinding;
   private boolean inEjbRefDes;
   private boolean inResEnvDes;
   private boolean inResDes;
   private boolean inAllowConcurrentCalls;
   private Map bindings;

   public WeblogicEjbJarReader(InputStream delegate, AbstractDescriptorLoader2 loader) throws XMLStreamException {
      super(delegate, loader, "weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanImpl$SchemaHelper2", "http://xmlns.oracle.com/weblogic/weblogic-ejb-jar");
   }

   protected void initialize() {
      this.bindings = new HashMap();
   }

   public boolean supportsValidation() {
      return true;
   }

   protected boolean enableCallbacksOnSchema() {
      return true;
   }

   protected VersionMunger.Continuation onStartElement(String localName) {
      if ("jndi-name".equals(localName)) {
         if (this.inBuzJndiNameMap) {
            this.inMapJndiName = true;
            return this.SKIP;
         }

         if (!this.inJndiBinding && !this.inEjbRefDes && !this.inResDes && !this.inResEnvDes) {
            this.inJndiName = true;
            return this.SKIP;
         }
      }

      if ("local-jndi-name".equals(localName)) {
         this.inLocalJndiName = true;
         return this.SKIP;
      } else {
         if ("jndi-binding".equals(localName)) {
            this.inJndiBinding = true;
         }

         if ("ejb-reference-description".equals(localName)) {
            this.inEjbRefDes = true;
         }

         if ("resource-description".equals(localName)) {
            this.inResDes = true;
         }

         if ("resource-env-description".equals(localName)) {
            this.inResEnvDes = true;
         }

         if ("business-interface-jndi-name-map".equals(localName)) {
            this.inBuzJndiNameMap = true;
            return this.SKIP;
         } else if ("business-remote".equals(localName)) {
            this.inBusniessRemote = true;
            return this.SKIP;
         } else if ("allow-concurrent-calls".equals(localName)) {
            this.inAllowConcurrentCalls = true;
            return this.SKIP;
         } else {
            return super.onStartElement(localName);
         }
      }
   }

   private VersionMunger.Continuation addJndiBinding() {
      Iterator var1 = this.bindings.entrySet().iterator();

      while(var1.hasNext()) {
         Map.Entry entry = (Map.Entry)var1.next();
         String className = (String)entry.getKey();
         String jndiName = (String)entry.getValue();
         this.pushStartElementLastEvent("jndi-binding");
         this.pushStartElementWithStackAsParent("class-name");
         this.pushCharacters(className.toCharArray());
         this.pushEndElement("class-name");
         this.pushStartElementWithStackAsParent("jndi-name");
         this.pushCharacters(jndiName.toCharArray());
         this.pushEndElement("jndi-name");
         this.pushEndElementLastEvent("jndi-binding");
      }

      this.pushEndElement("weblogic-enterprise-bean");
      this.bindings.clear();
      return this.USE_BUFFER;
   }

   protected VersionMunger.Continuation onCharacters(String text) {
      if (this.inJndiName) {
         this.bindings.put("_WL_HOME", text);
      }

      if (this.inLocalJndiName) {
         this.bindings.put("_WL_LOCALHOME", text);
      }

      if (this.inBusniessRemote) {
         this.businessIntf = text;
      }

      if (this.inMapJndiName) {
         this.businessJndiName = text;
      }

      if (this.addBusinessBinding) {
         this.addBusinessBinding = false;
         this.bindings.put(this.businessIntf, this.businessJndiName);
      }

      return this.inAllowConcurrentCalls ? this.SKIP : super.onCharacters(text);
   }

   protected VersionMunger.Continuation onEndElement(String localName) {
      if (this.inJndiName) {
         this.inJndiName = false;
         return this.SKIP;
      } else if (this.inLocalJndiName) {
         this.inLocalJndiName = false;
         return this.SKIP;
      } else if (this.inBuzJndiNameMap) {
         if (this.inBusniessRemote) {
            this.inBusniessRemote = false;
            this.buzRemoteJndiNameFound = true;
            return this.SKIP;
         } else if (this.inMapJndiName) {
            this.inMapJndiName = false;
            return this.SKIP;
         } else {
            this.inBuzJndiNameMap = false;
            if (this.buzRemoteJndiNameFound) {
               this.addBusinessBinding = true;
               this.buzRemoteJndiNameFound = false;
            }

            return this.SKIP;
         }
      } else {
         if ("jndi-binding".equals(localName)) {
            this.inJndiBinding = false;
         }

         if ("ejb-reference-description".equals(localName)) {
            this.inEjbRefDes = false;
         }

         if ("resource-env-description".equals(localName)) {
            this.inResEnvDes = false;
         }

         if ("resource-description".equals(localName)) {
            this.inResDes = false;
         }

         if (localName.equals("weblogic-enterprise-bean") && !this.bindings.isEmpty()) {
            return this.addJndiBinding();
         } else if ("allow-concurrent-calls".equals(localName)) {
            this.inAllowConcurrentCalls = false;
            return this.SKIP;
         } else {
            return super.onEndElement(localName);
         }
      }
   }
}
