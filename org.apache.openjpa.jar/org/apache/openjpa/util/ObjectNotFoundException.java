package org.apache.openjpa.util;

import java.util.Collection;
import org.apache.openjpa.lib.util.Localizer;

public class ObjectNotFoundException extends StoreException {
   private static final transient Localizer _loc = Localizer.forPackage(ObjectNotFoundException.class);

   public ObjectNotFoundException(Localizer.Message msg) {
      super(msg);
   }

   public ObjectNotFoundException(Object failed) {
      super(_loc.get("not-found", (Object)Exceptions.toString(failed)));
      this.setFailedObject(failed);
   }

   public ObjectNotFoundException(Collection failed, Throwable[] nested) {
      super(_loc.get("not-found-multi", (Object)Exceptions.toString(failed)));
      this.setNestedThrowables(nested);
   }

   public int getSubtype() {
      return 2;
   }
}
