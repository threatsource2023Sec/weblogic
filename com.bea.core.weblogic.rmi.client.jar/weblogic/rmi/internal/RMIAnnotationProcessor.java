package weblogic.rmi.internal;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import weblogic.rmi.annotation.LoadAlgorithmType;
import weblogic.rmi.annotation.Rmi;
import weblogic.rmi.annotation.RmiMethod;
import weblogic.rmi.annotation.internal.DgcPolicy;
import weblogic.rmi.annotation.internal.DispatchContext;
import weblogic.rmi.annotation.internal.RmiInternal;
import weblogic.rmi.annotation.internal.RmiMethodInternal;
import weblogic.rmi.annotation.internal.Security;
import weblogic.utils.collections.ArrayMap;
import weblogic.utils.reflect.MethodSignatureBuilder;

public class RMIAnnotationProcessor implements DescriptorConstants {
   private static final Map loadAlgorithms = new HashMap();
   private static final Map securityOptions = new HashMap();
   private static final Map dgcPolicies = new HashMap();
   private static final Map dispatchContexts = new HashMap();

   static ArrayMap getDescriptorAsMap(Class c) throws IOException {
      RmiInternal rmiInternal = (RmiInternal)c.getAnnotation(RmiInternal.class);
      if (rmiInternal != null) {
         if (!checkValidRemoteInterfaces(c, rmiInternal.remoteInterfaces())) {
            throw new IOException(c + " doesnt implement all interfaces as specified in the remoteInterfaces annotation");
         } else {
            return processRMIInternalAnnotation(rmiInternal, c);
         }
      } else {
         Rmi rmi = (Rmi)c.getAnnotation(Rmi.class);
         if (rmi != null) {
            if (!checkValidRemoteInterfaces(c, rmi.remoteInterfaces())) {
               throw new IOException(c + " does not implement all interfaces as specified in the remoteInterfaces annotation");
            } else {
               return processRMIAnnotation(rmi, c);
            }
         } else {
            return null;
         }
      }
   }

