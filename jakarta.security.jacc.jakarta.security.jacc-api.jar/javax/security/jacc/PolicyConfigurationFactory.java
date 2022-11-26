package javax.security.jacc;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.SecurityPermission;

public abstract class PolicyConfigurationFactory {
   private static String FACTORY_NAME = "javax.security.jacc.PolicyConfigurationFactory.provider";
   private static volatile PolicyConfigurationFactory policyConfigurationFactory;

   public static PolicyConfigurationFactory getPolicyConfigurationFactory() throws ClassNotFoundException, PolicyContextException {
      SecurityManager securityManager = System.getSecurityManager();
      if (securityManager != null) {
         securityManager.checkPermission(new SecurityPermission("setPolicy"));
      }

      if (policyConfigurationFactory != null) {
         return policyConfigurationFactory;
      } else {
         final String[] className = new String[]{null};

         try {
            Class clazz = null;
            if (securityManager != null) {
               try {
                  clazz = (Class)AccessController.doPrivileged(new PrivilegedExceptionAction() {
                     public Class run() throws Exception {
                        className[0] = System.getProperty(PolicyConfigurationFactory.FACTORY_NAME);
                        if (className[0] == null) {
                           throw new ClassNotFoundException("JACC:Error PolicyConfigurationFactory : property not set : " + PolicyConfigurationFactory.FACTORY_NAME);
                        } else {
                           return Class.forName(className[0], true, Thread.currentThread().getContextClassLoader());
                        }
                     }
                  });
               } catch (PrivilegedActionException var5) {
                  Exception e = var5.getException();
                  if (e instanceof ClassNotFoundException) {
                     throw (ClassNotFoundException)e;
                  }

                  if (e instanceof InstantiationException) {
                     throw (InstantiationException)e;
                  }

                  if (e instanceof IllegalAccessException) {
                     throw (IllegalAccessException)e;
                  }
               }
            } else {
               className[0] = System.getProperty(FACTORY_NAME);
               if (className[0] == null) {
                  throw new ClassNotFoundException("JACC:Error PolicyConfigurationFactory : property not set : " + FACTORY_NAME);
               }

               clazz = Class.forName(className[0], true, Thread.currentThread().getContextClassLoader());
            }

            if (clazz != null) {
               Object factory = clazz.newInstance();
               policyConfigurationFactory = (PolicyConfigurationFactory)factory;
            }
         } catch (ClassNotFoundException var6) {
            throw new ClassNotFoundException("JACC:Error PolicyConfigurationFactory : cannot find class : " + className[0], var6);
         } catch (IllegalAccessException var7) {
            throw new PolicyContextException("JACC:Error PolicyConfigurationFactory : cannot access class : " + className[0], var7);
         } catch (InstantiationException var8) {
            throw new PolicyContextException("JACC:Error PolicyConfigurationFactory : cannot instantiate : " + className[0], var8);
         } catch (ClassCastException var9) {
            throw new ClassCastException("JACC:Error PolicyConfigurationFactory : class not PolicyConfigurationFactory : " + className[0]);
         }

         return policyConfigurationFactory;
      }
   }

   public abstract PolicyConfiguration getPolicyConfiguration(String var1, boolean var2) throws PolicyContextException;

   public abstract boolean inService(String var1) throws PolicyContextException;
}
