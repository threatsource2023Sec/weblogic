package weblogic.management.runtime;

public interface WseeBaseRuntimeMBean extends RuntimeMBean {
   String getServiceName();

   String getURI();

   WseePortRuntimeMBean[] getPorts();

   WseePolicyRuntimeMBean getPolicyRuntime();

   OwsmSecurityPolicyRuntimeMBean getOwsmSecurityPolicyRuntime();

   long getConversationInstanceCount();

   String getImplementationType();

   String getWebserviceDescriptionName();

   void addPort(WseePortRuntimeMBean var1);

   long getStartTime();

   int getPolicyFaults();

   /** @deprecated */
   @Deprecated
   int getTotalFaults();

   int getTotalSecurityFaults();

   Type getWsType();

   public static enum Type {
      JAXRPC("JAX-RPC"),
      JAXWS("JAX-WS");

      private String _name;

      private Type(String name) {
         this._name = name;
      }

      public String toString() {
         return this._name;
      }
   }
}
