package weblogic.jndi;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;

public interface OpaqueReference {
   Object getReferent(Name var1, Context var2) throws NamingException;

   String toString();
}
