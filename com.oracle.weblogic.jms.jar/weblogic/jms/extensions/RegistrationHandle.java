package weblogic.jms.extensions;

import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.naming.NamingException;

public interface RegistrationHandle {
   void unregister();

   ConnectionFactory lookupConnectionFactory(String var1) throws NamingException;

   Destination lookupDestination(String var1) throws NamingException;

   Object runAs(PrivilegedExceptionAction var1) throws PrivilegedActionException;
}
