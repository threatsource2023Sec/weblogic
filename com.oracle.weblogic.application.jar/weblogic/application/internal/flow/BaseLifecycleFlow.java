package weblogic.application.internal.flow;

import java.lang.reflect.Method;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.security.auth.login.LoginException;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ApplicationLifecycleEvent;
import weblogic.application.ApplicationLifecycleListener;
import weblogic.application.ApplicationStateListener;
import weblogic.application.ApplicationVersionLifecycleListener;
import weblogic.application.DeploymentOperationType;
import weblogic.application.WrappedDeploymentException;
import weblogic.application.internal.FlowContext;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.application.utils.TargetUtils;
import weblogic.j2ee.J2EELogger;
import weblogic.jndi.factories.java.javaURLContextFactory;
import weblogic.management.DeploymentException;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.classloaders.GenericClassLoader;

abstract class BaseLifecycleFlow extends BaseFlow {
   private boolean isAppDeployedLocally = true;
   LifecycleListenerAction preStart;
   LifecycleListenerAction postStart;
   LifecycleListenerAction postDeactivate;
   LifecycleListenerAction preStop;
   LifecycleListenerAction postStop;
   private static boolean methodsInitialized = false;
   private static Method preStartMethod;
   private static Method postStartMethod;
   private static Method preStopMethod;
   private static Method postDeactivateMethod;
   private static Method postStopMethod;

   public BaseLifecycleFlow(FlowContext appCtx) {
      super(appCtx);
      this.isAppDeployedLocally = TargetUtils.isDeployedLocally(appCtx.getBasicDeploymentMBean().getTargets());
      if (this.isDebugEnabled()) {
         this.debug("isAppDeployedLocally = " + this.isAppDeployedLocally);
      }

      initMethods();
      this.preStart = new LifecycleListenerAction(preStartMethod, ApplicationLifecycleListener.class, true, false, false);
      this.postStart = new LifecycleListenerAction(postStartMethod, ApplicationLifecycleListener.class, true, false, true);
      this.preStop = new LifecycleListenerAction(preStopMethod, ApplicationLifecycleListener.class, false, true, true);
      this.postDeactivate = new LifecycleListenerAction(postDeactivateMethod, ApplicationStateListener.class, false, true, false);
      this.postStop = new LifecycleListenerAction(postStopMethod, ApplicationLifecycleListener.class, false, true, false);
   }

   private static synchronized void initMethods() {
      if (!methodsInitialized) {
         try {
            preStartMethod = ApplicationLifecycleListener.class.getMethod("preStart", ApplicationLifecycleEvent.class);
            postStartMethod = ApplicationLifecycleListener.class.getMethod("postStart", ApplicationLifecycleEvent.class);
            preStopMethod = ApplicationLifecycleListener.class.getMethod("preStop", ApplicationLifecycleEvent.class);
            postDeactivateMethod = ApplicationStateListener.class.getMethod("postDeactivate", ApplicationLifecycleEvent.class);
            postStopMethod = ApplicationLifecycleListener.class.getMethod("postStop", ApplicationLifecycleEvent.class);
         } catch (NoSuchMethodException var1) {
            throw new AssertionError(var1);
         }

         methodsInitialized = true;
      }

   }

   private ApplicationLifecycleEvent createEvent() {
      return new ApplicationLifecycleEvent(this.appCtx, DeploymentOperationType.valueOf(this.appCtx.getDeploymentOperation()), this.appCtx.isStaticDeploymentOperation());
   }

   protected class CreateMainClassAction extends BaseAction implements PrivilegedAction {
      private final GenericClassLoader gcl;
      private final String className;

      CreateMainClassAction(GenericClassLoader gcl, String className) {
         super(null);
         this.gcl = gcl;
         this.className = className;
      }

      public Object run() {
         Class listenerClass = null;

         try {
            listenerClass = Class.forName(this.className, false, this.gcl);
            return listenerClass == null ? new DeploymentException("Cannot load ApplicationLifecycleListener class " + this.className) : listenerClass;
         } catch (ClassNotFoundException var3) {
            return new DeploymentException(var3);
         }
      }
   }

   class LifecycleListenerAction implements PrivilegedAction {
      private final Method method;
      private final Class clazz;
      private final boolean failFast;
      private final boolean reverseListenerList;
      private final boolean namingContextValid;
      protected ApplicationLifecycleListener listener = null;

      protected LifecycleListenerAction(Method method, Class clazz, boolean failFast, boolean reverseListenerList, boolean namingContextValid) {
         this.method = method;
         this.clazz = clazz;
         this.failFast = failFast;
         this.reverseListenerList = reverseListenerList;
         this.namingContextValid = namingContextValid;
      }

