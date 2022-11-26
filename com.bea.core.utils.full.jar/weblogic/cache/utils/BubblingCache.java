package weblogic.cache.utils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class BubblingCache implements Map, Externalizable {
   static final long serialVersionUID = -7053192861279007995L;
   private static final boolean debug = false;
   private static final boolean verbose = false;
   private final HashMap underlying;
   private ArrayList bubbler;
   private int maxSize;

   public BubblingCache() {
      this(100);
   }

   public BubblingCache(int size) {
      this.underlying = new HashMap();
      this.maxSize = size;
      this.bubbler = new ArrayList(size);
   }

   public synchronized Object get(Object key) {
      ValuePositionPair vpp = (ValuePositionPair)this.underlying.get(key);
      if (key == null) {
         throw new IllegalArgumentException("Cannot get key = null");
      } else if (vpp == null) {
         return null;
      } else {
         if (vpp.getPosition() != 0) {
            Object otherKey = this.bubbler.get(vpp.getPosition() - 1);
            ValuePositionPair ovpp = (ValuePositionPair)this.underlying.get(otherKey);
            ovpp.setPosition(vpp.getPosition());
            vpp.setPosition(vpp.getPosition() - 1);
            this.bubbler.set(ovpp.getPosition(), otherKey);
            this.bubbler.set(vpp.getPosition(), key);
         }

         return vpp.getValue();
      }
   }

   public synchronized Object put(Object key, Object value) {
      if (key != null && value != null) {
         ValuePositionPair oldVpp = (ValuePositionPair)this.underlying.get(key);
         if (oldVpp != null) {
            Object old = oldVpp.getValue();
            oldVpp.setValue(value);
            return old;
         } else {
            int size = this.underlying.size();
            ValuePositionPair vpp = new ValuePositionPair();
            vpp.setValue(value);
            ValuePositionPair replaced = null;
            if (size == this.maxSize) {
               vpp.setPosition(size - 1);
               replaced = (ValuePositionPair)this.underlying.remove(this.bubbler.set(vpp.getPosition(), key));
            } else {
               vpp.setPosition(size);
               this.bubbler.add(vpp.getPosition(), key);
            }

            this.underlying.put(key, vpp);
            return replaced == null ? null : replaced.getValue();
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   public synchronized Object remove(Object key) {
      ValuePositionPair vpp = (ValuePositionPair)this.underlying.get(key);
      if (key == null) {
         throw new IllegalArgumentException("Cannot get key = null");
      } else if (vpp == null) {
         return null;
      } else {
         Object removed = ((ValuePositionPair)this.underlying.remove(key)).getValue();

         for(int i = vpp.getPosition() + 1; i < this.bubbler.size(); ++i) {
            Object otherKey = this.bubbler.get(i);
            ValuePositionPair ovpp = (ValuePositionPair)this.underlying.get(otherKey);
            ovpp.setPosition(i - 1);
            this.bubbler.set(i - 1, otherKey);
         }

         int end = this.bubbler.size() - 1;
         this.bubbler.remove(end);
         vpp.setPosition(-1);
         vpp.setValue((Object)null);
         return removed;
      }
   }

   public synchronized int size() {
      return this.underlying.size();
   }

   public synchronized boolean isEmpty() {
      return this.underlying.isEmpty();
   }

   public synchronized boolean containsKey(Object key) {
      return this.underlying.containsKey(key);
   }

   public synchronized boolean containsValue(Object value) {
      Iterator var2 = this.underlying.values().iterator();

      ValuePositionPair vpp;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         vpp = (ValuePositionPair)var2.next();
      } while(!vpp.getValue().equals(value));

      return true;
   }

   public synchronized void putAll(Map m) {
      Iterator var2 = m.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         this.put(entry.getKey(), entry.getValue());
      }

   }

   public synchronized void clear() {
      this.underlying.clear();
      this.bubbler.clear();
   }

   public synchronized Set keySet() {
      return this.underlying.keySet();
   }

   public synchronized Collection values() {
      ArrayList retVal = new ArrayList(this.underlying.size());
      Iterator var2 = this.underlying.values().iterator();

      while(var2.hasNext()) {
         ValuePositionPair vpp = (ValuePositionPair)var2.next();
         retVal.add(vpp.getValue());
      }

      return retVal;
   }

   public synchronized Set entrySet() {
      HashSet retVal = new HashSet();
      Iterator var2 = this.underlying.entrySet().iterator();

      while(var2.hasNext()) {
         final Map.Entry underlyingEntries = (Map.Entry)var2.next();
         retVal.add(new Map.Entry() {
            public Object getKey() {
               return underlyingEntries.getKey();
            }

            public Object getValue() {
               return ((ValuePositionPair)underlyingEntries.getValue()).getValue();
            }

            public Object setValue(Object value) {
               throw new UnsupportedOperationException();
            }
         });
      }

      return retVal;
   }

   public synchronized void writeExternal(ObjectOutput oo) throws IOException {
      oo.writeInt(this.maxSize);
      oo.writeInt(this.underlying.size());
      Iterator i = this.underlying.keySet().iterator();

      while(i.hasNext()) {
         Object key = i.next();
         oo.writeObject(key);
         oo.writeObject(this.underlying.get(key));
      }

   }

   public synchronized void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
      this.maxSize = oi.readInt();
      int size = oi.readInt();
      this.bubbler = new ArrayList(this.maxSize);

      int i;
      for(i = 0; i < size; ++i) {
         this.bubbler.add((Object)null);
      }

      for(i = 0; i < size; ++i) {
         Object key = oi.readObject();
         ValuePositionPair value = (ValuePositionPair)oi.readObject();
         int position = value.getPosition();
         this.bubbler.set(position, key);
         this.underlying.put(key, value);
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("[");
      Iterator i = this.underlying.keySet().iterator();

      while(i.hasNext()) {
         String key = (String)i.next();
         sb.append("[");
         sb.append(key);
         sb.append(": ");
         sb.append(this.underlying.get(key));
         sb.append("]");
      }

      sb.append("]");
      return sb.toString();
   }
}
