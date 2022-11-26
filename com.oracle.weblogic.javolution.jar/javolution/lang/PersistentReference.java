package javolution.lang;

import java.io.Serializable;
import java.util.Map;
import javolution.util.FastMap;
import javolution.util.FastSet;

public class PersistentReference implements Reference, Serializable {
   private static final FastSet IDENTIFIERS = new FastSet();
   private static final FastMap ID_TO_VALUE = new FastMap();
   private final String _id;

   public PersistentReference(String var1) {
      synchronized(IDENTIFIERS) {
         if (IDENTIFIERS.contains(var1)) {
            throw new IllegalArgumentException("id: " + var1 + " already in use");
         }

         IDENTIFIERS.add(var1);
      }

      this._id = var1;
   }

   public PersistentReference(String var1, Object var2) {
      this(var1);
      synchronized(ID_TO_VALUE) {
         if (!ID_TO_VALUE.containsKey(this._id)) {
            ID_TO_VALUE.put(var1, var2);
         }

      }
   }

   public final String id() {
      return this._id;
   }

   public Object get() {
      synchronized(ID_TO_VALUE) {
         return ID_TO_VALUE.get(this._id);
      }
   }

   public void set(Object var1) {
      synchronized(ID_TO_VALUE) {
         ID_TO_VALUE.put(this._id, var1);
      }
   }

   public void setMinimum(Object var1) {
      synchronized(ID_TO_VALUE) {
         Object var3;
         if (var1 instanceof Comparable) {
            var3 = this.get();
            if (((Comparable)var1).compareTo(var3) > 0) {
               ID_TO_VALUE.put(this._id, var1);
            }
         } else {
            if (!(var1 instanceof Integer)) {
               throw new IllegalArgumentException();
            }

            var3 = this.get();
            if ((Integer)var1 > (Integer)var3) {
               ID_TO_VALUE.put(this._id, var1);
            }
         }

      }
   }

   public void setMaximum(Object var1) {
      synchronized(ID_TO_VALUE) {
         Object var3;
         if (var1 instanceof Comparable) {
            var3 = this.get();
            if (((Comparable)var1).compareTo(var3) < 0) {
               ID_TO_VALUE.put(this._id, var1);
            }
         } else {
            if (!(var1 instanceof Integer)) {
               throw new IllegalArgumentException();
            }

            var3 = this.get();
            if ((Integer)var1 < (Integer)var3) {
               ID_TO_VALUE.put(this._id, var1);
            }
         }

      }
   }

   public static Map values() {
      return ID_TO_VALUE.unmodifiable();
   }

   public static void put(String var0, Object var1) {
      ID_TO_VALUE.put(var0, var1);
   }

   public static void putAll(Map var0) {
      ID_TO_VALUE.putAll(var0);
   }

   public String toString() {
      return String.valueOf(this.get());
   }
}
