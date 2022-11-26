package com.sun.faces.facelets.compiler;

import com.sun.faces.facelets.tag.TagLibrary;
import java.util.ArrayList;
import java.util.List;

final class NamespaceManager {
   private final List namespaces = new ArrayList();

   public NamespaceManager() {
   }

   public void reset() {
      this.namespaces.clear();
   }

   public void pushNamespace(String prefix, String namespace) {
      NS ns = new NS(prefix, namespace);
      this.namespaces.add(0, ns);
   }

   public String getNamespace(String prefix) {
      NS ns = null;

      for(int i = 0; i < this.namespaces.size(); ++i) {
         ns = (NS)this.namespaces.get(i);
         if (ns.prefix.equals(prefix)) {
            return ns.namespace;
         }
      }

      return null;
   }

   public void popNamespace(String prefix) {
      NS ns = null;

      for(int i = 0; i < this.namespaces.size(); ++i) {
         ns = (NS)this.namespaces.get(i);
         if (ns.prefix.equals(prefix)) {
            this.namespaces.remove(i);
            return;
         }
      }

   }

   public NamespaceUnit toNamespaceUnit(TagLibrary library) {
      NamespaceUnit unit = new NamespaceUnit(library);
      if (this.namespaces.size() > 0) {
         NS ns = null;

         for(int i = this.namespaces.size() - 1; i >= 0; --i) {
            ns = (NS)this.namespaces.get(i);
            unit.setNamespace(ns.prefix, ns.namespace);
         }
      }

      return unit;
   }

   private static final class NS {
      public final String prefix;
      public final String namespace;

      public NS(String prefix, String ns) {
         this.prefix = prefix;
         this.namespace = ns;
      }
   }
}
