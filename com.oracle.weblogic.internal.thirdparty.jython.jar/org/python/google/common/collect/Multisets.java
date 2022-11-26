package org.python.google.common.collect;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Objects;
import org.python.google.common.base.Preconditions;
import org.python.google.common.base.Predicate;
import org.python.google.common.base.Predicates;
import org.python.google.common.math.IntMath;
import org.python.google.common.primitives.Ints;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
public final class Multisets {
   private Multisets() {
   }

   public static Multiset unmodifiableMultiset(Multiset multiset) {
      return (Multiset)(!(multiset instanceof UnmodifiableMultiset) && !(multiset instanceof ImmutableMultiset) ? new UnmodifiableMultiset((Multiset)Preconditions.checkNotNull(multiset)) : multiset);
   }

   /** @deprecated */
   @Deprecated
   public static Multiset unmodifiableMultiset(ImmutableMultiset multiset) {
      return (Multiset)Preconditions.checkNotNull(multiset);
   }

   @Beta
   public static SortedMultiset unmodifiableSortedMultiset(SortedMultiset sortedMultiset) {
      return new UnmodifiableSortedMultiset((SortedMultiset)Preconditions.checkNotNull(sortedMultiset));
   }

   public static Multiset.Entry immutableEntry(@Nullable Object e, int n) {
      return new ImmutableEntry(e, n);
   }

   @Beta
   public static Multiset filter(Multiset unfiltered, Predicate predicate) {
      if (unfiltered instanceof FilteredMultiset) {
         FilteredMultiset filtered = (FilteredMultiset)unfiltered;
         Predicate combinedPredicate = Predicates.and(filtered.predicate, predicate);
         return new FilteredMultiset(filtered.unfiltered, combinedPredicate);
      } else {
         return new FilteredMultiset(unfiltered, predicate);
      }
   }

   static int inferDistinctElements(Iterable elements) {
      return elements instanceof Multiset ? ((Multiset)elements).elementSet().size() : 11;
   }

   @Beta
   public static Multiset union(final Multiset multiset1, final Multiset multiset2) {
      Preconditions.checkNotNull(multiset1);
      Preconditions.checkNotNull(multiset2);
      return new AbstractMultiset() {
         public boolean contains(@Nullable Object element) {
            return multiset1.contains(element) || multiset2.contains(element);
         }

         public boolean isEmpty() {
            return multiset1.isEmpty() && multiset2.isEmpty();
         }

         public int count(Object element) {
            return Math.max(multiset1.count(element), multiset2.count(element));
         }

         Set createElementSet() {
            return Sets.union(multiset1.elementSet(), multiset2.elementSet());
         }

         Iterator entryIterator() {
            final Iterator iterator1 = multiset1.entrySet().iterator();
            final Iterator iterator2 = multiset2.entrySet().iterator();
            return new AbstractIterator() {
               protected Multiset.Entry computeNext() {
                  Multiset.Entry entry2;
                  Object element;
                  if (iterator1.hasNext()) {
                     entry2 = (Multiset.Entry)iterator1.next();
                     element = entry2.getElement();
                     int count = Math.max(entry2.getCount(), multiset2.count(element));
                     return Multisets.immutableEntry(element, count);
                  } else {
                     do {
                        if (!iterator2.hasNext()) {
                           return (Multiset.Entry)this.endOfData();
                        }

                        entry2 = (Multiset.Entry)iterator2.next();
                        element = entry2.getElement();
                     } while(multiset1.contains(element));

                     return Multisets.immutableEntry(element, entry2.getCount());
                  }
               }
            };
         }

         int distinctElements() {
            return this.elementSet().size();
         }
      };
   }

   public static Multiset intersection(final Multiset multiset1, final Multiset multiset2) {
      Preconditions.checkNotNull(multiset1);
      Preconditions.checkNotNull(multiset2);
      return new AbstractMultiset() {
         public int count(Object element) {
            int count1 = multiset1.count(element);
            return count1 == 0 ? 0 : Math.min(count1, multiset2.count(element));
         }

         Set createElementSet() {
            return Sets.intersection(multiset1.elementSet(), multiset2.elementSet());
         }

         Iterator entryIterator() {
            final Iterator iterator1 = multiset1.entrySet().iterator();
            return new AbstractIterator() {
               protected Multiset.Entry computeNext() {
                  while(true) {
                     if (iterator1.hasNext()) {
                        Multiset.Entry entry1 = (Multiset.Entry)iterator1.next();
                        Object element = entry1.getElement();
                        int count = Math.min(entry1.getCount(), multiset2.count(element));
                        if (count <= 0) {
                           continue;
                        }

                        return Multisets.immutableEntry(element, count);
                     }

                     return (Multiset.Entry)this.endOfData();
                  }
               }
            };
         }

         int distinctElements() {
            return this.elementSet().size();
         }
      };
   }

