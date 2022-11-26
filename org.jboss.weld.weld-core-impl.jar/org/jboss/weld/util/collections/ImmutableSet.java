package org.jboss.weld.util.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;
import org.jboss.weld.util.Preconditions;

public abstract class ImmutableSet extends AbstractImmutableSet {
   ImmutableSet() {
   }

   public static Set copyOf(Collection collection) {
      Preconditions.checkNotNull(collection);
      if (collection instanceof AbstractImmutableSet) {
         return (Set)collection;
      } else if (collection.isEmpty()) {
         return Collections.emptySet();
      } else {
         return collection instanceof Set ? from((Set)collection) : builder().addAll((Iterable)collection).build();
      }
   }

   @SafeVarargs
   public static Set of(Object... elements) {
      Preconditions.checkNotNull(elements);
      return builder().addAll(elements).build();
   }

   public static ImmutableSetCollector collector() {
      return ImmutableSet.ImmutableSetCollector.INSTANCE;
   }

   public static Builder builder() {
      return new BuilderImpl();
   }

   private static Set from(Set set) {
      switch (set.size()) {
         case 0:
            return Collections.emptySet();
         case 1:
            return new ImmutableTinySet.Singleton(set);
         case 2:
            return new ImmutableTinySet.Doubleton(set);
         case 3:
            return new ImmutableTinySet.Tripleton(set);
         default:
            return new ImmutableHashSet(set);
      }
   }

   private static class ImmutableSetCollector implements Collector {
      private static final ImmutableSetCollector INSTANCE = new ImmutableSetCollector();
      private static final Set CHARACTERISTICS;

      public Supplier supplier() {
         return () -> {
            return new BuilderImpl();
         };
      }

      public BiConsumer accumulator() {
         return BuilderImpl::add;
      }

      public BinaryOperator combiner() {
         return (builder1, builder2) -> {
            return builder1.addAll(builder2);
         };
      }

      public Function finisher() {
         return BuilderImpl::build;
      }

      public Set characteristics() {
         return CHARACTERISTICS;
      }

      static {
         CHARACTERISTICS = ImmutableSet.of(Characteristics.UNORDERED);
      }
   }

   private static class BuilderImpl implements Builder {
      private Set set;

      private BuilderImpl() {
         this.set = new LinkedHashSet();
      }

      public Builder add(Object item) {
         if (item == null) {
            throw new IllegalArgumentException("This collection does not support null values");
         } else {
            this.set.add(item);
            return this;
         }
      }

      public Builder addAll(Object... items) {
         Object[] var2 = items;
         int var3 = items.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Object item = var2[var4];
            this.add(item);
         }

         return this;
      }

      public Builder addAll(Iterable items) {
         Iterator var2 = items.iterator();

         while(var2.hasNext()) {
            Object item = var2.next();
            this.add(item);
         }

         return this;
      }

      BuilderImpl addAll(BuilderImpl items) {
         this.addAll((Iterable)items.set);
         return this;
      }

      public Set build() {
         return ImmutableSet.from(this.set);
      }

      // $FF: synthetic method
      BuilderImpl(Object x0) {
         this();
      }
   }

   public interface Builder {
      Builder add(Object var1);

      Builder addAll(Iterable var1);

      Builder addAll(Object... var1);

      Set build();
   }
}
