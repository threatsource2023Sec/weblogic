package weblogic.jms.adapter;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.jms.TransactionRolledBackException;
import javax.jms.XAConnection;
import javax.jms.XAConnectionFactory;
import javax.jms.XAQueueConnection;
import javax.jms.XAQueueConnectionFactory;
import javax.jms.XAQueueSession;
import javax.jms.XASession;
import javax.jms.XATopicConnection;
import javax.jms.XATopicConnectionFactory;
import javax.jms.XATopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.transaction.xa.XAResource;
import weblogic.common.internal.PeerInfo;
import weblogic.connector.exception.NoEnlistXAResourceException;
import weblogic.jms.bridge.AdapterConnectionMetaData;
import weblogic.jms.bridge.GenericMessage;
import weblogic.jms.bridge.LocalTransaction;
import weblogic.jms.bridge.NotificationListener;
import weblogic.jms.bridge.ResourceTransactionRolledBackException;
import weblogic.jms.bridge.SourceConnection;
import weblogic.jms.bridge.TargetConnection;
import weblogic.jms.bridge.TemporaryResourceException;
import weblogic.jms.bridge.internal.BridgeDebug;
import weblogic.jms.client.ConnectionInternal;
import weblogic.jms.client.JMSConnectionFactory;
import weblogic.jms.client.XASessionInternal;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.extensions.JMSForwardHelper;
import weblogic.jms.extensions.MDBTransaction;
import weblogic.jms.extensions.WLConnection;
import weblogic.jms.extensions.WLMessageProducer;
import weblogic.security.SubjectUtils;
import weblogic.security.WLSPrincipals;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.transaction.internal.IgnoreXAResource;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManagerFactory;

public class JMSBaseConnection implements SourceConnection, TargetConnection, MessageListener, ExceptionListener {
   private JMSManagedConnectionFactory mcf;
   private JMSManagedConnection mc;
   protected ConnectionFactory cf;
   protected XAConnectionFactory xcf;
   protected Connection connection;
   protected XASession xaSession;
   protected Session session;
   protected Destination destination;
   private MessageProducer messageProducer;
   private MessageConsumer messageConsumer;
   private MessageListener mlistener;
   private ExceptionListener elistener;
   private String name;
   private String fullName;
   private String userName;
   private String password;
   private String destJNDI;
   private int destType;
   private String cfJNDI;
   private String url;
   private String icFactory;
   private String selector;
   private AdapterConnectionMetaData metaData;
   private boolean transacted;
   private boolean durable;
   private boolean isXA;
   private int ackMode = 1;
   private boolean ignoreXA = false;
   static final int QUEUE = 0;
   static final int TOPIC = 1;
   private boolean closed = true;
   private ClassLoader classLoader;
   private static AuthenticatedSubject kernelId;
   private AuthenticatedSubject subject;
   private boolean forwardMethodAvailable = false;
   private boolean preserveMsgProperty = false;
   private int logCount = 0;
   private static final boolean DEBUG = false;
   private static final String[] ISOLATING_PACKAGES = new String[]{"weblogic.*", "COM.rsa.*"};
   Object onExceptionLock = new Object();

