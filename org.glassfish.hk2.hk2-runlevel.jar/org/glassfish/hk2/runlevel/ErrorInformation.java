package org.glassfish.hk2.runlevel;

import org.glassfish.hk2.api.Descriptor;

public interface ErrorInformation {
   Throwable getError();

   ErrorAction getAction();

   void setAction(ErrorAction var1);

   Descriptor getFailedDescriptor();

   public static enum ErrorAction {
      GO_TO_NEXT_LOWER_LEVEL_AND_STOP,
      IGNORE;
   }
}
