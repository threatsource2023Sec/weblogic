package com.bea.wls.redef;

import java.net.URL;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.utils.classloaders.AbstractClassFinder;
import weblogic.utils.classloaders.ByteArraySource;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.classloaders.Source;
import weblogic.utils.enumerations.EmptyEnumerator;
import weblogic.utils.enumerations.SingleItemEnumeration;

class InMemoryClassFinder extends MultiClassFinder {
   private final ConcurrentHashMap sources = new ConcurrentHashMap();

   public InMemoryClassFinder() {
      this.addFinder(new SourceClassFinder());
   }

   public void addSource(String name, byte[] bytes) {
      this.sources.put(name.replace('.', '/') + ".class", new ByteArraySource(bytes, (URL)null));
   }

   protected int getFirstIndex() {
      return 1;
   }

   public void close() {
      super.close();
      this.sources.clear();
   }

   private class SourceClassFinder extends AbstractClassFinder {
      private SourceClassFinder() {
      }

      public Source getClassSource(String name) {
         return (Source)InMemoryClassFinder.this.sources.get(name.replace('.', '/') + ".class");
      }

      public Source getSource(String name) {
         return (Source)InMemoryClassFinder.this.sources.get(name);
      }

      public Enumeration getSources(String name) {
         Object s = InMemoryClassFinder.this.sources.get(name);
         return (Enumeration)(s != null ? new SingleItemEnumeration(s) : EmptyEnumerator.EMPTY);
      }

      public String getClassPath() {
         return null;
      }

      // $FF: synthetic method
      SourceClassFinder(Object x1) {
         this();
      }
   }
}
