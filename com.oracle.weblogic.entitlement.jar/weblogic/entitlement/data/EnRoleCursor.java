package weblogic.entitlement.data;

public interface EnRoleCursor extends EnCursor {
   ERole getCurrentRole();

   ERole next();
}
