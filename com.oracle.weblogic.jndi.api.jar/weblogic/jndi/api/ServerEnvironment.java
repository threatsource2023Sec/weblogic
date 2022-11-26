package weblogic.jndi.api;

import java.rmi.Remote;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import org.jvnet.hk2.annotations.Contract;
import weblogic.jndi.ClientEnvironment;
import weblogic.security.acl.UserInfo;
import weblogic.security.acl.internal.AuthenticatedSubject;

@Contract
public interface ServerEnvironment extends ClientEnvironment {
   String LOCAL_URL = "local://";

   String getProviderUrl();

   Object setProperty(String var1, Object var2);

   String getProviderChannel();

   UserInfo getSecurityUser() throws IllegalArgumentException;

   boolean isClientCertAvailable();

   void setSecuritySubject(AuthenticatedSubject var1);

   void setSecurityUser(UserInfo var1);

   void setProviderUrl(String var1);

   Remote getInitialReference(Class var1) throws NamingException;

   void setInitialContextFactory(String var1);

   Context getInitialContext() throws NamingException;

   void setEnableDefaultUser(boolean var1);

   boolean getEnableDefaultUser();

   void setCreateIntermediateContexts(boolean var1) throws IllegalArgumentException;

   void setReplicateBindings(boolean var1);

   void setInitialProperties(Hashtable var1);
}
