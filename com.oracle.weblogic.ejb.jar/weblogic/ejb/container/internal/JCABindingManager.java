package weblogic.ejb.container.internal;

import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;
import javax.resource.spi.ActivationSpec;
import weblogic.connector.external.EndpointActivationException;
import weblogic.connector.external.EndpointActivationUtils;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.MessageDrivenBeanInfo;
import weblogic.j2ee.descriptor.ActivationConfigBean;
import weblogic.j2ee.descriptor.ActivationConfigPropertyBean;
import weblogic.management.runtime.MessageDrivenEJBRuntimeMBean;
import weblogic.utils.Debug;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.utils.bean.BeanInitializer;
import weblogic.utils.bean.ConversionException;

public final class JCABindingManager extends MDConnectionManager {
   private MessageEndpointFactoryImpl factory;
   private ActivationSpec activationSpec;

   public JCABindingManager(MessageDrivenBeanInfo mdbi, MessageDrivenEJBRuntimeMBean mb) {
      super(mdbi, mb);
   }

   protected void disconnect(boolean checkExceptions) throws EndpointActivationException {
      if (this.factory != null) {
         this.factory.setReady(false);
      }

      try {
         if (debugLogger.isDebugEnabled()) {
            this.debugWithState("ActivationSpec: " + this.activationSpec);
         }

         if (this.activationSpec != null) {
            EJBRuntimeUtils.pushEnvironment(this.mgr.getEnvironmentContext());

            try {
               this.getEAUtil().deActivateEndpoint(this.info.getDisplayName(), this.getDestinationJndi(), this.info.getMessagingTypeInterfaceName(), this.activationSpec, this.factory, this.runtimeMBean);
            } finally {
               EJBRuntimeUtils.popEnvironment();
            }
         }

         this.setState(1);
      } catch (Throwable var6) {
         if (debugLogger.isDebugEnabled()) {
            debug("FAILED to deactivate endpoint: " + StackTraceUtilsClient.throwable2StackTrace(var6));
         }

         if (var6 instanceof EndpointActivationException) {
            throw (EndpointActivationException)var6;
         } else {
            throw new EndpointActivationException("Failed to deactivate endpoint: ", var6, false);
         }
      }
   }

   protected void connect() throws EndpointActivationException {
      assert this.getState() != 2;

      if (debugLogger.isDebugEnabled()) {
         this.debugState();
      }

      try {
         ++this.reconnectionCount;
         this.activationSpec = (ActivationSpec)this.getEAUtil().getActivationSpec(this.getDestinationJndi(), this.info.getMessagingTypeInterfaceName());
         if (this.activationSpec == null) {
            throw new RuntimeException("ActivationSpec is null, cannot connect");
         }

         this.setActivationSpec(this.activationSpec, this.info.getActivationConfigBean());
         if (debugLogger.isDebugEnabled()) {
            debug("ActivationSpec: " + this.activationSpec);
         }

         this.factory = new MessageEndpointFactoryImpl(this.info);
         this.factory.setReady(true);
         EJBRuntimeUtils.pushEnvironment(this.mgr.getEnvironmentContext());

         try {
            this.getEAUtil().activateEndpoint(this.info.getDisplayName(), this.getDestinationJndi(), this.info.getMessagingTypeInterfaceName(), this.activationSpec, this.factory, this.runtimeMBean);
         } finally {
            EJBRuntimeUtils.popEnvironment();
         }

         this.info.setResourceAdapterVersion(this.getEAUtil().getAdapterSpecVersion(this.getDestinationJndi()));
         if (this.state != 3) {
            this.setState(2);
         }

         this.runtimeMBean.setMDBStatus(this.runtimeMBean.statusAsString(2));
         if (debugLogger.isDebugEnabled()) {
            this.debugWithState("Connected to Endpoint");
         }

         EJBLogger.logMDBReConnectedToResourceAdapter(this.info.getEJBName(), this.getDestinationJndi());
         if (this.mgr.shouldConnectionSuspendOnStart()) {
            EJBLogger.logSuspendJCAMDBConnectionOnDeploymentNotSupported(this.info.getDisplayName(), this.getDestinationJndi(), "weblogic.mdbs.suspendConnectionOnStart");
         }
      } catch (RuntimeException | EndpointActivationException var10) {
         if (debugLogger.isDebugEnabled()) {
            debug("FAILED to activate endpoint: " + StackTraceUtilsClient.throwable2StackTrace(var10));
         }

         throw var10;
      } finally {
         this.runtimeMBean.setJMSConnectionAlive(this.getState() == 2);
         if (this.getState() == 2) {
            this.runtimeMBean.setConnectionStatus("Connected");
         } else {
            this.runtimeMBean.setConnectionStatus("re-connecting");
         }

      }

   }

