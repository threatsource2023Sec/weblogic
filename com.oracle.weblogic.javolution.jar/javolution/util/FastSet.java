package javolution.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Set;
import javolution.lang.Reusable;
import javolution.realtime.RealtimeObject;

public class FastSet extends FastCollection implements Set, Reusable {
   private static final RealtimeObject.Factory FACTORY = new RealtimeObject.Factory() {
      public Object create() {
         return new FastSet();
      }

      public void cleanup(Object var1) {
         ((FastSet)var1).reset();
      }
   };
   private transient FastMap _map;
   private static final long serialVersionUID = 3257563997099275574L;

   public FastSet() {
      this(new FastMap());
   }

   public FastSet(String var1) {
      this(new FastMap(var1));
   }

   public FastSet(int var1) {
      this(new FastMap(var1));
   }

   public FastSet(Set var1) {
      this(new FastMap(var1.size()));
      this.addAll(var1);
   }

   private FastSet(FastMap var1) {
      this._map = var1;
   }

   public static FastSet newInstance() {
      return (FastSet)FACTORY.object();
   }

   public final int size() {
      return this._map.size();
   }

   public final boolean add(Object var1) {
      return this._map.put(var1, var1) == null;
   }

   public Set unmodifiable() {
      return (Set)super.unmodifiable();
   }

   public final void clear() {
      this._map.clear();
   }

   public final boolean contains(Object var1) {
      return this._map.containsKey(var1);
   }

   public final boolean remove(Object var1) {
      return this._map.remove(var1) == var1;
   }

   public FastCollection setValueComparator(FastComparator var1) {
      super.setValueComparator(var1);
      this._map.setKeyComparator(var1);
      return this;
   }

   public void reset() {
      super.setValueComparator(FastComparator.DIRECT);
      this._map.reset();
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      int var2 = var1.readInt();
      this._map = new FastMap(var2);
      this.setValueComparator((FastComparator)var1.readObject());
      int var3 = var2;

      while(var3-- != 0) {
         this.add(var1.readObject());
      }

   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.writeInt(this.size());
      var1.writeObject(this.getValueComparator());
      FastMap.Entry var2 = this._map.head();
      FastMap.Entry var3 = this._map.tail();

      while((var2 = var2.getNext()) != var3) {
         var1.writeObject(var2.getKey());
      }

   }

   public final FastCollection.Record head() {
      return this._map.head();
   }

   public final FastCollection.Record tail() {
      return this._map.tail();
   }

   public final Object valueOf(FastCollection.Record var1) {
      return ((FastMap.Entry)var1).getKey();
   }

   public final void delete(FastCollection.Record var1) {
      this._map.remove(((FastMap.Entry)var1).getKey());
   }

   public Collection unmodifiable() {
      return this.unmodifiable();
   }
}
