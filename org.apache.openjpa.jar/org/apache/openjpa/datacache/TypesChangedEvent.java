package org.apache.openjpa.datacache;

import java.util.Collection;
import java.util.EventObject;

public class TypesChangedEvent extends EventObject {
   private final Collection _types;

   public TypesChangedEvent(Object source, Collection types) {
      super(source);
      this._types = types;
   }

   public Collection getTypes() {
      return this._types;
   }
}
