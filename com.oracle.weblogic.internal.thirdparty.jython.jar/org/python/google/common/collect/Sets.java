package org.python.google.common.collect;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.common.base.Predicate;
import org.python.google.common.base.Predicates;
import org.python.google.common.math.IntMath;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible(
   emulated = true
)
public final class Sets {
   private Sets() {
   }

   @GwtCompatible(
      serializable = true
   )
   public static ImmutableSet immutableEnumSet(Enum anElement, Enum... otherElements) {
      return ImmutableEnumSet.asImmutable(EnumSet.of(anElement, otherElements));
   }

   @GwtCompatible(
      serializable = true
   )
   public static ImmutableSet immutableEnumSet(Iterable elements) {
      if (elements instanceof ImmutableEnumSet) {
         return (ImmutableEnumSet)elements;
      } else if (elements instanceof Collection) {
         Collection collection = (Collection)elements;
         return collection.isEmpty() ? ImmutableSet.of() : ImmutableEnumSet.asImmutable(EnumSet.copyOf(collection));
      } else {
         Iterator itr = elements.iterator();
         if (itr.hasNext()) {
            EnumSet enumSet = EnumSet.of((Enum)itr.next());
            Iterators.addAll(enumSet, itr);
            return ImmutableEnumSet.asImmutable(enumSet);
         } else {
            return ImmutableSet.of();
         }
      }
   }

   public static EnumSet newEnumSet(Iterable iterable, Class elementType) {
      EnumSet set = EnumSet.noneOf(elementType);
      Iterables.addAll(set, iterable);
      return set;
   }

   public static HashSet newHashSet() {
      return new HashSet();
   }

   public static HashSet newHashSet(Object... elements) {
      HashSet set = newHashSetWithExpectedSize(elements.length);
      Collections.addAll(set, elements);
      return set;
   }

   public static HashSet newHashSetWithExpectedSize(int expectedSize) {
      return new HashSet(Maps.capacity(expectedSize));
   }

   public static HashSet newHashSet(Iterable elements) {
      return elements instanceof Collection ? new HashSet(Collections2.cast(elements)) : newHashSet(elements.iterator());
   }

   public static HashSet newHashSet(Iterator elements) {
      HashSet set = newHashSet();
      Iterators.addAll(set, elements);
      return set;
   }

   public static Set newConcurrentHashSet() {
      return Collections.newSetFromMap(new ConcurrentHashMap());
   }

   public static Set newConcurrentHashSet(Iterable elements) {
      Set set = newConcurrentHashSet();
      Iterables.addAll(set, elements);
      return set;
   }

   public static LinkedHashSet newLinkedHashSet() {
      return new LinkedHashSet();
   }

   public static LinkedHashSet newLinkedHashSetWithExpectedSize(int expectedSize) {
      return new LinkedHashSet(Maps.capacity(expectedSize));
   }

   public static LinkedHashSet newLinkedHashSet(Iterable elements) {
      if (elements instanceof Collection) {
         return new LinkedHashSet(Collections2.cast(elements));
      } else {
         LinkedHashSet set = newLinkedHashSet();
         Iterables.addAll(set, elements);
         return set;
      }
   }

   public static TreeSet newTreeSet() {
      return new TreeSet();
   }

   public static TreeSet newTreeSet(Iterable elements) {
      TreeSet set = newTreeSet();
      Iterables.addAll(set, elements);
      return set;
   }

   public static TreeSet newTreeSet(Comparator comparator) {
      return new TreeSet((Comparator)Preconditions.checkNotNull(comparator));
   }

   public static Set newIdentityHashSet() {
      return Collections.newSetFromMap(Maps.newIdentityHashMap());
   }

   @GwtIncompatible
   public static CopyOnWriteArraySet newCopyOnWriteArraySet() {
      return new CopyOnWriteArraySet();
   }

