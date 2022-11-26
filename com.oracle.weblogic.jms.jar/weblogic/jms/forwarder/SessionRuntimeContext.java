package weblogic.jms.forwarder;

import javax.jms.Connection;
import javax.jms.Session;
import javax.naming.Context;
import weblogic.security.subject.AbstractSubject;

public interface SessionRuntimeContext {
   long JNDI_TIMEOUT = 60000L;
   String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";

   String getName();

   Context getProviderContext();

   void setProviderContext(Context var1);

   String getLoginUrl();

   Connection getJMSConnection();

   Session getJMSSession();

   boolean isForLocalCluster();

   void refresh(Context var1, Connection var2, Session var3, AbstractSubject var4);

   AbstractSubject getSubject();

   void setSubject(AbstractSubject var1);

   String getUsername();

   String getPassword();

   boolean getForceResolveDNS();

   long getJndiTimeout();
}
