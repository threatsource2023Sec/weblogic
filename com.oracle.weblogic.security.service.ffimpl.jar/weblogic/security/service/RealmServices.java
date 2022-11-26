package weblogic.security.service;

import com.bea.security.css.CSS;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class RealmServices {
   private CSS css;
   private String realmName;
   private boolean isDefault = false;
   private HashMap services = new HashMap();
   private HashMap proxies = new HashMap();
   private HashMap cssProxies = new HashMap();
   private Object proxyLock = new Object();
   private Map cleanupHandlers = Collections.synchronizedMap(new IdentityHashMap());
   private volatile boolean isShutdown = false;

   RealmServices(String realmName, CSS css) {
      this.realmName = realmName;
      this.css = css;
   }

   public String getRealmName() {
      return this.realmName;
   }

   public void setDefault() {
      this.isDefault = true;
   }

   public boolean isDefault() {
      return this.isDefault;
   }

   public HashMap getServices() {
      return this.services;
   }

   public void setProxies(HashMap proxies) {
      this.proxies = proxies;
   }

   public HashMap getProxies() {
      return this.proxies;
   }

   public void setCSSProxies(HashMap cssProxies) {
      this.cssProxies = cssProxies;
   }

   public HashMap getCSSProxies() {
      return this.cssProxies;
   }

   public CSS getCSS() {
      return this.css;
   }

   public void setProxyLock(Object lockObject) {
      this.proxyLock = lockObject;
   }

   public Object getProxyLock() {
      return this.proxyLock;
   }

   public boolean isShutdown() {
      return this.isShutdown;
   }

   public void shutdown() {
      this.isShutdown = true;
      this.css = null;
      this.services = null;
      this.cleanupHandlers = null;
   }

   public void registerCleanupHandler(RealmServicesCleanup handler) {
      if (handler != null && !this.isShutdown) {
         this.cleanupHandlers.put(handler, handler);
      }
   }

   public void removeCleanupHandler(RealmServicesCleanup handler) {
      if (handler != null && !this.isShutdown) {
         this.cleanupHandlers.remove(handler);
      }
   }

   public void cleanup() {
      Set handlers = this.cleanupHandlers.keySet();
      synchronized(this.cleanupHandlers) {
         if (!handlers.isEmpty()) {
            Iterator var3 = handlers.iterator();

            while(var3.hasNext()) {
               RealmServicesCleanup handler = (RealmServicesCleanup)var3.next();

               try {
                  handler.cleanup();
               } catch (Exception var7) {
               }
            }

            handlers.clear();
         }

      }
   }
}
