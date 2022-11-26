package weblogic.jms.forwarder.internal;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Hashtable;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import weblogic.jms.common.PasswordStore;
import weblogic.jms.forwarder.SessionRuntimeContext;
import weblogic.security.subject.AbstractSubject;

public final class SessionRuntimeContextImpl implements SessionRuntimeContext {
   private static final char[] key = new char[]{'S', 'r', 'C', 'i', ' ', 'S', 'a', 'F', '5', ' ', 'n'};
   private String name;
   private Context providerContext;
   private Connection connection;
   private Session session;
   private boolean isForLocalCluster;
   private String loginUrl;
   private AbstractSubject subject;
   private boolean forceResolveDNS = false;
   private String username;
   private PasswordStore passwordStore;
   private Object passwordHandle;
   private long timeout;

   public SessionRuntimeContextImpl(String providerUrl, String username, String password, String jmsConnectionFactoryName) throws NamingException, JMSException {
      this.passwordStore = new PasswordStore(key);
      this.timeout = 60000L;
      if (providerUrl == null) {
         this.providerContext = new InitialContext();
         this.isForLocalCluster = true;
      } else {
         Hashtable env = new Hashtable();
         env.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
         env.put("java.naming.provider.url", providerUrl);
         env.put("java.naming.security.principal", username);
         env.put("java.naming.security.credentials", password);
         env.put("weblogic.jndi.connectTimeout", 60000L);
         env.put("weblogic.jndi.responseReadTimeout", 60000L);
         this.providerContext = new InitialContext(env);
      }

      this.connection = (Connection)this.providerContext.lookup(jmsConnectionFactoryName);
      this.session = this.connection.createSession(false, 1);
   }

   public SessionRuntimeContextImpl(String name, Context providerContext, String loginUrl, Connection connection, Session session, boolean isForLocalCluster, AbstractSubject subject, String username, String password, boolean forceResolveDNS) {
      this.passwordStore = new PasswordStore(key);
      this.timeout = 60000L;
      this.name = name;
      this.providerContext = providerContext;
      this.loginUrl = loginUrl;
      this.connection = connection;
      this.session = session;
      this.isForLocalCluster = isForLocalCluster;
      this.subject = subject;
      this.username = username;
      this.setPassword(password);
      this.forceResolveDNS = forceResolveDNS;
   }

   private void setPassword(String password) {
      if (this.passwordHandle != null) {
         this.passwordStore.removePassword(this.passwordHandle);
         this.passwordHandle = null;
      }

      try {
         this.passwordHandle = this.passwordStore.storePassword(password);
      } catch (GeneralSecurityException var3) {
         throw new AssertionError(var3);
      }
   }

   public String getPassword() {
      String pw = null;
      if (this.passwordHandle != null) {
         try {
            pw = (String)this.passwordStore.retrievePassword(this.passwordHandle);
         } catch (GeneralSecurityException var3) {
         } catch (IOException var4) {
         }
      }

      return pw;
   }

   public String getUsername() {
      return this.username;
   }

   public String getName() {
      return this.name;
   }

   public boolean getForceResolveDNS() {
      return this.forceResolveDNS;
   }

   public synchronized Context getProviderContext() {
      return this.providerContext;
   }

   public synchronized void setProviderContext(Context ctx) {
      this.providerContext = ctx;
   }

   public String getLoginUrl() {
      return this.loginUrl;
   }

   public synchronized Connection getJMSConnection() {
      return this.connection;
   }

   public synchronized Session getJMSSession() {
      return this.session;
   }

   public boolean isForLocalCluster() {
      return this.isForLocalCluster;
   }

   public synchronized void refresh(Context ctx, Connection connection, Session session, AbstractSubject subject) {
      this.providerContext = ctx;
      this.connection = connection;
      this.session = session;
      this.subject = subject;
   }

   public synchronized AbstractSubject getSubject() {
      return this.subject;
   }

   public synchronized void setSubject(AbstractSubject subject) {
      this.subject = subject;
   }

   public long getJndiTimeout() {
      return this.timeout;
   }
}
