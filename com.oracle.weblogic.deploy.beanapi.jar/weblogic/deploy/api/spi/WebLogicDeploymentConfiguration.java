package weblogic.deploy.api.spi;

import java.io.IOException;
import javax.enterprise.deploy.model.DDBeanRoot;
import javax.enterprise.deploy.spi.DeploymentConfiguration;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import javax.enterprise.deploy.spi.exceptions.InvalidModuleException;
import weblogic.deploy.api.internal.Closable;
import weblogic.deploy.api.internal.utils.InstallDir;
import weblogic.deploy.api.spi.config.DescriptorSupport;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;

public interface WebLogicDeploymentConfiguration extends DeploymentConfiguration, Closable {
   String DEFAULT_APPNAME = "MyApp";

   void restore(DeploymentPlanBean var1) throws ConfigurationException;

   DeploymentPlanBean getPlan();

   String getModuleName(DDBeanRoot var1);

   void addToPlan(WebLogicDConfigBeanRoot var1);

   InstallDir getInstallDir();

   void export(int var1) throws IllegalArgumentException;

   void export(int var1, boolean var2) throws IllegalArgumentException;

   void export(int var1, boolean var2, String var3) throws IllegalArgumentException;

   void close();

   boolean isPlanRestored();

   String getRootTag(DDBeanRoot var1) throws ConfigurationException;

   WebLogicDConfigBeanRoot initDConfig(DDBeanRoot var1, String var2, DescriptorSupport var3) throws InvalidModuleException, IOException;
}
