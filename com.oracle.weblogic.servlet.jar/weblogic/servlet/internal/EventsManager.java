package weblogic.servlet.internal;

import java.io.IOException;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;
import weblogic.i18n.logging.Loggable;
import weblogic.j2ee.descriptor.ListenerBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.management.DeploymentException;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.WebLogicServletContextListener;
import weblogic.servlet.internal.session.SharedSessionData;
import weblogic.servlet.security.internal.WebAppSecurity;
import weblogic.servlet.spi.SubjectHandle;
import weblogic.servlet.spi.WebServerRegistry;

public final class EventsManager {
   private final List wlCtxListeners = new ArrayList();
   private final List ctxListeners = new ArrayList();
   private final List ctxAttrListeners = new ArrayList();
   private final List sessListeners = new ArrayList();
   private final List sessIdListeners = new ArrayList();
   private final List sessAttrListeners = new ArrayList();
   private final List authenListeners = new ArrayList();
   private final List requestListeners = new ArrayList();
   private final List requestAttrListeners = new ArrayList();
   private final List tldCtxListeners = new ArrayList();
   private final Set tldCtxListenerClasses = new HashSet();
   private final List dynamicCtxListeners = new ArrayList();
   private final WebAppServletContext context;
   private boolean hasRequestListeners = false;
   private RegistrationListener regListener;
   private static WebServerRegistry registry = WebServerRegistry.getInstance();
   private static final String[] internalListeners;
   private static final String internalSessAttrListener = "weblogic.servlet.internal.session.WLSessionAttributeChangedListener";
   private static final String WELD_INITIAL_LISTENER = "org.jboss.weld.module.web.servlet.WeldInitialListener";
   private static final String WELD_TERMINAL_LISTENER = "org.jboss.weld.module.web.servlet.WeldTerminalListener";

   public EventsManager(WebAppServletContext ctx) {
      this.context = ctx;
   }

   public boolean hasRequestListeners() {
      return this.hasRequestListeners;
   }

   void registerPreparePhaseListeners() throws DeploymentException {
      this.registerEventListeners(true);
   }

   void registerEventListeners() throws DeploymentException {
      this.registerEventListeners(false);
   }

   private void registerEventListeners(boolean preparePhase) throws DeploymentException {
      String listenerClz = null;

      try {
         for(int i = 0; i < internalListeners.length; ++i) {
            listenerClz = internalListeners[i];

            try {
               Class clz = Class.forName(listenerClz);
               if (WebLogicServletContextListener.class.isAssignableFrom(clz) == preparePhase) {
                  this.registerEventListener(internalListeners[i]);
               }
            } catch (ClassNotFoundException var7) {
               if (WebAppModule.DEBUG.isDebugEnabled()) {
                  WebAppModule.DEBUG.debug("Ignoring CNFE in PWP for now");
               }

               var7.printStackTrace();
            }
         }

         if (!preparePhase && !registry.isProductionMode()) {
            this.registerEventListener("weblogic.servlet.internal.session.WLSessionAttributeChangedListener");
         }

         if (this.context.getHelper() != null) {
            Set listener = this.context.getHelper().getTagListeners(false);
            if (listener != null && !listener.isEmpty()) {
               Iterator i = listener.iterator();

               while(i.hasNext()) {
                  listenerClz = (String)i.next();
                  Class clz = this.context.getServletClassLoader().loadClass(listenerClz);
                  if (WebLogicServletContextListener.class.isAssignableFrom(clz) == preparePhase) {
                     this.tldCtxListenerClasses.add(clz);
                     this.registerEventListener(listenerClz);
                  }
               }
            }
         }

         if (this.context.getWebAppModule() != null) {
            WebAppBean bean = this.context.getWebAppModule().getWebAppBean();
            if (bean != null) {
               ListenerBean[] listener = bean.getListeners();
               if (listener != null) {
                  for(int i = 0; i < listener.length; ++i) {
                     listenerClz = listener[i].getListenerClass();
                     Class clz = this.context.getServletClassLoader().loadClass(listenerClz);
                     if (WebLogicServletContextListener.class.isAssignableFrom(clz) == preparePhase) {
                        this.registerEventListener(listener[i].getListenerClass());
                     }
                  }
               }
            }
         }

      } catch (ClassNotFoundException var8) {
         Loggable l = HTTPLogger.logCouldNotLoadListenerLoggable(listenerClz, var8);
         l.log();
         throw new DeploymentException(l.getMessage(), var8);
      }
   }

