package org.python.google.common.collect;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.RandomAccess;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Function;
import org.python.google.common.base.Optional;
import org.python.google.common.base.Preconditions;
import org.python.google.common.base.Predicate;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible(
   emulated = true
)
public final class Iterables {
   private Iterables() {
   }

   public static Iterable unmodifiableIterable(Iterable iterable) {
      Preconditions.checkNotNull(iterable);
      return (Iterable)(!(iterable instanceof UnmodifiableIterable) && !(iterable instanceof ImmutableCollection) ? new UnmodifiableIterable(iterable) : iterable);
   }

   /** @deprecated */
   @Deprecated
   public static Iterable unmodifiableIterable(ImmutableCollection iterable) {
      return (Iterable)Preconditions.checkNotNull(iterable);
   }

   public static int size(Iterable iterable) {
      return iterable instanceof Collection ? ((Collection)iterable).size() : Iterators.size(iterable.iterator());
   }

   public static boolean contains(Iterable iterable, @Nullable Object element) {
      if (iterable instanceof Collection) {
         Collection collection = (Collection)iterable;
         return Collections2.safeContains(collection, element);
      } else {
         return Iterators.contains(iterable.iterator(), element);
      }
   }

   @CanIgnoreReturnValue
   public static boolean removeAll(Iterable removeFrom, Collection elementsToRemove) {
      return removeFrom instanceof Collection ? ((Collection)removeFrom).removeAll((Collection)Preconditions.checkNotNull(elementsToRemove)) : Iterators.removeAll(removeFrom.iterator(), elementsToRemove);
   }

   @CanIgnoreReturnValue
   public static boolean retainAll(Iterable removeFrom, Collection elementsToRetain) {
      return removeFrom instanceof Collection ? ((Collection)removeFrom).retainAll((Collection)Preconditions.checkNotNull(elementsToRetain)) : Iterators.retainAll(removeFrom.iterator(), elementsToRetain);
   }

   @CanIgnoreReturnValue
   public static boolean removeIf(Iterable removeFrom, Predicate predicate) {
      return removeFrom instanceof RandomAccess && removeFrom instanceof List ? removeIfFromRandomAccessList((List)removeFrom, (Predicate)Preconditions.checkNotNull(predicate)) : Iterators.removeIf(removeFrom.iterator(), predicate);
   }

   private static boolean removeIfFromRandomAccessList(List list, Predicate predicate) {
      int from = 0;

      int to;
      for(to = 0; from < list.size(); ++from) {
         Object element = list.get(from);
         if (!predicate.apply(element)) {
            if (from > to) {
               try {
                  list.set(to, element);
               } catch (UnsupportedOperationException var6) {
                  slowRemoveIfForRemainingElements(list, predicate, to, from);
                  return true;
               } catch (IllegalArgumentException var7) {
                  slowRemoveIfForRemainingElements(list, predicate, to, from);
                  return true;
               }
            }

            ++to;
         }
      }

      list.subList(to, list.size()).clear();
      return from != to;
   }

   private static void slowRemoveIfForRemainingElements(List list, Predicate predicate, int to, int from) {
      int n;
      for(n = list.size() - 1; n > from; --n) {
         if (predicate.apply(list.get(n))) {
            list.remove(n);
         }
      }

      for(n = from - 1; n >= to; --n) {
         list.remove(n);
      }

   }

   @Nullable
   static Object removeFirstMatching(Iterable removeFrom, Predicate predicate) {
      Preconditions.checkNotNull(predicate);
      Iterator iterator = removeFrom.iterator();

      Object next;
      do {
         if (!iterator.hasNext()) {
            return null;
         }

         next = iterator.next();
      } while(!predicate.apply(next));

      iterator.remove();
      return next;
   }

   public static boolean elementsEqual(Iterable iterable1, Iterable iterable2) {
      if (iterable1 instanceof Collection && iterable2 instanceof Collection) {
         Collection collection1 = (Collection)iterable1;
         Collection collection2 = (Collection)iterable2;
         if (collection1.size() != collection2.size()) {
            return false;
         }
      }

      return Iterators.elementsEqual(iterable1.iterator(), iterable2.iterator());
   }

   public static String toString(Iterable iterable) {
      return Iterators.toString(iterable.iterator());
   }

   public static Object getOnlyElement(Iterable iterable) {
      return Iterators.getOnlyElement(iterable.iterator());
   }

   @Nullable
   public static Object getOnlyElement(Iterable iterable, @Nullable Object defaultValue) {
      return Iterators.getOnlyElement(iterable.iterator(), defaultValue);
   }

   @GwtIncompatible
   public static Object[] toArray(Iterable iterable, Class type) {
      return toArray(iterable, ObjectArrays.newArray((Class)type, 0));
   }

   static Object[] toArray(Iterable iterable, Object[] array) {
      Collection collection = castOrCopyToCollection(iterable);
      return collection.toArray(array);
   }

