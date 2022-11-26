package org.opensaml.saml.metadata.resolver.index.impl;

import com.google.common.collect.ImmutableSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.saml.metadata.resolver.index.MetadataIndexKey;

public class MetadataIndexStore {
   @Nonnull
   private Map index = new ConcurrentHashMap();

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   public Set getKeys() {
      return ImmutableSet.copyOf(this.index.keySet());
   }

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   public Set lookup(@Nonnull MetadataIndexKey key) {
      Constraint.isNotNull(key, "IndexKey was null");
      Set items = (Set)this.index.get(key);
      return (Set)(items == null ? Collections.emptySet() : ImmutableSet.copyOf(items));
   }

   public void add(MetadataIndexKey key, Object item) {
      Constraint.isNotNull(key, "IndexKey was null");
      Constraint.isNotNull(item, "The indexed data element was null");
      Set items = (Set)this.index.get(key);
      if (items == null) {
         items = new HashSet();
         this.index.put(key, items);
      }

      ((Set)items).add(item);
   }

   public void remove(MetadataIndexKey key, Object item) {
      Constraint.isNotNull(key, "IndexKey was null");
      Constraint.isNotNull(item, "The indexed data element was null");
      Set items = (Set)this.index.get(key);
      if (items != null) {
         items.remove(item);
      }
   }

   public void clear(MetadataIndexKey key) {
      Constraint.isNotNull(key, "IndexKey was null");
      this.index.remove(key);
   }

   public void clear() {
      this.index.clear();
   }
}
