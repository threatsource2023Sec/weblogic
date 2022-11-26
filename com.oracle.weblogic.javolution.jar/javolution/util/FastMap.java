package javolution.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.realtime.MemoryArea;
import javolution.Configuration;
import javolution.lang.PersistentReference;
import javolution.lang.Reusable;
import javolution.lang.Text;
import javolution.lang.TextBuilder;
import javolution.realtime.Realtime;
import javolution.realtime.RealtimeObject;

public class FastMap extends RealtimeObject implements Map, Reusable, Serializable {
   private static final int R0 = 5;
   private static final int M0 = 31;
   private static final RealtimeObject.Factory FACTORY = new RealtimeObject.Factory() {
      public Object create() {
         return new FastMap();
      }

      public void cleanup(Object var1) {
         ((FastMap)var1).reset();
      }
   };
   private transient Entry[][] _entries;
   private transient Entry _head;
   private transient Entry _tail;
   private transient int _size;
   private transient Values _values;
   private transient KeySet _keySet;
   private transient EntrySet _entrySet;
   private transient Map _unmodifiable;
   private transient FastMap _oldEntries;
   private transient FastComparator _keyComparator;
   private transient FastComparator _keyComp;
   private transient boolean _isShared;
   private static final Entry[] NULL_BLOCK = new Entry[32];
   static volatile boolean CHECK_POINT;

   public FastMap() {
      this(4);
   }

   public FastMap(String var1) {
      this(256);
      PersistentReference var2 = new PersistentReference(var1);
      FastMap var3 = (FastMap)var2.get();
      if (var3 != null) {
         this.putAll(var3);
      }

      var2.set(this);
   }

   public FastMap(int var1) {
      this._head = new Entry();
      this._tail = new Entry();
      this._values = new Values();
      this._keySet = new KeySet();
      this._entrySet = new EntrySet();
      this._unmodifiable = new Unmodifiable();
      this._keyComparator = FastComparator.DEFAULT;
      this._keyComp = Configuration.isPoorSystemHash() ? FastComparator.REHASH : null;

      int var2;
      for(var2 = 32; var2 < var1; var2 <<= 1) {
      }

      this._entries = (Entry[][])(new Entry[var2 >> 5][]);

      for(int var3 = 0; var3 < this._entries.length; this._entries[var3++] = (Entry[])(new Entry[32])) {
      }

      FastMap.Entry.access$502(this._head, this._tail);
      FastMap.Entry.access$602(this._tail, this._head);
      Entry var6 = this._tail;

      Entry var5;
      for(int var4 = 0; var4++ < var1; var6 = var5) {
         var5 = new Entry();
         FastMap.Entry.access$602(var5, var6);
         FastMap.Entry.access$502(var6, var5);
      }

   }

   public FastMap(Map var1) {
      this(var1.size());
      this.putAll(var1);
   }

   private FastMap(Entry[][] var1) {
      this._head = new Entry();
      this._tail = new Entry();
      this._values = new Values();
      this._keySet = new KeySet();
      this._entrySet = new EntrySet();
      this._unmodifiable = new Unmodifiable();
      this._keyComparator = FastComparator.DEFAULT;
      this._keyComp = Configuration.isPoorSystemHash() ? FastComparator.REHASH : null;
      this._entries = var1;
      FastMap.Entry.access$502(this._head, this._tail);
      FastMap.Entry.access$602(this._tail, this._head);
   }

   public static FastMap newInstance() {
      return (FastMap)FACTORY.object();
   }

   public final Entry head() {
      return this._head;
   }

   public final Entry tail() {
      return this._tail;
   }

   public final int size() {
      return this._size;
   }

   public final boolean isEmpty() {
      return FastMap.Entry.access$500(this._head) == this._tail;
   }

   public final boolean containsKey(Object var1) {
      return this.getEntry(var1) != null;
   }

   public final boolean containsValue(Object var1) {
      return this._values.contains(var1);
   }

