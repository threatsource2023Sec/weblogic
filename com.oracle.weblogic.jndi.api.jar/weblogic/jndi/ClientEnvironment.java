package weblogic.jndi;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import org.jvnet.hk2.annotations.Contract;
import weblogic.security.subject.AbstractSubject;

@Contract
public interface ClientEnvironment {
   void setProviderURL(String var1);

   void setSecurityPrincipal(String var1);

   void setSecurityCredentials(Object var1);

   void setEnableServerAffinity(boolean var1);

   void setDisableLoggingOfWarningMsg(boolean var1);

   Context getContext() throws NamingException;

   AbstractSubject getSubject();

   Hashtable getProperties();

   void setResponseReadTimeout(long var1);

   void setConnectionTimeout(long var1);
}
