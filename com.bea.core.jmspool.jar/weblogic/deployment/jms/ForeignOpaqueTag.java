package weblogic.deployment.jms;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;

public interface ForeignOpaqueTag {
   Object getReferent(Name var1, Context var2) throws NamingException;

   String toString();

   boolean isFactory();

   String getUsername();

   String getPassword();

   Hashtable getJNDIEnvironment();
}
