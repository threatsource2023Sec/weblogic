package weblogic.connector.external.impl;

import com.oracle.injection.InjectionException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.InvalidPropertyException;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterAssociation;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.transaction.SystemException;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.connector.common.Debug;
import weblogic.connector.common.RACollectionManager;
import weblogic.connector.common.RAInstanceManager;
import weblogic.connector.common.Utils;
import weblogic.connector.exception.RAException;
import weblogic.connector.extensions.Suspendable;
import weblogic.connector.external.ActivationSpecFindOrCreateException;
import weblogic.connector.external.ActivationSpecInfo;
import weblogic.connector.external.ConfigPropInfo;
import weblogic.connector.external.ElementNotFoundException;
import weblogic.connector.external.EndpointActivationException;
import weblogic.connector.external.InboundInfo;
import weblogic.connector.external.MissingPropertiesException;
import weblogic.connector.external.PropSetterTable;
import weblogic.connector.external.RAInfo;
import weblogic.connector.external.RequiredConfigPropInfo;
import weblogic.connector.external.ResourceAdapterNotActiveException;
import weblogic.connector.external.ResourceAdapterNotFoundException;
import weblogic.connector.inbound.RAInboundManager;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.management.runtime.MessageDrivenEJBRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.PlatformConstants;

public class EndpointActivationUtils implements weblogic.connector.external.EndpointActivationUtils {
   private static final String CLASS_NAME = "weblogic.connector.external.impl.EndpointActivationUtils";
   private static final EndpointActivationUtils SINGLETON;
   static final long serialVersionUID = 5980471790071884643L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.connector.external.impl.EndpointActivationUtils");
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Before_Inbound;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Around_Inbound;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_After_Inbound;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Endpoint_Activate_Low;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Endpoint_Deactivate_Low;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;

   private EndpointActivationUtils() {
   }

   public static weblogic.connector.external.EndpointActivationUtils getAccessor() {
      return SINGLETON;
   }

   public void activateEndpoint(String ejbName, String jndiName, String messageListenerType, ActivationSpec activationSpec, MessageEndpointFactory endpointFactory, MessageDrivenEJBRuntimeMBean mdbRuntime) throws ResourceAdapterNotFoundException, MissingPropertiesException, EndpointActivationException {
      LocalHolder var14;
      if ((var14 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var14.argsCapture) {
            var14.args = new Object[7];
            Object[] var10000 = var14.args;
            var10000[0] = this;
            var10000[1] = ejbName;
            var10000[2] = jndiName;
            var10000[3] = messageListenerType;
            var10000[4] = activationSpec;
            var10000[5] = endpointFactory;
            var10000[6] = mdbRuntime;
         }

         if (var14.monitorHolder[0] != null) {
            var14.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var14);
            InstrumentationSupport.process(var14);
         }

         if (var14.monitorHolder[1] != null) {
            var14.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var14);
            InstrumentationSupport.process(var14);
         }

