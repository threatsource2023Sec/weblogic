package weblogic.jndi.security;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;

public interface SubjectPusher {
   void pushSubject(Hashtable var1, Context var2) throws NamingException;

   void popSubject();

   void clearSubjectStack();
}
