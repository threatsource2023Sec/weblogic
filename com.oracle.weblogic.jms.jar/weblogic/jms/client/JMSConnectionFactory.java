package weblogic.jms.client;

import java.io.Externalizable;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Hashtable;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.deployment.jms.ForeignJMSServerAware;
import weblogic.deployment.jms.ObjectBasedSecurityAware;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.jms.JMSEnvironment;
import weblogic.jms.common.CrossDomainSecurityManager;
import weblogic.jms.common.JMSConstants;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSOBSHelper;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.DispatcherPartitionContext;
import weblogic.jms.dispatcher.DispatcherWrapper;
import weblogic.jms.frontend.FEConnectionCreateRequest;
import weblogic.jms.frontend.FEConnectionFactoryRemote;
import weblogic.jndi.annotation.CrossPartitionAware;
import weblogic.messaging.dispatcher.DispatcherException;
import weblogic.rmi.extensions.PortableRemoteObject;
import weblogic.rmi.extensions.server.RemoteWrapper;
import weblogic.security.Security;
import weblogic.security.SubjectUtils;
import weblogic.security.subject.AbstractSubject;

@CrossPartitionAware
public class JMSConnectionFactory implements QueueConnectionFactory, TopicConnectionFactory, Externalizable, RemoteWrapper, Reconnectable, ForeignJMSServerAware, ObjectBasedSecurityAware {
   static final long serialVersionUID = 2752718129231506407L;
   private static final String DOMAIN_PARTITION = "DOMAIN";
   private static final byte EXTVERSION1 = 1;
   private static final byte EXTVERSION2 = 2;
   private static final byte EXTVERSION3 = 3;
   private static final byte EXTVERSION4 = 4;
   private static final byte EXTVERSION5 = 5;
   private static final byte INTERFACE_VERSION_PRE_81 = 0;
   private static final byte INTERFACE_VERSION_81 = 1;
   private static final byte INTERFACE_VERSION_CURRENT = 5;
   private FEConnectionFactoryRemote feConnectionFactoryRemote;
   private byte interfaceVersion = 5;
   private String fullyQualifiedName;
   private String serverPartitionName;
   private boolean isRA = false;
   private boolean isOBSIC = false;
   private Subject subject;
   private Hashtable jndiEnv;
   private int securityPolicy;
   private boolean isReferencedByFS = false;
   private static long RECONNECT_TIMEOUT_DEFAULT = 60000L;
   private static long RECONNECT_PERIOD_DEFAULT;
   private static final String ALIAS_PREFIX = "->";
   private static final String WALLET_DIR_KEY = "weblogic.jms.walletDir";

   public JMSConnectionFactory() {
   }

   public JMSConnectionFactory(FEConnectionFactoryRemote feConnectionFactoryRemote, String paramFullyQualifiedName, String partitionName, int securityPolicy) {
      if (JMSDebug.JMSOBS.isDebugEnabled()) {
         JMSDebug.JMSOBS.debug("JMSConnectionFactory:constructor securityPolicy=" + JMSOBSHelper.convertSecurityPolicyToString(securityPolicy));
      }

      this.feConnectionFactoryRemote = feConnectionFactoryRemote;
      this.fullyQualifiedName = paramFullyQualifiedName;
      this.serverPartitionName = partitionName;
      this.securityPolicy = securityPolicy;
   }

   public Reconnectable getReconnectState(int reconnectPolicy) throws CloneNotSupportedException {
      assert false;

      return null;
   }

   public boolean isClosed() {
      return false;
   }

   public void publicCheckClosed() {
   }

   public ReconnectController getReconnectController() {
      return null;
   }

   public boolean isReconnectControllerClosed() {
      assert false;

      return false;
   }

   public Reconnectable preCreateReplacement(Reconnectable parent) throws JMSException {
      assert false;

      return null;
   }

   public void postCreateReplacement() {
      assert false;

   }

   public void forgetReconnectState() {
      assert false;

   }

   public PeerInfo getFEPeerInfo() {
      assert false;

      return null;
   }

   public void close() {
      assert false;

   }

