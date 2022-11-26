package org.python.google.common.collect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.EnumMap;
import java.util.Map;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;

@GwtCompatible(
   emulated = true
)
public final class EnumBiMap extends AbstractBiMap {
   private transient Class keyType;
   private transient Class valueType;
   @GwtIncompatible
   private static final long serialVersionUID = 0L;

   public static EnumBiMap create(Class keyType, Class valueType) {
      return new EnumBiMap(keyType, valueType);
   }

   public static EnumBiMap create(Map map) {
      EnumBiMap bimap = create(inferKeyType(map), inferValueType(map));
      bimap.putAll(map);
      return bimap;
   }

   private EnumBiMap(Class keyType, Class valueType) {
      super(WellBehavedMap.wrap(new EnumMap(keyType)), (Map)WellBehavedMap.wrap(new EnumMap(valueType)));
      this.keyType = keyType;
      this.valueType = valueType;
   }

   static Class inferKeyType(Map map) {
      if (map instanceof EnumBiMap) {
         return ((EnumBiMap)map).keyType();
      } else if (map instanceof EnumHashBiMap) {
         return ((EnumHashBiMap)map).keyType();
      } else {
         Preconditions.checkArgument(!map.isEmpty());
         return ((Enum)map.keySet().iterator().next()).getDeclaringClass();
      }
   }

   private static Class inferValueType(Map map) {
      if (map instanceof EnumBiMap) {
         return ((EnumBiMap)map).valueType;
      } else {
         Preconditions.checkArgument(!map.isEmpty());
         return ((Enum)map.values().iterator().next()).getDeclaringClass();
      }
   }

   public Class keyType() {
      return this.keyType;
   }

   public Class valueType() {
      return this.valueType;
   }

   Enum checkKey(Enum key) {
      return (Enum)Preconditions.checkNotNull(key);
   }

   Enum checkValue(Enum value) {
      return (Enum)Preconditions.checkNotNull(value);
   }

   @GwtIncompatible
   private void writeObject(ObjectOutputStream stream) throws IOException {
      stream.defaultWriteObject();
      stream.writeObject(this.keyType);
      stream.writeObject(this.valueType);
      Serialization.writeMap(this, stream);
   }

   @GwtIncompatible
   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
      stream.defaultReadObject();
      this.keyType = (Class)stream.readObject();
      this.valueType = (Class)stream.readObject();
      this.setDelegates(WellBehavedMap.wrap(new EnumMap(this.keyType)), WellBehavedMap.wrap(new EnumMap(this.valueType)));
      Serialization.populateMap(this, stream);
   }
}