   private static ArrayMap processRMIInternalAnnotation(RmiInternal rmiInternal, Class c) throws IOException {
      ArrayMap rmiDescriptorMap = new ArrayMap();
      rmiDescriptorMap.put("name", c.getName());
      if (rmiInternal.useServerSideStubs()) {
         rmiDescriptorMap.put("use-server-side-stubs", Boolean.toString(rmiInternal.useServerSideStubs()));
      }

      if (!rmiInternal.enableCallByReference()) {
         rmiDescriptorMap.put("enable-call-by-reference", Boolean.toString(rmiInternal.enableCallByReference()));
      }

      if (!rmiInternal.remoteRefClassname().equals("")) {
         rmiDescriptorMap.put("remote-ref-classname", rmiInternal.remoteRefClassname());
      }

      if (!rmiInternal.serverRefClassname().equals("")) {
         rmiDescriptorMap.put("server-ref-classname", rmiInternal.serverRefClassname());
      }

      if (rmiInternal.initialReference() > -1) {
         rmiDescriptorMap.put("initial-reference", Integer.toString(rmiInternal.initialReference()));
      }

      if (!rmiInternal.networkAccessPoint().equals("")) {
         rmiDescriptorMap.put("network-access-point", rmiInternal.networkAccessPoint());
      }

      ArrayMap clusterDescriptorMap = new ArrayMap();
      if (rmiInternal.clusterable()) {
         clusterDescriptorMap.put("clusterable", Boolean.toString(rmiInternal.clusterable()));
      }

      if (rmiInternal.loadAlgorithm() != LoadAlgorithmType.DEFAULT) {
         clusterDescriptorMap.put("load-algorithm", loadAlgorithms.get(rmiInternal.loadAlgorithm()));
      }

      if (!rmiInternal.replicaHandlerClassname().equals("")) {
         clusterDescriptorMap.put("replica-handler-classname", rmiInternal.replicaHandlerClassname());
      }

      if (!rmiInternal.callRouterClassname().equals("")) {
         clusterDescriptorMap.put("call-router-classname", rmiInternal.callRouterClassname());
      }

      if (rmiInternal.stickToFirstServer()) {
         clusterDescriptorMap.put("stick-to-first-server", Boolean.toString(rmiInternal.stickToFirstServer()));
      }

      if (rmiInternal.propagateEnvironment()) {
         clusterDescriptorMap.put("propagate-environment", Boolean.toString(rmiInternal.propagateEnvironment()));
      }

      ArrayMap lifecycleDescriptorMap = new ArrayMap();
      if (rmiInternal.dgcPolicy() != DgcPolicy.DEFAULT) {
         lifecycleDescriptorMap.put("dgc-policy", dgcPolicies.get(rmiInternal.dgcPolicy()));
      }

      ArrayMap securityDescriptorMap = new ArrayMap();
      if (rmiInternal.confidentiality() != Security.DEFAULT) {
         securityDescriptorMap.put("confidentiality", securityOptions.get(rmiInternal.confidentiality()));
      }

      if (rmiInternal.integrity() != Security.DEFAULT) {
         securityDescriptorMap.put("integrity", securityOptions.get(rmiInternal.integrity()));
      }

      if (rmiInternal.clientAuthentication() != Security.DEFAULT) {
         securityDescriptorMap.put("client-authentication", securityOptions.get(rmiInternal.clientAuthentication()));
      }

      if (rmiInternal.clientCertAuthentication() != Security.DEFAULT) {
         securityDescriptorMap.put("client-cert-authentication", securityOptions.get(rmiInternal.clientCertAuthentication()));
      }

      if (rmiInternal.identityAssertion() != Security.DEFAULT) {
         securityDescriptorMap.put("identity-assertion", securityOptions.get(rmiInternal.identityAssertion()));
      }

      if (rmiInternal.statefulAuthentication()) {
         securityDescriptorMap.put("stateful-authentication", Boolean.toString(rmiInternal.statefulAuthentication()));
      }

      if (!rmiInternal.activationIdentifierClassname().equals("")) {
         lifecycleDescriptorMap.put("activation-identifier-classname", rmiInternal.activationIdentifierClassname());
      }

      if (rmiInternal.isActivatable()) {
         rmiDescriptorMap.put("remote-ref-classname", "weblogic.rmi.internal.activation.ActivatableRemoteRef");
         rmiDescriptorMap.put("server-ref-classname", "weblogic.rmi.internal.activation.ActivatableServerRef");
         lifecycleDescriptorMap.put("dgc-policy", dgcPolicies.get(DgcPolicy.MANAGED));
      }

      ArrayMap rmiMethodDescriptorMap = getRMIInternalMethodDescriptors(rmiInternal, c);
      ArrayMap result = new ArrayMap();
      if (!rmiDescriptorMap.isEmpty()) {
         result.put("rmidescriptor", rmiDescriptorMap);
      }

      if (!clusterDescriptorMap.isEmpty()) {
         result.put("clusterdescriptor", clusterDescriptorMap);
      }

      if (!lifecycleDescriptorMap.isEmpty()) {
         result.put("lifecycledescriptor", lifecycleDescriptorMap);
      }

      if (!securityDescriptorMap.isEmpty()) {
         result.put("securitydescriptor", securityDescriptorMap);
      }

      if (rmiMethodDescriptorMap != null && !rmiMethodDescriptorMap.isEmpty()) {
         result.put("methoddescriptor", rmiMethodDescriptorMap);
      }

      return result;
   }

   private static ArrayMap getRMIInternalMethodDescriptors(RmiInternal rmiInternal, Class c) throws IOException {
      ArrayMap methodDescriptorMap = new ArrayMap();
      RmiMethodInternal classLevelAnnotation = rmiInternal.defaultRMIMethodParams();
      if (classLevelAnnotation != null) {
         ArrayMap globalMethodDescriptor = processRMIMethodInternalAnnotation(classLevelAnnotation, c);
         if (globalMethodDescriptor != null) {
            String key = MethodSignatureBuilder.compute("*");
            methodDescriptorMap.put(key, globalMethodDescriptor);
         }
      }

      Method[] var11 = c.getMethods();
      int var12 = var11.length;

      for(int var6 = 0; var6 < var12; ++var6) {
         Method m = var11[var6];
         RmiMethodInternal methodAnnotation = (RmiMethodInternal)m.getAnnotation(RmiMethodInternal.class);
         if (methodAnnotation != null) {
            ArrayMap methodDescriptor = processRMIMethodInternalAnnotation(methodAnnotation, c);
            if (methodDescriptor != null) {
               String key = MethodSignatureBuilder.compute(m);
               methodDescriptorMap.put(key, methodDescriptor);
            }
         }
      }

      return methodDescriptorMap;
   }

