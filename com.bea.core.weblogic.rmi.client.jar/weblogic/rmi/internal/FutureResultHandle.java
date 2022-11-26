package weblogic.rmi.internal;

import java.rmi.Remote;
import weblogic.security.acl.internal.AuthenticatedSubject;

public interface FutureResultHandle extends Remote {
   boolean __WL_cancel(FutureResultID var1, boolean var2);

   void __WL_setFutureResultID(FutureResultID var1, AuthenticatedSubject var2);
}
