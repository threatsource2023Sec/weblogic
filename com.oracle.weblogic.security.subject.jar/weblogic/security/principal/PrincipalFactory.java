package weblogic.security.principal;

import com.bea.common.security.ApiLogger;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidParameterException;

public class PrincipalFactory {
   private static PrincipalFactory instance = null;
   private static PrincipalConfigurationDelegate delegate = null;

   public static synchronized PrincipalFactory getInstance() {
      if (instance != null) {
         return instance;
      } else {
         instance = new PrincipalFactory();
         return instance;
      }
   }

   private PrincipalFactory() {
      delegate = PrincipalConfigurationDelegate.getInstance();
   }

   public WLSPrincipal createWLSUser(String userName) {
      return this.createWLSUser(userName, (String)null, (String)null);
   }

   public WLSPrincipal createWLSUser(String userName, String identityDomain) {
      return this.createWLSUser(userName, identityDomain, (String)null, (String)null);
   }

   public WLSPrincipal createWLSUser(String userName, String guid, String dn) {
      return this.create(WLSUserImpl.class, userName, guid, dn);
   }

   public WLSPrincipal createWLSUser(String userName, String identityDomain, String guid, String dn) {
      return this.create(WLSUserImpl.class, userName, identityDomain, guid, dn);
   }

   public WLSPrincipal createWLSGroup(String groupName) {
      return this.createWLSGroup(groupName, (String)null, (String)null);
   }

   public WLSPrincipal createWLSGroup(String groupName, String identityDomain) {
      return this.createWLSGroup(groupName, identityDomain, (String)null, (String)null);
   }

   public WLSPrincipal createWLSGroup(String groupName, String guid, String dn) {
      return this.create(WLSGroupImpl.class, groupName, guid, dn);
   }

   public WLSPrincipal createWLSGroup(String groupName, String identityDomain, String guid, String dn) {
      return this.create(WLSGroupImpl.class, groupName, identityDomain, guid, dn);
   }

   public WLSPrincipal createIDCSAppRole(String appRoleName, String appName) throws InvalidParameterException {
      return this.createIDCSAppRole(appRoleName, (String)null, (String)null, appName, (String)null);
   }

   public WLSPrincipal createIDCSAppRole(String appRoleName, String id, String ref, String appName, String appId) throws InvalidParameterException {
      Class klass = IDCSAppRoleImpl.class;
      if (WLSAbstractPrincipal.class.isAssignableFrom(klass)) {
         try {
            Class[] parameterTypes = new Class[]{String.class, String.class, String.class, String.class, String.class};
            Constructor ctor = klass.getConstructor(parameterTypes);
            Object[] argList = new String[]{appRoleName, id, ref, appName, appId};
            WLSAbstractPrincipal abstractPrincipal = (WLSAbstractPrincipal)ctor.newInstance(argList);
            Class var11 = PrincipalConfigurationDelegate.class;
            synchronized(PrincipalConfigurationDelegate.class) {
               abstractPrincipal.setEqualsCaseInsensitive(delegate.isEqualsCaseInsensitive());
               abstractPrincipal.setEqualsCompareDnAndGuid(delegate.isEqualsCompareDnAndGuid());
            }

            abstractPrincipal.principalFactoryCreated = true;
            return abstractPrincipal;
         } catch (IllegalAccessException var14) {
            throw new IllegalStateException(ApiLogger.getUnableToInstantiatePrincipal(klass.getName(), var14));
         } catch (NoSuchMethodException var15) {
            throw new IllegalStateException(ApiLogger.getUnableToInstantiatePrincipal(klass.getName(), var15));
         } catch (InvocationTargetException var16) {
            throw new IllegalStateException(ApiLogger.getUnableToInstantiatePrincipal(klass.getName(), var16));
         } catch (InstantiationException var17) {
            throw new IllegalStateException(ApiLogger.getUnableToInstantiatePrincipal(klass.getName(), var17));
         } catch (IllegalArgumentException var18) {
            throw new IllegalStateException(ApiLogger.getUnableToInstantiatePrincipal(klass.getName(), var18));
         }
      } else {
         throw new InvalidParameterException(ApiLogger.getInvalidPrincipalClassName(klass.getName()));
      }
   }

   public WLSPrincipal createIDCSClient(String clientName, String clientTenant, String clientId) throws InvalidParameterException {
      return this.create(IDCSClientImpl.class, clientName, clientTenant, clientId, (String)null);
   }

   public WLSPrincipal createIDCSScope(String scopeName, String tenant) {
      return this.create(IDCSScopeImpl.class, scopeName, tenant, (String)null, (String)null);
   }

