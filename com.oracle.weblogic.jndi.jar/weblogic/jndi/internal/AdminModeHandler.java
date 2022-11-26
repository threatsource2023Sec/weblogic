package weblogic.jndi.internal;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Hashtable;
import javax.naming.NameNotFoundException;
import weblogic.management.runtime.ApplicationRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.utils.ApplicationVersionUtilsService;
import weblogic.utils.LocatorUtilities;

final class AdminModeHandler extends AbstractAdminModeHandler {
   private Hashtable listeners = new Hashtable();

   AdminModeHandler(ServerNamingNode node) {
      super(node);
   }

   private void addListener(ApplicationRuntimeMBean appRTMBean, String name, AdminModeStateChangeListener listener) {
      if (NamingDebugLogger.isDebugEnabled()) {
         NamingDebugLogger.debug("+++ AdminMode.addListener: name=" + name + ")");
      }

      AdminModeStateChangeListener old = (AdminModeStateChangeListener)this.listeners.put(name, listener);
      if (old != null) {
         appRTMBean.removePropertyChangeListener(old);
      }

      appRTMBean.addPropertyChangeListener(listener);
   }

   private void removeListener(String name) {
      if (!this.listeners.isEmpty()) {
         AdminModeStateChangeListener listener = (AdminModeStateChangeListener)this.listeners.remove(name);
         if (listener != null) {
            ApplicationVersionUtilsService avus = (ApplicationVersionUtilsService)LocatorUtilities.getService(ApplicationVersionUtilsService.class);
            ApplicationRuntimeMBean appRTMBean = avus.getCurrentApplicationRuntime();
            if (appRTMBean != null) {
               if (NamingDebugLogger.isDebugEnabled()) {
                  NamingDebugLogger.debug("+++ AdminMode.removeListener: name=" + name);
               }

               ((RuntimeMBeanDelegate)appRTMBean).removePropertyChangeListener(listener);
            }
         }
      }
   }

   void checkBind(String name, boolean isBindVersioned) {
      if (!isBindVersioned) {
         ApplicationVersionUtilsService avus = (ApplicationVersionUtilsService)LocatorUtilities.getService(ApplicationVersionUtilsService.class);
         ApplicationRuntimeMBean appRTMBean = avus.getCurrentApplicationRuntime();
         if (appRTMBean != null) {
            this.addListener(appRTMBean, name, new AdminModeStateChangeListener(name, this));
            if (appRTMBean.getActiveVersionState() == 1) {
               this.setAdminMode(name);
            }

         }
      }
   }

   void checkLookup(String name, String restOfName, Hashtable env) throws NameNotFoundException {
      if ((restOfName == null || restOfName.length() <= 0) && !this.node.isVersioned() && this.isAdminMode(name)) {
         ApplicationVersionUtilsService avus = (ApplicationVersionUtilsService)LocatorUtilities.getService(ApplicationVersionUtilsService.class);
         if (NamingDebugLogger.isDebugEnabled()) {
            NamingDebugLogger.debug("+++ AdminMode.checkLookup: Attempt to look up admin mode binding, name=" + name + "), adminRequest=" + avus.isAdminModeRequest());
         }

         if (!avus.isAdminModeRequest()) {
            if (NamingDebugLogger.isDebugEnabled()) {
               NamingDebugLogger.debug("+++ AdminMode.checkLookup failed: binding is in admin mode, only allow access from admin channel.");
            }

            throw this.node.newNameNotFoundException(new AdminModeAccessException("Unable to resolve '" + name + "'. Resolved '" + this.node.getNameInNamespace() + "'"), restOfName, env);
         }
      }
   }

   void checkUnbind(String name, boolean isBindVersioned) {
      if (!isBindVersioned) {
         this.unsetAdminMode(name);
         this.removeListener(name);
      }
   }

   private final class AdminModeStateChangeListener implements PropertyChangeListener {
      String name;
      AdminModeHandler handler;

      private AdminModeStateChangeListener(String name, AdminModeHandler handler) {
         this.name = name;
         this.handler = handler;
      }

      public void propertyChange(PropertyChangeEvent evt) {
         if (evt.getPropertyName().equalsIgnoreCase("ActiveVersionState")) {
            Object oldValue = evt.getOldValue();
            Object newValue = evt.getNewValue();
            if (oldValue instanceof Integer && newValue instanceof Integer) {
               int oldState = (Integer)oldValue;
               int newState = (Integer)newValue;
               if (NamingDebugLogger.isDebugEnabled()) {
                  NamingDebugLogger.debug("+++ AdminModeStateChange for name=" + this.name + ", oldState=" + oldState + ", newState=" + newState);
               }

               if (oldState == newState) {
                  return;
               }

               if (oldState == 1) {
                  this.handler.unsetAdminMode(this.name);
               } else if (newState == 1) {
                  this.handler.setAdminMode(this.name);
               }

               if (newState == 0) {
                  this.handler.removeListener(this.name);
               }
            }
         }

      }

      // $FF: synthetic method
      AdminModeStateChangeListener(String x1, AdminModeHandler x2, Object x3) {
         this(x1, x2);
      }
   }
}
