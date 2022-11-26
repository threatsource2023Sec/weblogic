package org.apache.openjpa.datacache;

import java.util.EventObject;

public class ExpirationEvent extends EventObject {
   private final Object _key;
   private final boolean _expired;

   public ExpirationEvent(Object source, Object key, boolean expired) {
      super(source);
      this._key = key;
      this._expired = expired;
   }

   public Object getKey() {
      return this._key;
   }

   public boolean getExpired() {
      return this._expired;
   }
}
