package weblogic.ejb.container.deployer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.naming.Context;
import javax.naming.LinkRef;
import javax.naming.Name;
import javax.naming.NamingException;
import weblogic.application.naming.Environment;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.ClientDrivenBeanInfo;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.jndi.ClassTypeOpaqueReference;
import weblogic.logging.Loggable;

class Ejb2JndiBinder implements EjbJndiBinder {
   protected static final DebugLogger debugLogger;
   protected final ClientDrivenBeanInfo cdbi;
   protected final EjbJndiService jndiService;

   Ejb2JndiBinder(ClientDrivenBeanInfo beanInfo, Environment env) throws NamingException {
      this.cdbi = beanInfo;
      this.jndiService = new EjbJndiService(this.cdbi.getClientsOnSameServer(), env);
   }

   private void bindHome() throws WLDeploymentException {
      if (!this.cdbi.getGeneratedJndiNameForHome().equals(this.cdbi.getJNDINameAsString()) || this.cdbi.hasDeclaredRemoteHome() && this.cdbi.isVersionGreaterThan30()) {
         try {
            if (this.cdbi.getJNDINameAsString() != null && !this.cdbi.getGeneratedJndiNameForHome().equals(this.cdbi.getJNDINameAsString())) {
               this.jndiService.bind(this.cdbi.getJNDINameAsString(), this.cdbi.getRemoteHome(), this.cdbi.getClusteringDescriptor().getHomeIsClusterable(), true);
            }

            this.bindPortableNames(this.cdbi.getRemoteHome(), this.cdbi.getPortableJNDINames(), this.cdbi.getClusteringDescriptor().getHomeIsClusterable(), true);
         } catch (NamingException var3) {
            Loggable l = EJBLogger.logHomeJNDIRebindFailedLoggable(this.cdbi.getJNDINameAsString());
            throw new WLDeploymentException(l.getMessageText(), var3);
         }
      }
   }

   protected void bindToNameSpaces(Set portableNames, Object obj, boolean isRemote) throws WLDeploymentException {
      if (portableNames != null) {
         Iterator var4 = portableNames.iterator();

         while(var4.hasNext()) {
            String portableName = (String)var4.next();

            try {
               if (portableName.startsWith("java:module")) {
                  this.jndiService.bindInModuleNS(portableName, obj);
               } else if (portableName.startsWith("java:app")) {
                  this.jndiService.bindInAppNS(portableName, obj);
               } else if (portableName.startsWith("java:global")) {
                  this.jndiService.bindInGlobalNS(portableName, obj, isRemote);
               }
            } catch (NamingException var8) {
               Loggable l = EJBLogger.logPortableJNDIBindFailedLoggable(portableName);
               throw new WLDeploymentException(l.getMessageText(), var8);
            }
         }

      }
   }

   protected void nonRepBindToNameSpaces(Set portableNames, Object obj, boolean isRemote) throws WLDeploymentException {
      if (portableNames != null) {
         Iterator var4 = portableNames.iterator();

         while(var4.hasNext()) {
            String portableName = (String)var4.next();

            try {
               if (portableName.startsWith("java:module")) {
                  this.jndiService.nonRepBindInModuleNS(portableName, obj);
               } else if (portableName.startsWith("java:app")) {
                  this.jndiService.nonRepBindInAppNS(portableName, obj);
               } else if (portableName.startsWith("java:global")) {
                  this.jndiService.nonRepBindInGlobalNS(portableName, obj, isRemote);
               }
            } catch (NamingException var8) {
               Loggable l = EJBLogger.logPortableJNDIBindFailedLoggable(portableName);
               throw new WLDeploymentException(l.getMessageText(), var8);
            }
         }

      }
   }

   protected void bindPortableNames(Object obj, Set portableNames, boolean repBind, boolean isRemote) throws WLDeploymentException {
      assert portableNames != null && !portableNames.isEmpty();

      Set names = new HashSet(portableNames);
      String portableName = this.pickAndBindInGlobalNS(names, obj, repBind, isRemote);
      if (!repBind) {
         this.nonRepBindToNameSpaces(names, new LinkRef(portableName), isRemote);
      } else {
         this.bindToNameSpaces(names, new LinkRef(portableName), isRemote);
      }

   }

