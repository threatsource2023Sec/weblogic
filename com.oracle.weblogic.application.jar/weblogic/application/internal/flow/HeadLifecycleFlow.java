package weblogic.application.internal.flow;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ApplicationException;
import weblogic.application.ApplicationLifecycleEvent;
import weblogic.application.ApplicationLifecycleListener;
import weblogic.application.ApplicationVersionLifecycleListener;
import weblogic.application.event.ApplicationEventManager;
import weblogic.application.internal.ApplicationVersionLifecycleNotifier;
import weblogic.application.internal.FlowContext;
import weblogic.j2ee.J2EELogger;
import weblogic.j2ee.descriptor.wl.ListenerBean;
import weblogic.j2ee.descriptor.wl.ShutdownBean;
import weblogic.j2ee.descriptor.wl.StartupBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.logging.Loggable;
import weblogic.management.DeploymentException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.t3.srvr.ServerRuntime;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.GenericClassLoader;

public final class HeadLifecycleFlow extends BaseLifecycleFlow {
   private static final ApplicationLifecycleListener[] EMPTY_LISTENERS = new ApplicationLifecycleListener[0];

   public HeadLifecycleFlow(FlowContext appCtx) {
      super(appCtx);
   }

   private void addListenerJarToLoader(GenericClassLoader gcl, String jarUri) throws DeploymentException {
      URL u = gcl.getResource(this.appCtx.getApplicationId() + "#" + jarUri);
      if (u == null) {
         Loggable l = J2EELogger.logUnabletoFindLifecycleJarLoggable(this.appCtx.getApplicationId(), jarUri);
         throw new DeploymentException(l.getMessage());
      } else {
         gcl.addClassFinder(new ClasspathClassFinder2(u.getFile()));
      }
   }

   private Class createMainClass(String className, String jarUri) throws DeploymentException {
      GenericClassLoader gcl = this.appCtx.getAppClassLoader();
      if (jarUri != null) {
         this.addListenerJarToLoader(gcl, jarUri);
      }

      BaseLifecycleFlow.CreateMainClassAction action = new BaseLifecycleFlow.CreateMainClassAction(gcl, className);
      return (Class)action.invoke();
   }

   private Object createListener(String className, String jarUri) throws DeploymentException {
      return this.createListener(className, jarUri, false);
   }

   private Object createListener(String className, String jarUri, boolean nonVersionOnly) throws DeploymentException {
      GenericClassLoader gcl = this.appCtx.getAppClassLoader();
      if (jarUri != null) {
         this.addListenerJarToLoader(gcl, jarUri);
      }

      BaseLifecycleFlow.CreateListenerAction action = new BaseLifecycleFlow.CreateListenerAction(gcl, className, nonVersionOnly);
      return action.invoke();
   }

   private ApplicationLifecycleListener createStartupListener(String className, String uri) throws DeploymentException {
      return new MainListener(this.createMainClass(className, uri)) {
         public void preStart(ApplicationLifecycleEvent evt) throws ApplicationException {
            this.invokeMain();
         }
      };
   }

   private ApplicationLifecycleListener createShutdownListener(String className, String uri) throws DeploymentException {
      return new MainListener(this.createMainClass(className, uri)) {
         public void postStop(ApplicationLifecycleEvent evt) throws ApplicationException {
            this.invokeMain();
         }
      };
   }

