package weblogic.utils.collections;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class PartitionMatchMap {
   public static final String WILDCARD = "_*_";
   private HashMap matchMap;
   private boolean ignoreCase;
   private final transient ReentrantLock lock;

   public PartitionMatchMap() {
      this(false);
   }

   public PartitionMatchMap(boolean ignoreURLCase) {
      this.lock = new ReentrantLock();
      this.matchMap = new HashMap();
      this.ignoreCase = ignoreURLCase;
   }

   public Object put(String hostname, int port, String uriPath, Object value) {
      String normalizedHostname = normalizeHostname(hostname);
      int normalizedPort = normalizePort(port);
      String normalizedURIPath = this.normalizeURI(uriPath);
      if (normalizedHostname == null && normalizedPort == -1 && "/".equals(normalizedURIPath)) {
         return null;
      } else {
         PartitionMatchMapKey matchMapKey = new PartitionMatchMapKey(normalizedHostname, normalizedPort, normalizedURIPath);
         ReentrantLock lock = this.lock;
         lock.lock();

         Object var12;
         try {
            Object entry = this.matchMap.get(matchMapKey);
            if (entry != null) {
               var12 = entry;
               return var12;
            }

            HashMap snapshot = (HashMap)this.matchMap.clone();
            entry = snapshot.put(matchMapKey, value);
            this.matchMap = snapshot;
            var12 = entry;
         } finally {
            lock.unlock();
         }

         return var12;
      }
   }

   public Object match(String hostname, int port, String uri) {
      String normalizedHostname = normalizeHostname(hostname);
      int normalizedPort = normalizePort(port);
      String normalizedURIPath = this.normalizeURI(uri);
      new HashMap();
      HashMap exactSubMap = new HashMap();
      HashMap wildcardSubMap = new HashMap();
      boolean exactPortMatch = false;
      HashMap filteredSubMap;
      Iterator var11;
      PartitionMatchMapKey key;
      if (normalizedPort != -1) {
         var11 = this.matchMap.keySet().iterator();

         while(var11.hasNext()) {
            key = (PartitionMatchMapKey)var11.next();
            if (normalizedPort == key.getPort()) {
               exactSubMap.put(key, this.matchMap.get(key));
               exactPortMatch = true;
            }

            if (key.getPort() == -1) {
               wildcardSubMap.put(key, this.matchMap.get(key));
            }
         }

         if (exactPortMatch) {
            if (exactSubMap.size() == 1) {
               return ((Map.Entry)exactSubMap.entrySet().iterator().next()).getValue();
            }

            filteredSubMap = (HashMap)exactSubMap.clone();
         } else {
            filteredSubMap = (HashMap)wildcardSubMap.clone();
         }
      } else {
         var11 = this.matchMap.keySet().iterator();

         while(var11.hasNext()) {
            key = (PartitionMatchMapKey)var11.next();
            if (key.getPort() == -1) {
               wildcardSubMap.put(key, this.matchMap.get(key));
            }
         }

         filteredSubMap = (HashMap)wildcardSubMap.clone();
      }

      exactSubMap.clear();
      wildcardSubMap.clear();
      boolean exactHostnameMatch = false;
      PartitionMatchMapKey key;
      Iterator var20;
      if (normalizedHostname != null) {
         var20 = filteredSubMap.keySet().iterator();

         while(var20.hasNext()) {
            key = (PartitionMatchMapKey)var20.next();
            if (normalizedHostname.equals(key.getHostname())) {
               exactSubMap.put(key, filteredSubMap.get(key));
               exactHostnameMatch = true;
            }

            if (key.getHostname() == null) {
               wildcardSubMap.put(key, filteredSubMap.get(key));
            }

            if ("_*_".equals(key.getHostname())) {
               exactSubMap.put(key, filteredSubMap.get(key));
               wildcardSubMap.put(key, filteredSubMap.get(key));
            }
         }

         if (exactHostnameMatch) {
            filteredSubMap = (HashMap)exactSubMap.clone();
         } else {
            filteredSubMap = (HashMap)wildcardSubMap.clone();
         }
      } else {
         var20 = filteredSubMap.keySet().iterator();

         label122:
         while(true) {
            do {
               if (!var20.hasNext()) {
                  filteredSubMap = (HashMap)wildcardSubMap.clone();
                  break label122;
               }

               key = (PartitionMatchMapKey)var20.next();
            } while(key.getHostname() != null && !"_*_".equals(key.getHostname()));

            wildcardSubMap.put(key, filteredSubMap.get(key));
         }
      }

      exactSubMap.clear();
      wildcardSubMap.clear();
      String matchingPath = normalizedURIPath;

      for(boolean matchFound = false; matchingPath != null; matchingPath = shortenUrl(matchingPath)) {
         Iterator var14 = filteredSubMap.keySet().iterator();

         while(var14.hasNext()) {
            PartitionMatchMapKey key = (PartitionMatchMapKey)var14.next();
            if (matchingPath.equals(key.getPath())) {
               exactSubMap.put(key, filteredSubMap.get(key));
               matchFound = true;
            }
         }

         if (matchFound) {
            break;
         }
      }

      int matchNumber = exactSubMap.size();
      if (matchNumber == 0) {
         return null;
      } else if (matchNumber == 1) {
         return ((Map.Entry)exactSubMap.entrySet().iterator().next()).getValue();
      } else {
         Object matchValue = ((Map.Entry)exactSubMap.entrySet().iterator().next()).getValue();
         boolean allValuesEqual = true;
         Iterator var17 = exactSubMap.values().iterator();

         while(var17.hasNext()) {
            Object value = var17.next();
            if (!value.equals(matchValue)) {
               allValuesEqual = false;
               break;
            }
         }

         return allValuesEqual ? matchValue : null;
      }
   }

   public Object remove(String hostname, int port, String uri) {
      String normalizedHostname = normalizeHostname(hostname);
      int normalizedPort = normalizePort(port);
      String normalizedURIPath = this.normalizeURI(uri);
      PartitionMatchMapKey matchMapKey = new PartitionMatchMapKey(normalizedHostname, normalizedPort, normalizedURIPath);
      ReentrantLock lock = this.lock;
      lock.lock();

      Object var11;
      try {
         HashMap snapshot = (HashMap)this.matchMap.clone();
         Object entry = snapshot.remove(matchMapKey);
         this.matchMap = snapshot;
         var11 = entry;
      } finally {
         lock.unlock();
      }

      return var11;
   }

   public boolean containsKey(String hostname, int port, String uri) {
      String normalizedHostname = normalizeHostname(hostname);
      int normalizedPort = normalizePort(port);
      String normalizedURIPath = this.normalizeURI(uri);
      PartitionMatchMapKey matchMapKey = new PartitionMatchMapKey(normalizedHostname, normalizedPort, normalizedURIPath);
      return this.matchMap.containsKey(matchMapKey);
   }

   private String normalizeURI(String url) {
      if (url == null) {
         return "/";
      } else {
         url = url.trim();
         if (!url.isEmpty() && !"/".equals(url)) {
            String result = url;
            if (url.charAt(0) != '/') {
               result = "/" + url;
            }

            if (result.charAt(result.length() - 1) == '/') {
               result = result.substring(0, result.length() - 1);
            }

            return this.ignoreCase ? result.toUpperCase() : result;
         } else {
            return "/";
         }
      }
   }

   private static String normalizeHostname(String hostname) {
      if (hostname == null) {
         return null;
      } else {
         return hostname.isEmpty() ? null : hostname.toUpperCase();
      }
   }

   private static int normalizePort(int port) {
      return port != 0 && port != -1 ? port : -1;
   }

   private static String shortenUrl(String url) {
      int i = url.lastIndexOf(47);
      int len = url.length();
      if (i > 0 && len > 1) {
         return url.substring(0, i);
      } else {
         return i == 0 && len > 1 ? "/" : null;
      }
   }

   private class PartitionMatchMapKey {
      private final String hostname;
      private final int port;
      private final String path;

      public PartitionMatchMapKey(String hostname, int port, String path) {
         this.hostname = hostname;
         this.port = port;
         this.path = path;
      }

      String getHostname() {
         return this.hostname;
      }

      int getPort() {
         return this.port;
      }

      String getPath() {
         return this.path;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o == null) {
            return false;
         } else if (this.getClass() != o.getClass()) {
            return false;
         } else {
            PartitionMatchMapKey theOther = (PartitionMatchMapKey)o;
            return this.fieldEqual(this.hostname, theOther.hostname) && this.port == theOther.port && this.fieldEqual(this.path, theOther.path);
         }
      }

      private boolean fieldEqual(String a, String b) {
         if (a == null && b == null) {
            return true;
         } else {
            return a != null ? a.equals(b) : false;
         }
      }

      public int hashCode() {
         String stringSum = this.hostname + this.path;
         return stringSum.length() + this.port;
      }
   }
}
