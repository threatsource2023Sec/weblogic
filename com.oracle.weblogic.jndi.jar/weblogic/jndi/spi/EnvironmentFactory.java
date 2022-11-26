package weblogic.jndi.spi;

import java.rmi.Remote;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.jndi.Environment;
import weblogic.rmi.spi.HostID;

public interface EnvironmentFactory {
   Context getInitialContext(Environment var1, String var2) throws NamingException;

   Remote getInitialReference(Environment var1, Class var2) throws NamingException;

   Context getInitialContext(Environment var1, String var2, HostID var3) throws NamingException;
}