   @GwtIncompatible
   public static CopyOnWriteArraySet newCopyOnWriteArraySet(Iterable elements) {
      Collection elementsCollection = elements instanceof Collection ? Collections2.cast(elements) : Lists.newArrayList(elements);
      return new CopyOnWriteArraySet((Collection)elementsCollection);
   }

   public static EnumSet complementOf(Collection collection) {
      if (collection instanceof EnumSet) {
         return EnumSet.complementOf((EnumSet)collection);
      } else {
         Preconditions.checkArgument(!collection.isEmpty(), "collection is empty; use the other version of this method");
         Class type = ((Enum)collection.iterator().next()).getDeclaringClass();
         return makeComplementByHand(collection, type);
      }
   }

   public static EnumSet complementOf(Collection collection, Class type) {
      Preconditions.checkNotNull(collection);
      return collection instanceof EnumSet ? EnumSet.complementOf((EnumSet)collection) : makeComplementByHand(collection, type);
   }

   private static EnumSet makeComplementByHand(Collection collection, Class type) {
      EnumSet result = EnumSet.allOf(type);
      result.removeAll(collection);
      return result;
   }

   /** @deprecated */
   @Deprecated
   public static Set newSetFromMap(Map map) {
      return Collections.newSetFromMap(map);
   }

   public static SetView union(final Set set1, final Set set2) {
      Preconditions.checkNotNull(set1, "set1");
      Preconditions.checkNotNull(set2, "set2");
      final Set set2minus1 = difference(set2, set1);
      return new SetView() {
         public int size() {
            return IntMath.saturatedAdd(set1.size(), set2minus1.size());
         }

         public boolean isEmpty() {
            return set1.isEmpty() && set2.isEmpty();
         }

         public UnmodifiableIterator iterator() {
            return Iterators.unmodifiableIterator(Iterators.concat(set1.iterator(), set2minus1.iterator()));
         }

         public boolean contains(Object object) {
            return set1.contains(object) || set2.contains(object);
         }

         public Set copyInto(Set set) {
            set.addAll(set1);
            set.addAll(set2);
            return set;
         }

         public ImmutableSet immutableCopy() {
            return (new ImmutableSet.Builder()).addAll((Iterable)set1).addAll((Iterable)set2).build();
         }
      };
   }

   public static SetView intersection(final Set set1, final Set set2) {
      Preconditions.checkNotNull(set1, "set1");
      Preconditions.checkNotNull(set2, "set2");
      final Predicate inSet2 = Predicates.in(set2);
      return new SetView() {
         public UnmodifiableIterator iterator() {
            return Iterators.filter(set1.iterator(), inSet2);
         }

         public int size() {
            return Iterators.size(this.iterator());
         }

         public boolean isEmpty() {
            return !this.iterator().hasNext();
         }

         public boolean contains(Object object) {
            return set1.contains(object) && set2.contains(object);
         }

         public boolean containsAll(Collection collection) {
            return set1.containsAll(collection) && set2.containsAll(collection);
         }
      };
   }

   public static SetView difference(final Set set1, final Set set2) {
      Preconditions.checkNotNull(set1, "set1");
      Preconditions.checkNotNull(set2, "set2");
      final Predicate notInSet2 = Predicates.not(Predicates.in(set2));
      return new SetView() {
         public UnmodifiableIterator iterator() {
            return Iterators.filter(set1.iterator(), notInSet2);
         }

         public int size() {
            return Iterators.size(this.iterator());
         }

         public boolean isEmpty() {
            return set2.containsAll(set1);
         }

         public boolean contains(Object element) {
            return set1.contains(element) && !set2.contains(element);
         }
      };
   }

