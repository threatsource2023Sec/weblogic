package javolution.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import javax.realtime.MemoryArea;
import javolution.lang.Text;
import javolution.realtime.RealtimeObject;

public abstract class FastCollection extends RealtimeObject implements Collection, Serializable {
   private FastComparator _valueComp;
   private Unmodifiable _unmodifiable;

   protected FastCollection() {
      this._valueComp = FastComparator.DEFAULT;
   }

   public abstract int size();

   public abstract Record head();

   public abstract Record tail();

   public abstract Object valueOf(Record var1);

   public abstract void delete(Record var1);

   public Collection unmodifiable() {
      if (this._unmodifiable == null) {
         MemoryArea.getMemoryArea(this).executeInArea(new Runnable() {
            public void run() {
               FastCollection.access$002(FastCollection.this, FastCollection.this.new Unmodifiable());
            }
         });
      }

      return this._unmodifiable;
   }

   public Iterator iterator() {
      return FastIterator.valueOf(this);
   }

   public FastCollection setValueComparator(FastComparator var1) {
      this._valueComp = var1;
      return this;
   }

   public FastComparator getValueComparator() {
      return this._valueComp;
   }

   public boolean add(Object var1) {
      throw new UnsupportedOperationException();
   }

   public boolean remove(Object var1) {
      FastComparator var2 = this.getValueComparator();
      Record var3 = this.head();
      Record var4 = this.tail();

      do {
         if ((var3 = var3.getNext()) == var4) {
            return false;
         }
      } while(!var2.areEqual(var1, this.valueOf(var3)));

      this.delete(var3);
      return true;
   }

   public void clear() {
      Record var1 = this.head();

      for(Record var2 = this.tail().getPrevious(); var2 != var1; var2 = var2.getPrevious()) {
         this.delete(var2);
      }

   }

   public final boolean isEmpty() {
      return this.size() == 0;
   }

   public boolean contains(Object var1) {
      FastComparator var2 = this.getValueComparator();
      Record var3 = this.head();
      Record var4 = this.tail();

      do {
         if ((var3 = var3.getNext()) == var4) {
            return false;
         }
      } while(!var2.areEqual(var1, this.valueOf(var3)));

      return true;
   }

   public boolean addAll(Collection var1) {
      if (var1 instanceof FastCollection) {
         return this.addAll((FastCollection)var1);
      } else {
         boolean var2 = false;
         Iterator var3 = var1.iterator();
         int var4 = var1.size();

         while(true) {
            --var4;
            if (var4 < 0) {
               return var2;
            }

            if (this.add(var3.next())) {
               var2 = true;
            }
         }
      }
   }

   private boolean addAll(FastCollection var1) {
      boolean var2 = false;
      Record var3 = var1.head();
      Record var4 = var1.tail();

      while((var3 = var3.getNext()) != var4) {
         if (this.add(var1.valueOf(var3))) {
            var2 = true;
         }
      }

      return var2;
   }

   public boolean containsAll(Collection var1) {
      if (var1 instanceof FastCollection) {
         return this.containsAll((FastCollection)var1);
      } else {
         Iterator var2 = var1.iterator();
         int var3 = var1.size();

         do {
            --var3;
            if (var3 < 0) {
               return true;
            }
         } while(this.contains(var2.next()));

         return false;
      }
   }

   private boolean containsAll(FastCollection var1) {
      Record var2 = var1.head();
      Record var3 = var1.tail();

      do {
         if ((var2 = var2.getNext()) == var3) {
            return true;
         }
      } while(this.contains(var1.valueOf(var2)));

      return false;
   }

   public boolean removeAll(Collection var1) {
      boolean var2 = false;
      Record var3 = this.head();

      Record var5;
      for(Record var4 = this.tail().getPrevious(); var4 != var3; var4 = var5) {
         var5 = var4.getPrevious();
         if (var1.contains(this.valueOf(var4))) {
            this.delete(var4);
            var2 = true;
         }
      }

      return var2;
   }

   public boolean retainAll(Collection var1) {
      boolean var2 = false;
      Record var3 = this.head();

      Record var5;
      for(Record var4 = this.tail().getPrevious(); var4 != var3; var4 = var5) {
         var5 = var4.getPrevious();
         if (!var1.contains(this.valueOf(var4))) {
            this.delete(var4);
            var2 = true;
         }
      }

      return var2;
   }

   public Object[] toArray() {
      return this.toArray(new Object[this.size()]);
   }

   public Object[] toArray(Object[] var1) {
      int var2 = this.size();
      if (var1.length < var2) {
         throw new UnsupportedOperationException("Destination array too small");
      } else {
         if (var1.length > var2) {
            var1[var2] = null;
         }

         int var3 = 0;
         Object[] var4 = var1;
         Record var5 = this.head();

         for(Record var6 = this.tail(); (var5 = var5.getNext()) != var6; var4[var3++] = this.valueOf(var5)) {
         }

         return var1;
      }
   }