   void registerEventsListeners(List list) throws DeploymentException {
      if (list != null && !list.isEmpty()) {
         Iterator i = list.iterator();

         while(i.hasNext()) {
            String listenerClass = (String)i.next();
            this.registerEventListener(listenerClass);
         }

      }
   }

   void notifyContextPreparedEvent() throws DeploymentException {
      Iterator i = this.wlCtxListeners.iterator();

      while(i.hasNext()) {
         WebLogicServletContextListener l = (WebLogicServletContextListener)i.next();
         ServletContextEvent event = new ServletContextEvent(this.context);
         FireContextPreparedAction action = new FireContextPreparedAction(l, event);
         String className = l.getClass().getName();
         this.executeContextListener(action, className);
      }

   }

   void notifyContextCreatedEvent() throws DeploymentException {
      this.notifyContextCreatedEvent(this.ctxListeners);
      this.context.prepareNotifyDynamicContextListener();
      this.notifyContextCreatedEvent(this.tldCtxListeners);
      this.notifyContextCreatedEvent(this.dynamicCtxListeners);
   }

   private void notifyContextCreatedEvent(List listeners) throws DeploymentException {
      Iterator i = listeners.iterator();

      while(i.hasNext()) {
         ServletContextListener l = (ServletContextListener)i.next();
         ServletContextEvent event = new ServletContextEvent(this.context);
         FireContextListenerAction action = new FireContextListenerAction(true, l, event);
         String listenerClass = l.getClass().getName();
         this.executeContextListener(action, listenerClass);
      }

   }

   void notifyContextDestroyedEvent() {
      this.notifyContextDestroyedEvent(this.dynamicCtxListeners);
      this.notifyContextDestroyedEvent(this.tldCtxListeners);
      this.notifyContextDestroyedEvent(this.ctxListeners);
      this.destroyListeners(this.wlCtxListeners);
      this.destroyListeners(this.dynamicCtxListeners);
      this.destroyListeners(this.ctxListeners);
      this.destroyListeners(this.ctxAttrListeners);
      this.destroyListeners(this.sessListeners);
      this.destroyListeners(this.sessIdListeners);
      this.destroyListeners(this.sessAttrListeners);
      this.destroyListeners(this.requestListeners);
      this.destroyListeners(this.requestAttrListeners);
      this.destroyListeners(this.tldCtxListeners);
      this.tldCtxListenerClasses.clear();
   }

   void notifyContextDestroyedEvent(List listeners) {
      for(int i = listeners.size() - 1; i >= 0; --i) {
         ServletContextListener l = (ServletContextListener)listeners.get(i);
         ServletContextEvent event = new ServletContextEvent(this.context);
         FireContextListenerAction action = new FireContextListenerAction(false, l, event);
         String listenerClass = l.getClass().getName();

         try {
            this.executeContextListener(action, listenerClass);
         } catch (DeploymentException var8) {
         }
      }

   }

   private void executeContextListener(PrivilegedAction action, String listenerClass) throws DeploymentException {
      SubjectHandle sub = registry.getContainerSupportProvider().getDeploymentInitiator(this.context);
      if (sub == null) {
         sub = WebAppSecurity.getProvider().getAnonymousSubject();
      }

      Throwable e = (Throwable)sub.run(action);
      if (e != null) {
         HTTPLogger.logListenerFailed(listenerClass, e);
         registry.getManagementProvider().handleOutOfMemory(e);
         throw new DeploymentException(e);
      }
   }

   private void destroyListeners(List l) {
      if (this.context.getComponentCreator() != null) {
         Iterator it = l.iterator();

         while(it.hasNext()) {
            this.context.getComponentCreator().notifyPreDestroy(it.next());
         }
      }

      this.notifyEventListenersRemoved(l);
      l.clear();
   }

   void notifyContextAttributeChange(String name, Object newVal, Object prev) {
      prev = this.unwrapAttribute(prev);
      Iterator i = this.ctxAttrListeners.iterator();

      while(i.hasNext()) {
         ServletContextAttributeListener l = (ServletContextAttributeListener)i.next();
         if (prev == null) {
            if (newVal != null) {
               l.attributeAdded(new ServletContextAttributeEvent(this.context, name, newVal));
            }
         } else if (newVal == null) {
            l.attributeRemoved(new ServletContextAttributeEvent(this.context, name, prev));
         } else {
            if (prev.equals(newVal)) {
               return;
            }

            l.attributeReplaced(new ServletContextAttributeEvent(this.context, name, prev));
         }
      }

   }

