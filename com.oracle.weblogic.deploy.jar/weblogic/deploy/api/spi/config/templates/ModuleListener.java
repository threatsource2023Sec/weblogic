package weblogic.deploy.api.spi.config.templates;

import java.beans.PropertyChangeEvent;
import java.io.FileNotFoundException;
import javax.enterprise.deploy.model.DDBeanRoot;
import javax.enterprise.deploy.model.exceptions.DDBeanCreateException;
import weblogic.deploy.api.internal.SPIDeployerLogger;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.DescriptorSupport;
import weblogic.deploy.api.spi.config.DescriptorSupportManager;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBeanDConfig;
import weblogic.j2ee.descriptor.wl.WeblogicModuleBean;

public class ModuleListener extends ConfigListener {
   private WeblogicModuleBean wmb = null;

   public ModuleListener(WeblogicApplicationBeanDConfig dcb) {
      super(dcb);
   }

   public void propertyChange(PropertyChangeEvent event) {
      String prop = event.getPropertyName();
      if ("Path".equals(prop)) {
         this.wmb = (WeblogicModuleBean)event.getSource();
         if (debug) {
            Debug.say("caught update");
         }

         String oldVal = (String)event.getOldValue();
         String newVal = (String)event.getNewValue();
         if (debug) {
            Debug.say("old: " + oldVal + ", new: " + newVal);
         }

         if (oldVal != null) {
            this.removeDCB(oldVal);
         }

         if (newVal != null) {
            this.addDCB(newVal, prop);
         }

      }
   }

   private void addDCB(String uri, String prop) {
      try {
         DDBeanRoot ddroot = this.root.getDDBean().getRoot().getDeployableObject().getDDBeanRoot(uri);
         DescriptorSupport ds = null;
         if ("JDBC".equals(this.wmb.getType())) {
            ds = DescriptorSupportManager.getForTag("jdbc-data-source");
         } else if ("JMS".equals(this.wmb.getType())) {
            ds = DescriptorSupportManager.getForTag("weblogic-jms");
         } else if ("Interception".equals(this.wmb.getType())) {
            ds = DescriptorSupportManager.getForTag("weblogic-interception");
         } else if ("GAR".equals(this.wmb.getType())) {
            ds = DescriptorSupportManager.getForTag("coherence-application");
         }

         if (ds != null) {
            ds.setBaseURI(uri);
            ds.setConfigURI(uri);
            this.addDCB(uri, ds);
         }
      } catch (FileNotFoundException var5) {
         SPIDeployerLogger.logMissingDD(uri, prop);
      } catch (DDBeanCreateException var6) {
         SPIDeployerLogger.logDDCreateError(uri);
      }

   }
}