   protected String pickAndBindInGlobalNS(Set portableNames, Object obj, boolean repBind, boolean isRemote) throws WLDeploymentException {
      if (portableNames != null && !portableNames.isEmpty()) {
         String pickedName = null;
         Iterator it = portableNames.iterator();

         while(it.hasNext()) {
            String portableName = (String)it.next();
            if (portableName.startsWith("java:global")) {
               pickedName = portableName;
               it.remove();

               try {
                  if (!repBind) {
                     this.jndiService.nonRepBindInGlobalNS(pickedName, obj, isRemote);
                     break;
                  }

                  this.jndiService.bindInGlobalNS(pickedName, obj, isRemote);
               } catch (NamingException var10) {
                  Loggable l = EJBLogger.logPortableJNDIBindFailedLoggable(portableName);
                  throw new WLDeploymentException(l.getMessageText(), var10);
               }
            }
         }

         if (pickedName == null) {
            throw new AssertionError("The names " + portableNames + "are not portable!");
         } else {
            return pickedName;
         }
      } else {
         return null;
      }
   }

   private void bindLocalHome() throws WLDeploymentException {
      String customJNDIName = this.cdbi.getLocalJNDINameAsString();

      try {
         LocalViewRef ref = new LocalViewRef(this.cdbi.getLocalHome(), this.cdbi.getLocalHomeClass());
         if (customJNDIName != null) {
            this.jndiService.bind(customJNDIName, ref, false, false);
            this.nonRepBindToNameSpaces(this.cdbi.getLocalPortableJNDINames(), new LinkRef(customJNDIName), false);
         } else {
            this.bindPortableNames(ref, this.cdbi.getLocalPortableJNDINames(), false, false);
         }

      } catch (NamingException var4) {
         Loggable l = EJBLogger.logLocalHomeJNDIRebindFailedLoggable(customJNDIName, var4);
         throw new WLDeploymentException(l.getMessageText(), var4);
      }
   }

   private void bindStatelessEO(SessionBeanInfo sbi) throws WLDeploymentException {
      assert sbi.isStateless();

      Object eo = sbi.getRemoteHome().allocateEO();
      String jndiName = sbi.getGeneratedJndiNameFor("EO");

      try {
         this.jndiService.bind(jndiName, eo, sbi.getClusteringDescriptor().getBeanIsClusterable(), false);
      } catch (NamingException var6) {
         Loggable l = EJBLogger.logStatelessEOJNDIBindErrorLoggable(jndiName);
         throw new WLDeploymentException(l.getMessageText(), var6);
      }
   }

   public void bindToJNDI() throws WLDeploymentException {
      if (debugLogger.isDebugEnabled()) {
         debug("bindToJNDI() for bean:" + this.cdbi.getEJBName() + ".");
      }

      try {
         if (this.cdbi.hasDeclaredRemoteHome()) {
            if (this.cdbi.isSessionBean() && ((SessionBeanInfo)this.cdbi).isStateless()) {
               this.bindStatelessEO((SessionBeanInfo)this.cdbi);
            }

            this.bindHome();
         }

         if (this.cdbi.hasDeclaredLocalHome()) {
            this.bindLocalHome();
         }

      } catch (WLDeploymentException var2) {
         this.unbindFromJNDI();
         throw var2;
      }
   }

   public void unbindFromJNDI() {
      if (debugLogger.isDebugEnabled()) {
         debug("unbindFromJNDI() for bean:" + this.cdbi.getEJBName() + ".");
      }

      this.jndiService.unbindAll();
   }

   public boolean isNameBound(String name) {
      return this.jndiService.isNameBound(name);
   }

   private static void debug(String s) {
      debugLogger.debug("[Ejb2JndiBinder] " + s);
   }

   static {
      debugLogger = EJBDebugService.deploymentLogger;
   }

   protected static final class LocalViewRef implements ClassTypeOpaqueReference {
      private final Class type;
      private final Object referent;

      LocalViewRef(Object ref, Class type) {
         this.referent = ref;
         this.type = type;
      }

      public Object getReferent(Name name, Context ctx) throws NamingException {
         return this.referent;
      }

      public Class getObjectClass() {
         return this.type;
      }
   }
}