   private Object unwrapAttribute(Object attr) {
      if (attr == null) {
         return null;
      } else {
         Object ret = null;

         try {
            if (attr instanceof AttributeWrapper) {
               ret = ((AttributeWrapper)attr).getObject(this.context);
            } else {
               ret = attr;
            }
         } catch (ClassNotFoundException var4) {
            HTTPLogger.logUnableToDeserializeAttribute(this.context.getLogContext(), var4);
         } catch (IOException var5) {
            HTTPLogger.logUnableToDeserializeAttribute(this.context.getLogContext(), var5);
         } catch (RuntimeException var6) {
            HTTPLogger.logUnableToDeserializeAttribute(this.context.getLogContext(), var6);
         }

         return ret;
      }
   }

   private void notifySessionCreatedEvent(HttpSession s) {
      Iterator i = this.sessListeners.iterator();

      while(i.hasNext()) {
         HttpSessionListener l = (HttpSessionListener)i.next();

         try {
            l.sessionCreated(new HttpSessionEvent(s));
         } catch (Throwable var5) {
            HTTPLogger.logListenerFailed(l.getClass().getName(), var5);
         }
      }

   }

   private void notifySessionDestroyedEvent(HttpSession s) {
      ListIterator i = this.sessListeners.listIterator(this.sessListeners.size());

      while(i.hasPrevious()) {
         HttpSessionListener l = (HttpSessionListener)i.previous();

         try {
            l.sessionDestroyed(new HttpSessionEvent(s));
         } catch (Throwable var5) {
            HTTPLogger.logListenerFailed(l.getClass().getName(), var5);
         }
      }

   }

   public void notifySessionLifetimeEvent(HttpSession s, boolean created) {
      if (created) {
         this.notifySessionCreatedEvent(s);
      } else {
         this.notifySessionDestroyedEvent(s);
      }

      if (this.isSessionSharing(s)) {
         WebAppServletContext[] allContexts = this.context.getServer().getServletContextManager().getAllContexts();
         if (allContexts == null || allContexts.length < 2) {
            return;
         }

         for(int j = 0; j < allContexts.length; ++j) {
            if (this.isSiblingWebModule(allContexts[j])) {
               allContexts[j].getEventsManager().notifySessionLifetimeEvent(new SharedSessionData(s, allContexts[j]), created);
            }
         }
      }

   }

   public void notifySessionIdChangedEvent(HttpSession s, String oldId) {
      Iterator var3 = this.sessIdListeners.iterator();

      while(var3.hasNext()) {
         HttpSessionIdListener l = (HttpSessionIdListener)var3.next();

         try {
            l.sessionIdChanged(new HttpSessionEvent(s), oldId);
         } catch (Throwable var6) {
            HTTPLogger.logListenerFailed(l.getClass().getName(), var6);
         }
      }

   }

   public void notifyLogoutEvent(HttpSession sess) {
      Iterator var2 = this.authenListeners.iterator();

      while(var2.hasNext()) {
         AuthenticationListener l = (AuthenticationListener)var2.next();

         try {
            l.onLogout(new HttpSessionEvent(sess));
         } catch (Throwable var5) {
            HTTPLogger.logListenerFailed(l.getClass().getName(), var5);
         }
      }

   }

   public void notifySessionAttributeChange(HttpSession sess, String name, Object prev, Object attr) {
      Iterator i = this.sessAttrListeners.iterator();

      while(i.hasNext()) {
         HttpSessionAttributeListener l = (HttpSessionAttributeListener)i.next();
         if (prev == null) {
            if (attr != null) {
               l.attributeAdded(new HttpSessionBindingEvent(sess, name, attr));
            }
         } else if (attr == null) {
            l.attributeRemoved(new HttpSessionBindingEvent(sess, name, prev));
         } else {
            l.attributeReplaced(new HttpSessionBindingEvent(sess, name, prev));
         }
      }

      if (this.isSessionSharing(sess)) {
         WebAppServletContext[] allContexts = this.context.getServer().getServletContextManager().getAllContexts();
         if (allContexts == null || allContexts.length < 2) {
            return;
         }

         for(int j = 0; j < allContexts.length; ++j) {
            if (this.isSiblingWebModule(allContexts[j])) {
               allContexts[j].getEventsManager().notifySessionAttributeChange(new SharedSessionData(sess, allContexts[j]), name, prev, attr);
            }
         }
      }

   }

