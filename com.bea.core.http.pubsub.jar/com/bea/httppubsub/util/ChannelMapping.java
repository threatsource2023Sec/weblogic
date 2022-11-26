package com.bea.httppubsub.util;

import java.util.Iterator;
import java.util.Map;
import weblogic.utils.collections.MatchMap;

public final class ChannelMapping implements Cloneable {
   private Object defaultValue;
   private final MatchMap matchMap;
   private int size;
   private boolean ignoreCase;

   public ChannelMapping() {
      this(false);
   }

   public ChannelMapping(boolean ignoreCase) {
      this(new MatchMap(), ignoreCase);
   }

   private ChannelMapping(MatchMap mm, boolean ignoreCase) {
      this.matchMap = mm;
      this.ignoreCase = ignoreCase;
   }

   public void put(String pattern, Object value) {
      pattern = this.fixPattern(pattern);
      Object oldValue = null;
      if (pattern.equals("/")) {
         oldValue = this.putRootChannelValue(value);
      } else if (pattern.startsWith("/") && pattern.endsWith("/*")) {
         oldValue = this.pubStarChannelPatternValue(pattern, value);
      } else {
         oldValue = this.putExactChannelPatternValue(pattern, value);
      }

      if (oldValue == null) {
         ++this.size;
      }

   }

   public Object remove(String pattern) {
      pattern = this.fixPattern(pattern);
      Object removed = null;
      if (pattern.equals("/")) {
         removed = this.removeRootChannelValue();
      } else if (pattern.startsWith("/") && pattern.endsWith("/*")) {
         removed = this.removeStarChannelPatternValue(pattern);
      } else {
         removed = this.removeExactChannelPatternValue(pattern);
      }

      if (removed != null) {
         --this.size;
      }

      return removed;
   }

   public Object get(String pattern) {
      if (pattern.length() == 0) {
         pattern = "/";
      }

      if (pattern.endsWith("/**")) {
         pattern = pattern.substring(0, pattern.length() - 1);
      }

      if (this.ignoreCase) {
         pattern = pattern.toLowerCase();
      }

      Object value = this.getPrefixMatch(pattern);
      return value != null ? value : this.defaultValue;
   }

   public String[] keys() {
      String[] keys = new String[this.size()];
      int index = 0;
      if (this.defaultValue != null) {
         keys[index++] = "/";
      }

      Map.Entry e;
      for(Iterator it = this.matchMap.entrySet().iterator(); it.hasNext(); index = ((Node)e.getValue()).addKey((String)e.getKey(), keys, index)) {
         e = (Map.Entry)it.next();
      }

      return keys;
   }

   public Object[] values() {
      Object[] values = new Object[this.size()];
      int index = 0;
      if (this.defaultValue != null) {
         values[index++] = this.defaultValue;
      }

      Map.Entry e;
      for(Iterator it = this.matchMap.entrySet().iterator(); it.hasNext(); index = ((Node)e.getValue()).addValue(values, index)) {
         e = (Map.Entry)it.next();
      }

      return values;
   }

   public int size() {
      return this.size;
   }

   public void setDefault(Object value) {
      this.defaultValue = value;
   }

   public Object getDefault() {
      return this.defaultValue;
   }

   public void setCaseInsensitive(boolean ci) {
      this.ignoreCase = ci;
   }

   public boolean isCaseInsensitive() {
      return this.ignoreCase;
   }

   public Object clone() {
      ChannelMapping clone = new ChannelMapping((MatchMap)this.matchMap.clone(), this.ignoreCase);
      clone.setDefault(this.defaultValue);
      clone.size = this.size;
      return clone;
   }

   private String fixPattern(String pattern) {
      if (!pattern.startsWith("/")) {
         pattern = "/" + pattern;
      }

      if (pattern.endsWith("/") && pattern.length() > 1) {
         pattern = pattern.substring(0, pattern.length() - 1);
      }

      if (pattern.endsWith("/**")) {
         pattern = pattern.substring(0, pattern.length() - 1);
      }

      return this.ignoreCase ? pattern.toLowerCase() : pattern;
   }

   private Object putRootChannelValue(Object value) {
      Object oldValue = this.defaultValue;
      this.defaultValue = value;
      return oldValue;
   }

   private Object removeRootChannelValue() {
      Object old = this.defaultValue;
      this.defaultValue = null;
      return old;
   }

   private Object pubStarChannelPatternValue(String pattern, Object value) {
      Map.Entry e = this.matchMap.put(pattern.substring(0, pattern.length() - 1), new Node(value));
      return e == null ? null : ((Node)e.getValue()).patternValue;
   }

   private Object removeStarChannelPatternValue(String pattern) {
      int l = pattern.length();
      Node n = (Node)this.matchMap.remove(pattern.substring(0, l - 1));
      if (n == null) {
         return null;
      } else {
         String fmk = pattern.substring(0, l - 2);
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

   private Object putExactChannelPatternValue(String pattern, Object value) {
      FullMatchNode fmn = (FullMatchNode)this.matchMap.get(pattern);
      if (fmn == null) {
         this.matchMap.put(pattern, new FullMatchNode((Object)null, pattern.length(), value));
         return null;
      } else {
         Object oldValue = fmn.exactValue;
         fmn.exactValue = value;
         return oldValue;
      }
   }

   private Object removeExactChannelPatternValue(String pattern) {
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

   private Object getPrefixMatch(String pattern) {
      String prefix = pattern;

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