   @Beta
   public static Multiset sum(final Multiset multiset1, final Multiset multiset2) {
      Preconditions.checkNotNull(multiset1);
      Preconditions.checkNotNull(multiset2);
      return new AbstractMultiset() {
         public boolean contains(@Nullable Object element) {
            return multiset1.contains(element) || multiset2.contains(element);
         }

         public boolean isEmpty() {
            return multiset1.isEmpty() && multiset2.isEmpty();
         }

         public int size() {
            return IntMath.saturatedAdd(multiset1.size(), multiset2.size());
         }

         public int count(Object element) {
            return multiset1.count(element) + multiset2.count(element);
         }

         Set createElementSet() {
            return Sets.union(multiset1.elementSet(), multiset2.elementSet());
         }

         Iterator entryIterator() {
            final Iterator iterator1 = multiset1.entrySet().iterator();
            final Iterator iterator2 = multiset2.entrySet().iterator();
            return new AbstractIterator() {
               protected Multiset.Entry computeNext() {
                  Multiset.Entry entry2;
                  Object element;
                  if (iterator1.hasNext()) {
                     entry2 = (Multiset.Entry)iterator1.next();
                     element = entry2.getElement();
                     int count = entry2.getCount() + multiset2.count(element);
                     return Multisets.immutableEntry(element, count);
                  } else {
                     do {
                        if (!iterator2.hasNext()) {
                           return (Multiset.Entry)this.endOfData();
                        }

                        entry2 = (Multiset.Entry)iterator2.next();
                        element = entry2.getElement();
                     } while(multiset1.contains(element));

                     return Multisets.immutableEntry(element, entry2.getCount());
                  }
               }
            };
         }

         int distinctElements() {
            return this.elementSet().size();
         }
      };
   }

   @Beta
   public static Multiset difference(final Multiset multiset1, final Multiset multiset2) {
      Preconditions.checkNotNull(multiset1);
      Preconditions.checkNotNull(multiset2);
      return new AbstractMultiset() {
         public int count(@Nullable Object element) {
            int count1 = multiset1.count(element);
            return count1 == 0 ? 0 : Math.max(0, count1 - multiset2.count(element));
         }

         Iterator entryIterator() {
            final Iterator iterator1 = multiset1.entrySet().iterator();
            return new AbstractIterator() {
               protected Multiset.Entry computeNext() {
                  while(true) {
                     if (iterator1.hasNext()) {
                        Multiset.Entry entry1 = (Multiset.Entry)iterator1.next();
                        Object element = entry1.getElement();
                        int count = entry1.getCount() - multiset2.count(element);
                        if (count <= 0) {
                           continue;
                        }

                        return Multisets.immutableEntry(element, count);
                     }

                     return (Multiset.Entry)this.endOfData();
                  }
               }
            };
         }

         int distinctElements() {
            return Iterators.size(this.entryIterator());
         }
      };
   }

   @CanIgnoreReturnValue
   public static boolean containsOccurrences(Multiset superMultiset, Multiset subMultiset) {
      Preconditions.checkNotNull(superMultiset);
      Preconditions.checkNotNull(subMultiset);
      Iterator var2 = subMultiset.entrySet().iterator();

      Multiset.Entry entry;
      int superCount;
      do {
         if (!var2.hasNext()) {
            return true;
         }

         entry = (Multiset.Entry)var2.next();
         superCount = superMultiset.count(entry.getElement());
      } while(superCount >= entry.getCount());

      return false;
   }

   @CanIgnoreReturnValue
   public static boolean retainOccurrences(Multiset multisetToModify, Multiset multisetToRetain) {
      return retainOccurrencesImpl(multisetToModify, multisetToRetain);
   }

