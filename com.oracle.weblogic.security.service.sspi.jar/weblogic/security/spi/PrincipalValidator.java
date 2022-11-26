package weblogic.security.spi;

import java.security.Principal;

public interface PrincipalValidator {
   boolean validate(Principal var1) throws SecurityException;

   boolean sign(Principal var1);

   Class getPrincipalBaseClass();
}
