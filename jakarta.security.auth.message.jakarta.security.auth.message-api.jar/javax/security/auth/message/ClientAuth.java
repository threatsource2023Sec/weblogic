package javax.security.auth.message;

import javax.security.auth.Subject;

public interface ClientAuth {
   AuthStatus secureRequest(MessageInfo var1, Subject var2) throws AuthException;

   AuthStatus validateResponse(MessageInfo var1, Subject var2, Subject var3) throws AuthException;

   void cleanSubject(MessageInfo var1, Subject var2) throws AuthException;
}