   private static ArrayMap processRMIMethodInternalAnnotation(RmiMethodInternal rmiMethodInternal, Class c) throws IOException {
      ArrayMap methodDescriptor = new ArrayMap();
      if (rmiMethodInternal.future()) {
         methodDescriptor.put("future", Boolean.toString(rmiMethodInternal.future()));
      }

      String dispatchPolicy = rmiMethodInternal.dispatchPolicy();
      if (dispatchPolicy != null && dispatchPolicy.length() > 0) {
         methodDescriptor.put("dispatch-policy", rmiMethodInternal.dispatchPolicy());
      }

      if (rmiMethodInternal.dispatchContext() != DispatchContext.NONE) {
         methodDescriptor.put("dispatch-context", dispatchContexts.get(rmiMethodInternal.dispatchContext()));
      }

      if (!rmiMethodInternal.transactional()) {
         methodDescriptor.put("transactional", Boolean.toString(rmiMethodInternal.transactional()));
      }

      if (rmiMethodInternal.requiresTransaction()) {
         methodDescriptor.put("requires-transaction", Boolean.toString(rmiMethodInternal.requiresTransaction()));
      }

      if (rmiMethodInternal.oneway()) {
         methodDescriptor.put("oneway", Boolean.toString(rmiMethodInternal.oneway()));
      }

      boolean async = rmiMethodInternal.asynchronousResult();
      if (async) {
         methodDescriptor.put("asynchronous", Boolean.toString(async));
      }

      if (rmiMethodInternal.onewayTransactionalRequest()) {
         methodDescriptor.put("oneway-transactional-request", Boolean.toString(rmiMethodInternal.onewayTransactionalRequest()));
      }

      if (rmiMethodInternal.onewayTransactionalResponse()) {
         methodDescriptor.put("oneway-transactional-response", Boolean.toString(rmiMethodInternal.onewayTransactionalResponse()));
      }

      if (rmiMethodInternal.timeout() > 0) {
         methodDescriptor.put("timeout", Integer.toString(rmiMethodInternal.timeout()));
      }

      if (rmiMethodInternal.idempotent()) {
         methodDescriptor.put("idempotent", Boolean.toString(rmiMethodInternal.idempotent()));
      }

      Class remoteExceptionWrapper = rmiMethodInternal.remoteExceptionWrapper();
      if (remoteExceptionWrapper != null && remoteExceptionWrapper != Exception.class) {
         if (!checkValidRemoteExceptionWrapperClass(remoteExceptionWrapper)) {
            throw new IOException("remoteExceptionWrapper " + remoteExceptionWrapper + " must be a RuntimeException in " + c);
         }

         methodDescriptor.put("remote-exception-wrapper-classname", rmiMethodInternal.remoteExceptionWrapper().getName());
      }

      return methodDescriptor.keySet().size() > 0 ? methodDescriptor : null;
   }

   private static ArrayMap processRMIAnnotation(Rmi rmi, Class c) throws IOException {
      ArrayMap rmiDescriptorMap = new ArrayMap();
      ArrayMap clusterDescriptorMap = new ArrayMap();
      if (rmi.clusterable()) {
         clusterDescriptorMap.put("clusterable", Boolean.toString(rmi.clusterable()));
      }

      if (rmi.loadAlgorithm() != LoadAlgorithmType.DEFAULT) {
         clusterDescriptorMap.put("load-algorithm", loadAlgorithms.get(rmi.loadAlgorithm()));
      }

      if (!rmi.callRouterClassname().equals("")) {
         clusterDescriptorMap.put("call-router-classname", rmi.callRouterClassname());
      }

      if (rmi.stickToFirstServer()) {
         clusterDescriptorMap.put("stick-to-first-server", Boolean.toString(rmi.stickToFirstServer()));
      }

      ArrayMap lifecycleDescriptorMap = new ArrayMap();
      ArrayMap securityDescriptorMap = new ArrayMap();
      ArrayMap rmiMethodDescriptorMap = getRMIMethodDescriptors(rmi, c);
      ArrayMap result = new ArrayMap();
      result.put("rmidescriptor", rmiDescriptorMap);
      result.put("clusterdescriptor", clusterDescriptorMap);
      result.put("lifecycledescriptor", lifecycleDescriptorMap);
      result.put("securitydescriptor", securityDescriptorMap);
      if (rmiMethodDescriptorMap != null && !rmiMethodDescriptorMap.isEmpty()) {
         result.put("methoddescriptor", rmiMethodDescriptorMap);
      }

      return result;
   }

   private static ArrayMap getRMIMethodDescriptors(Rmi rmi, Class c) throws IOException {
      ArrayMap methodDescriptorMap = new ArrayMap();
      RmiMethod classLevelAnnotation = rmi.defaultRMIMethodParams();
      if (classLevelAnnotation != null) {
         ArrayMap globalMethodDescriptor = processRMIMethodAnnotation(classLevelAnnotation, c);
         if (globalMethodDescriptor != null) {
            String key = MethodSignatureBuilder.compute("*");
            methodDescriptorMap.put(key, globalMethodDescriptor);
         }
      }

      Method[] var11 = c.getMethods();
      int var12 = var11.length;

      for(int var6 = 0; var6 < var12; ++var6) {
         Method m = var11[var6];
         RmiMethod methodAnnotation = (RmiMethod)m.getAnnotation(RmiMethod.class);
         if (methodAnnotation != null) {
            ArrayMap methodDescriptor = processRMIMethodAnnotation(methodAnnotation, c);
            if (methodDescriptor != null) {
               String key = MethodSignatureBuilder.compute(m);
               methodDescriptorMap.put(key, methodDescriptor);
            }
         }
      }

      return methodDescriptorMap;
   }

