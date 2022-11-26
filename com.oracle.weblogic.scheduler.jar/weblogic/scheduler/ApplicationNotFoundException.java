package weblogic.scheduler;

import java.io.IOException;

public class ApplicationNotFoundException extends IOException {
   public ApplicationNotFoundException() {
   }

   public ApplicationNotFoundException(String applicationName) {
      super(applicationName);
   }
}
