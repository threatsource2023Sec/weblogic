package weblogic.management.configuration;

import java.util.Properties;
import javax.management.InvalidAttributeValueException;

public interface OsgiFrameworkMBean extends DeploymentMBean {
   String getName();

   String getOsgiImplementationLocation();

   void setOsgiImplementationLocation(String var1);

   String getFactoryImplementationClass();

   void setFactoryImplementationClass(String var1);

   Properties getInitProperties();

   void setInitProperties(Properties var1) throws InvalidAttributeValueException;

   boolean isRegisterGlobalWorkManagers();

   void setRegisterGlobalWorkManagers(boolean var1);

   boolean isRegisterGlobalDataSources();

   void setRegisterGlobalDataSources(boolean var1);

   String getOrgOsgiFrameworkBootdelegation();

   void setOrgOsgiFrameworkBootdelegation(String var1);

   String getOrgOsgiFrameworkSystemPackagesExtra();

   void setOrgOsgiFrameworkSystemPackagesExtra(String var1);

   String getDeployInstallationBundles();

   void setDeployInstallationBundles(String var1);
}
