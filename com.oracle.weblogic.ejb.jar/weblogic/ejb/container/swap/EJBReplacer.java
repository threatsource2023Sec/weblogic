package weblogic.ejb.container.swap;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import javax.ejb.EJBContext;
import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.naming.ModuleRegistry;
import weblogic.application.naming.PersistenceUnitRegistry;
import weblogic.application.naming.PersistenceUnitRegistryProvider;
import weblogic.ejb.container.interfaces.WLLocalEJBObject;
import weblogic.ejb.container.internal.BaseEJBLocalHome;
import weblogic.ejb.container.internal.BaseLocalObject;
import weblogic.ejb.container.internal.EntityEJBLocalObject;
import weblogic.ejb.container.internal.LocalHandle30Impl;
import weblogic.ejb.container.internal.SingletonLocalObject;
import weblogic.ejb.container.internal.StatefulEJBLocalObject;
import weblogic.ejb.container.internal.StatefulLocalObject;
import weblogic.ejb.container.internal.StatelessEJBLocalObject;
import weblogic.ejb.container.internal.StatelessLocalObject;
import weblogic.ejb20.internal.LocalHandleImpl;
import weblogic.ejb20.internal.LocalHomeHandleImpl;
import weblogic.ejb20.swap.HandleReplacer;
import weblogic.ejb20.swap.HomeHandleReplacer;
import weblogic.persistence.BasePersistenceUnitInfo;
import weblogic.persistence.EntityManagerFactoryProxyImpl;
import weblogic.persistence.EntityManagerInvocationHandlerFactory;
import weblogic.persistence.TransactionalEntityManagerProxyImpl;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;
import weblogic.utils.io.Replacer;

final class EJBReplacer implements Replacer {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final boolean debug = false;
   private static final Replacer remoteReplacer = RemoteObjectReplacer.getReplacer();
   private EJBContext ctx = null;
   private UserTransaction utx = null;

   public EJBReplacer() {
   }

   void setContext(EJBContext c) {
      this.ctx = c;
   }

