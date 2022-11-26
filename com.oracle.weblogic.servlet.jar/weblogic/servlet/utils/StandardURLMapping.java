package weblogic.servlet.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.utils.collections.MatchMap;

public class StandardURLMapping implements URLMapping, Cloneable {
   private MatchMap matchMap;
   private HashMap extensionMap;
   protected boolean ignoreExtensionCase;
   protected boolean ignoreCase;
   protected Object defaultObject;
   protected int size;

   public StandardURLMapping() {
      this(false, false);
   }

   public StandardURLMapping(boolean ignoreCase, boolean ignoreExtensionCase) {
      this(new MatchMap(), new HashMap(), ignoreCase, ignoreExtensionCase);
   }

   protected StandardURLMapping(MatchMap matchMap, HashMap extensionMap, boolean ignoreCase, boolean ignoreExtensionCase) {
      this.matchMap = matchMap;
      this.extensionMap = extensionMap;
      this.ignoreCase = ignoreCase;
      this.ignoreExtensionCase = ignoreExtensionCase;
      this.defaultObject = null;
      this.size = 0;
   }

   public void put(String pattern, Object value) {
      Object oldValue = null;
      if (pattern.equals("/")) {
         oldValue = this.putDefaultMapping(value);
      } else if (pattern.startsWith("*.")) {
         oldValue = this.putExtensionMapping(pattern, value);
      } else if (pattern.startsWith("/") && pattern.endsWith("/*")) {
         oldValue = this.putPathMapping(pattern, value);
      } else {
         oldValue = this.putExactMapping(pattern, value);
      }

      if (oldValue == null) {
         ++this.size;
      }

   }

   public void remove(String pattern) {
      this.removePattern(pattern);
   }

   public Object removePattern(String pattern) {
      Object oldValue = null;
      if (pattern.equals("/")) {
         oldValue = this.removeDefaultMapping();
      } else if (pattern.startsWith("*.")) {
         oldValue = this.removeExtensionMapping(pattern);
      } else if (pattern.startsWith("/") && pattern.endsWith("/*")) {
         oldValue = this.removePathMapping(pattern);
      } else {
         oldValue = this.removeExactMapping(pattern);
      }

      if (oldValue != null) {
         --this.size;
      }

      return oldValue;
   }

   public Object get(String path) {
      path = path.length() == 0 ? "/" : this.cased(path);
      Object value = null;
      if ((value = this.getExactOrPathMatch(path)) != null) {
         return value;
      } else {
         return (value = this.getExtensionMatch(path)) != null ? value : this.getDefault();
      }
   }

   public void setDefault(Object o) {
      this.defaultObject = o;
   }

   public Object getDefault() {
      return this.defaultObject;
   }

   public String[] keys() {
      String[] keys = new String[this.size()];
      int index = 0;
      if (this.defaultObject != null) {
         keys[index++] = "/";
      }

      Iterator it;
      Map.Entry e;
      for(it = this.matchMap.entrySet().iterator(); it.hasNext(); index = ((Node)e.getValue()).addKey((String)e.getKey(), keys, index)) {
         e = (Map.Entry)it.next();
      }

      for(it = this.extensionMap.keySet().iterator(); it.hasNext(); keys[index++] = "*." + it.next()) {
      }

      return keys;
   }

   public Object[] values() {
      Object[] values = new Object[this.size()];
      int index = 0;
      if (this.defaultObject != null) {
         values[index++] = this.defaultObject;
      }

      Iterator it;
      Map.Entry e;
      for(it = this.matchMap.entrySet().iterator(); it.hasNext(); index = ((Node)e.getValue()).addValue(values, index)) {
         e = (Map.Entry)it.next();
      }

      for(it = this.extensionMap.values().iterator(); it.hasNext(); values[index++] = it.next()) {
      }

      return values;
   }

   public int size() {
      return this.size;
   }

   public void setCaseInsensitive(boolean ci) {
      this.ignoreCase = ci;
   }

   public boolean isCaseInsensitive() {
      return this.ignoreCase;
   }

   public void setExtensionCaseInsensitive(boolean ci) {
      this.ignoreExtensionCase = ci;
   }

   public boolean isExtensionCaseInsensitive() {
      return this.ignoreExtensionCase;
   }

   public Object clone() {
      StandardURLMapping clone = null;

      try {
         clone = (StandardURLMapping)super.clone();
      } catch (CloneNotSupportedException var3) {
         var3.printStackTrace();
      }

      if (clone != null) {
         clone.matchMap = (MatchMap)this.matchMap.clone();
         clone.extensionMap = new HashMap(this.extensionMap);
      }

      return clone;
   }

   protected Object putDefaultMapping(Object value) {
      Object oldValue = this.defaultObject;
      this.defaultObject = value;
      return oldValue;
   }

   protected Object removeDefaultMapping() {
      Object oldValue = this.defaultObject;
      this.defaultObject = null;
      return oldValue;
   }

