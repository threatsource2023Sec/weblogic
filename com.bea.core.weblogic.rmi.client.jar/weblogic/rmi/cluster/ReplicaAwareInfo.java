package weblogic.rmi.cluster;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.internal.RMIEnvironment;
import weblogic.rmi.internal.RuntimeDescriptor;

public final class ReplicaAwareInfo {
   private final boolean propagateEnvironment;
   private final boolean stickToFirstServer;
   private String jndiName;
   private String callRouterName;
   private String replicaHandlerClassName;
   private transient CallRouter callRouter;
   private transient Class replicaHandlerClass;

   ReplicaAwareInfo(boolean stickToFirstServer, String jndiName, String callRouterName, boolean propagateEnvironment, String replicaHandlerClassName) {
      this.stickToFirstServer = stickToFirstServer;
      this.jndiName = jndiName;
      this.callRouterName = callRouterName;
      this.propagateEnvironment = propagateEnvironment;
      this.replicaHandlerClassName = replicaHandlerClassName;
   }

   public ReplicaAwareInfo(RuntimeDescriptor desc) throws IllegalArgumentException {
      this(desc.getStickToFirstServer(), (String)null, desc.getCallRouterClassName(), desc.getPropagateEnvironment(), getReplicaHandlerClass(desc));
   }

   public ReplicaAwareInfo(boolean stickToFirstServer, String algorithm, String jndiName) throws IllegalArgumentException {
      this(stickToFirstServer, jndiName, (String)null, true, getReplicaHandlerClass((String)null, algorithm));
   }

   public ReplicaAwareInfo(boolean stickToFirstServer, String replicaHandler, String algorithm, boolean propagateEnvironment) {
      this(stickToFirstServer, (String)null, (String)null, true, getReplicaHandlerClass(replicaHandler, algorithm));
   }

   public ReplicaAwareInfo(String jndiName, Class replicaHandlerClass) {
      this(false, jndiName, (String)null, true, (String)null);
      this.replicaHandlerClass = replicaHandlerClass;
   }

   private static String getReplicaHandlerClass(RuntimeDescriptor desc) {
      return getReplicaHandlerClass(desc.getReplicaHandlerClassName(), desc.getLoadAlgorithm());
   }

   private static String getReplicaHandlerClass(String className, String algorithm) {
      if (className == null) {
         if (algorithm.equals("default")) {
            algorithm = RMIEnvironment.getEnvironment().getClusterDefaultLoadAlgorithm();
            if (algorithm == null) {
               algorithm = "round-robin";
            }
         }

         if (algorithm.equals("random")) {
            className = RandomReplicaHandler.class.getName();
         } else if (algorithm.equals("round-robin")) {
            className = BasicReplicaHandler.class.getName();
         } else if (algorithm.equals("weight-based")) {
            className = WeightBasedReplicaHandler.class.getName();
         } else if (algorithm.equals("server-affinity")) {
            className = BasicAffinityReplicaHandler.class.getName();
         } else if (algorithm.equals("round-robin-affinity")) {
            className = BasicAffinityReplicaHandler.class.getName();
         } else if (algorithm.equals("random-affinity")) {
            className = RandomAffinityReplicaHandler.class.getName();
         } else if (algorithm.equals("weight-based-affinity")) {
            className = WeightBasedAffinityReplicaHandler.class.getName();
         } else if (algorithm.equals("primary-secondary")) {
            className = PrimarySecondaryReplicaHandler.class.getName();
         } else if (algorithm.equals("migratable")) {
            className = MigratableReplicaHandler.class.getName();
         } else {
            className = BasicReplicaHandler.class.getName();
         }
      }

      return className;
   }

   private Class getReplicaHandlerClass() {
      if (this.replicaHandlerClass == null) {
         this.replicaHandlerClass = classForName(this.replicaHandlerClassName, ReplicaHandler.class);
      }

      return this.replicaHandlerClass;
   }

   public boolean getStickToFirstServer() {
      return this.stickToFirstServer;
   }

   ReplicaHandler getReplicaHandler(RemoteReference primary) {
      Class[] sig = new Class[]{ReplicaAwareInfo.class, RemoteReference.class};
      Object[] params = new Object[]{this, primary};
      return (ReplicaHandler)instantiate(this.getReplicaHandlerClass(), sig, params);
   }

   PiggybackResponder getReplicaListUpdater(ClusterableServerRef serverRef) {
      return PiggybackRequester.class.isAssignableFrom(this.getReplicaHandlerClass()) ? new ReplicaListUpdater(serverRef) : null;
   }

   CallRouter getCallRouter() {
      if (this.callRouterName != null) {
         Class cls = classForName(this.callRouterName, CallRouter.class);
         Class[] sig = new Class[0];
         Object[] params = new Object[0];
         return this.callRouter = (CallRouter)instantiate(cls, sig, params);
      } else {
         return this.callRouter;
      }
   }

   boolean getPropagateEnvironment() {
      return this.propagateEnvironment;
   }

   public String getJNDIName() {
      return this.jndiName;
   }

   public void setJNDIName(String name) {
      this.jndiName = name;
   }

   private static Class classForName(String className, Class expectedType) throws IllegalArgumentException {
      if (className == null) {
         return null;
      } else {
         try {
            ClassLoader currentCL = Thread.currentThread().getContextClassLoader();
            Class c = Class.forName(className, true, currentCL);
            if (expectedType.isAssignableFrom(c)) {
               return c;
            } else {
               throw new IllegalArgumentException("Class is not an instanceof " + expectedType.getName());
            }
         } catch (ClassNotFoundException var4) {
            throw new IllegalArgumentException("Class " + className + " not found", var4);
         }
      }
   }

   private static Object instantiate(Class c, Class[] signature, Object[] params) throws IllegalArgumentException {
      try {
         Constructor constructor = c.getConstructor(signature);
         return constructor.newInstance(params);
      } catch (NoSuchMethodException | InstantiationException | IllegalAccessException var4) {
         throw new IllegalArgumentException("Failed to instantiate " + c.getName() + " due to " + var4, var4);
      } catch (InvocationTargetException var5) {
         throw new IllegalArgumentException("Failed to instantiate " + c.getName() + " due to " + var5.getTargetException(), var5);
      }
   }

   static boolean isServerInCluster() {
      return RMIEnvironment.getEnvironment().isServerInCluster();
   }

   public String toString() {
      return super.toString() + " - stickToFirstServer: '" + this.stickToFirstServer + "', jndiName: '" + this.jndiName + "'callRouter: '" + this.callRouter + "', replicaHandlerClass: '" + this.replicaHandlerClass + "'";
   }

   private static class ReplicaListUpdater implements PiggybackResponder {
      private ClusterableServerRef serverRef;

      public ReplicaListUpdater(ClusterableServerRef serverRef) {
         this.serverRef = serverRef;
      }

      public PiggybackResponse handlePiggybackRequest(Object request) {
         Version serverVersion = this.serverRef.getReplicaList().version();
         if (this.isClientVersionObsolete(request, serverVersion)) {
            return this.serverRef.getReplicaList();
         } else {
            return this.serverRef.getReplicaList().isReplicaVersionChanged() ? this.serverRef.getReplicaList().getReplicaVersion() : null;
         }
      }

      private boolean isClientVersionObsolete(Object request, Version serverVersion) {
         return serverVersion.getVersion() != 0L && !serverVersion.equals(request);
      }
   }
}
