package weblogic.security;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.HashSet;
import java.util.Iterator;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.auth.message.config.AuthConfigFactory;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Optional;
import org.jvnet.hk2.annotations.Service;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorDiff;
import weblogic.descriptor.DescriptorUpdateEvent;
import weblogic.descriptor.DescriptorUpdateFailedException;
import weblogic.descriptor.DescriptorUpdateListener;
import weblogic.descriptor.DescriptorUpdateRejectedException;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ConfigurationError;
import weblogic.management.configuration.SecurityConfigurationMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.security.ProviderMBean;
import weblogic.management.security.RDBMSSecurityStoreMBean;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authentication.UserLockoutManagerMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.jaspic.AuthConfigFactoryImpl;
import weblogic.security.jaspic.RegStoreFileParser;
import weblogic.security.net.ConnectionFilter;
import weblogic.security.net.ConnectionFilterRulesListener;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityServiceRuntimeException;
import weblogic.security.shared.LoggerWrapper;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.utils.NestedRuntimeException;
import weblogic.utils.annotation.Secure;

@Service
@Named
@RunLevel(10)
@Secure
public class PreSecurityService extends AbstractServerService implements PropertyChangeListener, DescriptorUpdateListener {
   private static final AuthenticatedSubject KERNELID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String DEFAULT_REALM = "weblogic.security.acl.internal.FileRealm";
   private static final String WLREALMNAME = "weblogic";
   @Inject
   @Named("X509CertRegisterService")
   private ServerService dependencyOnX509CertRegisterService;
   @Inject
   @Named("EmbeddedLDAP")
   private ServerService dependencyOnEmbeddedLDAP;
   @Inject
   @Optional
   @Named("JpsDefaultService")
   private ServerService dependencyOnOPSS;
   private SecurityConfigurationMBean newMbean = null;
   private SecurityServiceManager secmgr;
   private static LoggerWrapper log = LoggerWrapper.getInstance("SecurityService");
   public static boolean isPerfDebug = false;
   private static PreSecurityService singleton;

   public PreSecurityService() {
      setSingleton(this);
      if (log.isDebugEnabled()) {
         log.debug("PreSecurityService init");
      }

   }

   private static void setSingleton(PreSecurityService param) {
      singleton = param;
   }

   static PreSecurityService getSingleton() {
      return singleton;
   }

   SecurityConfigurationMBean getSecurityConfigurationMBean(AuthenticatedSubject kernelID) {
      SecurityServiceManager.checkKernelIdentity(kernelID);
      return this.newMbean;
   }

   SecurityServiceManager getSecurityServiceManager(AuthenticatedSubject kernelID) {
      SecurityServiceManager.checkKernelIdentity(kernelID);
      return this.secmgr;
   }

   public void start() throws ServiceFailureException {
      if (log.isDebugEnabled()) {
         log.debug("starting PreSecurityService");
      }

      long startTime = 0L;
      long currentTime = 0L;
      long runTime = 0L;
      if (isPerfDebug) {
         dbgPsr("PreSecurityService start");
         startTime = System.currentTimeMillis();
      }

      try {
         this.initializeMBean();
         this.initializeConnectionFilter();
         this.initializeJASPICFactory();

         try {
            new SecurityRuntime(this.newMbean);
         } catch (ManagementException var8) {
            SecurityLogger.logErrorCreatingSecurityRuntime(var8);
            throw new ServiceFailureException(var8);
         }

         this.secmgr = new SecurityServiceManager(KERNELID);
         this.secmgr.preInitialize(KERNELID);
         if (isPerfDebug) {
            currentTime = System.currentTimeMillis();
            runTime = currentTime - startTime;
            dbgPsr("PreSecurityService start = " + runTime);
         }
      } catch (SecurityServiceRuntimeException var9) {
         throw new ServiceFailureException(var9);
      } catch (RuntimeException var10) {
         throw var10;
      } catch (Exception var11) {
         SecurityLogger.logStackTrace(var11);
         throw new ServiceFailureException(var11);
      }

      if (log.isDebugEnabled()) {
         log.debug("finished starting PreSecurityService");
      }

   }