   protected Object putExtensionMapping(String pattern, Object value) {
      return this.extensionMap.put(this.casedExtension(pattern.substring(2)), value);
   }

   protected Object removeExtensionMapping(String pattern) {
      return this.extensionMap.remove(this.casedExtension(pattern.substring(2)));
   }

   protected Object getExtensionMatch(String url) {
      int dotIndex = url.lastIndexOf(46);
      return dotIndex >= 0 ? this.extensionMap.get(this.casedExtension(url.substring(dotIndex + 1))) : null;
   }

   protected Object putPathMapping(String pattern, Object value) {
      pattern = this.cased(pattern);
      int patternLength = pattern.length();
      String fullMatchKey = pattern.substring(0, patternLength - 2);
      FullMatchNode fmn = (FullMatchNode)this.matchMap.get(fullMatchKey);
      if (fmn != null) {
         fmn.patternValue = value;
      } else {
         this.matchMap.put(fullMatchKey, new FullMatchNode(value, patternLength - 2));
      }

      Map.Entry e = this.matchMap.put(pattern.substring(0, patternLength - 1), new Node(value));
      return e == null ? null : ((Node)e.getValue()).patternValue;
   }

   protected Object removePathMapping(String pattern) {
      pattern = this.cased(pattern);
      int patternLength = pattern.length();
      Node n = (Node)this.matchMap.remove(pattern.substring(0, patternLength - 1));
      if (n == null) {
         return null;
      } else {
         String fmk = pattern.substring(0, patternLength - 2);
         FullMatchNode fmn = (FullMatchNode)this.matchMap.get(fmk);
         if (fmn != null && fmn.patternValue != null) {
            fmn.patternValue = null;
            if (fmn.exactValue == null) {
               this.matchMap.remove(fmk);
            }
         }

         return n.patternValue;
      }
   }

   protected Object putExactMapping(String pattern, Object value) {
      pattern = this.cased(pattern);
      int patternLength = pattern.length();
      FullMatchNode fmn = (FullMatchNode)this.matchMap.get(pattern);
      if (fmn == null) {
         this.matchMap.put(pattern, new FullMatchNode((Object)null, patternLength, value));
         return null;
      } else {
         Object oldValue = fmn.exactValue;
         fmn.exactValue = value;
         return oldValue;
      }
   }

   protected Object removeExactMapping(String pattern) {
      pattern = this.cased(pattern);
      FullMatchNode fmn = (FullMatchNode)this.matchMap.get(pattern);
      if (fmn == null) {
         return null;
      } else {
         Object oldValue = fmn.exactValue;
         fmn.exactValue = null;
         if (fmn.patternValue == null) {
            this.matchMap.remove(pattern);
         }

         return oldValue;
      }
   }

   protected Object getExactOrPathMatch(String url) {
      String prefix = url;

      do {
         Map.Entry e = this.matchMap.match(prefix);
         if (e == null) {
            break;
         }

         Node n = (Node)e.getValue();
         Object value = n.match(prefix);
         if (value != null) {
            return value;
         }

         prefix = n.shorterKey(prefix);
      } while(prefix != null);

      return null;
   }

   protected String cased(String pattern) {
      if (pattern == null) {
         return null;
      } else {
         return this.ignoreCase ? pattern.toLowerCase() : pattern;
      }
   }

   protected String casedExtension(String ext) {
      if (ext == null) {
         return null;
      } else {
         return this.ignoreExtensionCase ? ext.toLowerCase() : ext;
      }
   }

   private static final class FullMatchNode extends Node {
      final int keyLength;

      FullMatchNode(Object pv, int kl) {
         this(pv, kl, (Object)null);
      }

      FullMatchNode(Object pv, int kl, Object ev) {
         super(pv);
         this.keyLength = kl;
         this.exactValue = ev;
      }

      Object match(String path) {
         if (path.length() != this.keyLength) {
            return null;
         } else {
            return this.exactValue == null ? this.patternValue : this.exactValue;
         }
      }

      int addKey(String k, String[] keys, int off) {
         if (this.exactValue != null) {
            keys[off++] = k;
         }

         return off;
      }

      int addValue(Object[] values, int off) {
         if (this.exactValue != null) {
            values[off++] = this.exactValue;
         }

         return off;
      }

      String shorterKey(String key) {
         int i = key.lastIndexOf(47, this.keyLength - 1);
         return i >= 0 ? key.substring(0, i + 1) : null;
      }
   }

   private static class Node {
      Object patternValue;
      Object exactValue;

      Node(Object pv) {
         this.patternValue = pv;
      }

      Object match(String path) {
         return this.patternValue;
      }

      int addKey(String k, String[] keys, int off) {
         keys[off++] = k + "*";
         return off;
      }

      int addValue(Object[] values, int off) {
         values[off++] = this.patternValue;
         return off;
      }

      boolean isExactMatch() {
         return this.exactValue != null;
      }

      String shorterKey(String key) {
         return null;
      }
   }
}
