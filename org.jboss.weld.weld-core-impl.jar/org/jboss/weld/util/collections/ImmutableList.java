package org.jboss.weld.util.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import org.jboss.weld.util.Preconditions;

public abstract class ImmutableList extends AbstractImmutableList {
   ImmutableList() {
   }

   @SafeVarargs
   public static List of(Object... elements) {
      Preconditions.checkNotNull(elements);
      return ofInternal(elements);
   }

   public static List copyOf(Object[] elements) {
      Preconditions.checkNotNull(elements);
      return ofInternal((Object[])elements.clone());
   }

   public static List copyOf(Collection source) {
      Preconditions.checkNotNull(source);
      if (source instanceof ImmutableList) {
         return (ImmutableList)source;
      } else {
         return source.isEmpty() ? Collections.emptyList() : ofInternal(source.toArray());
      }
   }

   public static List copyOf(Iterable source) {
      Preconditions.checkNotNull(source);
      return source instanceof Collection ? copyOf((Collection)source) : builder().addAll(source).build();
   }

   private static List ofInternal(Object[] elements) {
      switch (elements.length) {
         case 0:
            return Collections.emptyList();
         case 1:
            return new ImmutableTinyList.Singleton(elements[0]);
         default:
            return new ImmutableArrayList(checkElementsNotNull(elements));
      }
   }

   private static Object[] checkElementsNotNull(Object[] objects) {
      Object[] var1 = objects;
      int var2 = objects.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Object object = var1[var3];
         Preconditions.checkNotNull(object);
      }

      return objects;
   }

   public static ImmutableListCollector collector() {
      return ImmutableList.ImmutableListCollector.INSTANCE;
   }

   public static Builder builder() {
      return new BuilderImpl();
   }

   private static class ImmutableListCollector implements Collector {
      private static final ImmutableListCollector INSTANCE = new ImmutableListCollector();
      private static final Set CHARACTERISTICS = ImmutableSet.of();

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
   }

   private static class BuilderImpl implements Builder {
      private static final int DEFAULT_CAPACITY = 10;
      private List list;

      private BuilderImpl() {
         this.list = new ArrayList(10);
      }

      public Builder add(Object item) {
         if (item == null) {
            throw new IllegalArgumentException("This collection does not support null values");
         } else {
            this.list.add(item);
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
         this.addAll((Iterable)items.list);
         return this;
      }

      public List build() {
         return ImmutableList.ofInternal(this.list.toArray());
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

      List build();
   }
}