   public final Object get(Object var1) {
      Entry var2 = this.getEntry(var1, this._keyComp == null ? var1.hashCode() : this._keyComp.hashCodeOf(var1));
      return var2 != null ? FastMap.Entry.access$700(var2) : null;
   }

   public final Entry getEntry(Object var1) {
      return this.getEntry(var1, this._keyComp == null ? var1.hashCode() : this._keyComp.hashCodeOf(var1));
   }

   public final Object put(Object var1, Object var2) {
      int var3 = this._keyComp == null ? var1.hashCode() : this._keyComp.hashCodeOf(var1);
      if (this._isShared) {
         return this.putShared(var1, var2, var3);
      } else {
         Entry var4 = this.getEntry(var1, var3);
         if (var4 == null) {
            this.addEntry(var3, var1, var2);
            return null;
         } else {
            Object var5 = FastMap.Entry.access$700(var4);
            FastMap.Entry.access$702(var4, var2);
            return var5;
         }
      }
   }

   private synchronized Object putShared(Object var1, Object var2, int var3) {
      Entry var4 = this.getEntry(var1, var3);
      if (var4 == null) {
         this.addEntry(var3, var1, var2);
         return null;
      } else {
         Object var5 = FastMap.Entry.access$700(var4);
         FastMap.Entry.access$702(var4, var2);
         return var5;
      }
   }

   public final void putAll(Map var1) {
      if (var1 instanceof FastMap) {
         FastMap var2 = (FastMap)var1;
         Entry var3 = var2._head;
         Entry var4 = var2._tail;

         while((var3 = FastMap.Entry.access$500(var3)) != var4) {
            this.put(FastMap.Entry.access$800(var3), FastMap.Entry.access$700(var3));
         }
      } else {
         Iterator var5 = var1.entrySet().iterator();

         while(var5.hasNext()) {
            Map.Entry var6 = (Map.Entry)var5.next();
            this.put(var6.getKey(), var6.getValue());
         }
      }

   }

   public final Object remove(Object var1) {
      if (this._isShared) {
         return this.removeShared(var1);
      } else {
         Entry var2 = this.getEntry(var1);
         if (var2 != null) {
            Object var3 = FastMap.Entry.access$700(var2);
            this.removeEntry(var2);
            return var3;
         } else {
            return null;
         }
      }
   }

   private synchronized Object removeShared(Object var1) {
      Entry var2 = this.getEntry(var1);
      if (var2 != null) {
         --this._size;
         FastMap.Entry.access$900(var2);
         return FastMap.Entry.access$700(var2);
      } else {
         return null;
      }
   }

   public FastMap setShared(boolean var1) {
      this._isShared = var1;
      return this;
   }

   public boolean isShared() {
      return this._isShared;
   }

   public FastMap setKeyComparator(FastComparator var1) {
      this._keyComparator = var1;
      this._keyComp = var1 instanceof FastComparator.Default ? (Configuration.isPoorSystemHash() ? FastComparator.REHASH : null) : (var1 instanceof FastComparator.Direct ? null : var1);
      return this;
   }

   public FastComparator getKeyComparator() {
      return this._keyComparator;
   }

   public FastMap setValueComparator(FastComparator var1) {
      this._values.setValueComparator(var1);
      return this;
   }

   public FastComparator getValueComparator() {
      return this._values.getValueComparator();
   }

   public final void clear() {
      if (this._isShared) {
         this.clearShared();
      } else {
         Entry var1 = this._head;

         Entry[][] var3;
         for(Entry var2 = this._tail; (var1 = FastMap.Entry.access$500(var1)) != var2; var3[FastMap.Entry.access$1100(var1) >> 5 & var3.length - 1][FastMap.Entry.access$1100(var1) & 31] = null) {
            FastMap.Entry.access$802(var1, (Object)null);
            FastMap.Entry.access$702(var1, (Object)null);
            var3 = FastMap.Entry.access$1000(var1);
         }

         this._tail = FastMap.Entry.access$500(this._head);
         this._size = 0;
         this._oldEntries = null;
      }
   }

