package org.jboss.weld.module.web.el;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Namespace {
   private final String qualifiedName;
   private final String name;
   private final Map children;

   public Namespace(Iterable namespaces) {
      this((String)null, (String)null);
      Iterator var2 = namespaces.iterator();

      while(var2.hasNext()) {
         String namespace = (String)var2.next();
         String[] hierarchy = namespace.split("\\.");
         Namespace n = this;
         String[] var6 = hierarchy;
         int var7 = hierarchy.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            String s = var6[var8];
            n = n.putIfAbsent(s);
         }
      }

   }

   protected Namespace(String name, String qualifiedName) {
      this.name = name;
      this.qualifiedName = qualifiedName;
      this.children = new HashMap();
   }

   private Namespace putIfAbsent(String key) {
      Namespace result = (Namespace)this.children.get(key);
      if (result == null) {
         result = new Namespace(key, this.qualifyName(key));
         this.children.put(key, result);
      }

      return result;
   }

   public Namespace get(String key) {
      return (Namespace)this.children.get(key);
   }

   public boolean contains(String key) {
      return this.children.containsKey(key);
   }

   public String getQualifiedName() {
      return this.qualifiedName;
   }

   protected String getName() {
      return this.name;
   }

   public String qualifyName(String suffix) {
      return this.qualifiedName == null ? suffix : this.qualifiedName + "." + suffix;
   }

   public int hashCode() {
      return this.name == null ? 0 : this.name.hashCode();
   }

   public boolean equals(Object other) {
      if (other instanceof Namespace) {
         Namespace that = (Namespace)other;
         return this.getQualifiedName().equals(that.getQualifiedName());
      } else {
         return false;
      }
   }

   public String toString() {
      return "Namespace(" + (this.name == null ? "Root" : this.name) + ')';
   }

   public void clear() {
   }
}
