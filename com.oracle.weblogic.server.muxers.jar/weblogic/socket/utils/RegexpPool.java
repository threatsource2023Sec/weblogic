package weblogic.socket.utils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class RegexpPool {
   private static final String STAR = "*";
   private boolean matchAll = false;
   private volatile List prefixes = null;
   private volatile List suffixes = null;
   private volatile Map strings = null;

   public boolean add(String expr) {
      if (expr == null) {
         throw new NullPointerException("null expression string");
      } else {
         boolean alreadyExists = false;
         if (expr.equals("*")) {
            alreadyExists = this.matchAll;
            this.matchAll = true;
         } else {
            String prefix;
            if (expr.startsWith("*")) {
               prefix = expr.substring(1);
               if (this.suffixes == null) {
                  synchronized(this) {
                     if (this.suffixes == null) {
                        this.suffixes = new CopyOnWriteArrayList();
                     }
                  }
               } else {
                  alreadyExists = this.suffixes.contains(prefix);
               }

               if (!alreadyExists) {
                  this.suffixes.add(prefix);
               }
            } else if (expr.endsWith("*")) {
               prefix = expr.substring(0, expr.length() - 1);
               if (this.prefixes == null) {
                  synchronized(this) {
                     if (this.prefixes == null) {
                        this.prefixes = new CopyOnWriteArrayList();
                     }
                  }
               } else {
                  alreadyExists = this.prefixes.contains(prefix);
               }

               if (!alreadyExists) {
                  this.prefixes.add(prefix);
               }
            } else {
               if (this.strings == null) {
                  synchronized(this) {
                     if (this.strings == null) {
                        this.strings = new ConcurrentHashMap();
                     }
                  }
               }

               alreadyExists = this.strings.put(expr, expr) != null;
            }
         }

         return !alreadyExists;
      }
   }

   public boolean remove(String expr) {
      if (expr == null) {
         throw new NullPointerException("null expression string");
      } else if (expr.equals("*")) {
         boolean existed = this.matchAll;
         this.matchAll = false;
         return existed;
      } else if (expr.startsWith("*")) {
         return this.suffixes != null && this.suffixes.remove(expr.substring(1));
      } else if (!expr.endsWith("*")) {
         return this.strings.remove(expr) != null;
      } else {
         return this.prefixes != null && this.prefixes.remove(expr.substring(0, expr.length() - 1));
      }
   }

   public boolean match(String string) {
      if (this.matchAll) {
         return true;
      } else if (this.strings != null && this.strings.containsKey(string)) {
         return true;
      } else {
         Iterator var2;
         String prefix;
         if (this.suffixes != null) {
            var2 = this.suffixes.iterator();

            while(var2.hasNext()) {
               prefix = (String)var2.next();
               if (string.endsWith(prefix)) {
                  return true;
               }
            }
         }

         if (this.prefixes != null) {
            var2 = this.prefixes.iterator();

            while(var2.hasNext()) {
               prefix = (String)var2.next();
               if (string.startsWith(prefix)) {
                  return true;
               }
            }
         }

         return false;
      }
   }
}
