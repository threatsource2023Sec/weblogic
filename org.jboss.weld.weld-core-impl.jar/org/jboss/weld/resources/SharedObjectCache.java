package org.jboss.weld.resources;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import org.jboss.weld.annotated.enhanced.TypeClosureLazyValueHolder;
import org.jboss.weld.bootstrap.api.BootstrapService;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.LazyValueHolder;
import org.jboss.weld.util.cache.ComputingCache;
import org.jboss.weld.util.cache.ComputingCacheBuilder;
import org.jboss.weld.util.collections.ImmutableMap;
import org.jboss.weld.util.collections.ImmutableSet;

public class SharedObjectCache implements BootstrapService {
   private final ComputingCache sharedSets = ComputingCacheBuilder.newBuilder().build(new Function() {
      public Set apply(Set from) {
         return ImmutableSet.copyOf(from);
      }
   });
   private final ComputingCache sharedMaps = ComputingCacheBuilder.newBuilder().build(new Function() {
      public Map apply(Map from) {
         return ImmutableMap.copyOf(from);
      }
   });
   private final ComputingCache typeClosureHolders = ComputingCacheBuilder.newBuilder().build(new Function() {
      public LazyValueHolder apply(Type input) {
         return new TypeClosureLazyValueHolder(input);
      }
   });

   public static SharedObjectCache instance(BeanManagerImpl manager) {
      return (SharedObjectCache)manager.getServices().get(SharedObjectCache.class);
   }

   public Set getSharedSet(Set set) {
      return (Set)this.sharedSets.getCastValue(set);
   }

   public Map getSharedMap(Map map) {
      return (Map)this.sharedMaps.getCastValue(map);
   }

   public LazyValueHolder getTypeClosureHolder(Type type) {
      return (LazyValueHolder)this.typeClosureHolders.getCastValue(type);
   }

   public void cleanupAfterBoot() {
      this.sharedSets.clear();
      this.sharedMaps.clear();
      this.typeClosureHolders.clear();
   }

   public void cleanup() {
      this.cleanupAfterBoot();
   }
}
