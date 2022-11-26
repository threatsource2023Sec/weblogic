package org.python.google.common.collect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;

@GwtCompatible(
   serializable = true,
   emulated = true
)
public final class HashMultiset extends AbstractMapBasedMultiset {
   @GwtIncompatible
   private static final long serialVersionUID = 0L;

   public static HashMultiset create() {
      return new HashMultiset();
   }

   public static HashMultiset create(int distinctElements) {
      return new HashMultiset(distinctElements);
   }

   public static HashMultiset create(Iterable elements) {
      HashMultiset multiset = create(Multisets.inferDistinctElements(elements));
      Iterables.addAll(multiset, elements);
      return multiset;
   }

   private HashMultiset() {
      super(new ObjectCountHashMap());
   }

   private HashMultiset(int distinctElements) {
      super(new ObjectCountHashMap(distinctElements));
   }

   @GwtIncompatible
   private void writeObject(ObjectOutputStream stream) throws IOException {
      stream.defaultWriteObject();
      Serialization.writeMultiset(this, stream);
   }

   @GwtIncompatible
   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
      stream.defaultReadObject();
      int distinctElements = Serialization.readCount(stream);
      this.setBackingMap(new ObjectCountHashMap());
      Serialization.populateMultiset(this, stream, distinctElements);
   }
}