   public static SetView symmetricDifference(final Set set1, final Set set2) {
      Preconditions.checkNotNull(set1, "set1");
      Preconditions.checkNotNull(set2, "set2");
      return new SetView() {
         public UnmodifiableIterator iterator() {
            final Iterator itr1 = set1.iterator();
            final Iterator itr2 = set2.iterator();
            return new AbstractIterator() {
               public Object computeNext() {
                  while(true) {
                     Object elem2;
                     if (itr1.hasNext()) {
                        elem2 = itr1.next();
                        if (set2.contains(elem2)) {
                           continue;
                        }

                        return elem2;
                     }

                     do {
                        if (!itr2.hasNext()) {
                           return this.endOfData();
                        }

                        elem2 = itr2.next();
                     } while(set1.contains(elem2));

                     return elem2;
                  }
               }
            };
         }

         public int size() {
            return Iterators.size(this.iterator());
         }

         public boolean isEmpty() {
            return set1.equals(set2);
         }

         public boolean contains(Object element) {
            return set1.contains(element) ^ set2.contains(element);
         }
      };
   }

   public static Set filter(Set unfiltered, Predicate predicate) {
      if (unfiltered instanceof SortedSet) {
         return filter((SortedSet)unfiltered, predicate);
      } else if (unfiltered instanceof FilteredSet) {
         FilteredSet filtered = (FilteredSet)unfiltered;
         Predicate combinedPredicate = Predicates.and(filtered.predicate, predicate);
         return new FilteredSet((Set)filtered.unfiltered, combinedPredicate);
      } else {
         return new FilteredSet((Set)Preconditions.checkNotNull(unfiltered), (Predicate)Preconditions.checkNotNull(predicate));
      }
   }

   public static SortedSet filter(SortedSet unfiltered, Predicate predicate) {
      if (unfiltered instanceof FilteredSet) {
         FilteredSet filtered = (FilteredSet)unfiltered;
         Predicate combinedPredicate = Predicates.and(filtered.predicate, predicate);
         return new FilteredSortedSet((SortedSet)filtered.unfiltered, combinedPredicate);
      } else {
         return new FilteredSortedSet((SortedSet)Preconditions.checkNotNull(unfiltered), (Predicate)Preconditions.checkNotNull(predicate));
      }
   }

   @GwtIncompatible
   public static NavigableSet filter(NavigableSet unfiltered, Predicate predicate) {
      if (unfiltered instanceof FilteredSet) {
         FilteredSet filtered = (FilteredSet)unfiltered;
         Predicate combinedPredicate = Predicates.and(filtered.predicate, predicate);
         return new FilteredNavigableSet((NavigableSet)filtered.unfiltered, combinedPredicate);
      } else {
         return new FilteredNavigableSet((NavigableSet)Preconditions.checkNotNull(unfiltered), (Predicate)Preconditions.checkNotNull(predicate));
      }
   }

   public static Set cartesianProduct(List sets) {
      return Sets.CartesianSet.create(sets);
   }

   public static Set cartesianProduct(Set... sets) {
      return cartesianProduct(Arrays.asList(sets));
   }

   @GwtCompatible(
      serializable = false
   )
   public static Set powerSet(Set set) {
      return new PowerSet(set);
   }

   static int hashCodeImpl(Set s) {
      int hashCode = 0;

      for(Iterator var2 = s.iterator(); var2.hasNext(); hashCode = ~(~hashCode)) {
         Object o = var2.next();
         hashCode += o != null ? o.hashCode() : 0;
      }

      return hashCode;
   }

   static boolean equalsImpl(Set s, @Nullable Object object) {
      if (s == object) {
         return true;
      } else if (object instanceof Set) {
         Set o = (Set)object;

         try {
            return s.size() == o.size() && s.containsAll(o);
         } catch (NullPointerException var4) {
            return false;
         } catch (ClassCastException var5) {
            return false;
         }
      } else {
         return false;
      }
   }

   public static NavigableSet unmodifiableNavigableSet(NavigableSet set) {
      return (NavigableSet)(!(set instanceof ImmutableSortedSet) && !(set instanceof UnmodifiableNavigableSet) ? new UnmodifiableNavigableSet(set) : set);
   }

   @GwtIncompatible
   public static NavigableSet synchronizedNavigableSet(NavigableSet navigableSet) {
      return Synchronized.navigableSet(navigableSet);
   }

