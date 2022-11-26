package org.glassfish.grizzly.http.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import org.glassfish.grizzly.http.server.jmxbase.JmxEventListener;

public class ServerConfiguration extends ServerFilterConfiguration {
   private static final AtomicInteger INSTANCE_COUNT = new AtomicInteger(-1);
   private static final HttpHandlerRegistration[] ROOT_MAPPING;
   final Map handlers = new ConcurrentHashMap();
   private final Map unmodifiableHandlers;
   final List orderedHandlers;
   private final Set jmxEventListeners;
   private final HttpServerMonitoringConfig monitoringConfig;
   private String name;
   final HttpServer instance;
   private boolean jmxEnabled;
   private boolean allowPayloadForUndefinedHttpMethods;
   private long maxPayloadRemainderToSkip;
   final Object handlersSync;

   ServerConfiguration(HttpServer instance) {
      this.unmodifiableHandlers = Collections.unmodifiableMap(this.handlers);
      this.orderedHandlers = new LinkedList();
      this.jmxEventListeners = new CopyOnWriteArraySet();
      this.monitoringConfig = new HttpServerMonitoringConfig();
      this.maxPayloadRemainderToSkip = -1L;
      this.handlersSync = new Object();
      this.instance = instance;
   }

   public void addHttpHandler(HttpHandler httpHandler) {
      this.addHttpHandler(httpHandler, ROOT_MAPPING);
   }

   public void addHttpHandler(HttpHandler httpHandler, String... mappings) {
      if (mappings != null && mappings.length != 0) {
         HttpHandlerRegistration[] registrations = new HttpHandlerRegistration[mappings.length];

         for(int i = 0; i < mappings.length; ++i) {
            registrations[i] = HttpHandlerRegistration.fromString(mappings[i]);
         }

         this.addHttpHandler(httpHandler, registrations);
      } else {
         this.addHttpHandler(httpHandler, ROOT_MAPPING);
      }
   }

   public void addHttpHandler(HttpHandler httpHandler, HttpHandlerRegistration... mapping) {
      synchronized(this.handlersSync) {
         if (mapping == null || mapping.length == 0) {
            mapping = ROOT_MAPPING;
         }

         if (this.handlers.put(httpHandler, mapping) != null) {
            this.orderedHandlers.remove(httpHandler);
         }

         this.orderedHandlers.add(httpHandler);
         this.instance.onAddHttpHandler(httpHandler, mapping);
      }
   }

   public synchronized boolean removeHttpHandler(HttpHandler httpHandler) {
      synchronized(this.handlersSync) {
         boolean result = this.handlers.remove(httpHandler) != null;
         if (result) {
            this.orderedHandlers.remove(httpHandler);
            this.instance.onRemoveHttpHandler(httpHandler);
         }

         return result;
      }
   }

   /** @deprecated */
   public Map getHttpHandlers() {
      Map map = new HashMap(this.unmodifiableHandlers.size());
      Iterator var2 = this.unmodifiableHandlers.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         HttpHandlerRegistration[] regs = (HttpHandlerRegistration[])entry.getValue();
         String[] strRegs = new String[regs.length];

         for(int i = 0; i < regs.length; ++i) {
            String contextPath = regs[i].getContextPath();
            String urlPattern = regs[i].getUrlPattern();
            if (contextPath == null) {
               strRegs[i] = urlPattern;
            } else if (urlPattern == null) {
               strRegs[i] = contextPath;
            } else if (contextPath.endsWith("/") && urlPattern.startsWith("/")) {
               strRegs[i] = contextPath.substring(0, contextPath.length() - 1) + urlPattern;
            } else {
               strRegs[i] = contextPath + urlPattern;
            }
         }

         map.put(entry.getKey(), strRegs);
      }

      return Collections.unmodifiableMap(map);
   }

   public Map getHttpHandlersWithMapping() {
      return this.unmodifiableHandlers;
   }

   public HttpServerMonitoringConfig getMonitoringConfig() {
      return this.monitoringConfig;
   }

   public String getName() {
      if (this.name == null) {
         if (!this.instance.isStarted()) {
            return null;
         }

         int count = INSTANCE_COUNT.incrementAndGet();
         this.name = count == 0 ? "HttpServer" : "HttpServer-" + count;
      }

      return this.name;
   }

   public void setName(String name) {
      if (!this.instance.isStarted()) {
         this.name = name;
      }

   }

   public boolean isJmxEnabled() {
      return this.jmxEnabled;
   }

   public void setJmxEnabled(boolean jmxEnabled) {
      this.jmxEnabled = jmxEnabled;
      if (this.instance.isStarted()) {
         Iterator var2;
         JmxEventListener l;
         if (jmxEnabled) {
            this.instance.enableJMX();
            if (!this.jmxEventListeners.isEmpty()) {
               var2 = this.jmxEventListeners.iterator();

               while(var2.hasNext()) {
                  l = (JmxEventListener)var2.next();
                  l.jmxEnabled();
               }
            }
         } else {
            if (!this.jmxEventListeners.isEmpty()) {
               var2 = this.jmxEventListeners.iterator();

               while(var2.hasNext()) {
                  l = (JmxEventListener)var2.next();
                  l.jmxDisabled();
               }
            }

            this.instance.disableJMX();
         }
      }

   }

   public void addJmxEventListener(JmxEventListener listener) {
      if (listener != null) {
         this.jmxEventListeners.add(listener);
      }

   }

   public void removeJmxEventListener(JmxEventListener listener) {
      if (listener != null) {
         this.jmxEventListeners.remove(listener);
      }

   }

   public Set getJmxEventListeners() {
      return this.jmxEventListeners;
   }

   public boolean isAllowPayloadForUndefinedHttpMethods() {
      return this.allowPayloadForUndefinedHttpMethods;
   }

   public void setAllowPayloadForUndefinedHttpMethods(boolean allowPayloadForUndefinedHttpMethods) {
      this.allowPayloadForUndefinedHttpMethods = allowPayloadForUndefinedHttpMethods;
   }

   public long getMaxPayloadRemainderToSkip() {
      return this.maxPayloadRemainderToSkip;
   }

   public void setMaxPayloadRemainderToSkip(long maxPayloadRemainderToSkip) {
      this.maxPayloadRemainderToSkip = maxPayloadRemainderToSkip;
   }

   static {
      ROOT_MAPPING = new HttpHandlerRegistration[]{HttpHandlerRegistration.ROOT};
   }
}
