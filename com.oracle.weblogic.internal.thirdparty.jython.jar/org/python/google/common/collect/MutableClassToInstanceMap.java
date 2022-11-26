package org.python.google.common.collect;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.common.primitives.Primitives;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtIncompatible
public final class MutableClassToInstanceMap extends ForwardingMap implements ClassToInstanceMap, Serializable {
   private final Map delegate;

   public static MutableClassToInstanceMap create() {
      return new MutableClassToInstanceMap(new HashMap());
   }

   public static MutableClassToInstanceMap create(Map backingMap) {
      return new MutableClassToInstanceMap(backingMap);
   }

   private MutableClassToInstanceMap(Map delegate) {
      this.delegate = (Map)Preconditions.checkNotNull(delegate);
   }

   protected Map delegate() {
      return this.delegate;
   }

   static Map.Entry checkedEntry(final Map.Entry entry) {
      return new ForwardingMapEntry() {
         protected Map.Entry delegate() {
            return entry;
         }

         public Object setValue(Object value) {
            return super.setValue(MutableClassToInstanceMap.cast((Class)this.getKey(), value));
         }
      };
   }

   public Set entrySet() {
      return new ForwardingSet() {
         protected Set delegate() {
            return MutableClassToInstanceMap.this.delegate().entrySet();
         }

         public Iterator iterator() {
            return new TransformedIterator(this.delegate().iterator()) {
               Map.Entry transform(Map.Entry from) {
                  return MutableClassToInstanceMap.checkedEntry(from);
               }
            };
         }

         public Object[] toArray() {
            return this.standardToArray();
         }

         public Object[] toArray(Object[] array) {
            return this.standardToArray(array);
         }
      };
   }

   @CanIgnoreReturnValue
   public Object put(Class key, Object value) {
      return super.put(key, cast(key, value));
   }

   public void putAll(Map map) {
      Map copy = new LinkedHashMap(map);
      Iterator var3 = copy.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry entry = (Map.Entry)var3.next();
         cast((Class)entry.getKey(), entry.getValue());
      }

      super.putAll(copy);
   }

   @CanIgnoreReturnValue
   public Object putInstance(Class type, Object value) {
      return cast(type, this.put(type, value));
   }

   public Object getInstance(Class type) {
      return cast(type, this.get(type));
   }

   @CanIgnoreReturnValue
   private static Object cast(Class type, Object value) {
      return Primitives.wrap(type).cast(value);
   }

   private Object writeReplace() {
      return new SerializedForm(this.delegate());
   }

   private static final class SerializedForm implements Serializable {
      private final Map backingMap;
      private static final long serialVersionUID = 0L;

      SerializedForm(Map backingMap) {
         this.backingMap = backingMap;
      }

      Object readResolve() {
         return MutableClassToInstanceMap.create(this.backingMap);
      }
   }
}