   static boolean removeAllImpl(Set set, Iterator iterator) {
      boolean changed;
      for(changed = false; iterator.hasNext(); changed |= set.remove(iterator.next())) {
      }

      return changed;
   }

   static boolean removeAllImpl(Set set, Collection collection) {
      Preconditions.checkNotNull(collection);
      if (collection instanceof Multiset) {
         collection = ((Multiset)collection).elementSet();
      }

      return collection instanceof Set && ((Collection)collection).size() > set.size() ? Iterators.removeAll(set.iterator(), (Collection)collection) : removeAllImpl(set, ((Collection)collection).iterator());
   }

   @Beta
   @GwtIncompatible
   public static NavigableSet subSet(NavigableSet set, Range range) {
      if (set.comparator() != null && set.comparator() != Ordering.natural() && range.hasLowerBound() && range.hasUpperBound()) {
         Preconditions.checkArgument(set.comparator().compare(range.lowerEndpoint(), range.upperEndpoint()) <= 0, "set is using a custom comparator which is inconsistent with the natural ordering.");
      }

      if (range.hasLowerBound() && range.hasUpperBound()) {
         return set.subSet(range.lowerEndpoint(), range.lowerBoundType() == BoundType.CLOSED, range.upperEndpoint(), range.upperBoundType() == BoundType.CLOSED);
      } else if (range.hasLowerBound()) {
         return set.tailSet(range.lowerEndpoint(), range.lowerBoundType() == BoundType.CLOSED);
      } else {
         return range.hasUpperBound() ? set.headSet(range.upperEndpoint(), range.upperBoundType() == BoundType.CLOSED) : (NavigableSet)Preconditions.checkNotNull(set);
      }
   }

   @GwtIncompatible
   static class DescendingSet extends ForwardingNavigableSet {
      private final NavigableSet forward;

      DescendingSet(NavigableSet forward) {
         this.forward = forward;
      }

      protected NavigableSet delegate() {
         return this.forward;
      }

      public Object lower(Object e) {
         return this.forward.higher(e);
      }

      public Object floor(Object e) {
         return this.forward.ceiling(e);
      }

      public Object ceiling(Object e) {
         return this.forward.floor(e);
      }

      public Object higher(Object e) {
         return this.forward.lower(e);
      }

      public Object pollFirst() {
         return this.forward.pollLast();
      }

      public Object pollLast() {
         return this.forward.pollFirst();
      }

      public NavigableSet descendingSet() {
         return this.forward;
      }

      public Iterator descendingIterator() {
         return this.forward.iterator();
      }

      public NavigableSet subSet(Object fromElement, boolean fromInclusive, Object toElement, boolean toInclusive) {
         return this.forward.subSet(toElement, toInclusive, fromElement, fromInclusive).descendingSet();
      }

      public NavigableSet headSet(Object toElement, boolean inclusive) {
         return this.forward.tailSet(toElement, inclusive).descendingSet();
      }

      public NavigableSet tailSet(Object fromElement, boolean inclusive) {
         return this.forward.headSet(fromElement, inclusive).descendingSet();
      }

      public Comparator comparator() {
         Comparator forwardComparator = this.forward.comparator();
         return forwardComparator == null ? Ordering.natural().reverse() : reverse(forwardComparator);
      }

      private static Ordering reverse(Comparator forward) {
         return Ordering.from(forward).reverse();
      }

      public Object first() {
         return this.forward.last();
      }

      public SortedSet headSet(Object toElement) {
         return this.standardHeadSet(toElement);
      }

      public Object last() {
         return this.forward.first();
      }

      public SortedSet subSet(Object fromElement, Object toElement) {
         return this.standardSubSet(fromElement, toElement);
      }

      public SortedSet tailSet(Object fromElement) {
         return this.standardTailSet(fromElement);
      }

      public Iterator iterator() {
         return this.forward.descendingIterator();
      }

      public Object[] toArray() {
         return this.standardToArray();
      }

