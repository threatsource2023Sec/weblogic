package weblogic.j2eeclient;

import java.io.File;
import java.net.MalformedURLException;
import javax.persistence.spi.PersistenceUnitInfo;
import weblogic.application.utils.IOUtils;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.persistence.AbstractPersistenceUnitRegistry;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public class AppClientPersistenceUnitRegistry extends AbstractPersistenceUnitRegistry {
   public AppClientPersistenceUnitRegistry(File clientJarFile, GenericClassLoader cl, String module, File configDir, DeploymentPlanBean plan) throws Exception, MalformedURLException {
      super(cl, module, configDir, plan);
      VirtualJarFile vjf = VirtualJarFactory.createVirtualJar(clientJarFile);

      try {
         this.loadPersistenceDescriptor(vjf, true, clientJarFile);
      } finally {
         IOUtils.forceClose(vjf);
      }

   }

   public PersistenceUnitInfo getPersistenceUnit(String name) {
      return (PersistenceUnitInfo)this.persistenceUnits.get(name);
   }
}
