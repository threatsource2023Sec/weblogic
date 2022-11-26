package weblogic.ejb.spi;

import java.io.File;
import weblogic.descriptor.DescriptorManager;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.utils.classloaders.GenericClassLoader;

public final class RDBMSDescriptorFactory {
   public static RDBMSDescriptor createRDBMSDescriptor(DescriptorManager edm, String uri, GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName) {
      return new weblogic.ejb.container.cmp.rdbms.RDBMSDescriptor(edm, uri, gcl, configDir, plan, moduleName);
   }
}