   JMSBaseConnection(String userName, String password, JMSManagedConnectionFactory mcf, String name, String fullName, String url, String icFactory, String cfJNDI, String destJNDI, String destinationType, String selector, boolean durable, String classPath, boolean preserveMsgProperty) throws Exception {
      this.userName = userName;
      this.password = password;
      this.mcf = mcf;
      this.name = name;
      this.fullName = fullName;
      this.url = url;
      this.icFactory = icFactory;
      this.destJNDI = destJNDI;
      if (destinationType != null) {
         if (destinationType.equalsIgnoreCase("Queue")) {
            this.destType = 0;
         } else if (destinationType.equalsIgnoreCase("Topic")) {
            this.destType = 1;
         }
      } else {
         this.destType = 0;
      }

      this.cfJNDI = cfJNDI;
      this.selector = selector;
      this.durable = durable;
      this.preserveMsgProperty = preserveMsgProperty;
      if (classPath != null && classPath.length() > 0) {
         this.classLoader = new IsolatingClassLoader("JMS Interop Adapter Class Loader", getClassPath(classPath), ISOLATING_PACKAGES, true);
      }

      kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   private static URL[] getClassPath(String classpathString) throws MalformedURLException {
      ArrayList result = new ArrayList();
      StringTokenizer tokenizer = new StringTokenizer(classpathString, File.pathSeparator);

      while(tokenizer.hasMoreTokens()) {
         String filename = tokenizer.nextToken();
         if (filename.length() > 0) {
            result.add((new File(filename)).toURL());
         }
      }

      return (URL[])((URL[])result.toArray(new URL[0]));
   }

   public void start() throws ResourceException {
      boolean sendEvent = true;
      if (this.classLoader != null) {
         Thread currentThread = Thread.currentThread();
         ClassLoader clSave = currentThread.getContextClassLoader();

         try {
            currentThread.setContextClassLoader(this.classLoader);
            this.startInternal();
         } catch (ResourceException var10) {
            this.closeInternal();
            if (var10 instanceof ResourceAdapterInternalException) {
               sendEvent = false;
            }

            this.throwResourceException("Error starting connection", var10, sendEvent);
         } finally {
            currentThread.setContextClassLoader(clSave);
         }
      } else {
         try {
            this.startInternal();
         } catch (ResourceException var9) {
            this.closeInternal();
            if (var9 instanceof ResourceAdapterInternalException) {
               sendEvent = false;
            }

            this.throwResourceException("Error starting connection", var9, sendEvent);
         }
      }

   }

   private synchronized void startInternal() throws ResourceException {
      if (this.connection == null || this.closed) {
         Object cfObj = null;
         Object destObj = null;
         final Context ctx = null;

         try {
            AuthenticatedSubject as = SubjectUtils.getAnonymousSubject();
            as.setQOS((byte)101);

            try {
               ctx = (Context)as.doAs(kernelId, new PrivilegedExceptionAction() {
                  public Object run() throws Exception {
                     return JMSBaseConnection.this.getInitialContext();
                  }
               });
            } catch (PrivilegedActionException var20) {
               if (var20.getCause() instanceof NamingException) {
                  throw (NamingException)var20.getCause();
               }

               throw new AssertionError(var20);
            }

            final String clientId;
            label567: {
               if (this.cfJNDI != null && this.cfJNDI.length() != 0) {
                  try {
                     clientId = this.cfJNDI;
                     cfObj = this.subject.doAs(kernelId, new PrivilegedExceptionAction() {
                        public Object run() throws Exception {
                           return ctx.lookup(clientId);
                        }
                     });
                     if (cfObj == null) {
                        throw new ResourceAdapterInternalException("Adapter internal error -- connectionFactory object null from JNDI " + this.cfJNDI);
                     }

                     if (cfObj instanceof JMSConnectionFactory && ((JMSConnectionFactory)cfObj).isOBSEnabled()) {
                        ((JMSConnectionFactory)cfObj).setSecurityPolicy("ThreadBased");
                        if (JMSDebug.JMSOBS.isDebugEnabled()) {
                           JMSDebug.JMSOBS.debug("The messaging bridge destination's connection factory security policy should not be ObjectBasedDelegated, ObjectBasedAnonymous or ObjectBased, if security policy is one of them, change it to ThreadBased internally. ");
                        }
                     }
                  } catch (PrivilegedActionException var22) {
                     if (var22.getCause() instanceof NamingException) {
                        throw (NamingException)var22.getCause();
                     }

                     throw new AssertionError(var22);
                  }

                  if (this.destJNDI != null && this.destJNDI.length() != 0) {
                     try {
                        clientId = this.destJNDI;
                        destObj = this.subject.doAs(kernelId, new PrivilegedExceptionAction() {
                           public Object run() throws Exception {
                              return ctx.lookup(clientId);
                           }
                        });
                        if (destObj != null) {
                           break label567;
                        }

                        throw new ResourceAdapterInternalException("Adapter internal error -- destination object null from JNDI " + this.destJNDI);
                     } catch (PrivilegedActionException var21) {
                        if (var21.getCause() instanceof NamingException) {
                           throw (NamingException)var21.getCause();
                        }

                        throw new AssertionError(var21);
                     }
                  }

                  throw new ResourceAdapterInternalException("Adapter internal error -- Destination JNDI name is null");
               }

               throw new ResourceAdapterInternalException("Adapter internal error -- ConnectionFactory JNDI name is null");
            }

            if (cfObj instanceof XAConnectionFactory) {
               this.xcf = (XAConnectionFactory)cfObj;
               this.isXA = true;
            } else {
               if (!(cfObj instanceof ConnectionFactory)) {
                  throw new ResourceAdapterInternalException("Adapter internal error -- connectionFactory object is not instanceof ConnectionFactory " + cfObj.getClass().getName());
               }

               this.cf = (ConnectionFactory)cfObj;
            }

            if (this.ignoreXA) {
               this.isXA = false;
            }

            if (destObj instanceof Queue && !(destObj instanceof Topic)) {
               this.destType = 0;
            } else if (!(destObj instanceof Queue) && destObj instanceof Topic) {
               this.destType = 1;
            }

            if (this.destType == 0) {
               this.destination = (Queue)destObj;
               if (this.isXA) {
                  if (this.userName != null) {
                     if (this.xcf instanceof XAQueueConnectionFactory) {
                        this.connection = ((XAQueueConnectionFactory)this.xcf).createXAQueueConnection(this.userName, this.password);
                     } else if (this.xcf instanceof XAConnectionFactory) {
                        this.connection = this.xcf.createXAConnection(this.userName, this.password);
                     }
                  } else if (this.xcf instanceof XAQueueConnectionFactory) {
                     this.connection = ((XAQueueConnectionFactory)this.xcf).createXAQueueConnection();
                  } else if (this.xcf instanceof XAConnectionFactory) {
                     this.connection = this.xcf.createXAConnection();
                  }

                  if (this.connection instanceof XAQueueConnection) {
                     this.xaSession = ((XAQueueConnection)this.connection).createXAQueueSession();
                     this.session = ((XAQueueSession)this.xaSession).getQueueSession();
                  } else {
                     this.xaSession = ((XAConnection)this.connection).createXASession();
                     this.session = this.xaSession.getSession();
                  }
               } else {
                  if (this.userName != null) {
                     if (this.cf instanceof QueueConnectionFactory) {
                        this.connection = ((QueueConnectionFactory)this.cf).createQueueConnection(this.userName, this.password);
                     } else {
                        this.connection = this.cf.createConnection(this.userName, this.password);
                     }
                  } else if (this.cf instanceof QueueConnectionFactory) {
                     this.connection = ((QueueConnectionFactory)this.cf).createQueueConnection();
                  } else {
                     this.connection = this.cf.createConnection();
                  }

                  if (this.connection instanceof QueueConnection) {
                     this.session = ((QueueConnection)this.connection).createQueueSession(this.transacted, this.ackMode);
                  } else {
                     this.session = this.connection.createSession(this.transacted, this.ackMode);
                  }
               }
            } else {
               if (this.destType != 1) {
                  throw new ResourceAdapterInternalException("Adapter internal error -- Found non-JMS objects");
               }

               this.destination = (Topic)destObj;
               clientId = null;
               if (this.durable) {
                  clientId = "MessagingBridge." + this.fullName;
               }

               if (this.isXA) {
                  if (this.userName != null) {
                     if (this.xcf instanceof XATopicConnectionFactory) {
                        this.connection = ((XATopicConnectionFactory)this.xcf).createXATopicConnection(this.userName, this.password);
                     } else {
                        this.connection = this.xcf.createXAConnection(this.userName, this.password);
                     }
                  } else if (this.xcf instanceof XATopicConnectionFactory) {
                     this.connection = ((XATopicConnectionFactory)this.xcf).createXATopicConnection();
                  } else {
                     this.connection = this.xcf.createXAConnection();
                  }

                  if (this.durable && (this.connection.getClientID() == null || this.connection.getClientID().length() == 0)) {
                     this.connection.setClientID(clientId);
                  }

                  if (this.connection instanceof XATopicConnection) {
                     this.xaSession = ((XATopicConnection)this.connection).createXATopicSession();
                     this.session = ((XATopicSession)this.xaSession).getTopicSession();
                  } else {
                     this.xaSession = ((XAConnection)this.connection).createXASession();
                     this.session = this.xaSession.getSession();
                  }
               } else {
                  if (this.userName != null) {
                     if (this.cf instanceof TopicConnectionFactory) {
                        this.connection = ((TopicConnectionFactory)this.cf).createTopicConnection(this.userName, this.password);
                     } else {
                        this.connection = this.cf.createConnection(this.userName, this.password);
                     }
                  } else if (this.cf instanceof TopicConnectionFactory) {
                     this.connection = ((TopicConnectionFactory)this.cf).createTopicConnection();
                  } else {
                     this.connection = this.cf.createConnection();
                  }

                  if (this.durable && (this.connection.getClientID() == null || this.connection.getClientID().length() == 0)) {
                     this.connection.setClientID(clientId);
                  }

                  if (this.connection instanceof TopicConnection) {
                     this.session = ((TopicConnection)this.connection).createTopicSession(this.transacted, this.ackMode);
                  } else {
                     this.session = this.connection.createSession(this.transacted, this.ackMode);
                  }
               }
            }
         } catch (NamingException var23) {
            if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
               BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Exception:", var23);
            }

            if (ctx == null) {
               this.throwResourceException("ConnectionFactory: failed to get initial context (InitialContextFactory =" + this.icFactory + ", url = " + this.url + ", user name = " + this.userName + ")", var23, false);
            }

            if (cfObj == null) {
               if (this.logCount++ < 2) {
                  throw new TemporaryResourceException();
               }

               this.throwResourceException("ConnectionFactory: " + this.cfJNDI + " not found", var23, false);
            }

            if (destObj == null) {
               if (this.logCount++ < 2) {
                  throw new TemporaryResourceException();
               }

               this.throwResourceException("Destination: " + this.destJNDI + " not found", var23, false);
            }
         } catch (Throwable var24) {
            if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
               BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Exception:", var24);
               Exception le = null;
               if (var24 instanceof JMSException) {
                  le = ((JMSException)var24).getLinkedException();
                  if (le != null) {
                     BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Exception:", le);
                  }
               }
            }

            this.throwResourceException("Failed to start the connection", var24, false);
         } finally {
            try {
               if (ctx != null) {
                  ctx.close();
               }

               ctx = null;
               cfObj = null;
               destObj = null;
            } catch (NamingException var19) {
            }

         }

         this.closed = false;
         if (this.connection instanceof ConnectionInternal) {
            PeerInfo peerInfo = ((ConnectionInternal)this.connection).getFEPeerInfo();
            if (peerInfo.compareTo(PeerInfo.VERSION_DIABLO) >= 0) {
               this.forwardMethodAvailable = true;
            }
         }

         if (this.connection instanceof WLConnection) {
            ((WLConnection)this.connection).setReconnectPolicy(WLConnection.RECONNECT_POLICY_NONE);
         }

         JMSManagedConnectionFactory.printInfo(this.mcf.getLogWriter(), this.name, "Connection started to " + this.destJNDI);
      }
   }

   public void close() throws ResourceException {
      synchronized(this) {
         if (this.closed) {
            return;
         }
      }

      if (this.classLoader != null) {
         Thread currentThread = Thread.currentThread();
         ClassLoader clSave = currentThread.getContextClassLoader();

         try {
            currentThread.setContextClassLoader(this.classLoader);
            SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedAction() {
               public Object run() {
                  JMSBaseConnection.this.closeInternal();
                  return null;
               }
            });
         } finally {
            currentThread.setContextClassLoader(clSave);
         }

      } else {
         SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedAction() {
            public Object run() {
               JMSBaseConnection.this.closeInternal();
               return null;
            }
         });
      }
   }

   private synchronized void closeInternal() {
      this.closed = true;

      try {
         if (this.connection != null) {
            this.connection.stop();
         }
      } catch (JMSException var5) {
      }

      try {
         this.closeSession();
      } catch (JMSException var4) {
      }

      if (this.connection != null) {
         try {
            this.connection.close();
         } catch (JMSException var3) {
         }

         this.connection = null;
      }

      try {
         JMSManagedConnectionFactory.printInfo(this.mcf.getLogWriter(), this.name, "Connection closed to " + this.destJNDI);
      } catch (ResourceException var2) {
      }

   }

   void cleanup() {
      synchronized(this) {
         if (this.closed) {
            return;
         }
      }

      if (this.classLoader != null) {
         Thread currentThread = Thread.currentThread();
         ClassLoader clSave = currentThread.getContextClassLoader();

         try {
            currentThread.setContextClassLoader(this.classLoader);
            SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedAction() {
               public Object run() {
                  JMSBaseConnection.this.cleanupInternal();
                  return null;
               }
            });
         } finally {
            currentThread.setContextClassLoader(clSave);
         }

      } else {
         SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedAction() {
            public Object run() {
               JMSBaseConnection.this.cleanupInternal();
               return null;
            }
         });
      }
   }

   private synchronized void cleanupInternal() {
      if (this.messageProducer != null) {
         try {
            this.messageProducer.close();
         } catch (JMSException var3) {
         }

         this.messageProducer = null;
      }

      if (this.messageConsumer != null) {
         try {
            this.messageConsumer.close();
         } catch (JMSException var2) {
         }

         this.messageConsumer = null;
         this.mlistener = null;
      }

   }

   private synchronized void closeSession() throws JMSException {
      JMSException firstJMSException = null;
      if (this.messageProducer != null) {
         try {
            this.messageProducer.close();
         } catch (JMSException var6) {
            if (firstJMSException == null) {
               firstJMSException = var6;
            }
         }

         this.messageProducer = null;
      }

      if (this.messageConsumer != null) {
         try {
            this.messageConsumer.close();
         } catch (JMSException var5) {
            if (firstJMSException == null) {
               firstJMSException = var5;
            }
         }

         this.messageConsumer = null;
         this.mlistener = null;
      }

      if (this.xaSession != null) {
         try {
            this.xaSession.close();
         } catch (JMSException var4) {
            if (firstJMSException == null) {
               firstJMSException = var4;
            }
         }

         this.xaSession = null;
         this.session = null;
      } else if (this.session != null) {
         try {
            this.session.close();
         } catch (JMSException var3) {
            if (firstJMSException == null) {
               firstJMSException = var3;
            }
         }

         this.session = null;
      }

      if (firstJMSException != null) {
         throw firstJMSException;
      }
   }

   public void pause() throws ResourceException {
      throw new NotSupportedException("pause() -- Not supported!");
   }

   public void resume() throws ResourceException {
      throw new NotSupportedException("resume() -- Not supported!");
   }

   public synchronized LocalTransaction getLocalTransaction() throws ResourceException {
      return new AdapterLocalTransactionImpl(this.mc);
   }

   public void send(final Message msg) throws ResourceException {
      boolean sendEvent = false;
      if (this.classLoader != null) {
         Thread currentThread = Thread.currentThread();
         ClassLoader clSave = currentThread.getContextClassLoader();

         try {
            currentThread.setContextClassLoader(this.classLoader);
            SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedExceptionAction() {
               public Object run() throws ResourceException {
                  JMSBaseConnection.this.sendInternal(msg);
                  return null;
               }
            });
         } catch (PrivilegedActionException var17) {
            synchronized(this) {
               if (this.connection == null || this.session == null) {
                  sendEvent = true;
               }
            }

            this.throwResourceException("Error sending message", var17.getException(), sendEvent);
            return;
         } finally {
            currentThread.setContextClassLoader(clSave);
         }

      } else {
         final Message msgFinal = msg;

         try {
            SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedExceptionAction() {
               public Object run() throws ResourceException {
                  JMSBaseConnection.this.sendInternal(msgFinal);
                  return null;
               }
            });
         } catch (PrivilegedActionException var19) {
            synchronized(this) {
               if (this.connection == null || this.session == null) {
                  sendEvent = true;
               }
            }

            this.throwResourceException("Error sending message", var19.getException(), sendEvent);
         }
      }
   }

   private synchronized void sendInternal(Message msg) throws ResourceException {
      try {
         if (this.messageProducer == null) {
            this.ensureStarted();
            if (this.session == null || this.destination == null) {
               JMSManagedConnectionFactory.printError(this.mcf.getLogWriter(), this.name, "Internal error -- invalid state!");
               throw new ResourceAdapterInternalException("Bridge Adapter internal error -- invalid state!");
            }

            if (this.destType == 0 && this.session instanceof QueueSession) {
               this.messageProducer = ((QueueSession)this.session).createSender((Queue)this.destination);
            } else if (this.destType == 1 && this.session instanceof TopicSession) {
               this.messageProducer = ((TopicSession)this.session).createPublisher((Topic)this.destination);
            } else {
               this.messageProducer = this.session.createProducer(this.destination);
            }

            this.connection.start();
         }

         if (this.preserveMsgProperty) {
            if (this.forwardMethodAvailable && msg instanceof MessageImpl) {
               ((MessageImpl)msg).setJMSXUserID((String)null);
               ((MessageImpl)msg).requestJMSXUserID(false);
               ((MessageImpl)msg).setSAFSequenceName((String)null);
               ((MessageImpl)msg).setSAFSeqNumber(0L);
               JMSForwardHelper.ForwardFromMessage((WLMessageProducer)this.messageProducer, msg, false);
            } else {
               long timeToLive = JMSForwardHelper.getRelativeTimeToLive(msg);
               if (timeToLive < 0L) {
                  timeToLive = 1L;
               }

               if (this.destType == 0) {
                  ((QueueSender)this.messageProducer).send(msg, msg.getJMSDeliveryMode(), msg.getJMSPriority(), timeToLive);
               } else if (this.destType == 1) {
                  ((TopicPublisher)this.messageProducer).publish(msg, msg.getJMSDeliveryMode(), msg.getJMSPriority(), timeToLive);
               }
            }
         } else {
            if (msg instanceof MessageImpl) {
               ((MessageImpl)msg).setDeliveryCount(0);
            }

            if (this.destType == 0) {
               if (this.messageProducer instanceof QueueSender) {
                  ((QueueSender)this.messageProducer).send(msg);
               } else {
                  this.messageProducer.send(msg);
               }
            } else if (this.destType == 1) {
               if (this.messageProducer instanceof TopicPublisher) {
                  ((TopicPublisher)this.messageProducer).publish(msg);
               } else {
                  this.messageProducer.send(msg);
               }
            }
         }
      } catch (Throwable var4) {
         if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
            BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Exception:", var4);
            Exception le = null;
            if (var4 instanceof JMSException) {
               le = ((JMSException)var4).getLinkedException();
               if (le != null) {
                  BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Exception:", le);
               }
            }
         }

         JMSManagedConnectionFactory.printError(this.mcf.getLogWriter(), this.name, "Failed to send a message");
         this.throwResourceException("Error creating producer or sending message", var4, false);
      }

   }

   public synchronized void send(GenericMessage msg) throws ResourceException {
      throw new NotSupportedException("Not implemented");
   }

   private Message receiveFromQueue(QueueReceiver receiver, long timeout) throws JMSException {
      if (timeout < 0L) {
         return receiver.receive();
      } else {
         return timeout == 0L ? receiver.receiveNoWait() : receiver.receive(timeout);
      }
   }

   private Message receiveFromTopic(TopicSubscriber subscriber, long timeout) throws JMSException {
      if (timeout < 0L) {
         return subscriber.receive();
      } else {
         return timeout == 0L ? subscriber.receiveNoWait() : subscriber.receive(timeout);
      }
   }

   private Message receiveFromDestination(MessageConsumer consumer, long timeout) throws JMSException {
      if (timeout < 0L) {
         return consumer.receive();
      } else {
         return timeout == 0L ? consumer.receiveNoWait() : consumer.receive(timeout);
      }
   }

   private Message receiveCommon(final long timeout) throws ResourceException {
      boolean sendEvent = true;
      if (this.classLoader != null) {
         Thread currentThread = Thread.currentThread();
         ClassLoader clSave = currentThread.getContextClassLoader();

         Message var8;
         try {
            currentThread.setContextClassLoader(this.classLoader);
            var8 = (Message)SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedExceptionAction() {
               public Object run() throws ResourceException {
                  return JMSBaseConnection.this.receiveInternal(timeout);
               }
            });
         } catch (PrivilegedActionException var13) {
            if (var13.getException() instanceof ResourceAdapterInternalException) {
               sendEvent = false;
            }

            this.throwResourceException("Error receiving message", var13.getException(), sendEvent);
            return null;
         } finally {
            currentThread.setContextClassLoader(clSave);
         }

         return var8;
      } else {
         final long timeoutFinal = timeout;

         try {
            return (Message)SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedExceptionAction() {
               public Object run() throws ResourceException {
                  return JMSBaseConnection.this.receiveInternal(timeoutFinal);
               }
            });
         } catch (PrivilegedActionException var15) {
            if (var15.getException() instanceof ResourceAdapterInternalException) {
               sendEvent = false;
            }

            this.throwResourceException("Error receiving message", var15.getException(), sendEvent);
            return null;
         }
      }
   }

   private synchronized Message receiveInternal(long timeout) throws ResourceException {
      Exception le;
      if (this.messageConsumer != null) {
         try {
            if (this.destType == 0) {
               if (this.messageConsumer instanceof QueueReceiver) {
                  return this.receiveFromQueue((QueueReceiver)this.messageConsumer, timeout);
               }

               return this.receiveFromDestination(this.messageConsumer, timeout);
            }

            if (this.destType == 1) {
               if (this.messageConsumer instanceof TopicSubscriber) {
                  return this.receiveFromTopic((TopicSubscriber)this.messageConsumer, timeout);
               }

               return this.receiveFromDestination(this.messageConsumer, timeout);
            }

            JMSManagedConnectionFactory.printError(this.mcf.getLogWriter(), this.name, "Internal error -- invalid state!");
            throw new ResourceAdapterInternalException("Adapter internal error:  detect non-JMS objects in creating consumer or receiving message");
         } catch (Throwable var6) {
            this.messageConsumer = null;
            if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
               BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Exception:", var6);
               le = null;
               if (var6 instanceof JMSException) {
                  le = ((JMSException)var6).getLinkedException();
                  if (le != null) {
                     BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Exception:", le);
                  }
               }
            }

            JMSManagedConnectionFactory.printError(this.mcf.getLogWriter(), this.name, "Failed to receive a message!");
            this.throwResourceException("Error receiving message", var6, false);
         }
      }

      this.ensureStarted();
      if (this.session != null && this.destination != null) {
         try {
            if (this.destType != 0) {
               if (this.destType != 1) {
                  JMSManagedConnectionFactory.printError(this.mcf.getLogWriter(), this.name, "Internal error -- invalid state!");
                  throw new ResourceAdapterInternalException("Error creating consumer or receiving message -- internal error");
               }

               if (this.selector != null && this.selector.trim().length() > 0) {
                  if (this.durable) {
                     if (this.session instanceof TopicSession) {
                        this.messageConsumer = ((TopicSession)this.session).createDurableSubscriber((Topic)this.destination, this.name, this.selector, true);
                     } else {
                        this.messageConsumer = this.session.createDurableSubscriber((Topic)this.destination, this.name, this.selector, true);
                     }
                  } else if (this.session instanceof TopicSession) {
                     this.messageConsumer = ((TopicSession)this.session).createSubscriber((Topic)this.destination, this.selector, true);
                  } else {
                     this.messageConsumer = this.session.createConsumer(this.destination, this.selector, true);
                  }
               } else if (this.durable) {
                  if (this.session instanceof TopicSession) {
                     this.messageConsumer = ((TopicSession)this.session).createDurableSubscriber((Topic)this.destination, this.name);
                  } else {
                     this.messageConsumer = this.session.createDurableSubscriber((Topic)this.destination, this.name);
                  }
               } else if (this.session instanceof TopicSession) {
                  this.messageConsumer = ((TopicSession)this.session).createSubscriber((Topic)this.destination);
               } else {
                  this.messageConsumer = this.session.createConsumer(this.destination);
               }

               this.connection.start();
               if (this.messageConsumer instanceof TopicSubscriber) {
                  return this.receiveFromTopic((TopicSubscriber)this.messageConsumer, timeout);
               }

               return this.receiveFromDestination(this.messageConsumer, timeout);
            }

            if (this.selector != null && this.selector.trim().length() > 0) {
               if (this.session instanceof QueueSession) {
                  this.messageConsumer = ((QueueSession)this.session).createReceiver((Queue)this.destination, this.selector);
               } else {
                  this.messageConsumer = this.session.createConsumer(this.destination, this.selector);
               }
            } else if (this.session instanceof QueueSession) {
               this.messageConsumer = ((QueueSession)this.session).createReceiver((Queue)this.destination);
            } else {
               this.messageConsumer = this.session.createConsumer(this.destination);
            }

            this.connection.start();
            if (this.messageConsumer instanceof QueueReceiver) {
               return this.receiveFromQueue((QueueReceiver)this.messageConsumer, timeout);
            }

            return this.receiveFromDestination(this.messageConsumer, timeout);
         } catch (Throwable var5) {
            if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
               BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Exception:", var5);
               le = null;
               if (var5 instanceof JMSException) {
                  le = ((JMSException)var5).getLinkedException();
                  if (le != null) {
                     BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Exception:", le);
                  }
               }
            }

            JMSManagedConnectionFactory.printError(this.mcf.getLogWriter(), this.name, "Failed to receive a messag");
            this.throwResourceException("Error creating consumer or receiving message", var5, false);
         }
      }

      JMSManagedConnectionFactory.printError(this.mcf.getLogWriter(), this.name, "Internal error -- invalid state!");
      throw new ResourceAdapterInternalException("Error receiving message -- internal error");
   }

   public GenericMessage receiveGenericMessage() throws ResourceException {
      throw new NotSupportedException("Not implemented");
   }

   public Message receive() throws ResourceException {
      return this.receiveCommon(-1L);
   }

   public Message receive(long timeout) throws ResourceException {
      return this.receiveCommon(timeout);
   }

   public GenericMessage receiveGenericMessage(long timeout) throws ResourceException {
      throw new NotSupportedException("Not implemented");
   }

   public synchronized Message createMessage(Message msg) throws ResourceException {
      return msg;
   }

   public Message createMessage(GenericMessage msg) throws ResourceException {
      throw new NotSupportedException("Not implemented");
   }

   public GenericMessage createGenericMessage(Message msg) throws ResourceException {
      throw new NotSupportedException("Not implemented");
   }

   public GenericMessage createGenericMessage(GenericMessage msg) throws ResourceException {
      throw new NotSupportedException("Not implemented");
   }

   public void setMessageListener(final MessageListener mListener) throws ResourceException {
      boolean sendEvent = true;
      this.mlistener = mListener;
      if (this.classLoader != null) {
         Thread currentThread = Thread.currentThread();
         ClassLoader clSave = currentThread.getContextClassLoader();

         try {
            currentThread.setContextClassLoader(this.classLoader);
            if (this.messageConsumer == null) {
               SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedExceptionAction() {
                  public Object run() throws ResourceException {
                     JMSBaseConnection.this.createConsumer();
                     return null;
                  }
               });
            }

            SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedExceptionAction() {
               public Object run() throws ResourceException {
                  if (mListener == null) {
                     JMSBaseConnection.this.setMessageListenerInternal((MessageListener)null);
                  } else {
                     JMSBaseConnection.this.setMessageListenerInternal(JMSBaseConnection.this);
                  }

                  return null;
               }
            });
         } catch (PrivilegedActionException var11) {
            if (var11.getException() instanceof ResourceAdapterInternalException) {
               sendEvent = false;
            }

            this.throwResourceException("Error setting message listener", var11.getException(), sendEvent);
            return;
         } finally {
            currentThread.setContextClassLoader(clSave);
         }

      } else {
         final MessageListener mListenerFinal = mListener;
         final JMSBaseConnection thisFinal = this;

         try {
            if (this.messageConsumer == null) {
               SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedExceptionAction() {
                  public Object run() throws ResourceException {
                     JMSBaseConnection.this.createConsumer();
                     return null;
                  }
               });
            }

            SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedExceptionAction() {
               public Object run() throws ResourceException {
                  if (mListenerFinal == null) {
                     JMSBaseConnection.this.setMessageListenerInternal((MessageListener)null);
                  } else {
                     JMSBaseConnection.this.setMessageListenerInternal(thisFinal);
                  }

                  return null;
               }
            });
         } catch (PrivilegedActionException var13) {
            if (var13.getException() instanceof ResourceAdapterInternalException) {
               sendEvent = false;
            }

            this.throwResourceException("Error setting message listener", var13.getException(), sendEvent);
         }
      }
   }

   private synchronized void setMessageListenerInternal(MessageListener mListener) throws ResourceException {
      if (this.messageConsumer != null) {
         try {
            this.messageConsumer.setMessageListener(mListener);
            JMSManagedConnectionFactory.printInfo(this.mcf.getLogWriter(), this.name, "MessageListener is set on " + this.destJNDI);
            return;
         } catch (Throwable var3) {
            if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
               BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Exception:", var3);
            }

            JMSManagedConnectionFactory.printError(this.mcf.getLogWriter(), this.name, "Failed to set a message listener on " + this.destJNDI);
            this.throwResourceException("Error setting message listener", var3, false);
         }
      }

   }

   private synchronized void createConsumer() throws ResourceException {
      this.ensureStarted();
      if (this.session != null && this.destination != null) {
         try {
            if (this.destType == 0) {
               if (this.selector != null && this.selector.trim().length() > 0) {
                  if (this.session instanceof QueueSession) {
                     this.messageConsumer = ((QueueSession)this.session).createReceiver((Queue)this.destination, this.selector);
                  } else {
                     this.messageConsumer = this.session.createConsumer(this.destination, this.selector);
                  }
               } else if (this.session instanceof QueueSession) {
                  this.messageConsumer = ((QueueSession)this.session).createReceiver((Queue)this.destination);
               } else {
                  this.messageConsumer = this.session.createConsumer(this.destination);
               }
            } else if (this.destType == 1) {
               if (this.selector != null && this.selector.trim().length() > 0) {
                  if (this.durable) {
                     if (this.session instanceof TopicSession) {
                        this.messageConsumer = ((TopicSession)this.session).createDurableSubscriber((Topic)this.destination, this.name, this.selector, true);
                     } else {
                        this.messageConsumer = this.session.createDurableSubscriber((Topic)this.destination, this.name, this.selector, true);
                     }
                  } else if (this.session instanceof TopicSession) {
                     this.messageConsumer = ((TopicSession)this.session).createSubscriber((Topic)this.destination, this.selector, true);
                  } else {
                     this.messageConsumer = this.session.createConsumer(this.destination, this.selector, true);
                  }
               } else if (this.durable) {
                  if (this.session instanceof TopicSession) {
                     this.messageConsumer = ((TopicSession)this.session).createDurableSubscriber((Topic)this.destination, this.name);
                  } else {
                     this.messageConsumer = this.session.createDurableSubscriber((Topic)this.destination, this.name);
                  }
               } else if (this.session instanceof TopicSession) {
                  this.messageConsumer = ((TopicSession)this.session).createSubscriber((Topic)this.destination);
               } else {
                  this.messageConsumer = this.session.createConsumer(this.destination);
               }
            }

            this.connection.start();
            JMSManagedConnectionFactory.printInfo(this.mcf.getLogWriter(), this.name, "Consumer created on " + this.destJNDI);
            return;
         } catch (Throwable var2) {
            if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
               BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Exception:", var2);
            }

            JMSManagedConnectionFactory.printError(this.mcf.getLogWriter(), this.name, "Failed to create consumer " + this.destJNDI);
            this.throwResourceException("Error creating asynchronous consumer ", var2, false);
         }
      }

      JMSManagedConnectionFactory.printError(this.mcf.getLogWriter(), this.name, "Internal error -- invalid state!");
      throw new ResourceAdapterInternalException("Bridge Adapter internal error -- invalid state!");
   }

   public void addNotificationListener(NotificationListener nListener, int event) throws ResourceException {
      throw new NotSupportedException("Not implemented");
   }

   public void removeNotificationListener(NotificationListener nListener, int event) throws ResourceException {
      throw new NotSupportedException("Not implemented");
   }

   public void associateTransaction(final Message msg) throws ResourceException {
      if (this.classLoader != null) {
         Thread currentThread = Thread.currentThread();
         ClassLoader clSave = currentThread.getContextClassLoader();

         try {
            currentThread.setContextClassLoader(this.classLoader);
            SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedExceptionAction() {
               public Object run() throws ResourceException {
                  JMSBaseConnection.this.associateTransactionInternal(msg);
                  return null;
               }
            });
         } catch (PrivilegedActionException var9) {
            this.throwResourceException("Error associating message with current transaction", var9.getException(), false);
            return;
         } finally {
            currentThread.setContextClassLoader(clSave);
         }

      } else {
         try {
            SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedExceptionAction() {
               public Object run() throws ResourceException {
                  JMSBaseConnection.this.associateTransactionInternal(msg);
                  return null;
               }
            });
         } catch (PrivilegedActionException var11) {
            this.throwResourceException("Error associating message with current transaction", var11.getException(), false);
         }
      }
   }

   private synchronized void associateTransactionInternal(Message msg) throws ResourceException {
      if (this.session instanceof MDBTransaction) {
         try {
            ((MDBTransaction)this.session).associateTransaction(msg);
         } catch (Throwable var3) {
            if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
               BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Exception:", var3);
            }

            this.throwResourceException("Failed to associate a message with the current transaction", var3, false);
         }

      } else {
         throw new NotSupportedException("Not implemented");
      }
   }

   public void associateTransaction(GenericMessage msg) throws ResourceException {
      throw new NotSupportedException("Not implemented");
   }

   private void throwResourceException(String info, Throwable ex) throws ResourceException {
      this.throwResourceException(info, ex, true);
   }

   private void throwResourceException(String info, Throwable ex, boolean sendEvent) throws ResourceException {
      if (sendEvent) {
         this.mc.sendEvent(5, (Exception)null);
      }

      ResourceException re = null;
      if (ex != null && ex instanceof ResourceException) {
         re = (ResourceException)ex;
      } else if (ex != null && ex instanceof TransactionRolledBackException) {
         re = new ResourceTransactionRolledBackException("Transaction rolled back");
         ((ResourceException)re).setLinkedException((Exception)ex);
      } else {
         re = new ResourceException(info);
         if (ex != null && ex instanceof Exception) {
            ((ResourceException)re).setLinkedException((Exception)ex);
         }
      }

      throw re;
   }

   public XAResource getXAResource() throws ResourceException {
      if (this.classLoader != null) {
         Thread currentThread = Thread.currentThread();
         ClassLoader clSave = currentThread.getContextClassLoader();

         XAResource var3;
         try {
            currentThread.setContextClassLoader(this.classLoader);
            var3 = (XAResource)SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedExceptionAction() {
               public Object run() throws ResourceException {
                  return JMSBaseConnection.this.getXAResourceInternal();
               }
            });
         } catch (PrivilegedActionException var8) {
            if (var8.getException() instanceof NoEnlistXAResourceException) {
               throw (NoEnlistXAResourceException)var8.getException();
            }

            this.throwResourceException("Error getting XA resource", var8.getException());
            return null;
         } finally {
            currentThread.setContextClassLoader(clSave);
         }

         return var3;
      } else {
         try {
            return (XAResource)SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedExceptionAction() {
               public Object run() throws ResourceException {
                  return JMSBaseConnection.this.getXAResourceInternal();
               }
            });
         } catch (PrivilegedActionException var10) {
            if (var10.getException() instanceof NoEnlistXAResourceException) {
               throw (NoEnlistXAResourceException)var10.getException();
            } else {
               this.throwResourceException("Error getting XA resource", var10.getException());
               return null;
            }
         }
      }
   }

   private synchronized XAResource getXAResourceInternal() throws ResourceException {
      if (this.xaSession != null && !(this.xaSession instanceof XASessionInternal)) {
         XAResource xares = this.xaSession.getXAResource();
         if (xares instanceof IgnoreXAResource) {
            throw new NoEnlistXAResourceException("No need to enlist this resource");
         } else {
            return xares;
         }
      } else {
         throw new NoEnlistXAResourceException("No need to enlist this resource");
      }
   }

   public synchronized boolean implementsMDBTransaction() throws ResourceException {
      return this.session != null && this.session instanceof MDBTransaction;
   }

   public synchronized boolean isXAConnection() throws ResourceException {
      return this.xcf != null && this.xcf instanceof XAConnectionFactory;
   }

   public void setExceptionListener(final ExceptionListener eListener) throws ResourceException {
      this.elistener = eListener;
      if (this.classLoader != null) {
         Thread currentThread = Thread.currentThread();
         ClassLoader clSave = currentThread.getContextClassLoader();

         try {
            currentThread.setContextClassLoader(this.classLoader);
            SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedExceptionAction() {
               public Object run() throws ResourceException {
                  JMSBaseConnection.this.setExceptionListenerInternal(eListener);
                  return null;
               }
            });
         } catch (PrivilegedActionException var9) {
            this.throwResourceException("Error setting exception listener", var9.getException());
            return;
         } finally {
            currentThread.setContextClassLoader(clSave);
         }

      } else {
         final ExceptionListener eListenerFinal = eListener;

         try {
            SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedExceptionAction() {
               public Object run() throws ResourceException {
                  JMSBaseConnection.this.setExceptionListenerInternal(eListenerFinal);
                  return null;
               }
            });
         } catch (PrivilegedActionException var11) {
            this.throwResourceException("Error setting exception listener", var11.getException());
         }
      }
   }

   private synchronized void setExceptionListenerInternal(ExceptionListener eListener) throws ResourceException {
      if (this.connection != null) {
         try {
            this.connection.setExceptionListener(eListener);
         } catch (Throwable var3) {
            if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
               BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Exception:", var3);
            }

            this.throwResourceException("Error setting exception listener", var3, false);
         }
      }

   }

   public void setAcknowledgeMode(final int mode) throws ResourceException {
      if (this.classLoader != null) {
         Thread currentThread = Thread.currentThread();
         ClassLoader clSave = currentThread.getContextClassLoader();

         try {
            currentThread.setContextClassLoader(this.classLoader);
            SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedExceptionAction() {
               public Object run() throws ResourceException {
                  JMSBaseConnection.this.setAcknowledgeModeInternal(mode);
                  return null;
               }
            });
         } catch (PrivilegedActionException var9) {
            this.throwResourceException("Error setting acknowledge mode", var9.getException());
            return;
         } finally {
            currentThread.setContextClassLoader(clSave);
         }

      } else {
         final int modeFinal = mode;

         try {
            SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedExceptionAction() {
               public Object run() throws ResourceException {
                  JMSBaseConnection.this.setAcknowledgeModeInternal(modeFinal);
                  return null;
               }
            });
         } catch (PrivilegedActionException var11) {
            this.throwResourceException("Error setting acknowledge mode", var11.getException());
         }
      }
   }

   private synchronized void setAcknowledgeModeInternal(int mode) throws ResourceException {
      if (mode != this.ackMode) {
         this.ignoreXA = false;
         switch (mode) {
            case 1:
               this.ackMode = 1;
               break;
            case 2:
               this.ackMode = 2;
               break;
            case 3:
               this.ackMode = 3;
               break;
            default:
               this.ackMode = 1;
               this.ignoreXA = true;
         }

         if (this.connection != null) {
            try {
               this.closeSession();
            } catch (Exception var3) {
            }

            try {
               if (this.destType == 0 && this.connection instanceof QueueConnection) {
                  this.session = ((QueueConnection)this.connection).createQueueSession(this.transacted, this.ackMode);
               } else if (this.destType == 1 && this.connection instanceof TopicConnection) {
                  this.session = ((TopicConnection)this.connection).createTopicSession(this.transacted, this.ackMode);
               } else {
                  this.session = this.connection.createSession(this.transacted, this.ackMode);
               }
            } catch (Throwable var4) {
               if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
                  BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Exception:", var4);
               }

               this.throwResourceException("Error starting a transaction", var4, false);
            }

         }
      }
   }

   public AdapterConnectionMetaData getMetaData() throws ResourceException {
      return this.metaData;
   }

   public void recover() throws ResourceException {
      try {
         if (this.session.getTransacted()) {
            return;
         }
      } catch (JMSException var9) {
         this.throwResourceException("Error recovering messages", var9, false);
      }

      if (this.classLoader != null) {
         Thread currentThread = Thread.currentThread();
         ClassLoader clSave = currentThread.getContextClassLoader();

         try {
            currentThread.setContextClassLoader(this.classLoader);
            final Session sessionFinal = this.session;
            SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedExceptionAction() {
               public Object run() throws ResourceException {
                  try {
                     sessionFinal.recover();
                  } catch (Throwable var2) {
                     if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
                        BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Exception:", var2);
                     }

                     JMSBaseConnection.this.throwResourceException("Error recovering messages", var2, false);
                  }

                  return null;
               }
            });
         } catch (Throwable var10) {
            if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
               BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Exception:", var10);
            }

            this.throwResourceException("Error recovering messages", var10, false);
            return;
         } finally {
            currentThread.setContextClassLoader(clSave);
         }

      } else {
         try {
            final Session sessionFinal = this.session;
            SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedExceptionAction() {
               public Object run() throws ResourceException {
                  try {
                     sessionFinal.recover();
                  } catch (Throwable var2) {
                     if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
                        BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Exception:", var2);
                     }

                     JMSBaseConnection.this.throwResourceException("Error recovering messages", var2, false);
                  }

                  return null;
               }
            });
         } catch (Throwable var12) {
            if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
               BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Exception:", var12);
            }

            this.throwResourceException("Error recovering messages", var12, false);
         }
      }
   }

   void createTransactedSession() throws ResourceException {
      boolean sendEvent = true;
      if (this.classLoader != null) {
         Thread currentThread = Thread.currentThread();
         ClassLoader clSave = currentThread.getContextClassLoader();

         try {
            currentThread.setContextClassLoader(this.classLoader);
            SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedExceptionAction() {
               public Object run() throws ResourceException {
                  JMSBaseConnection.this.createTransactedSessionInternal();
                  return null;
               }
            });
         } catch (PrivilegedActionException var9) {
            if (var9.getException() instanceof ResourceAdapterInternalException) {
               sendEvent = false;
            }

            this.throwResourceException("Error creating transacted session", var9.getException(), sendEvent);
            return;
         } finally {
            currentThread.setContextClassLoader(clSave);
         }

      } else {
         try {
            SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedExceptionAction() {
               public Object run() throws ResourceException {
                  JMSBaseConnection.this.createTransactedSessionInternal();
                  return null;
               }
            });
         } catch (PrivilegedActionException var11) {
            if (var11.getException() instanceof ResourceAdapterInternalException) {
               sendEvent = false;
            }

            this.throwResourceException("Error creating transacted session", var11.getException(), sendEvent);
         }
      }
   }

   private synchronized void createTransactedSessionInternal() throws ResourceException {
      if (!this.transacted) {
         if (this.connection == null) {
            this.transacted = true;
         } else {
            MessageListener mListener = null;
            if (this.messageConsumer != null) {
               try {
                  mListener = this.messageConsumer.getMessageListener();
               } catch (Throwable var5) {
                  if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
                     BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Exception:", var5);
                  }

                  this.throwResourceException("Error restore message listener", var5, false);
               }
            }

            try {
               this.closeSession();
            } catch (Exception var3) {
            }

            try {
               if (this.destType == 0 && this.connection instanceof QueueConnection) {
                  this.session = ((QueueConnection)this.connection).createQueueSession(true, this.ackMode);
               } else if (this.destType == 1 && this.connection instanceof TopicConnection) {
                  this.session = ((TopicConnection)this.connection).createTopicSession(true, this.ackMode);
               } else {
                  this.session = this.connection.createSession(true, this.ackMode);
               }

               if (this.mlistener != null) {
                  if (this.messageConsumer == null) {
                     this.createConsumer();
                  }

                  this.setMessageListenerInternal(this.mlistener);
               }
            } catch (Throwable var4) {
               if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
                  BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Exception:", var4);
               }

               this.throwResourceException("Error beginning a transaction", var4, false);
            }

            this.transacted = true;
         }
      }
   }

   void commit() throws ResourceException {
      if (!this.transacted) {
         this.throwResourceException("Error committing a transaction -- not transacted", (Throwable)null, false);
      }

      if (this.classLoader != null) {
         Thread currentThread = Thread.currentThread();
         ClassLoader clSave = currentThread.getContextClassLoader();

         try {
            currentThread.setContextClassLoader(this.classLoader);
            final Session sessionFinal = this.session;
            SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedExceptionAction() {
               public Object run() throws ResourceException {
                  try {
                     sessionFinal.commit();
                  } catch (Throwable var2) {
                     JMSBaseConnection.this.throwResourceException("Error committing a transaction", var2, false);
                  }

                  return null;
               }
            });
         } catch (PrivilegedActionException var8) {
            if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
               BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Exception:", var8.getException());
            }

            this.throwResourceException("Error committing a transaction", var8.getException());
            return;
         } finally {
            currentThread.setContextClassLoader(clSave);
         }

      } else {
         try {
            final Session sessionFinal = this.session;
            SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedExceptionAction() {
               public Object run() throws ResourceException {
                  try {
                     sessionFinal.commit();
                  } catch (Throwable var2) {
                     JMSBaseConnection.this.throwResourceException("Error committing a transaction", var2, false);
                  }

                  return null;
               }
            });
         } catch (PrivilegedActionException var10) {
            if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
               BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Exception:", var10.getException());
            }

            this.throwResourceException("Error committing a transaction", var10.getException());
         }
      }
   }

   void rollback() throws ResourceException {
      if (!this.transacted) {
         this.throwResourceException("Error rolling back a transaction -- not transacted", (Throwable)null, false);
      }

      if (this.classLoader != null) {
         Thread currentThread = Thread.currentThread();
         ClassLoader clSave = currentThread.getContextClassLoader();

         try {
            currentThread.setContextClassLoader(this.classLoader);
            final Session sessionFinal = this.session;
            SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedExceptionAction() {
               public Object run() throws ResourceException {
                  try {
                     sessionFinal.rollback();
                  } catch (Throwable var2) {
                     JMSBaseConnection.this.throwResourceException("Error rolling back a transaction", var2, false);
                  }

                  return null;
               }
            });
         } catch (PrivilegedActionException var8) {
            if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
               BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Exception:", var8.getException());
            }

            this.throwResourceException("Error rolling back a transaction", var8.getException());
            return;
         } finally {
            currentThread.setContextClassLoader(clSave);
         }

      } else {
         try {
            final Session sessionFinal = this.session;
            SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedExceptionAction() {
               public Object run() throws ResourceException {
                  try {
                     sessionFinal.rollback();
                  } catch (Throwable var2) {
                     JMSBaseConnection.this.throwResourceException("Error rolling back a transaction", var2, false);
                  }

                  return null;
               }
            });
         } catch (PrivilegedActionException var10) {
            if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
               BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Exception:", var10.getException());
            }

            this.throwResourceException("Error rolling back a transaction", var10.getException());
         }
      }
   }

   void setManagedConnection(JMSManagedConnection mc) {
      this.mc = mc;
      this.metaData = new AdapterConnectionMetaDataImpl(mc, this.mcf);
   }

   private InitialContext getInitialContext() throws NamingException {
      InitialContext ic = null;
      if (this.url != null || this.userName != null && this.password != null) {
         Hashtable env = new Hashtable();
         if (this.userName != null && this.password != null) {
            env.put("java.naming.security.principal", this.userName);
            env.put("java.naming.security.credentials", this.password);
         }

         env.put("java.naming.factory.initial", this.icFactory);
         if (this.url != null) {
            env.put("java.naming.provider.url", this.url);
         }

         ic = new InitialContext(env);
      } else {
         ic = new InitialContext();
      }

      this.subject = SecurityServiceManager.getCurrentSubject(kernelId);
      String subjectUserName = SubjectUtils.getUsername(this.subject);
      if (WLSPrincipals.isKernelUsername(subjectUserName)) {
         this.subject = SubjectUtils.getAnonymousSubject();
         this.subject.setQOS((byte)101);
      }

      return ic;
   }

   private void ensureStarted() throws ResourceException {
      if (this.closed) {
         try {
            this.startInternal();
         } catch (ResourceException var2) {
            this.closeInternal();
            throw var2;
         }

         this.closed = false;
      }

   }

   public void onMessage(final Message msg) {
      try {
         final Session sessionFinal = this.session;
         final MessageListener mlistenerFinal = this.mlistener;
         SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedExceptionAction() {
            public Object run() {
               if (mlistenerFinal != null) {
                  mlistenerFinal.onMessage(msg);
                  return null;
               } else {
                  if (sessionFinal != null) {
                     try {
                        sessionFinal.recover();
                     } catch (Throwable var2) {
                        if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
                           BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Exception:", var2);
                        }
                     }
                  }

                  return null;
               }
            }
         });
      } catch (final Throwable var5) {
         if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
            BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Exception:", var5);
         }

         WorkManagerFactory.getInstance().getSystem().schedule(new WorkAdapter() {
            public void run() {
               JMSBaseConnection.this.onException(new weblogic.jms.common.JMSException("Fail to call runAs()", var5));
            }
         });
      }

   }

   public void onException(JMSException jmse) {
      synchronized(this.onExceptionLock) {
         if (this.elistener != null) {
            this.elistener.onException(jmse);
         }

      }
   }

   public void acknowledge(final Message msg) throws ResourceException {
      if (this.classLoader != null) {
         Thread currentThread = Thread.currentThread();
         ClassLoader clSave = currentThread.getContextClassLoader();

         try {
            currentThread.setContextClassLoader(this.classLoader);
            SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedExceptionAction() {
               public Object run() throws ResourceException {
                  try {
                     msg.acknowledge();
                  } catch (Throwable var2) {
                     JMSBaseConnection.this.throwResourceException("Error acknowledging messages", var2, false);
                  }

                  return null;
               }
            });
         } catch (PrivilegedActionException var9) {
            if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
               BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Exception:", var9.getException());
            }

            this.throwResourceException("Error acknowledge messages", var9.getException(), false);
            return;
         } finally {
            currentThread.setContextClassLoader(clSave);
         }

      } else {
         try {
            SecurityServiceManager.runAs(kernelId, this.subject, new PrivilegedExceptionAction() {
               public Object run() throws ResourceException {
                  try {
                     msg.acknowledge();
                  } catch (Throwable var2) {
                     JMSBaseConnection.this.throwResourceException("Error acknowledging messages", var2, false);
                  }

                  return null;
               }
            });
         } catch (PrivilegedActionException var11) {
            if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
               BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Exception:", var11.getException());
            }

            this.throwResourceException("Error acknowledging messages", var11.getException(), false);
         }
      }
   }
}
