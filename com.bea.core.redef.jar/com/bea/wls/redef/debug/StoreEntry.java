package com.bea.wls.redef.debug;

import java.util.HashMap;
import java.util.Map;

public class StoreEntry {
   private Map entries = new HashMap();
   public static final StoreEntryFilter ACCEPT_STAR = new StoreEntryFilter() {
      public boolean accept(String className) {
         return true;
      }
   };
   private String annotation;
   private StoreEntryFilter filter;

   public StoreEntry(String ann) {
      this.filter = ACCEPT_STAR;
      this.annotation = ann;
   }

   public StoreEntry(String ann, StoreEntryFilter filter) {
      this.filter = filter;
      this.annotation = ann;
   }

   public void record(String className, byte[] clazz) {
      if (this.filter.accept(className)) {
         this.entries.put(className, clazz);
      }

   }

   Map getEntries() {
      return this.entries;
   }

   String getAnnotation() {
      return this.annotation;
   }

   public void reset() {
      this.entries.clear();
   }

   public static class NamedEntryFilter {
      private String match;

      public NamedEntryFilter(String className) {
         this.match = className;
      }

      public boolean accept(String name) {
         return name.startsWith(this.match);
      }
   }
}