   public WLSPrincipal create(Class klass, String name, String guid, String dn) throws InvalidParameterException {
      if (WLSPrincipal.class.isAssignableFrom(klass)) {
         Constructor ctor = null;
         Class[] parameterTypes = null;
         Object[] argList = null;
         WLSPrincipal principal = null;
         WLSAbstractPrincipal abstractPrincipal = null;

         try {
            if (WLSAbstractPrincipal.class.isAssignableFrom(klass)) {
               parameterTypes = new Class[]{String.class};
               argList = new String[]{name};
               ctor = klass.getConstructor(parameterTypes);
               abstractPrincipal = (WLSAbstractPrincipal)ctor.newInstance(argList);
               abstractPrincipal.setGuid(guid);
               abstractPrincipal.setDn(dn);
               Class var10 = PrincipalConfigurationDelegate.class;
               synchronized(PrincipalConfigurationDelegate.class) {
                  abstractPrincipal.setEqualsCaseInsensitive(delegate.isEqualsCaseInsensitive());
                  abstractPrincipal.setEqualsCompareDnAndGuid(delegate.isEqualsCompareDnAndGuid());
               }

               abstractPrincipal.principalFactoryCreated = true;
               return abstractPrincipal;
            } else {
               parameterTypes = new Class[]{String.class, String.class, String.class};
               argList = new String[]{name, guid, dn};
               ctor = klass.getConstructor(parameterTypes);
               principal = (WLSPrincipal)ctor.newInstance(argList);
               return principal;
            }
         } catch (IllegalAccessException var13) {
            throw new IllegalStateException(ApiLogger.getUnableToInstantiatePrincipal(klass.getName(), var13));
         } catch (NoSuchMethodException var14) {
            throw new IllegalStateException(ApiLogger.getUnableToInstantiatePrincipal(klass.getName(), var14));
         } catch (InvocationTargetException var15) {
            throw new IllegalStateException(ApiLogger.getUnableToInstantiatePrincipal(klass.getName(), var15));
         } catch (InstantiationException var16) {
            throw new IllegalStateException(ApiLogger.getUnableToInstantiatePrincipal(klass.getName(), var16));
         } catch (IllegalArgumentException var17) {
            throw new IllegalStateException(ApiLogger.getUnableToInstantiatePrincipal(klass.getName(), var17));
         }
      } else {
         throw new InvalidParameterException(ApiLogger.getInvalidPrincipalClassName(klass.getName()));
      }
   }

   public WLSPrincipal create(Class klass, String name, String identityDomain, String guid, String dn) throws InvalidParameterException {
      if (WLSPrincipal.class.isAssignableFrom(klass)) {
         Constructor ctor = null;
         Class[] parameterTypes = null;
         Object[] argList = null;
         WLSPrincipal principal = null;
         WLSAbstractPrincipal abstractPrincipal = null;

         try {
            if (WLSAbstractPrincipal.class.isAssignableFrom(klass)) {
               parameterTypes = new Class[]{String.class};
               argList = new String[]{name};
               ctor = klass.getConstructor(parameterTypes);
               abstractPrincipal = (WLSAbstractPrincipal)ctor.newInstance(argList);
               abstractPrincipal.setGuid(guid);
               abstractPrincipal.setDn(dn);
               abstractPrincipal.setIdentityDomain(identityDomain);
               Class var11 = PrincipalConfigurationDelegate.class;
               synchronized(PrincipalConfigurationDelegate.class) {
                  abstractPrincipal.setEqualsCaseInsensitive(delegate.isEqualsCaseInsensitive());
                  abstractPrincipal.setEqualsCompareDnAndGuid(delegate.isEqualsCompareDnAndGuid());
               }

               abstractPrincipal.principalFactoryCreated = true;
               return abstractPrincipal;
            } else {
               parameterTypes = new Class[]{String.class, String.class, String.class, String.class};
               argList = new String[]{name, guid, dn, identityDomain};
               ctor = klass.getConstructor(parameterTypes);
               principal = (WLSPrincipal)ctor.newInstance(argList);
               return principal;
            }
         } catch (IllegalAccessException var14) {
            throw new IllegalStateException(ApiLogger.getUnableToInstantiatePrincipal(klass.getName(), var14));
         } catch (NoSuchMethodException var15) {
            throw new IllegalStateException(ApiLogger.getUnableToInstantiatePrincipal(klass.getName(), var15));
         } catch (InvocationTargetException var16) {
            throw new IllegalStateException(ApiLogger.getUnableToInstantiatePrincipal(klass.getName(), var16));
         } catch (InstantiationException var17) {
            throw new IllegalStateException(ApiLogger.getUnableToInstantiatePrincipal(klass.getName(), var17));
         } catch (IllegalArgumentException var18) {
            throw new IllegalStateException(ApiLogger.getUnableToInstantiatePrincipal(klass.getName(), var18));
         }
      } else {
         throw new InvalidParameterException(ApiLogger.getInvalidPrincipalClassName(klass.getName()));
      }
   }
}
