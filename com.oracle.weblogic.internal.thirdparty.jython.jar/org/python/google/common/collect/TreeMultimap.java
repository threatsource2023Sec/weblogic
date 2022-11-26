package org.python.google.common.collect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Comparator;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;

@GwtCompatible(
   serializable = true,
   emulated = true
)
public class TreeMultimap extends AbstractSortedKeySortedSetMultimap {
   private transient Comparator keyComparator;
   private transient Comparator valueComparator;
   @GwtIncompatible
   private static final long serialVersionUID = 0L;

   public static TreeMultimap create() {
      return new TreeMultimap(Ordering.natural(), Ordering.natural());
   }

   public static TreeMultimap create(Comparator keyComparator, Comparator valueComparator) {
      return new TreeMultimap((Comparator)Preconditions.checkNotNull(keyComparator), (Comparator)Preconditions.checkNotNull(valueComparator));
   }

   public static TreeMultimap create(Multimap multimap) {
      return new TreeMultimap(Ordering.natural(), Ordering.natural(), multimap);
   }

   TreeMultimap(Comparator keyComparator, Comparator valueComparator) {
      super(new TreeMap(keyComparator));
      this.keyComparator = keyComparator;
      this.valueComparator = valueComparator;
   }

   private TreeMultimap(Comparator keyComparator, Comparator valueComparator, Multimap multimap) {
      this(keyComparator, valueComparator);
      this.putAll(multimap);
   }

   SortedSet createCollection() {
      return new TreeSet(this.valueComparator);
   }

   Collection createCollection(@Nullable Object key) {
      if (key == null) {
         this.keyComparator().compare(key, key);
      }

      return super.createCollection(key);
   }

   /** @deprecated */
   @Deprecated
   public Comparator keyComparator() {
      return this.keyComparator;
   }

   public Comparator valueComparator() {
      return this.valueComparator;
   }

   @GwtIncompatible
   public NavigableSet get(@Nullable Object key) {
      return (NavigableSet)super.get(key);
   }

   public NavigableSet keySet() {
      return (NavigableSet)super.keySet();
   }

   public NavigableMap asMap() {
      return (NavigableMap)super.asMap();
   }

   @GwtIncompatible
   private void writeObject(ObjectOutputStream stream) throws IOException {
      stream.defaultWriteObject();
      stream.writeObject(this.keyComparator());
      stream.writeObject(this.valueComparator());
      Serialization.writeMultimap(this, stream);
   }

   @GwtIncompatible
   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
      stream.defaultReadObject();
      this.keyComparator = (Comparator)Preconditions.checkNotNull((Comparator)stream.readObject());
      this.valueComparator = (Comparator)Preconditions.checkNotNull((Comparator)stream.readObject());
      this.setMap(new TreeMap(this.keyComparator));
      Serialization.populateMultimap(this, stream);
   }
}