   private boolean isSessionSharing(HttpSession session) {
      return this.context.getSessionContext().getConfigMgr().isSessionSharingEnabled() && !(session instanceof SharedSessionData);
   }

   private boolean isSiblingWebModule(WebAppServletContext aContext) {
      return aContext.getApplicationContext() == this.context.getApplicationContext() && aContext != this.context;
   }

   void notifyRequestLifetimeEvent(ServletRequest req, boolean initialized) {
      if (!this.requestListeners.isEmpty()) {
         Thread thread = Thread.currentThread();
         ClassLoader oldClassLoader = null;
         if (thread.getContextClassLoader() != this.context.getServletClassLoader()) {
            oldClassLoader = this.context.pushEnvironment(thread);
         }

         try {
            ServletRequestEvent sre = new ServletRequestEvent(this.context, req);
            ServletRequestListener listener;
            if (initialized) {
               Iterator var6 = this.requestListeners.iterator();

               while(var6.hasNext()) {
                  listener = (ServletRequestListener)var6.next();
                  listener.requestInitialized(sre);
               }
            } else {
               for(int i = this.requestListeners.size() - 1; i >= 0; --i) {
                  listener = null;

                  try {
                     listener = (ServletRequestListener)this.requestListeners.get(i);
                  } catch (IndexOutOfBoundsException var12) {
                     continue;
                  }

                  if (listener != null) {
                     listener.requestDestroyed(sre);
                  }
               }
            }
         } finally {
            if (oldClassLoader != null) {
               WebAppServletContext.popEnvironment(thread, oldClassLoader);
            }

         }

      }
   }

   public void notifyCDIRequestLifetimeEvent(ServletRequest req, boolean initialized) {
      if (!this.requestListeners.isEmpty()) {
         if (initialized) {
            Iterator var3 = this.requestListeners.iterator();

            while(var3.hasNext()) {
               ServletRequestListener listener = (ServletRequestListener)var3.next();
               if (listener.getClass().getName().equals("org.jboss.weld.module.web.servlet.WeldInitialListener")) {
                  listener.requestInitialized(new ServletRequestEvent(this.context, req));
                  break;
               }
            }
         } else {
            int count = this.requestListeners.size();

            for(int i = 0; i < count; ++i) {
               try {
                  ServletRequestListener listener = (ServletRequestListener)this.requestListeners.get(i);
                  if (listener != null && listener.getClass().getName().equals("org.jboss.weld.module.web.servlet.WeldInitialListener")) {
                     listener.requestDestroyed(new ServletRequestEvent(this.context, req));
                     break;
                  }
               } catch (IndexOutOfBoundsException var6) {
               }
            }
         }

      }
   }

   void notifyRequestAttributeEvent(ServletRequest req, String name, Object prev, Object attr) {
      prev = this.unwrapAttribute(prev);
      Iterator i = this.requestAttrListeners.iterator();

      while(i.hasNext()) {
         ServletRequestAttributeListener l = (ServletRequestAttributeListener)i.next();
         if (prev == null) {
            if (attr != null) {
               l.attributeAdded(new ServletRequestAttributeEvent(this.context, req, name, attr));
            }
         } else if (attr == null) {
            l.attributeRemoved(new ServletRequestAttributeEvent(this.context, req, name, prev));
         } else {
            l.attributeReplaced(new ServletRequestAttributeEvent(this.context, req, name, prev));
         }
      }

   }

   private synchronized void registerEventListener(String className) throws DeploymentException {
      if (className == null) {
         throw new DeploymentException("listener-class is null");
      } else {
         weblogic.logging.Loggable l;
         try {
            Object l = this.context.createInstance(className);
            this.addEventListener((EventListener)l);
         } catch (ClassNotFoundException var4) {
            l = HTTPLogger.logCouldNotLoadListenerLoggable(className, var4);
            l.log();
            throw new DeploymentException(l.getMessage(), var4);
         } catch (NoClassDefFoundError var5) {
            l = HTTPLogger.logCouldNotLoadListenerLoggable(className, var5);
            l.log();
            throw new DeploymentException(l.getMessage(), var5);
         } catch (InstantiationException var6) {
            l = HTTPLogger.logCouldNotLoadListenerLoggable(className, var6);
            l.log();
            throw new DeploymentException(l.getMessage(), var6);
         } catch (IllegalAccessException var7) {
            l = HTTPLogger.logCouldNotLoadListenerLoggable(className, var7);
            l.log();
            throw new DeploymentException(l.getMessage(), var7);
         } catch (ClassCastException var8) {
            l = HTTPLogger.logCouldNotLoadListenerLoggable(className, var8);
            l.log();
            throw new DeploymentException(l.getMessage(), var8);
         } catch (IllegalArgumentException var9) {
            l = HTTPLogger.logCouldNotLoadListenerLoggable(className, var9);
            l.log();
            throw new DeploymentException(l.getMessage(), var9);
         }
      }
   }

