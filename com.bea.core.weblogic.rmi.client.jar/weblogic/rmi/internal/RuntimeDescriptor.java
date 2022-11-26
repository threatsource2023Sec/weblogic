package weblogic.rmi.internal;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.rmi.server.SkeletonNotFoundException;
import java.util.HashMap;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.utils.classloaders.Source;

public interface RuntimeDescriptor {
   String RANDOM_ALGORITHM = "random";
   String ROUND_ROBIN_ALGORITHM = "round-robin";
   String WEIGHT_BASED_ALGORITHM = "weight-based";
   String BASIC_AFFINITY_ALGORITHM = "server-affinity";
   String PRIMARY_SECONDARY_ALGORITHM = "primary-secondary";
   String ROUND_ROBIN_AFFINITY_ALGORITHM = "round-robin-affinity";
   String RANDOM_AFFINITY_ALGORITHM = "random-affinity";
   String WEIGHT_BASED_AFFINITY_ALGORITHM = "weight-based-affinity";
   String MIGRATABLE_ALGORITHM = "migratable";
   String DEFAULT_ALGORITHM = "default";
   String NONE_VAL = "none";
   String SUPPORTED_VAL = "supported";
   String REQUIRED_VAL = "required";
   String CONFIG_VAL = "config";

   String getRemoteClassName();

   String getStubClassName();

   String getSkeletonClassName();

   int getDGCPolicy();

   String getRemoteReferenceClassName();

   int getInitialReference();

   String getNetworkAccessPoint();

   String getDispatchPolicyName();

   boolean getEnableServerSideStubs();

   boolean getEnforceCallByValue();

   boolean isClusterable();

   boolean getStickToFirstServer();

   boolean getMethodsAreIdempotent();

   boolean getMethodsAreTransactional();

   String getLoadAlgorithm();

   String getReplicaHandlerClassName();

   String getCallRouterClassName();

   boolean getPropagateEnvironment();

   String[] getRemoteInterfacesClassNames();

   Constructor getCBVWrapper();

   Skeleton getSkeleton() throws SkeletonNotFoundException;

   RemoteReference getRemoteReference(int var1, Object var2) throws RemoteException;

   RuntimeMethodDescriptor getControlDescriptor(RuntimeMethodDescriptor var1);

   HashMap getClientMethodDescriptors();

   ClientMethodDescriptor getDefaultClientMethodDescriptor();

   RemoteType getRemoteType();

   MethodDescriptor getMethodDescriptor(String var1);

   ClientMethodDescriptor getClientMethodDescriptor(String var1);

   Method getMethod(String var1);

   MethodDescriptor[] getMethodDescriptors();

   Method[] getRemoteMethods();

   Class[] getRemoteInterfaces();

   ClientRuntimeDescriptor getClientRuntimeDescriptor(String var1);

   boolean isActivatable();

   String getConfidentiality();

   String getClientCertAuthentication();

   String getClientAuthentication();

   String getIdentityAssertion();

   String getIntegrity();

   boolean getStatefulAuthentication();

   Class getRemoteClass();

   ServerReference createServerReference(Object var1) throws RemoteException;

   boolean hasCustomMethodDescriptors();

   Source getRuntimeDescriptorSource() throws IOException;

   void generateXMLDescriptor(String var1, String var2) throws IOException;
}
