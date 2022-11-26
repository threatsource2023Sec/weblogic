package org.python.google.common.collect;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;
import org.python.google.errorprone.annotations.concurrent.LazyInit;

@GwtCompatible(
   serializable = true,
   emulated = true
)
public abstract class ImmutableMultiset extends ImmutableMultisetGwtSerializationDependencies implements Multiset {
   @LazyInit
   private transient ImmutableList asList;
   @LazyInit
   private transient ImmutableSet entrySet;

   public static ImmutableMultiset of() {
      return RegularImmutableMultiset.EMPTY;
   }

   public static ImmutableMultiset of(Object element) {
      return copyFromElements(element);
   }

   public static ImmutableMultiset of(Object e1, Object e2) {
      return copyFromElements(e1, e2);
   }

   public static ImmutableMultiset of(Object e1, Object e2, Object e3) {
      return copyFromElements(e1, e2, e3);
   }

   public static ImmutableMultiset of(Object e1, Object e2, Object e3, Object e4) {
      return copyFromElements(e1, e2, e3, e4);
   }

   public static ImmutableMultiset of(Object e1, Object e2, Object e3, Object e4, Object e5) {
      return copyFromElements(e1, e2, e3, e4, e5);
   }

   public static ImmutableMultiset of(Object e1, Object e2, Object e3, Object e4, Object e5, Object e6, Object... others) {
      return (new Builder()).add(e1).add(e2).add(e3).add(e4).add(e5).add(e6).add(others).build();
   }

   public static ImmutableMultiset copyOf(Object[] elements) {
      return copyFromElements(elements);
   }

   public static ImmutableMultiset copyOf(Iterable elements) {
      if (elements instanceof ImmutableMultiset) {
         ImmutableMultiset result = (ImmutableMultiset)elements;
         if (!result.isPartialView()) {
            return result;
         }
      }

      Builder builder = new Builder(Multisets.inferDistinctElements(elements));
      builder.addAll(elements);
      return builder.build();
   }

   private static ImmutableMultiset copyFromElements(Object... elements) {
      return (new Builder()).add(elements).build();
   }

   static ImmutableMultiset copyFromEntries(Collection entries) {
      Builder builder = new Builder(entries.size());
      Iterator var2 = entries.iterator();

      while(var2.hasNext()) {
         Multiset.Entry entry = (Multiset.Entry)var2.next();
         builder.addCopies(entry.getElement(), entry.getCount());
      }

      return builder.build();
   }

   public static ImmutableMultiset copyOf(Iterator elements) {
      return (new Builder()).addAll(elements).build();
   }

   ImmutableMultiset() {
   }

   public UnmodifiableIterator iterator() {
      final Iterator entryIterator = this.entrySet().iterator();
      return new UnmodifiableIterator() {
         int remaining;
         Object element;

         public boolean hasNext() {
            return this.remaining > 0 || entryIterator.hasNext();
         }

         public Object next() {
            if (this.remaining <= 0) {
               Multiset.Entry entry = (Multiset.Entry)entryIterator.next();
               this.element = entry.getElement();
               this.remaining = entry.getCount();
            }

            --this.remaining;
            return this.element;
         }
      };
   }

   public ImmutableList asList() {
      ImmutableList result = this.asList;
      return result == null ? (this.asList = super.asList()) : result;
   }

