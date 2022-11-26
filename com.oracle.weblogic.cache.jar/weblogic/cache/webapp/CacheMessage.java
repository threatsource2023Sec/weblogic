package weblogic.cache.webapp;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import weblogic.cache.CacheException;
import weblogic.cache.CacheValue;
import weblogic.cache.RefWrapper;
import weblogic.cache.utils.BubblingCache;
import weblogic.cluster.GroupMessage;
import weblogic.rmi.spi.HostID;
import weblogic.servlet.internal.HttpServer;
import weblogic.servlet.spi.HttpServerManager;
import weblogic.servlet.spi.WebServerRegistry;

class CacheMessage implements GroupMessage, Serializable {
   private static final long serialVersionUID = -8369932656337893639L;
   private String httpServerName;
   private String contextName;
   private CacheListener.CacheEvent ce;

   public CacheMessage(String httpServerName, String contextName, CacheListener.CacheEvent ce) {
      this.httpServerName = httpServerName;
      this.contextName = contextName;
      this.ce = ce;
   }

   public void execute(HostID sender) {
      HttpServerManager httpSrvManager = WebServerRegistry.getInstance().getHttpServerManager();
      HttpServer httper = httpSrvManager.getHttpServer(this.httpServerName);
      ServletContext sc = httper.getServletContextManager().getContextForContextPath(this.contextName);

      try {
         CacheSystem cs = new CacheSystem();
         ServletContextAttributeScope scas = new ServletContextAttributeScope();
         scas.setContext(sc);
         cs.registerScope("cluster", scas);
         CacheValue cv = this.ce.getValue();
         String scope = this.ce.getScope();
         String name = this.ce.getName();
         KeySet keySet = this.ce.getKeySet();
         String key = keySet == null ? null : keySet.getKey();
         if (key == null) {
            if (cv == null) {
               cs.removeValueInScope(scope, name);
            } else {
               cs.waitOnLock(scope, name);
               cs.setValueInScope(scope, name, new RefWrapper(cv));
               cs.releaseLock(scope, name);
            }
         } else {
            cs.waitOnLock(scope, name);

            Object map;
            try {
               map = (Map)cs.getValueFromScope(scope, name);
            } catch (ClassCastException var14) {
               map = null;
            }

            if (map == null) {
               int size = this.ce.getSize();
               if (size == -1) {
                  map = Collections.synchronizedMap(new HashMap());
               } else {
                  map = new BubblingCache(size);
               }

               cs.setValueInScope(scope, name, map);
            }

            cs.releaseLock(scope, name);
            if (cv == null) {
               ((Map)map).remove(key);
            } else {
               String lock = name + '\u0000' + key;
               cs.waitOnLock(scope, lock);
               ((Map)map).put(key, new RefWrapper(cv));
               cs.releaseLock(scope, lock);
            }
         }
      } catch (CacheException var15) {
         if (sc != null) {
            sc.log("Could not set cache value", var15);
         }
      }

   }

   public boolean runInSameThread() {
      return false;
   }
}
