package net.shibboleth.utilities.java.support.xml;

import com.google.common.collect.ImmutableBiMap;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import javax.xml.namespace.NamespaceContext;
import net.shibboleth.utilities.java.support.annotation.constraint.NullableElements;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

@ThreadSafe
public class SimpleNamespaceContext implements NamespaceContext {
   private final ImmutableBiMap mappings;

   public SimpleNamespaceContext() {
      this.mappings = this.getMappingsBuilder().build();
   }

   public SimpleNamespaceContext(@Nullable @NullableElements Map prefixToUriMappings) {
      ImmutableBiMap.Builder mappingBuilder = this.getMappingsBuilder();
      if (prefixToUriMappings != null && !prefixToUriMappings.isEmpty()) {
         Iterator i$ = prefixToUriMappings.keySet().iterator();

         while(i$.hasNext()) {
            String key = (String)i$.next();
            String trimmedPrefix = StringSupport.trimOrNull(key);
            if (trimmedPrefix != null) {
               String trimmedUri = StringSupport.trimOrNull((String)prefixToUriMappings.get(key));
               if (trimmedUri != null) {
                  mappingBuilder.put(trimmedPrefix, trimmedUri);
               }
            }
         }

         this.mappings = mappingBuilder.build();
      } else {
         this.mappings = mappingBuilder.build();
      }
   }

   @Nullable
   public String getNamespaceURI(@Nonnull String prefix) {
      if (prefix == null) {
         throw new IllegalArgumentException("Prefix can not be null");
      } else {
         String uri = (String)this.mappings.get(prefix);
         return uri == null ? "" : uri;
      }
   }

   @Nullable
   public String getPrefix(@Nonnull String namespaceURI) {
      if (namespaceURI == null) {
         throw new IllegalArgumentException("Namespace URI can not be null");
      } else {
         return (String)this.mappings.inverse().get(namespaceURI);
      }
   }

   @Nonnull
   public Iterator getPrefixes(String namespaceURI) {
      if (namespaceURI == null) {
         throw new IllegalArgumentException("Namespace URI can not be null");
      } else {
         String prefix = (String)this.mappings.inverse().get(namespaceURI);
         return prefix == null ? Collections.emptyList().iterator() : Collections.singletonList(prefix).iterator();
      }
   }

   @Nonnull
   private ImmutableBiMap.Builder getMappingsBuilder() {
      ImmutableBiMap.Builder mappingBuilder = new ImmutableBiMap.Builder();
      mappingBuilder.put("xml", "http://www.w3.org/XML/1998/namespace");
      mappingBuilder.put("xmlns", "http://www.w3.org/2000/xmlns/");
      return mappingBuilder;
   }
}
