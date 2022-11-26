package weblogic.ejb.container.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.util.Map;
import java.util.Properties;
import javax.resource.ResourceException;
import javax.resource.spi.UnavailableException;
import javax.resource.spi.endpoint.MessageEndpoint;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.transaction.xa.XAResource;
import weblogic.connector.external.SuspendableEndpointFactory;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.interfaces.MessageDrivenBeanInfo;
import weblogic.ejb.container.manager.MessageDrivenManager;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.utils.StackTraceUtilsClient;

public final class MessageEndpointFactoryImpl implements MessageEndpointFactory, SuspendableEndpointFactory {
   private static final DebugLogger DEBUG_LOGGER;
   private final MessageDrivenBeanInfo beanInfo;
   private final MessageDrivenManager beanManager;
   private volatile boolean ready;
   static final long serialVersionUID = -8540092137655856973L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.ejb.container.internal.MessageEndpointFactoryImpl");
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Before_Inbound;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Around_Inbound;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_After_Inbound;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;

   public MessageEndpointFactoryImpl(MessageDrivenBeanInfo mdbi) {
      this.beanInfo = mdbi;
      this.beanManager = (MessageDrivenManager)this.beanInfo.getBeanManager();
   }

   public MessageEndpoint createEndpoint(XAResource res) throws UnavailableException {
      LocalHolder var4;
      if ((var4 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var4.argsCapture) {
            var4.args = InstrumentationSupport.toSensitive(2);
         }

         if (var4.monitorHolder[0] != null) {
            var4.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.process(var4);
         }

         if (var4.monitorHolder[1] != null) {
            var4.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.preProcess(var4);
         }

         var4.resetPostBegin();
      }

      MessageEndpoint var10000;
      try {
         if (!this.ready) {
            throw new UnavailableException("MessageEndpointFactory Unavailable. It is either not ready or suspended by the user.");
         }

         MessageEndpoint mdo = this.allocateMDO(res);
         if (mdo instanceof Remote) {
            try {
               mdo = (MessageEndpoint)ServerHelper.replaceAndResolveRemoteObject((Remote)mdo);
            } catch (Exception var7) {
               debug("Error calling ServerHelper.replaceAndResolveRemoteObject(). " + StackTraceUtilsClient.throwable2StackTrace(var7));
            }
         }

         var10000 = mdo;
      } catch (Throwable var8) {
         if (var4 != null) {
            var4.th = var8;
            var4.ret = null;
            if (var4.monitorHolder[2] != null) {
               var4.monitorIndex = 2;
               InstrumentationSupport.createDynamicJoinPoint(var4);
               InstrumentationSupport.process(var4);
            }

            if (var4.monitorHolder[1] != null) {
               var4.monitorIndex = 1;
               InstrumentationSupport.createDynamicJoinPoint(var4);
               InstrumentationSupport.postProcess(var4);
            }
         }

         throw var8;
      }

      if (var4 != null) {
         var4.ret = var10000;
         if (var4.monitorHolder[2] != null) {
            var4.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.process(var4);
         }

         if (var4.monitorHolder[1] != null) {
            var4.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.postProcess(var4);
         }
      }

      return var10000;
   }

   private MessageEndpoint allocateMDO(XAResource res) {
      try {
         Class mdoClass = this.beanInfo.getMessageDrivenLocalObjectClass();
         MessageDrivenLocalObject mdo = new MessageDrivenLocalObject(res);
         mdo.setBeanManager(this.beanManager);
         mdo.setBeanInfo(this.beanInfo);
         return (MessageEndpoint)mdoClass.getConstructor(MessageDrivenLocalObject.class).newInstance(mdo);
      } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException var4) {
         throw new AssertionError(var4);
      }
   }

   public MessageEndpoint createEndpoint(XAResource res, long var2) throws UnavailableException {
      LocalHolder var4;
      if ((var4 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var4.argsCapture) {
            var4.args = InstrumentationSupport.toSensitive(3);
         }

         if (var4.monitorHolder[0] != null) {
            var4.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.process(var4);
         }

         if (var4.monitorHolder[1] != null) {
            var4.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.preProcess(var4);
         }

         var4.resetPostBegin();
      }

      MessageEndpoint var10000;
      try {
         var10000 = this.createEndpoint(res);
      } catch (Throwable var6) {
         if (var4 != null) {
            var4.th = var6;
            var4.ret = null;
            if (var4.monitorHolder[2] != null) {
               var4.monitorIndex = 2;
               InstrumentationSupport.createDynamicJoinPoint(var4);
               InstrumentationSupport.process(var4);
            }

            if (var4.monitorHolder[1] != null) {
               var4.monitorIndex = 1;
               InstrumentationSupport.createDynamicJoinPoint(var4);
               InstrumentationSupport.postProcess(var4);
            }
         }

         throw var6;
      }

      if (var4 != null) {
         var4.ret = var10000;
         if (var4.monitorHolder[2] != null) {
            var4.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.process(var4);
         }

         if (var4.monitorHolder[1] != null) {
            var4.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.postProcess(var4);
         }
      }

      return var10000;
   }

   public boolean isDeliveryTransacted(Method method) throws NoSuchMethodException {
      return this.beanInfo.isDeliveryTransacted(method);
   }

   public void setReady(boolean ready) {
      this.ready = ready;
   }

   public void suspend(Properties props) throws ResourceException {
      this.setReady(false);
   }

   public void resume(Properties props) throws ResourceException {
      this.setReady(true);
   }

   public boolean isSuspended() {
      return this.ready;
   }

   public void disconnect() throws ResourceException {
      this.beanManager.onRAUndeploy();
   }

   public String getActivationName() {
      return this.beanInfo.getDisplayName();
   }

   public Class getEndpointClass() {
      return this.beanInfo.getBeanClass();
   }

   private static void debug(String s) {
      DEBUG_LOGGER.debug("[MessageEndpointFactoryImpl] " + s);
   }

   static {
      _WLDF$INST_FLD_Connector_Before_Inbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Before_Inbound");
      _WLDF$INST_FLD_Connector_Around_Inbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Around_Inbound");
      _WLDF$INST_FLD_Connector_After_Inbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_After_Inbound");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "MessageEndpointFactoryImpl.java", "weblogic.ejb.container.internal.MessageEndpointFactoryImpl", "createEndpoint", "(Ljavax/transaction/xa/XAResource;)Ljavax/resource/spi/endpoint/MessageEndpoint;", 47, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_Before_Inbound, _WLDF$INST_FLD_Connector_Around_Inbound, _WLDF$INST_FLD_Connector_After_Inbound};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "MessageEndpointFactoryImpl.java", "weblogic.ejb.container.internal.MessageEndpointFactoryImpl", "createEndpoint", "(Ljavax/transaction/xa/XAResource;J)Ljavax/resource/spi/endpoint/MessageEndpoint;", 81, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_Before_Inbound, _WLDF$INST_FLD_Connector_Around_Inbound, _WLDF$INST_FLD_Connector_After_Inbound};
      DEBUG_LOGGER = EJBDebugService.mdbConnectionLogger;
   }
}
