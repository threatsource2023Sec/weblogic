package org.apache.openjpa.util;

import org.apache.openjpa.lib.util.Localizer;

public class ObjectExistsException extends StoreException {
   public ObjectExistsException(String msg) {
      super(msg);
   }

   public ObjectExistsException(Localizer.Message msg) {
      super(msg);
   }

   public int getSubtype() {
      return 5;
   }
}