   private static ArrayMap processRMIMethodAnnotation(RmiMethod rmiMethod, Class c) throws IOException {
      ArrayMap methodDescriptor = new ArrayMap();
      String dispatchPolicy = rmiMethod.dispatchPolicy();
      if (dispatchPolicy != null && dispatchPolicy.length() > 0) {
         methodDescriptor.put("dispatch-policy", rmiMethod.dispatchPolicy());
      }

      methodDescriptor.put("transactional", Boolean.toString(rmiMethod.transactional()));
      if (rmiMethod.oneway()) {
         methodDescriptor.put("oneway", Boolean.toString(rmiMethod.oneway()));
      }

      boolean async = rmiMethod.asynchronousResult();
      if (async) {
         methodDescriptor.put("asynchronous", Boolean.toString(async));
      }

      if (rmiMethod.timeout() > 0) {
         methodDescriptor.put("timeout", Integer.toString(rmiMethod.timeout()));
      }

      if (rmiMethod.idempotent()) {
         methodDescriptor.put("idempotent", Boolean.toString(rmiMethod.idempotent()));
      }

      return methodDescriptor.keySet().size() > 0 ? methodDescriptor : null;
   }

   private static boolean checkValidRemoteExceptionWrapperClass(Class c) {
      return RuntimeException.class.isAssignableFrom(c);
   }

   private static boolean checkValidRemoteInterfaces(Class c, Class[] remoteInterfaces) {
      if (remoteInterfaces != null && remoteInterfaces.length > 0) {
         Set implementedInterfaces = new LinkedHashSet();
         implementedInterfaces.addAll(getInterfaces(c, implementedInterfaces));

         while(c.getSuperclass() != null) {
            c = c.getSuperclass();
            implementedInterfaces.addAll(getInterfaces(c, implementedInterfaces));
         }

         for(int i = 0; i < remoteInterfaces.length; ++i) {
            if (!implementedInterfaces.contains(remoteInterfaces[i])) {
               return false;
            }
         }
      }

      return true;
   }

   private static Set getInterfaces(Class c, Set s) {
      Class[] implementedInterfaces = c.getInterfaces();

      for(int i = 0; i < implementedInterfaces.length; ++i) {
         s.add(implementedInterfaces[i]);
         s.addAll(getInterfaces(implementedInterfaces[i], s));
      }

      return s;
   }

   static {
      loadAlgorithms.put(LoadAlgorithmType.RANDOM, "random");
      loadAlgorithms.put(LoadAlgorithmType.ROUND_ROBIN, "round-robin");
      loadAlgorithms.put(LoadAlgorithmType.WEIGHT_BASED, "weight-based");
      loadAlgorithms.put(LoadAlgorithmType.SERVER_AFFINITY, "server-affinity");
      loadAlgorithms.put(LoadAlgorithmType.ROUND_ROBIN_AFFINITY, "round-robin-affinity");
      loadAlgorithms.put(LoadAlgorithmType.RANDOM_AFFINITY, "random-affinity");
      loadAlgorithms.put(LoadAlgorithmType.WEIGHT_BASED_AFFINITY, "weight-based-affinity");
      loadAlgorithms.put(LoadAlgorithmType.DEFAULT, "default");
      securityOptions.put(Security.NONE, "none");
      securityOptions.put(Security.SUPPORTED, "supported");
      securityOptions.put(Security.REQUIRED, "required");
      securityOptions.put(Security.CONFIG, "config");
      dgcPolicies.put(DgcPolicy.DEFAULT, "");
      dgcPolicies.put(DgcPolicy.LEASED, "leased");
      dgcPolicies.put(DgcPolicy.REFERENCE_COUNTED, "referenceCounted");
      dgcPolicies.put(DgcPolicy.MANAGED, "managed");
      dgcPolicies.put(DgcPolicy.USE_IT_OR_LOSE_IT, "useItOrLoseIt");
      dgcPolicies.put(DgcPolicy.DEACTIVATE_ON_METHOD_BOUNDARIES, "deactivateOnMethodBoundaries");
      dispatchContexts.put(DispatchContext.NONE, "none");
      dispatchContexts.put(DispatchContext.FUTURE, "future");
      dispatchContexts.put(DispatchContext.REQUEST, "request");
   }
}
