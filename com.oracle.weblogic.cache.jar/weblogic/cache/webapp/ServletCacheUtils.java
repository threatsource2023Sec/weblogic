package weblogic.cache.webapp;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.servlet.ServletContext;
import weblogic.cache.CacheException;
import weblogic.cache.CacheValue;
import weblogic.cache.KeyEnumerator;

public class ServletCacheUtils {
   public static final String CACHING = "weblogic.cache.tag.CacheTag.caching";

   public static void removeCacheListener(ServletContext sc, CacheListener listener) {
      List listeners = (List)sc.getAttribute("weblogic.cache.CacheListener");
      if (listeners != null) {
         listeners.remove(listener);
      }
   }

   public static void addCacheListener(ServletContext sc, CacheListener listener) {
      List listeners = (List)sc.getAttribute("weblogic.cache.CacheListener");
      if (listeners == null) {
         listeners = new ArrayList();
         sc.setAttribute("weblogic.cache.CacheListener", listeners);
      }

      if (!((List)listeners).contains(listener)) {
         ((List)listeners).add(listener);
      }

   }

   public static int getTimeout(String timeoutString) throws CacheException {
      if (timeoutString == null) {
         return -1;
      } else {
         int length = timeoutString.length();

         int i;
         for(i = 0; i < length && Character.isDigit(timeoutString.charAt(i)); ++i) {
         }

         if (i == 0) {
            return -1;
         } else {
            int timeout = Integer.parseInt(timeoutString.substring(0, i));
            String unit = timeoutString.substring(i);
            if (!unit.equals("s") && !unit.equals("")) {
               if (unit.equals("ms")) {
                  return timeout;
               } else if (unit.equals("h")) {
                  return timeout * 1000 * 3600;
               } else if (unit.equals("m")) {
                  return timeout * 1000 * 60;
               } else if (unit.equals("d")) {
                  return timeout * 1000 * 3600 * 24;
               } else {
                  throw new CacheException("Invalid time unit: " + unit + " in " + timeoutString);
               }
            } else {
               return 1000 * timeout;
            }
         }
      }
   }

   public static void saveVars(CacheSystem cs, CacheValue cache, String vars, String defaultScope) throws CacheException {
      if (vars != null) {
         Hashtable data = new Hashtable();
         KeyEnumerator keys = new KeyEnumerator(vars);

         while(keys.hasMoreKeys()) {
            String key = keys.getNextKey();
            String keyScope = keys.getKeyScope();
            Object value = cs.getValueFromScope(keyScope, key);
            if (value != null) {
               cs.setValueInScope(defaultScope, key, value);
               data.put(key, value);
            } else {
               cs.removeValueInScope(defaultScope, key);
            }
         }

         cache.setVariables(data);
      }
   }

   public static void restoreVars(CacheSystem cs, CacheValue cache, String vars, String defaultScope) throws CacheException {
      if (vars != null) {
         Hashtable data = cache.getVariables();
         if (data == null) {
            throw new CacheException("Variable not present, probably an inconsistent cache state");
         } else {
            KeyEnumerator keys = new KeyEnumerator(vars);

            while(keys.hasMoreKeys()) {
               String key = keys.getNextKey();
               String keyScope = keys.getKeyScope();
               if (keyScope.equals("any")) {
                  keyScope = defaultScope;
               }

               Object value = data.get(key);
               if (value == null) {
                  cs.removeValueInScope(keyScope, key);
                  cs.removeValueInScope(defaultScope, key);
               } else {
                  cs.setValueInScope(keyScope, key, value);
                  cs.setValueInScope(defaultScope, key, value);
               }
            }

         }
      }
   }
}
