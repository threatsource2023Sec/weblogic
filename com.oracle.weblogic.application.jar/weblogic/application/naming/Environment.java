package weblogic.application.naming;

import java.util.List;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import weblogic.j2ee.descriptor.J2eeClientEnvironmentBean;
import weblogic.j2ee.descriptor.J2eeEnvironmentBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnvironmentBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.validation.injection.ValidationManager;

public interface Environment {
   Context getCompEnvContext();

   Context getRootContext();

   Context getCompContext();

   Context getGlobalNSContext();

   Context getAppNSContext();

   Context getModuleNSContext();

   String getApplicationName();

   String getComponentName();

   String getModuleId();

   boolean isEjbComponent();

   void destroy();

   void bindValidation(List var1) throws NamingException, EnvironmentException;

   void bindValidation(ValidationManager.ValidationBean var1, List var2) throws NamingException, EnvironmentException;

   void unbindValidation();

   void contributeClientEnvEntries(J2eeClientEnvironmentBean var1, WeblogicEnvironmentBean var2);

   void contributeEnvEntries(J2eeEnvironmentBean var1, WeblogicEnvironmentBean var2, AuthenticatedSubject var3);

   void validateEnvEntries(ClassLoader var1) throws EnvironmentException;

   void unbindEnvEntries();

   void bindEnvEntriesFromDDs(ClassLoader var1, PersistenceUnitRegistryProvider var2) throws NamingException, EnvironmentException;

   void bindEnvEntriesFromDDs(ClassLoader var1, PersistenceUnitRegistryProvider var2, ServletContext var3) throws NamingException, EnvironmentException;

   public static enum EnvType {
      WEBAPP,
      EJB,
      MANAGED_BEAN,
      CLIENT,
      APPLICATION;
   }
}