   private ApplicationLifecycleListener[] createListeners() throws DeploymentException {
      WeblogicApplicationBean wlappDD = this.appCtx.getWLApplicationDD();
      List factoryListeners = ApplicationEventManager.getInstance().createListeners(this.appCtx);
      if (wlappDD == null) {
         return factoryListeners.size() == 0 ? EMPTY_LISTENERS : (ApplicationLifecycleListener[])factoryListeners.toArray(new ApplicationLifecycleListener[0]);
      } else {
         List listeners = new ArrayList();
         List versionListeners = new LinkedList();
         if (wlappDD != null) {
            ListenerBean[] l = wlappDD.getListeners();
            int i;
            if (l != null) {
               Map appListenerIdentityMap = null;

               for(i = 0; i < l.length; ++i) {
                  Object listener = this.createListener(l[i].getListenerClass(), l[i].getListenerUri());
                  if (listener instanceof ApplicationLifecycleListener) {
                     listeners.add(listener);
                  } else if (listener instanceof ApplicationVersionLifecycleListener) {
                     versionListeners.add((ApplicationVersionLifecycleListener)listener);
                  }

                  if (l[i].getRunAsPrincipalName() != null) {
                     String runAs = this.getRunAsIdentity(l[i]);
                     if (runAs != null) {
                        if (appListenerIdentityMap == null) {
                           appListenerIdentityMap = new HashMap();
                        }

                        appListenerIdentityMap.put(listener, runAs);
                     }
                  }
               }

               this.appCtx.setAppListenerIdentityMappings((Map)(appListenerIdentityMap != null ? appListenerIdentityMap : Collections.EMPTY_MAP));
               this.appCtx.setApplicationVersionNotifier(new ApplicationVersionLifecycleNotifier(this.appCtx.getApplicationId(), versionListeners));
            }

            StartupBean[] s = wlappDD.getStartups();
            if (s != null) {
               for(i = 0; i < s.length; ++i) {
                  listeners.add(this.createStartupListener(s[i].getStartupClass(), s[i].getStartupUri()));
               }
            }

            ShutdownBean[] sh = wlappDD.getShutdowns();
            if (sh != null) {
               for(int i = 0; i < sh.length; ++i) {
                  listeners.add(this.createShutdownListener(sh[i].getShutdownClass(), sh[i].getShutdownUri()));
               }
            }
         }

         listeners.addAll(factoryListeners);
         ApplicationLifecycleListener[] a = new ApplicationLifecycleListener[listeners.size()];
         return (ApplicationLifecycleListener[])((ApplicationLifecycleListener[])listeners.toArray(a));
      }
   }

   private String getRunAsIdentity(ListenerBean bean) throws DeploymentException {
      String identity = bean.getRunAsPrincipalName();
      if (!this.appCtx.getSecurityProvider().isIdentityValid(this.appCtx.getApplicationSecurityRealmName(), identity)) {
         Loggable log = J2EELogger.logRunAsPrincipalNotFoundLoggable(this.appCtx.getApplicationId(), bean.getListenerClass(), identity);
         throw new DeploymentException(log.getMessageText());
      } else {
         this.checkDeployUserPrivileges(bean, identity);
         return identity;
      }
   }

   private void checkDeployUserPrivileges(ListenerBean bean, String identity) throws DeploymentException {
      AuthenticatedSubject currentSubject = this.appCtx.getDeploymentInitiator();
      ApplicationContextInternal.SecurityProvider securityProvider = this.appCtx.getSecurityProvider();
      if (currentSubject != null && (ServerRuntime.theOne().getStateVal() != 1 || !securityProvider.isUserAnonymous(currentSubject)) && securityProvider.isAdminPrivilegeEscalation(currentSubject, identity, this.appCtx.getApplicationSecurityRealmName())) {
         Loggable log = J2EELogger.logAttemptToBumpUpPrivilegesWithRunAsLoggable(this.appCtx.getApplicationId(), bean.getListenerClass(), bean.getRunAsPrincipalName());
         throw new DeploymentException(log.getMessageText());
      }
   }

   public void prepare() throws DeploymentException {
      this.appCtx.setApplicationListeners(this.createListeners());
      this.preStart.invoke();
   }

   public void unprepare() throws DeploymentException {
      try {
         this.postStop.invoke();
      } finally {
         this.appCtx.setApplicationListeners((ApplicationLifecycleListener[])null);
         this.appCtx.setApplicationVersionNotifier((ApplicationVersionLifecycleNotifier)null);
      }

   }

   public void deactivate() throws DeploymentException {
      this.postDeactivate.invoke();
   }

   private abstract static class MainListener extends ApplicationLifecycleListener {
      private static final Class[] MAIN_SIGNATURE = new Class[]{String[].class};
      private static final Object[] MAIN_ARGS = new Object[]{new String[0]};
      private final Class mainClass;
      protected Method mainMethod;

      MainListener(Class mainClass) throws DeploymentException {
         this.mainClass = mainClass;

         try {
            this.mainMethod = mainClass.getMethod("main", MAIN_SIGNATURE);
         } catch (NoSuchMethodException var3) {
            throw new DeploymentException(var3);
         }
      }

      protected void invokeMain() throws ApplicationException {
         try {
            this.mainMethod.invoke((Object)null, MAIN_ARGS);
         } catch (IllegalAccessException var3) {
            throw new AssertionError(var3);
         } catch (IllegalArgumentException var4) {
            throw new AssertionError(var4);
         } catch (InvocationTargetException var5) {
            Throwable th = var5.getTargetException();
            if (th == null) {
               th = var5.getCause();
            }

            if (th == null) {
               th = var5;
            }

            throw new ApplicationException((Throwable)th);
         }
      }
   }
}
