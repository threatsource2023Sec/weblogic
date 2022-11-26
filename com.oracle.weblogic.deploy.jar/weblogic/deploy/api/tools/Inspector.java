package weblogic.deploy.api.tools;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import javax.enterprise.deploy.spi.exceptions.DeploymentManagerCreationException;
import javax.enterprise.deploy.spi.exceptions.InvalidModuleException;
import weblogic.deploy.api.internal.utils.LibrarySpec;
import weblogic.deploy.api.model.WebLogicDeployableObject;
import weblogic.deploy.api.model.WebLogicDeployableObjectFactory;
import weblogic.server.GlobalServiceLocator;

public class Inspector {
   private File app;
   private ModuleInfo info;
   private WebLogicDeployableObject dObject;

   public Inspector(File app) {
      this.app = app;
   }

   public ModuleInfo getModuleInfo() throws DeploymentManagerCreationException, IOException, InvalidModuleException, ConfigurationException {
      if (this.info != null) {
         return this.info;
      } else {
         this.setup();
         this.info = ModuleInfo.createModuleInfo(this.dObject);
         return this.info;
      }
   }

   private void setup() throws InvalidModuleException, IOException {
      this.dObject = ((WebLogicDeployableObjectFactory)GlobalServiceLocator.getServiceLocator().getService(WebLogicDeployableObjectFactory.class, new Annotation[0])).createLazyDeployableObject(this.app, (File)null, (File)null, (File)null, (LibrarySpec[])null);
   }

   public void close() {
      if (this.dObject != null) {
         this.dObject.close();
      }

   }
}
