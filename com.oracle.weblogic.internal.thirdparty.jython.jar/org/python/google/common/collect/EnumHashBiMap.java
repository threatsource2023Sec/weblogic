package org.python.google.common.collect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible(
   emulated = true
)
public final class EnumHashBiMap extends AbstractBiMap {
   private transient Class keyType;
   @GwtIncompatible
   private static final long serialVersionUID = 0L;

   public static EnumHashBiMap create(Class keyType) {
      return new EnumHashBiMap(keyType);
   }

   public static EnumHashBiMap create(Map map) {
      EnumHashBiMap bimap = create(EnumBiMap.inferKeyType(map));
      bimap.putAll(map);
      return bimap;
   }

   private EnumHashBiMap(Class keyType) {
      super(WellBehavedMap.wrap(new EnumMap(keyType)), (Map)Maps.newHashMapWithExpectedSize(((Enum[])keyType.getEnumConstants()).length));
      this.keyType = keyType;
   }

   Enum checkKey(Enum key) {
      return (Enum)Preconditions.checkNotNull(key);
   }

   @CanIgnoreReturnValue
   public Object put(Enum key, @Nullable Object value) {
      return super.put(key, value);
   }

   @CanIgnoreReturnValue
   public Object forcePut(Enum key, @Nullable Object value) {
      return super.forcePut(key, value);
   }

   public Class keyType() {
      return this.keyType;
   }

   @GwtIncompatible
   private void writeObject(ObjectOutputStream stream) throws IOException {
      stream.defaultWriteObject();
      stream.writeObject(this.keyType);
      Serialization.writeMap(this, stream);
   }

   @GwtIncompatible
   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
      stream.defaultReadObject();
      this.keyType = (Class)stream.readObject();
      this.setDelegates(WellBehavedMap.wrap(new EnumMap(this.keyType)), new HashMap(((Enum[])this.keyType.getEnumConstants()).length * 3 / 2));
      Serialization.populateMap(this, stream);
   }
}
