package weblogic.entitlement.engine;

import javax.security.auth.Subject;

public interface ESubject {
   Subject getSubject();

   boolean isUser(String var1);

   boolean isMemberOf(String var1);

   boolean isInRole(String var1);
}
