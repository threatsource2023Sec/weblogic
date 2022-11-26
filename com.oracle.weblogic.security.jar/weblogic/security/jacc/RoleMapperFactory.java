package weblogic.security.jacc;

import javax.security.jacc.PolicyContextException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.logging.Loggable;
import weblogic.security.SecurityLogger;

public abstract class RoleMapperFactory {
   private static String ROLEMAPPERFACTORY_NAME = "weblogic.security.jacc.RoleMapperFactory.provider";
   public static final String JACC_POLICY_PROVIDER_PROPERTY = "javax.security.jacc.policy.provider";
   public static final String JACC_POLICY_CONFIGURATION_FACTORY_PROVIDER_PROPERTY = "javax.security.jacc.PolicyConfigurationFactory.provider";
   private static final String DEFAULT_JACC_ROLEMAPPER_FACTORY_IMPL_CLASS_NAME = "weblogic.security.jacc.simpleprovider.RoleMapperFactoryImpl";
   private static final String DEFAULT_JACC_POLICY_PROVIDER_CLASS_NAME = "weblogic.security.jacc.simpleprovider.SimpleJACCPolicy";
   private static final String DEFAULT_JACC_POLICY_CONFIGURATION_FACTORY_IMPL_CLASS_NAME = "weblogic.security.jacc.simpleprovider.PolicyConfigurationFactoryImpl";
   private static RoleMapperFactory rmFactory;
   private static DebugLogger jaccDebugLogger = DebugLogger.getDebugLogger("DebugSecurityJACCNonPolicy");

   public RoleMapperFactory() {
      if (jaccDebugLogger.isDebugEnabled()) {
         jaccDebugLogger.debug("RoleMapperFactory noarg constructor");
      }

   }

   public static RoleMapperFactory getRoleMapperFactory() throws ClassNotFoundException, PolicyContextException {
      if (rmFactory != null) {
         return rmFactory;
      } else {
         String[] classname = new String[]{null};
         String rmfclassname = null;

         Loggable loggable;
         try {
            Class clazz = null;
            classname[0] = System.getProperty(ROLEMAPPERFACTORY_NAME);
            rmfclassname = classname[0];
            if (rmfclassname == null) {
               if ("weblogic.security.jacc.simpleprovider.SimpleJACCPolicy".equals(System.getProperty("javax.security.jacc.policy.provider")) && "weblogic.security.jacc.simpleprovider.PolicyConfigurationFactoryImpl".equals(System.getProperty("javax.security.jacc.PolicyConfigurationFactory.provider"))) {
                  rmfclassname = "weblogic.security.jacc.simpleprovider.RoleMapperFactoryImpl";
               }

               if (rmfclassname == null) {
                  throw new ClassNotFoundException(SecurityLogger.getJACCPropertyNotSet(ROLEMAPPERFACTORY_NAME));
               }
            }

            clazz = Class.forName(rmfclassname, true, ClassLoader.getSystemClassLoader());
            Object factory = clazz.newInstance();
            rmFactory = (RoleMapperFactory)factory;
         } catch (ClassNotFoundException var4) {
            loggable = SecurityLogger.logJACCRoleMapperFactoryProviderClassNotFoundLoggable(rmfclassname, var4);
            throw new ClassNotFoundException(loggable.getMessageText(), var4);
         } catch (IllegalAccessException var5) {
            loggable = SecurityLogger.logJACCRoleMapperFactoryProviderClassNotFoundLoggable(rmfclassname, var5);
            throw new PolicyContextException(loggable.getMessageText(), var5);
         } catch (InstantiationException var6) {
            loggable = SecurityLogger.logJACCRoleMapperFactoryProviderClassNotFoundLoggable(rmfclassname, var6);
            throw new PolicyContextException(loggable.getMessageText(), var6);
         } catch (ClassCastException var7) {
            loggable = SecurityLogger.logJACCRoleMapperFactoryProviderClassNotFoundLoggable(rmfclassname, var7);
            throw new PolicyContextException(loggable.getMessageText(), var7);
         }

         return rmFactory;
      }
   }

   public abstract RoleMapper getRoleMapper(String var1, boolean var2);

   public abstract RoleMapper getRoleMapper(String var1, String var2, boolean var3);

   public abstract RoleMapper getRoleMapperForContextID(String var1);

   public abstract void removeRoleMapper(String var1);
}