   private synchronized void clearShared() {
      Entry var1 = this._head;

      Entry[][] var3;
      for(Entry var2 = this._tail; (var1 = FastMap.Entry.access$500(var1)) != var2; var3[FastMap.Entry.access$1100(var1) >> 5 & var3.length - 1][FastMap.Entry.access$1100(var1) & 31] = null) {
         var3 = FastMap.Entry.access$1000(var1);
      }

      FastMap.Entry.access$502(this._head, this._tail);
      FastMap.Entry.access$602(this._tail, this._head);
      this._oldEntries = null;
      this._size = 0;
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 instanceof Map) {
         Map var2 = (Map)var1;
         if (this.size() == var2.size()) {
            Set var3 = var2.entrySet();
            Entry var4 = this._head;
            Entry var5 = this._tail;

            do {
               if ((var4 = FastMap.Entry.access$500(var4)) == var5) {
                  return true;
               }
            } while(var3.contains(var4));

            return false;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int var1 = 0;
      Entry var2 = this._head;

      for(Entry var3 = this._tail; (var2 = FastMap.Entry.access$500(var2)) != var3; var1 += var2.hashCode()) {
      }

      return var1;
   }

   public Text toText() {
      return this._entrySet.toText();
   }

   public void printStatistics(PrintStream var1) {
      int var2 = 0;
      int var3 = 0;
      int var4 = 0;

      for(int var5 = 0; var5 < this._entries.length; ++var5) {
         for(int var6 = 0; var6 < this._entries[var5].length; ++var6) {
            Entry var7 = this._entries[var5][var6];

            for(int var8 = 0; var7 != null; ++var4) {
               ++var8;
               if (var8 > var2) {
                  var2 = var8;
               }

               if (var8 > 1) {
                  ++var3;
               }

               var7 = FastMap.Entry.access$1200(var7);
            }
         }
      }

      TextBuilder var11 = TextBuilder.newInstance();
      if (var4 != 0) {
         var11.append(100 * var3 / var4);
         var11.append('%');
      } else {
         var11.append("N/A");
      }

      synchronized(var1) {
         var1.print("SIZE: " + var4);
         var1.print(", TABLE LENGTH: " + this._entries.length * this._entries[0].length);
         var1.print(", AVG COLLISIONS: " + var11);
         var1.print(", MAX SLOT OCCUPANCY: " + var2);
         var1.print(", KEY COMPARATOR: " + (this._keyComp == null ? FastComparator.DIRECT : this._keyComp));
         var1.print(", SHARED: " + this._isShared);
         var1.println();
         if (this._oldEntries != null) {
            var1.print(" + ");
            this._oldEntries.printStatistics(var1);
         }

      }
   }

   public final Collection values() {
      return this._values;
   }

   public final Set entrySet() {
      return this._entrySet;
   }

   public final Set keySet() {
      return this._keySet;
   }

   public final Map unmodifiable() {
      return this._unmodifiable;
   }

   private final Entry getEntry(Object var1, int var2) {
      Entry var3 = this._entries[var2 >> 5 & this._entries.length - 1][var2 & 31];

      while(true) {
         if (var3 == null) {
            return this._oldEntries != null ? this._oldEntries.getEntry(var1, var2) : null;
         }

         if (var1 == FastMap.Entry.access$800(var3)) {
            break;
         }

         if (FastMap.Entry.access$1100(var3) == var2) {
            if (this._keyComp == null) {
               if (var1.equals(FastMap.Entry.access$800(var3))) {
                  break;
               }
            } else if (this._keyComp.areEqual(var1, FastMap.Entry.access$800(var3))) {
               break;
            }
         }

         var3 = FastMap.Entry.access$1200(var3);
      }

      return var3;
   }

   private void addEntry(int var1, Object var2, Object var3) {
      if (this._size++ >> 5 >= this._entries.length) {
         this.increaseEntryTable();
      }

      if (FastMap.Entry.access$500(this._tail) == null) {
         this.increaseCapacity();
      }

      Entry var4 = FastMap.Entry.access$500(this._tail);
      FastMap.Entry.access$802(this._tail, var2);
      FastMap.Entry.access$702(this._tail, var3);
      FastMap.Entry.access$1102(this._tail, var1);
      FastMap.Entry.access$1002(this._tail, this._entries);
      int var5 = var1 >> 5 & this._entries.length - 1;
      Entry[] var6 = this._entries[var5];
      if (var6 == NULL_BLOCK) {
         this.newBlock(var5);
         var6 = this._entries[var5];
      }

      Entry var7 = var6[var1 & 31];
      FastMap.Entry.access$1202(this._tail, var7);
      var6[var1 & 31] = this._tail;
      this._tail = var4;
   }

   private final void removeEntry(Entry var1) {
      --this._size;
      FastMap.Entry.access$802(var1, (Object)null);
      FastMap.Entry.access$702(var1, (Object)null);
      FastMap.Entry.access$900(var1);
      Entry var2 = FastMap.Entry.access$500(this._tail);
      FastMap.Entry.access$602(var1, this._tail);
      FastMap.Entry.access$502(var1, var2);
      FastMap.Entry.access$502(this._tail, var1);
      if (var2 != null) {
         FastMap.Entry.access$602(var2, var1);
      }

   }

   private void newBlock(final int var1) {
      MemoryArea.getMemoryArea(this).executeInArea(new Runnable() {
         public void run() {
            FastMap.access$1600(FastMap.this)[var1] = new Entry[32];
         }
      });
   }

   private void increaseCapacity() {
      MemoryArea.getMemoryArea(this).executeInArea(new Runnable() {
         public void run() {
            Entry var1 = new Entry();
            FastMap.Entry.access$502(FastMap.access$1500(FastMap.this), var1);
            FastMap.Entry.access$602(var1, FastMap.access$1500(FastMap.this));
            Entry var2 = new Entry();
            FastMap.Entry.access$502(var1, var2);
            FastMap.Entry.access$602(var2, var1);
            Entry var3 = new Entry();
            FastMap.Entry.access$502(var2, var3);
            FastMap.Entry.access$602(var3, var2);
            Entry var4 = new Entry();
            FastMap.Entry.access$502(var3, var4);
            FastMap.Entry.access$602(var4, var3);
         }
      });
   }

   private void increaseEntryTable() {
      MemoryArea.getMemoryArea(this).executeInArea(new Runnable() {
         public void run() {
            int var1 = FastMap.access$1600(FastMap.this).length << 3;
            FastMap var2;
            if (var1 <= 8) {
               var2 = new FastMap(new Entry[8][]);
            } else if (var1 <= 64) {
               var2 = new FastMap(new Entry[64][]);
            } else if (var1 <= 512) {
               var2 = new FastMap(new Entry[512][]);
            } else if (var1 <= 4096) {
               var2 = new FastMap(new Entry[4096][]);
            } else if (var1 <= 32768) {
               var2 = new FastMap(new Entry['耀'][]);
            } else if (var1 <= 262144) {
               var2 = new FastMap(new Entry[262144][]);
            } else if (var1 <= 2097152) {
               var2 = new FastMap(new Entry[2097152][]);
            } else if (var1 <= 16777216) {
               var2 = new FastMap(new Entry[16777216][]);
            } else {
               if (var1 > 134217728) {
                  return;
               }

               var2 = new FastMap(new Entry[134217728][]);
            }

            for(int var3 = 0; var3 < FastMap.access$1600(var2).length; FastMap.access$1600(var2)[var3++] = FastMap.access$1800()) {
            }

            Entry[][] var4 = FastMap.access$1600(var2);
            FastMap.access$1602(var2, FastMap.access$1600(FastMap.this));
            FastMap.access$1902(var2, FastMap.access$1900(FastMap.this));
            FastMap.access$2002(var2, FastMap.access$2000(FastMap.this));
            FastMap.access$1402(var2, (Entry)null);
            FastMap.access$1502(var2, (Entry)null);
            FastMap.access$1302(var2, -1);
            FastMap.access$1902(FastMap.this, var2);
            FastMap.access$2100();
            FastMap.access$1602(FastMap.this, var4);
         }
      });
   }

   public boolean move(Realtime.ObjectSpace var1) {
      if (super.move(var1)) {
         Entry var2 = this._head;
         Entry var3 = this._tail;

         while((var2 = FastMap.Entry.access$500(var2)) != var3) {
            if (FastMap.Entry.access$800(var2) instanceof Realtime) {
               ((Realtime)FastMap.Entry.access$800(var2)).move(var1);
            }

            if (FastMap.Entry.access$700(var2) instanceof Realtime) {
               ((Realtime)FastMap.Entry.access$700(var2)).move(var1);
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public void reset() {
      this.setShared(false);
      this.clear();
      this.setKeyComparator(FastComparator.DEFAULT);
      this.setValueComparator(FastComparator.DEFAULT);
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      int var2 = var1.readInt();
      int var3 = var1.readInt();
      this._entries = (Entry[][])(new Entry[var3][]);

      int var4;
      for(var4 = 0; var4 < this._entries.length; this._entries[var4++] = NULL_BLOCK) {
      }

      this._head = new Entry();
      this._tail = new Entry();
      FastMap.Entry.access$502(this._head, this._tail);
      FastMap.Entry.access$602(this._tail, this._head);
      this._values = new Values();
      this._entrySet = new EntrySet();
      this._keySet = new KeySet();
      this._unmodifiable = new Unmodifiable();
      this.setShared(var1.readBoolean());
      this.setKeyComparator((FastComparator)var1.readObject());
      this.setValueComparator((FastComparator)var1.readObject());

      for(var4 = 0; var4 < var2; ++var4) {
         Object var5 = var1.readObject();
         Object var6 = var1.readObject();
         this.addEntry(this._keyComparator.hashCodeOf(var5), var5, var6);
      }

   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.writeInt(this._size);
      var1.writeInt(this._entries.length);
      var1.writeBoolean(this._isShared);
      var1.writeObject(this._keyComparator);
      var1.writeObject(this._values.getValueComparator());
      Entry var2 = this._head;
      Entry var3 = this._tail;

      while((var2 = FastMap.Entry.access$500(var2)) != var3) {
         var1.writeObject(FastMap.Entry.access$800(var2));
         var1.writeObject(FastMap.Entry.access$700(var2));
      }

   }

   private static void checkpoint() {
      if (CHECK_POINT) {
         throw new Error();
      }
   }

   static int access$1300(FastMap var0) {
      return var0._size;
   }

   static Entry access$1400(FastMap var0) {
      return var0._head;
   }

   static Entry access$1500(FastMap var0) {
      return var0._tail;
   }

   static Entry[][] access$1600(FastMap var0) {
      return var0._entries;
   }

   FastMap(Entry[][] var1, Object var2) {
      this(var1);
   }

   static Entry[] access$1800() {
      return NULL_BLOCK;
   }

   static Entry[][] access$1602(FastMap var0, Entry[][] var1) {
      return var0._entries = var1;
   }

   static FastMap access$1902(FastMap var0, FastMap var1) {
      return var0._oldEntries = var1;
   }

   static FastMap access$1900(FastMap var0) {
      return var0._oldEntries;
   }

   static FastComparator access$2002(FastMap var0, FastComparator var1) {
      return var0._keyComp = var1;
   }

   static FastComparator access$2000(FastMap var0) {
      return var0._keyComp;
   }

   static Entry access$1402(FastMap var0, Entry var1) {
      return var0._head = var1;
   }

   static Entry access$1502(FastMap var0, Entry var1) {
      return var0._tail = var1;
   }

   static int access$1302(FastMap var0, int var1) {
      return var0._size = var1;
   }

   static void access$2100() {
      checkpoint();
   }

   static KeySet access$2200(FastMap var0) {
      return var0._keySet;
   }

   static Values access$2300(FastMap var0) {
      return var0._values;
   }

   private final class Unmodifiable extends RealtimeObject implements Map, Serializable {
      private Unmodifiable() {
      }

      public boolean equals(Object var1) {
         return FastMap.this.equals(var1);
      }

      public int hashCode() {
         return FastMap.this.hashCode();
      }

      public Text toText() {
         return FastMap.this.toText();
      }

      public int size() {
         return FastMap.this.size();
      }

      public boolean isEmpty() {
         return FastMap.this.isEmpty();
      }

      public boolean containsKey(Object var1) {
         return FastMap.this.containsKey(var1);
      }

      public boolean containsValue(Object var1) {
         return FastMap.this.containsValue(var1);
      }

      public Object get(Object var1) {
         return FastMap.this.get(var1);
      }

      public Object put(Object var1, Object var2) {
         throw new UnsupportedOperationException("Unmodifiable map");
      }

      public Object remove(Object var1) {
         throw new UnsupportedOperationException("Unmodifiable map");
      }

      public void putAll(Map var1) {
         throw new UnsupportedOperationException("Unmodifiable map");
      }

      public void clear() {
         throw new UnsupportedOperationException("Unmodifiable map");
      }

      public Set keySet() {
         return (Set)FastMap.access$2200(FastMap.this).unmodifiable();
      }

      public Collection values() {
         return FastMap.access$2300(FastMap.this).unmodifiable();
      }

      public Set entrySet() {
         throw new UnsupportedOperationException("Direct view over unmodifiable map entries is not supported  (to prevent access to Entry.setValue(Object) method). To iterate over unmodifiable map entries, applications may use the keySet() and values() fast collection views in conjonction.");
      }

      Unmodifiable(Object var2) {
         this();
      }
   }

   public static final class Entry implements Map.Entry, FastCollection.Record {
      private Entry _next;
      private Entry _previous;
      private Object _key;
      private Object _value;
      private Entry _beside;
      private Entry[][] _table;
      private int _keyHash;

      private Entry() {
      }

      public final Entry getNext() {
         return this._next;
      }

      public final Entry getPrevious() {
         return this._previous;
      }

      public final Object getKey() {
         return this._key;
      }

      public final Object getValue() {
         return this._value;
      }

      public final Object setValue(Object var1) {
         Object var2 = this._value;
         this._value = var1;
         return var2;
      }

      public boolean equals(Object var1) {
         if (!(var1 instanceof Map.Entry)) {
            return false;
         } else {
            boolean var10000;
            label31: {
               Map.Entry var2 = (Map.Entry)var1;
               if (this._key.equals(var2.getKey())) {
                  if (this._value != null) {
                     if (this._value.equals(var2.getValue())) {
                        break label31;
                     }
                  } else if (var2.getValue() == null) {
                     break label31;
                  }
               }

               var10000 = false;
               return var10000;
            }

            var10000 = true;
            return var10000;
         }
      }

      public int hashCode() {
         return this._key.hashCode() ^ (this._value != null ? this._value.hashCode() : 0);
      }

      private final void detach() {
         this._previous._next = this._next;
         this._next._previous = this._previous;
         int var1 = this._keyHash >> 5 & this._table.length - 1;
         Entry var2 = this._beside;
         Entry var3 = this._table[var1][this._keyHash & 31];
         if (var3 == this) {
            this._table[var1][this._keyHash & 31] = var2;
         } else {
            while(true) {
               if (var3._beside == this) {
                  var3._beside = var2;
                  break;
               }

               var3 = var3._beside;
            }
         }

      }

      public FastCollection.Record getNext() {
         return this.getNext();
      }

      public FastCollection.Record getPrevious() {
         return this.getPrevious();
      }

      Entry(Object var1) {
         this();
      }

      static Entry access$502(Entry var0, Entry var1) {
         return var0._next = var1;
      }

      static Entry access$602(Entry var0, Entry var1) {
         return var0._previous = var1;
      }

      static Entry access$500(Entry var0) {
         return var0._next;
      }

      static Object access$700(Entry var0) {
         return var0._value;
      }

      static Object access$702(Entry var0, Object var1) {
         return var0._value = var1;
      }

      static Object access$800(Entry var0) {
         return var0._key;
      }

      static void access$900(Entry var0) {
         var0.detach();
      }

      static Object access$802(Entry var0, Object var1) {
         return var0._key = var1;
      }

      static Entry[][] access$1000(Entry var0) {
         return var0._table;
      }

      static int access$1100(Entry var0) {
         return var0._keyHash;
      }

      static Entry access$1200(Entry var0) {
         return var0._beside;
      }

      static int access$1102(Entry var0, int var1) {
         return var0._keyHash = var1;
      }

      static Entry[][] access$1002(Entry var0, Entry[][] var1) {
         return var0._table = var1;
      }

      static Entry access$1202(Entry var0, Entry var1) {
         return var0._beside = var1;
      }
   }

   private final class KeySet extends FastCollection implements Set {
      private KeySet() {
      }

      public int size() {
         return FastMap.access$1300(FastMap.this);
      }

      public void clear() {
         FastMap.this.clear();
      }

      public boolean contains(Object var1) {
         return FastMap.this.containsKey(var1);
      }

      public boolean remove(Object var1) {
         return FastMap.this.remove(var1) != null;
      }

      public FastCollection.Record head() {
         return FastMap.access$1400(FastMap.this);
      }

      public FastCollection.Record tail() {
         return FastMap.access$1500(FastMap.this);
      }

      public Object valueOf(FastCollection.Record var1) {
         return FastMap.Entry.access$800((Entry)var1);
      }

      public void delete(FastCollection.Record var1) {
         FastMap.this.remove(((Entry)var1).getKey());
      }

      KeySet(Object var2) {
         this();
      }
   }

   private final class EntrySet extends FastCollection implements Set {
      private EntrySet() {
      }

      public int size() {
         return FastMap.access$1300(FastMap.this);
      }

      public void clear() {
         FastMap.this.clear();
      }

      public boolean contains(Object var1) {
         if (var1 instanceof Map.Entry) {
            Entry var2 = (Entry)var1;
            Entry var3 = FastMap.this.getEntry(var2.getKey());
            return var2.equals(var3);
         } else {
            return false;
         }
      }

      public Text toText() {
         Text var1 = Text.valueOf('[');
         Text var2 = Text.valueOf('=');
         Text var3 = Text.valueOf((Object)", ");
         Entry var4 = FastMap.access$1400(FastMap.this);
         Entry var5 = FastMap.access$1500(FastMap.this);

         while((var4 = FastMap.Entry.access$500(var4)) != var5) {
            var1 = var1.concat(Text.valueOf(FastMap.Entry.access$800(var4))).concat(var2).concat(Text.valueOf(FastMap.Entry.access$700(var4)));
            if (FastMap.Entry.access$500(var4) != var5) {
               var1 = var1.concat(var3);
            }
         }

         return var1.concat(Text.valueOf(']'));
      }

      public FastCollection.Record head() {
         return FastMap.access$1400(FastMap.this);
      }

      public FastCollection.Record tail() {
         return FastMap.access$1500(FastMap.this);
      }

      public Object valueOf(FastCollection.Record var1) {
         return (Map.Entry)var1;
      }

      public void delete(FastCollection.Record var1) {
         FastMap.this.remove(((Entry)var1).getKey());
      }

      EntrySet(Object var2) {
         this();
      }
   }

   private final class Values extends FastCollection {
      private Values() {
      }

      public int size() {
         return FastMap.access$1300(FastMap.this);
      }

      public void clear() {
         FastMap.this.clear();
      }

      public FastCollection.Record head() {
         return FastMap.access$1400(FastMap.this);
      }

      public FastCollection.Record tail() {
         return FastMap.access$1500(FastMap.this);
      }

      public Object valueOf(FastCollection.Record var1) {
         return FastMap.Entry.access$700((Entry)var1);
      }

      public void delete(FastCollection.Record var1) {
         FastMap.this.remove(((Entry)var1).getKey());
      }

      Values(Object var2) {
         this();
      }
   }
}
