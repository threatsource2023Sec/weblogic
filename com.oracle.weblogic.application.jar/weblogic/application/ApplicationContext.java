package weblogic.application;

import java.io.IOException;
import java.io.InputStream;
import javax.naming.Context;
import javax.security.jacc.PolicyConfiguration;
import weblogic.application.library.LibraryContext;
import weblogic.application.library.LibraryManager;
import weblogic.application.utils.XMLWriter;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.utils.classloaders.GenericClassLoader;

public interface ApplicationContext extends LibraryContext {
   ApplicationMBean getApplicationMBean();

   AppDeploymentMBean getAppDeploymentMBean();

   /** @deprecated */
   @Deprecated
   String getApplicationName();

   String getApplicationId();

   String getPartialApplicationId(boolean var1);

   Context getEnvContext();

   GenericClassLoader getAppClassLoader();

   String getApplicationSecurityRealmName();

   String getApplicationParameter(String var1);

   InputStream getElement(String var1) throws IOException;

   void addJACCPolicyConfiguration(PolicyConfiguration var1);

   void addLibraryManager(String var1, LibraryManager var2);

   void removeLibraryManager(String var1, LibraryManager var2);

   void writeDiagnosticImage(XMLWriter var1);

   String getStagingPath();

   String[] getInternalSourceDirectories();
}