   public void stop() throws ServiceFailureException {
      if (log.isDebugEnabled()) {
         log.debug("PreSecurityService stop");
      }

   }

   public void halt() throws ServiceFailureException {
      if (log.isDebugEnabled()) {
         log.debug("PreSecurityService halt");
      }

   }

   private void initializeMBean() {
      this.newMbean = ManagementService.getRuntimeAccess(KERNELID).getDomain().getSecurityConfiguration();
      if (this.newMbean.getSalt() == null) {
         throw new ConfigurationError(SecurityLogger.getSaltNotSet());
      } else {
         this.newMbean.addPropertyChangeListener(this);
         this.newMbean.getDescriptor().addUpdateListener(this);
      }
   }

   private void setConnectionLoggerEnabled() {
      SecurityService.setConnectionLoggerEnabled(this.newMbean.getConnectionLoggerEnabled());
   }

   private void setCompatibilityConnectionFiltersEnabled() {
      SecurityService.setCompatibilityConnectionFiltersEnabled(this.newMbean.getCompatibilityConnectionFiltersEnabled());
   }

   private synchronized void setConnectionFilterRules() {
      String[] filterList = this.newMbean.getConnectionFilterRules();

      try {
         Class c = Class.forName(SecurityService.getFilterClass());
         if (ConnectionFilterRulesListener.class.isAssignableFrom(c)) {
            try {
               String methodName = "setRules";
               Class[] params = new Class[]{String[].class};
               Method setter = c.getMethod(methodName, params);
               Object[] parameters = new Object[]{filterList};
               setter.invoke(SecurityService.getConnectionFilter(), parameters);
            } catch (InvocationTargetException var7) {
               Throwable thr = var7.getTargetException();
               if (thr.toString().startsWith("java.text.ParseException")) {
                  SecurityLogger.logBootFilterCritical(thr.getMessage());
               }

               throw var7;
            }
         }

      } catch (Throwable var8) {
         SecurityLogger.logStackTrace(var8);
         throw new NestedRuntimeException(SecurityLogger.getProblemWithConnFilterRules(), var8);
      }
   }

   private void initializeConnectionFilter() {
      this.setConnectionFilter();
      this.setConnectionLoggerEnabled();
      this.setCompatibilityConnectionFiltersEnabled();
   }

   private synchronized void setConnectionFilter() {
      String filterClass = this.newMbean.getConnectionFilter();
      SecurityService.setFilterClass(filterClass);
      if (filterClass != null) {
         try {
            SecurityService.setConnectionFilter((ConnectionFilter)Class.forName(filterClass).newInstance());
            SecurityService.setEnableConnectionFilter(true);
            this.setConnectionFilterRules();
         } catch (Exception var3) {
            SecurityLogger.logStackTrace(var3);
            throw new NestedRuntimeException(SecurityLogger.getProblemWithConnFilter(), var3);
         }
      } else {
         SecurityService.setEnableConnectionFilter(false);
         SecurityService.setConnectionFilter((ConnectionFilter)null);
      }

   }

   private void initializeJASPICFactory() {
      if (AuthConfigFactory.getFactory() == null) {
         String parentDir = DomainDir.getSecurityDir();
         AuthConfigFactory.setFactory(new AuthConfigFactoryImpl(new RegStoreFileParser(parentDir, "auth.conf", false)));
      }

   }