         if (var14.monitorHolder[2] != null) {
            var14.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var14);
            InstrumentationSupport.preProcess(var14);
         }

         if (var14.monitorHolder[3] != null) {
            var14.monitorIndex = 3;
            InstrumentationSupport.createDynamicJoinPoint(var14);
            InstrumentationSupport.process(var14);
         }

         var14.resetPostBegin();
      }

      try {
         AuthenticatedSubject kernelId = getKernelId();
         if (Debug.isRALifecycleEnabled()) {
            Debug.raLifecycle("weblogic.connector.external.impl.EndpointActivationUtils.activateEndpoint called, ejbName = " + ejbName + ", jndiName = " + jndiName + ", messageListenerType = " + messageListenerType + ", .. )");
         }

         checkStringArg("activateEndPoint()", "ejbName", ejbName);
         checkStringArg("activateEndPoint()", "jndiName", jndiName);
         checkStringArg("activateEndpoint()", "messageListenerType", messageListenerType);
         checkObjectArg("activateEndpoint()", "activationSpec", activationSpec);
         checkObjectArg("activateEndpoint()", "endpointFactory", endpointFactory);
         RAInstanceManager raIM = RACollectionManager.getRAInstanceManager(jndiName);
         String errMsg;
         if (raIM == null) {
            errMsg = Debug.getExceptionRANotDeployed(jndiName);
            throw new ResourceAdapterNotFoundException(errMsg);
         }

         if (!raIM.isActivated()) {
            errMsg = Debug.getExceptionRANotActive(jndiName);
            throw new ResourceAdapterNotActiveException(errMsg);
         }

         ResourceAdapter resourceAdapterBean = raIM.getResourceAdapter();
         if (resourceAdapterBean == null) {
            Debug.throwAssertionError("RA bean bound with JNDI name '" + jndiName + "' is null");
         }

         try {
            raIM.getAdapterLayer().invokePostConstruct(activationSpec);
         } catch (InjectionException var22) {
            throw new ActivationSpecFindOrCreateException(var22.getMessage(), var22);
         }

         raIM = this.validateActivationSpec(ejbName, jndiName, messageListenerType, activationSpec, kernelId);

         String errMsg;
         try {
            raIM.getAdapterLayer().endpointActivation(resourceAdapterBean, endpointFactory, activationSpec, kernelId);
         } catch (Throwable var21) {
            errMsg = raIM.getAdapterLayer().toString(var21, kernelId);
            throw (EndpointActivationException)((EndpointActivationException)(new EndpointActivationException(errMsg, false)).initCause(var21));
         }

         synchronized(raIM) {
            if (!raIM.isActivated()) {
               errMsg = Debug.getExceptionRANotActive(jndiName);
               throw new ResourceAdapterNotActiveException(errMsg);
            }

            String exString;
            try {
               RAInboundManager raInboundMgr = raIM.getRAInboundManager();
               exString = mdbRuntime.getApplicationName() + "_" + mdbRuntime.getEJBName();
               raInboundMgr.setupForRecovery(activationSpec, exString);
               raInboundMgr.addEJB(messageListenerType, ejbName);
               raInboundMgr.addEndpointFactory(messageListenerType, endpointFactory, mdbRuntime);
            } catch (SystemException var20) {
               exString = raIM.getAdapterLayer().toString(var20, kernelId);
               throw (EndpointActivationException)((EndpointActivationException)(new EndpointActivationException(exString, false)).initCause(var20));
            }
         }
      } catch (Throwable var24) {
         if (var14 != null) {
            var14.th = var24;
            if (var14.monitorHolder[4] != null) {
               var14.monitorIndex = 4;
               InstrumentationSupport.process(var14);
            }

            if (var14.monitorHolder[2] != null) {
               var14.monitorIndex = 2;
               InstrumentationSupport.postProcess(var14);
            }
         }

         throw var24;
      }

      if (var14 != null) {
         if (var14.monitorHolder[4] != null) {
            var14.monitorIndex = 4;
            InstrumentationSupport.process(var14);
         }

         if (var14.monitorHolder[2] != null) {
            var14.monitorIndex = 2;
            InstrumentationSupport.postProcess(var14);
         }
      }

   }

   private static AuthenticatedSubject getKernelId() {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      return kernelId;
   }

   private RAInstanceManager validateActivationSpec(String ejbName, String jndiName, String messageListenerType, ActivationSpec activationSpec, AuthenticatedSubject kernelId) throws ResourceAdapterNotFoundException, MissingPropertiesException, ActivationSpecFindOrCreateException, EndpointActivationException {
      RAInstanceManager raIM = RACollectionManager.getRAInstanceManager(jndiName);
      if (raIM == null) {
         String exMsg = Debug.getExceptionRANotDeployed(jndiName);
         throw new ResourceAdapterNotFoundException(exMsg);
      } else {
         this.checkRequiredConfigProperties(activationSpec, jndiName, messageListenerType);

         try {
            raIM.getBeanValidator().validate(activationSpec, "ActivationSpec from EJB '" + ejbName + "'");
         } catch (RAException var10) {
            throw (EndpointActivationException)((EndpointActivationException)(new EndpointActivationException("Failed to validate ActivationSpec " + activationSpec, false)).initCause(var10));
         }

         try {
            raIM.getAdapterLayer().validate(activationSpec, kernelId);
         } catch (UnsupportedOperationException var8) {
            Debug.raLifecycle("Ignored UnsupportedOperationException from ActivationSpec.validate() method: " + var8);
         } catch (InvalidPropertyException var9) {
            throw (EndpointActivationException)((EndpointActivationException)(new EndpointActivationException("Exception when call validate() method on ActivationSpec " + activationSpec, false)).initCause(var9));
         }

         return raIM;
      }
   }

   public void deActivateEndpoint(String ejbName, String jndiName, String messageListenerType, ActivationSpec activationSpec, MessageEndpointFactory endpointFactory, MessageDrivenEJBRuntimeMBean mdbRuntime) throws EndpointActivationException {
      LocalHolder var14;
      if ((var14 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var14.argsCapture) {
            var14.args = InstrumentationSupport.toSensitive(7);
         }

         if (var14.monitorHolder[0] != null) {
            var14.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var14);
            InstrumentationSupport.process(var14);
         }

         if (var14.monitorHolder[1] != null) {
            var14.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var14);
            InstrumentationSupport.preProcess(var14);
         }

         var14.resetPostBegin();
      }

      try {
         ResourceAdapter resourceAdapterBean = null;
         if (Debug.isRALifecycleEnabled()) {
            Debug.raLifecycle("weblogic.connector.external.impl.EndpointActivationUtils.deactivateEndpoint called, ejbName = " + ejbName + ", jndiName = " + jndiName + ", messageListenerType = " + messageListenerType + ", .. )");
         }

         checkStringArg("deActivateEndpoint()", "ejbName", ejbName);
         checkStringArg("deActivateEndpoint()", "jndiName", jndiName);
         checkObjectArg("deActivateEndpoint()", "activationSpec", activationSpec);
         checkObjectArg("deActivateEndpoint()", "endpointFactory", endpointFactory);
         RAInstanceManager raIM = RACollectionManager.getRAInstanceManager(jndiName);
         if (raIM != null) {
            synchronized(raIM) {
               if (!raIM.isActivated() && !raIM.isSuspended()) {
                  String errMsg = Debug.getExceptionRANotActive(jndiName);
                  throw new ResourceAdapterNotActiveException(errMsg);
               }

               resourceAdapterBean = raIM.getResourceAdapter();
               if (resourceAdapterBean == null) {
                  Debug.throwAssertionError("RA bean bound with JNDI name '" + jndiName + "' is null");
               }
            }
         } else {
            Debug.throwAssertionError("Attempt to deactivate RA with JNDI name '" + jndiName + "' and RA cannot be found");
         }

         AuthenticatedSubject kernelId = getKernelId();
         raIM.getAdapterLayer().endpointDeactivation(resourceAdapterBean, endpointFactory, activationSpec, kernelId);

         try {
            String errMsg;
            try {
               synchronized(raIM) {
                  if (!raIM.isActivated() && !raIM.isSuspended()) {
                     errMsg = Debug.getExceptionRANotActive(jndiName);
                     throw new ResourceAdapterNotActiveException(errMsg);
                  }

                  RAInboundManager raInboundMgr = raIM.getRAInboundManager();
                  if (raInboundMgr == null) {
                     Debug.throwAssertionError("Attempt to deactivate endpoint of RA with JNDI name = '" + jndiName + "' but no InboundManger can be found for that RA");
                  }

                  raInboundMgr.removeEJB(messageListenerType, ejbName);
                  raInboundMgr.removeEndpointFactory(messageListenerType, endpointFactory, mdbRuntime);
                  raInboundMgr.cleanupForRecovery(activationSpec);
               }
            } catch (Throwable var27) {
               errMsg = raIM.getAdapterLayer().toString(var27, kernelId);
               throw (EndpointActivationException)((EndpointActivationException)(new EndpointActivationException(errMsg, false)).initCause(var27));
            }
         } finally {
            raIM.getAdapterLayer().invokePreDestroy(activationSpec, "activation spec");
         }
      } catch (Throwable var29) {
         if (var14 != null) {
            var14.th = var29;
            if (var14.monitorHolder[2] != null) {
               var14.monitorIndex = 2;
               InstrumentationSupport.process(var14);
            }

            if (var14.monitorHolder[1] != null) {
               var14.monitorIndex = 1;
               InstrumentationSupport.postProcess(var14);
            }
         }

         throw var29;
      }

      if (var14 != null) {
         if (var14.monitorHolder[2] != null) {
            var14.monitorIndex = 2;
            InstrumentationSupport.process(var14);
         }

         if (var14.monitorHolder[1] != null) {
            var14.monitorIndex = 1;
            InstrumentationSupport.postProcess(var14);
         }
      }

   }

   public void suspendInbound(String jndiName, MessageEndpointFactory endpointFactory, Properties props) throws EndpointActivationException {
      ResourceAdapter resourceAdapterBean = null;
      if (Debug.isRALifecycleEnabled()) {
         Debug.raLifecycle("weblogic.connector.external.impl.EndpointActivationUtils.suspendInbound() called, jndiName = " + jndiName + ", endpointFactory = " + endpointFactory);
      }

      checkStringArg("deActivateEndpoint()", "jndiName", jndiName);
      checkObjectArg("deActivateEndpoint()", "endpointFactory", endpointFactory);
      RAInstanceManager raIM = RACollectionManager.getRAInstanceManager(jndiName);
      if (raIM != null) {
         resourceAdapterBean = raIM.getResourceAdapter();
         if (resourceAdapterBean == null) {
            Debug.throwAssertionError("RA bean bound with JNDI name '" + jndiName + "' is null");
         }
      } else {
         Debug.throwAssertionError("Attempt to suspend inbound of RA with JNDI name '" + jndiName + "' and RA cannot be found");
      }

      AuthenticatedSubject kernelId = getKernelId();

      try {
         if (resourceAdapterBean instanceof Suspendable && raIM.getAdapterLayer().supportsSuspend((Suspendable)resourceAdapterBean, 1, kernelId)) {
            raIM.getAdapterLayer().suspendInbound((Suspendable)resourceAdapterBean, endpointFactory, props, kernelId);
         }

         RAInboundManager raInboundMgr = raIM.getRAInboundManager();
         if (raInboundMgr == null) {
            Debug.throwAssertionError("Attempt to suspend inbound for endpoint of RA with JNDI name = '" + jndiName + "' but no InboundManger can be found for that RA");
         }

         raInboundMgr.setEndpointFactorySuspendedState(endpointFactory, true);
      } catch (Throwable var9) {
         String exString = raIM.getAdapterLayer().toString(var9, kernelId);
         throw (EndpointActivationException)((EndpointActivationException)(new EndpointActivationException(exString, false)).initCause(var9));
      }
   }

   public void resumeInbound(String jndiName, MessageEndpointFactory endpointFactory, Properties props) throws EndpointActivationException {
      ResourceAdapter resourceAdapterBean = null;
      if (Debug.isRALifecycleEnabled()) {
         Debug.raLifecycle("weblogic.connector.external.impl.EndpointActivationUtils.resumeInbound() called, jndiName = " + jndiName + ", endpointFactory = " + endpointFactory);
      }

      checkStringArg("deActivateEndpoint()", "jndiName", jndiName);
      checkObjectArg("deActivateEndpoint()", "endpointFactory", endpointFactory);
      RAInstanceManager raIM = RACollectionManager.getRAInstanceManager(jndiName);
      if (raIM != null) {
         resourceAdapterBean = raIM.getResourceAdapter();
         if (resourceAdapterBean == null) {
            Debug.throwAssertionError("RA bean bound with JNDI name '" + jndiName + "' is null");
         }
      } else {
         Debug.throwAssertionError("Attempt to resume inbound of RA with JNDI name '" + jndiName + "' and RA cannot be found");
      }

      AuthenticatedSubject kernelId = getKernelId();

      try {
         if (resourceAdapterBean instanceof Suspendable) {
            raIM.getAdapterLayer().resumeInbound((Suspendable)resourceAdapterBean, endpointFactory, props, kernelId);
         }

         RAInboundManager raInboundMgr = raIM.getRAInboundManager();
         if (raInboundMgr == null) {
            Debug.throwAssertionError("Attempt to resume inbound for endpoint of RA with JNDI name = '" + jndiName + "' but no InboundManger can be found for that RA");
         }

         raInboundMgr.setEndpointFactorySuspendedState(endpointFactory, false);
      } catch (Throwable var9) {
         String exString = raIM.getAdapterLayer().toString(var9, kernelId);
         throw (EndpointActivationException)((EndpointActivationException)(new EndpointActivationException(exString, false)).initCause(var9));
      }
   }

   private static Object initializeActivationSpec(RAInstanceManager raIM, String activationSpecClass) throws ActivationSpecFindOrCreateException {
      Debug.println((Object)"weblogic.connector.external.impl.EndpointActivationUtils", (String)(".initializeActivationSpec( raIM = " + raIM + ", activationSpecClass = " + activationSpecClass));
      Object activationSpec = null;
      AuthenticatedSubject kernelId = getKernelId();

      try {
         activationSpec = instantiateActivationSpec(raIM, activationSpecClass, raIM.getClassloader());
         raIM.getAdapterLayer().setResourceAdapter((ResourceAdapterAssociation)activationSpec, raIM.getResourceAdapter(), kernelId);
         return activationSpec;
      } catch (ActivationSpecFindOrCreateException var6) {
         throw var6;
      } catch (Throwable var7) {
         String exString = raIM.getAdapterLayer().toString(var7, kernelId);
         throw (ActivationSpecFindOrCreateException)((ActivationSpecFindOrCreateException)(new ActivationSpecFindOrCreateException(exString)).initCause(var7));
      }
   }

   private static Object instantiateActivationSpec(RAInstanceManager raIM, String className, ClassLoader classLoader) throws ActivationSpecFindOrCreateException {
      AuthenticatedSubject kernelId = getKernelId();

      try {
         return raIM.getAdapterLayer().createInstance(className, true, classLoader, kernelId);
      } catch (Throwable var6) {
         String exMsg = Debug.getExceptionInstantiateClassFailed(className, var6.toString());
         throw new ActivationSpecFindOrCreateException(exMsg, var6);
      }
   }

   private static void checkStringArg(String methodName, String argName, String argValue) {
      if (argValue == null || argValue.trim().equals("")) {
         String exMsg = methodName + " passed " + argName + " of '" + argValue + "'";
         Debug.throwAssertionError(exMsg);
      }

   }

   private static void checkObjectArg(String methodName, String argName, Object argValue) {
      if (argValue == null) {
         String exMsg = methodName + " passed " + argName + " of 'null'";
         Debug.throwAssertionError(exMsg);
      }

   }

   public Object getActivationSpec(String jndiName, String messageListenerType) throws ActivationSpecFindOrCreateException, ResourceAdapterNotActiveException, ResourceAdapterNotFoundException {
      ActivationSpecInfo activationSpecInfo = null;
      Object initializedActivationSpec = null;
      Debug.println((Object)"weblogic.connector.external.impl.EndpointActivationUtils", (String)(".getActivationSpec( " + jndiName + ", " + messageListenerType + " )"));
      checkStringArg("getActivationSpec()", "jndiName", jndiName);
      checkStringArg("getAcitvationSpec()", "messageListenerType", messageListenerType);
      String errMsg = null;
      RAInstanceManager raIM = RACollectionManager.getRAInstanceManager(jndiName);
      if (raIM == null) {
         errMsg = Debug.getExceptionRANotDeployed(jndiName);
         throw new ResourceAdapterNotFoundException(errMsg);
      } else if (!raIM.isActivated()) {
         errMsg = Debug.getExceptionRANotActive(jndiName);
         throw new ResourceAdapterNotActiveException(errMsg);
      } else {
         RAInfo raInfo = raIM.getRAInfo();
         activationSpecInfo = getActivationSpecInfo(raInfo, messageListenerType);
         initializedActivationSpec = initializeActivationSpec(raIM, activationSpecInfo.getActivationSpecClass());

         try {
            this.setDefaultProperties(initializedActivationSpec, raIM, activationSpecInfo);
            return initializedActivationSpec;
         } catch (RAException var9) {
            throw new ActivationSpecFindOrCreateException(var9.getMessage(), var9);
         }
      }
   }

   private void setDefaultProperties(Object initializedActivationSpec, RAInstanceManager raIM, ActivationSpecInfo activationSpecInfo) throws RAException {
      Map configProps = activationSpecInfo.getConfigProps();
      if (!configProps.isEmpty()) {
         PropSetterTable activationSpecPropSetterTable = raIM.getRAValidationInfo().getActivationSpecPropSetterTable(initializedActivationSpec.getClass().getName());
         Utils.setProperties(raIM, initializedActivationSpec, configProps.values(), activationSpecPropSetterTable);
      }

   }

   public List getRequiredConfigProperties(String jndiName, String messageListenerType) throws ResourceAdapterNotFoundException, ActivationSpecFindOrCreateException {
      checkStringArg("getRequiredConfigProperties()", "jndiName", jndiName);
      checkStringArg("getRequiredConfigProperties()", "messageListenerType", messageListenerType);
      RAInstanceManager raIM = RACollectionManager.getRAInstanceManager(jndiName);
      if (raIM == null) {
         String exMsg = Debug.getExceptionRANotFound(jndiName);
         throw new ResourceAdapterNotFoundException(exMsg);
      } else {
         RAInfo raInfo = raIM.getRAInfo();
         ActivationSpecInfo activationSpecInfo = getActivationSpecInfo(raInfo, messageListenerType);
         return activationSpecInfo.getRequiredProps();
      }
   }

   private static ActivationSpecInfo getActivationSpecInfo(RAInfo raInfo, String messageListenerType) throws ActivationSpecFindOrCreateException {
      Iterator inboundIterator = null;
      ActivationSpecInfo activationSpecInfo = null;
      boolean foundIt = false;

      try {
         inboundIterator = raInfo.getInboundInfos().iterator();
      } catch (ElementNotFoundException var7) {
         throw new ActivationSpecFindOrCreateException(var7.toString());
      }

      while(inboundIterator.hasNext() && !foundIt) {
         InboundInfo inboundInfo = (InboundInfo)inboundIterator.next();
         if (Debug.getVerbose()) {
            Debug.println((Object)"weblogic.connector.external.impl.EndpointActivationUtils", (String)(".getActivationSpecInfo( " + messageListenerType + " ) found " + inboundInfo.getMsgType()));
         }

         if (inboundInfo.getMsgType().equals(messageListenerType)) {
            activationSpecInfo = inboundInfo.getActivationSpec();
            foundIt = true;
         }
      }

      if (activationSpecInfo == null) {
         String exMsg = Debug.getExceptionNoMessageListener(raInfo.getJndiName(), messageListenerType);
         throw new ActivationSpecFindOrCreateException(exMsg);
      } else {
         return activationSpecInfo;
      }
   }

   private void checkRequiredConfigProperties(ActivationSpec activationSpec, String jndiName, String messageListenerType) throws MissingPropertiesException, ResourceAdapterNotFoundException, ActivationSpecFindOrCreateException {
      List missingPropsVector = new ArrayList();
      List requiredConfigPropsList = this.getRequiredConfigProperties(jndiName, messageListenerType);
      Iterator iter = requiredConfigPropsList.iterator();

      String propertyName;
      while(iter.hasNext()) {
         boolean foundProp = false;
         RequiredConfigPropInfo prop = (RequiredConfigPropInfo)iter.next();
         propertyName = prop.getName();
         Method[] methods = activationSpec.getClass().getMethods();

         for(int idx = 0; idx < methods.length; ++idx) {
            if (methods[idx].getName().equalsIgnoreCase("get" + propertyName)) {
               foundProp = true;
               break;
            }
         }

         if (!foundProp) {
            if (Debug.getVerbose()) {
               Debug.println((Object)"weblogic.connector.external.impl.EndpointActivationUtils", (String)(".checkRequiredConfigProperties():  missing property found, '" + propertyName + "'"));
            }

            missingPropsVector.add(propertyName);
         }
      }

      Debug.println((Object)"weblogic.connector.external.impl.EndpointActivationUtils", (String)(".checkRequiredConfigProperties found " + missingPropsVector.size() + " missing properties in the activation spec"));
      if (missingPropsVector.size() > 0) {
         Object[] missingPropsArray = missingPropsVector.toArray();
         String missing = "";

         for(int idx = 0; idx < missingPropsArray.length; ++idx) {
            if (idx > 0) {
               missing = missing + PlatformConstants.EOL + "  ";
            }

            missing = missing + (String)missingPropsArray[idx];
         }

         Debug.println((Object)"weblogic.connector.external.impl.EndpointActivationUtils", (String)(".activateEndpoint call missing required properties in the passed activation spec, " + missing));
         propertyName = Debug.getExceptionMissingRequiredProperty(missing);
         throw new MissingPropertiesException(propertyName);
      }
   }

   public String getAdapterSpecVersion(String jndiName) throws ResourceAdapterNotFoundException {
      if (Debug.isRALifecycleEnabled()) {
         Debug.raLifecycle("weblogic.connector.external.impl.EndpointActivationUtils.getAdapterSpecVersion( " + jndiName + " )");
      }

      RAInfo raInfo = getRAInfo(jndiName, "getAdapterSpecVersion");
      String version = raInfo.getSpecVersion();
      if (Debug.isRALifecycleEnabled()) {
         Debug.raLifecycle("weblogic.connector.external.impl.EndpointActivationUtils.getAdapterSpecVersion( " + jndiName + " ) returns: " + version);
      }

      return version;
   }

   private static RAInfo getRAInfo(String jndiName, String callerInfo) throws ResourceAdapterNotFoundException {
      checkStringArg(callerInfo, "jndiName", jndiName);
      RAInstanceManager raIM = RACollectionManager.getRAInstanceManager(jndiName);
      if (raIM == null) {
         String errMsg = Debug.getExceptionRANotDeployed(jndiName);
         throw new ResourceAdapterNotFoundException(errMsg);
      } else {
         RAInfo raInfo = raIM.getRAInfo();
         return raInfo;
      }
   }

   public String[] getActivationSpecDynamicReconfigProperties(String jndiName, String messageListenerType) throws ResourceAdapterNotFoundException, ActivationSpecFindOrCreateException {
      if (Debug.isRALifecycleEnabled()) {
         Debug.raLifecycle("weblogic.connector.external.impl.EndpointActivationUtils.getActivationSpecDynamicReconfigProperties( " + jndiName + ", " + messageListenerType + " )");
      }

      RAInfo raInfo = getRAInfo(jndiName, "getActivationSpecDynamicReconfigProperties");
      String[] names = getActivationSpecDynamicReconfigProperties(raInfo, messageListenerType);
      if (Debug.isRALifecycleEnabled()) {
         Debug.raLifecycle("weblogic.connector.external.impl.EndpointActivationUtils.getActivationSpecDynamicReconfigProperties( " + jndiName + ", " + messageListenerType + " ) returns: " + Arrays.toString(names));
      }

      return names;
   }

   static String[] getActivationSpecDynamicReconfigProperties(RAInfo raInfo, String messageListenerType) throws ResourceAdapterNotFoundException, ActivationSpecFindOrCreateException {
      ActivationSpecInfo activationSpecInfo = getActivationSpecInfo(raInfo, messageListenerType, "getActivationSpecDynamicReconfigProperties");
      Map configProps = activationSpecInfo.getConfigProps();
      ArrayList propertyNames = new ArrayList();
      Iterator var5 = configProps.values().iterator();

      while(var5.hasNext()) {
         ConfigPropInfo info = (ConfigPropInfo)var5.next();
         if (info.isDynamicUpdatable()) {
            propertyNames.add(info.getName());
         }
      }

      String[] names = new String[propertyNames.size()];
      names = (String[])propertyNames.toArray(names);
      return names;
   }

   private static ActivationSpecInfo getActivationSpecInfo(RAInfo raInfo, String messageListenerType, String callerInfo) throws ResourceAdapterNotFoundException, ActivationSpecFindOrCreateException {
      checkStringArg(callerInfo, "messageListenerType", messageListenerType);
      ActivationSpecInfo activationSpecInfo = null;
      activationSpecInfo = getActivationSpecInfo(raInfo, messageListenerType);
      return activationSpecInfo;
   }

   public void validateActivationSpec(String ejbName, String jndiName, String messageListenerType, ActivationSpec activationSpec) throws ResourceAdapterNotFoundException, MissingPropertiesException, ActivationSpecFindOrCreateException, EndpointActivationException {
      AuthenticatedSubject kernelId = getKernelId();
      if (Debug.isRALifecycleEnabled()) {
         Debug.raLifecycle("weblogic.connector.external.impl.EndpointActivationUtils.validateActivationSpec called, ejbName = " + ejbName + ", jndiName = " + jndiName + ", messageListenerType = " + messageListenerType + ", activationSpec = " + activationSpec + " )");
      }

      checkStringArg("validateActivationSpec()", "ejbName", ejbName);
      checkStringArg("validateActivationSpec()", "jndiName", jndiName);
      checkStringArg("validateActivationSpec()", "messageListenerType", messageListenerType);
      checkObjectArg("validateActivationSpec()", "activationSpec", activationSpec);
      this.validateActivationSpec(ejbName, jndiName, messageListenerType, activationSpec, kernelId);
   }

   public String[] getActivationSpecConfidentialProperties(String jndiName, String messageListenerType) throws ResourceAdapterNotFoundException, ActivationSpecFindOrCreateException {
      if (Debug.isRALifecycleEnabled()) {
         Debug.raLifecycle("weblogic.connector.external.impl.EndpointActivationUtils.getActivationSpecConfidentialProperties( " + jndiName + ", " + messageListenerType + " )");
      }

      RAInfo raInfo = getRAInfo(jndiName, "getActivationSpecConfidentialProperties");
      String[] names = getActivationSpecConfidentialProperties(raInfo, messageListenerType);
      if (Debug.isRALifecycleEnabled()) {
         Debug.raLifecycle("weblogic.connector.external.impl.EndpointActivationUtils.getActivationSpecConfidentialProperties( " + jndiName + ", " + messageListenerType + " ) returns: " + Arrays.toString(names));
      }

      return names;
   }

   static String[] getActivationSpecConfidentialProperties(RAInfo raInfo, String messageListenerType) throws ResourceAdapterNotFoundException, ActivationSpecFindOrCreateException {
      ActivationSpecInfo activationSpecInfo = getActivationSpecInfo(raInfo, messageListenerType, "getActivationSpecConfidentialProperties");
      Map configProps = activationSpecInfo.getConfigProps();
      ArrayList propertyNames = new ArrayList();
      Iterator var5 = configProps.values().iterator();

      while(var5.hasNext()) {
         ConfigPropInfo info = (ConfigPropInfo)var5.next();
         if (info.isConfidential()) {
            propertyNames.add(info.getName());
         }
      }

      String[] names = new String[propertyNames.size()];
      names = (String[])propertyNames.toArray(names);
      return names;
   }

   public List getRAJndiName(String messageListenerType) {
      return RACollectionManager.getRAJndiNamesByMessageListenerType(messageListenerType);
   }

   public List getRAJndiName(String messageListenerType, String appId) {
      String appName = ApplicationVersionUtils.getApplicationName(appId);
      String version = ApplicationVersionUtils.getVersionId(appId);
      List jndis = RACollectionManager.getRAJndiNamesByMessageListenerType(messageListenerType, appName, version);
      return jndis.size() == 0 ? RACollectionManager.getRAJndiNamesByMessageListenerType(messageListenerType) : jndis;
   }

   static {
      _WLDF$INST_FLD_Connector_Before_Inbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Before_Inbound");
      _WLDF$INST_FLD_Connector_Around_Inbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Around_Inbound");
      _WLDF$INST_FLD_Connector_After_Inbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_After_Inbound");
      _WLDF$INST_FLD_Connector_Endpoint_Activate_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Endpoint_Activate_Low");
      _WLDF$INST_FLD_Connector_Endpoint_Deactivate_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Endpoint_Deactivate_Low");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "EndpointActivationUtils.java", "weblogic.connector.external.impl.EndpointActivationUtils", "activateEndpoint", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljavax/resource/spi/ActivationSpec;Ljavax/resource/spi/endpoint/MessageEndpointFactory;Lweblogic/management/runtime/MessageDrivenEJBRuntimeMBean;)V", 101, "", "", "", InstrumentationSupport.makeMap(new String[]{"Connector_Endpoint_Deactivate_Low", "Connector_Around_Inbound", "Connector_Before_Inbound", "Connector_After_Inbound", "Connector_Endpoint_Activate_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("ejb", (String)null, false, true), InstrumentationSupport.createValueHandlingInfo("jndi", (String)null, false, true), InstrumentationSupport.createValueHandlingInfo("listener", (String)null, false, true), null, null, null}), null, null, null, InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("ejb", (String)null, false, true), InstrumentationSupport.createValueHandlingInfo("jndi", (String)null, false, true), InstrumentationSupport.createValueHandlingInfo("listener", (String)null, false, true), null, null, null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_Before_Inbound, _WLDF$INST_FLD_Connector_Endpoint_Activate_Low, _WLDF$INST_FLD_Connector_Around_Inbound, _WLDF$INST_FLD_Connector_Endpoint_Deactivate_Low, _WLDF$INST_FLD_Connector_After_Inbound};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "EndpointActivationUtils.java", "weblogic.connector.external.impl.EndpointActivationUtils", "deActivateEndpoint", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljavax/resource/spi/ActivationSpec;Ljavax/resource/spi/endpoint/MessageEndpointFactory;Lweblogic/management/runtime/MessageDrivenEJBRuntimeMBean;)V", 247, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_Before_Inbound, _WLDF$INST_FLD_Connector_Around_Inbound, _WLDF$INST_FLD_Connector_After_Inbound};
      SINGLETON = new EndpointActivationUtils();
   }
}