   private static boolean retainOccurrencesImpl(Multiset multisetToModify, Multiset occurrencesToRetain) {
      Preconditions.checkNotNull(multisetToModify);
      Preconditions.checkNotNull(occurrencesToRetain);
      Iterator entryIterator = multisetToModify.entrySet().iterator();
      boolean changed = false;

      while(entryIterator.hasNext()) {
         Multiset.Entry entry = (Multiset.Entry)entryIterator.next();
         int retainCount = occurrencesToRetain.count(entry.getElement());
         if (retainCount == 0) {
            entryIterator.remove();
            changed = true;
         } else if (retainCount < entry.getCount()) {
            multisetToModify.setCount(entry.getElement(), retainCount);
            changed = true;
         }
      }

      return changed;
   }

   @CanIgnoreReturnValue
   public static boolean removeOccurrences(Multiset multisetToModify, Iterable occurrencesToRemove) {
      if (occurrencesToRemove instanceof Multiset) {
         return removeOccurrences(multisetToModify, (Multiset)occurrencesToRemove);
      } else {
         Preconditions.checkNotNull(multisetToModify);
         Preconditions.checkNotNull(occurrencesToRemove);
         boolean changed = false;

         Object o;
         for(Iterator var3 = occurrencesToRemove.iterator(); var3.hasNext(); changed |= multisetToModify.remove(o)) {
            o = var3.next();
         }

         return changed;
      }
   }

   @CanIgnoreReturnValue
   public static boolean removeOccurrences(Multiset multisetToModify, Multiset occurrencesToRemove) {
      Preconditions.checkNotNull(multisetToModify);
      Preconditions.checkNotNull(occurrencesToRemove);
      boolean changed = false;
      Iterator entryIterator = multisetToModify.entrySet().iterator();

      while(entryIterator.hasNext()) {
         Multiset.Entry entry = (Multiset.Entry)entryIterator.next();
         int removeCount = occurrencesToRemove.count(entry.getElement());
         if (removeCount >= entry.getCount()) {
            entryIterator.remove();
            changed = true;
         } else if (removeCount > 0) {
            multisetToModify.remove(entry.getElement(), removeCount);
            changed = true;
         }
      }

      return changed;
   }

