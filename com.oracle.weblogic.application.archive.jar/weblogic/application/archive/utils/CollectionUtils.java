package weblogic.application.archive.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CollectionUtils {
   public static Object[] emptyArray() {
      return (Object[])Collections.emptyList().toArray();
   }

   public static List mapToList(Mapper mapper, Iterator it) {
      List list = new LinkedList();

      while(it.hasNext()) {
         list.add(mapper.map(it.next()));
      }

      return list;
   }

   public static Set mapToSet(Mapper mapper, Iterator it) {
      Set set = new HashSet();

      while(it.hasNext()) {
         set.add(mapper.map(it.next()));
      }

      return set;
   }

   public static List mapToList(Mapper mapper, Object[] objects) {
      return mapToList(mapper, Arrays.asList(objects).iterator());
   }

   public static Set mapToSet(Mapper mapper, Object[] objects) {
      return mapToSet(mapper, Arrays.asList(objects).iterator());
   }

   public static Collection map(Mapper mapper, Collection objects) {
      Collection results = new LinkedList();
      Iterator var3 = objects.iterator();

      while(var3.hasNext()) {
         Object object = var3.next();
         results.add(mapper.map(object));
      }

      return results;
   }

   public static Iterator iterator(final Object[] array, final int startIndex, final int count) {
      return new Iterator() {
         Object next;
         boolean hasNext;
         int idx = startIndex;
         int lastIdx = startIndex + count;

         public boolean hasNext() {
            if (this.idx < this.lastIdx) {
               this.next = array[this.idx++];
               this.hasNext = true;
            } else {
               this.hasNext = false;
            }

            return this.hasNext;
         }

         public Object next() {
            if (this.hasNext) {
               this.hasNext = false;
               return this.next;
            } else {
               throw new UnsupportedOperationException();
            }
         }

         public void remove() {
            throw new UnsupportedOperationException("This iterator does mutate the underlying collection.");
         }
      };
   }

   public interface Mapper {
      Object map(Object var1);
   }
}
