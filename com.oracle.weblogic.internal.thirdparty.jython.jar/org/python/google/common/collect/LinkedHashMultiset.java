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
public final class LinkedHashMultiset extends AbstractMapBasedMultiset {
   @GwtIncompatible
   private static final long serialVersionUID = 0L;

   public static LinkedHashMultiset create() {
      return new LinkedHashMultiset();
   }

   public static LinkedHashMultiset create(int distinctElements) {
      return new LinkedHashMultiset(distinctElements);
   }

   public static LinkedHashMultiset create(Iterable elements) {
      LinkedHashMultiset multiset = create(Multisets.inferDistinctElements(elements));
      Iterables.addAll(multiset, elements);
      return multiset;
   }

   private LinkedHashMultiset() {
      super(new ObjectCountLinkedHashMap());
   }

   private LinkedHashMultiset(int distinctElements) {
      super(new ObjectCountLinkedHashMap(distinctElements));
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
      this.setBackingMap(new ObjectCountLinkedHashMap());
      Serialization.populateMultiset(this, stream, distinctElements);
   }
}