   protected void logException(Exception e) {
      if (e instanceof EndpointActivationException) {
         EJBLogger.logMDBUnableToConnectToJCA(this.info.getEJBName(), this.getDestinationJndi(), e.getMessage());
      } else if (e instanceof RuntimeException) {
         EJBLogger.logMDBUnableToConnectToJCA(this.info.getEJBName(), this.getDestinationJndi(), e.getMessage() == null ? StackTraceUtilsClient.throwable2StackTrace(e) : e.getMessage());
      }

   }

   private void setActivationSpec(ActivationSpec spec, ActivationConfigBean acBean) {
      if (acBean != null) {
         Hashtable propData = new Hashtable();
         ActivationConfigPropertyBean[] var4 = acBean.getActivationConfigProperties();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            ActivationConfigPropertyBean acp = var4[var6];
            String name = acp.getActivationConfigPropertyName();
            String value = acp.getActivationConfigPropertyValue();
            if ((!"MESSAGESELECTOR".equalsIgnoreCase(name) || value != "") && name != null && value != null) {
               propData.put(name, value);
            }
         }

         try {
            (new BeanInitializer()).initializeBean(spec, propData);
         } catch (ConversionException var10) {
            Debug.say("Failed to set ActivationSpec. " + var10.getMessage());
         }

      }
   }

   public void onRAUndeploy() {
      this.setState(6);
      this.activationSpec = null;
      Thread currentThread = Thread.currentThread();
      ClassLoader clSave = currentThread.getContextClassLoader();
      currentThread.setContextClassLoader(this.info.getClassLoader());

      try {
         this.scheduleReconnection();
      } finally {
         if (clSave != null) {
            currentThread.setContextClassLoader(clSave);
         }

      }

   }

   public synchronized boolean suspend(boolean byUser) {
      try {
         int currentState = this.getState();
         if (this.activationSpec != null && currentState == 2) {
            this.getEAUtil().suspendInbound(this.getDestinationJndi(), this.factory, (Properties)null);
            this.factory.setReady(false);
            this.runtimeMBean.setMDBStatus(this.runtimeMBean.statusAsStringInStartCase(3) + " at " + new Date() + " by the user.");
         } else {
            EJBLogger.logAttemptToSuspendNonConnectedMDB(this.info.getEJBName(), this.getStateNameById(currentState));
         }

         return true;
      } catch (EndpointActivationException var3) {
         if (debugLogger.isDebugEnabled()) {
            debug("FAILED to suspend endpoint: " + StackTraceUtilsClient.throwable2StackTrace(var3));
         }

         throw new RuntimeException(var3);
      }
   }

   public synchronized boolean resume(boolean byUser) {
      try {
         this.getEAUtil().resumeInbound(this.getDestinationJndi(), this.factory, (Properties)null);
         this.factory.setReady(true);
         this.runtimeMBean.setMDBStatus(this.runtimeMBean.statusAsString(2));
         return true;
      } catch (EndpointActivationException var3) {
         if (debugLogger.isDebugEnabled()) {
            debug("FAILED to suspend endpoint: " + StackTraceUtilsClient.throwable2StackTrace(var3));
         }

         throw new RuntimeException(var3);
      }
   }

   private EndpointActivationUtils getEAUtil() {
      return EndpointActivationUtils.accessor;
   }

   private static void debug(String s) {
      debugLogger.debug("[JCABindingManager] " + s);
   }
}