   void registerDynamicContextListener(ServletContextListener listener) {
      this.dynamicCtxListeners.add(listener);
      this.notifyEventListenerAdded(listener);
   }

   synchronized void addEventListener(EventListener listener) {
      boolean isListener = false;
      if (listener instanceof WebLogicServletContextListener) {
         this.wlCtxListeners.add(listener);
         isListener = true;
      }

      if (listener instanceof AuthenticationListener) {
         this.authenListeners.add((AuthenticationListener)listener);
         isListener = true;
      }

      if (listener instanceof ServletContextListener) {
         if (this.tldCtxListenerClasses.contains(listener.getClass())) {
            this.addListenterToList(this.tldCtxListeners, (ServletContextListener)listener);
         } else {
            this.addListenterToList(this.ctxListeners, listener);
         }

         isListener = true;
      }

      if (listener instanceof ServletContextAttributeListener) {
         this.addListenterToList(this.ctxAttrListeners, listener);
         isListener = true;
      }

      if (listener instanceof HttpSessionListener) {
         this.addListenterToList(this.sessListeners, listener);
         isListener = true;
      }

      if (listener instanceof HttpSessionIdListener) {
         this.addListenterToList(this.sessIdListeners, (HttpSessionIdListener)listener);
         isListener = true;
      }

      if (listener instanceof HttpSessionAttributeListener) {
         this.addListenterToList(this.sessAttrListeners, listener);
         isListener = true;
      }

      if (listener instanceof ServletRequestListener) {
         this.addListenterToList(this.requestListeners, (ServletRequestListener)listener);
         this.hasRequestListeners = true;
         isListener = true;
      }

      if (listener instanceof ServletRequestAttributeListener) {
         this.addListenterToList(this.requestAttrListeners, listener);
         this.hasRequestListeners = true;
         isListener = true;
      }

      if (isListener) {
         this.notifyEventListenerAdded(listener);
      } else {
         String listenerName = listener.getClass().getName();
         Loggable logger = HTTPLogger.logNotAListenerLoggable(listenerName);
         logger.log();
         throw new IllegalArgumentException(logger.getMessage());
      }
   }

   private void addListenterToList(List listenerList, EventListener listener) {
      if (listener.getClass().getName().equalsIgnoreCase("org.jboss.weld.module.web.servlet.WeldInitialListener")) {
         listenerList.add(0, listener);
      } else if (listener.getClass().getName().equalsIgnoreCase("org.jboss.weld.module.web.servlet.WeldTerminalListener")) {
         listenerList.add(listener);
      } else {
         int size = listenerList.size();
         if (size > 0 && listenerList.get(size - 1).getClass().getName().equalsIgnoreCase("org.jboss.weld.module.web.servlet.WeldTerminalListener")) {
            listenerList.add(size - 1, listener);
         } else {
            listenerList.add(listener);
         }
      }

   }

   EventListener createListener(Class clazz) throws ReflectiveOperationException {
      weblogic.logging.Loggable l;
      if (!ServletContextListener.class.isAssignableFrom(clazz) && !ServletContextAttributeListener.class.isAssignableFrom(clazz) && !ServletRequestListener.class.isAssignableFrom(clazz) && !ServletRequestAttributeListener.class.isAssignableFrom(clazz) && !HttpSessionListener.class.isAssignableFrom(clazz) && !HttpSessionIdListener.class.isAssignableFrom(clazz) && !HttpSessionAttributeListener.class.isAssignableFrom(clazz)) {
         String listenerName = clazz.getName();
         l = HTTPLogger.logNotAListenerLoggable(listenerName);
         throw new IllegalArgumentException(l.getMessage());
      } else {
         try {
            return (EventListener)this.context.createInstance(clazz);
         } catch (ReflectiveOperationException var4) {
            l = HTTPLogger.logCouldNotLoadListenerLoggable(clazz.getName(), var4);
            l.log();
            throw var4;
         }
      }
   }

   EventListener createListener(String className) {
      try {
         Class clazz = this.context.getClassLoader().loadClass(className);
         return this.createListener(clazz);
      } catch (ReflectiveOperationException var3) {
         throw new IllegalArgumentException(var3);
      }
   }

