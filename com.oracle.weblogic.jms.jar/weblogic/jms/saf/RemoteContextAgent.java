package weblogic.jms.saf;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.security.AccessControlException;
import java.security.AccessController;
import java.util.HashMap;
import java.util.Map;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.SAFLoginContextBean;
import weblogic.j2ee.descriptor.wl.SAFRemoteContextBean;
import weblogic.jms.forwarder.Forwarder;
import weblogic.jms.forwarder.ReplyHandler;
import weblogic.jms.forwarder.RuntimeHandler;
import weblogic.jndi.ClientEnvironmentFactory;
import weblogic.management.EncryptionHelper;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.utils.GenericBeanListener;
import weblogic.messaging.kernel.Queue;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;
import weblogic.store.xa.PersistentStoreXA;
import weblogic.t3.srvr.T3Srvr;
import weblogic.work.WorkManager;

public class RemoteContextAgent {
   private static final AbstractSubject kernelID = getKernelIdentity();
   private final String rcBeanFullyQualifiedName;
   private final SAFRemoteContextBean rcBean;
   private static final HashMap RC_ATTRIBUTES = new HashMap();
   private static final HashMap LC_ATTRIBUTES = new HashMap();
   private GenericBeanListener rcChangeListener;
   private GenericBeanListener lcChangeListener;
   Forwarder forwarder;
   ReplyHandler replyHandler;
   private boolean isInitialized;
   private static AuthenticatedSubject KERNEL_ID;
   private static RuntimeAccess runtimeAccess;
   private ServerStateChangeListener stateChangeListener;

   private static final AbstractSubject getKernelIdentity() {
      try {
         return (AbstractSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());
      } catch (AccessControlException var1) {
         return null;
      }
   }

   public RemoteContextAgent(String rcBeanFullyQualifiedName, SAFRemoteContextBean rcBean, ReplyHandler paramReplyHandler, ClientEnvironmentFactory environmentFactory) {
      this.rcBeanFullyQualifiedName = rcBeanFullyQualifiedName;
      this.rcBean = rcBean;
      this.replyHandler = paramReplyHandler;
      this.forwarder = new Forwarder(rcBean == null, this.replyHandler, environmentFactory);
      this.initializeListeners();
      if (T3Srvr.getT3Srvr().getRunState() == 2) {
         this.forwarder.start();
      } else {
         this.stateChangeListener = new ServerStateChangeListener();
         runtimeAccess.getServerRuntime().addPropertyChangeListener(this.stateChangeListener);
      }

   }

   private void initializeListeners() {
      if (this.rcBean == null) {
         this.isInitialized = true;
      } else {
         this.rcChangeListener = new GenericBeanListener((DescriptorBean)this.rcBean, this, RC_ATTRIBUTES, (Map)null);
         SAFLoginContextBean lcb = this.rcBean.getSAFLoginContext();
         this.lcChangeListener = new GenericBeanListener((DescriptorBean)lcb, this, LC_ATTRIBUTES, (Map)null);

         try {
            this.rcChangeListener.initialize();
            this.lcChangeListener.initialize();
            this.isInitialized = true;
         } catch (ManagementException var3) {
            throw new AssertionError(var3);
         }
      }
   }

   void closeListeners() {
      if (this.rcChangeListener != null) {
         this.rcChangeListener.close();
      }

      if (this.lcChangeListener != null) {
         this.lcChangeListener.close();
      }

   }

   public String toString() {
      return this.rcBeanFullyQualifiedName;
   }

   public String getRcBeanFullyQualifiedName() {
      return this.rcBeanFullyQualifiedName;
   }

   public SAFRemoteContextBean getRcBean() {
      return this.rcBean;
   }

   public boolean isLocalServerContext() {
      return this.rcBean == null;
   }

   public void addForwarder(PersistentStoreXA persistentStore, WorkManager workManager, RuntimeHandler remoteEndpoint, Queue sourceQueue, String targetJNDI, int nonPersistentQos, int persistentQos, String exactlyOnceLoadBalancingPolicy) {
      this.forwarder.addSubforwarder(persistentStore, workManager, remoteEndpoint, sourceQueue, targetJNDI, nonPersistentQos, persistentQos, exactlyOnceLoadBalancingPolicy);
   }

   void removeForwarder(Queue sourceQueue, String targetJNDI) {
      this.forwarder.removeSubforwarder(sourceQueue);
   }

   public void setCompressionThreshold(int compressionThreshold) {
      this.forwarder.setCompressionThreshold(compressionThreshold);
   }

   public void setReplyToSAFRemoteContextName(String replyToRemoteContextName) {
      this.replyHandler.setReplyToSAFRemoteContextName(replyToRemoteContextName);
   }

   public void setLoginURL(String loginURL) {
      if (this.isInitialized) {
         this.forwarder.stop();
      }

      this.forwarder.setLoginURL(loginURL);
      if (this.isInitialized) {
         this.forwarder.start();
      }

   }

   public void setUsername(String username) {
      if (this.isInitialized) {
         this.forwarder.stop();
      }

      this.forwarder.setUsername(username);
      if (this.isInitialized) {
         this.forwarder.start();
      }

   }

   public void setPassword(String password) {
      if (this.isInitialized) {
         this.forwarder.stop();
      }

      this.forwarder.setPassword(password);
      if (this.isInitialized) {
         this.forwarder.start();
      }

   }

   public void setPasswordEncrypted(byte[] passwordEncrypted) {
      if (this.isInitialized) {
         this.forwarder.stop();
      }

      this.forwarder.setPassword(EncryptionHelper.decryptString(passwordEncrypted, (AuthenticatedSubject)kernelID));
      if (this.isInitialized) {
         this.forwarder.start();
      }

   }

   public void setRetryDelayBase(long retryDelayBase) {
      this.forwarder.setRetryDelayBase(retryDelayBase);
   }

   public void setRetryDelayMaximum(long retryDelayMaximum) {
      this.forwarder.setRetryDelayMaximum(retryDelayMaximum);
   }

   public void setRetryDelayMultiplier(double retryDelayMultiplier) {
      this.forwarder.setRetryDelayMultiplier(retryDelayMultiplier);
   }

   public void setWindowSize(int windowSize) {
      this.forwarder.setWindowSize(windowSize);
   }

   public void setWindowInterval(long windowInterval) {
      this.forwarder.setWindowInterval(windowInterval);
   }

   static {
      RC_ATTRIBUTES.put("CompressionThreshold", Integer.TYPE);
      RC_ATTRIBUTES.put("ReplyToSAFRemoteContextName", String.class);
      LC_ATTRIBUTES.put("LoginURL", String.class);
      LC_ATTRIBUTES.put("Username", String.class);
      LC_ATTRIBUTES.put("Password", String.class);
      LC_ATTRIBUTES.put("PasswordEncrypted", byte[].class);
      KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      runtimeAccess = ManagementService.getRuntimeAccess(KERNEL_ID);
   }

   class ServerStateChangeListener implements PropertyChangeListener {
      public void propertyChange(PropertyChangeEvent evt) {
         if ("State".equals(evt.getPropertyName()) && "RUNNING".equals((String)evt.getNewValue())) {
            RemoteContextAgent.this.forwarder.start();
         }

      }
   }
}
