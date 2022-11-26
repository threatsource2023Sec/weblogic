package com.bea.core.repackaged.springframework.core;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.LinkedMultiValueMap;
import com.bea.core.repackaged.springframework.util.MultiValueMap;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public final class CollectionFactory {
   private static final Set approximableCollectionTypes = new HashSet();
   private static final Set approximableMapTypes = new HashSet();

   private CollectionFactory() {
   }

   public static boolean isApproximableCollectionType(@Nullable Class collectionType) {
      return collectionType != null && approximableCollectionTypes.contains(collectionType);
   }

   public static Collection createApproximateCollection(@Nullable Object collection, int capacity) {
      if (collection instanceof LinkedList) {
         return new LinkedList();
      } else if (collection instanceof List) {
         return new ArrayList(capacity);
      } else if (collection instanceof EnumSet) {
         Collection enumSet = EnumSet.copyOf((EnumSet)collection);
         enumSet.clear();
         return enumSet;
      } else {
         return (Collection)(collection instanceof SortedSet ? new TreeSet(((SortedSet)collection).comparator()) : new LinkedHashSet(capacity));
      }
   }

   public static Collection createCollection(Class collectionType, int capacity) {
      return createCollection(collectionType, (Class)null, capacity);
   }

   public static Collection createCollection(Class collectionType, @Nullable Class elementType, int capacity) {
      Assert.notNull(collectionType, (String)"Collection type must not be null");
      if (collectionType.isInterface()) {
         if (Set.class != collectionType && Collection.class != collectionType) {
            if (List.class == collectionType) {
               return new ArrayList(capacity);
            } else if (SortedSet.class != collectionType && NavigableSet.class != collectionType) {
               throw new IllegalArgumentException("Unsupported Collection interface: " + collectionType.getName());
            } else {
               return new TreeSet();
            }
         } else {
            return new LinkedHashSet(capacity);
         }
      } else if (EnumSet.class.isAssignableFrom(collectionType)) {
         Assert.notNull(elementType, (String)"Cannot create EnumSet for unknown element type");
         return EnumSet.noneOf(asEnumType(elementType));
      } else if (!Collection.class.isAssignableFrom(collectionType)) {
         throw new IllegalArgumentException("Unsupported Collection type: " + collectionType.getName());
      } else {
         try {
            return (Collection)ReflectionUtils.accessibleConstructor(collectionType).newInstance();
         } catch (Throwable var4) {
            throw new IllegalArgumentException("Could not instantiate Collection type: " + collectionType.getName(), var4);
         }
      }
   }

   public static boolean isApproximableMapType(@Nullable Class mapType) {
      return mapType != null && approximableMapTypes.contains(mapType);
   }

   public static Map createApproximateMap(@Nullable Object map, int capacity) {
      if (map instanceof EnumMap) {
         EnumMap enumMap = new EnumMap((EnumMap)map);
         enumMap.clear();
         return enumMap;
      } else {
         return (Map)(map instanceof SortedMap ? new TreeMap(((SortedMap)map).comparator()) : new LinkedHashMap(capacity));
      }
   }

   public static Map createMap(Class mapType, int capacity) {
      return createMap(mapType, (Class)null, capacity);
   }

   public static Map createMap(Class mapType, @Nullable Class keyType, int capacity) {
      Assert.notNull(mapType, (String)"Map type must not be null");
      if (mapType.isInterface()) {
         if (Map.class == mapType) {
            return new LinkedHashMap(capacity);
         } else if (SortedMap.class != mapType && NavigableMap.class != mapType) {
            if (MultiValueMap.class == mapType) {
               return new LinkedMultiValueMap();
            } else {
               throw new IllegalArgumentException("Unsupported Map interface: " + mapType.getName());
            }
         } else {
            return new TreeMap();
         }
      } else if (EnumMap.class == mapType) {
         Assert.notNull(keyType, (String)"Cannot create EnumMap for unknown key type");
         return new EnumMap(asEnumType(keyType));
      } else if (!Map.class.isAssignableFrom(mapType)) {
         throw new IllegalArgumentException("Unsupported Map type: " + mapType.getName());
      } else {
         try {
            return (Map)ReflectionUtils.accessibleConstructor(mapType).newInstance();
         } catch (Throwable var4) {
            throw new IllegalArgumentException("Could not instantiate Map type: " + mapType.getName(), var4);
         }
      }
   }

   public static Properties createStringAdaptingProperties() {
      return new Properties() {
         @Nullable
         public String getProperty(String key) {
            Object value = this.get(key);
            return value != null ? value.toString() : null;
         }
      };
   }

   private static Class asEnumType(Class enumType) {
      Assert.notNull(enumType, (String)"Enum type must not be null");
      if (!Enum.class.isAssignableFrom(enumType)) {
         throw new IllegalArgumentException("Supplied type is not an enum: " + enumType.getName());
      } else {
         return enumType.asSubclass(Enum.class);
      }
   }

   static {
      approximableCollectionTypes.add(Collection.class);
      approximableCollectionTypes.add(List.class);
      approximableCollectionTypes.add(Set.class);
      approximableCollectionTypes.add(SortedSet.class);
      approximableCollectionTypes.add(NavigableSet.class);
      approximableMapTypes.add(Map.class);
      approximableMapTypes.add(SortedMap.class);
      approximableMapTypes.add(NavigableMap.class);
      approximableCollectionTypes.add(ArrayList.class);
      approximableCollectionTypes.add(LinkedList.class);
      approximableCollectionTypes.add(HashSet.class);
      approximableCollectionTypes.add(LinkedHashSet.class);
      approximableCollectionTypes.add(TreeSet.class);
      approximableCollectionTypes.add(EnumSet.class);
      approximableMapTypes.add(HashMap.class);
      approximableMapTypes.add(LinkedHashMap.class);
      approximableMapTypes.add(TreeMap.class);
      approximableMapTypes.add(EnumMap.class);
   }
}
