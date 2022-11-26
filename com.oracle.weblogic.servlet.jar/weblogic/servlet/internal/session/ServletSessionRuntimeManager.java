package weblogic.servlet.internal.session;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import weblogic.management.ManagementException;
import weblogic.management.runtime.ServletSessionRuntimeMBean;
import weblogic.servlet.security.internal.WebAppSecurity;
import weblogic.servlet.spi.SubjectHandle;

final class ServletSessionRuntimeManager implements SessionConstants {
   private Map runtimesBySessionKey;

   static final ServletSessionRuntimeManager getInstance() {
      return ServletSessionRuntimeManager.SINGLETON.instance;
   }

   private ServletSessionRuntimeManager() {
      this.runtimesBySessionKey = Collections.synchronizedMap(new HashMap(128));
   }

   static String getSessionKey(SessionData session) {
      return session.id + "!" + session.creationTime;
   }

   private ServletSessionRuntimeMBean find(SessionData session) {
      return (ServletSessionRuntimeMBean)this.runtimesBySessionKey.get(getSessionKey(session));
   }

   ServletSessionRuntimeMBean findOrCreate(SessionData session) {
      ServletSessionRuntimeMBean result = this.find(session);
      return result != null ? result : this.create(session);
   }

   void removeAndCreate(SessionData session) {
      ServletSessionRuntimeMBean runtime = (ServletSessionRuntimeMBean)this.runtimesBySessionKey.remove(session.oldId + "!" + session.creationTime);
      if (runtime != null) {
         this.runtimesBySessionKey.put(getSessionKey(session), runtime);
      }

   }

   private synchronized ServletSessionRuntimeMBean create(SessionData session) {
      ServletSessionRuntimeMBean result = this.find(session);
      if (result != null) {
         return result;
      } else {
         result = (ServletSessionRuntimeMBean)this.getKernelSubject().run((PrivilegedAction)(new CreateAction(session)));
         if (result != null) {
            this.runtimesBySessionKey.put(getSessionKey(session), result);
         }

         return result;
      }
   }

   void destroy(SessionData session) {
      this.getKernelSubject().run((PrivilegedAction)(new DestroyAction(session)));
   }

   private SubjectHandle getKernelSubject() {
      return (SubjectHandle)AccessController.doPrivileged(new PrivilegedAction() {
         public SubjectHandle run() {
            return WebAppSecurity.getProvider().getKernelSubject();
         }
      });
   }

   // $FF: synthetic method
   ServletSessionRuntimeManager(Object x0) {
      this();
   }

   private class DestroyAction implements PrivilegedAction {
      SessionData session;

      DestroyAction(SessionData session) {
         this.session = session;
      }

      public Object run() {
         String sessionKey = ServletSessionRuntimeManager.getSessionKey(this.session);

         try {
            ServletSessionRuntimeMBeanImpl toRemove = (ServletSessionRuntimeMBeanImpl)ServletSessionRuntimeManager.this.runtimesBySessionKey.remove(sessionKey);
            if (toRemove == null) {
               return null;
            }

            toRemove.unregister();
         } catch (ManagementException var4) {
            String msg = sessionKey + " was not properly unregistered.";
            HTTPSessionLogger.logErrorUnregisteringServletSessionRuntime(msg, (Throwable)null);
         }

         return null;
      }
   }

   private class CreateAction implements PrivilegedAction {
      SessionData session;

      CreateAction(SessionData session) {
         this.session = session;
      }

      public Object run() {
         try {
            return new ServletSessionRuntimeMBeanImpl(this.session);
         } catch (ManagementException var2) {
            HTTPSessionLogger.logErrorCreatingServletSessionRuntimeMBean(var2);
            return null;
         }
      }
   }

   private static class SINGLETON {
      static ServletSessionRuntimeManager instance = new ServletSessionRuntimeManager();
   }
}