   static Object[] toArray(Iterable iterable) {
      return castOrCopyToCollection(iterable).toArray();
   }

   private static Collection castOrCopyToCollection(Iterable iterable) {
      return (Collection)(iterable instanceof Collection ? (Collection)iterable : Lists.newArrayList(iterable.iterator()));
   }

   @CanIgnoreReturnValue
   public static boolean addAll(Collection addTo, Iterable elementsToAdd) {
      if (elementsToAdd instanceof Collection) {
         Collection c = Collections2.cast(elementsToAdd);
         return addTo.addAll(c);
      } else {
         return Iterators.addAll(addTo, ((Iterable)Preconditions.checkNotNull(elementsToAdd)).iterator());
      }
   }

   public static int frequency(Iterable iterable, @Nullable Object element) {
      if (iterable instanceof Multiset) {
         return ((Multiset)iterable).count(element);
      } else if (iterable instanceof Set) {
         return ((Set)iterable).contains(element) ? 1 : 0;
      } else {
         return Iterators.frequency(iterable.iterator(), element);
      }
   }

   public static Iterable cycle(final Iterable iterable) {
      Preconditions.checkNotNull(iterable);
      return new FluentIterable() {
         public Iterator iterator() {
            return Iterators.cycle(iterable);
         }

         public String toString() {
            return iterable.toString() + " (cycled)";
         }
      };
   }

   @SafeVarargs
   public static Iterable cycle(Object... elements) {
      return cycle((Iterable)Lists.newArrayList(elements));
   }

   public static Iterable concat(Iterable a, Iterable b) {
      return FluentIterable.concat(a, b);
   }

   public static Iterable concat(Iterable a, Iterable b, Iterable c) {
      return FluentIterable.concat(a, b, c);
   }

   public static Iterable concat(Iterable a, Iterable b, Iterable c, Iterable d) {
      return FluentIterable.concat(a, b, c, d);
   }

   @SafeVarargs
   public static Iterable concat(Iterable... inputs) {
      return concat((Iterable)ImmutableList.copyOf((Object[])inputs));
   }

   public static Iterable concat(Iterable inputs) {
      return FluentIterable.concat(inputs);
   }

   public static Iterable partition(final Iterable iterable, final int size) {
      Preconditions.checkNotNull(iterable);
      Preconditions.checkArgument(size > 0);
      return new FluentIterable() {
         public Iterator iterator() {
            return Iterators.partition(iterable.iterator(), size);
         }
      };
   }

   public static Iterable paddedPartition(final Iterable iterable, final int size) {
      Preconditions.checkNotNull(iterable);
      Preconditions.checkArgument(size > 0);
      return new FluentIterable() {
         public Iterator iterator() {
            return Iterators.paddedPartition(iterable.iterator(), size);
         }
      };
   }

   public static Iterable filter(final Iterable unfiltered, final Predicate retainIfTrue) {
      Preconditions.checkNotNull(unfiltered);
      Preconditions.checkNotNull(retainIfTrue);
      return new FluentIterable() {
         public Iterator iterator() {
            return Iterators.filter(unfiltered.iterator(), retainIfTrue);
         }
      };
   }

   @GwtIncompatible
   public static Iterable filter(final Iterable unfiltered, final Class desiredType) {
      Preconditions.checkNotNull(unfiltered);
      Preconditions.checkNotNull(desiredType);
      return new FluentIterable() {
         public Iterator iterator() {
            return Iterators.filter(unfiltered.iterator(), desiredType);
         }
      };
   }

   public static boolean any(Iterable iterable, Predicate predicate) {
      return Iterators.any(iterable.iterator(), predicate);
   }

   public static boolean all(Iterable iterable, Predicate predicate) {
      return Iterators.all(iterable.iterator(), predicate);
   }

   public static Object find(Iterable iterable, Predicate predicate) {
      return Iterators.find(iterable.iterator(), predicate);
   }

   @Nullable
   public static Object find(Iterable iterable, Predicate predicate, @Nullable Object defaultValue) {
      return Iterators.find(iterable.iterator(), predicate, defaultValue);
   }

   public static Optional tryFind(Iterable iterable, Predicate predicate) {
      return Iterators.tryFind(iterable.iterator(), predicate);
   }

   public static int indexOf(Iterable iterable, Predicate predicate) {
      return Iterators.indexOf(iterable.iterator(), predicate);
   }

   public static Iterable transform(final Iterable fromIterable, final Function function) {
      Preconditions.checkNotNull(fromIterable);
      Preconditions.checkNotNull(function);
      return new FluentIterable() {
         public Iterator iterator() {
            return Iterators.transform(fromIterable.iterator(), function);
         }
      };
   }

   public static Object get(Iterable iterable, int position) {
      Preconditions.checkNotNull(iterable);
      return iterable instanceof List ? ((List)iterable).get(position) : Iterators.get(iterable.iterator(), position);
   }

