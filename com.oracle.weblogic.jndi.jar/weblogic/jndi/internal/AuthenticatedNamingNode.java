package weblogic.jndi.internal;

import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class AuthenticatedNamingNode extends BasicNamingNode {
   protected static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public Context getContext(Hashtable env) {
      Hashtable newEnv = new Hashtable();
      if (env != null) {
         newEnv.putAll(env);
      }

      newEnv.put("weblogic.jndi.replicateBindings", false);
      return new WLEventContextImpl(newEnv, this, true);
   }

   public Object authenticatedLookup(String name, Hashtable env, AuthenticatedSubject id) throws NamingException, RemoteException {
      if (id != null && id == kernelId) {
         return super.lookup(name, env);
      } else {
         throw new NamingException("ID = " + id + " is not valid for lookup of " + name);
      }
   }

   public Object lookup(String name, Hashtable env) throws NamingException, RemoteException {
      throw new UnsupportedOperationException("ID required for lookup of " + name);
   }

   protected Collection listThis(Hashtable env) throws NamingException {
      return Collections.EMPTY_LIST;
   }
}