   public boolean isListenerRegistered(String listenerClassName) {
      Iterator iter = this.ctxListeners.iterator();
      if (this.contains(iter, listenerClassName)) {
         return true;
      } else {
         iter = this.wlCtxListeners.iterator();
         if (this.contains(iter, listenerClassName)) {
            return true;
         } else {
            iter = this.ctxAttrListeners.iterator();
            if (this.contains(iter, listenerClassName)) {
               return true;
            } else {
               iter = this.sessListeners.iterator();
               if (this.contains(iter, listenerClassName)) {
                  return true;
               } else {
                  iter = this.sessIdListeners.iterator();
                  if (this.contains(iter, listenerClassName)) {
                     return true;
                  } else {
                     iter = this.sessAttrListeners.iterator();
                     if (this.contains(iter, listenerClassName)) {
                        return true;
                     } else {
                        iter = this.requestListeners.iterator();
                        if (this.contains(iter, listenerClassName)) {
                           return true;
                        } else {
                           iter = this.requestAttrListeners.iterator();
                           if (this.contains(iter, listenerClassName)) {
                              return true;
                           } else {
                              iter = this.dynamicCtxListeners.iterator();
                              return this.contains(iter, listenerClassName);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private boolean contains(Iterator iter, String listenerClassName) {
      while(true) {
         if (iter.hasNext()) {
            if (!listenerClassName.equals(iter.next().getClass().getName())) {
               continue;
            }

            return true;
         }

         return false;
      }
   }

   public synchronized void setRegistrationListener(RegistrationListener listener) {
      this.regListener = listener;
      this.notifyEventListenersAdded(this.wlCtxListeners);
      this.notifyEventListenersAdded(this.ctxListeners);
      this.notifyEventListenersAdded(this.dynamicCtxListeners);
      this.notifyEventListenersAdded(this.ctxAttrListeners);
      this.notifyEventListenersAdded(this.sessListeners);
      this.notifyEventListenersAdded(this.sessIdListeners);
      this.notifyEventListenersAdded(this.sessAttrListeners);
      this.notifyEventListenersAdded(this.requestListeners);
      this.notifyEventListenersAdded(this.requestAttrListeners);
   }

   private void notifyEventListenersAdded(List listeners) {
      if (this.regListener != null) {
         Iterator iter = listeners.iterator();

         while(iter.hasNext()) {
            this.notifyEventListenerAdded(iter.next());
         }
      }

   }

   private void notifyEventListenerAdded(Object l) {
      RegistrationListener listener = this.regListener;
      if (listener != null) {
         listener.eventListenerAdded(l);
      }

   }

   private void notifyEventListenersRemoved(List listeners) {
      if (this.regListener != null) {
         Iterator iter = listeners.iterator();

         while(iter.hasNext()) {
            this.notifyEventListenerRemoved(iter.next());
         }
      }

   }

   private void notifyEventListenerRemoved(Object l) {
      RegistrationListener listener = this.regListener;
      if (listener != null) {
         listener.eventListenerRemoved(l);
      }

   }

   static {
      internalListeners = registry.getContainerSupportProvider().getInternalWebAppListeners();
   }

   public interface RegistrationListener {
      void eventListenerAdded(Object var1);

      void eventListenerRemoved(Object var1);
   }

   private static final class FireContextPreparedAction implements PrivilegedAction {
      private final WebLogicServletContextListener listener;
      private final ServletContextEvent event;

      FireContextPreparedAction(WebLogicServletContextListener l, ServletContextEvent e) {
         this.listener = l;
         this.event = e;
      }

      public Object run() {
         try {
            this.listener.contextPrepared(this.event);
            return null;
         } catch (Throwable var2) {
            return var2;
         }
      }
   }

   private static final class FireContextListenerAction implements PrivilegedAction {
      private final ServletContextListener listener;
      private final ServletContextEvent event;
      private final boolean init;

      FireContextListenerAction(boolean b, ServletContextListener l, ServletContextEvent e) {
         this.listener = l;
         this.event = e;
         this.init = b;
      }

      public Object run() {
         try {
            if (this.init) {
               this.listener.contextInitialized(this.event);
            } else {
               this.listener.contextDestroyed(this.event);
            }
         } catch (Throwable var2) {
            if (this.init) {
               return var2;
            }

            HTTPLogger.logListenerFailed(this.listener.getClass().getName(), var2);
         }

         return null;
      }
   }
}