   public Text toText() {
      Text var1 = Text.valueOf((Object)", ");
      Text var2 = Text.valueOf('[');
      Record var3 = this.head();
      Record var4 = this.tail();

      while((var3 = var3.getNext()) != var4) {
         var2 = var2.concat(Text.valueOf(this.valueOf(var3)));
         if (var3.getNext() != var4) {
            var2 = var2.concat(var1);
         }
      }

      return var2.concat(Text.valueOf(']'));
   }

   public boolean equals(Object var1) {
      if (this instanceof List) {
         return this.equalsList(var1);
      } else {
         return var1 == this || var1 instanceof Collection && ((Collection)var1).size() == this.size() && this.containsAll((Collection)var1);
      }
   }

   private boolean equalsList(Object var1) {
      FastComparator var2 = this.getValueComparator();
      if (var1 == this) {
         return true;
      } else if (var1 instanceof List) {
         List var3 = (List)var1;
         if (this.size() != var3.size()) {
            return false;
         } else {
            Record var4 = this.head();
            Iterator var5 = var3.iterator();
            int var6 = this.size();

            Object var7;
            Object var8;
            do {
               if (var6-- == 0) {
                  return true;
               }

               var4 = var4.getNext();
               var7 = this.valueOf(var4);
               var8 = var5.next();
            } while(var2.areEqual(var7, var8));

            return false;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      if (this instanceof List) {
         return this.hashCodeList();
      } else {
         FastComparator var1 = this.getValueComparator();
         int var2 = 0;
         Record var3 = this.head();

         for(Record var4 = this.tail(); (var3 = var3.getNext()) != var4; var2 += var1.hashCodeOf(this.valueOf(var3))) {
         }

         return var2;
      }
   }

   private int hashCodeList() {
      FastComparator var1 = this.getValueComparator();
      int var2 = 1;
      Record var3 = this.head();

      for(Record var4 = this.tail(); (var3 = var3.getNext()) != var4; var2 = 31 * var2 + var1.hashCodeOf(this.valueOf(var3))) {
      }

      return var2;
   }

   static Unmodifiable access$002(FastCollection var0, Unmodifiable var1) {
      return var0._unmodifiable = var1;
   }

   private final class Unmodifiable extends FastCollection implements Set, List {
      private Unmodifiable() {
      }

      public int size() {
         return FastCollection.this.size();
      }

      public Record head() {
         return FastCollection.this.head();
      }

      public Record tail() {
         return FastCollection.this.tail();
      }

      public Object valueOf(Record var1) {
         return FastCollection.this.valueOf(var1);
      }

      public boolean contains(Object var1) {
         return FastCollection.this.contains(var1);
      }

      public boolean containsAll(Collection var1) {
         return FastCollection.this.containsAll(var1);
      }

      public FastComparator getValueComparator() {
         return FastCollection.this.getValueComparator();
      }

      public FastCollection setValueComparator(FastComparator var1) {
         throw new UnsupportedOperationException("Unmodifiable");
      }

      public boolean add(Object var1) {
         throw new UnsupportedOperationException("Unmodifiable");
      }

      public void delete(Record var1) {
         throw new UnsupportedOperationException("Unmodifiable");
      }

      public boolean addAll(int var1, Collection var2) {
         throw new UnsupportedOperationException("Unmodifiable");
      }

      public Object get(int var1) {
         return ((List)FastCollection.this).get(var1);
      }

      public Object set(int var1, Object var2) {
         throw new UnsupportedOperationException("Unmodifiable");
      }

      public void add(int var1, Object var2) {
         throw new UnsupportedOperationException("Unmodifiable");
      }

      public Object remove(int var1) {
         throw new UnsupportedOperationException("Unmodifiable");
      }

      public int indexOf(Object var1) {
         return ((List)FastCollection.this).indexOf(var1);
      }

      public int lastIndexOf(Object var1) {
         return ((List)FastCollection.this).lastIndexOf(var1);
      }

      public ListIterator listIterator() {
         throw new UnsupportedOperationException("List iterator not supported for unmodifiable collection");
      }

      public ListIterator listIterator(int var1) {
         throw new UnsupportedOperationException("List iterator not supported for unmodifiable collection");
      }

      public List subList(int var1, int var2) {
         throw new UnsupportedOperationException("Sub-List not supported for unmodifiable collection");
      }

      Unmodifiable(Object var2) {
         this();
      }
   }

   public interface Record {
      Record getPrevious();

      Record getNext();
   }
}
