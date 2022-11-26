package org.jboss.weld.resolution;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import org.jboss.weld.config.ConfigurationKey;
import org.jboss.weld.config.WeldConfiguration;
import org.jboss.weld.util.cache.ComputingCache;
import org.jboss.weld.util.cache.ComputingCacheBuilder;
import org.jboss.weld.util.collections.ImmutableList;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Reflections;

public abstract class TypeSafeResolver {
   private final ComputingCache resolved;
   private final Iterable allBeans;
   private final ResolvableToBeanCollection resolverFunction = new ResolvableToBeanCollection(this);

   public TypeSafeResolver(Iterable allBeans, WeldConfiguration configuration) {
      this.resolved = ComputingCacheBuilder.newBuilder().setMaxSize(configuration.getLongProperty(ConfigurationKey.RESOLUTION_CACHE_SIZE)).build(this.resolverFunction);
      this.allBeans = allBeans;
   }

   public void clear() {
      this.resolved.clear();
   }

   public Object resolve(Resolvable resolvable, boolean cache) {
      Resolvable wrappedResolvable = this.wrap(resolvable);
      return cache ? this.resolved.getValue(wrappedResolvable) : this.resolverFunction.apply(wrappedResolvable);
   }

   private Set findMatching(Resolvable resolvable) {
      Set result = new HashSet();
      Iterator var3 = this.getAllBeans(resolvable).iterator();

      while(var3.hasNext()) {
         Object bean = var3.next();
         if (this.matches(resolvable, bean)) {
            result.add(bean);
         }
      }

      return result;
   }

   protected Iterable getAllBeans(Resolvable resolvable) {
      return this.allBeans;
   }

   protected Iterable getAllBeans() {
      return this.allBeans;
   }

   protected abstract Set filterResult(Set var1);

   protected abstract Collection sortResult(Set var1);

   protected abstract boolean matches(Resolvable var1, Object var2);

   protected Object makeResultImmutable(Collection result) {
      if (result instanceof List) {
         return Reflections.cast(ImmutableList.copyOf((Collection)((List)result)));
      } else if (result instanceof Set) {
         return Reflections.cast(ImmutableSet.copyOf((Set)result));
      } else {
         throw new IllegalArgumentException("Unable to make result immutable");
      }
   }

   protected Resolvable wrap(Resolvable resolvable) {
      return resolvable;
   }

   public boolean isCached(Resolvable resolvable) {
      return this.resolved.getValueIfPresent(this.wrap(resolvable)) != null;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("Resolver\n");
      sb.append("Resolved injection points: ").append(this.resolved.size()).append('\n');
      return sb.toString();
   }

   private static class ResolvableToBeanCollection implements Function {
      private final TypeSafeResolver resolver;

      private ResolvableToBeanCollection(TypeSafeResolver resolver) {
         this.resolver = resolver;
      }

      public Object apply(Resolvable from) {
         return this.resolver.makeResultImmutable(this.resolver.sortResult(this.resolver.filterResult(this.resolver.findMatching(from))));
      }

      // $FF: synthetic method
      ResolvableToBeanCollection(TypeSafeResolver x0, Object x1) {
         this(x0);
      }
   }
}
