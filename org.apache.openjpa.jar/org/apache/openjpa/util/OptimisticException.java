package org.apache.openjpa.util;

import java.util.Collection;
import org.apache.openjpa.lib.util.Localizer;

public class OptimisticException extends StoreException {
   private static final transient Localizer _loc = Localizer.forPackage(OptimisticException.class);

   public OptimisticException(Localizer.Message msg) {
      super(msg);
   }

   public OptimisticException(Object failed) {
      this(_loc.get("opt-lock", (Object)Exceptions.toString(failed)));
      this.setFailedObject(failed);
   }

   public OptimisticException(Throwable[] nested) {
      this(_loc.get("opt-lock-nested"));
      this.setNestedThrowables(nested);
   }

   public OptimisticException(Collection failed, Throwable[] nested) {
      this(_loc.get("opt-lock-multi", (Object)Exceptions.toString(failed)));
      this.setNestedThrowables(nested);
   }

   public int getSubtype() {
      return 3;
   }
}