      public Object[] toArray(Object[] array) {
         return this.standardToArray(array);
      }

      public String toString() {
         return this.standardToString();
      }
   }

   static final class UnmodifiableNavigableSet extends ForwardingSortedSet implements NavigableSet, Serializable {
      private final NavigableSet delegate;
      private transient UnmodifiableNavigableSet descendingSet;
      private static final long serialVersionUID = 0L;

      UnmodifiableNavigableSet(NavigableSet delegate) {
         this.delegate = (NavigableSet)Preconditions.checkNotNull(delegate);
      }

      protected SortedSet delegate() {
         return Collections.unmodifiableSortedSet(this.delegate);
      }

      public Object lower(Object e) {
         return this.delegate.lower(e);
      }

      public Object floor(Object e) {
         return this.delegate.floor(e);
      }

      public Object ceiling(Object e) {
         return this.delegate.ceiling(e);
      }

      public Object higher(Object e) {
         return this.delegate.higher(e);
      }

      public Object pollFirst() {
         throw new UnsupportedOperationException();
      }

      public Object pollLast() {
         throw new UnsupportedOperationException();
      }

      public NavigableSet descendingSet() {
         UnmodifiableNavigableSet result = this.descendingSet;
         if (result == null) {
            result = this.descendingSet = new UnmodifiableNavigableSet(this.delegate.descendingSet());
            result.descendingSet = this;
         }

         return result;
      }

      public Iterator descendingIterator() {
         return Iterators.unmodifiableIterator(this.delegate.descendingIterator());
      }

      public NavigableSet subSet(Object fromElement, boolean fromInclusive, Object toElement, boolean toInclusive) {
         return Sets.unmodifiableNavigableSet(this.delegate.subSet(fromElement, fromInclusive, toElement, toInclusive));
      }

      public NavigableSet headSet(Object toElement, boolean inclusive) {
         return Sets.unmodifiableNavigableSet(this.delegate.headSet(toElement, inclusive));
      }

      public NavigableSet tailSet(Object fromElement, boolean inclusive) {
         return Sets.unmodifiableNavigableSet(this.delegate.tailSet(fromElement, inclusive));
      }
   }

   private static final class PowerSet extends AbstractSet {
      final ImmutableMap inputSet;

      PowerSet(Set input) {
         this.inputSet = Maps.indexMap(input);
         Preconditions.checkArgument(this.inputSet.size() <= 30, "Too many elements to create power set: %s > 30", this.inputSet.size());
      }

      public int size() {
         return 1 << this.inputSet.size();
      }

      public boolean isEmpty() {
         return false;
      }

      public Iterator iterator() {
         return new AbstractIndexedListIterator(this.size()) {
            protected Set get(int setBits) {
               return new SubSet(PowerSet.this.inputSet, setBits);
            }
         };
      }

      public boolean contains(@Nullable Object obj) {
         if (obj instanceof Set) {
            Set set = (Set)obj;
            return this.inputSet.keySet().containsAll(set);
         } else {
            return false;
         }
      }

      public boolean equals(@Nullable Object obj) {
         if (obj instanceof PowerSet) {
            PowerSet that = (PowerSet)obj;
            return this.inputSet.equals(that.inputSet);
         } else {
            return super.equals(obj);
         }
      }

      public int hashCode() {
         return this.inputSet.keySet().hashCode() << this.inputSet.size() - 1;
      }

      public String toString() {
         return "powerSet(" + this.inputSet + ")";
      }
   }

   private static final class SubSet extends AbstractSet {
      private final ImmutableMap inputSet;
      private final int mask;

      SubSet(ImmutableMap inputSet, int mask) {
         this.inputSet = inputSet;
         this.mask = mask;
      }

