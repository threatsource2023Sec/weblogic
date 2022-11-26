package weblogic.jndi.internal;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.List;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.event.NamingListener;
import weblogic.security.acl.internal.AuthenticatedSubject;

public interface NamingNode extends Remote {
   char DEFAULT_NAME_SEPARATOR = '.';

   void bind(String var1, Object var2, Hashtable var3) throws NamingException, RemoteException;

   Context createSubcontext(String var1, Hashtable var2) throws NamingException, RemoteException;

   void destroySubcontext(String var1, Hashtable var2) throws NamingException, RemoteException;

   NameParser getNameParser(String var1, Hashtable var2) throws NamingException, RemoteException;

   String getNameInNamespace() throws RemoteException;

   String getNameInNamespace(String var1) throws NamingException, RemoteException;

   NamingEnumeration list(String var1, Hashtable var2) throws NamingException, RemoteException;

   NamingEnumeration listBindings(String var1, Hashtable var2) throws NamingException, RemoteException;

   Object lookup(String var1, Hashtable var2) throws NamingException, RemoteException;

   Object authenticatedLookup(String var1, Hashtable var2, AuthenticatedSubject var3) throws NamingException, RemoteException;

   Object lookupLink(String var1, Hashtable var2) throws NamingException, RemoteException;

   void rebind(String var1, Object var2, Hashtable var3) throws NamingException, RemoteException;

   void rebind(Name var1, Object var2, Hashtable var3) throws NamingException, RemoteException;

   void rebind(String var1, Object var2, Object var3, Hashtable var4) throws NamingException, RemoteException;

   void rename(String var1, String var2, Hashtable var3) throws NamingException, RemoteException;

   void unbind(String var1, Object var2, Hashtable var3) throws NamingException, RemoteException;

   NamingNode getParent();

   Context getContext(Hashtable var1);

   void addNamingListener(String var1, int var2, NamingListener var3, Hashtable var4) throws NamingException;

   void removeNamingListener(NamingListener var1, Hashtable var2) throws NamingException;

   boolean isBindable(String var1, Object var2, Hashtable var3) throws NamingException, RemoteException;

   boolean isBindable(String var1, boolean var2, Hashtable var3) throws NamingException, RemoteException;

   List getOneLevelScopeNamingListeners();

   void addOneLevelScopeNamingListener(NamingListener var1);
}
