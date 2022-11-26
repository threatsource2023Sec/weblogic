package weblogic.security.jaspic;

import java.util.Set;
import javax.security.auth.login.LoginException;
import org.jvnet.hk2.annotations.Contract;
import weblogic.security.acl.internal.AuthenticatedSubject;

@Contract
public interface SecurityServices {
   AuthenticatedSubject authenticate(String var1, char[] var2) throws LoginException;

   AuthenticatedSubject impersonate(String var1) throws LoginException;

   void signPrincipals(Set var1);

   boolean isAdminUser(AuthenticatedSubject var1);
}