      public Iterator iterator() {
         return new UnmodifiableIterator() {
            final ImmutableList elements;
            int remainingSetBits;

            {
               this.elements = SubSet.this.inputSet.keySet().asList();
               this.remainingSetBits = SubSet.this.mask;
            }

            public boolean hasNext() {
               return this.remainingSetBits != 0;
            }

            public Object next() {
               int index = Integer.numberOfTrailingZeros(this.remainingSetBits);
               if (index == 32) {
                  throw new NoSuchElementException();
               } else {
                  this.remainingSetBits &= ~(1 << index);
                  return this.elements.get(index);
               }
            }
         };
      }

      public int size() {
         return Integer.bitCount(this.mask);
      }

      public boolean contains(@Nullable Object o) {
         Integer index = (Integer)this.inputSet.get(o);
         return index != null && (this.mask & 1 << index) != 0;
      }
   }

   private static final class CartesianSet extends ForwardingCollection implements Set {
      private final transient ImmutableList axes;
      private final transient CartesianList delegate;

      static Set create(List sets) {
         ImmutableList.Builder axesBuilder = new ImmutableList.Builder(sets.size());
         Iterator var2 = sets.iterator();

         while(var2.hasNext()) {
            Set set = (Set)var2.next();
            ImmutableSet copy = ImmutableSet.copyOf((Collection)set);
            if (copy.isEmpty()) {
               return ImmutableSet.of();
            }

            axesBuilder.add((Object)copy);
         }

         final ImmutableList axes = axesBuilder.build();
         ImmutableList listAxes = new ImmutableList() {
            public int size() {
               return axes.size();
            }

            public List get(int index) {
               return ((ImmutableSet)axes.get(index)).asList();
            }

            boolean isPartialView() {
               return true;
            }
         };
         return new CartesianSet(axes, new CartesianList(listAxes));
      }

      private CartesianSet(ImmutableList axes, CartesianList delegate) {
         this.axes = axes;
         this.delegate = delegate;
      }

      protected Collection delegate() {
         return this.delegate;
      }

      public boolean equals(@Nullable Object object) {
         if (object instanceof CartesianSet) {
            CartesianSet that = (CartesianSet)object;
            return this.axes.equals(that.axes);
         } else {
            return super.equals(object);
         }
      }

      public int hashCode() {
         int adjust = this.size() - 1;

         int hash;
         for(hash = 0; hash < this.axes.size(); ++hash) {
            adjust *= 31;
            adjust = ~(~adjust);
         }

         hash = 1;

         for(UnmodifiableIterator var3 = this.axes.iterator(); var3.hasNext(); hash = ~(~hash)) {
            Set axis = (Set)var3.next();
            hash = 31 * hash + this.size() / axis.size() * axis.hashCode();
         }

         hash += adjust;
         return ~(~hash);
      }
   }

   @GwtIncompatible
   private static class FilteredNavigableSet extends FilteredSortedSet implements NavigableSet {
      FilteredNavigableSet(NavigableSet unfiltered, Predicate predicate) {
         super(unfiltered, predicate);
      }

      NavigableSet unfiltered() {
         return (NavigableSet)this.unfiltered;
      }

      @Nullable
      public Object lower(Object e) {
         return Iterators.getNext(this.headSet(e, false).descendingIterator(), (Object)null);
      }

      @Nullable
      public Object floor(Object e) {
         return Iterators.getNext(this.headSet(e, true).descendingIterator(), (Object)null);
      }

      public Object ceiling(Object e) {
         return Iterables.getFirst(this.tailSet(e, true), (Object)null);
      }

      public Object higher(Object e) {
         return Iterables.getFirst(this.tailSet(e, false), (Object)null);
      }

      public Object pollFirst() {
         return Iterables.removeFirstMatching(this.unfiltered(), this.predicate);
      }

      public Object pollLast() {
         return Iterables.removeFirstMatching(this.unfiltered().descendingSet(), this.predicate);
      }

      public NavigableSet descendingSet() {
         return Sets.filter(this.unfiltered().descendingSet(), this.predicate);
      }

      public Iterator descendingIterator() {
         return Iterators.filter(this.unfiltered().descendingIterator(), this.predicate);
      }

      public Object last() {
         return this.descendingIterator().next();
      }

