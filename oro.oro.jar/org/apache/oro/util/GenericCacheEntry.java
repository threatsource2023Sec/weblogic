package org.apache.oro.util;

import java.io.Serializable;

final class GenericCacheEntry implements Serializable {
   int _index;
   Object _value;
   Object _key;

   GenericCacheEntry(int var1) {
      this._index = var1;
      this._value = null;
      this._key = null;
   }
}
