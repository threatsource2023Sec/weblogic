package org.apache.openjpa.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.openjpa.kernel.StoreManager;

public class StoreFacadeTypeRegistry {
   private Map _impls = new ConcurrentHashMap();

   public void registerImplementation(Class facadeType, Class storeType, Class implType) {
      Object key = storeType == null ? facadeType : new Key(facadeType, storeType);
      this._impls.put(key, implType);
   }

   public Class getImplementation(Class facadeType, Class storeType) {
      while(storeType != null && storeType != StoreManager.class) {
         Class impl = (Class)this._impls.get(new Key(facadeType, storeType));
         if (impl != null) {
            return impl;
         }

         storeType = storeType.getSuperclass();
      }

      return (Class)this._impls.get(facadeType);
   }

   public Class getImplementation(Class facadeType, Class storeType, Class defaultType) {
      Class result = this.getImplementation(facadeType, storeType);
      if (result == null) {
         result = defaultType;
      }

      if (facadeType != null && facadeType.isAssignableFrom(result)) {
         return result;
      } else {
         throw new InternalException();
      }
   }

   private static class Key {
      public final Class _facadeType;
      public final Class _storeType;

      public Key(Class facadeType, Class storeType) {
         this._facadeType = facadeType;
         this._storeType = storeType;
      }

      public int hashCode() {
         return this._facadeType.hashCode() ^ this._storeType.hashCode();
      }

      public boolean equals(Object other) {
         if (other == this) {
            return true;
         } else {
            Key k = (Key)other;
            return this._facadeType == k._facadeType && this._storeType == k._storeType;
         }
      }
   }
}
