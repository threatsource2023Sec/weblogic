package weblogic.jndi.internal;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;

public interface RemoteContextFactory extends Remote {
   String STUB_NAME = "weblogic.jndi.internal.RemoteContextFactoryImpl_WLStub";

   Context getContext(Hashtable var1, String var2) throws NamingException, RemoteException;
}
