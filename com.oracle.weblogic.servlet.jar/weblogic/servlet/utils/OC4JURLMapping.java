package weblogic.servlet.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.utils.collections.MatchMap;

public class OC4JURLMapping extends StandardURLMapping implements URLMapping, Cloneable {
   final MatchMap wildcardMap;

   public OC4JURLMapping() {
      this(false, false);
   }

   public OC4JURLMapping(boolean ignoreCase, boolean ignoreExtensionCase) {
      super(ignoreCase, ignoreExtensionCase);
      this.wildcardMap = new MatchMap();
   }

   private OC4JURLMapping(MatchMap matchMap, HashMap extensionMap, boolean ignoreCase, boolean ignoreExtensionCase, MatchMap wildcardMap) {
      super(matchMap, extensionMap, ignoreCase, ignoreExtensionCase);
      this.wildcardMap = wildcardMap;
   }

   public void put(String pattern, Object value) {
      pattern = fixPattern(pattern);
      int starIndex = pattern.indexOf("*");
      int patternLength = pattern.length();
      if (starIndex == patternLength - 1 && pattern.charAt(patternLength - 2) != '/') {
         this.putWildcardMapping(pattern.substring(0, patternLength - 1), (String)null, value);
      } else if (starIndex > 0 && starIndex < patternLength - 1) {
         this.putWildcardMapping(pattern.substring(0, starIndex), pattern.substring(starIndex + 1), value);
      } else {
         super.put(pattern, value);
      }

   }

   public Object get(String path) {
      path = path.length() == 0 ? "/" : this.cased(path);
      Object value = null;
      if ((value = this.getExactOrPathMatch(path)) != null) {
         return value;
      } else if (!this.wildcardMap.isEmpty() && (value = this.getWildcardMatch(path, true)) != null) {
         return value;
      } else if ((value = this.getExtensionMatch(path)) != null) {
         return value;
      } else {
         return !this.wildcardMap.isEmpty() && (value = this.getWildcardMatch(path, false)) != null ? value : this.getDefault();
      }
   }

   public Object removePattern(String pattern) {
      pattern = fixPattern(pattern);
      int starIndex = pattern.indexOf("*");
      int patternLength = pattern.length();
      if (starIndex == patternLength - 1 && pattern.charAt(patternLength - 2) != '/') {
         return this.removeWildcardMapping(pattern.substring(0, patternLength - 1), (String)null);
      } else {
         return starIndex > 1 && starIndex < patternLength - 1 ? this.removeWildcardMapping(pattern.substring(0, starIndex), pattern.substring(starIndex + 1)) : super.removePattern(pattern);
      }
   }

   public String[] keys() {
      String[] keys = super.keys();
      if (!this.wildcardMap.isEmpty()) {
         int index = getIndexOfFirstNull(keys);

         Map.Entry e;
         for(Iterator it = this.wildcardMap.entrySet().iterator(); it.hasNext(); index = ((WildcardNode)e.getValue()).addPatterns((String)e.getKey(), keys, index)) {
            e = (Map.Entry)it.next();
         }
      }

      return keys;
   }

   public Object[] values() {
      Object[] values = super.values();
      if (!this.wildcardMap.isEmpty()) {
         int index = getIndexOfFirstNull(values);

         Map.Entry e;
         for(Iterator it = this.wildcardMap.entrySet().iterator(); it.hasNext(); index = ((WildcardNode)e.getValue()).addPatternValues(values, index)) {
            e = (Map.Entry)it.next();
         }
      }

      return values;
   }

   public Object clone() {
      return (OC4JURLMapping)super.clone();
   }

   private Object putWildcardMapping(String prefix, String suffix, Object value) {
      prefix = this.cased(prefix);
      suffix = this.cased(suffix);
      int slashIndex = prefix.lastIndexOf(47);
      String matchKey = prefix.substring(0, slashIndex);
      WildcardNode node = (WildcardNode)this.wildcardMap.get(matchKey);
      if (node != null) {
         Object result = node.addValueToMapNode(prefix.substring(slashIndex), suffix, value);
         if (result == null) {
            ++this.size;
         }

         return result;
      } else {
         node = new WildcardNode(matchKey);
         node.addValueToMapNode(prefix.substring(slashIndex), suffix, value);
         this.wildcardMap.put(matchKey, node);
         ++this.size;
         return null;
      }
   }