   @Nullable
   public static Object get(Iterable iterable, int position, @Nullable Object defaultValue) {
      Preconditions.checkNotNull(iterable);
      Iterators.checkNonnegative(position);
      if (iterable instanceof List) {
         List list = Lists.cast(iterable);
         return position < list.size() ? list.get(position) : defaultValue;
      } else {
         Iterator iterator = iterable.iterator();
         Iterators.advance(iterator, position);
         return Iterators.getNext(iterator, defaultValue);
      }
   }

   @Nullable
   public static Object getFirst(Iterable iterable, @Nullable Object defaultValue) {
      return Iterators.getNext(iterable.iterator(), defaultValue);
   }

   public static Object getLast(Iterable iterable) {
      if (iterable instanceof List) {
         List list = (List)iterable;
         if (list.isEmpty()) {
            throw new NoSuchElementException();
         } else {
            return getLastInNonemptyList(list);
         }
      } else {
         return Iterators.getLast(iterable.iterator());
      }
   }

   @Nullable
   public static Object getLast(Iterable iterable, @Nullable Object defaultValue) {
      if (iterable instanceof Collection) {
         Collection c = Collections2.cast(iterable);
         if (c.isEmpty()) {
            return defaultValue;
         }

         if (iterable instanceof List) {
            return getLastInNonemptyList(Lists.cast(iterable));
         }
      }

      return Iterators.getLast(iterable.iterator(), defaultValue);
   }

   private static Object getLastInNonemptyList(List list) {
      return list.get(list.size() - 1);
   }

   public static Iterable skip(final Iterable iterable, final int numberToSkip) {
      Preconditions.checkNotNull(iterable);
      Preconditions.checkArgument(numberToSkip >= 0, "number to skip cannot be negative");
      if (iterable instanceof List) {
         final List list = (List)iterable;
         return new FluentIterable() {
            public Iterator iterator() {
               int toSkip = Math.min(list.size(), numberToSkip);
               return list.subList(toSkip, list.size()).iterator();
            }
         };
      } else {
         return new FluentIterable() {
            public Iterator iterator() {
               final Iterator iterator = iterable.iterator();
               Iterators.advance(iterator, numberToSkip);
               return new Iterator() {
                  boolean atStart = true;

                  public boolean hasNext() {
                     return iterator.hasNext();
                  }

                  public Object next() {
                     Object result = iterator.next();
                     this.atStart = false;
                     return result;
                  }

                  public void remove() {
                     CollectPreconditions.checkRemove(!this.atStart);
                     iterator.remove();
                  }
               };
            }
         };
      }
   }

   public static Iterable limit(final Iterable iterable, final int limitSize) {
      Preconditions.checkNotNull(iterable);
      Preconditions.checkArgument(limitSize >= 0, "limit is negative");
      return new FluentIterable() {
         public Iterator iterator() {
            return Iterators.limit(iterable.iterator(), limitSize);
         }
      };
   }

   public static Iterable consumingIterable(final Iterable iterable) {
      if (iterable instanceof Queue) {
         return new FluentIterable() {
            public Iterator iterator() {
               return new ConsumingQueueIterator((Queue)iterable);
            }

            public String toString() {
               return "Iterables.consumingIterable(...)";
            }
         };
      } else {
         Preconditions.checkNotNull(iterable);
         return new FluentIterable() {
            public Iterator iterator() {
               return Iterators.consumingIterator(iterable.iterator());
            }

            public String toString() {
               return "Iterables.consumingIterable(...)";
            }
         };
      }
   }

   public static boolean isEmpty(Iterable iterable) {
      if (iterable instanceof Collection) {
         return ((Collection)iterable).isEmpty();
      } else {
         return !iterable.iterator().hasNext();
      }
   }

   @Beta
   public static Iterable mergeSorted(final Iterable iterables, final Comparator comparator) {
      Preconditions.checkNotNull(iterables, "iterables");
      Preconditions.checkNotNull(comparator, "comparator");
      Iterable iterable = new FluentIterable() {
         public Iterator iterator() {
            return Iterators.mergeSorted(Iterables.transform(iterables, Iterables.toIterator()), comparator);
         }
      };
      return new UnmodifiableIterable(iterable);
   }

   static Function toIterator() {
      return new Function() {
         public Iterator apply(Iterable iterable) {
            return iterable.iterator();
         }
      };
   }

   private static final class UnmodifiableIterable extends FluentIterable {
      private final Iterable iterable;

      private UnmodifiableIterable(Iterable iterable) {
         this.iterable = iterable;
      }

      public Iterator iterator() {
         return Iterators.unmodifiableIterator(this.iterable.iterator());
      }

      public String toString() {
         return this.iterable.toString();
      }

      // $FF: synthetic method
      UnmodifiableIterable(Iterable x0, Object x1) {
         this(x0);
      }
   }
}
