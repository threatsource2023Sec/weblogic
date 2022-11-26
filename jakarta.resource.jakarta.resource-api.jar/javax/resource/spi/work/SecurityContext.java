package javax.resource.spi.work;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;

public abstract class SecurityContext implements WorkContext {
   private static final long serialVersionUID = 7730296651802712658L;

   public String getDescription() {
      return "Security Context";
   }

   public String getName() {
      return "SecurityContext";
   }

   public abstract void setupSecurityContext(CallbackHandler var1, Subject var2, Subject var3);
}