   static boolean equalsImpl(Multiset multiset, @Nullable Object object) {
      if (object == multiset) {
         return true;
      } else if (object instanceof Multiset) {
         Multiset that = (Multiset)object;
         if (multiset.size() == that.size() && multiset.entrySet().size() == that.entrySet().size()) {
            Iterator var3 = that.entrySet().iterator();

            Multiset.Entry entry;
            do {
               if (!var3.hasNext()) {
                  return true;
               }

               entry = (Multiset.Entry)var3.next();
            } while(multiset.count(entry.getElement()) == entry.getCount());

            return false;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   static boolean addAllImpl(Multiset self, Collection elements) {
      if (elements.isEmpty()) {
         return false;
      } else {
         if (elements instanceof Multiset) {
            Multiset that = cast(elements);
            Iterator var3 = that.entrySet().iterator();

            while(var3.hasNext()) {
               Multiset.Entry entry = (Multiset.Entry)var3.next();
               self.add(entry.getElement(), entry.getCount());
            }
         } else {
            Iterators.addAll(self, elements.iterator());
         }

         return true;
      }
   }

   static boolean removeAllImpl(Multiset self, Collection elementsToRemove) {
      Collection collection = elementsToRemove instanceof Multiset ? ((Multiset)elementsToRemove).elementSet() : elementsToRemove;
      return self.elementSet().removeAll((Collection)collection);
   }

   static boolean retainAllImpl(Multiset self, Collection elementsToRetain) {
      Preconditions.checkNotNull(elementsToRetain);
      Collection collection = elementsToRetain instanceof Multiset ? ((Multiset)elementsToRetain).elementSet() : elementsToRetain;
      return self.elementSet().retainAll((Collection)collection);
   }

   static int setCountImpl(Multiset self, Object element, int count) {
      CollectPreconditions.checkNonnegative(count, "count");
      int oldCount = self.count(element);
      int delta = count - oldCount;
      if (delta > 0) {
         self.add(element, delta);
      } else if (delta < 0) {
         self.remove(element, -delta);
      }

      return oldCount;
   }

   static boolean setCountImpl(Multiset self, Object element, int oldCount, int newCount) {
      CollectPreconditions.checkNonnegative(oldCount, "oldCount");
      CollectPreconditions.checkNonnegative(newCount, "newCount");
      if (self.count(element) == oldCount) {
         self.setCount(element, newCount);
         return true;
      } else {
         return false;
      }
   }

   static Iterator iteratorImpl(Multiset multiset) {
      return new MultisetIteratorImpl(multiset, multiset.entrySet().iterator());
   }

   static int sizeImpl(Multiset multiset) {
      long size = 0L;

      Multiset.Entry entry;
      for(Iterator var3 = multiset.entrySet().iterator(); var3.hasNext(); size += (long)entry.getCount()) {
         entry = (Multiset.Entry)var3.next();
      }

      return Ints.saturatedCast(size);
   }

   static Multiset cast(Iterable iterable) {
      return (Multiset)iterable;
   }

   @Beta
   public static ImmutableMultiset copyHighestCountFirst(Multiset multiset) {
      Multiset.Entry[] entries = (Multiset.Entry[])((Multiset.Entry[])multiset.entrySet().toArray(new Multiset.Entry[0]));
      Arrays.sort(entries, Multisets.DecreasingCount.INSTANCE);
      return ImmutableMultiset.copyFromEntries(Arrays.asList(entries));
   }

   private static final class DecreasingCount implements Comparator {
      static final DecreasingCount INSTANCE = new DecreasingCount();

      public int compare(Multiset.Entry entry1, Multiset.Entry entry2) {
         return entry2.getCount() - entry1.getCount();
      }
   }

   static final class MultisetIteratorImpl implements Iterator {
      private final Multiset multiset;
      private final Iterator entryIterator;
      private Multiset.Entry currentEntry;
      private int laterCount;
      private int totalCount;
      private boolean canRemove;

      MultisetIteratorImpl(Multiset multiset, Iterator entryIterator) {
         this.multiset = multiset;
         this.entryIterator = entryIterator;
      }

      public boolean hasNext() {
         return this.laterCount > 0 || this.entryIterator.hasNext();
      }

      public Object next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            if (this.laterCount == 0) {
               this.currentEntry = (Multiset.Entry)this.entryIterator.next();
               this.totalCount = this.laterCount = this.currentEntry.getCount();
            }

            --this.laterCount;
            this.canRemove = true;
            return this.currentEntry.getElement();
         }
      }

      public void remove() {
         CollectPreconditions.checkRemove(this.canRemove);
         if (this.totalCount == 1) {
            this.entryIterator.remove();
         } else {
            this.multiset.remove(this.currentEntry.getElement());
         }

         --this.totalCount;
         this.canRemove = false;
      }
   }

   abstract static class EntrySet extends Sets.ImprovedAbstractSet {
      abstract Multiset multiset();

      public boolean contains(@Nullable Object o) {
         if (o instanceof Multiset.Entry) {
            Multiset.Entry entry = (Multiset.Entry)o;
            if (entry.getCount() <= 0) {
               return false;
            } else {
               int count = this.multiset().count(entry.getElement());
               return count == entry.getCount();
            }
         } else {
            return false;
         }
      }

      public boolean remove(Object object) {
         if (object instanceof Multiset.Entry) {
            Multiset.Entry entry = (Multiset.Entry)object;
            Object element = entry.getElement();
            int entryCount = entry.getCount();
            if (entryCount != 0) {
               Multiset multiset = this.multiset();
               return multiset.setCount(element, entryCount, 0);
            }
         }

         return false;
      }

      public void clear() {
         this.multiset().clear();
      }
   }

   abstract static class ElementSet extends Sets.ImprovedAbstractSet {
      abstract Multiset multiset();

      public void clear() {
         this.multiset().clear();
      }

      public boolean contains(Object o) {
         return this.multiset().contains(o);
      }

      public boolean containsAll(Collection c) {
         return this.multiset().containsAll(c);
      }

      public boolean isEmpty() {
         return this.multiset().isEmpty();
      }

      public Iterator iterator() {
         return new TransformedIterator(this.multiset().entrySet().iterator()) {
            Object transform(Multiset.Entry entry) {
               return entry.getElement();
            }
         };
      }

      public boolean remove(Object o) {
         return this.multiset().remove(o, Integer.MAX_VALUE) > 0;
      }

      public int size() {
         return this.multiset().entrySet().size();
      }
   }

   abstract static class AbstractEntry implements Multiset.Entry {
      public boolean equals(@Nullable Object object) {
         if (!(object instanceof Multiset.Entry)) {
            return false;
         } else {
            Multiset.Entry that = (Multiset.Entry)object;
            return this.getCount() == that.getCount() && Objects.equal(this.getElement(), that.getElement());
         }
      }

      public int hashCode() {
         Object e = this.getElement();
         return (e == null ? 0 : e.hashCode()) ^ this.getCount();
      }

      public String toString() {
         String text = String.valueOf(this.getElement());
         int n = this.getCount();
         return n == 1 ? text : text + " x " + n;
      }
   }

   private static final class FilteredMultiset extends AbstractMultiset {
      final Multiset unfiltered;
      final Predicate predicate;

      FilteredMultiset(Multiset unfiltered, Predicate predicate) {
         this.unfiltered = (Multiset)Preconditions.checkNotNull(unfiltered);
         this.predicate = (Predicate)Preconditions.checkNotNull(predicate);
      }

      public UnmodifiableIterator iterator() {
         return Iterators.filter(this.unfiltered.iterator(), this.predicate);
      }

      Set createElementSet() {
         return Sets.filter(this.unfiltered.elementSet(), this.predicate);
      }

      Set createEntrySet() {
         return Sets.filter(this.unfiltered.entrySet(), new Predicate() {
            public boolean apply(Multiset.Entry entry) {
               return FilteredMultiset.this.predicate.apply(entry.getElement());
            }
         });
      }

      Iterator entryIterator() {
         throw new AssertionError("should never be called");
      }

      int distinctElements() {
         return this.elementSet().size();
      }

      public int count(@Nullable Object element) {
         int count = this.unfiltered.count(element);
         if (count > 0) {
            return this.predicate.apply(element) ? count : 0;
         } else {
            return 0;
         }
      }

      public int add(@Nullable Object element, int occurrences) {
         Preconditions.checkArgument(this.predicate.apply(element), "Element %s does not match predicate %s", element, this.predicate);
         return this.unfiltered.add(element, occurrences);
      }

      public int remove(@Nullable Object element, int occurrences) {
         CollectPreconditions.checkNonnegative(occurrences, "occurrences");
         if (occurrences == 0) {
            return this.count(element);
         } else {
            return this.contains(element) ? this.unfiltered.remove(element, occurrences) : 0;
         }
      }

      public void clear() {
         this.elementSet().clear();
      }
   }

   static class ImmutableEntry extends AbstractEntry implements Serializable {
      @Nullable
      private final Object element;
      private final int count;
      private static final long serialVersionUID = 0L;

      ImmutableEntry(@Nullable Object element, int count) {
         this.element = element;
         this.count = count;
         CollectPreconditions.checkNonnegative(count, "count");
      }

      @Nullable
      public final Object getElement() {
         return this.element;
      }

      public final int getCount() {
         return this.count;
      }

      public ImmutableEntry nextInBucket() {
         return null;
      }
   }

   static class UnmodifiableMultiset extends ForwardingMultiset implements Serializable {
      final Multiset delegate;
      transient Set elementSet;
      transient Set entrySet;
      private static final long serialVersionUID = 0L;

      UnmodifiableMultiset(Multiset delegate) {
         this.delegate = delegate;
      }

      protected Multiset delegate() {
         return this.delegate;
      }

      Set createElementSet() {
         return Collections.unmodifiableSet(this.delegate.elementSet());
      }

      public Set elementSet() {
         Set es = this.elementSet;
         return es == null ? (this.elementSet = this.createElementSet()) : es;
      }

      public Set entrySet() {
         Set es = this.entrySet;
         return es == null ? (this.entrySet = Collections.unmodifiableSet(this.delegate.entrySet())) : es;
      }

      public Iterator iterator() {
         return Iterators.unmodifiableIterator(this.delegate.iterator());
      }

      public boolean add(Object element) {
         throw new UnsupportedOperationException();
      }

      public int add(Object element, int occurences) {
         throw new UnsupportedOperationException();
      }

      public boolean addAll(Collection elementsToAdd) {
         throw new UnsupportedOperationException();
      }

      public boolean remove(Object element) {
         throw new UnsupportedOperationException();
      }

      public int remove(Object element, int occurrences) {
         throw new UnsupportedOperationException();
      }

      public boolean removeAll(Collection elementsToRemove) {
         throw new UnsupportedOperationException();
      }

      public boolean retainAll(Collection elementsToRetain) {
         throw new UnsupportedOperationException();
      }

      public void clear() {
         throw new UnsupportedOperationException();
      }

      public int setCount(Object element, int count) {
         throw new UnsupportedOperationException();
      }

      public boolean setCount(Object element, int oldCount, int newCount) {
         throw new UnsupportedOperationException();
      }
   }
}
