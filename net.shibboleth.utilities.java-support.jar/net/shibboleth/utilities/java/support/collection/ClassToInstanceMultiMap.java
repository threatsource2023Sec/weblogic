package net.shibboleth.utilities.java.support.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import net.shibboleth.utilities.java.support.annotation.constraint.Live;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;

@NotThreadSafe
public class ClassToInstanceMultiMap {
   private final boolean indexSupertypes;
   @Nonnull
   private final HashMap backingMap;
   @Nonnull
   private final List values;

   public ClassToInstanceMultiMap() {
      this(false);
   }

   public ClassToInstanceMultiMap(boolean isIndexingSupertypes) {
      this.backingMap = new HashMap();
      this.values = new ArrayList();
      this.indexSupertypes = isIndexingSupertypes;
   }

   public void clear() {
      this.values.clear();
      this.backingMap.clear();
   }

   public boolean containsKey(@Nullable Class key) {
      return key == null ? false : this.backingMap.containsKey(key);
   }

   public boolean containsValue(@Nonnull Object value) {
      return value == null ? false : this.values.contains(value);
   }

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @Live
   public List get(@Nullable Class type) {
      if (type == null) {
         return Collections.emptyList();
      } else {
         List indexedValues = (List)this.backingMap.get(type);
         return indexedValues == null ? Collections.emptyList() : Collections.unmodifiableList(indexedValues);
      }
   }

   public boolean isEmpty() {
      return this.values.isEmpty();
   }

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @Live
   public Set keys() {
      return Collections.unmodifiableSet(this.backingMap.keySet());
   }

   public void put(@Nonnull Object value) {
      if (value != null) {
         if (!this.values.contains(value)) {
            this.values.add(value);
         }

         Iterator i$ = this.getIndexTypes(value).iterator();

         while(i$.hasNext()) {
            Class indexKey = (Class)i$.next();
            List indexValues = (List)this.backingMap.get(indexKey);
            if (indexValues == null) {
               indexValues = new ArrayList();
               this.backingMap.put(indexKey, indexValues);
            }

            if (!((List)indexValues).contains(value)) {
               ((List)indexValues).add(value);
            }
         }

      }
   }

   public void putAll(@Nullable @NonnullElements Iterable newValues) {
      if (newValues != null) {
         Iterator i$ = newValues.iterator();

         while(i$.hasNext()) {
            Object value = i$.next();
            this.put(value);
         }

      }
   }

   public void putAll(@Nullable @NonnullElements ClassToInstanceMultiMap map) {
      if (map != null) {
         this.putAll((Iterable)map.values());
      }
   }

   public void remove(@Nonnull Object value) {
      if (value != null) {
         this.values.remove(value);
         Iterator i$ = this.getIndexTypes(value).iterator();

         while(i$.hasNext()) {
            Class indexKey = (Class)i$.next();
            List indexValues = (List)this.backingMap.get(indexKey);
            if (indexValues != null) {
               indexValues.remove(value);
               if (indexValues.isEmpty()) {
                  this.backingMap.remove(indexKey);
               }
            }
         }

      }
   }

   public void removeAll(@Nullable @NonnullElements Iterable removeValues) {
      if (removeValues != null) {
         Iterator i$ = removeValues.iterator();

         while(i$.hasNext()) {
            Object value = i$.next();
            this.remove(value);
         }

      }
   }

   public void removeAll(@Nullable @NonnullElements ClassToInstanceMultiMap map) {
      if (map != null) {
         this.removeAll((Iterable)map.values());
      }
   }

   public void remove(@Nullable Class type) {
      if (type != null) {
         List indexValues = (List)this.backingMap.remove(type);
         if (indexValues != null) {
            Iterator i$ = indexValues.iterator();

            while(i$.hasNext()) {
               Object value = i$.next();
               this.remove(value);
            }
         }

      }
   }

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @Live
   public Collection values() {
      return Collections.unmodifiableList(this.values);
   }

   @Nonnull
   @NonnullElements
   private Set getIndexTypes(@Nonnull Object value) {
      HashSet indexTypes = new HashSet();
      indexTypes.add(value.getClass());
      if (this.indexSupertypes) {
         this.getSuperTypes(value.getClass(), indexTypes);
      }

      return indexTypes;
   }

   private void getSuperTypes(@Nonnull Class clazz, @Nonnull @NonnullElements Set accumulator) {
      Class superclass = clazz.getSuperclass();
      if (superclass != null && superclass != Object.class) {
         accumulator.add(superclass);
         this.getSuperTypes(superclass, accumulator);
      }

      Class[] interfaces = clazz.getInterfaces();
      if (interfaces.length > 0) {
         Class[] arr$ = interfaces;
         int len$ = interfaces.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Class iface = arr$[i$];
            accumulator.add(iface);
            this.getSuperTypes(iface, accumulator);
         }
      }

   }

   public int hashCode() {
      return this.backingMap.hashCode() + this.values.hashCode();
   }

   public boolean equals(Object obj) {
      if (null == obj) {
         return false;
      } else if (!(obj instanceof ClassToInstanceMultiMap)) {
         return false;
      } else {
         ClassToInstanceMultiMap cast = (ClassToInstanceMultiMap)obj;
         return this.backingMap.equals(cast.backingMap) && this.values.equals(cast.values);
      }
   }
}
