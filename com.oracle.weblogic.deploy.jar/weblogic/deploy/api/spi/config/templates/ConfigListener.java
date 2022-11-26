package weblogic.deploy.api.spi.config.templates;

import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import javax.enterprise.deploy.model.DDBeanRoot;
import javax.enterprise.deploy.model.exceptions.DDBeanCreateException;
import javax.enterprise.deploy.spi.DConfigBeanRoot;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.SPIDeployerLogger;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.WebLogicDConfigBean;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.deploy.api.spi.config.BasicDConfigBeanRoot;
import weblogic.deploy.api.spi.config.DescriptorSupport;

public abstract class ConfigListener implements PropertyChangeListener {
   protected static final boolean debug = Debug.isDebug("config");
   protected WebLogicDConfigBean dcb;
   protected BasicDConfigBeanRoot root;

   protected ConfigListener(WebLogicDConfigBean dcb) {
      this.dcb = dcb;

      BasicDConfigBean dcb2;
      for(dcb2 = (BasicDConfigBean)dcb; dcb2.getParent() != null; dcb2 = dcb2.getParent()) {
      }

      this.root = (BasicDConfigBeanRoot)dcb2;
      if (debug) {
         Debug.say("root is " + this.root.toString());
      }

   }

   protected void addDCB(String uri, DescriptorSupport ds) {
      try {
         DDBeanRoot ddroot = this.root.getDDBean().getRoot().getDeployableObject().getDDBeanRoot(uri);
         if (this.root.getDConfigBean(ddroot, ds) == null && debug) {
            Debug.say("can't create dcb for dd at " + uri);
         }
      } catch (FileNotFoundException var4) {
         SPIDeployerLogger.logNoDCB(uri, var4.getMessage());
      } catch (DDBeanCreateException var5) {
         SPIDeployerLogger.logDDCreateError(uri);
      } catch (ConfigurationException var6) {
         SPIDeployerLogger.logNoDCB(uri, var6.getMessage());
      }

   }

   protected void removeDCB(String uri) {
      try {
         DDBeanRoot ddroot = this.root.getDDBean().getRoot().getDeployableObject().getDDBeanRoot(uri);
         this.root.getDeploymentConfiguration().removeDConfigBean((DConfigBeanRoot)this.root.getDConfigBean(ddroot));
      } catch (Exception var3) {
         if (debug) {
            var3.printStackTrace();
         }
      }

   }
}
