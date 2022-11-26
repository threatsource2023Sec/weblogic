package org.python.google.common.collect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.annotations.VisibleForTesting;

@GwtCompatible(
   serializable = true,
   emulated = true
)
public final class ArrayListMultimap extends ArrayListMultimapGwtSerializationDependencies {
   private static final int DEFAULT_VALUES_PER_KEY = 3;
   @VisibleForTesting
   transient int expectedValuesPerKey;
   @GwtIncompatible
   private static final long serialVersionUID = 0L;

   public static ArrayListMultimap create() {
      return new ArrayListMultimap();
   }

   public static ArrayListMultimap create(int expectedKeys, int expectedValuesPerKey) {
      return new ArrayListMultimap(expectedKeys, expectedValuesPerKey);
   }

   public static ArrayListMultimap create(Multimap multimap) {
      return new ArrayListMultimap(multimap);
   }

   private ArrayListMultimap() {
      super(new HashMap());
      this.expectedValuesPerKey = 3;
   }

   private ArrayListMultimap(int expectedKeys, int expectedValuesPerKey) {
      super(Maps.newHashMapWithExpectedSize(expectedKeys));
      CollectPreconditions.checkNonnegative(expectedValuesPerKey, "expectedValuesPerKey");
      this.expectedValuesPerKey = expectedValuesPerKey;
   }

   private ArrayListMultimap(Multimap multimap) {
      this(multimap.keySet().size(), multimap instanceof ArrayListMultimap ? ((ArrayListMultimap)multimap).expectedValuesPerKey : 3);
      this.putAll(multimap);
   }

   List createCollection() {
      return new ArrayList(this.expectedValuesPerKey);
   }

   /** @deprecated */
   @Deprecated
   public void trimToSize() {
      Iterator var1 = this.backingMap().values().iterator();

      while(var1.hasNext()) {
         Collection collection = (Collection)var1.next();
         ArrayList arrayList = (ArrayList)collection;
         arrayList.trimToSize();
      }

   }

   @GwtIncompatible
   private void writeObject(ObjectOutputStream stream) throws IOException {
      stream.defaultWriteObject();
      Serialization.writeMultimap(this, stream);
   }

   @GwtIncompatible
   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
      stream.defaultReadObject();
      this.expectedValuesPerKey = 3;
      int distinctKeys = Serialization.readCount(stream);
      Map map = Maps.newHashMap();
      this.setMap(map);
      Serialization.populateMultimap(this, stream, distinctKeys);
   }
}
