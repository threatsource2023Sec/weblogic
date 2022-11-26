package weblogic.j2ee.descriptor.wl;

public interface PortPolicyBean {
   void setPortName(String var1);

   String getPortName();

   WsPolicyBean[] getWsPolicy();

   WsPolicyBean createWsPolicy();

   OwsmSecurityPolicyBean[] getOwsmSecurityPolicy();

   OwsmSecurityPolicyBean createOwsmSecurityPolicy();

   void destroyWsPolicy(WsPolicyBean var1);

   void destroyOwsmSecurityPolicy(OwsmSecurityPolicyBean var1);
}