   public Object replaceObject(Object obj) throws IOException {
      IOException ioe;
      if (obj instanceof EJBObject) {
         final EJBObject eo = (EJBObject)obj;

         try {
            return SecurityManager.runAs(kernelId, kernelId, new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  return new HandleReplacer(eo.getHandle());
               }
            });
         } catch (PrivilegedActionException var5) {
            ioe = new IOException("Exception while replacing EO");
            ioe.initCause(var5.getException());
            throw ioe;
         }
      } else if (obj instanceof WLLocalEJBObject) {
         BaseLocalObject localObj = ((WLLocalEJBObject)obj).getWLLocalObject();
         Object returnObj = null;
         if (localObj instanceof SingletonLocalObject) {
            returnObj = ((SingletonLocalObject)localObj).getLocalHandle30Object(obj);
         } else if (localObj instanceof StatelessLocalObject) {
            returnObj = ((StatelessLocalObject)localObj).getLocalHandle30Object(obj);
         } else if (localObj instanceof StatefulLocalObject) {
            Object pk = ((WLLocalEJBObject)obj).getWLPrimaryKey();
            returnObj = ((StatefulLocalObject)localObj).getLocalHandle30Object(obj, pk);
         }

         return returnObj != null ? returnObj : remoteReplacer.resolveObject(obj);
      } else if (obj instanceof EJBLocalObject) {
         if (obj instanceof EntityEJBLocalObject) {
            return ((EntityEJBLocalObject)obj).getLocalHandleObject();
         } else {
            return obj instanceof StatefulEJBLocalObject ? ((StatefulEJBLocalObject)obj).getLocalHandleObject() : ((StatelessEJBLocalObject)obj).getLocalHandleObject();
         }
      } else if (obj instanceof EJBHome) {
         final EJBHome home = (EJBHome)obj;

         try {
            return SecurityManager.runAs(kernelId, kernelId, new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  return new HomeHandleReplacer(home.getHomeHandle());
               }
            });
         } catch (PrivilegedActionException var6) {
            ioe = new IOException("Exception while replacing Home");
            ioe.initCause(var6.getException());
            throw ioe;
         }
      } else if (obj instanceof EJBLocalHome) {
         BaseEJBLocalHome elh = (BaseEJBLocalHome)obj;
         return elh.getLocalHomeHandleObject();
      } else if (obj instanceof EJBContext) {
         return new weblogic.ejb20.swap.EJBReplacer.EJBContextReplacement();
      } else if (obj instanceof Context) {
         Context c = (Context)obj;

         try {
            String name = c.getNameInNamespace();
            return name.indexOf("comp/env") <= 0 && name.indexOf("comp.env") <= 0 ? obj : new weblogic.ejb20.swap.EJBReplacer.EnvironmentContextReplacement(name);
         } catch (NamingException var7) {
            return obj;
         }
      } else if (obj instanceof TransactionalEntityManagerProxyImpl) {
         TransactionalEntityManagerProxyImpl emProxy = (TransactionalEntityManagerProxyImpl)obj;
         return new TransactionalEntityManagerProxyReplacer(emProxy.getAppName(), emProxy.getModuleName(), emProxy.getUnqualifiedUnitName(), emProxy.getSynchronizationType());
      } else if (obj instanceof EntityManagerFactoryProxyImpl) {
         EntityManagerFactoryProxyImpl emProxy = (EntityManagerFactoryProxyImpl)obj;
         return new EntityManagerFactoryReplacement(emProxy.getAppName(), emProxy.getModuleName(), emProxy.getUnitName());
      } else {
         return obj instanceof UserTransaction ? new weblogic.ejb20.swap.EJBReplacer.UserTransactionReplacement() : remoteReplacer.replaceObject(remoteReplacer.replaceObject(obj));
      }
   }

   public Object resolveObject(Object obj) throws IOException {
      if (obj instanceof HandleReplacer) {
         HandleReplacer hr = (HandleReplacer)obj;
         return hr.getHandle().getEJBObject();
      } else if (obj instanceof HomeHandleReplacer) {
         HomeHandleReplacer hhr = (HomeHandleReplacer)obj;
         return hhr.getHomeHandle().getEJBHome();
      } else if (obj instanceof LocalHandle30Impl) {
         LocalHandle30Impl h = (LocalHandle30Impl)obj;
         return h.getEJB30LocalObject();
      } else if (obj instanceof LocalHandleImpl) {
         LocalHandleImpl h = (LocalHandleImpl)obj;
         return h.getEJBLocalObject();
      } else if (obj instanceof LocalHomeHandleImpl) {
         LocalHomeHandleImpl hh = (LocalHomeHandleImpl)obj;
         return hh.getEJBLocalHome();
      } else if (obj instanceof weblogic.ejb20.swap.EJBReplacer.EJBContextReplacement) {
         return this.ctx;
      } else {
         String appName;
         if (obj instanceof weblogic.ejb20.swap.EJBReplacer.EnvironmentContextReplacement) {
            weblogic.ejb20.swap.EJBReplacer.EnvironmentContextReplacement ecr = (weblogic.ejb20.swap.EJBReplacer.EnvironmentContextReplacement)obj;
            appName = ecr.getName();
            appName = "java:" + appName;

            try {
               Context c = new InitialContext();
               return c.lookup(appName);
            } catch (NamingException var9) {
               throw new AssertionError("Unexpected Exception during activation: " + var9);
            }
         } else {
            String moduleName;
            String unitName;
            if (obj instanceof TransactionalEntityManagerProxyReplacer) {
               TransactionalEntityManagerProxyReplacer emProxyReplacement = (TransactionalEntityManagerProxyReplacer)obj;
               appName = emProxyReplacement.getAppName();
               moduleName = emProxyReplacement.getModuleName();
               unitName = emProxyReplacement.getUnitName();
               return EntityManagerInvocationHandlerFactory.createTransactionalEntityManagerInvocationHandler(appName, moduleName, unitName, this.getPURegistry(appName, moduleName), emProxyReplacement.getSynchronizationType());
            } else if (obj instanceof EntityManagerFactoryReplacement) {
               EntityManagerFactoryReplacement emfr = (EntityManagerFactoryReplacement)obj;
               appName = emfr.getAppName();
               moduleName = emfr.getModuleName();
               unitName = emfr.getUnitName();
               BasePersistenceUnitInfo puInfo = (BasePersistenceUnitInfo)this.getPURegistry(appName, moduleName).getPersistenceUnit(unitName);
               EntityManagerFactory emf = puInfo.getEntityManagerFactory();
               EntityManagerFactoryProxyImpl proxyImpl = (EntityManagerFactoryProxyImpl)Proxy.getInvocationHandler(emf);
               proxyImpl.setAppName(appName);
               proxyImpl.setModuleName(moduleName);
               return proxyImpl;
            } else if (obj instanceof weblogic.ejb20.swap.EJBReplacer.UserTransactionReplacement) {
               if (this.utx == null) {
                  try {
                     InitialContext icx = new InitialContext();
                     this.utx = (UserTransaction)icx.lookup("javax.transaction.UserTransaction");
                  } catch (NamingException var10) {
                     throw new AssertionError("Unexpected Exception during activation: " + var10);
                  }
               }

               return this.utx;
            } else {
               return remoteReplacer.resolveObject(obj);
            }
         }
      }
   }

   private PersistenceUnitRegistry getPURegistry(String appName, String moduleId) {
      ApplicationAccess aa = ApplicationAccess.getApplicationAccess();
      ApplicationContextInternal ac = aa.getApplicationContext(appName);
      ModuleRegistry mr = ac.getModuleContext(moduleId).getRegistry();
      PersistenceUnitRegistryProvider prov = (PersistenceUnitRegistryProvider)mr.get(PersistenceUnitRegistryProvider.class.getName());
      return prov.getPersistenceUnitRegistry();
   }

   public void insertReplacer(Replacer replacer) {
   }

   public static class EntityManagerFactoryReplacement implements Serializable {
      private String appName;
      private String moduleName;
      private String unitName;

      public EntityManagerFactoryReplacement(String appName, String moduleName, String unitName) {
         this.appName = appName;
         this.moduleName = moduleName;
         this.unitName = unitName;
      }

      public String getAppName() {
         return this.appName;
      }

      public String getModuleName() {
         return this.moduleName;
      }

      public String getUnitName() {
         return this.unitName;
      }
   }
}
