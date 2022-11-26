package com.bea.core.repackaged.springframework.util.xml;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.xml.namespace.NamespaceContext;

public class SimpleNamespaceContext implements NamespaceContext {
   private final Map prefixToNamespaceUri = new HashMap();
   private final Map namespaceUriToPrefixes = new HashMap();
   private String defaultNamespaceUri = "";

   public String getNamespaceURI(String prefix) {
      Assert.notNull(prefix, (String)"No prefix given");
      if ("xml".equals(prefix)) {
         return "http://www.w3.org/XML/1998/namespace";
      } else if ("xmlns".equals(prefix)) {
         return "http://www.w3.org/2000/xmlns/";
      } else if ("".equals(prefix)) {
         return this.defaultNamespaceUri;
      } else {
         return this.prefixToNamespaceUri.containsKey(prefix) ? (String)this.prefixToNamespaceUri.get(prefix) : "";
      }
   }

   @Nullable
   public String getPrefix(String namespaceUri) {
      Set prefixes = this.getPrefixesSet(namespaceUri);
      return !prefixes.isEmpty() ? (String)prefixes.iterator().next() : null;
   }

   public Iterator getPrefixes(String namespaceUri) {
      return this.getPrefixesSet(namespaceUri).iterator();
   }

   private Set getPrefixesSet(String namespaceUri) {
      Assert.notNull(namespaceUri, (String)"No namespaceUri given");
      if (this.defaultNamespaceUri.equals(namespaceUri)) {
         return Collections.singleton("");
      } else if ("http://www.w3.org/XML/1998/namespace".equals(namespaceUri)) {
         return Collections.singleton("xml");
      } else if ("http://www.w3.org/2000/xmlns/".equals(namespaceUri)) {
         return Collections.singleton("xmlns");
      } else {
         Set prefixes = (Set)this.namespaceUriToPrefixes.get(namespaceUri);
         return prefixes != null ? Collections.unmodifiableSet(prefixes) : Collections.emptySet();
      }
   }

   public void setBindings(Map bindings) {
      bindings.forEach(this::bindNamespaceUri);
   }

   public void bindDefaultNamespaceUri(String namespaceUri) {
      this.bindNamespaceUri("", namespaceUri);
   }

   public void bindNamespaceUri(String prefix, String namespaceUri) {
      Assert.notNull(prefix, (String)"No prefix given");
      Assert.notNull(namespaceUri, (String)"No namespaceUri given");
      if ("".equals(prefix)) {
         this.defaultNamespaceUri = namespaceUri;
      } else {
         this.prefixToNamespaceUri.put(prefix, namespaceUri);
         Set prefixes = (Set)this.namespaceUriToPrefixes.computeIfAbsent(namespaceUri, (k) -> {
            return new LinkedHashSet();
         });
         prefixes.add(prefix);
      }

   }

   public void removeBinding(@Nullable String prefix) {
      if ("".equals(prefix)) {
         this.defaultNamespaceUri = "";
      } else if (prefix != null) {
         String namespaceUri = (String)this.prefixToNamespaceUri.remove(prefix);
         if (namespaceUri != null) {
            Set prefixes = (Set)this.namespaceUriToPrefixes.get(namespaceUri);
            if (prefixes != null) {
               prefixes.remove(prefix);
               if (prefixes.isEmpty()) {
                  this.namespaceUriToPrefixes.remove(namespaceUri);
               }
            }
         }
      }

   }

   public void clear() {
      this.prefixToNamespaceUri.clear();
      this.namespaceUriToPrefixes.clear();
   }

   public Iterator getBoundPrefixes() {
      return this.prefixToNamespaceUri.keySet().iterator();
   }
}
