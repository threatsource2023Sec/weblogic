package com.kenai.jffi;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Struct extends Aggregate {
   private static final Map structCache = new ConcurrentHashMap();
   private static final ReferenceQueue structReferenceQueue = new ReferenceQueue();
   private final Type[] fields;

   public static Struct newStruct(Type... fields) {
      List fieldsList = Arrays.asList(fields);
      StructReference ref = (StructReference)structCache.get(fieldsList);
      Struct s = ref != null ? (Struct)ref.get() : null;
      if (s != null) {
         return s;
      } else {
         while((ref = (StructReference)structReferenceQueue.poll()) != null) {
            structCache.remove(ref.fieldsList);
         }

         structCache.put(fieldsList, new StructReference(s = new Struct(Foreign.getInstance(), fields), structReferenceQueue, fieldsList));
         return s;
      }
   }

   private Struct(Foreign foreign, Type... fields) {
      super(foreign, foreign.newStruct(Type.nativeHandles(fields), false));
      this.fields = (Type[])fields.clone();
   }

   /** @deprecated */
   @Deprecated
   public Struct(Type... fields) {
      super(Foreign.getInstance(), Foreign.getInstance().newStruct(Type.nativeHandles(fields), false));
      this.fields = (Type[])fields.clone();
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         return !super.equals(o) ? false : Arrays.equals(this.fields, ((Struct)o).fields);
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + Arrays.hashCode(this.fields);
      return result;
   }

   private static final class StructReference extends WeakReference {
      List fieldsList;

      private StructReference(Struct struct, ReferenceQueue referenceQueue, List fieldsList) {
         super(struct, referenceQueue);
         this.fieldsList = fieldsList;
      }

      // $FF: synthetic method
      StructReference(Struct x0, ReferenceQueue x1, List x2, Object x3) {
         this(x0, x1, x2);
      }
   }
}
