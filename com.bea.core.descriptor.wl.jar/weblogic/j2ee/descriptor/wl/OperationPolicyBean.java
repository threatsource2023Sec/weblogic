package weblogic.j2ee.descriptor.wl;

public interface OperationPolicyBean {
   void setOperationName(String var1);

   String getOperationName();

   String getServiceLink();

   void setServiceLink(String var1);

   WsPolicyBean[] getWsPolicy();

   WsPolicyBean createWsPolicy();

   void destroyWsPolicy(WsPolicyBean var1);
}