      Object invoke() throws DeploymentException {
         if (BaseLifecycleFlow.this.isAppDeployedLocally) {
            ErrorCollectionException errors = null;

            try {
               javaURLContextFactory.pushContext(this.namingContextValid ? BaseLifecycleFlow.this.appCtx.getRootContext() : null);
               Iterator it = this.getListeners();

               while(it.hasNext()) {
                  this.listener = (ApplicationLifecycleListener)it.next();
                  String runAs = BaseLifecycleFlow.this.appCtx.getAppListenerIdentity(this.listener);
                  Object result = null;
                  ApplicationContextInternal.SecurityProvider securityProvider = BaseLifecycleFlow.this.appCtx.getSecurityProvider();
                  if (runAs == null) {
                     result = securityProvider.invokePrivilegedAction(BaseLifecycleFlow.this.appCtx.getDeploymentInitiator(), (PrivilegedAction)this);
                  } else {
                     try {
                        result = securityProvider.invokePrivilegedAction(BaseLifecycleFlow.this.appCtx.getApplicationSecurityRealmName(), runAs, this);
                     } catch (LoginException var10) {
                     }
                  }

                  if (result != null) {
                     if (this.failFast) {
                        this.throwsException(result);
                     }

                     if (errors == null) {
                        errors = new ErrorCollectionException();
                     }

                     errors.addError((Throwable)result);
                  }
               }
            } finally {
               javaURLContextFactory.popContext();
            }

            if (errors != null && !errors.isEmpty()) {
               this.throwsException(errors);
            }
         }

         return null;
      }

      private void throwsException(Object obj) throws DeploymentException {
         if (obj instanceof DeploymentException) {
            throw (DeploymentException)obj;
         } else if (obj instanceof Throwable) {
            throw new WrappedDeploymentException((Throwable)obj);
         }
      }

      public Object run() {
         try {
            if (this.clazz.isInstance(this.listener)) {
               this.method.invoke(this.listener, BaseLifecycleFlow.this.createEvent());
            }

            return null;
         } catch (Throwable var2) {
            var2.addSuppressed(new Exception("Method's class: " + this.method.getDeclaringClass() + ", object's class: " + this.listener.getClass()));
            return var2;
         }
      }

      private Iterator getListeners() {
         if (!this.reverseListenerList) {
            return Arrays.asList(BaseLifecycleFlow.this.appCtx.getApplicationListeners()).iterator();
         } else {
            List list = new ArrayList();
            ApplicationLifecycleListener[] listeners = BaseLifecycleFlow.this.appCtx.getApplicationListeners();

            for(int i = listeners.length - 1; i >= 0; --i) {
               list.add(listeners[i]);
            }

            return list.iterator();
         }
      }
   }

   protected class CreateListenerAction extends BaseAction implements PrivilegedAction {
      private final GenericClassLoader gcl;
      private final String className;
      private final boolean nonVersionOnly;

      CreateListenerAction(GenericClassLoader gcl, String className, boolean nonVersionOnly) {
         super(null);
         this.gcl = gcl;
         this.className = className;
         this.nonVersionOnly = nonVersionOnly;
      }

      public Object run() {
         Class listenerClass = null;

         try {
            listenerClass = Class.forName(this.className, false, this.gcl);
            if (listenerClass == null) {
               return new DeploymentException("Cannot load ApplicationLifecycleListener class " + this.className);
            } else {
               Object listener = listenerClass.newInstance();
               if (listener instanceof ApplicationLifecycleListener) {
                  return listener;
               } else if (this.nonVersionOnly) {
                  return new DeploymentException("ApplicationLifecycleListener " + listenerClass.getName() + " was not an instanceof weblogic.application.ApplicationLifecycleListener");
               } else if (listener instanceof ApplicationVersionLifecycleListener) {
                  String appId = BaseLifecycleFlow.this.appCtx.getApplicationId();
                  if (ApplicationVersionUtils.getVersionId(appId) != null) {
                     return listener;
                  } else {
                     J2EELogger.logIgnoreAppVersionListenerForNonVersionApp(ApplicationVersionUtils.getDisplayName(appId), this.className);
                     return null;
                  }
               } else {
                  return new DeploymentException("Application Lifecycle Listener " + listenerClass.getName() + " was not an instanceof weblogic.application.ApplicationLifecycleListener or weblogic.application.ApplicationVersionLifecycleListener");
               }
            }
         } catch (ClassNotFoundException var4) {
            return new DeploymentException(var4);
         } catch (InstantiationException var5) {
            return new DeploymentException(var5);
         } catch (IllegalAccessException var6) {
            return new DeploymentException(var6);
         }
      }
   }

   private abstract class BaseAction implements PrivilegedAction {
      private BaseAction() {
      }

      Object invoke() throws DeploymentException {
         Object o = BaseLifecycleFlow.this.appCtx.getSecurityProvider().invokePrivilegedAction(BaseLifecycleFlow.this.appCtx.getDeploymentInitiator(), (PrivilegedAction)this);
         if (o instanceof DeploymentException) {
            throw (DeploymentException)o;
         } else if (o instanceof Throwable) {
            throw new WrappedDeploymentException((Throwable)o);
         } else {
            return o;
         }
      }

      // $FF: synthetic method
      BaseAction(Object x1) {
         this();
      }
   }
}
