package org.python.google.common.collect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.annotations.VisibleForTesting;
import org.python.google.common.base.Preconditions;

@GwtCompatible(
   serializable = true,
   emulated = true
)
public final class HashMultimap extends HashMultimapGwtSerializationDependencies {
   private static final int DEFAULT_VALUES_PER_KEY = 2;
   @VisibleForTesting
   transient int expectedValuesPerKey = 2;
   @GwtIncompatible
   private static final long serialVersionUID = 0L;

   public static HashMultimap create() {
      return new HashMultimap();
   }

   public static HashMultimap create(int expectedKeys, int expectedValuesPerKey) {
      return new HashMultimap(expectedKeys, expectedValuesPerKey);
   }

   public static HashMultimap create(Multimap multimap) {
      return new HashMultimap(multimap);
   }

   private HashMultimap() {
      super(new HashMap());
   }

   private HashMultimap(int expectedKeys, int expectedValuesPerKey) {
      super(Maps.newHashMapWithExpectedSize(expectedKeys));
      Preconditions.checkArgument(expectedValuesPerKey >= 0);
      this.expectedValuesPerKey = expectedValuesPerKey;
   }

   private HashMultimap(Multimap multimap) {
      super(Maps.newHashMapWithExpectedSize(multimap.keySet().size()));
      this.putAll(multimap);
   }

   Set createCollection() {
      return Sets.newHashSetWithExpectedSize(this.expectedValuesPerKey);
   }

   @GwtIncompatible
   private void writeObject(ObjectOutputStream stream) throws IOException {
      stream.defaultWriteObject();
      Serialization.writeMultimap(this, stream);
   }

   @GwtIncompatible
   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
      stream.defaultReadObject();
      this.expectedValuesPerKey = 2;
      int distinctKeys = Serialization.readCount(stream);
      Map map = Maps.newHashMap();
      this.setMap(map);
      Serialization.populateMultimap(this, stream, distinctKeys);
   }
}