      public NavigableSet subSet(Object fromElement, boolean fromInclusive, Object toElement, boolean toInclusive) {
         return Sets.filter(this.unfiltered().subSet(fromElement, fromInclusive, toElement, toInclusive), this.predicate);
      }

      public NavigableSet headSet(Object toElement, boolean inclusive) {
         return Sets.filter(this.unfiltered().headSet(toElement, inclusive), this.predicate);
      }

      public NavigableSet tailSet(Object fromElement, boolean inclusive) {
         return Sets.filter(this.unfiltered().tailSet(fromElement, inclusive), this.predicate);
      }
   }

   private static class FilteredSortedSet extends FilteredSet implements SortedSet {
      FilteredSortedSet(SortedSet unfiltered, Predicate predicate) {
         super(unfiltered, predicate);
      }

      public Comparator comparator() {
         return ((SortedSet)this.unfiltered).comparator();
      }

      public SortedSet subSet(Object fromElement, Object toElement) {
         return new FilteredSortedSet(((SortedSet)this.unfiltered).subSet(fromElement, toElement), this.predicate);
      }

      public SortedSet headSet(Object toElement) {
         return new FilteredSortedSet(((SortedSet)this.unfiltered).headSet(toElement), this.predicate);
      }

      public SortedSet tailSet(Object fromElement) {
         return new FilteredSortedSet(((SortedSet)this.unfiltered).tailSet(fromElement), this.predicate);
      }

      public Object first() {
         return this.iterator().next();
      }

      public Object last() {
         SortedSet sortedUnfiltered = (SortedSet)this.unfiltered;

         while(true) {
            Object element = sortedUnfiltered.last();
            if (this.predicate.apply(element)) {
               return element;
            }

            sortedUnfiltered = sortedUnfiltered.headSet(element);
         }
      }
   }

   private static class FilteredSet extends Collections2.FilteredCollection implements Set {
      FilteredSet(Set unfiltered, Predicate predicate) {
         super(unfiltered, predicate);
      }

      public boolean equals(@Nullable Object object) {
         return Sets.equalsImpl(this, object);
      }

      public int hashCode() {
         return Sets.hashCodeImpl(this);
      }
   }

   public abstract static class SetView extends AbstractSet {
      private SetView() {
      }

      public ImmutableSet immutableCopy() {
         return ImmutableSet.copyOf((Collection)this);
      }

      @CanIgnoreReturnValue
      public Set copyInto(Set set) {
         set.addAll(this);
         return set;
      }

      /** @deprecated */
      @Deprecated
      @CanIgnoreReturnValue
      public final boolean add(Object e) {
         throw new UnsupportedOperationException();
      }

      /** @deprecated */
      @Deprecated
      @CanIgnoreReturnValue
      public final boolean remove(Object object) {
         throw new UnsupportedOperationException();
      }

      /** @deprecated */
      @Deprecated
      @CanIgnoreReturnValue
      public final boolean addAll(Collection newElements) {
         throw new UnsupportedOperationException();
      }

      /** @deprecated */
      @Deprecated
      @CanIgnoreReturnValue
      public final boolean removeAll(Collection oldElements) {
         throw new UnsupportedOperationException();
      }

      /** @deprecated */
      @Deprecated
      @CanIgnoreReturnValue
      public final boolean retainAll(Collection elementsToKeep) {
         throw new UnsupportedOperationException();
      }

      /** @deprecated */
      @Deprecated
      public final void clear() {
         throw new UnsupportedOperationException();
      }

      public abstract UnmodifiableIterator iterator();

      // $FF: synthetic method
      SetView(Object x0) {
         this();
      }
   }

   abstract static class ImprovedAbstractSet extends AbstractSet {
      public boolean removeAll(Collection c) {
         return Sets.removeAllImpl(this, (Collection)c);
      }

      public boolean retainAll(Collection c) {
         return super.retainAll((Collection)Preconditions.checkNotNull(c));
      }
   }
}