   public synchronized void propertyChange(PropertyChangeEvent evt) {
      if (log.isDebugEnabled()) {
         log.debug("propertyChange, event= " + evt);
      }

      String att = evt.getPropertyName();
      if (att.equalsIgnoreCase("ConnectionFilter")) {
         this.setConnectionFilter();
      }

      if (att.equalsIgnoreCase("ConnectionFilterRules") && SecurityService.getConnectionFilterEnabled()) {
         this.setConnectionFilterRules();
      }

      if (att.equalsIgnoreCase("ConnectionLoggerEnabled")) {
         this.setConnectionLoggerEnabled();
      }

      if (att.equalsIgnoreCase("CompatibilityConnectionFiltersEnabled")) {
         this.setCompatibilityConnectionFiltersEnabled();
      }

   }

   public void prepareUpdate(DescriptorUpdateEvent event) throws DescriptorUpdateRejectedException {
   }

   public synchronized void activateUpdate(DescriptorUpdateEvent event) throws DescriptorUpdateFailedException {
      HashSet restartRealms = new HashSet();
      DescriptorDiff diff = event.getDiff();
      Iterator it = diff.iterator();

      while(true) {
         while(it.hasNext()) {
            BeanUpdateEvent ev = (BeanUpdateEvent)it.next();
            DescriptorBean proposedBean = ev.getProposedBean();
            if (proposedBean instanceof SecurityConfigurationMBean) {
               if (log.isDebugEnabled()) {
                  log.debug("handling security config changes");
               }

               BeanUpdateEvent.PropertyUpdate[] updated = ev.getUpdateList();

               for(int i = 0; i < updated.length; ++i) {
                  BeanUpdateEvent.PropertyUpdate propertyUpdate = updated[i];
                  String name = propertyUpdate.getPropertyName();
                  if ("Realms".equals(name)) {
                     RealmMBean newRealm;
                     if (propertyUpdate.getUpdateType() == 2) {
                        if (ManagementService.getRuntimeAccess(KERNELID).isAdminServer()) {
                           newRealm = (RealmMBean)propertyUpdate.getAddedObject();
                           SecurityServiceManager.initializeRealm(KERNELID, newRealm.getName());
                        }
                     } else if (propertyUpdate.getUpdateType() == 3) {
                        newRealm = (RealmMBean)propertyUpdate.getRemovedObject();
                        this.secmgr.shutdownRealm(KERNELID, newRealm.getName());
                     }
                  }
               }
            } else if (proposedBean instanceof RealmMBean || proposedBean instanceof UserLockoutManagerMBean || proposedBean instanceof RDBMSSecurityStoreMBean || proposedBean instanceof ProviderMBean) {
               RealmMBean realm = this.getRealmForBean(proposedBean);
               if (realm != null && realm.isAutoRestartOnNonDynamicChanges()) {
                  boolean needsRestart = false;
                  BeanUpdateEvent.PropertyUpdate[] updated = ev.getUpdateList();

                  for(int i = 0; i < updated.length; ++i) {
                     BeanUpdateEvent.PropertyUpdate propertyUpdate = updated[i];
                     String name = propertyUpdate.getPropertyName();
                     if (!propertyUpdate.isDynamic()) {
                        needsRestart = true;
                     }
                  }

                  if (needsRestart) {
                     restartRealms.add(realm.getName());
                  }
               } else if (log.isDebugEnabled()) {
                  log.debug("Realm restart is not enabled so realm or provider changes may require a restart of the server.");
               }
            }
         }

         if (!restartRealms.isEmpty()) {
            Iterator var13 = restartRealms.iterator();

            while(var13.hasNext()) {
               String realmName = (String)var13.next();
               this.secmgr.restartRealm(KERNELID, realmName);
            }
         }

         return;
      }
   }

   public void rollbackUpdate(DescriptorUpdateEvent event) {
   }

   public static final void dbgPsr(String msg) {
      System.out.println(msg);
   }

   private RealmMBean getRealmForBean(DescriptorBean bean) {
      RealmMBean realm = null;

      for(DescriptorBean curBean = bean; curBean != null && realm == null; curBean = curBean.getParentBean()) {
         if (curBean instanceof RealmMBean) {
            realm = (RealmMBean)curBean;
         }
      }

      return realm;
   }
}