   private Object getWildcardMatch(String path, boolean nullSuffixMatch) {
      int slashIndex = path.lastIndexOf(47);

      for(String prefix = path.substring(0, slashIndex); prefix != null; prefix = slashIndex <= 0 ? null : prefix.substring(0, slashIndex)) {
         Map.Entry e = this.wildcardMap.match(prefix);
         if (e == null) {
            break;
         }

         WildcardNode node = (WildcardNode)e.getValue();
         Object value = node.match(path, nullSuffixMatch);
         if (value != null) {
            return value;
         }

         slashIndex = prefix.lastIndexOf(47);
      }

      return null;
   }

   private Object removeWildcardMapping(String prefix, String suffix) {
      prefix = this.cased(prefix);
      suffix = this.cased(suffix);
      int slashIndex = prefix.lastIndexOf(47);
      String matchKey = prefix.substring(0, slashIndex);
      WildcardNode node = (WildcardNode)this.wildcardMap.get(matchKey);
      if (node == null) {
         return null;
      } else {
         Object result = node.removeValueFromMapNode(prefix.substring(slashIndex), suffix);
         if (result != null) {
            --this.size;
         }

         return result;
      }
   }

   private static String fixPattern(String pattern) {
      if (pattern.startsWith("/*.")) {
         return pattern.substring(1);
      } else {
         return !pattern.startsWith("/") && !pattern.startsWith("*.") ? "/".concat(pattern) : pattern;
      }
   }

   private static int getIndexOfFirstNull(Object[] objects) {
      int index;
      for(index = 0; index < objects.length && objects[index] != null; ++index) {
      }

      return index;
   }

   private static class WildcardKey {
      final String prefix;
      final String suffix;
      volatile int hashCode;

      WildcardKey(String prefix, String suffix) {
         this.prefix = prefix;
         this.suffix = suffix;
      }

      public boolean equals(Object obj) {
         if (obj == this) {
            return true;
         } else if (!(obj instanceof WildcardKey)) {
            return false;
         } else {
            boolean var10000;
            label43: {
               label29: {
                  WildcardKey wcKey = (WildcardKey)obj;
                  if (wcKey.prefix != null) {
                     if (!wcKey.prefix.equals(this.prefix)) {
                        break label29;
                     }
                  } else if (this.prefix != null) {
                     break label29;
                  }

                  if (wcKey.suffix != null) {
                     if (wcKey.suffix.equals(this.suffix)) {
                        break label43;
                     }
                  } else if (this.suffix == null) {
                     break label43;
                  }
               }

               var10000 = false;
               return var10000;
            }

            var10000 = true;
            return var10000;
         }
      }

      public int hashCode() {
         int result = this.hashCode;
         if (result == 0) {
            int result = 17;
            result = 31 * result + (this.prefix == null ? 0 : this.prefix.hashCode());
            result = 31 * result + (this.suffix == null ? 0 : this.suffix.hashCode());
            this.hashCode = result;
         }

         return result;
      }
   }

   private static class WildcardNode {
      final String pattern;
      final Map values;

      WildcardNode(String pattern) {
         this.pattern = pattern;
         this.values = new HashMap();
      }

      Object match(String path, boolean nullSuffixMatch) {
         String prefix = path.substring(this.pattern.length());
         Iterator it = this.values.keySet().iterator();

         while(it.hasNext()) {
            WildcardKey key = (WildcardKey)it.next();
            if (prefix.startsWith(key.prefix)) {
               if (nullSuffixMatch) {
                  if (key.suffix == null) {
                     return this.values.get(key);
                  }
               } else if (key.suffix != null && prefix.endsWith(key.suffix)) {
                  return this.values.get(key);
               }
            }
         }

         return null;
      }

      Object addValueToMapNode(String prefix, String suffix, Object value) {
         return this.values.put(new WildcardKey(prefix, suffix), value);
      }

      Object removeValueFromMapNode(String prefix, String suffix) {
         return this.values.remove(new WildcardKey(prefix, suffix));
      }

      int addPatterns(String matchKey, String[] keys, int offset) {
         WildcardKey paskey;
         for(Iterator it = this.values.keySet().iterator(); it.hasNext(); keys[offset++] = matchKey + paskey.prefix + "*" + (paskey.suffix == null ? "" : paskey.suffix)) {
            paskey = (WildcardKey)it.next();
         }

         return offset;
      }

      int addPatternValues(Object[] patternValues, int offset) {
         for(Iterator it = this.values.values().iterator(); it.hasNext(); patternValues[offset++] = it.next()) {
         }

         return offset;
      }
   }
}