   public boolean contains(@Nullable Object object) {
      return this.count(object) > 0;
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public final int add(Object element, int occurrences) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public final int remove(Object element, int occurrences) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public final int setCount(Object element, int count) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public final boolean setCount(Object element, int oldCount, int newCount) {
      throw new UnsupportedOperationException();
   }

   @GwtIncompatible
   int copyIntoArray(Object[] dst, int offset) {
      Multiset.Entry entry;
      for(UnmodifiableIterator var3 = this.entrySet().iterator(); var3.hasNext(); offset += entry.getCount()) {
         entry = (Multiset.Entry)var3.next();
         Arrays.fill(dst, offset, offset + entry.getCount(), entry.getElement());
      }

      return offset;
   }

   public boolean equals(@Nullable Object object) {
      return Multisets.equalsImpl(this, object);
   }

   public int hashCode() {
      return Sets.hashCodeImpl(this.entrySet());
   }

   public String toString() {
      return this.entrySet().toString();
   }

   public abstract ImmutableSet elementSet();

   public ImmutableSet entrySet() {
      ImmutableSet es = this.entrySet;
      return es == null ? (this.entrySet = this.createEntrySet()) : es;
   }

   private final ImmutableSet createEntrySet() {
      return (ImmutableSet)(this.isEmpty() ? ImmutableSet.of() : new EntrySet());
   }

   abstract Multiset.Entry getEntry(int var1);

   Object writeReplace() {
      return new SerializedForm(this);
   }

   public static Builder builder() {
      return new Builder();
   }

   public static class Builder extends ImmutableCollection.Builder {
      AbstractObjectCountMap contents;
      boolean buildInvoked;
      boolean isLinkedHash;

      public Builder() {
         this(4);
      }

      Builder(int estimatedDistinct) {
         this.buildInvoked = false;
         this.isLinkedHash = false;
         this.contents = ObjectCountHashMap.createWithExpectedSize(estimatedDistinct);
      }

      @CanIgnoreReturnValue
      public Builder add(Object element) {
         return this.addCopies(element, 1);
      }

      @CanIgnoreReturnValue
      public Builder addCopies(Object element, int occurrences) {
         if (occurrences == 0) {
            return this;
         } else {
            if (this.buildInvoked) {
               this.contents = new ObjectCountHashMap(this.contents);
               this.isLinkedHash = false;
            }

            this.buildInvoked = false;
            Preconditions.checkNotNull(element);
            this.contents.put(element, occurrences + this.contents.get(element));
            return this;
         }
      }

      @CanIgnoreReturnValue
      public Builder setCount(Object element, int count) {
         if (count == 0 && !this.isLinkedHash) {
            this.contents = new ObjectCountLinkedHashMap(this.contents);
            this.isLinkedHash = true;
         } else if (this.buildInvoked) {
            this.contents = new ObjectCountHashMap(this.contents);
            this.isLinkedHash = false;
         }

         this.buildInvoked = false;
         Preconditions.checkNotNull(element);
         if (count == 0) {
            this.contents.remove(element);
         } else {
            this.contents.put(Preconditions.checkNotNull(element), count);
         }

         return this;
      }

      @CanIgnoreReturnValue
      public Builder add(Object... elements) {
         super.add(elements);
         return this;
      }

      @CanIgnoreReturnValue
      public Builder addAll(Iterable elements) {
         if (elements instanceof Multiset) {
            Multiset multiset = Multisets.cast(elements);
            Iterator var3 = multiset.entrySet().iterator();

            while(var3.hasNext()) {
               Multiset.Entry entry = (Multiset.Entry)var3.next();
               this.addCopies(entry.getElement(), entry.getCount());
            }
         } else {
            super.addAll(elements);
         }

         return this;
      }

      @CanIgnoreReturnValue
      public Builder addAll(Iterator elements) {
         super.addAll(elements);
         return this;
      }

      public ImmutableMultiset build() {
         if (this.contents.isEmpty()) {
            return ImmutableMultiset.of();
         } else {
            if (this.isLinkedHash) {
               this.contents = new ObjectCountHashMap(this.contents);
               this.isLinkedHash = false;
            }

            this.buildInvoked = true;
            return new RegularImmutableMultiset((ObjectCountHashMap)this.contents);
         }
      }
   }

   private static class SerializedForm implements Serializable {
      final Object[] elements;
      final int[] counts;
      private static final long serialVersionUID = 0L;

      SerializedForm(Multiset multiset) {
         int distinct = multiset.entrySet().size();
         this.elements = new Object[distinct];
         this.counts = new int[distinct];
         int i = 0;

         for(Iterator var4 = multiset.entrySet().iterator(); var4.hasNext(); ++i) {
            Multiset.Entry entry = (Multiset.Entry)var4.next();
            this.elements[i] = entry.getElement();
            this.counts[i] = entry.getCount();
         }

      }

      Object readResolve() {
         Builder builder = new Builder(this.elements.length);

         for(int i = 0; i < this.elements.length; ++i) {
            builder.addCopies(this.elements[i], this.counts[i]);
         }

         return builder.build();
      }
   }

   static class EntrySetSerializedForm implements Serializable {
      final ImmutableMultiset multiset;

      EntrySetSerializedForm(ImmutableMultiset multiset) {
         this.multiset = multiset;
      }

      Object readResolve() {
         return this.multiset.entrySet();
      }
   }

   private final class EntrySet extends ImmutableSet.Indexed {
      private static final long serialVersionUID = 0L;

      private EntrySet() {
      }

      boolean isPartialView() {
         return ImmutableMultiset.this.isPartialView();
      }

      Multiset.Entry get(int index) {
         return ImmutableMultiset.this.getEntry(index);
      }

      public int size() {
         return ImmutableMultiset.this.elementSet().size();
      }

      public boolean contains(Object o) {
         if (o instanceof Multiset.Entry) {
            Multiset.Entry entry = (Multiset.Entry)o;
            if (entry.getCount() <= 0) {
               return false;
            } else {
               int count = ImmutableMultiset.this.count(entry.getElement());
               return count == entry.getCount();
            }
         } else {
            return false;
         }
      }

      public int hashCode() {
         return ImmutableMultiset.this.hashCode();
      }

      Object writeReplace() {
         return new EntrySetSerializedForm(ImmutableMultiset.this);
      }

      // $FF: synthetic method
      EntrySet(Object x1) {
         this();
      }
   }
}
