package javolution.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javolution.realtime.LocalReference;

public final class LocalMap implements Map {
   private final LocalReference _mapRef = new LocalReference((new FastMap()).setShared(true));

   public LocalMap setKeyComparator(FastComparator var1) {
      this.localMap().setKeyComparator(var1);
      return this;
   }

   public LocalMap setValueComparator(FastComparator var1) {
      this.localMap().setValueComparator(var1);
      return this;
   }

   public Object putDefault(Object var1, Object var2) {
      return ((FastMap)this._mapRef.getDefault()).put(var1, var2);
   }

   public int size() {
      return ((FastMap)this._mapRef.get()).size();
   }

   public boolean isEmpty() {
      return ((FastMap)this._mapRef.get()).isEmpty();
   }

   public boolean containsKey(Object var1) {
      return ((FastMap)this._mapRef.get()).containsKey(var1);
   }

   public boolean containsValue(Object var1) {
      return ((FastMap)this._mapRef.get()).containsValue(var1);
   }

   public Object get(Object var1) {
      return ((FastMap)this._mapRef.get()).get(var1);
   }

   public Object put(Object var1, Object var2) {
      return this.localMap().put(var1, var2);
   }

   public void putAll(Map var1) {
      this.localMap().putAll(var1);
   }

   public Object remove(Object var1) {
      return this.put(var1, (Object)null);
   }

   public void clear() {
      FastMap var1 = this.localMap();
      FastMap.Entry var2 = var1.head();
      FastMap.Entry var3 = var1.tail();

      while((var2 = var2.getNext()) != var3) {
         var2.setValue((Object)null);
      }

   }

   public Set keySet() {
      return this.localMap().keySet();
   }

   public Collection values() {
      return this.localMap().values();
   }

   public Set entrySet() {
      return this.localMap().entrySet();
   }

   private FastMap localMap() {
      FastMap var1 = (FastMap)this._mapRef.getLocal();
      return var1 != null ? var1 : this.newLocalMap();
   }

   private FastMap newLocalMap() {
      FastMap var1 = (FastMap)this._mapRef.get();
      FastMap var2 = FastMap.newInstance();
      var2.setShared(true);
      var2.setKeyComparator(var1.getKeyComparator());
      var2.setValueComparator(var1.getValueComparator());
      var2.putAll(var1);
      this._mapRef.set(var2);
      return var2;
   }
}