   public void isCloseAllowed(String operation) throws JMSException {
   }

   public final QueueConnection createQueueConnection(String username, String password) throws JMSException {
      ConnectionInternal connection = this.createConnectionInternal(username, password, false, 1);
      return connection;
   }

   public final QueueConnection createQueueConnection() throws JMSException {
      ConnectionInternal connection = this.createConnectionInternal((String)null, (String)null, false, 1);
      return connection;
   }

   public final TopicConnection createTopicConnection(String username, String password) throws JMSException {
      ConnectionInternal connection = this.createConnectionInternal(username, password, false, 2);
      return connection;
   }

   public final TopicConnection createTopicConnection() throws JMSException {
      ConnectionInternal connection = this.createConnectionInternal((String)null, (String)null, false, 2);
      return connection;
   }

   public final Connection createConnection() throws JMSException {
      return this.createConnectionInternal((String)null, (String)null, false, 0);
   }

   public final Connection createConnection(String username, String password) throws JMSException {
      return this.createConnectionInternal(username, password, false, 0);
   }

   public final String getPartitionName() {
      return this.serverPartitionName;
   }

   final JMSConnection setupJMSConnection(String username, String password, boolean wantXAConnection, DispatcherPartitionContext clientDPC, int connectionType) throws JMSException {
      DispatcherWrapper dispatcherWrapper = null;
      if (username != null && username.isEmpty()) {
         username = null;
      }

      if (JMSDebug.JMSOBS.isDebugEnabled()) {
         JMSDebug.JMSOBS.debug("JMSConnectionFactory:setupJMSConnection username=" + username + ", isRA=" + this.isRA + ", isOBSIC=" + this.isOBSIC);
      }

      if (this.isRA) {
         username = getDecryptedValue(this.jndiEnv, username);
         password = getDecryptedValue(this.jndiEnv, password);
      }

      clientDPC.exportLocalDispatcher();
      dispatcherWrapper = clientDPC.getLocalDispatcherWrapper();

      JMSConnection connection;
      FEConnectionCreateRequest connSub;
      try {
         if (this.interfaceVersion >= 1) {
            connSub = new FEConnectionCreateRequest(dispatcherWrapper, username, password, wantXAConnection);
            connection = this.createConnection(connSub);
         } else if (username != null) {
            connection = this.createConnection(dispatcherWrapper, username, password);
         } else {
            connection = this.createConnection(dispatcherWrapper, (String)null, (String)null);
         }
      } catch (UnmarshalException var16) {
         try {
            if (username != null) {
               connection = this.createConnection(dispatcherWrapper, username, password);
            } else {
               connection = this.createConnection(dispatcherWrapper, (String)null, (String)null);
            }

            this.interfaceVersion = 0;
         } catch (RemoteException var15) {
            try {
               clientDPC.unexportLocalDispatcher();
            } catch (JMSException var13) {
            }

            throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logErrorCreatingConnectionLoggable(var15), var15);
         }
      } catch (RemoteException var17) {
         try {
            clientDPC.unexportLocalDispatcher();
         } catch (JMSException var11) {
         }

         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logErrorCreatingConnectionLoggable(var17), var17);
      }

      connSub = null;
      Subject connSub;
      if (this.isRA) {
         connSub = this.createSubject(username, password);
         connection.setJndiEnv(this.jndiEnv);
         connection.setWrappedIC(true);
      } else {
         connSub = this.createSubjectForOBSJMS(username, password, connection.getSubject());
      }

      try {
         connection.setupDispatcher(connSub, clientDPC);
      } catch (DispatcherException var14) {
         try {
            clientDPC.unexportLocalDispatcher();
         } catch (JMSException var12) {
         }

         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logErrorFindingDispatcherLoggable(var14), var14);
      }

      clientDPC.getInvocableManagerDelegate().invocableAdd(3, connection);
      connection.setType(connectionType);
      connection.rememberCredentials(username, password, wantXAConnection);
      return connection;
   }

   private Subject getExpectedSubjectForCreateConnectionRMICall(FEConnectionCreateRequest request) throws weblogic.jms.common.JMSException {
      if (JMSDebug.JMSOBS.isDebugEnabled()) {
         JMSDebug.JMSOBS.debug("JMSConnectionFactory:getExpectedSubjectForCreateConnectionRMICall isOBSIC=" + this.isOBSIC + ", securityPolicy=" + JMSOBSHelper.convertSecurityPolicyToString(this.securityPolicy));
      }

      Subject ret = null;
      if (this.isOBSIC) {
         ret = this.subject;
      } else if (this.securityPolicy == 2) {
         if (request != null) {
            request.setSendBackSubject(true);
         }

         ret = SubjectUtils.getAnonymousSubject().getSubject();
      } else {
         if (this.securityPolicy == 1) {
            throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logInvalidSecurityPolicyLoggable(this.fullyQualifiedName, JMSOBSHelper.convertSecurityPolicyToString(this.securityPolicy)));
         }

         if (this.securityPolicy == 3 && request != null) {
            request.setSendBackSubject(true);
         }
      }

      if (JMSDebug.JMSOBS.isDebugEnabled()) {
         JMSDebug.JMSOBS.debug("JMSConnectionFactory:getExpectedSubjectForCreateConnectionRMICall return " + ret);
      }

      return ret;
   }

   private JMSConnection createConnection(FEConnectionCreateRequest request) throws JMSException, RemoteException {
      final FEConnectionCreateRequest frequest = request;
      Subject subject = this.getExpectedSubjectForCreateConnectionRMICall(request);
      if (subject == null) {
         return this.feConnectionFactoryRemote.connectionCreateRequest(request);
      } else {
         try {
            JMSConnection conn = (JMSConnection)Security.runAs(subject, new PrivilegedExceptionAction() {
               public Object run() throws JMSException, RemoteException {
                  return JMSConnectionFactory.this.feConnectionFactoryRemote.connectionCreateRequest(frequest);
               }
            });
            return conn;
         } catch (PrivilegedActionException var7) {
            Exception e = var7.getException();
            if (e instanceof JMSException) {
               throw (JMSException)e;
            } else {
               throw new JMSException(e.getMessage());
            }
         }
      }
   }

   private JMSConnection createConnection(DispatcherWrapper dispatcherWrapper, String username, String password) throws JMSException, RemoteException {
      final DispatcherWrapper fdispatcherWrapper = dispatcherWrapper;
      final String fusername = username;
      final String fpassword = password;
      Subject subject = this.getExpectedSubjectForCreateConnectionRMICall((FEConnectionCreateRequest)null);
      if (JMSDebug.JMSOBS.isDebugEnabled()) {
         JMSDebug.JMSOBS.debug("JMSConnectionFactory:getExpectedSubjectForCreateConnectionRMICall subject=" + subject + ", username=" + username);
      }

      JMSConnection conn;
      if (subject == null) {
         if (username != null) {
            conn = this.feConnectionFactoryRemote.connectionCreate(dispatcherWrapper, username, password);
         } else {
            conn = this.feConnectionFactoryRemote.connectionCreate(dispatcherWrapper);
         }

         return conn;
      } else {
         try {
            if (fusername != null) {
               conn = (JMSConnection)Security.runAs(subject, new PrivilegedExceptionAction() {
                  public Object run() throws JMSException, RemoteException {
                     return JMSConnectionFactory.this.feConnectionFactoryRemote.connectionCreate(fdispatcherWrapper, fusername, fpassword);
                  }
               });
            } else {
               conn = (JMSConnection)Security.runAs(subject, new PrivilegedExceptionAction() {
                  public Object run() throws JMSException, RemoteException {
                     return JMSConnectionFactory.this.feConnectionFactoryRemote.connectionCreate(fdispatcherWrapper);
                  }
               });
            }

            return conn;
         } catch (PrivilegedActionException var11) {
            Exception e = var11.getException();
            if (e instanceof JMSException) {
               throw (JMSException)e;
            } else {
               throw new JMSException(e.getMessage());
            }
         }
      }
   }

   private Subject createSubject(String usr, String pwd) throws JMSException {
      if (usr != null) {
         Context ctx = null;

         Subject var7;
         try {
            Hashtable tmpEnv = null;
            tmpEnv = (Hashtable)this.jndiEnv.clone();
            tmpEnv.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
            tmpEnv.put("java.naming.security.principal", usr);
            tmpEnv.put("java.naming.security.credentials", pwd);
            Object[] obj;
            if (this.isRA) {
               obj = createSubjectByAnonymousforRA(tmpEnv);
            } else {
               obj = createSubjectByAnonymous(tmpEnv);
            }

            ctx = (Context)obj[0];
            Subject subject = (Subject)obj[1];
            var7 = subject;
         } catch (PrivilegedActionException var16) {
            throw new weblogic.jms.common.JMSException(var16);
         } finally {
            if (ctx != null) {
               try {
                  ctx.close();
               } catch (NamingException var15) {
               }
            }

         }

         return var7;
      } else {
         return this.subject;
      }
   }

   private Subject createSubjectForOBSJMS(String usr, String pwd, AbstractSubject connectionSubject) throws JMSException {
      if (JMSDebug.JMSOBS.isDebugEnabled()) {
         JMSDebug.JMSOBS.debug("JMSConnectionFactory:createSubjectForOBSJMS usr=" + usr + ", isOBSIC=" + this.isOBSIC + ", securityPolicy=" + JMSOBSHelper.convertSecurityPolicyToString(this.securityPolicy));
      }

      Subject ret = null;
      if (this.isOBSIC) {
         if (usr != null) {
            ret = this.createSubject(usr, pwd);
         } else if (this.securityPolicy == 2) {
            ret = SubjectUtils.getAnonymousSubject().getSubject();
         } else if (this.securityPolicy == 3) {
            ret = Security.getCurrentSubject();
         } else {
            ret = this.subject;
         }
      } else {
         if (this.securityPolicy == 1) {
            throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logInvalidSecurityPolicyLoggable(this.fullyQualifiedName, JMSOBSHelper.convertSecurityPolicyToString(this.securityPolicy)));
         }

         if (this.securityPolicy == 2) {
            if (usr != null) {
               ret = connectionSubject != null ? connectionSubject.getSubject() : null;
            } else {
               ret = SubjectUtils.getAnonymousSubject().getSubject();
            }
         } else if (this.securityPolicy == 3) {
            if (usr != null) {
               ret = connectionSubject != null ? connectionSubject.getSubject() : null;
            } else {
               ret = Security.getCurrentSubject();
            }
         }
      }

      return ret;
   }

   final ConnectionInternal createConnectionInternal(String user, String pass, boolean wantXAConnection, int connectionType) throws JMSException {
      DispatcherPartitionContext clientDPC = JMSEnvironment.getJMSEnvironment().findDispatcherPartitionContextJMSException();
      JMSConnection connection = this.setupJMSConnection(user, pass, wantXAConnection, clientDPC, connectionType);
      Object reconnectConnection;
      if (wantXAConnection) {
         reconnectConnection = new XAConnectionInternalImpl((JMSXAConnectionFactory)this, (JMSXAConnection)connection);
      } else {
         reconnectConnection = new WLConnectionImpl(this, connection);
      }

      connection.setWlConnectionImpl((WLConnectionImpl)reconnectConnection);
      int reconnectPolicyInternal;
      long blockingMillis;
      long periodMillis;
      if (JMSConnection.isT3Client()) {
         reconnectPolicyInternal = connection.getReconnectPolicyInternal();
         blockingMillis = connection.getReconnectBlockingMillisInternal();
         periodMillis = connection.getTotalReconnectPeriodMillisInternal();
      } else {
         reconnectPolicyInternal = 0;
         blockingMillis = 0L;
         periodMillis = 0L;
      }

      ((WLConnectionImpl)reconnectConnection).setReconnectPolicy(WLConnectionImpl.convertReconnectPolicy(reconnectPolicyInternal));
      ((WLConnectionImpl)reconnectConnection).setReconnectBlockingMillis(blockingMillis);
      ((WLConnectionImpl)reconnectConnection).setTotalReconnectPeriodMillis(periodMillis);
      if (this.isRA) {
         connection.setReconnectPolicy(JMSConstants.RECONNECT_POLICY_NONE);
      }

      return (ConnectionInternal)reconnectConnection;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      PeerInfo peerInfo = null;
      if (out instanceof PeerInfoable) {
         peerInfo = ((PeerInfoable)out).getPeerInfo();
      }

      byte version = 1;
      if (peerInfo != null && peerInfo.compareTo(PeerInfo.VERSION_81) >= 0) {
         if (peerInfo.compareTo(PeerInfo.VERSION_DIABLO) >= 0) {
            if (peerInfo.compareTo(PeerInfo.VERSION_1221) >= 0) {
               if (peerInfo.compareTo(PeerInfo.VERSION_122140) >= 0) {
                  version = 5;
               } else {
                  version = 4;
               }
            } else {
               version = 3;
            }
         } else {
            version = 2;
         }
      } else if (peerInfo == null) {
         version = 5;
      }

      out.writeByte(version);
      if (version >= 2) {
         out.writeByte(5);
      }

      out.writeObject(this.feConnectionFactoryRemote);
      if (version >= 3) {
         if (this.fullyQualifiedName == null) {
            out.writeUTF("");
         } else {
            out.writeUTF(this.fullyQualifiedName);
         }

         if (version >= 4) {
            if (this.serverPartitionName == null) {
               out.writeUTF("");
            } else {
               out.writeUTF(this.serverPartitionName);
            }

            if (version >= 5) {
               out.writeInt(this.securityPolicy);
               out.writeBoolean(this.isReferencedByFS);
            }
         }
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int vrsn = in.readByte();
      if (vrsn >= 1 && vrsn <= 5) {
         if (vrsn >= 2) {
            this.interfaceVersion = in.readByte();
         } else {
            this.interfaceVersion = 0;
         }

         this.feConnectionFactoryRemote = (FEConnectionFactoryRemote)PortableRemoteObject.narrow(in.readObject(), FEConnectionFactoryRemote.class);
         if (vrsn >= 3) {
            this.fullyQualifiedName = in.readUTF();
            if (this.fullyQualifiedName.length() <= 0) {
               this.fullyQualifiedName = null;
            }
         } else {
            this.fullyQualifiedName = null;
         }

         if (vrsn >= 4) {
            this.serverPartitionName = in.readUTF();
            if (this.serverPartitionName.trim().length() == 0) {
               this.serverPartitionName = "DOMAIN";
            } else {
               this.serverPartitionName = this.serverPartitionName.trim();
            }
         } else {
            this.serverPartitionName = "DOMAIN";
         }

         if (vrsn >= 5) {
            this.securityPolicy = in.readInt();
            this.isReferencedByFS = in.readBoolean();
         } else {
            this.securityPolicy = 0;
         }

      } else {
         throw JMSUtilities.versionIOException(vrsn, 1, 5);
      }
   }

   public Remote getRemoteDelegate() {
      return this.feConnectionFactoryRemote;
   }

   public String getFullyQualifiedName() {
      return this.fullyQualifiedName == null ? "" : this.fullyQualifiedName;
   }

   public void setSubject(Subject subject) {
      this.subject = subject;
   }

   public boolean containsSubject() {
      return this.subject != null;
   }

   public void setJNDIEnv(Hashtable env) {
      this.jndiEnv = env;
   }

   public void setRA(boolean value) {
      this.isRA = value;
      this.setOBSIC(value);
   }

   public void setOBSIC(boolean value) {
      this.isOBSIC = value;
   }

   public void setSecurityPolicy(String value) {
      this.securityPolicy = JMSOBSHelper.convertSecurityPolicyToInt(value);
   }

   public boolean isReferencedByFS() {
      return this.isReferencedByFS;
   }

   public void setReferencedByFS(boolean isReferencedByFS) {
      this.isReferencedByFS = isReferencedByFS;
   }

   public boolean isOBSEnabled() {
      return this.securityPolicy != 0;
   }

   public static Object[] createSubjectByAnonymousforRA(Hashtable jndiEnv) throws PrivilegedActionException, JMSException {
      decryptCredential(jndiEnv);
      return createSubjectByAnonymous(jndiEnv);
   }

   public static Object[] createSubjectByAnonymous(final Hashtable jndiEnv) throws PrivilegedActionException, JMSException {
      Object[] obj = (Object[])((Object[])CrossDomainSecurityManager.runAs(SubjectUtils.getAnonymousSubject(), (PrivilegedExceptionAction)(new PrivilegedExceptionAction() {
         public Object run() throws NamingException {
            Context ctx = new InitialContext(jndiEnv);
            Subject subject = Security.getCurrentSubject();
            return new Object[]{ctx, subject};
         }
      })));
      return obj;
   }

   public static String getOracleJMSRAProviderCustomization() {
      return "weblogic.jms.ra.providers.wl.WLProviderInfo";
   }

   static void decryptCredential(Hashtable jndiEnv) throws JMSException {
      String user = (String)jndiEnv.get("java.naming.security.principal");
      if (user != null && user.startsWith("->")) {
         user = getDecryptedValue(jndiEnv, user);
         if (user == null) {
            throw new JMSException("Could not resolve wallet alias.  Username alias can not be empty");
         }

         jndiEnv.put("java.naming.security.principal", user);
      }

      String pwd = (String)jndiEnv.get("java.naming.security.credentials");
      if (pwd != null && pwd.startsWith("->")) {
         pwd = getDecryptedValue(jndiEnv, pwd);
         if (pwd == null) {
            pwd = user;
         }

         jndiEnv.put("java.naming.security.credentials", pwd);
      }

   }

   private static String getDecryptedValue(Hashtable jndiEnv, String value) throws JMSException {
      if (value != null && value.startsWith("->")) {
         String walletDir = (String)jndiEnv.get("weblogic.jms.walletDir");
         if (walletDir == null) {
            walletDir = ".";
         }

         if (!(new File(walletDir)).exists()) {
            throw new JMSException("Could not resolve wallet alias.  Wallet directory not found: '" + walletDir + "'");
         } else if (!(new File(walletDir)).isDirectory()) {
            throw new JMSException("Could not resolve wallet alias.  The specified wallet location is not a directory: '" + walletDir + "'");
         } else {
            return getValueFromWallet(walletDir, value.substring("->".length()));
         }
      } else {
         return value;
      }
   }

   private static String getValueFromWallet(String walletLocation, String alias) throws JMSException {
      return JMSEnvironment.getJMSEnvironment().getValueFromWallet(walletLocation, alias);
   }

   public javax.jms.JMSContext createContext() {
      return new JMSContextImpl(this, getContainerType());
   }

   public javax.jms.JMSContext createContext(String userName, String password) {
      return new JMSContextImpl(this, getContainerType(), userName, password);
   }

   public javax.jms.JMSContext createContext(String userName, String password, int sessionMode) {
      return new JMSContextImpl(this, getContainerType(), userName, password, sessionMode);
   }

   public javax.jms.JMSContext createContext(int sessionMode) {
      return new JMSContextImpl(this, getContainerType(), sessionMode);
   }

   protected static ContainerType getContainerType() {
      return ContainerType.JavaSE;
   }

   static {
      String millis;
      long timeout;
      try {
         millis = System.getProperty("weblogic.jms.ReconnectBlockingMillis");
         if (millis != null) {
            timeout = Long.parseLong(millis);
            WLConnectionImpl.validateReconnectMillis(timeout);
            RECONNECT_TIMEOUT_DEFAULT = timeout;
         }
      } catch (Exception var4) {
      }

      RECONNECT_PERIOD_DEFAULT = -1L;

      try {
         millis = System.getProperty("weblogic.jms.ReconnectPeriodMillis");
         if (millis != null) {
            timeout = Long.parseLong(millis);
            WLConnectionImpl.validateReconnectMillis(timeout);
            RECONNECT_PERIOD_DEFAULT = timeout;
         }
      } catch (Exception var3) {
      }

   }
}
