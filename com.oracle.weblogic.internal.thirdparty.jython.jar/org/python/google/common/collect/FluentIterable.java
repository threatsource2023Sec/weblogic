package org.python.google.common.collect;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Function;
import org.python.google.common.base.Joiner;
import org.python.google.common.base.Optional;
import org.python.google.common.base.Preconditions;
import org.python.google.common.base.Predicate;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible(
   emulated = true
)
public abstract class FluentIterable implements Iterable {
   private final Optional iterableDelegate;

   protected FluentIterable() {
      this.iterableDelegate = Optional.absent();
   }

   FluentIterable(Iterable iterable) {
      Preconditions.checkNotNull(iterable);
      this.iterableDelegate = Optional.fromNullable(this != iterable ? iterable : null);
   }

   private Iterable getDelegate() {
      return (Iterable)this.iterableDelegate.or((Object)this);
   }

   public static FluentIterable from(final Iterable iterable) {
      return iterable instanceof FluentIterable ? (FluentIterable)iterable : new FluentIterable(iterable) {
         public Iterator iterator() {
            return iterable.iterator();
         }
      };
   }

   @Beta
   public static FluentIterable from(Object[] elements) {
      return from((Iterable)Arrays.asList(elements));
   }

   /** @deprecated */
   @Deprecated
   public static FluentIterable from(FluentIterable iterable) {
      return (FluentIterable)Preconditions.checkNotNull(iterable);
   }

   @Beta
   public static FluentIterable concat(Iterable a, Iterable b) {
      return concat((Iterable)ImmutableList.of(a, b));
   }

   @Beta
   public static FluentIterable concat(Iterable a, Iterable b, Iterable c) {
      return concat((Iterable)ImmutableList.of(a, b, c));
   }

   @Beta
   public static FluentIterable concat(Iterable a, Iterable b, Iterable c, Iterable d) {
      return concat((Iterable)ImmutableList.of(a, b, c, d));
   }

   @Beta
   public static FluentIterable concat(Iterable... inputs) {
      return concat((Iterable)ImmutableList.copyOf((Object[])inputs));
   }

   @Beta
   public static FluentIterable concat(final Iterable inputs) {
      Preconditions.checkNotNull(inputs);
      return new FluentIterable() {
         public Iterator iterator() {
            return Iterators.concat(Iterables.transform(inputs, Iterables.toIterator()).iterator());
         }
      };
   }

   @Beta
   public static FluentIterable of() {
      return from((Iterable)ImmutableList.of());
   }

   /** @deprecated */
   @Deprecated
   @Beta
   public static FluentIterable of(Object[] elements) {
      return from((Iterable)Lists.newArrayList(elements));
   }

   @Beta
   public static FluentIterable of(@Nullable Object element, Object... elements) {
      return from((Iterable)Lists.asList(element, elements));
   }

   public String toString() {
      return Iterables.toString(this.getDelegate());
   }

   public final int size() {
      return Iterables.size(this.getDelegate());
   }

   public final boolean contains(@Nullable Object target) {
      return Iterables.contains(this.getDelegate(), target);
   }

   public final FluentIterable cycle() {
      return from(Iterables.cycle(this.getDelegate()));
   }

   @Beta
   public final FluentIterable append(Iterable other) {
      return from(concat(this.getDelegate(), other));
   }

   @Beta
   public final FluentIterable append(Object... elements) {
      return from(concat(this.getDelegate(), Arrays.asList(elements)));
   }

   public final FluentIterable filter(Predicate predicate) {
      return from(Iterables.filter(this.getDelegate(), predicate));
   }

   @GwtIncompatible
   public final FluentIterable filter(Class type) {
      return from(Iterables.filter(this.getDelegate(), type));
   }

   public final boolean anyMatch(Predicate predicate) {
      return Iterables.any(this.getDelegate(), predicate);
   }

   public final boolean allMatch(Predicate predicate) {
      return Iterables.all(this.getDelegate(), predicate);
   }

   public final Optional firstMatch(Predicate predicate) {
      return Iterables.tryFind(this.getDelegate(), predicate);
   }

   public final FluentIterable transform(Function function) {
      return from(Iterables.transform(this.getDelegate(), function));
   }

   public FluentIterable transformAndConcat(Function function) {
      return from(concat((Iterable)this.transform(function)));
   }

   public final Optional first() {
      Iterator iterator = this.getDelegate().iterator();
      return iterator.hasNext() ? Optional.of(iterator.next()) : Optional.absent();
   }

   public final Optional last() {
      Iterable iterable = this.getDelegate();
      if (iterable instanceof List) {
         List list = (List)iterable;
         return list.isEmpty() ? Optional.absent() : Optional.of(list.get(list.size() - 1));
      } else {
         Iterator iterator = iterable.iterator();
         if (!iterator.hasNext()) {
            return Optional.absent();
         } else if (iterable instanceof SortedSet) {
            SortedSet sortedSet = (SortedSet)iterable;
            return Optional.of(sortedSet.last());
         } else {
            Object current;
            do {
               current = iterator.next();
            } while(iterator.hasNext());

            return Optional.of(current);
         }
      }
   }

   public final FluentIterable skip(int numberToSkip) {
      return from(Iterables.skip(this.getDelegate(), numberToSkip));
   }

   public final FluentIterable limit(int maxSize) {
      return from(Iterables.limit(this.getDelegate(), maxSize));
   }

   public final boolean isEmpty() {
      return !this.getDelegate().iterator().hasNext();
   }

   public final ImmutableList toList() {
      return ImmutableList.copyOf(this.getDelegate());
   }

   public final ImmutableList toSortedList(Comparator comparator) {
      return Ordering.from(comparator).immutableSortedCopy(this.getDelegate());
   }

   public final ImmutableSet toSet() {
      return ImmutableSet.copyOf(this.getDelegate());
   }

   public final ImmutableSortedSet toSortedSet(Comparator comparator) {
      return ImmutableSortedSet.copyOf(comparator, this.getDelegate());
   }

   public final ImmutableMultiset toMultiset() {
      return ImmutableMultiset.copyOf(this.getDelegate());
   }

   public final ImmutableMap toMap(Function valueFunction) {
      return Maps.toMap(this.getDelegate(), valueFunction);
   }

   public final ImmutableListMultimap index(Function keyFunction) {
      return Multimaps.index(this.getDelegate(), keyFunction);
   }

   public final ImmutableMap uniqueIndex(Function keyFunction) {
      return Maps.uniqueIndex(this.getDelegate(), keyFunction);
   }

   @GwtIncompatible
   public final Object[] toArray(Class type) {
      return Iterables.toArray(this.getDelegate(), type);
   }

   @CanIgnoreReturnValue
   public final Collection copyInto(Collection collection) {
      Preconditions.checkNotNull(collection);
      Iterable iterable = this.getDelegate();
      if (iterable instanceof Collection) {
         collection.addAll(Collections2.cast(iterable));
      } else {
         Iterator var3 = iterable.iterator();

         while(var3.hasNext()) {
            Object item = var3.next();
            collection.add(item);
         }
      }

      return collection;
   }

   @Beta
   public final String join(Joiner joiner) {
      return joiner.join((Iterable)this);
   }

   public final Object get(int position) {
      return Iterables.get(this.getDelegate(), position);
   }

   private static class FromIterableFunction implements Function {
      public FluentIterable apply(Iterable fromObject) {
         return FluentIterable.from(fromObject);
      }
   }
}
