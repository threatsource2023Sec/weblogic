package weblogic.security.subject;

import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Set;
import javax.security.auth.Subject;

public interface AbstractSubject {
   Set getPrincipals();

   Set getPublicCredentials();

   Set getPrivateCredentials(AbstractSubject var1);

   Set getPrincipals(Class var1);

   Set getPublicCredentials(Class var1);

   Set getPrivateCredentials(AbstractSubject var1, Class var2);

   boolean isReadOnly();

   void setReadOnly(AbstractSubject var1);

   Object doAs(AbstractSubject var1, PrivilegedAction var2);

   Object doAs(AbstractSubject var1, PrivilegedExceptionAction var2) throws PrivilegedActionException;

   Subject getSubject();
}
