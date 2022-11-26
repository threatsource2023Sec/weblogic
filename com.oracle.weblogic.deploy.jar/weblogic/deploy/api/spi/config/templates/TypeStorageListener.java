package weblogic.deploy.api.spi.config.templates;

import java.beans.PropertyChangeEvent;
import java.io.FileNotFoundException;
import javax.enterprise.deploy.model.DDBeanRoot;
import javax.enterprise.deploy.model.exceptions.DDBeanCreateException;
import weblogic.deploy.api.internal.SPIDeployerLogger;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.DescriptorSupport;
import weblogic.deploy.api.spi.config.DescriptorSupportManager;
import weblogic.j2ee.descriptor.wl.PersistenceUseBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig;

public class TypeStorageListener extends ConfigListener {
   public TypeStorageListener(WeblogicEnterpriseBeanBeanDConfig dcb) {
      super(dcb);
   }

   public void propertyChange(PropertyChangeEvent event) {
      if (debug) {
         Debug.say("update listener");
      }

      String prop = event.getPropertyName();
      if (debug) {
         Debug.say("caught update");
      }

      if ("TypeStorage".equals(prop)) {
         PersistenceUseBean pu = (PersistenceUseBean)event.getSource();
         String ts = (String)event.getOldValue();
         String newts = (String)event.getNewValue();
         if (debug) {
            Debug.say("old: " + ts + ", new: " + newts);
         }

         if (ts != null) {
            this.removeDCB(ts);
         }

         if (newts != null) {
            this.addDCB(newts, pu.getTypeVersion());
         }

      }
   }

   private void addDCB(String uri, String version) {
      try {
         DDBeanRoot ddroot = this.root.getDDBean().getRoot().getDeployableObject().getDDBeanRoot(uri);
         DescriptorSupport ds = DescriptorSupportManager.getForSecondaryTag("weblogic-rdbms-jar")[0];
         ds.setBaseURI(uri);
         ds.setConfigURI(uri);
         if ("5.1.0".equals(version)) {
            ds.setBaseNameSpace("http://www.bea.com/ns/weblogic/60");
            ds.setConfigNameSpace("http://www.bea.com/ns/weblogic/60");
            ds.setStandardClassName("weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBeanImpl");
            ds.setConfigClassName("weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBeanImpl");
            ds.setDConfigClassName("weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBeanDConfig");
         }

         this.addDCB(uri, ds);
      } catch (FileNotFoundException var5) {
         SPIDeployerLogger.logNoCMPDD(uri);
      } catch (DDBeanCreateException var6) {
         SPIDeployerLogger.logDDCreateError(uri);
      }

   }
}
