package javax.security.auth.message;

import javax.security.auth.Subject;

public interface ServerAuth {
   AuthStatus validateRequest(MessageInfo var1, Subject var2, Subject var3) throws AuthException;

   AuthStatus secureResponse(MessageInfo var1, Subject var2) throws AuthException;

   void cleanSubject(MessageInfo var1, Subject var2) throws AuthException;
}
