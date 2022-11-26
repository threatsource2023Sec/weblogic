package org.python.google.common.collect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.python.google.common.annotations.GwtIncompatible;

@GwtIncompatible
final class Serialization {
   private Serialization() {
   }

   static int readCount(ObjectInputStream stream) throws IOException {
      return stream.readInt();
   }

   static void writeMap(Map map, ObjectOutputStream stream) throws IOException {
      stream.writeInt(map.size());
      Iterator var2 = map.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         stream.writeObject(entry.getKey());
         stream.writeObject(entry.getValue());
      }

   }

   static void populateMap(Map map, ObjectInputStream stream) throws IOException, ClassNotFoundException {
      int size = stream.readInt();
      populateMap(map, stream, size);
   }

   static void populateMap(Map map, ObjectInputStream stream, int size) throws IOException, ClassNotFoundException {
      for(int i = 0; i < size; ++i) {
         Object key = stream.readObject();
         Object value = stream.readObject();
         map.put(key, value);
      }

   }

   static void writeMultiset(Multiset multiset, ObjectOutputStream stream) throws IOException {
      int entryCount = multiset.entrySet().size();
      stream.writeInt(entryCount);
      Iterator var3 = multiset.entrySet().iterator();

      while(var3.hasNext()) {
         Multiset.Entry entry = (Multiset.Entry)var3.next();
         stream.writeObject(entry.getElement());
         stream.writeInt(entry.getCount());
      }

   }

   static void populateMultiset(Multiset multiset, ObjectInputStream stream) throws IOException, ClassNotFoundException {
      int distinctElements = stream.readInt();
      populateMultiset(multiset, stream, distinctElements);
   }

   static void populateMultiset(Multiset multiset, ObjectInputStream stream, int distinctElements) throws IOException, ClassNotFoundException {
      for(int i = 0; i < distinctElements; ++i) {
         Object element = stream.readObject();
         int count = stream.readInt();
         multiset.add(element, count);
      }

   }

   static void writeMultimap(Multimap multimap, ObjectOutputStream stream) throws IOException {
      stream.writeInt(multimap.asMap().size());
      Iterator var2 = multimap.asMap().entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         stream.writeObject(entry.getKey());
         stream.writeInt(((Collection)entry.getValue()).size());
         Iterator var4 = ((Collection)entry.getValue()).iterator();

         while(var4.hasNext()) {
            Object value = var4.next();
            stream.writeObject(value);
         }
      }

   }

   static void populateMultimap(Multimap multimap, ObjectInputStream stream) throws IOException, ClassNotFoundException {
      int distinctKeys = stream.readInt();
      populateMultimap(multimap, stream, distinctKeys);
   }

   static void populateMultimap(Multimap multimap, ObjectInputStream stream, int distinctKeys) throws IOException, ClassNotFoundException {
      for(int i = 0; i < distinctKeys; ++i) {
         Object key = stream.readObject();
         Collection values = multimap.get(key);
         int valueCount = stream.readInt();

         for(int j = 0; j < valueCount; ++j) {
            Object value = stream.readObject();
            values.add(value);
         }
      }

   }

   static FieldSetter getFieldSetter(Class clazz, String fieldName) {
      try {
         Field field = clazz.getDeclaredField(fieldName);
         return new FieldSetter(field);
      } catch (NoSuchFieldException var3) {
         throw new AssertionError(var3);
      }
   }

   static final class FieldSetter {
      private final Field field;

      private FieldSetter(Field field) {
         this.field = field;
         field.setAccessible(true);
      }

      void set(Object instance, Object value) {
         try {
            this.field.set(instance, value);
         } catch (IllegalAccessException var4) {
            throw new AssertionError(var4);
         }
      }

      void set(Object instance, int value) {
         try {
            this.field.set(instance, value);
         } catch (IllegalAccessException var4) {
            throw new AssertionError(var4);
         }
      }

      // $FF: synthetic method
      FieldSetter(Field x0, Object x1) {
         this(x0);
      }
   }
}
