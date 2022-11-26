package weblogic.j2ee.descriptor.wl;

public interface WebservicePolicyRefBean {
   void setRefName(String var1);

   String getRefName();

   PortPolicyBean[] getPortPolicy();

   PortPolicyBean createPortPolicy();

   void destroyPortPolicy(PortPolicyBean var1);

   OperationPolicyBean[] getOperationPolicy();

   OperationPolicyBean createOperationPolicy();

   void destroyOperationPolicy(OperationPolicyBean var1);

   String getVersion();

   void setVersion(String var1);
}
