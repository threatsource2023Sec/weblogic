package weblogic.servlet.utils;

import java.util.HashMap;
import weblogic.utils.collections.MatchMap;

public final class ServletMapping extends StandardURLMapping implements URLMapping {
   final boolean strictPattern;
   private Object slashStarObject;

   public ServletMapping() {
      this(false, true);
   }

   public ServletMapping(boolean ignoreCase, boolean strictPattern) {
      super(ignoreCase, ignoreCase);
      this.strictPattern = strictPattern;
   }

   private ServletMapping(MatchMap matchMap, HashMap extensionMap, boolean ignoreCase, boolean ignoreExtensionCase, boolean strictPattern) {
      super(matchMap, extensionMap, ignoreCase, ignoreExtensionCase);
      this.strictPattern = strictPattern;
   }

   public void put(String pattern, Object value) {
      pattern = fixPattern(pattern);
      if (!this.strictPattern && pattern.equals("/*")) {
         if (this.slashStarObject == null) {
            ++this.size;
         }

         this.slashStarObject = value;
      } else {
         super.put(pattern, value);
      }
   }

   public Object removePattern(String pattern) {
      pattern = fixPattern(pattern);
      if (!this.strictPattern && pattern.equals("/*")) {
         if (this.slashStarObject != null) {
            --this.size;
         }

         Object oldValue = this.slashStarObject;
         this.slashStarObject = null;
         return oldValue;
      } else {
         return super.removePattern(pattern);
      }
   }

   public Object get(String path) {
      if (this.strictPattern) {
         return super.get(path);
      } else {
         path = path.length() == 0 ? "/" : this.cased(path);
         Object value = null;
         if ((value = this.getExactOrPathMatch(path)) != null) {
            return value;
         } else if ((value = this.getExtensionMatch(path)) != null) {
            return value;
         } else {
            return this.slashStarObject != null ? this.slashStarObject : this.getDefault();
         }
      }
   }

   public String[] keys() {
      String[] keys = super.keys();
      if (this.slashStarObject != null) {
         keys[keys.length - 1] = "/*";
      }

      return keys;
   }

   public Object[] values() {
      Object[] values = super.values();
      if (this.slashStarObject != null) {
         values[values.length - 1] = this.slashStarObject;
      }

      return values;
   }

   public Object clone() {
      return (ServletMapping)super.clone();
   }

   private static String fixPattern(String pattern) {
      if (!pattern.startsWith("/") && !pattern.startsWith("*.")) {
         pattern = "/" + pattern;
      }

      if (pattern.endsWith("/") && pattern.length() > 1) {
         pattern = pattern.substring(0, pattern.length() - 1);
      }

      return pattern;
   }
}
