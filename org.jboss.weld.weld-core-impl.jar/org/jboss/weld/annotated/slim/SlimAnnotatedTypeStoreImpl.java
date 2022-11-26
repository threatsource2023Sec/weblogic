package org.jboss.weld.annotated.slim;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import org.jboss.weld.bootstrap.api.helpers.AbstractBootstrapService;
import org.jboss.weld.util.cache.ComputingCache;
import org.jboss.weld.util.cache.ComputingCacheBuilder;
import org.jboss.weld.util.collections.WeldCollections;

public class SlimAnnotatedTypeStoreImpl extends AbstractBootstrapService implements SlimAnnotatedTypeStore {
   private final ComputingCache typesByClass = ComputingCacheBuilder.newBuilder().build((x) -> {
      return new CopyOnWriteArraySet();
   });

   public SlimAnnotatedType get(Class type, String suffix) {
      Iterator var3 = this.get(type).iterator();

      SlimAnnotatedType annotatedType;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         annotatedType = (SlimAnnotatedType)var3.next();
      } while(!Objects.equals(((AnnotatedTypeIdentifier)annotatedType.getIdentifier()).getSuffix(), suffix));

      return annotatedType;
   }

   public Set get(Class type) {
      return WeldCollections.immutableSetView((Set)this.typesByClass.getCastValue(type));
   }

   public void put(SlimAnnotatedType type) {
      ((Set)this.typesByClass.getValue(type.getJavaClass())).add(type);
   }

   public void cleanupAfterBoot() {
      this.typesByClass.clear();
   }
}
